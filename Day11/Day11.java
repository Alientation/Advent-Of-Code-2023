package Day11;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day11 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "11";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        ArrayList<String> lines = new ArrayList<String>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }

        int countExtraRows = 0;

        char[][] universe = new char[lines.size()][lines.get(0).length()];
        boolean[] rows = new boolean[universe.length];
        for (int i = 0; i < lines.size(); i++) {
            universe[i] = lines.get(i).toCharArray();
        }

        for (int i = 0; i < universe.length; i++) {
            boolean isEmpty = true;
            for (int j = 0; j < universe[i].length; j++) {
                if (universe[i][j] == '#') {
                    isEmpty = false;
                    break;
                }
            }

            if (isEmpty) {
                rows[i] = true;
                countExtraRows++;
            }
        }

        char[][] newUniverse = new char[universe.length + countExtraRows][lines.get(0).length()];
        for (int i = 0, oldI = 0; i < newUniverse.length; i++, oldI++) {
            if (rows[oldI]) {
                newUniverse[i] = universe[oldI];
                i++;
            }

            newUniverse[i] = universe[oldI];
        }

        for (int i = 0; i < newUniverse.length; i++) {
            for (int j = 0; j < newUniverse[i].length; j++) {
                System.out.print(newUniverse[i][j]);
            }
            System.out.println();
        }

        int countExtraCols = 0;
        boolean[] cols = new boolean[newUniverse[0].length];
        for (int j = 0; j < newUniverse[0].length; j++) {
            boolean isEmpty = true;
            for (int i = 0; i < newUniverse.length; i++) {
                if (newUniverse[i][j] == '#') {
                    isEmpty = false;
                    break;
                }
            }

            if (isEmpty) {
                cols[j] = true;
                countExtraCols++;
            }
        }

        char[][] newUniverse2 = new char[newUniverse.length][newUniverse[0].length + countExtraCols];
        for (int i = 0; i < newUniverse2.length; i++) {
            for (int j = 0, oldJ = 0; j < newUniverse2[i].length; j++, oldJ++) {
                if (cols[oldJ]) {
                    newUniverse2[i][j] = newUniverse[i][oldJ];
                    j++;
                }

                newUniverse2[i][j] = newUniverse[i][oldJ];
            }
        }

        for (int i = 0; i < newUniverse2.length; i++) {
            for (int j = 0; j < newUniverse2[i].length; j++) {
                System.out.print(newUniverse2[i][j]);
            }
            System.out.println();
        }

        ArrayList<int[]> galaxies = new ArrayList<>();
        for (int i = 0; i < newUniverse2.length; i++) {
            for (int j = 0; j < newUniverse2[i].length; j++) {
                if (newUniverse2[i][j] == '#') {
                    int[] galaxy = new int[2];
                    galaxy[0] = i;
                    galaxy[1] = j;
                    galaxies.add(galaxy);
                }
            }
        }

        int sumDistance = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                int[] galaxy1 = galaxies.get(i);
                int[] galaxy2 = galaxies.get(j);
                int distance = Math.abs(galaxy1[0] - galaxy2[0]) + Math.abs(galaxy1[1] - galaxy2[1]);
                sumDistance += distance;
            }
        }

        System.out.println(sumDistance);
    }

    private static void part2(Scanner console) {
        ArrayList<String> lines = new ArrayList<String>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }

        char[][] universe = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            universe[i] = lines.get(i).toCharArray();
        }

        int[] prefixSumExtraRows = new int[universe.length + 1];
        int[] prefixSumExtraCols = new int[universe[0].length + 1];

        for (int i = 0; i < universe.length; i++) {
            boolean isEmpty = true;
            for (int j = 0; j < universe[i].length; j++) {
                if (universe[i][j] == '#') {
                    isEmpty = false;
                    break;
                }
            }

            if (isEmpty) {
                prefixSumExtraRows[i + 1] += 1_000_000;
            } else {
                prefixSumExtraRows[i + 1] += 1;
            }
            prefixSumExtraRows[i + 1] += prefixSumExtraRows[i];
        }

        for (int j = 0; j < universe[0].length; j++) {
            boolean isEmpty = true;
            for (int i = 0; i < universe.length; i++) {
                if (universe[i][j] == '#') {
                    isEmpty = false;
                    break;
                }
            }

            if (isEmpty) {
                prefixSumExtraCols[j + 1] += 1_000_000;
            } else {
                prefixSumExtraCols[j + 1] += 1;
            }
            prefixSumExtraCols[j + 1] += prefixSumExtraCols[j];
        }

        ArrayList<int[]> galaxies = new ArrayList<>();
        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe[i].length; j++) {
                if (universe[i][j] == '#') {
                    int[] galaxy = new int[2];
                    galaxy[0] = i;
                    galaxy[1] = j;
                    galaxies.add(galaxy);
                }
            }
        }

        long sumDistance = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                int[] galaxy1 = galaxies.get(i);
                int[] galaxy2 = galaxies.get(j);
                int distance = Math.abs(prefixSumExtraRows[galaxy1[0]] - prefixSumExtraRows[galaxy2[0]]) +
                        Math.abs(prefixSumExtraCols[galaxy1[1]] - prefixSumExtraCols[galaxy2[1]]);
                sumDistance += distance;
            }
        }

        System.out.println(sumDistance);
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