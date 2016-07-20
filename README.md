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
