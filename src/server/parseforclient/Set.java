package server.parseforclient;

import server.JsonForClient;

import java.util.LinkedHashMap;
import java.util.Map;

public class Set implements StringParsingStrategy {


    @Override
    public String prepareStringForClient(JsonForClient response) {
        Map<String,String> parser = new LinkedHashMap<>();


        parser.put("Response", response.getResponse());


        return parser.toString();


    }
}
