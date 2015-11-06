package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * TODO: Add desription of Class
 * <p/>
 * A part of RainyWord project.
 *
 * @author Patcharapon Nuimark
 */
public class SocketConnectionHandler implements Runnable {

    private static int socketCount = 0;
    private Socket connectionSocket;
    private int socketID;

    public SocketConnectionHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
        this.socketID = socketCount++;
    }

    @Override
    public void run() {
        try {
            InputStream is = connectionSocket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String dataString = br.readLine();
            while (dataString != null) {
                System.out.println("Socket " + socketID + ": Data received: " + dataString);
                dataString = br.readLine();
            }
            connectionSocket.close();
            System.out.println("Socket " + socketID + " closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
