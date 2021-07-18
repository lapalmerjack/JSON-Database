package client;

import FileManager.ClientFileManager;
import com.beust.jcommander.JCommander;
import json.ClientParseForServer;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Client Started!");
        Args arguments = new Args();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        String inFile = arguments.files;
        String type = arguments.type;
        List<String> index = arguments.index;
        List<String>message = arguments.message;

        String msg = "";

        if(type == null) {
            ClientFileManager fileManager = new ClientFileManager();
            String jsonFromFile = fileManager.importFileContent(inFile);
            new Client().start(jsonFromFile);

        } else {

            StringBuilder key = new StringBuilder();
            StringBuilder value = new StringBuilder();
            switch (type) {
                case "set": {
                    value.append(message.get(0));
                    key.append(index.get(0));
                    for (int i = 1; i < message.size(); i++) {
                        value.append(" ").append(message.get(i));
                    }
                    for (int i = 1; i < index.size(); i++) {
                        key.append(" ").append(index.get(i));
                    }
                    String newIndex = key.toString();
                    String newMessage = value.toString();
                    msg += type + " " + String.valueOf(newIndex) + " " + newMessage;
                    break;
                }
                case "exit":
                    msg = type;
                    break;
                case "get":
                case "delete": {
                    key.append(index.get(0));
                    for (int i = 1; i < index.size(); i++) {
                        key.append(" ").append(index.get(i));
                    }
                    String newIndex = key.toString();
                    msg += type + " " + newIndex;

                    break;
                }
            }

            ClientParseForServer jsonRequest = new ClientParseForServer();
            String jsonMessage = jsonRequest.ParseToJson(msg);
            new  Client().start(jsonMessage);

        }



    }
}
