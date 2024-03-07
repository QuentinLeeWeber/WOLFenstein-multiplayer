package commands;

public class CommandWithSender {
    public final Command command;
    public final int sender;

    public CommandWithSender(Command command, int sender) {
        this.command = command;
        this.sender = sender;
    }
}
