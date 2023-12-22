package Day22;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Arrays;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day22 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "22";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        ArrayList<int[][]> bricks = new ArrayList<>();

        int largestX = 0;
        int largestY = 0;
        while (console.hasNextLine()) {
            String[] parts = console.nextLine().split("~");
            String[] start = parts[0].split(",");
            String[] end = parts[1].split(",");
            int[] s = new int[] { Integer.parseInt(start[0]), Integer.parseInt(start[1]),
                    Integer.parseInt(start[2]) };
            int[] e = new int[] { Integer.parseInt(end[0]), Integer.parseInt(end[1]),
                    Integer.parseInt(end[2]) };

            for (int i = 0; i < 3; i++) {
                if (s[i] > e[i]) {
                    int temp = s[i];
                    s[i] = e[i];
                    e[i] = temp;
                }
            }

            bricks.add(new int[][] { s, e });

            largestX = Math.max(largestX, Math.max(s[0], e[0]));
            largestY = Math.max(largestY, Math.max(s[1], e[1]));
        }

        Collections.sort(bricks, new Comparator<int[][]>() {
            @Override
            public int compare(int[][] o1, int[][] o2) {
                return o1[0][2] - o2[0][2];
            }
        });
        int sum = 0;

        int[][] heightMap = new int[largestX + 1][largestY + 1];
        HashMap<Integer, Integer> bricksLoc = new HashMap<>();
        for (int b = 0; b < bricks.size(); b++) {
            int[][] brick = bricks.get(b);
            int x = brick[0][0];
            int y = brick[0][1];
            int z = brick[0][2];
            int x2 = brick[1][0];
            int y2 = brick[1][1];
            int z2 = brick[1][2];

            int maxZ = 0;
            for (int i = x; i <= x2; i++) {
                for (int j = y; j <= y2; j++) {
                    maxZ = Math.max(maxZ, heightMap[i][j]);
                }
            }

            for (int i = x; i <= x2; i++) {
                for (int j = y; j <= y2; j++) {
                    heightMap[i][j] = maxZ + (z2 - z + 1);
                }
            }

            bricksLoc.put(b, maxZ);
        }

        for (int b = 0; b < bricks.size(); b++) {
            int[][] brick = bricks.get(b);
            boolean same = true;

            int[][] newHeightMap = new int[largestX + 1][largestY + 1];
            for (int br = 0; br < bricks.size(); br++) {
                if (br == b) {
                    continue;
                }
                int[][] newBrick = bricks.get(br);
                int x = newBrick[0][0];
                int y = newBrick[0][1];
                int z = newBrick[0][2];
                int x2 = newBrick[1][0];
                int y2 = newBrick[1][1];
                int z2 = newBrick[1][2];

                int maxZ = 0;
                for (int i = x; i <= x2; i++) {
                    for (int j = y; j <= y2; j++) {
                        maxZ = Math.max(maxZ, newHeightMap[i][j]);
                    }
                }

                for (int i = x; i <= x2; i++) {
                    for (int j = y; j <= y2; j++) {
                        newHeightMap[i][j] = maxZ + (z2 - z + 1);
                    }
                }

                if (bricksLoc.get(br) != maxZ) {
                    same = false;
                    break;
                }
            }

            if (same) {
                System.out.println(Arrays.toString(brick[0]) + " -> " + Arrays.toString(brick[1]));

                sum++;
            }
        }

        System.out.println(sum);
    }

    private static void part2(Scanner console) {
        ArrayList<int[][]> bricks = new ArrayList<>();

        int largestX = 0;
        int largestY = 0;
        while (console.hasNextLine()) {
            String[] parts = console.nextLine().split("~");
            String[] start = parts[0].split(",");
            String[] end = parts[1].split(",");
            int[] s = new int[] { Integer.parseInt(start[0]), Integer.parseInt(start[1]),
                    Integer.parseInt(start[2]) };
            int[] e = new int[] { Integer.parseInt(end[0]), Integer.parseInt(end[1]),
                    Integer.parseInt(end[2]) };

            for (int i = 0; i < 3; i++) {
                if (s[i] > e[i]) {
                    int temp = s[i];
                    s[i] = e[i];
                    e[i] = temp;
                }
            }

            bricks.add(new int[][] { s, e });

            largestX = Math.max(largestX, Math.max(s[0], e[0]));
            largestY = Math.max(largestY, Math.max(s[1], e[1]));
        }

        Collections.sort(bricks, new Comparator<int[][]>() {
            @Override
            public int compare(int[][] o1, int[][] o2) {
                return o1[0][2] - o2[0][2];
            }
        });
        int sum = 0;

        int[][] heightMap = new int[largestX + 1][largestY + 1];
        HashMap<Integer, Integer> bricksLoc = new HashMap<>();
        for (int b = 0; b < bricks.size(); b++) {
            int[][] brick = bricks.get(b);
            int x = brick[0][0];
            int y = brick[0][1];
            int z = brick[0][2];
            int x2 = brick[1][0];
            int y2 = brick[1][1];
            int z2 = brick[1][2];

            int maxZ = 0;
            for (int i = x; i <= x2; i++) {
                for (int j = y; j <= y2; j++) {
                    maxZ = Math.max(maxZ, heightMap[i][j]);
                }
            }

            for (int i = x; i <= x2; i++) {
                for (int j = y; j <= y2; j++) {
                    heightMap[i][j] = maxZ + (z2 - z + 1);
                }
            }

            bricksLoc.put(b, maxZ);
        }

        for (int b = 0; b < bricks.size(); b++) {
            int[][] newHeightMap = new int[largestX + 1][largestY + 1];
            for (int br = 0; br < bricks.size(); br++) {
                if (br == b) {
                    continue;
                }
                int[][] newBrick = bricks.get(br);
                int x = newBrick[0][0];
                int y = newBrick[0][1];
                int z = newBrick[0][2];
                int x2 = newBrick[1][0];
                int y2 = newBrick[1][1];
                int z2 = newBrick[1][2];

                int maxZ = 0;
                for (int i = x; i <= x2; i++) {
                    for (int j = y; j <= y2; j++) {
                        maxZ = Math.max(maxZ, newHeightMap[i][j]);
                    }
                }

                for (int i = x; i <= x2; i++) {
                    for (int j = y; j <= y2; j++) {
                        newHeightMap[i][j] = maxZ + (z2 - z + 1);
                    }
                }

                if (bricksLoc.get(br) != maxZ) {
                    sum++;
                }
            }
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