package server.parseforclient;

import server.JsonForClient;

import java.util.LinkedHashMap;
import java.util.Map;

public class Get implements  StringParsingStrategy {


    @Override
    public String prepareStringForClient(JsonForClient response) {
        Map<String,String> parser = new LinkedHashMap<>();

        if(response.getReason().isEmpty()) {
            parser.put("Response", response.getResponse());
            parser.put("Value", response.getValue());
        } else {
            parser.put("Response", response.getResponse());
            parser.put("Reason", response.getReason());
        }


        return parser.toString();
    }
}
