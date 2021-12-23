package server.command;

public class ClientRequestExecutor {


    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeClientRequest () {
        command.execute();
    }

}
