Qoosky IoT BaaS APIs / Cloud Controller / Ruby
==================
本ページは [Qoosky Cloud Controller](https://www.qoosky.io/help/api) を Raspberry Pi から利用するための情報をまとめたものです。ここでは特に Ruby を利用した接続方法をまとめます。

### 必要なもの
- インターネット接続設定が完了した Raspberry Pi


Ruby がインストールされていることを確認
==================
以下のコマンドで Ruby がインストール済みであることを確認します。

	pi@raspberrypi:~ $ which ruby
	/usr/bin/ruby


ライブラリのインストール
==================
[websocket-client-simple](https://github.com/shokai/websocket-client-simple) ライブラリを利用します。内部的には [WebSocket Ruby](https://github.com/imanel/websocket-ruby) が利用されています。以下のコマンドを実行して gem をインストールします。

	pi@raspberrypi:~ $ sudo gem install websocket-client-simple -v 0.3.0


サンプルコードのダウンロード
==================
以下のコマンドで本レポジトリのサンプルコードをダウンロードします。

	pi@raspberrypi:~ $ wget https://raw.githubusercontent.com/qoosky/websocket_clients/master/ruby/ws.rb
	pi@raspberrypi:~ $ wget https://raw.githubusercontent.com/qoosky/websocket_clients/master/ruby/wss.rb

平文通信を行う場合 (ws.rb)

```ruby
#!/usr/bin/ruby
# -*- coding: utf-8 -*-
require 'websocket-client-simple'

ws = WebSocket::Client::Simple.connect 'ws://api.qoosky.io/v1/controller/actuator/ws'

ws.on :open do
  if ws.handshake.valid?
    puts "Successfully connected to the API server."
    ws.send '{"token":"XXXX-XXXX-XXXX-XXXX"}'
  else
    ws.emit :error, 'websocket handshake failed.'
  end
end

ws.on :error do |err|
  puts "An unexpected error has occurred: #{err}"
  ws.emit :close
end

ws.on :message do |msg|
  puts "received: #{msg}"
end

ws.on :close do |e|
  puts "Connection closed."
  exit 1
end

loop do
  sleep 1
end
```

SSL 通信を行う場合 (wss.rb)

```ruby
#!/usr/bin/ruby
# -*- coding: utf-8 -*-
require 'websocket-client-simple'

ws = WebSocket::Client::Simple.connect 'wss://api.qoosky.io/v1/controller/actuator/ws'

ws.on :open do
  if ws.handshake.valid?
    puts "Successfully connected to the API server."
    ws.send '{"token":"XXXX-XXXX-XXXX-XXXX"}'
  else
    ws.emit :error, 'websocket handshake failed.'
  end
end

ws.on :error do |err|
  puts "An unexpected error has occurred: #{err}"
  ws.emit :close
end

ws.on :message do |msg|
  puts "received: #{msg}"
end

ws.on :close do |e|
  puts "Connection closed."
  exit 1
end

loop do
  sleep 1
end
```

以下の部分を [Qoosky Cloud Controller](https://www.qoosky.io/help/api) で発行した API トークンで書き換えてください。

	ws.send '{"token":"XXXX-XXXX-XXXX-XXXX"}'


実行および結果の確認
==================
以下のコマンドで起動します。

	pi@raspberrypi:~ $ ruby ws.rb
	pi@raspberrypi:~ $ ruby wss.rb

以下のように出力されれば成功です。ゲームパッド状の [Cloud Controller](https://www.qoosky.io/help/api/cc) を起動すれば相互接続できる状態になりました。

ws.rb

	pi@raspberrypi:~ $ ruby ws.rb
	Successfully connected to the API server.
	received: {"notification":"Authentication success."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}

wss.rb

	pi@raspberrypi:~ $ ruby wss.rb
	Successfully connected to the API server.
	received: {"notification":"Authentication success."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
	received: {"notification":"Searching for your cloud controller device..."}
