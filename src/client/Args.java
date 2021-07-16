package client;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Args {

    @Parameter(names = "-t", description = "Type of request")
    String type;
    @Parameter(names = "-k", description = "Index")
    List<String> index = new ArrayList<>();
    @Parameter(names = "-v", description = "Message to store in the database")
    List<String> message = new ArrayList<>();
    @Parameter(names = "-in", description = "Retrieving request from file")
    String files;
}
