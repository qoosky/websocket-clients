const WebSocket = require('ws');
const ws = new WebSocket('ws://api.qoosky.io/v1/websocket-relay-server/actuator/ws');

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
