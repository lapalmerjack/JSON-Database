package client;

import FileManager.ClientFileManager;
import com.beust.jcommander.JCommander;
import json.ClientParseForServer;

import java.io.IOException;
import java.util.List;

public class Main {

    protected static String JSONDataFromFile;
    protected static String type;
    protected static List<String> index;
    protected  static List<String>message;

    public static void main(String[] args) throws IOException {
        Args arguments = new Args();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        JSONDataFromFile = arguments.files;
        type = arguments.type;
        index = arguments.index;
        message = arguments.message;

        System.out.println("Client Started!");

        String msg = "";

        if(type == null) {
            ClientFileManager fileManager = new ClientFileManager();
            String jsonFromFile = fileManager.importFileContent(JSONDataFromFile);
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
