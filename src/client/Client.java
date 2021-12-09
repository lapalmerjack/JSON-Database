package client;

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
    private ArgumentsFromCommandLine arguments;



    public void start(String msg) {

        try {
            Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());


            dataOutputStream.writeUTF(msg);
            System.out.println("sent to server: " + msg);
            String receivedMsg = dataInputStream.readUTF();

            JsonElement jsonElement = new JsonParser().parse(receivedMsg);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String newMessage = gson.toJson(jsonElement);

            System.out.println("Received: " + newMessage.replace("\\", ""));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
