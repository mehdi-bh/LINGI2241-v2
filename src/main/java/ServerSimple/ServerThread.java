package ServerSimple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread implements Runnable{
    private final Socket clientSocket;
    public ServerThread(Socket clientSocket) { this.clientSocket = clientSocket; }

    // Méthode utilisée dans chaque thread pour gérer la requête d'un client
    @Override
    public void run() {
        try{
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            final String client = in.readLine();
            final String request = in.readLine();
            ArrayList<String> output;

            // On process la requête et on affiche la réponse chez le client
            Protocol protocol = new Protocol();
            output = protocol.processInput(request);

            long start = System.currentTimeMillis();
            for (String s : output) {
                out.println(s);
            }
            final long printTime = System.currentTimeMillis() - start;

            final String time = in.readLine();
            System.out.println("-> Client #" + client + " waited : " + time + "ms in total | Time to print : " + printTime);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + ServerLaunch.port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}