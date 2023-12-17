import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day18 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "18";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        int targetTime = 2503;
        int maxDistance = 0;

        while (console.hasNextLine()) {
            String[] parts = console.nextLine().split(" ");

            int speed = Integer.parseInt(parts[3]);
            int time = Integer.parseInt(parts[6]);
            int wait = Integer.parseInt(parts[parts.length - 2]);

            int fullCycles = targetTime / (time + wait);
            int remainingTime = targetTime - fullCycles * (time + wait);

            int distance = fullCycles * time * speed + Math.min(remainingTime, time) * speed;
            maxDistance = Math.max(maxDistance, distance);
        }

        System.out.println(maxDistance);
    }

    private static void part2(Scanner console) {
        int targetTime = 2503;

        ArrayList<int[]> reindeers = new ArrayList<>();
        while (console.hasNextLine()) {
            String[] parts = console.nextLine().split(" ");

            int speed = Integer.parseInt(parts[3]);
            int time = Integer.parseInt(parts[6]);
            int wait = Integer.parseInt(parts[parts.length - 2]);

            reindeers.add(new int[] { speed, time, wait });
        }

        int[] pts = new int[reindeers.size()];
        for (int t = 1; t <= targetTime; t++) {
            int maxDistance = 0;
            for (int[] reindeer : reindeers) {
                int speed = reindeer[0];
                int time = reindeer[1];
                int wait = reindeer[2];

                int fullCycles = t / (time + wait);
                int remainingTime = t - fullCycles * (time + wait);

                int distance = fullCycles * time * speed + Math.min(remainingTime, time) * speed;
                maxDistance = Math.max(maxDistance, distance);
            }

            for (int i = 0; i < reindeers.size(); i++) {
                int[] reindeer = reindeers.get(i);
                int speed = reindeer[0];
                int time = reindeer[1];
                int wait = reindeer[2];

                int fullCycles = t / (time + wait);
                int remainingTime = t - fullCycles * (time + wait);

                int distance = fullCycles * time * speed + Math.min(remainingTime, time) * speed;
                if (distance == maxDistance) {
                    pts[i]++;
                }
            }
        }

        int maxPoints = 0;
        for (int i = 0; i < pts.length; i++) {
            maxPoints = Math.max(maxPoints, pts[i]);
        }
        System.out.println(maxPoints);
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String file = "";
        if (RUN_TEST_INPUT) {
            file = "Day" + DAY + "\\" + TEST_FILES_DIRECTORY + "\\day" + DAY;
        } else {
            file = "Day" + DAY + "\\" + INPUT_FILES_DIRECTORY + "\\day" + DAY;
        }

        if (RUN_PART_1) {
            file += "-p1.txt";
        } else {
            file += "-p2.txt";
        }

        Scanner console = new Scanner(new BufferedReader(new FileReader(file)));

        if (RUN_PART_1) {
            part1(console);
        } else {
            part2(console);
        }
        console.close();

        long endTime = System.currentTimeMillis();
        System.out.println("Program took " + (endTime - startTime) + " ms to complete");
    }
}