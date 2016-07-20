Qoosky IoT BaaS APIs / Cloud Controller / Python
==================
本ページは [Qoosky Cloud Controller](https://www.qoosky.io/help/api) を Raspberry Pi から利用するための情報をまとめたものです。ここでは特に Python を利用した接続方法をまとめます。

### 必要なもの
- インターネット接続設定が完了した Raspberry Pi


Python がインストールされていることを確認
==================
以下のコマンドで Python がインストール済みであることを確認します。

	pi@raspberrypi:~ $ which python
	/usr/bin/python


ライブラリのインストール
==================
[websocket-client](https://github.com/liris/websocket-client) を利用します。以下のコマンドを実行してインストールします。

	pi@raspberrypi:~ $ sudo pip install websocket-client


サンプルコードのダウンロード
==================
以下のコマンドで本レポジトリのサンプルコードおよび SSL 通信用の証明書をダウンロードします。

	pi@raspberrypi:~ $ wget https://raw.githubusercontent.com/qoosky/websocket_clients/master/python/ws.py
	pi@raspberrypi:~ $ wget https://raw.githubusercontent.com/qoosky/websocket_clients/master/python/wss.py
	pi@raspberrypi:~ $ wget https://raw.githubusercontent.com/qoosky/websocket_clients/master/qoosky-io-ca-root.crt

平文通信を行う場合 (ws.py)

```python
#!/usr/bin/python
# -*- coding: utf-8 -*-
import websocket

def on_open(ws):
    print "Successfully connected to the API server."
    ws.send('{"token":"XXXX-XXXX-XXXX-XXXX"}')

def on_error(ws, error):
    print "An unexpected error has occurred: %s" % error
    ws.close()

def on_message(ws, message):
    print "received: %s" % message

def on_close(ws):
    print "Connection closed."


if __name__ == "__main__":
    websocket.enableTrace(False)
    ws = websocket.WebSocketApp("ws://api.qoosky.io/v1/controller/actuator/ws",
                                on_open = on_open,
                                on_error = on_error,
                                on_message = on_message,
                                on_close = on_close)
    ws.run_forever()
```

SSL 通信を行う場合 (wss.py)

```python
#!/usr/bin/python
# -*- coding: utf-8 -*-
import websocket
import ssl

def on_open(ws):
    print "Successfully connected to the API server."
    ws.send('{"token":"XXXX-XXXX-XXXX-XXXX"}')

def on_error(ws, error):
    print "An unexpected error has occurred: %s" % error
    ws.close()

def on_message(ws, message):
    print "received: %s" % message

def on_close(ws):
    print "Connection closed."


if __name__ == "__main__":
    websocket.enableTrace(False)
    ws = websocket.WebSocketApp("wss://api.qoosky.io/v1/controller/actuator/ws",
                                on_open = on_open,
                                on_error = on_error,
                                on_message = on_message,
                                on_close = on_close)
    ws.run_forever(sslopt={"ca_certs": './qoosky-io-ca-root.crt'}) # Trust Qoosky Root CA
    # ws.run_forever(sslopt={"cert_reqs": ssl.CERT_NONE}) # Trust any server certificates.
```

以下の部分を [Qoosky Cloud Controller](https://www.qoosky.io/help/api/cc) で発行した API トークンで書き換えてください。

	ws.send('{"token":"XXXX-XXXX-XXXX-XXXX"}')


実行および結果の確認
==================
以下のコマンドで起動します。

	pi@raspberrypi:~ $ python ws.py
	pi@raspberrypi:~ $ python wss.py

以下のように出力されれば成功です。ゲームパッド状の [Cloud Controller](https://www.qoosky.io/help/api/cc) を起動すれば相互接続できる状態になりました。

ws.py

	pi@raspberrypi:~ $ python ws.py
	Successfully connected to the API server.
	received: {"notification":"Authentication success."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}

wss.py

	pi@raspberrypi:~ $ python wss.py
	Successfully connected to the API server.
	received: {"notification":"Authentication success."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
