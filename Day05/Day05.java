package Day05;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Notes...
 * 
 * Sorting the mapping ranges and inputs and filtering out low map ranges only
 * results in a 2 ms performance improvement. I suspect that most of the time
 * lost is a result of using Scanner to read inputs instead of BufferedReader.
 * However, since Scanner is cleaner and easier to use, I will continue to use
 * Scanner.
 */
public class Day05 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "05";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        console.next(); // 'seeds:'

        // read in starting numbers
        ArrayList<Long> inputs = new ArrayList<>();
        while (console.hasNextLong()) {
            inputs.add(console.nextLong());
        }

        // 7 mapping functions, parse each one
        for (int k = 0; k < 7; k++) {
            console.nextLine(); // skip newlines
            console.nextLine();
            console.nextLine();

            // create map of mapping function
            ArrayList<long[]> maps = new ArrayList<>();
            while (console.hasNextLong()) {
                long newStart = console.nextLong(); // where the start of the section moves to
                long start = console.nextLong(); // section's start
                long length = console.nextLong(); // length of section
                maps.add(new long[] { newStart, start, length });
            }

            // the results of the current mapping function
            ArrayList<Long> outputs = new ArrayList<>();

            // calculate output of the previous input numbers
            for (long input : inputs) {
                boolean found = false; // if found a map

                // for each mapping range
                for (long[] map : maps) {
                    if (input >= map[1] && input <= map[1] + map[2]) {
                        outputs.add(map[0] + input - map[1]);
                        found = true;
                        break;
                    }
                }

                // not found, therefore the input becomes the output
                if (!found) {
                    outputs.add(input);
                }
            }

            // feed this output into the next mapping inputs
            inputs = outputs;
        }

        // find lowest value of all the previous outputs
        long lowest = Long.MAX_VALUE;
        for (long loc : inputs) {
            lowest = Math.min(lowest, loc);
        }

        System.out.println(lowest);
    }

    private static void part2(Scanner console) {
        console.next(); // 'seeds:'

        // read in starting ranges
        ArrayList<long[]> inputs = new ArrayList<>();
        while (console.hasNextLong()) {
            long start = console.nextLong();
            long length = console.nextLong();
            inputs.add(new long[] { start, length });
        }

        // for each mapping function
        for (int k = 0; k < 7; k++) {
            console.nextLine(); // skip newlines
            console.nextLine();
            console.nextLine();

            // create map of mapping function
            ArrayList<long[]> maps = new ArrayList<>();
            while (console.hasNextLong()) {
                long newStart = console.nextLong();
                long start = console.nextLong();
                long length = console.nextLong();
                maps.add(new long[] { newStart, start, length });
            }

            // the resulting ranges of the mapping function
            ArrayList<long[]> outputs = new ArrayList<>();

            // for each input range
            for (int i = 0; i < inputs.size(); i++) { // can't use for each loop since concurrent modification
                long[] input = inputs.get(i);

                // whether this range was partially/fully mapped to a new range
                boolean found = false;

                // start and end of input range
                long start = input[0];
                long end = start + input[1] - 1;

                // for each mapping range
                for (long[] map : maps) {
                    // start and end of mapping range
                    long mapStart = map[1];
                    long mapEnd = mapStart + map[2] - 1;

                    // check if the input range intersects with the mapping range
                    // ie, if the input range begins to the left of the mapping range's end, and
                    // ends after the mapping range's start, since input range end > input range
                    // end,
                    // therefore the input range must be intersecting the mapping range.
                    if (start <= mapEnd && end >= mapStart) {
                        // start and length of the output range
                        // get distance from the input range's start to the mapping range's start
                        // (0 if to the left of mapping range), and add the ew mappingg start.
                        long newStart = Math.max(mapStart, start) - mapStart + map[0];
                        long newLength = Math.min(mapEnd, end) - Math.max(mapStart, start) + 1;
                        outputs.add(new long[] { newStart, newLength });
                        found = true;

                        // add the remaining parts of the input range on the right side
                        if (mapEnd < end) {
                            inputs.add(new long[] { mapEnd + 1, end - mapEnd });
                        }

                        // add the remaining parts of the input range on the left side
                        if (mapStart > start) {
                            inputs.add(new long[] { start, mapStart - start });
                        }
                        break;
                    }
                }

                // not found, therefore this input range becomes an output range
                if (!found) {
                    outputs.add(input);
                }
            }

            inputs = outputs;
        }

        // find lowest value of all the previous outputs
        long lowest = Long.MAX_VALUE;
        for (long[] loc : inputs) {
            lowest = Math.min(lowest, loc[0]); // lowest value will always be the start of a range
        }

        System.out.println(lowest);
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