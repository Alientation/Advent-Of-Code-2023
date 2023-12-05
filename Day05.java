import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day05 {
    private static final String TEST_FILES_DIRECTORY = "files\\tests";
    private static final String INPUT_FILES_DIRECTORY = "files\\inputs";

    private static final String DAY = "05";
    private static final boolean RUN_TEST_INPUT = true;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        console.next();
        ArrayList<Long> seeds = new ArrayList<>();
        while (console.hasNextLong()) {
            seeds.add(console.nextLong());
        }

        for (int k = 0; k < 7; k++) {
            console.nextLine();
            console.nextLine();
            console.nextLine();

            ArrayList<long[]> map = new ArrayList<>();
            while (console.hasNextLong()) {
                long low = console.nextLong();
                long high = console.nextLong();
                long val = console.nextLong();
                map.add(new long[] { low, high, val });
            }

            ArrayList<Long> newRes = new ArrayList<>();

            for (long prev : seeds) {
                boolean found = false;
                for (long[] m : map) {
                    if (prev >= m[1] && prev <= m[1] + m[2]) {
                        newRes.add(m[0] + prev - m[1]);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    newRes.add(prev);
                }
            }

            seeds = newRes;
        }

        long lowest = Long.MAX_VALUE;
        for (long loc : seeds) {
            lowest = Math.min(lowest, loc);
        }

        System.out.println(lowest);
    }

    private static void part2(Scanner console) {
        console.next();
        ArrayList<long[]> seeds = new ArrayList<>();
        while (console.hasNextLong()) {
            long start = console.nextLong();
            long length = console.nextLong();
            seeds.add(new long[] { start, length });
        }

        for (int k = 0; k < 7; k++) {
            console.nextLine();
            console.nextLine();
            console.nextLine();

            ArrayList<long[]> map = new ArrayList<>();
            while (console.hasNextLong()) {
                long low = console.nextLong();
                long high = console.nextLong();
                long val = console.nextLong();
                map.add(new long[] { low, high, val });
            }

            ArrayList<long[]> newRes = new ArrayList<>();

            for (int i = 0; i < seeds.size(); i++) {
                long[] prev = seeds.get(i);
                boolean found = false;

                long start = prev[0];
                long end = start + prev[1] - 1;
                for (long[] m : map) {
                    long mStart = m[1];
                    long mEnd = mStart + m[2] - 1;

                    // intersection
                    if (start <= mEnd && end >= mStart) {
                        long newStart = Math.max(mStart, start) - mStart + m[0];
                        long newLength = Math.min(mEnd, end) - Math.max(mStart, start) + 1;
                        newRes.add(new long[] { newStart, newLength });
                        found = true;

                        // add the remainder of this section
                        if (mEnd < end) {
                            seeds.add(new long[] { mEnd + 1, end - mEnd });
                        }

                        if (mStart > start) {
                            seeds.add(new long[] { start, mStart - start });
                        }
                        break;
                    }
                }

                if (!found) {
                    newRes.add(prev);
                }
            }

            for (long[] l : newRes) {
                System.out.print(Arrays.toString(l) + " ");
            }
            System.out.println();

            seeds = newRes;
        }

        long lowest = Long.MAX_VALUE;
        for (long[] loc : seeds) {
            lowest = Math.min(lowest, loc[0]);
        }

        System.out.println(lowest);
    }

    public static void main(String[] args) throws FileNotFoundException {
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

        Scanner console = new Scanner(new FileReader(file));

        if (RUN_PART_1) {
            part1(console);
        } else {
            part2(console);
        }
        console.close();
    }
}