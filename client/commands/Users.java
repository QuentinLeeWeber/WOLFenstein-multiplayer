package commands;

import java.util.ArrayList;
import java.util.HashMap;

public class Users extends Command {
    public final HashMap<Integer, Integer[]> users = new HashMap<>();

    @Override
    public Command parseCmd(String raw) {
        String[] users = raw.split("/");
        Users uslist = new Users();
        for (String u :users) {
            String[] s = u.split(" ");
            uslist.users.put(Integer.parseInt(s[0]), new Integer[]{Integer.parseInt(s[1]), Integer.parseInt(s[2])});
        }
        return uslist;
    }

    @Override
    public String id() {
        return "USERS";
    }

    @Override
    public String cmdString() {
        ArrayList<String> userStrings = new ArrayList<>();
        for (Integer id : users.keySet()) {
            userStrings.add(id + " " + users.get(id)[0] + " " + users.get(id)[1]);
        }
        return String.join("/", userStrings);
    }
}
