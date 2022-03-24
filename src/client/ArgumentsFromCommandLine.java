package client;

import java.util.List;

public class ArgumentsFromCommandLine {

   private final String JSONDataFromFile;
   private final String  type;
   private final String index;
   private final String message;

    public ArgumentsFromCommandLine(String JSONDataFromFile, String type, List<String> index, List<String>message) {
        this.JSONDataFromFile = JSONDataFromFile;
        this.type = type;
        this.index =parseArrayToString(index);
        this.message = parseArrayToString(message);
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
