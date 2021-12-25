package server.parseforclient;

import server.JsonForClient;

public interface StringParsingStrategy {

    public String prepareStringForClient(JsonForClient response);
}
