package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {

    private static final int PORT = 35255;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 3);
    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            System.out.println("Starting Server");
            serverSocket = new ServerSocket(PORT);


            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                System.out.println("Waiting for requests");

                Socket s = serverSocket.accept();
                System.out.println("Processing Request");
                executor.submit(new ServiceRequest(s));

            }
        }  catch (SocketException e) {
               if(serverSocket.isClosed())
                   System.out.println("Connection Closed.");

     }

        catch (IOException ioe) {
            System.out.println("Error accepting connection");
            ioe.printStackTrace();

        }

    }


    protected void stopServer () {
        try {
            serverSocket.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

        executor.shutdownNow();

    }

}


