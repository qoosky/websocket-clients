Qoosky IoT BaaS APIs / Cloud Controller / JavaScript
==================
本ページは [Qoosky Cloud Controller](https://www.qoosky.io/help/api) を Raspberry Pi から利用するための情報をまとめたものです。ここでは特に JavaScript (Node.js) を利用した接続方法をまとめます。

### 必要なもの
- インターネット接続設定が完了した Raspberry Pi


Node.js / npm のインストール
==================
OS によっては既にインストール済みの場合があります。以下のコマンドで確認します。

	pi@raspberrypi:~ $ which npm

インストールされていない場合は以下のコマンドでインストールします。

	pi@raspberrypi:~ $ sudo apt-get update
	pi@raspberrypi:~ $ sudo apt-get install npm


ライブラリのインストール
==================
[websockets/ws](https://github.com/websockets/ws) ライブラリを利用します。以下のコマンドでインストールします。

	pi@raspberrypi:~ $ npm install ws
	pi@raspberrypi:~ $ npm install ssl-root-cas


サンプルコードのダウンロード
==================
以下のコマンドで本レポジトリのサンプルコードおよび SSL 通信用の証明書をダウンロードします。

	pi@raspberrypi:~ $ wget https://raw.githubusercontent.com/qoosky/websocket_clients/master/javascript/ws.js
	pi@raspberrypi:~ $ wget https://raw.githubusercontent.com/qoosky/websocket_clients/master/javascript/wss.js
	pi@raspberrypi:~ $ wget https://raw.githubusercontent.com/qoosky/websocket_clients/master/qoosky-io-ca-root.crt

平文通信を行う場合 (ws.js)

	var ws = new require('ws')('ws://api.qoosky.io/v1/controller/actuator/ws');
	
	ws.on('open', function(){
	  console.log("Successfully connected to the API server.")
	  ws.send('{"token":"XXXX-XXXX-XXXX-XXXX"}');
	});
	
	ws.on('error', function(err){
	  console.log("An unexpected error has occurred: " + err);
	});
	
	ws.on('message', function(data){
	  console.log("received: " + data);
	});
	
	ws.on('close', function(){
	  console.log("Connection closed.");
	});

SSL 通信を行う場合 (wss.js)

	require('ssl-root-cas').addFile(__dirname + '/qoosky-io-ca-root.crt'); // Trust Qoosky Root CA
	// process.env['NODE_TLS_REJECT_UNAUTHORIZED'] = '0'; // Trust any server certificates.
	
	var ws = new require('ws')('wss://api.qoosky.io/v1/controller/actuator/ws');
	
	ws.on('open', function(){
	  console.log("Successfully connected to the API server.")
	  ws.send('{"token":"XXXX-XXXX-XXXX-XXXX"}');
	});
	
	ws.on('error', function(err){
	  console.log("An unexpected error has occurred: " + err);
	});
	
	ws.on('message', function(data){
	  console.log("received: " + data);
	});
	
	ws.on('close', function(){
	  console.log("Connection closed.");
	});

それぞれ以下の部分を [Qoosky Cloud Controller](https://www.qoosky.io/help/api) で発行した API トークンで書き換えてください。

	ws.send('{"token":"XXXX-XXXX-XXXX-XXXX"}');


実行および結果の確認
==================
以下のコマンドで起動します。

	pi@raspberrypi:~ $ node ws.js
	pi@raspberrypi:~ $ node wss.js

以下のように出力されれば成功です。ゲームパッド状の [Cloud Controller](https://www.qoosky.io/help/api/cc) を起動すれば相互接続できる状態になりました。

ws.js

	pi@raspberrypi:~ $ node ws.js
	Successfully connected to the API server.
	received: {"notification":"Authentication success."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}

wss.js

	pi@raspberrypi:~ $ node wss.js
	Successfully connected to the API server.
	received: {"notification":"Authentication success."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
