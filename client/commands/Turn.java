package commands;

// Command: TURN <angle>
public class Turn extends Command {
    public final float angle;

    Turn() {
        this(0);
    }

    public Turn(float angle) {
        this.angle = angle;
    }

    @Override
    public Command parseCmd(String raw) {
        return new Turn(Float.parseFloat(raw));
    }

    @Override
    public String id() {
        return "TURN";
    }

    @Override
    public String cmdString() {
        return Float.toString(angle);
    }
}
