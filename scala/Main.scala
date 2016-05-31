import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ws._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Main {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    var opened: Boolean = false

    // Handle messages from WebSocket. Send your API token once when it first receives a message.
    val flow: Flow[Message, Message, _] =
      Flow[Message].mapConcat{
        case message: TextMessage.Strict => {
          println("received: %s" format message.text)
          if(!opened) {
            opened = true
            List(TextMessage("""{"token":"XXXX-XXXX-XXXX-XXXX"}"""))
          }
          else Nil // Empty List
        }
        case _ => Nil
      }

    // WebSocket(Source) ~> flow ~> WebSocket(Sink)
    val (upgradeResponse, _) =
      Http().singleWebSocketRequest(WebSocketRequest("ws://api.qoosky.io/v1/controller/actuator/ws"), flow)
    val connected = upgradeResponse.map { upgrade: WebSocketUpgradeResponse =>
      if (upgrade.response.status == StatusCodes.SwitchingProtocols) Done
      else throw new RuntimeException("An unexpected error has occurred: %s" format upgrade.response.status)
    }

    connected.onComplete {
      case Success(result) => println("Successfully connected to the API server.")
      case Failure(err) => {
        println(err)
        system.terminate
        println("Connection closed.")
      }
    }
  }
}
