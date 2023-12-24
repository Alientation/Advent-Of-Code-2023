package Day21;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day21 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "21";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static class Node {
        int[] pos;
        int numSteps;

        public Node(int[] pos, int numSteps) {
            this.pos = pos;
            this.numSteps = numSteps;
        }
    }

    private static void part1(Scanner console) {
        ArrayList<String> lines = new ArrayList<>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }
        char[][] map = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }

        int startI = 0;
        int startJ = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    startI = i;
                    startJ = j;
                    break;
                }
            }
        }

        int num_steps = 64;
        int[] counts = new int[2];
        int[][] directions = new int[][] {
                { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 }
        };
        Queue<Node> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        queue.add(new Node(new int[] { startI, startJ }, 0));
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int curI = cur.pos[0];
            int curJ = cur.pos[1];
            int curSteps = cur.numSteps;

            if (visited.contains(curI * 100_000 + curJ) || curSteps > num_steps) {
                continue;
            }
            visited.add(curI * 100_000 + curJ);
            counts[curSteps % 2]++;

            for (int[] dir : directions) {
                int newI = curI + dir[0];
                int newJ = curJ + dir[1];
                if (newI < 0 || newI >= map.length || newJ < 0 || newJ >= map[0].length) {
                    continue;
                }

                if (map[newI][newJ] == '#') {
                    continue;
                }

                queue.add(new Node(new int[] { newI, newJ }, curSteps + 1));
            }
        }

        System.out.println(counts[0]);
    }

    private static interface Condition {
        public boolean isInBounds(int x, int y);
    }

    private static long[] bfs(char[][] map, int startI, int startJ, Condition condition) {
        // for (int i = 0; i < map.length; i++) {
        // for (int j = 0; j < map[i].length; j++) {
        // if (i == startI && j == startJ) {
        // System.out.print("S");
        // } else if (condition.isInBounds(i, j)) {
        // System.out.print("#");
        // } else {
        // System.out.print(".");
        // }
        // }
        // System.out.println();
        // }

        int[][] directions = new int[][] {
                { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 }
        };
        Queue<int[]> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        queue.add(new int[] { startI, startJ, 0 });

        long[] counts = new long[2];
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curI = cur[0];
            int curJ = cur[1];
            int curSteps = cur[2];

            if (visited.contains(curI * 10_000 + curJ)) {
                continue;
            }
            visited.add(curI * 10_000 + curJ);
            counts[(curSteps + 1) % 2]++; /// THIS IS WACKY FIX, BECAUSE I DONT WANT TO CHANGE ALL THE MATHS I ALREADY
                                          /// DID

            for (int[] dir : directions) {
                int newI = curI + dir[0];
                int newJ = curJ + dir[1];
                if (newI < 0 || newI >= map.length || newJ < 0 || newJ >= map[0].length
                        || !condition.isInBounds(newI, newJ)) {
                    continue;
                }

                if (map[newI][newJ] == '#') {
                    continue;
                }

                queue.add(new int[] { newI, newJ, curSteps + 1 });
            }
        }

        return counts;
    }

    private static void part2(Scanner console) {
        ArrayList<String> lines = new ArrayList<>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }
        char[][] map = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }

        System.out.println(map.length + " x " + map[0].length);

        int num_steps = 26501365;
        int startI = 0;
        int startJ = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    startI = i;
                    startJ = j;
                    break;
                }
            }
        }

        long r_floor = num_steps / map.length;
        System.out.println("\nENTIRE BLOCK");
        long[] ENTIRE_BLOCK = bfs(map, startI, startJ, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                return true;
            }
        });
        System.out.println(ENTIRE_BLOCK[0] + " - " + ENTIRE_BLOCK[1]);

        System.out.println("\nLEFT CORNER");
        long[] LEFT_CORNER = bfs(map, startI, startJ, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                if (y >= map[0].length / 2) {
                    return true;
                }

                if (x <= map.length / 2) {
                    return x + y >= map[0].length / 2;
                } else {
                    return x - y <= map[0].length / 2;
                }
            }
        });
        System.out.println(LEFT_CORNER[0] + " - " + LEFT_CORNER[1]);

        System.out.println("\nTOP CORNER");
        long[] TOP_CORNER = bfs(map, startI, startJ, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                if (x >= map.length / 2) {
                    return true;
                }

                if (y <= map[0].length / 2) {
                    return x + y >= map.length / 2;
                } else {
                    return y - x <= map.length / 2;
                }
            }
        });
        System.out.println(TOP_CORNER[0] + " - " + TOP_CORNER[1]);

        System.out.println("\nRIGHT CORNER");
        long[] RIGHT_CORNER = bfs(map, startI, startJ, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                if (y <= map[0].length / 2) {
                    return true;
                }

                if (x <= map.length / 2) {
                    return y - x <= map[0].length / 2;
                } else {
                    return x + y <= map.length / 2 + map[0].length - 1;
                }
            }
        });
        System.out.println(RIGHT_CORNER[0] + " - " + RIGHT_CORNER[1]);

        System.out.println("\nBOTTOM CORNER");
        long[] BOTTOM_CORNER = bfs(map, startI, startJ, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                if (x <= map.length / 2) {
                    return true;
                }

                if (y <= map[0].length / 2) {
                    return x - y <= map.length / 2;
                } else {
                    return x + y <= map.length / 2 + map[0].length - 1;
                }
            }
        });
        System.out.println(BOTTOM_CORNER[0] + " - " + BOTTOM_CORNER[1]);

        System.out.println("\nTOP LEFT SMALL EDGE");
        long[] TOP_LEFT_SMALL_EDGE = bfs(map, map.length - 1, map[0].length - 1, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                return x + y > map.length / 2 + map[0].length - 1;
            }
        });
        System.out.println(TOP_LEFT_SMALL_EDGE[0] + " - " + TOP_LEFT_SMALL_EDGE[1]);

        System.out.println("\nTOP LEFT BIG EDGE");
        long[] TOP_LEFT_BIG_EDGE = bfs(map, startI, startJ, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                return x + y >= map.length / 2;
            }
        });
        System.out.println(TOP_LEFT_BIG_EDGE[0] + " - " + TOP_LEFT_BIG_EDGE[1]);

        System.out.println("\nTOP RIGHT SMALL EDGE");
        long[] TOP_RIGHT_SMALL_EDGE = bfs(map, map[0].length - 1, 0, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                return x - y > map.length / 2;
            }
        });
        System.out.println(TOP_RIGHT_SMALL_EDGE[0] + " - " + TOP_RIGHT_SMALL_EDGE[1]);

        System.out.println("\nTOP RIGHT BIG EDGE");
        long[] TOP_RIGHT_BIG_EDGE = bfs(map, startI, startJ, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                return y - x <= map.length / 2;
            }
        });
        System.out.println(TOP_RIGHT_BIG_EDGE[0] + " - " + TOP_RIGHT_BIG_EDGE[1]);

        System.out.println("\nBOTTOM RIGHT SMALL EDGE");
        long[] BOTTOM_RIGHT_SMALL_EDGE = bfs(map, 0, 0, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                return x + y < map.length / 2;
            }
        });
        System.out.println(BOTTOM_RIGHT_SMALL_EDGE[0] + " - " + BOTTOM_RIGHT_SMALL_EDGE[1]);

        System.out.println("\nBOTTOM RIGHT BIG EDGE");
        long[] BOTTOM_RIGHT_BIG_EDGE = bfs(map, startI, startJ, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                return x + y <= map.length / 2 + map[0].length - 1;
            }
        });
        System.out.println(BOTTOM_RIGHT_BIG_EDGE[0] + " - " + BOTTOM_RIGHT_BIG_EDGE[1]);

        System.out.println("\nBOTTOM LEFT SMALL EDGE");
        long[] BOTTOM_LEFT_SMALL_EDGE = bfs(map, 0, map[0].length - 1, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                return y - x > map.length / 2;
            }
        });
        System.out.println(BOTTOM_LEFT_SMALL_EDGE[0] + " - " + BOTTOM_LEFT_SMALL_EDGE[1]);

        System.out.println("\nBOTTOM LEFT BIG EDGE");
        long[] BOTTOM_LEFT_BIG_EDGE = bfs(map, startI, startJ, new Condition() {
            @Override
            public boolean isInBounds(int x, int y) {
                return x - y <= map[0].length / 2;
            }
        });
        System.out.println(BOTTOM_LEFT_BIG_EDGE[0] + " - " + BOTTOM_LEFT_BIG_EDGE[1]);

        long sum = 0;
        sum += LEFT_CORNER[(int) (r_floor % 2)];
        sum += TOP_CORNER[(int) (r_floor % 2)];
        sum += RIGHT_CORNER[(int) (r_floor % 2)];
        sum += BOTTOM_CORNER[(int) (r_floor % 2)];

        sum += TOP_LEFT_SMALL_EDGE[(int) ((r_floor + 1) % 2)] * r_floor;
        sum += TOP_LEFT_BIG_EDGE[(int) (r_floor % 2)] * (r_floor - 1L);
        sum += TOP_RIGHT_SMALL_EDGE[(int) ((r_floor + 1) % 2)] * r_floor;
        sum += TOP_RIGHT_BIG_EDGE[(int) (r_floor % 2)] * (r_floor - 1L);
        sum += BOTTOM_RIGHT_SMALL_EDGE[(int) ((r_floor + 1) % 2)] * r_floor;
        sum += BOTTOM_RIGHT_BIG_EDGE[(int) (r_floor % 2)] * (r_floor - 1L);
        sum += BOTTOM_LEFT_SMALL_EDGE[(int) ((r_floor + 1) % 2)] * r_floor;
        sum += BOTTOM_LEFT_BIG_EDGE[(int) (r_floor % 2)] * (r_floor - 1L);

        long mult = 4;
        sum += ENTIRE_BLOCK[0];
        for (int i = 1; i < r_floor; i++) {
            sum += ENTIRE_BLOCK[i % 2] * mult;
            mult += 4;
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