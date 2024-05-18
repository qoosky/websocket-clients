#!/path/to/ruby
# -*- coding: utf-8 -*-
require 'websocket-client-simple'

ws = WebSocket::Client::Simple.connect 'wss://api.qoosky.dev/v1/websocket-relay-server/actuator/ws'

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
