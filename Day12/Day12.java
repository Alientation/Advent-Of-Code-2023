package Day12;

import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day12 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "12";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        int sum = 0;
        while (console.hasNextLine()) {
            String[] line = console.nextLine().split(" ");
            char[] chars = line[0].toCharArray();
            String[] stringRecords = line[1].split(",");
            int[] records = new int[stringRecords.length];
            for (int i = 0; i < stringRecords.length; i++) {
                records[i] = Integer.parseInt(stringRecords[i]);
            }

            sum += numWays(chars, records, 0);
        }
        System.out.println(sum);
    }

    private static int numWays(char[] chars, int[] records, int curC) {
        if (curC == chars.length) {
            int r = 0;
            int currentRun = 0;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '#') {
                    currentRun++;

                    if (r == records.length) {
                        return 0;
                    } else if (currentRun > records[r]) {
                        return 0;
                    }
                } else if (currentRun != 0) {
                    if (currentRun != records[r]) {
                        return 0;
                    }

                    r++;
                    currentRun = 0;
                }
            }
            if (currentRun > 0) {
                if (r == records.length) {
                    return 0;
                } else if (records[r] != currentRun || r != records.length - 1) {
                    return 0;
                }
                r++;
            }

            if (r != records.length) {
                return 0;
            }
            return 1;
        } else if (chars[curC] != '?') {
            return numWays(chars, records, curC + 1);
        }

        int sum = 0;
        chars[curC] = '.';
        sum += numWays(chars, records, curC + 1);
        chars[curC] = '#';
        sum += numWays(chars, records, curC + 1);
        chars[curC] = '?';

        return sum;
    }

    private static void part2(Scanner console) {
        long sum = 0;
        while (console.hasNextLine()) {
            String[] line = console.nextLine().split(" ");

            // five times
            String l1 = line[0];
            String l2 = line[1];
            for (int i = 0; i < 4; i++) {
                line[0] += "?" + l1;
                line[1] += "," + l2;
            }

            System.out.println(Arrays.toString(line));

            char[] chars = line[0].toCharArray();
            String[] stringRecords = line[1].split(",");
            int[] records = new int[stringRecords.length];
            for (int i = 0; i < stringRecords.length; i++) {
                records[i] = Integer.parseInt(stringRecords[i]);
            }

            long[][] dp = new long[records.length + 1][chars.length + 2];
            dp[0][0] = 1;
            for (int i = 0; i < records.length; i++) {
                for (int j = 0; j < chars.length; j++) {
                    if (dp[i][j] == 0) {
                        continue;
                    }

                    // skip '.'
                    if (chars[j] == '.') {
                        dp[i][j + 1] += dp[i][j];
                        continue;
                    }

                    // try to fit the current record here
                    if (j + records[i] - 1 < chars.length) {
                        boolean isPossible = true;
                        for (int pos = j; pos < j + records[i]; pos++) {
                            if (chars[pos] == '.') {
                                isPossible = false;
                                break;
                            }
                        }

                        // check if there is a . or ? or no characters after this length
                        if (isPossible) {
                            if (j + records[i] == chars.length || chars[j + records[i]] != '#') {
                                // its possible
                                dp[i + 1][j + records[i] + 1] += dp[i][j];
                            }
                        }
                    }

                    // skip this position if it is a ?
                    if (chars[j] == '?') {
                        dp[i][j + 1] += dp[i][j];
                    }
                }
            }

            long possibilities = 0;
            for (int i = dp[0].length - 1; i >= 0; i--) {
                if (i < chars.length && chars[i] == '#') {
                    break;
                }
                possibilities += dp[records.length][i];
            }
            sum += possibilities;
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