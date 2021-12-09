package client.strategy;

import client.ArgumentsFromCommandLine;

public class Context {

    private final StringParsingStrategy strategy;

    public Context(StringParsingStrategy strategy) {
        this.strategy = strategy;
    }

    public String executeStrategy(ArgumentsFromCommandLine arguments) {
      return  this.strategy.prepareJSONForServer(arguments);
    }



}
