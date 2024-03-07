package commands;

public class Hit extends Command {
    public final int id;

    Hit() {
        this(0);
    }

    public Hit(int id) {
        this.id = id;
    }

    @Override
    public Command parseCmd(String raw) {
        return new Hit(Integer.parseInt(raw.trim()));
    }

    @Override
    public String id() {
        return "HIT";
    }

    @Override
    public String cmdString() {
        return Integer.toString(id);
    }
}
