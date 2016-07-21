Qoosky IoT BaaS APIs / Cloud Controller / Java
==================
本ページは [Qoosky Cloud Controller](https://www.qoosky.io/help/api) を Raspberry Pi から利用するための情報をまとめたものです。ここでは特に Java を利用した接続方法をまとめます。

### 必要なもの
- インターネット接続設定が完了した Raspberry Pi


Java のインストール
==================
OS によっては既にインストール済みの場合があります。以下のコマンドで確認します。

	pi@raspberrypi:~ $ which java
	/usr/bin/java

インストールされていない場合は以下のコマンドでインストールします。

	pi@raspberrypi:~ $ sudo apt-get install oracle-java8-jdk


ライブラリのダウンロード
==================
[Java WebSockets](https://github.com/TooTallNate/Java-WebSocket) ライブラリを利用します。以下のコマンドを実行して JAR ファイルをダウンロードします。なお、最新の JAR ファイルは [The Central Repository](http://search.maven.org/#search|ga|1|a%3A%22Java-WebSocket%22) からダウンロードできます。

	pi@raspberrypi:~ $ wget https://github.com/qoosky/websocket_clients/raw/master/java/Java-WebSocket-1.3.0.jar


サンプルコードのダウンロード
==================
以下のコマンドで本レポジトリのサンプルコードをダウンロードします。

	pi@raspberrypi:~ $ wget https://raw.githubusercontent.com/qoosky/websocket_clients/master/java/Main.java
	pi@raspberrypi:~ $ wget https://raw.githubusercontent.com/qoosky/websocket_clients/master/java/WSClient.java

Main.java

```java
class Main {
    public static void main(String args[]) {
        try {
            WSClient ws = new WSClient("ws://api.qoosky.io/v1/controller/actuator/ws");
            ws.connect();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
```

WSClient.java

```java
import org.java_websocket.client.*;
import org.java_websocket.handshake.*;
import org.java_websocket.drafts.Draft_17;
import java.net.URISyntaxException;

class WSClient extends WebSocketClient {

    public WSClient(String url) throws URISyntaxException {
        super(new java.net.URI(url), new Draft_17());
    }

    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Successfully connected to the API server.");
        String str = "{\"token\":\"XXXX-XXXX-XXXX-XXXX\"}";
        send(str);
    }

    public void onError(Exception ex) {
        throw new RuntimeException("An unexpected error has occurred: " + ex.toString());
    }

    public void onMessage(String message) {
        System.out.println("received: " + message);
    }

    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed.");
    }
}
```

以下の部分を [Qoosky Cloud Controller](https://www.qoosky.io/help/api/cc) で発行した API トークンで書き換えてください。

	String str = "{\"token\":\"XXXX-XXXX-XXXX-XXXX\"}";


ビルドおよび実行
==================
以下のコマンドでビルドします。

	pi@raspberrypi:~ $ javac -cp ./Java-WebSocket-1.3.0.jar Main.java WSClient.java

実行してみましょう。

	pi@raspberrypi:~ $ java -cp '.:./Java-WebSocket-1.3.0.jar' Main

以下のように出力されれば成功です。ゲームパッド状の [Cloud Controller](https://www.qoosky.io/account/api/cc) を起動すれば相互接続できる状態になりました。

	pi@raspberrypi:~ $ java -cp '.:./Java-WebSocket-1.3.0.jar' Main
	Successfully connected to the API server.
	received: {"notification":"Authentication success."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
