package commands;

public abstract class Command {
    @Override
    public String toString() {
        return id() + " " + cmdString();
    };
    public abstract Command parseCmd(String raw);
    public abstract String id();
    public abstract String cmdString();
}
