package commands;

public class Move extends Command {
    public final int x;
    public final int y;

    Move() {
        this(0, 0);
    }

    public Move(int x, int  y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Command parseCmd(String raw) {
        String[] s = raw.split(" ");
        return new Move(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
    }

    @Override
    public String id() {
        return "MOVE";
    }

    @Override
    public String cmdString() {
        return x + " " + y;
    }
}
