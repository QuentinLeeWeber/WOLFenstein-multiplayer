package commands;

public class Unregister extends Command {
    @Override
    public Command parseCmd(String raw) {
        return new Unregister();
    }

    @Override
    public String id() {
        return "UNREGISTER";
    }

    @Override
    public String cmdString() {
        return "";
    }
}
