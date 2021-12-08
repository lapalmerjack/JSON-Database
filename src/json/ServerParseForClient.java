package json;

import server.ResponseToClient;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

public class ServerParseForClient {


    Map<String,String> responseFromJSON = new LinkedHashMap<>();


    public ServerParseForClient() {

    }

    public String ParseToJson(ResponseToClient response) {
        responseFromJSON.clear();
        if(response.getValue().isEmpty() && response.getReason().isEmpty()) {
            if(response.getResponse().equals("exit") || response.getResponse().equals("OK")) {
                responseFromJSON.put("response", "OK");
            }
        } else if (response.getReason().isEmpty() && !response.getValue().isEmpty() &&
                response.getResponse().equals("OK")) {
            responseFromJSON.put("response", response.getResponse());
            responseFromJSON.put("value", response.getValue());
        } else if (!response.getReason().equals("No such key") && response.getValue().isEmpty()
                && response.getResponse().equals("OK")) {
            responseFromJSON.put("response", response.getResponse());
        }

        else {
            responseFromJSON.put("response","ERROR");
            responseFromJSON.put("reason", "No such key");
        }

        return responseFromJSON.toString();
    }

}
