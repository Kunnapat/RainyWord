import javax.script.ScriptException;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int serverTime;
    private static int serverTime2;
    private static int clientTime;
    private static int clientTime2;
    private static int serverScore = 0;
    private static int clientScore = 0;
    private static String serverName;
    private static String clientName;
    private static String currentPlayer;
    private Game gameFrame = new Game("Rainy Word V0.0");

    public Server() throws NumberFormatException, ScriptException {
        serverName = JOptionPane.showInputDialog("Please enter you name");
        gameFrame = Game.createAndShowGUI(serverName);
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();

        System.out.println("Rainy Word is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(10096);
        try {
            while (true) {
                new Capitalizer(listener.accept(), clientNumber++).start();

            }
        } finally {
            listener.close();
        }

    }

    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }


        private void log(String message) {
            System.out.println(message);
        }

        public void run() {

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                System.out.println(in.readLine());

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    }
}
