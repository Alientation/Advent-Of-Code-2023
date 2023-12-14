
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day14 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "14";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        ArrayList<String> lines = new ArrayList<String>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }

        char[][] map = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'O') {
                    // move it up to the next possible space
                    int spot = i;
                    while (spot - 1 >= 0 && map[spot - 1][j] == '.') {
                        spot--;
                    }
                    map[i][j] = '.';
                    map[spot][j] = 'O';
                }
            }
        }

        for (int i = 0; i < map.length; i++) {
            System.out.println(Arrays.toString(map[i]));
        }

        int totalLoad = 0;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == 'O') {
                    totalLoad += map.length - row;
                }
            }
        }
        System.out.println(totalLoad);
    }

    private static String stringify(char[][] map) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                result.append(map[i][j]);
            }
        }
        return result.toString();
    }

    private static void part2(Scanner console) {
        ArrayList<String> lines = new ArrayList<String>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }

        char[][] map = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }

        System.out.println("\nBEGIN");
        for (int row = 0; row < map.length; row++) {
            System.out.println(Arrays.toString(map[row]));
        }

        HashMap<String, Integer> hash = new HashMap<>();

        for (int i = 0; i < 1_000_000_000; i++) {
            String string = stringify(map);
            if (hash.containsKey(string)) {
                int prevTime = hash.get(string);
                int cycleLength = i - prevTime;
                int remainingCycles = (1_000_000_000 - i) % cycleLength;
                i = 1_000_000_000 - remainingCycles;

            } else {
                hash.put(string, i);
            }

            // System.out.println("\nCYCLE " + i);
            // for (int row = 0; row < map.length; row++) {
            // System.out.println(Arrays.toString(map[row]));
            // }

            // simulate north, west, south and then east rolls
            for (int row = 0; row < map.length; row++) {
                for (int col = 0; col < map[row].length; col++) {
                    if (map[row][col] == 'O') {
                        // move it up to the next possible space
                        int spot = row;
                        while (spot - 1 >= 0 && map[spot - 1][col] == '.') {
                            spot--;
                        }
                        map[row][col] = '.';
                        map[spot][col] = 'O';
                    }
                }
            }

            // System.out.println("\nNORTH " + i);
            // for (int row = 0; row < map.length; row++) {
            // System.out.println(Arrays.toString(map[row]));
            // }

            // west
            for (int col = 0; col < map[0].length; col++) {
                for (int row = 0; row < map.length; row++) {
                    if (map[row][col] == 'O') {
                        // move it up to the next possible space
                        int spot = col;
                        while (spot - 1 >= 0 && map[row][spot - 1] == '.') {
                            spot--;
                        }
                        map[row][col] = '.';
                        map[row][spot] = 'O';
                    }
                }
            }

            // System.out.println("\nWEST " + i);
            // for (int row = 0; row < map.length; row++) {
            // System.out.println(Arrays.toString(map[row]));
            // }

            // south
            for (int row = map.length - 1; row >= 0; row--) {
                for (int col = 0; col < map[row].length; col++) {
                    if (map[row][col] == 'O') {
                        // move it up to the next possible space
                        int spot = row;
                        while (spot + 1 < map.length && map[spot + 1][col] == '.') {
                            spot++;
                        }
                        map[row][col] = '.';
                        map[spot][col] = 'O';
                    }
                }
            }

            // System.out.println("\nSOUTH " + i);
            // for (int row = 0; row < map.length; row++) {
            // System.out.println(Arrays.toString(map[row]));
            // }

            // east
            for (int col = map[0].length - 1; col >= 0; col--) {
                for (int row = 0; row < map.length; row++) {
                    if (map[row][col] == 'O') {
                        // move it up to the next possible space
                        int spot = col;
                        while (spot + 1 < map[0].length && map[row][spot + 1] == '.') {
                            spot++;
                        }
                        map[row][col] = '.';
                        map[row][spot] = 'O';
                    }
                }
            }

            // System.out.println("\nEAST " + i);
            // for (int row = 0; row < map.length; row++) {
            // System.out.println(Arrays.toString(map[row]));
            // }
        }

        int totalLoad = 0;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == 'O') {
                    totalLoad += map.length - row;
                }
            }
        }
        System.out.println(totalLoad);

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