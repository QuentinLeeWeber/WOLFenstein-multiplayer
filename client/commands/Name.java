package commands;

public class Name extends Command {
    public final String name;

    Name() {
        this("");
    }

    public Name(String name) {
        this.name = name;
    }

    @Override
    public Command parseCmd(String raw) {
        //return new Name(Integer.parseInt(raw.trim()));
        return new Name(raw);
    }

    @Override
    public String id() {
        return "NAME";
    }

    @Override
    public String cmdString() {
        return name;
    }
}
