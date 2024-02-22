package commands;

public class Move extends Command {
    public final int speed;

    Move() {
        this(0);
    }

    public Move(int speed) {
        this.speed = speed;
    }

    @Override
    public Command parseCmd(String raw) {
        return new Move(Integer.parseInt(raw));
    }

    @Override
    public String id() {
        return "MOVE";
    }

    @Override
    public String cmdString() {
        return Integer.toString(speed);
    }
}
