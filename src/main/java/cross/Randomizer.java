package cross;

import java.util.Arrays;
import java.util.Random;

public class Randomizer {
    private static Character[][] randomCrossword;

    public static Character[][] createRandomArray () {
        randomCrossword = new Character[6][5];
        Random random = new Random();
        for (int i = 0; i < randomCrossword.length; i++) {
            for (int j = 0; j < randomCrossword[i].length; j++) {
                randomCrossword[i][j] = (char) (random.nextInt(26)+97);
            }
        }
        for (int i = 0; i < randomCrossword.length; i++) {
            System.out.println(Arrays.toString(randomCrossword[i]));
        }
        return randomCrossword;
    }
}
