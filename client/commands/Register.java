package commands;

// Command: REGISTER <x start position> <y start position>
// Sent by server
public class Register extends Command {
    public final int x;
    public final int y;

    Register() {
        this(0, 0);
    }

    public Register(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Command parseCmd(String raw) {
        String[] s = raw.split(" ");
        return new Register(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
    }

    @Override
    public String id() {
        return "REGISTER";
    }

    @Override
    public String cmdString() {
        return x + " " + y;
    }
}
