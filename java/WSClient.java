import org.java_websocket.client.*;
import org.java_websocket.handshake.*;
import org.java_websocket.drafts.Draft_17;
import java.net.URISyntaxException;

class WSClient extends WebSocketClient {

    public WSClient(String url) throws URISyntaxException {
        super(new java.net.URI(url), new Draft_17());
    }

    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Successfully connected to the API server.");
        String str = "{\"token\":\"XXXX-XXXX-XXXX-XXXX\"}";
        send(str);
    }

    public void onError(Exception ex) {
        throw new RuntimeException("An unexpected error has occurred: " + ex.toString());
    }

    public void onMessage(String message) {
        System.out.println("received: " + message);
    }

    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed.");
    }
}
