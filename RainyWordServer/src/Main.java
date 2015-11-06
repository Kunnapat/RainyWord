import server.SocketServer;

/**
 * Created by peth on 11/5/2015 AD.
 */
public class Main {
    public static void main(String[] args) {
        SocketServer server = new SocketServer(3322);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}
