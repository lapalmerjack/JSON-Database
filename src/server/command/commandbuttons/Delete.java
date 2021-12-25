package server.command.commandbuttons;

import server.FromJSONParser;
import server.command.Command;
import server.command.Database;

public class Delete implements Command {



    private Database database;
    private final FromJSONParser deleteValue;


    public Delete(Database database, FromJSONParser fromJSONParser) {
        this.database = database;
        this.deleteValue = fromJSONParser;
    }

    @Override
    public void execute() {
        database.delete(deleteValue.getKey());


    }
}
