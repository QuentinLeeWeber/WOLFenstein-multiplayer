package commands;

public class Parser {
    private static final Command[] commands = new Command[]{new Users(), new Move(), new Turn(), new Register(), new Unregister(), new Hit(), new Kill(), new Name()};
    public static CommandWithSender parse(String raw) {
        String[] s = raw.split(" ", 3);
        for (Command c : commands) {
            if (s[1].equals(c.id())) {
                String rawCmd = "";
                if (s.length > 2) {
                    rawCmd = s[2];
                }
                Command cmd = c.parseCmd(rawCmd);
                return new CommandWithSender(cmd, Integer.parseInt(s[0]));
            }
        }
        throw new IllegalArgumentException(String.format("bad command type %s", s[1]));
    }
}
