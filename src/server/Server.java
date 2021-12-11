package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.ServerParseForClient;
import json.FromJSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        } catch (IOException ioe) {
            System.out.println("Error accepting connection");
            ioe.printStackTrace();

        }

    }


    private void stopServer () {
        try {
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        executor.shutdownNow();

    }

    static class ServiceRequest implements Runnable {

        private final Socket socket;
        private final Database database = new Database();

        public ServiceRequest(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {


            ServerParseForClient serverSideParse = new ServerParseForClient();



            try {
                String result;
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.setPrettyPrinting().create();
                String msg = inputStream.readUTF();
                FromJSONParser FromJSONParser = gson.fromJson(msg, FromJSONParser.class);



                switch (FromJSONParser.getType()) {

                    case "set":
                        database.set(FromJSONParser.getKey(), FromJSONParser.getValue());
                        break;
                    case "get":
                        database.get(FromJSONParser.getKey());
                        break;
                    case "delete":

                        database.delete(FromJSONParser.getKey());
                        break;
                    case "exit":
                        database.exitProgram();
                        Main.server.stopServer();

                }

                result = serverSideParse.ParseToJson(database.getResponseForClient());

                outputStream.writeUTF(result);

            } catch (IOException e) {
                e.printStackTrace();
            }

           finally {
                System.out.println("Closing socket");
                try{
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}


