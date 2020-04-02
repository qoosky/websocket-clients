public class App {
    public static void main(String[] args) {
        try {
            WSClient ws = new WSClient("ws://api.qoosky.io/v1/websocket-relay-server/actuator/ws");
            System.out.println("starting");
            ws.connect();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
