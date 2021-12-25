package server.parseforclient;

import server.JsonForClient;

public class Context {

    private final StringParsingStrategy parsingStrategy;


    public Context(StringParsingStrategy parsingStrategy) {
        this.parsingStrategy = parsingStrategy;
    }


    public String createStringForClient(JsonForClient response) {
        return parsingStrategy.prepareStringForClient(response);
    }


}
