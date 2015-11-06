import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * TODO: Add desription of Class
 * <p/>
 * A part of RainyWord project.
 *
 * @author Patcharapon Nuimark
 */
public class SocketClient {
    public static void main(String[] args) throws Exception {
        SocketClient client = new SocketClient();
        connectServer();
    }

    public static void connectServer() throws Exception {
        Socket socket = new Socket("localhost", 3322);
        PrintStream printStream = new PrintStream(socket.getOutputStream()); // this is
        // what
        // going out
        printStream.println("Hello Client"); // from client to server.

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream()); // listening
        // what
        // server
        // response.
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String message = bufferedReader.readLine();
        System.out.println(message);
    }
}
