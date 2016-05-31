Qoosky WebSocket API Clients
==================
This repository provides simple WebSocket clients especially intended to be used for Qoosky Cloud Controller API.  You can control your internet connected devices such as Raspberry Pi and Intel Edison in real-time and remotely by using the WebSocket API.  Please refer to the [Qoosky help](https://www.qoosky.io/help) for the information about the server architecture and how to use the API controller.


Python
==================

## Requirements

- python 2.7

## Libraries

- [websocket-client](https://github.com/liris/websocket-client)

## Installation

### Use "system" Python

	sudo pip install websocket-client

### pyenv

If you use [pyenv](https://www.qoosky.io/techs/0cf33bd9ac), please execute the following commands to install pythons.

	pyenv install 2.7.10
	pyenv rehash
	pyenv shell 2.7.10
	pyenv exec pip install --upgrade pip

Then install the library.

	pyenv exec pip install websocket-client

## Proxy settings

If you want to use the API in some proxy environments, please set the proxy information by referring [here](https://github.com/liris/websocket-client#http-proxy).  In proxy environments, [WebSocket connection can sometimes fail](https://en.wikipedia.org/wiki/WebSocket#Proxy_traversal).  Be sure to use SSL (wss) connections rather than unencrypted ones to prevent our WebSocket headers from being dropped by intermediate proxy servers.


Ruby
==================

## Requirements

At least tested with ruby 2.1.3

## libraries

- [websocket-client-simple](https://github.com/shokai/websocket-client-simple)

This is a simple wrapper library of [WebSocket Ruby](https://github.com/imanel/websocket-ruby).  There are also some other WebSocket client libraries such as [websocket-eventmachine-client](https://github.com/imanel/websocket-eventmachine-client).

## Installation

	sudo gem install websocket-client-simple -v 0.3.0

## About CA Certs

This library does not let our source code trust a custom root CA cert including our `qoosky-io-ca-root.crt`.  Instead, `OpenSSL::SSL::VERIFY_NONE` is [specified](https://github.com/shokai/websocket-client-simple/blob/master/lib/websocket-client-simple/client.rb#L25).


JavaScript
==================

## Libraries

- [websockets/ws](https://github.com/websockets/ws)

There are many other similar JavaScript client libraries.  However, some libraries like [WebSocket-Node](https://github.com/theturtle32/WebSocket-Node) have [known issues](https://github.com/theturtle32/WebSocket-Node/issues/92).

## Installation

Raspberry Pi (NOOBS)

	sudo apt-get update
	sudo apt-get install npm
	npm install ws
	npm install ssl-root-cas

CentOS 6

	sudo yum --enablerepo=epel install npm
	npm install ws
	npm install ssl-root-cas

## Execute client applications

	node ws.js
	node wss.js


Java
==================

## Libraries

- [Java WebSockets](https://github.com/TooTallNate/Java-WebSocket)

There may be some other useful libraries like [engine.io-client-java](https://github.com/socketio/engine.io-client-java).

## Installation & Build

Please download the JAR file from [The Central Repository](http://search.maven.org/#search|ga|1|a%3A%22Java-WebSocket%22) and execute the following command.

	javac -cp ./Java-WebSocket-1.3.0.jar Main.java WSClient.java

## Execute the client application

	java -cp '.:./Java-WebSocket-1.3.0.jar' Main

## About SSL connections

SSL example is not included yet. Please contribute by forking and creating pull requests :)


Scala
==================

## Requirements

- [sbt](https://www.qoosky.io/techs/1ec18db8bc)
- [akka-http](https://www.qoosky.io/techs/a98142497c#%E3%82%AF%E3%83%A9%E3%82%A4%E3%82%A2%E3%83%B3%E3%83%88%E3%82%B5%E3%82%A4%E3%83%89)

## Build the fat JAR

On your PC, execute the assembly command and build a fat JAR.

	sbt assembly

Then scp to your Raspberry Pi and execute the following command.

	java -jar ws-assembly-1.0.jar

## About SSL connections

SSL example is not included yet. Again, it would be nice if you could contribute by forking and creating pull requests ;)
