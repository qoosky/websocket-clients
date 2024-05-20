#!/path/to/python
# -*- coding: utf-8 -*-
import websocket
import ssl

def on_open(ws):
    print("Successfully connected to the API server.")
    ws.send('{"token":"XXXX-XXXX-XXXX-XXXX"}')

def on_error(ws, error):
    print("An unexpected error has occurred: %s" % error)
    ws.close()

def on_message(ws, message):
    print("received: %s" % message)

def on_close(ws):
    print("Connection closed.")


if __name__ == "__main__":
    websocket.enableTrace(False)
    ws = websocket.WebSocketApp("wss://api.qoosky.dev/v1/websocket-relay-server/actuator/ws", on_open=on_open, on_error=on_error, on_message=on_message, on_close=on_close)
    ws.run_forever(sslopt={"ca_certs": '../qoosky-dev-ca-root.crt'})
    # ws.run_forever(sslopt={"cert_reqs": ssl.CERT_NONE})
