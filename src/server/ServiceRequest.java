package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.command.ClientRequestExecutor;
import server.command.commandbuttons.Delete;
import server.command.commandbuttons.Get;
import server.command.commandbuttons.Set;
import server.parseforclient.Context;
import server.parseforclient.StringParsingStrategy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServiceRequest implements Runnable {

    private final Socket socket;
    private FromJSONParser fromJSONParser;
    private final JsonForClient response = new JsonForClient();

    public ServiceRequest(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            String msg = inputStream.readUTF();
            fromJSONParser = gson.fromJson(msg, FromJSONParser.class);

            if(!isExit()) {
                makeClientResponseObject();
            }

            writeResponseToClient(outputStream);


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

    private void makeClientResponseObject() {
        ClientRequestExecutor clientRequest = new ClientRequestExecutor();
        server.command.Database database = new server.command.Database(response);

        switch (fromJSONParser.getType()) {
            case "set":
                clientRequest.setCommand(new Set(database, fromJSONParser));
                break;
            case "get":
                clientRequest.setCommand(new Get(database, fromJSONParser));
                break;
            case "delete":
                clientRequest.setCommand(new Delete(database, fromJSONParser));
                break;
        }

        clientRequest.executeClientRequest();

    }
    
    private void writeResponseToClient(DataOutputStream outputStream) throws IOException {
        Context context = new Context(chooseStrategy());
        String messageForServer = context.createStringForClient(response);
        System.out.println("sent to server: " + messageForServer);
       outputStream.writeUTF(messageForServer);

    }

    private StringParsingStrategy chooseStrategy() {
        if(fromJSONParser.getType().equals("set")) {
            return new server.parseforclient.Set();
        } else if (fromJSONParser.getType().equals("get")) {
            return new server.parseforclient.Get();
        } else {
            return new server.parseforclient.Delete();
        }

    }

    private boolean isExit() {
        if(fromJSONParser.getType().equals("exit")) {
            response.setResponse("OK");
            Main.server.stopServer();
            return true;
        } else {
            return false;
        }
    }

}
