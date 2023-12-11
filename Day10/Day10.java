package Day10;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day10 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "10";
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

        int startI = -1;
        int startJ = -1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    startI = i;
                    startJ = j;
                }
            }
        }

        Queue<int[]> bfs = new LinkedList<>();
        bfs.offer(new int[] { startI, startJ, 0, 0, 0 });
        int[][] minDistance = new int[map.length][map[0].length];
        for (int i = 0; i < minDistance.length; i++) {
            for (int j = 0; j < minDistance[i].length; j++) {
                minDistance[i][j] = Integer.MAX_VALUE;
            }
        }

        while (!bfs.isEmpty()) {
            int[] curr = bfs.poll();
            int i = curr[0];
            int j = curr[1];
            int distance = curr[2];
            if (i < 0 || i >= map.length || j < 0 || j >= map[i].length || minDistance[i][j] <= distance
                    || map[i][j] == '.') {
                continue;
            }

            System.out.println(i + " " + j + "  |  " + curr[3] + " " + curr[4]);

            if (map[i][j] == 'S') {
                bfs.add(new int[] { i + 1, j, distance + 1, 1, 0 });
                bfs.add(new int[] { i - 1, j, distance + 1, -1, 0 });
                bfs.add(new int[] { i, j + 1, distance + 1, 0, 1 });
                bfs.add(new int[] { i, j - 1, distance + 1, 0, -1 });
            } else if (map[i][j] == '|') {
                if (curr[3] != 1 && curr[3] != -1) {
                    continue;
                }

                bfs.add(new int[] { i + 1, j, distance + 1, 1, 0 });
                bfs.add(new int[] { i - 1, j, distance + 1, -1, 0 });
            } else if (map[i][j] == '-') {
                if (curr[4] != 1 && curr[4] != -1) {
                    continue;
                }

                bfs.add(new int[] { i, j + 1, distance + 1, 0, 1 });
                bfs.add(new int[] { i, j - 1, distance + 1, 0, -1 });
            } else if (map[i][j] == 'L') {
                if (curr[3] != 1 && curr[4] != -1) {
                    continue;
                }

                bfs.add(new int[] { i - 1, j, distance + 1, -1, 0 });
                bfs.add(new int[] { i, j + 1, distance + 1, 0, 1 });
            } else if (map[i][j] == 'J') {
                if (curr[3] != 1 && curr[4] != 1) {
                    continue;
                }

                bfs.add(new int[] { i - 1, j, distance + 1, -1, 0 });
                bfs.add(new int[] { i, j - 1, distance + 1, 0, -1 });
            } else if (map[i][j] == '7') {
                if (curr[3] != -1 && curr[4] != 1) {
                    continue;
                }

                bfs.add(new int[] { i + 1, j, distance + 1, 1, 0 });
                bfs.add(new int[] { i, j - 1, distance + 1, 0, -1 });
            } else if (map[i][j] == 'F') {
                if (curr[3] != -1 && curr[4] != -1) {
                    continue;
                }

                bfs.add(new int[] { i + 1, j, distance + 1, 1, 0 });
                bfs.add(new int[] { i, j + 1, distance + 1, 0, 1 });
            }

            minDistance[i][j] = distance;

        }

        int max = 0;
        for (int i = 0; i < minDistance.length; i++) {
            for (int j = 0; j < minDistance[i].length; j++) {
                if (minDistance[i][j] != Integer.MAX_VALUE) {
                    System.out.print(0 + "");
                    max = Math.max(max, minDistance[i][j]);
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }

        System.out.println(max);

    }

    private static void part2(Scanner console) {
        ArrayList<String> lines = new ArrayList<String>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }

        char[][] map = new char[lines.size() * 3][lines.get(0).length() * 3];
        for (int i = 0; i < lines.size(); i++) {
            char[] line = lines.get(i).toCharArray();
            for (int j = 0; j < line.length; j++) {
                char[][] m = new char[3][3];

                switch (line[j]) {
                    case '|':
                        m = new char[][] {
                                { '.', '|', '.' },
                                { '.', '|', '.' },
                                { '.', '|', '.' }
                        };
                        break;
                    case '-':
                        m = new char[][] {
                                { '.', '.', '.' },
                                { '-', '-', '-' },
                                { '.', '.', '.' }
                        };
                        break;
                    case 'L':
                        m = new char[][] {
                                { '.', '|', '.' },
                                { '.', 'L', '-' },
                                { '.', '.', '.' }
                        };
                        break;
                    case 'J':
                        m = new char[][] {
                                { '.', '|', '.' },
                                { '-', 'J', '.' },
                                { '.', '.', '.' }
                        };
                        break;
                    case '7':
                        m = new char[][] {
                                { '.', '.', '.' },
                                { '-', '7', '.' },
                                { '.', '|', '.' }
                        };
                        break;
                    case 'F':
                        m = new char[][] {
                                { '.', '.', '.' },
                                { '.', 'F', '-' },
                                { '.', '|', '.' }
                        };
                        break;
                    case '.':
                        m = new char[][] {
                                { '.', '.', '.' },
                                { '.', '.', '.' },
                                { '.', '.', '.' }
                        };
                        break;
                    case 'S':
                        m = new char[][] {
                                { '.', '|', '.' },
                                { '-', 'S', '-' },
                                { '.', '|', '.' }
                        };
                        break;
                }

                for (int k = 0; k < m.length; k++) {
                    for (int l = 0; l < m[k].length; l++) {
                        map[i * 3 + k][j * 3 + l] = m[k][l];
                    }
                }
            }
        }

        int startI = -1;
        int startJ = -1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    startI = i;
                    startJ = j;
                }
            }
        }

        boolean[][] loop = new boolean[map.length][map[0].length];
        Queue<int[]> bfs = new LinkedList<>();
        bfs.offer(new int[] { startI, startJ, 0, 0, 0 });
        int[][] minDistance = new int[map.length][map[0].length];
        for (int i = 0; i < minDistance.length; i++) {
            for (int j = 0; j < minDistance[i].length; j++) {
                minDistance[i][j] = Integer.MAX_VALUE;
            }
        }

        while (!bfs.isEmpty()) {
            int[] curr = bfs.poll();
            int i = curr[0];
            int j = curr[1];
            int distance = curr[2];
            if (i < 0 || i >= map.length || j < 0 || j >= map[i].length || minDistance[i][j] <= distance
                    || map[i][j] == '.') {
                continue;
            }

            System.out.println(i + " " + j + "  |  " + curr[3] + " " + curr[4]);

            if (map[i][j] == 'S') {
                bfs.add(new int[] { i + 1, j, distance + 1, 1, 0 });
                bfs.add(new int[] { i - 1, j, distance + 1, -1, 0 });
                bfs.add(new int[] { i, j + 1, distance + 1, 0, 1 });
                bfs.add(new int[] { i, j - 1, distance + 1, 0, -1 });
            } else if (map[i][j] == '|') {
                if (curr[3] != 1 && curr[3] != -1) {
                    continue;
                }

                bfs.add(new int[] { i + 1, j, distance + 1, 1, 0 });
                bfs.add(new int[] { i - 1, j, distance + 1, -1, 0 });
            } else if (map[i][j] == '-') {
                if (curr[4] != 1 && curr[4] != -1) {
                    continue;
                }

                bfs.add(new int[] { i, j + 1, distance + 1, 0, 1 });
                bfs.add(new int[] { i, j - 1, distance + 1, 0, -1 });
            } else if (map[i][j] == 'L') {
                if (curr[3] != 1 && curr[4] != -1) {
                    continue;
                }

                bfs.add(new int[] { i - 1, j, distance + 1, -1, 0 });
                bfs.add(new int[] { i, j + 1, distance + 1, 0, 1 });
            } else if (map[i][j] == 'J') {
                if (curr[3] != 1 && curr[4] != 1) {
                    continue;
                }

                bfs.add(new int[] { i - 1, j, distance + 1, -1, 0 });
                bfs.add(new int[] { i, j - 1, distance + 1, 0, -1 });
            } else if (map[i][j] == '7') {
                if (curr[3] != -1 && curr[4] != 1) {
                    continue;
                }

                bfs.add(new int[] { i + 1, j, distance + 1, 1, 0 });
                bfs.add(new int[] { i, j - 1, distance + 1, 0, -1 });
            } else if (map[i][j] == 'F') {
                if (curr[3] != -1 && curr[4] != -1) {
                    continue;
                }

                bfs.add(new int[] { i + 1, j, distance + 1, 1, 0 });
                bfs.add(new int[] { i, j + 1, distance + 1, 0, 1 });
            }

            minDistance[i][j] = distance;
            loop[i][j] = true;
        }

        for (int i = 0; i < loop.length; i++) {
            for (int j = 0; j < loop[i].length; j++) {
                if (loop[i][j]) {
                    System.out.print(0 + "");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }

        Queue<int[]> floodfill = new LinkedList<>();
        for (int i = 0; i < map.length; i++) {
            if (!loop[i][0]) {
                floodfill.offer(new int[] { i, 0 });
            }
            if (!loop[i][map[i].length - 1]) {
                floodfill.offer(new int[] { i, map[i].length - 1 });
            }
        }

        for (int j = 0; j < map[0].length; j++) {
            if (!loop[0][j]) {
                floodfill.offer(new int[] { 0, j });
            }
            if (!loop[map.length - 1][j]) {
                floodfill.offer(new int[] { map.length - 1, j });
            }
        }

        boolean[][] outside = new boolean[map.length][map[0].length];
        int[][] countVisited = new int[map.length / 3][map[0].length / 3];
        while (!floodfill.isEmpty()) {
            int[] curr = floodfill.poll();
            int i = curr[0];
            int j = curr[1];
            if (i < 0 || i >= map.length || j < 0 || j >= map[i].length || loop[i][j] || outside[i][j]) {
                continue;
            }
            outside[i][j] = true;
            countVisited[i / 3][j / 3]++;

            floodfill.offer(new int[] { i + 1, j });
            floodfill.offer(new int[] { i - 1, j });
            floodfill.offer(new int[] { i, j + 1 });
            floodfill.offer(new int[] { i, j - 1 });
        }

        int countOutside = 0;
        int countLoop = 0;
        for (int i = 0; i < countVisited.length; i++) {
            for (int j = 0; j < countVisited[i].length; j++) {
                if (countVisited[i][j] == 9) {
                    countOutside++;
                } else if (loop[i * 3 + 1][j * 3 + 1]) {
                    countLoop++;
                }
            }
        }

        int inside = countVisited.length * countVisited[0].length - countOutside - countLoop;
        System.out.println(inside);
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