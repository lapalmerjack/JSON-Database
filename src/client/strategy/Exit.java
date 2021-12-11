package client.strategy;

import client.ArgumentsFromCommandLine;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Exit implements StringParsingStrategy {


    @Override
    public String prepareJSONForServer(ArgumentsFromCommandLine arguments) {
        Map<String, String> exit = new HashMap<>();

        exit.put("type", arguments.getType());

        return new Gson().toJson(exit);

    }
}
