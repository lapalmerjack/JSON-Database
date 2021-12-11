package client.strategy;

import client.ArgumentsFromCommandLine;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SetValue implements StringParsingStrategy {

    @Override
    public String prepareJSONForServer(ArgumentsFromCommandLine arguments) {
        Map<String, String> JSONStringForServer = new HashMap<>();


        JSONStringForServer.put("type", arguments.getType());
        JSONStringForServer.put("key", arguments.getIndex());
        JSONStringForServer.put("value", arguments.getMessage());

        return new Gson().toJson(JSONStringForServer);
    }

}
