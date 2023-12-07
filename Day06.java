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
    private static final String TEST_FILES_DIRECTORY = "files\\tests";
    private static final String INPUT_FILES_DIRECTORY = "files\\inputs";

    private static final String DAY = "06";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        console.next();
        ArrayList<Integer> times = new ArrayList<Integer>();
        while (console.hasNextInt()) {
            times.add(console.nextInt());
        }

        console.nextLine();
        console.next();

        ArrayList<Integer> distances = new ArrayList<Integer>();
        while (console.hasNextInt()) {
            distances.add(console.nextInt());
        }

        int sum = 1;
        for (int race = 0; race < times.size(); race++) {
            int count = 0;
            for (int timeHeld = 1; timeHeld < times.get(race); timeHeld++) {
                if ((times.get(race) - timeHeld) * timeHeld > distances.get(race)) {
                    count++;
                }
            }
            System.out.println(count);
            sum *= count;
        }

        System.out.println(sum);
    }

    private static void part2(Scanner console) {
        console.next();
        long time = 0;
        while (console.hasNextInt()) {
            int in = console.nextInt();
            int in2 = in;
            while (in > 0) {
                time *= 10;
                in /= 10;
            }
            time += in2;
        }

        console.nextLine();
        console.next();

        long distance = 0;
        while (console.hasNextInt()) {
            int in = console.nextInt();
            int in2 = in;
            while (in > 0) {
                distance *= 10;
                in /= 10;
            }
            distance += in2;
        }

        System.out.println(time + " " + distance);

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
            file = TEST_FILES_DIRECTORY + "\\day" + DAY;
        } else {
            file = INPUT_FILES_DIRECTORY + "\\day" + DAY;
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