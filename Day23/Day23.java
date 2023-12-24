package Day23;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day23 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "23";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static int[][] directions = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

    private static void part1(Scanner console) {
        ArrayList<String> lines = new ArrayList<>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }

        char[][] map = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }

        int[] start = new int[2];
        int[] end = new int[2];
        for (int j = 0; j < map[0].length; j++) {
            if (map[0][j] == '.') {
                start[0] = 0;
                start[1] = j;
            }
            if (map[map.length - 1][j] == '.') {
                end[0] = map.length - 1;
                end[1] = j;
            }
        }

        System.out.println("end " + Arrays.toString(end));

        int longest = dfs(map, start, end, new boolean[map.length][map[0].length], new int[map.length][map[0].length]);
        System.out.println(longest);
    }

    private static int dfs(char[][] map, int[] cur, int[] end, boolean[][] visited, int[][] memoize) {
        if (visited[cur[0]][cur[1]]) {
            return Integer.MIN_VALUE;
        }

        if (cur[0] == end[0] && cur[1] == end[1]) {
            return 0;
        }

        if (memoize[cur[0]][cur[1]] != 0) {
            // return memoize[cur[0]][cur[1]];
        }

        int only = -1;
        if (map[cur[0]][cur[1]] == '>') {
            only = 0;
        } else if (map[cur[0]][cur[1]] == '<') {
            only = 1;
        } else if (map[cur[0]][cur[1]] == 'v') {
            only = 2;
        } else if (map[cur[0]][cur[1]] == '^') {
            only = 3;
        }

        visited[cur[0]][cur[1]] = true;
        int longest = 0;
        for (int i = 0; i < directions.length; i++) {
            int[] dir = directions[i];
            if (only != -1 && only != i) {
                continue;
            }

            int[] next = new int[] { cur[0] + dir[0], cur[1] + dir[1] };
            if (next[0] >= 0 && next[0] < map.length && next[1] >= 0 && next[1] < map[0].length
                    && map[next[0]][next[1]] != '#') {
                longest = Math.max(longest, dfs(map, next, end, visited, memoize));
            }
        }
        visited[cur[0]][cur[1]] = false;
        memoize[cur[0]][cur[1]] = longest + 1;
        return longest + 1;
    }

    private static int count(char[][] map, int i, int j) {
        int count = 0;
        for (int[] dir : directions) {
            int[] next = new int[] { i + dir[0], j + dir[1] };
            if (next[0] >= 0 && next[0] < map.length && next[1] >= 0 && next[1] < map[0].length
                    && map[next[0]][next[1]] == '.') {
                count++;
            }
        }
        return count;
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

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != '#') {
                    map[i][j] = '.';
                }
                System.out.print(map[i][j]);
            }
            System.out.println();
        }

        int[] start = new int[2];
        int[] end = new int[2];
        for (int j = 0; j < map[0].length; j++) {
            if (map[0][j] == '.') {
                start[0] = 0;
                start[1] = j;
            }
            if (map[map.length - 1][j] == '.') {
                end[0] = map.length - 1;
                end[1] = j;
            }
        }

        int[][][] l = new int[map.length][map[0].length][4];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != '.') {
                    continue;
                }

                for (int dirI = 0; dirI < directions.length; dirI++) {
                    int[] dir = directions[dirI];
                    int curI = i + dir[0];
                    int curJ = j + dir[1];
                    while (curI >= 0 && curI < map.length && curJ >= 0 && curJ < map[0].length
                            && map[curI][curJ] != '#') {
                        l[i][j][dirI]++;

                        if (count(map, curI, curJ) > 2) {
                            break;
                        }
                        curI += dir[0];
                        curJ += dir[1];
                    }
                }
            }
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // System.out.println(i + ", " + j + " | " + Arrays.toString(l[i][j]));
                for (int d = 0; d < directions.length; d++) {
                    int len = l[i][j][d];
                    int[] dir = directions[d];
                    if (d <= 1) {
                        assert l[i][j][d] == l[i + dir[0] * len][j + dir[1] * len][(d + 1) % 2];
                    } else {
                        assert l[i][j][d] == l[i + dir[0] * len][j + dir[1] * len][(d + 1) % 2 + 2];
                    }
                }
            }
        }

        int longest = dfs2(map, start, end, 0, new boolean[map.length][map[0].length], l);

        System.out.println(longest);

        // { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }
        // RIGHT LEFT DOWN UP
        // 0 1 2 3
    }

    private static int dfs2(char[][] map, int[] cur, int[] end, int steps, boolean[][] visited, int[][][] l) {
        if (visited[cur[0]][cur[1]]) {
            return Integer.MIN_VALUE;
        }

        if (cur[0] == end[0] && cur[1] == end[1]) {
            return steps;
        }

        visited[cur[0]][cur[1]] = true;
        int longest = 0;
        for (int i = 0; i < directions.length; i++) {
            int length = l[cur[0]][cur[1]][i];

            int[] dir = directions[i];
            int[] next = new int[] { cur[0] + dir[0] * length, cur[1] + dir[1] * length };
            if (next[0] >= 0 && next[0] < map.length && next[1] >= 0 && next[1] < map[0].length
                    && map[next[0]][next[1]] != '#') {
                longest = Math.max(longest, dfs2(map, next, end, steps + length, visited, l));
            }
        }
        visited[cur[0]][cur[1]] = false;
        return longest;
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