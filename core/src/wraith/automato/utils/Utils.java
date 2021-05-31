package wraith.automato.utils;

import wraith.automato.libraries.FastNoiseLite;

import java.util.Random;

public final class Utils {

    private static boolean initialized = false;
    private static int seed = 1337;
    public static Random random;

    public static void initialize(int seed) {
        if (!initialized) {
            Utils.seed = seed;
            initialized = true;
            random = new Random(Utils.seed);
        }
    }

    public static void initialize() {
        if (!initialized) {
            initialized = true;
            random = new Random();
            seed = random.nextInt();
            random = new Random(seed);
        }
    }

    public static int getSeed() {
        return seed;
    }

}
