import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day18 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "18";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        HashMap<String, int[]> directions = new HashMap<String, int[]>();
        directions.put("U", new int[] { -1, 0 });
        directions.put("D", new int[] { 1, 0 });
        directions.put("L", new int[] { 0, -1 });
        directions.put("R", new int[] { 0, 1 });

        HashSet<Integer> visited = new HashSet<Integer>();
        int mostLeft = 0;
        int mostUp = 0;
        int mostRight = 0;
        int mostDown = 0;

        int i = 0;
        int j = 0;
        visited.add(i * 1000 + j);
        while (console.hasNextLine()) {
            String[] parts = console.nextLine().split(" ");

            String dir = parts[0];
            int steps = Integer.parseInt(parts[1]);
            String color = parts[2];

            int[] direction = directions.get(dir);
            while (steps > 0) {
                steps--;
                i += direction[0];
                j += direction[1];
                visited.add(i * 1000 + j);

                mostLeft = Math.min(mostLeft, j);
                mostRight = Math.max(mostRight, j);
                mostDown = Math.max(mostDown, i);
                mostUp = Math.min(mostUp, i);
            }
        }

        HashSet<Integer> outside = new HashSet<Integer>();
        Stack<int[]> stack = new Stack<>();
        for (int k = mostUp; k <= mostDown; k++) {
            if (!visited.contains(k * 1000 + mostLeft)) {
                stack.push(new int[] { k, mostLeft });
            }
            if (!visited.contains(k * 1000 + mostRight)) {
                stack.push(new int[] { k, mostRight });
            }
        }

        for (int k = mostLeft; k <= mostRight; k++) {
            if (!visited.contains(mostUp * 1000 + k)) {
                stack.push(new int[] { mostUp, k });
            }
            if (!visited.contains(mostDown * 1000 + k)) {
                stack.push(new int[] { mostDown, k });
            }
        }

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            if (outside.contains(current[0] * 1000 + current[1]) || visited.contains(current[0] * 1000 + current[1])) {
                continue;
            }

            outside.add(current[0] * 1000 + current[1]);

            for (int[] direction : directions.values()) {
                int[] next = new int[] { current[0] + direction[0], current[1] + direction[1] };
                if (next[0] < mostUp || next[0] > mostDown || next[1] < mostLeft || next[1] > mostRight) {
                    continue;
                }

                if (!outside.contains(next[0] * 1000 + next[1])) {
                    stack.push(next);
                }
            }
        }

        System.out.println(mostLeft + " " + mostRight + " " + mostUp + " " + mostDown);

        int totArea = (mostRight - mostLeft + 1) * (mostDown - mostUp + 1) - outside.size();
        System.out.println(totArea);
    }

    private static void part2(Scanner console) {
        long[][] directions = new long[][] {
                { 0, 1 },
                { 1, 0 },
                { 0, -1 },
                { -1, 0 }
        };

        ArrayList<long[]> vertices = new ArrayList<long[]>();

        long i = 0;
        long j = 0;
        long tot = 0;
        vertices.add(new long[] { 0, 0 });
        while (console.hasNextLine()) {
            String[] parts = console.nextLine().split(" ");
            String color = parts[2].substring(2, parts[2].length() - 1);

            String first = color.substring(0, color.length() - 1);

            long[] direction = directions[(color.charAt(color.length() - 1) - '0')];

            long dist = Long.parseLong(first, 16);
            i += dist * direction[0];
            j += dist * direction[1];

            tot += dist;

            vertices.add(new long[] { i, j });
        }

        long area = getArea(vertices) + tot / 2 + 1;
        System.out.println(area);
    }

    private static long getArea(ArrayList<long[]> vertices) {
        long area = 0;
        int j = vertices.size() - 1;
        for (int i = 0; i < vertices.size(); i++) {
            area += (vertices.get(j)[0] + vertices.get(i)[0]) * (vertices.get(j)[1] - vertices.get(i)[1]);
            j = i;
        }
        return Math.abs(area / 2);
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