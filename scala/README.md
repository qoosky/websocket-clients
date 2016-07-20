Qoosky IoT BaaS APIs / Cloud Controller / Scala
==================
本ページは [Qoosky Cloud Controller](https://www.qoosky.io/help/api) を Raspberry Pi から利用するための情報をまとめたものです。ここでは特に Scala を利用した接続方法をまとめます。

### 必要なもの
- インターネット接続設定が完了した Raspberry Pi
- Scala ソースコードをビルドするための PC 側の環境

以下の手順では PC 側の環境で Scala ソースコードをビルドして Raspberry Pi の java で実行できる jar を生成します。これを Raspberry Pi にコピーして使用します。Raspberry Pi には Scala 関連の設定をする必要がありません。


PC 側の環境における Scala のインストール
==================
ビルドツールの [sbt](http://www.scala-sbt.org/) をインストールして Scala を利用できるようにします。


Mac OS X
------------------

	$ brew install sbt


その他の UNIX 系 OS
------------------
sbt JAR をダウンロードして適当なディレクトリに保存します。

	$ wget https://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/0.13.12/sbt-launch.jar
	$ mv sbt-launch.jar ~/bin/

JAR を実行するシェルスクリプト `~/bin/sbt` を用意します。

```bash
#!/bin/bash
SBT_OPTS="-Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxMetaspaceSize=256M" # java 8 の場合
#SBT_OPTS="-Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=256M" # java 6-7 の場合
java $SBT_OPTS -jar `dirname $0`/sbt-launch.jar "$@"
```

パーミッションを設定します。

	$ chmod +x ~/bin/sbt

Scala を起動してみましょう。初回実行時は非常に時間がかかります。`~/.sbt/` および `~/.ivy2/` に Scala の JAR 等、sbt の動作に必要な JAR がダウンロードされるためです。

	$ sbt console


Raspberry Pi への Java のインストール
==================
OS によっては既にインストール済みの場合があります。以下のコマンドで確認します。

	pi@raspberrypi:~ $ which java
	/usr/bin/java

インストールされていない場合は以下のコマンドでインストールします。

	pi@raspberrypi:~ $ sudo apt-get install oracle-java8-jdk


PC 側の環境におけるサンプルコードのダウンロード
==================
以下のコマンドで本レポジトリのサンプルコードをダウンロードします。

	$ git clone https://github.com/qoosky/websocket_clients.git
	$ cd websocket_clients/scala/

Main.scala

```scala
import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ws._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Main {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    var opened: Boolean = false

    // Handle messages from WebSocket. Send your API token once when it first receives a message.
    val flow: Flow[Message, Message, _] =
      Flow[Message].mapConcat{
        case message: TextMessage.Strict => {
          println("received: %s" format message.text)
          if(!opened) {
            opened = true
            List(TextMessage("""{"token":"XXXX-XXXX-XXXX-XXXX"}"""))
          }
          else Nil // Empty List
        }
        case _ => Nil
      }

    // WebSocket(Source) ~> flow ~> WebSocket(Sink)
    val (upgradeResponse, _) =
      Http().singleWebSocketRequest(WebSocketRequest("ws://api.qoosky.io/v1/controller/actuator/ws"), flow)
    val connected = upgradeResponse.map { upgrade: WebSocketUpgradeResponse =>
      if (upgrade.response.status == StatusCodes.SwitchingProtocols) Done
      else throw new RuntimeException("An unexpected error has occurred: %s" format upgrade.response.status)
    }

    connected.onComplete {
      case Success(result) => println("Successfully connected to the API server.")
      case Failure(err) => {
        println(err)
        system.terminate
        println("Connection closed.")
      }
    }
  }
}
```

以下の部分を [Qoosky Cloud Controller](https://www.qoosky.io/help/api/cc) で発行した API トークンで書き換えてください。

	List(TextMessage("""{"token":"XXXX-XXXX-XXXX-XXXX"}"""))


PC 側の環境における Scala サンプルコードのビルド
==================
以下のコマンドでビルドします。

	$ sbt assembly


Raspberry Pi への JAR ファイルの転送および実行
==================
以下のコマンドで Raspberry Pi にビルドした JAR ファイルを転送します。

	$ scp target/scala-2.11/ws-assembly-1.0.jar pi@192.168.xxx.xxx:~/

Raspberry Pi で実行してみましょう。

	pi@raspberrypi:~ $ java -jar ws-assembly-1.0.jar

以下のように出力されれば成功です。ゲームパッド状の [Cloud Controller](https://www.qoosky.io/help/api/cc) を起動すれば相互接続できる状態になりました。

	pi@raspberrypi:~ $ java -jar ws-assembly-1.0.jar
	Successfully connected to the API server.
	received: {"notification":"Please send your Qoosky API token in the following json format, {\"token\":\"XXXX-XXXX-XXXX-XXXX\"}"}
	received: {"notification":"Authentication success."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
