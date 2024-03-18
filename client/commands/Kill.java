package commands;

public class Kill extends Command{
    public final int id;

    Kill() {
        this(0);
    }

    public Kill(int id) {
        this.id = id;
    }

    @Override
    public Command parseCmd(String raw) {
        return new Kill(Integer.parseInt(raw.trim()));
    }

    @Override
    public String id() {
        return "KILL";
    }

    @Override
    public String cmdString() {
        return Integer.toString(id);
    }
}
