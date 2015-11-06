import server.SocketServer;

public class Main {
    public static void main(String[] args) {
        SocketServer server = new SocketServer(3322);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}
