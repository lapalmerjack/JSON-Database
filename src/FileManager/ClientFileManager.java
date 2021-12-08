package FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ClientFileManager {

    private static final String  directory = "/Users/Programming/JSON_Databases/src/client/data/";


    public String importFileContent (String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(directory + fileName)));

    }
}
