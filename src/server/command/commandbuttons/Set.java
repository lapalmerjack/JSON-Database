package server.command.commandbuttons;

import server.FromJSONParser;
import server.command.Command;
import server.command.Database;

public class Set implements Command {


   private Database database;
   private final FromJSONParser setValue;


    public Set(Database database, FromJSONParser setValue) {
        this.database = database;
        this.setValue = setValue;
    }

    @Override
    public void execute() {
        database.set(setValue.getKey(), setValue.getValue());
    }
}
