package Day06;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day06 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "06";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        console.next(); // 'Time:'
        ArrayList<Integer> times = new ArrayList<Integer>();
        while (console.hasNextInt()) {
            times.add(console.nextInt());
        }

        console.nextLine(); // '\n'
        console.next(); // 'Distance:'

        ArrayList<Integer> distances = new ArrayList<Integer>();
        while (console.hasNextInt()) {
            distances.add(console.nextInt());
        }

        // for each race, multiply the number of possible ways to win
        int sum = 1;
        for (int race = 0; race < times.size(); race++) {
            int numPossibleWins = 0; // count how many possible ways to win for this race
            // for each time held, check if it is possible to win
            for (int timeHeld = 1; timeHeld < times.get(race); timeHeld++) { // timeHeld is the velocity
                // check if time remaining * velocity is strictly greater than the distance
                // required to win
                if ((times.get(race) - timeHeld) * timeHeld > distances.get(race)) {
                    numPossibleWins++;
                }
            }
            System.out.println(numPossibleWins);
            sum *= numPossibleWins;
        }

        System.out.println(sum);
    }

    private static void part2(Scanner console) {
        console.next(); // 'Time:'
        long time = 0;
        while (console.hasNextInt()) { // read in the numbers as one whole number
            int partialTime = console.nextInt();
            int temp = partialTime;
            // shift the previous digits over enough to append this partial time
            while (temp > 0) {
                time *= 10;
                temp /= 10;
            }
            time += partialTime;
        }

        console.nextLine(); // '\n'
        console.next(); // 'Distance:'

        long distance = 0;
        while (console.hasNextInt()) { // read in the numbers as one whole number
            int partialDistance = console.nextInt();
            int temp = partialDistance;
            // shift the previous digits over enough to append this partial time
            while (temp > 0) {
                distance *= 10;
                temp /= 10;
            }
            distance += partialDistance;
        }

        System.out.println(time + " " + distance);

        // Quadratic formula to find the intercepts with the line y = distance
        double a = -1;
        double b = time;
        double c = -distance;

        double d = b * b - 4L * a * c;

        if (d < 0) {
            System.out.println("No solution");
        } else {
            long x1 = (long) ((-b + Math.sqrt(d)) / (2 * a));
            long x2 = (long) ((-b - Math.sqrt(d)) / (2 * a));
            System.out.println(x2 - x1);
        }
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