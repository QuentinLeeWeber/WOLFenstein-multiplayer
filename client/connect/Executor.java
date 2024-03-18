package connect;

import commands.CommandWithSender;

public interface Executor {
    void execute(CommandWithSender c);
    void close();
}
