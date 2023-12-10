import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day09 {
    private static final String TEST_FILES_DIRECTORY = "files\\tests";
    private static final String INPUT_FILES_DIRECTORY = "files\\inputs";

    private static final String DAY = "09";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = true;

    private static void part1(Scanner console) {
        int sum = 0;
        while (console.hasNextLine()) {
            String line = console.nextLine();
            String[] numbers = line.split(" ");
            int[][] n = new int[numbers.length][numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                n[0][i] = Integer.parseInt(numbers[i]);
            }

            int li = 1;
            for (int i = 1; i < n.length; i++) {
                boolean isZero = true;
                for (int j = 0; j < n.length - i; j++) {
                    n[i][j] = n[i - 1][j + 1] - n[i - 1][j];
                    if (n[i][j] != 0) {
                        isZero = false;
                    }
                }

                if (isZero) {
                    li = i;
                    break;
                }
            }

            for (int i = 0; i <= li; i++) {
                for (int j = 0; j < n.length - i; j++) {
                    System.out.print(n[i][j] + " ");
                }
                System.out.println();
            }

            int val = 0;
            for (int i = 0; i < li; i++) {
                val += n[i][n.length - i - 1];
            }

            System.out.println(val + "\n");
            sum += val;
        }

        System.out.println(sum);
    }

    private static void part2(Scanner console) {
        int sum = 0;
        while (console.hasNextLine()) {
            String line = console.nextLine();
            String[] numbers = line.split(" ");
            int[][] n = new int[numbers.length][numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                n[0][i] = Integer.parseInt(numbers[i]);
            }

            int li = 1;
            for (int i = 1; i < n.length; i++) {
                boolean isZero = true;
                for (int j = 0; j < n.length - i; j++) {
                    n[i][j] = n[i - 1][j + 1] - n[i - 1][j];
                    if (n[i][j] != 0) {
                        isZero = false;
                    }
                }

                if (isZero) {
                    li = i;
                    break;
                }
            }

            for (int i = 0; i <= li; i++) {
                for (int j = 0; j < n.length - i; j++) {
                    System.out.print(n[i][j] + " ");
                }
                System.out.println();
            }

            int val = 0;
            for (int i = li - 1; i >= 0; i--) {
                val = n[i][0] - val;
            }

            System.out.println(val + "\n");
            sum += val;
        }

        System.out.println(sum);
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