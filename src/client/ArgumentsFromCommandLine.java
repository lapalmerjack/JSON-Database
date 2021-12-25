package client;

import java.util.List;

public class ArgumentsFromCommandLine {

   private final String JSONDataFromFile = Main.JSONDataFromFile;
   private final String  type = Main.type;
   private final String index = parseArrayToString(Main.index);
   private final String message = parseArrayToString(Main.message);

    public ArgumentsFromCommandLine() {

    }



    private String parseArrayToString(List<String> array) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String newString: array) {
            stringBuilder.append(" ").append(newString);
        }

        return stringBuilder.toString();
    }

    public String getJSONDataFromFile() {
        return JSONDataFromFile;
    }

    public String getType() {
        return type;
    }

    public String getIndex() {
        return index;
    }

    public String getMessage() {
        return message;
    }
}
