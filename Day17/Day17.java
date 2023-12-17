package Day17;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day17 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "17";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = true;

    private static class Point {
        int i;
        int j;
        int di;
        int dj;
        int timeSince;
        int tot;

        public Point(int i, int j, int di, int dj, int timeSince, int tot) {
            this.i = i;
            this.j = j;
            this.di = di;
            this.dj = dj;
            this.timeSince = timeSince;
            this.tot = tot;
        }
    }

    private static void part1(Scanner console) {
        ArrayList<String> lines = new ArrayList<String>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }

        char[][] map = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }

        int min = Integer.MAX_VALUE;
        PriorityQueue<Point> dijkstra = new PriorityQueue<Point>(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.tot - o2.tot;
            }
        });

        boolean[][][][] visited = new boolean[map.length][map[0].length][4][8];

        dijkstra.offer(new Point(0, 0, 1, 0, 0, '0' - map[0][0]));
        dijkstra.offer(new Point(0, 0, 0, 1, 0, '0' - map[0][0]));

        while (!dijkstra.isEmpty()) {
            Point p = dijkstra.poll();
            if (p.i < 0 || p.i >= map.length || p.j < 0 || p.j >= map[0].length || p.tot >= min) {
                continue;
            }
            p.tot += map[p.i][p.j] - '0';

            if (p.i == map.length - 1 && p.j == map[0].length - 1) {
                min = Math.min(min, p.tot);
                break;
            }

            if (p.tot >= min || visited[p.i][p.j][p.timeSince][(1 + p.di) * 2 + 1 + p.dj]) {
                continue;
            }
            visited[p.i][p.j][p.timeSince][(1 + p.di) * 2 + 1 + p.dj] = true;

            if (p.timeSince <= 2) {
                dijkstra.offer(new Point(p.i + p.di, p.j + p.dj, p.di, p.dj, p.timeSince + 1, p.tot));
            }

            dijkstra.offer(new Point(p.i + p.dj, p.j - p.di, p.dj, -p.di, 1, p.tot));
            dijkstra.offer(new Point(p.i - p.dj, p.j + p.di, -p.dj, p.di, 1, p.tot));
        }

        System.out.println(min);
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

        int min = Integer.MAX_VALUE;
        PriorityQueue<Point> dijkstra = new PriorityQueue<Point>(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.tot - o2.tot;
            }
        });

        boolean[][][][] visited = new boolean[map.length][map[0].length][11][8];

        dijkstra.offer(new Point(0, 0, 1, 0, 0, '0' - map[0][0]));
        dijkstra.offer(new Point(0, 0, 0, 1, 0, '0' - map[0][0]));

        while (!dijkstra.isEmpty()) {
            Point p = dijkstra.poll();
            if (p.i < 0 || p.i >= map.length || p.j < 0 || p.j >= map[0].length || p.tot >= min) {
                continue;
            }
            p.tot += map[p.i][p.j] - '0';

            if (p.i == map.length - 1 && p.j == map[0].length - 1) {
                min = Math.min(min, p.tot);
                break;
            }

            if (p.tot >= min || visited[p.i][p.j][p.timeSince][(1 + p.di) * 2 + 1 + p.dj]) {
                continue;
            }
            visited[p.i][p.j][p.timeSince][(1 + p.di) * 2 + 1 + p.dj] = true;

            if (p.timeSince <= 9) {
                dijkstra.offer(new Point(p.i + p.di, p.j + p.dj, p.di, p.dj, p.timeSince + 1, p.tot));
            }
            if (p.timeSince >= 4) {
                dijkstra.offer(new Point(p.i + p.dj, p.j - p.di, p.dj, -p.di, 1, p.tot));
                dijkstra.offer(new Point(p.i - p.dj, p.j + p.di, -p.dj, p.di, 1, p.tot));
            }
        }

        System.out.println(min);
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