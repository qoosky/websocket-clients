# Ruby

WebSocket client sample of Ruby for [Qoosky API](https://www.qoosky.io/help/api).

- [websocket-client-simple](https://github.com/shokai/websocket-client-simple)

# How to install and run

```
gem install websocket-client-simple -v 0.3.0
ruby ws.rb
ruby wss.rb
```

Response from the API server:

```
Successfully connected to the API server.
received: {"notification":"Invalid Qoosky API token. Authentication failure."}
received: {"notification":"Please send your Qoosky API token in the following json format, {\"token\":\"XXXX-XXXX-XXXX-XXXX\"}"}
received: {"notification":"Please send your Qoosky API token in the following json format, {\"token\":\"XXXX-XXXX-XXXX-XXXX\"}"}
received: {"notification":"Please send your Qoosky API token in the following json format, {\"token\":\"XXXX-XXXX-XXXX-XXXX\"}"}
received: {"notification":"Please send your Qoosky API token in the following json format, {\"token\":\"XXXX-XXXX-XXXX-XXXX\"}"}
```
