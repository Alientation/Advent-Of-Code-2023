package Day16;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day16 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "16";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static class Point {
        int i;
        int j;
        int di;
        int dj;

        public Point(int i, int j, int di, int dj) {
            this.i = i;
            this.j = j;
            this.di = di;
            this.dj = dj;
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

        int countVisited = dfs(map, 0, 0, 0, 1);
        System.out.println(countVisited);
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

        int maxVisited = 0;
        for (int i = 0; i < map.length; i++) {
            maxVisited = Math.max(maxVisited, dfs(map, i, 0, 0, 1));
            maxVisited = Math.max(maxVisited, dfs(map, i, map[i].length - 1, 0, -1));
        }

        for (int j = 0; j < map[0].length; j++) {
            maxVisited = Math.max(maxVisited, dfs(map, 0, j, 1, 0));
            maxVisited = Math.max(maxVisited, dfs(map, map.length - 1, j, -1, 0));
        }

        System.out.println(maxVisited);
    }

    private static int dfs(char[][] map, int startI, int startJ, int di, int dj) {
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(startI, startJ, di, dj));
        boolean[][][] visited = new boolean[map.length][map[0].length][8];
        while (!stack.isEmpty()) {
            Point p = stack.pop();

            if (p.i < 0 || p.i >= map.length || p.j < 0 || p.j >= map[0].length
                    || visited[p.i][p.j][(p.di + 1) * 2 + p.dj + 1]) {
                continue;
            }
            visited[p.i][p.j][(p.di + 1) * 2 + p.dj + 1] = true;

            final Point LEFT = new Point(p.i, p.j - 1, 0, -1);
            final Point RIGHT = new Point(p.i, p.j + 1, 0, 1);
            final Point UP = new Point(p.i - 1, p.j, -1, 0);
            final Point DOWN = new Point(p.i + 1, p.j, 1, 0);
            final Point CONTINUE = new Point(p.i + p.di, p.j + p.dj, p.di, p.dj);

            if (map[p.i][p.j] == '.') {
                stack.push(CONTINUE);
            } else if (map[p.i][p.j] == '|') {
                if (p.dj == 1 || p.dj == -1) {
                    stack.push(UP);
                    stack.push(DOWN);
                } else {
                    stack.push(CONTINUE);
                }
            } else if (map[p.i][p.j] == '-') {
                if (p.di == 1 || p.di == -1) {
                    stack.push(LEFT);
                    stack.push(RIGHT);
                } else {
                    stack.push(CONTINUE);
                }
            } else if (map[p.i][p.j] == '/') {
                if (p.di == 1) {
                    stack.push(LEFT);
                } else if (p.di == -1) {
                    stack.push(RIGHT);
                } else if (p.dj == 1) {
                    stack.push(UP);
                } else {
                    stack.push(DOWN);
                }
            } else if (map[p.i][p.j] == '\\') {
                if (p.di == 1) {
                    stack.push(RIGHT);
                } else if (p.di == -1) {
                    stack.push(LEFT);
                } else if (p.dj == 1) {
                    stack.push(DOWN);
                } else {
                    stack.push(UP);
                }
            }
        }

        int countVisited = 0;
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                boolean isVisited = false;
                for (boolean v : visited[i][j]) {
                    isVisited |= v;
                }

                if (isVisited) {
                    countVisited++;
                }
            }
        }
        return countVisited;
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