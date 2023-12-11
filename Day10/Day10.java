package Day10;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
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

    private static class Node {
        private static enum Direction {
            UP, DOWN, LEFT, RIGHT
        }

        int i;
        int j;
        int distance;
        Direction prevDir;

        public Node(int i, int j, int distance, Direction prevDir) {
            this.i = i;
            this.j = j;
            this.distance = distance;
            this.prevDir = prevDir;
        }

        public static Direction invert(Direction dir) {
            switch (dir) {
                case UP:
                    return Direction.DOWN;
                case DOWN:
                    return Direction.UP;
                case LEFT:
                    return Direction.RIGHT;
                case RIGHT:
                    return Direction.LEFT;
                default:
                    return null;
            }
        }
    }

    private static void part1(Scanner console) {
        ArrayList<String> lines = new ArrayList<String>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }

        // create a map of the pipes
        char[][] map = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }

        // find the starting point
        int startI = -1;
        int startJ = -1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    startI = i;
                    startJ = j;
                    break;
                }
            }
        }

        // do a breadth first search to find the closest point from the start to each
        // point on the pipe loop
        Queue<Node> bfs = new LinkedList<>();
        bfs.offer(new Node(startI, startJ, 0, Node.Direction.DOWN));
        int[][] minDistance = new int[map.length][map[0].length];
        for (int i = 0; i < minDistance.length; i++) {
            for (int j = 0; j < minDistance[i].length; j++) {
                minDistance[i][j] = Integer.MAX_VALUE;
            }
        }

        // possible next directions for each pipe type
        HashMap<Character, Node.Direction[]> pipes = new HashMap<>();
        pipes.put('S', new Node.Direction[] { Node.Direction.UP, Node.Direction.DOWN, Node.Direction.LEFT,
                Node.Direction.RIGHT });
        pipes.put('|', new Node.Direction[] { Node.Direction.UP, Node.Direction.DOWN });
        pipes.put('-', new Node.Direction[] { Node.Direction.LEFT, Node.Direction.RIGHT });
        pipes.put('L', new Node.Direction[] { Node.Direction.UP, Node.Direction.RIGHT });
        pipes.put('J', new Node.Direction[] { Node.Direction.UP, Node.Direction.LEFT });
        pipes.put('7', new Node.Direction[] { Node.Direction.DOWN, Node.Direction.LEFT });
        pipes.put('F', new Node.Direction[] { Node.Direction.DOWN, Node.Direction.RIGHT });

        while (!bfs.isEmpty()) {
            Node curNode = bfs.poll();
            int i = curNode.i;
            int j = curNode.j;
            int distance = curNode.distance;

            // out of bounds or already visited or not a pipe
            if (i < 0 || i >= map.length || j < 0 || j >= map[i].length || minDistance[i][j] <= distance
                    || map[i][j] == '.') {
                continue;
            }

            // check if it was possible to reach this pipe from before by inverting the
            // previous direction
            // to see if it is a valid direction to go to from this pipe
            boolean isPossibleToReach = false;
            for (Node.Direction possibleDirection : pipes.get(map[i][j])) {
                if (Node.invert(possibleDirection) == curNode.prevDir) {
                    isPossibleToReach = true;
                    break;
                }
            }

            // not possible to reach this pipe
            if (!isPossibleToReach) {
                continue;
            }

            minDistance[i][j] = distance;
            for (Node.Direction possibleDirection : pipes.get(map[i][j])) {
                switch (possibleDirection) {
                    case UP:
                        bfs.offer(new Node(i - 1, j, distance + 1, Node.Direction.UP));
                        break;
                    case DOWN:
                        bfs.offer(new Node(i + 1, j, distance + 1, Node.Direction.DOWN));
                        break;
                    case LEFT:
                        bfs.offer(new Node(i, j - 1, distance + 1, Node.Direction.LEFT));
                        break;
                    case RIGHT:
                        bfs.offer(new Node(i, j + 1, distance + 1, Node.Direction.RIGHT));
                        break;
                }
            }
        }

        int max = 0;
        for (int i = 0; i < minDistance.length; i++) {
            for (int j = 0; j < minDistance[i].length; j++) {
                if (minDistance[i][j] != Integer.MAX_VALUE) {
                    max = Math.max(max, minDistance[i][j]);
                }
            }
        }

        System.out.println(max);

    }

    private static void part2(Scanner console) {
        ArrayList<String> lines = new ArrayList<String>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }

        // expand map by 9 times to allow water to squeeze between pipes
        char[][] map = new char[lines.size() * 3][lines.get(0).length() * 3];

        HashMap<Character, char[][]> expandedPipes = new HashMap<>();
        expandedPipes.put('|', new char[][] {
                { '.', '|', '.' },
                { '.', '|', '.' },
                { '.', '|', '.' }
        });
        expandedPipes.put('-', new char[][] {
                { '.', '.', '.' },
                { '-', '-', '-' },
                { '.', '.', '.' }
        });
        expandedPipes.put('L', new char[][] {
                { '.', '|', '.' },
                { '.', 'L', '-' },
                { '.', '.', '.' }
        });
        expandedPipes.put('J', new char[][] {
                { '.', '|', '.' },
                { '-', 'J', '.' },
                { '.', '.', '.' }
        });
        expandedPipes.put('7', new char[][] {
                { '.', '.', '.' },
                { '-', '7', '.' },
                { '.', '|', '.' }
        });
        expandedPipes.put('F', new char[][] {
                { '.', '.', '.' },
                { '.', 'F', '-' },
                { '.', '|', '.' }
        });
        expandedPipes.put('.', new char[][] {
                { '.', '.', '.' },
                { '.', '.', '.' },
                { '.', '.', '.' }
        });
        expandedPipes.put('S', new char[][] {
                { '.', '|', '.' },
                { '-', 'S', '-' },
                { '.', '|', '.' }
        });
        for (int i = 0; i < lines.size(); i++) {
            char[] line = lines.get(i).toCharArray();
            for (int j = 0; j < line.length; j++) {
                char[][] expandedMap = expandedPipes.get(line[j]);

                for (int k = 0; k < expandedMap.length; k++) {
                    for (int l = 0; l < expandedMap[k].length; l++) {
                        map[i * 3 + k][j * 3 + l] = expandedMap[k][l];
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
                    break;
                }
            }
        }

        boolean[][] loop = new boolean[map.length][map[0].length];
        Queue<Node> bfs = new LinkedList<>();
        bfs.offer(new Node(startI, startJ, 0, Node.Direction.DOWN));
        int[][] minDistance = new int[map.length][map[0].length];
        for (int i = 0; i < minDistance.length; i++) {
            for (int j = 0; j < minDistance[i].length; j++) {
                minDistance[i][j] = Integer.MAX_VALUE;
            }
        }

        HashMap<Character, Node.Direction[]> pipes = new HashMap<>();
        pipes.put('S', new Node.Direction[] { Node.Direction.UP, Node.Direction.DOWN, Node.Direction.LEFT,
                Node.Direction.RIGHT });
        pipes.put('|', new Node.Direction[] { Node.Direction.UP, Node.Direction.DOWN });
        pipes.put('-', new Node.Direction[] { Node.Direction.LEFT, Node.Direction.RIGHT });
        pipes.put('L', new Node.Direction[] { Node.Direction.UP, Node.Direction.RIGHT });
        pipes.put('J', new Node.Direction[] { Node.Direction.UP, Node.Direction.LEFT });
        pipes.put('7', new Node.Direction[] { Node.Direction.DOWN, Node.Direction.LEFT });
        pipes.put('F', new Node.Direction[] { Node.Direction.DOWN, Node.Direction.RIGHT });

        while (!bfs.isEmpty()) {
            Node curNode = bfs.poll();
            int i = curNode.i;
            int j = curNode.j;
            int distance = curNode.distance;

            // out of bounds or already visited or not a pipe
            if (i < 0 || i >= map.length || j < 0 || j >= map[i].length || minDistance[i][j] <= distance
                    || map[i][j] == '.') {
                continue;
            }

            // check if it was possible to reach this pipe from before by inverting the
            // previous direction
            // to see if it is a valid direction to go to from this pipe
            boolean isPossibleToReach = false;
            for (Node.Direction possibleDirection : pipes.get(map[i][j])) {
                if (Node.invert(possibleDirection) == curNode.prevDir) {
                    isPossibleToReach = true;
                    break;
                }
            }

            // not possible to reach this pipe
            if (!isPossibleToReach) {
                continue;
            }

            minDistance[i][j] = distance;
            loop[i][j] = true;
            for (Node.Direction possibleDirection : pipes.get(map[i][j])) {
                switch (possibleDirection) {
                    case UP:
                        bfs.offer(new Node(i - 1, j, distance + 1, Node.Direction.UP));
                        break;
                    case DOWN:
                        bfs.offer(new Node(i + 1, j, distance + 1, Node.Direction.DOWN));
                        break;
                    case LEFT:
                        bfs.offer(new Node(i, j - 1, distance + 1, Node.Direction.LEFT));
                        break;
                    case RIGHT:
                        bfs.offer(new Node(i, j + 1, distance + 1, Node.Direction.RIGHT));
                        break;
                }
            }
        }

        // floodfill the edges if not part of the loop
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

        boolean[][] visited = new boolean[map.length][map[0].length];
        int[][] countVisited = new int[map.length / 3][map[0].length / 3]; // if this is 9, then it is outside the loop
        while (!floodfill.isEmpty()) {
            int[] curr = floodfill.poll();
            int i = curr[0];
            int j = curr[1];
            if (i < 0 || i >= map.length || j < 0 || j >= map[i].length || loop[i][j] || visited[i][j]) {
                continue;
            }
            visited[i][j] = true;
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

        // inside is just total subtracted by the outside and loop count
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