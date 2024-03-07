package connect;

import commands.Command;
import commands.CommandWithSender;

public interface Executor {
    void execute(CommandWithSender c);
    void close();
}
