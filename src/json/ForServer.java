package json;

import server.ResponseToClient;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

public class ForServer {


    Map<String,String> responseFromJSON;
    private final ResponseToClient response;

    public ForServer(ResponseToClient responseToClient) {
        responseFromJSON = new LinkedHashMap<>();
        this.response = responseToClient;
    }

    public String stringToJSON() {
        this.responseFromJSON.clear();
        if(this.response.getValue().isEmpty() && this.response.getReason().isEmpty()) {
            if(this.response.getResponse().equals("exit") || this.response.getResponse().equals("OK")) {
                this.responseFromJSON.put("response", "OK");
            }
        } else if (this.response.getReason().isEmpty() && !this.response.getValue().isEmpty() &&
                this.response.getResponse().equals("OK")) {
            this.responseFromJSON.put("response", this.response.getResponse());
            this.responseFromJSON.put("value", this.response.getValue());
        } else if (!this.response.getReason().equals("No such key") && this.response.getValue().isEmpty()
                && this.response.getResponse().equals("OK")) {
            this.responseFromJSON.put("response", this.response.getResponse());
        }

        else {
            this.responseFromJSON.put("response","ERROR");
            this.responseFromJSON.put("reason", "No such key");
        }

        return this.responseFromJSON.toString();
    }

}
