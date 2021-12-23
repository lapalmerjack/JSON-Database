package server.command.commandbuttons;

import json.FromJSONParser;
import server.command.Command;
import server.command.Database;

public class Get implements Command {

    private final Database database;
    private final FromJSONParser getValue;


    public Get(Database database, FromJSONParser getValue) {
        this.database = database;
        this.getValue = getValue;
    }

    @Override
    public void execute() {
        database.get(getValue.getKey());

    }
}
