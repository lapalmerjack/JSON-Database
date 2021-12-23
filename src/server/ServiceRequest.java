package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.FromJSONParser;
import json.ServerParseForClient;
import server.command.ClientRequestExecutor;
import server.command.Database;
import server.command.commandbuttons.Delete;
import server.command.commandbuttons.Get;
import server.command.commandbuttons.Set;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServiceRequest implements Runnable {

    private final Socket socket;
    private FromJSONParser fromJSONParser;
    private final ResponseToClient response = new ResponseToClient();

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
            fromJSONParser = gson.fromJson(msg, FromJSONParser.class);

            if(!isExit()) {
                makeClientResponseObject();
            }


            result = serverSideParse.ParseToJson(response);
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

    public boolean isExit() {
        if(fromJSONParser.getType().equals("exit")) {
            response.setResponse("OK");
            Main.server.stopServer();
            return true;
        } else {
            return false;
        }
    }

}
