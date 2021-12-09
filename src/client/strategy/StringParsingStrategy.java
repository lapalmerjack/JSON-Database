package client.strategy;

import client.ArgumentsFromCommandLine;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public interface StringParsingStrategy {
    public String prepareJSONForServer(ArgumentsFromCommandLine arguments);
}

