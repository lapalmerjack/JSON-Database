package client;


import com.beust.jcommander.JCommander;
import java.util.List;

public class Main {

    protected static String JSONDataFromFile;
    protected static String type;
    protected static List<String> index;
    protected  static List<String>message;

    public static void main(String[] args) {
        Args arguments = new Args();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        JSONDataFromFile = arguments.files;
        type = arguments.type;
        index = arguments.index;
        message = arguments.message;

        new Client().start();







    }
}
