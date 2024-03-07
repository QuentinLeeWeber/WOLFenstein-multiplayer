import java.util.ArrayList;
import java.util.HashMap;

public class UserList {
    private final HashMap<Integer, Position> users = new HashMap<>();

    public static class Position {
        public final int x;
        public final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void moveTo(Integer id, Integer x, Integer y) {
        users.put(id, new Position(x, y));
    }

    public void unregister(Integer id) {
        users.remove(id);
    }

    @Override
    public String toString() {
        ArrayList<String> userStrings = new ArrayList<>();
        for (Integer id : users.keySet()) {
            userStrings.add(id + " " + users.get(id).x + " " + users.get(id).y);
        }
        return String.join("/", userStrings);
    }
}
