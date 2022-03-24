package client;

import client.strategy.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 35255;
    private final ArgumentsFromCommandLine arguments =
            new ArgumentsFromCommandLine(Main.JSONDataFromFile, Main.type,
                    Main.index, Main.message);


    public void start() {

        try {
            System.out.println("Client Started!");

            Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            writeRequestToServer(dataOutputStream);
            printMessageFromServer(dataInputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeRequestToServer(DataOutputStream dataOutputStream) throws IOException {
        Context context = new Context(chooseStrategy());
        String messageForServer = context.createJSONStringForServer(arguments);
        System.out.println("sent to server: " + messageForServer);
        dataOutputStream.writeUTF(messageForServer);


    }

    private void printMessageFromServer(DataInputStream dataInputStream) throws IOException {
        String receivedMsg = dataInputStream.readUTF();
        JsonElement jsonElement = new JsonParser().parse(receivedMsg);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String newMessage = gson.toJson(jsonElement);
        System.out.println("Received: " + newMessage.replace("\\", ""));

    }

    private StringParsingStrategy chooseStrategy() {
        if (arguments.getJSONDataFromFile() != null) {
            return new GetValuesFromFile();
        } else if (arguments.getType().equals("exit")) {
            return new Exit();
        }
        else if (arguments.getType().equals("set")) {
            return new SetValue();
        } else {
            return new GetOrDeleteValue();
        }
    }


}
