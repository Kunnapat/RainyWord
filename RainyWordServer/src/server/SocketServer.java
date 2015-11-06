package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by peth on 11/6/2015 AD.
 */
public class SocketServer implements Runnable {
    private int listeningPort;
    private ServerSocket serverSocket;

    public SocketServer(int listeningPort) {
        this.listeningPort = listeningPort;
        changeListeningPort(listeningPort);
    }

    public boolean changeListeningPort(int listeningPort) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            serverSocket = new ServerSocket(listeningPort);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to start listening on port " + listeningPort);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Thread socketConnectionThread = new Thread(new SocketConnectionHandler(socket));
                socketConnectionThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
