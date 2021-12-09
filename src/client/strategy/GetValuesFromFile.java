package client.strategy;

import client.ArgumentsFromCommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetValuesFromFile implements StringParsingStrategy {

    private static final String  directory = "/Users/Programming/JSON_Databases/src/client/data/";


    @Override
    public String prepareJSONForServer(ArgumentsFromCommandLine arguments) {

        String JSONString = "";

        try {
            JSONString = new String(Files.readAllBytes
                    (Paths.get(directory + arguments.getJSONDataFromFile())));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return JSONString;
    }
}
