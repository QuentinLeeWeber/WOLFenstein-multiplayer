import java.util.concurrent.ThreadLocalRandom;

public class Config {
    public static final int width = 800;
    public static final int height = 600;

    public static int randIntFromZero(int to) {
        return ThreadLocalRandom.current().nextInt(0, to + 1);
    }
}
