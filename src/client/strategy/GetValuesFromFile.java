package client.strategy;

import client.ArgumentsFromCommandLine;

public class GetValuesFromFile implements StringParsingStrategy {


    @Override
    public String prepareJSONForServer(ArgumentsFromCommandLine arguments) {

        return arguments.getJSONDataFromFile();
    }
}
