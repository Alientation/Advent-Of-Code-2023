package Day09;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day09 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "09";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        int sum = 0;
        while (console.hasNextLine()) {
            String line = console.nextLine();
            String[] numbers = line.split(" ");
            ArrayList<int[]> sequence_lines = new ArrayList<>();
            int[] sequence = new int[numbers.length]; // initial sequence of numbers
            for (int i = 0; i < numbers.length; i++) {
                sequence[i] = Integer.parseInt(numbers[i]);
            }
            sequence_lines.add(sequence);

            // until the sequence becomes all zero, continue to add new sequences
            while (true) {
                boolean isZero = true;
                // each new sequence is one smaller than the previous
                sequence = new int[sequence_lines.get(sequence_lines.size() - 1).length - 1];
                for (int j = 0; j < sequence.length; j++) {
                    // difference between the two numbers above the current index
                    sequence[j] = sequence_lines.get(sequence_lines.size() - 1)[j + 1]
                            - sequence_lines.get(sequence_lines.size() - 1)[j];
                    if (sequence[j] != 0) {
                        isZero = false;
                    }
                }

                if (isZero) {
                    break;
                }

                sequence_lines.add(sequence); // add new nonzero sequence
            }

            // add the right most values in all the sequences to get the next value in the
            // original sequence
            int val = 0;
            for (int i = 0; i < sequence_lines.size(); i++) {
                val += sequence_lines.get(i)[sequence_lines.get(i).length - 1];
            }

            sum += val;
        }

        System.out.println(sum);
    }

    private static void part2(Scanner console) {
        int sum = 0;
        while (console.hasNextLine()) {
            String line = console.nextLine();
            String[] numbers = line.split(" ");
            ArrayList<int[]> sequence_lines = new ArrayList<>();
            int[] sequence = new int[numbers.length]; // initial sequence of numbers
            for (int i = 0; i < numbers.length; i++) {
                sequence[i] = Integer.parseInt(numbers[i]);
            }
            sequence_lines.add(sequence);

            // until the sequence becomes all zero, continue to add new sequences
            while (true) {
                boolean isZero = true;
                // each new sequence is one smaller than the previous
                sequence = new int[sequence_lines.get(sequence_lines.size() - 1).length - 1];
                for (int j = 0; j < sequence.length; j++) {
                    // difference between the two numbers above the current index
                    sequence[j] = sequence_lines.get(sequence_lines.size() - 1)[j + 1]
                            - sequence_lines.get(sequence_lines.size() - 1)[j];
                    if (sequence[j] != 0) {
                        isZero = false;
                    }
                }

                if (isZero) {
                    break;
                }

                sequence_lines.add(sequence); // add new nonzero sequence
            }

            // from the bottom sequence to the top original sequence,
            // take the left most number (starting number of the sequence) and subtract
            // with the previous value to store as the new val.
            // essentially, this is undoing the addition of the first number to get the
            // previous number in each sequence
            int val = 0;
            for (int i = sequence_lines.size() - 1; i >= 0; i--) {
                val = sequence_lines.get(i)[0] - val;
            }
            sum += val;
        }

        System.out.println(sum);
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