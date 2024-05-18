# Scala

WebSocket client sample of Scala for [Qoosky API](https://www.qoosky.dev/help/api).

- [Akka 2.5.31](https://doc.akka.io//docs/akka/2.5.31/?language=scala)
- [Akka HTTP 10.1.11](https://doc.akka.io//docs/akka-http/10.1.11/scala/http/)

# How to build and run

```
sbt run
```

Response from the API server:

```
Successfully connected to the API server.
received: {"notification":"Invalid Qoosky API token. Authentication failure."}
received: {"notification":"Please send your Qoosky API token in the following json format, {\"token\":\"XXXX-XXXX-XXXX-XXXX\"}"}
received: {"notification":"Please send your Qoosky API token in the following json format, {\"token\":\"XXXX-XXXX-XXXX-XXXX\"}"}
received: {"notification":"Please send your Qoosky API token in the following json format, {\"token\":\"XXXX-XXXX-XXXX-XXXX\"}"}
received: {"notification":"Please send your Qoosky API token in the following json format, {\"token\":\"XXXX-XXXX-XXXX-XXXX\"}"}
received: {"notification":"Please send your Qoosky API token in the following json format, {\"token\":\"XXXX-XXXX-XXXX-XXXX\"}"}
```
