class Main {
    public static void main(String args[]) {
        try {
            WSClient ws = new WSClient("ws://api.qoosky.io/v1/controller/actuator/ws");
            ws.connect();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
