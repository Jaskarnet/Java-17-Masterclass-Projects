import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Main {
    static  int counter = 0;
    public static void main(String[] args) {
        int seed = 1;
        var streamB = Stream.iterate(seed, i -> i <= 15, i -> i + 1)
                .map(i -> "B" + i);

        seed += 15;
        var streamI = Stream.iterate(seed, i -> i + 1)
                        .limit(15)
                                .map(i -> "I" + i);

        seed += 15;
        String[] oLabels = new String[15];

        int finalSeedN = seed;
        Arrays.setAll(oLabels, i -> "N" + (finalSeedN + i));
        var streamN = Arrays.stream(oLabels);

        seed += 15;
        var streamG = Stream.of("G46", "G47", "G48", "G49", "G50",
                "G51", "G52", "G53", "G54", "G55", "G56", "G57", "G58", "G59", "G60");

        // produces side effects
        seed += 15;
        int finalSeedO = seed;
        var streamO = Stream.generate(() -> "O" + (getCounter() + finalSeedO))
                .limit(15);

        // produces side effects
        var streamO2 = Stream.generate(Main::getCounter)
                .limit(15)
                .map(i -> "O" + (i + finalSeedO));

        var streamBI = Stream.concat(streamB, streamI);
        var streamNG = Stream.concat(streamN, streamG);
        var streamBING = Stream.concat(streamBI, streamNG);
        var streamBINGO = Stream.concat(streamBING, streamO);
        streamBINGO.forEach(System.out::println);

        System.out.println("-------------------------------------");
        Stream.generate(() -> new Random().nextInt(finalSeedO, finalSeedO + 15))
                .distinct()
                .limit(15)
                .map(i -> "O" + i)
                .sorted()
                .forEach(System.out::println);
    }

    private static int getCounter() {
        return counter++;
    }
}
