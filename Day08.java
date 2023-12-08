import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day08 {
    private static final String TEST_FILES_DIRECTORY = "files\\tests";
    private static final String INPUT_FILES_DIRECTORY = "files\\inputs";

    private static final String DAY = "08";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static class Node {
        String cur;
        String left;
        String right;

        public Node(String cur, String left, String right) {
            this.cur = cur;
            this.left = left;
            this.right = right;
        }
    }

    private static void part1(Scanner console) {
        String direction = console.nextLine();
        console.nextLine();
        HashMap<String, Node> nodes = new HashMap<String, Node>();
        while (console.hasNextLine()) {
            String line = console.nextLine();
            String[] split = line.split(" = ");
            String node = split[0];
            String[] split2 = split[1].substring(1, split[1].length() - 1).split(", ");
            String left = split2[0];
            String right = split2[1];
            nodes.put(node, new Node(node, left, right));
        }

        int index = 0;
        int count = 0;
        Node cur = nodes.get("AAA");
        while (cur != nodes.get("ZZZ")) {
            if (index >= direction.length()) {
                index = 0;
            }

            if (direction.charAt(index) == 'L') {
                cur = nodes.get(cur.left);
            } else {
                cur = nodes.get(cur.right);
            }

            index++;
            count++;
        }

        System.out.println(count);
    }

    private static void part2(Scanner console) {
        String direction = console.nextLine();
        console.nextLine();
        HashMap<String, Node> nodes = new HashMap<String, Node>();
        while (console.hasNextLine()) {
            String line = console.nextLine();
            String[] split = line.split(" = ");
            String node = split[0];
            String[] split2 = split[1].substring(1, split[1].length() - 1).split(", ");
            String left = split2[0];
            String right = split2[1];
            nodes.put(node, new Node(node, left, right));
        }

        ArrayList<Node> cur = new ArrayList<>();
        for (String node : nodes.keySet()) {
            if (node.endsWith("A")) {
                cur.add(nodes.get(node));
            }
        }

        ArrayList<ArrayList<Integer>> timeWhenVisitedEnd = new ArrayList<>();
        for (Node c : cur) {
            ArrayList<Integer> times = new ArrayList<>();
            timeWhenVisitedEnd.add(times);
            int index = 0;
            int time = 0;
            HashSet<String> visitedSuccessfully = new HashSet<>();
            while (true) {
                if (index == direction.length()) {
                    index = 0;
                }

                if (c.cur.endsWith("Z")) {
                    if (visitedSuccessfully.contains(c.cur + " " + index)) {
                        break;
                    } else {
                        visitedSuccessfully.add(c.cur + " " + index);
                        times.add(time);
                    }
                }

                if (direction.charAt(index) == 'L') {
                    c = nodes.get(c.left);
                } else {
                    c = nodes.get(c.right);
                }

                index++;
                time++;
            }
        }

        System.out.println(timeWhenVisitedEnd);

        long lcm = timeWhenVisitedEnd.get(0).get(0);
        for (int i = 1; i < timeWhenVisitedEnd.size(); i++) {
            lcm = lcm(timeWhenVisitedEnd.get(i).get(0), lcm);
        }

        System.out.println(lcm);
    }

    public static long gcd(long n1, long n2) {
        while (n2 > 0) {
            long temp = n2;
            n2 = n1 % n2;
            n1 = temp;
        }
        return n1;
    }

    public static long lcm(long n1, long n2) {
        if (n1 == 0 || n2 == 0) {
            return 0;
        }
        long gcd = gcd(n1, n2);
        return Math.abs(n1 * n2) / gcd;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String file = "";
        if (RUN_TEST_INPUT) {
            file = TEST_FILES_DIRECTORY + "\\day" + DAY;
        } else {
            file = INPUT_FILES_DIRECTORY + "\\day" + DAY;
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