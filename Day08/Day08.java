package Day08;

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
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

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
        // construct graph
        while (console.hasNextLine()) { // ugly ugly and ugly
            String line = console.nextLine();
            String[] nodeInfo = line.split(" = ");
            String name = nodeInfo[0];
            String[] children = nodeInfo[1].substring(1, nodeInfo[1].length() - 1).split(", ");
            String leftChild = children[0];
            String rightChild = children[1];
            nodes.put(name, new Node(name, leftChild, rightChild));
        }

        // traverse graph
        int dirIndex = 0;
        int steps = 0;
        Node cur = nodes.get("AAA");

        // while we haven't reached the end
        while (cur != nodes.get("ZZZ")) {
            if (dirIndex >= direction.length()) {
                dirIndex = 0; // loop back around
            }

            if (direction.charAt(dirIndex) == 'L') {
                cur = nodes.get(cur.left);
            } else {
                cur = nodes.get(cur.right);
            }

            dirIndex++;
            steps++;
        }

        System.out.println(steps);
    }

    private static void part2(Scanner console) {
        String direction = console.nextLine();
        console.nextLine();
        HashMap<String, Node> nodes = new HashMap<String, Node>();
        // construct graph
        while (console.hasNextLine()) { // ugly ugly and ugly
            String line = console.nextLine();
            String[] nodeInfo = line.split(" = ");
            String name = nodeInfo[0];
            String[] children = nodeInfo[1].substring(1, nodeInfo[1].length() - 1).split(", ");
            String leftChild = children[0];
            String rightChild = children[1];
            nodes.put(name, new Node(name, leftChild, rightChild));
        }

        // add all starting nodes
        ArrayList<Node> startNodes = new ArrayList<>();
        for (String nodeName : nodes.keySet()) {
            if (nodeName.endsWith("A")) {
                startNodes.add(nodes.get(nodeName));
            }
        }

        ArrayList<ArrayList<Integer>> timesWhenVisitedEnd = new ArrayList<>();
        for (Node c : startNodes) {
            ArrayList<Integer> times = new ArrayList<>();
            timesWhenVisitedEnd.add(times);
            int dirIndex = 0;
            int time = 0;
            HashSet<String> visitedSuccessfully = new HashSet<>();
            while (true) {
                if (dirIndex == direction.length()) {
                    dirIndex = 0; // loop back around
                }

                // reached end node
                if (c.cur.endsWith("Z")) {

                    if (visitedSuccessfully.contains(c.cur + " " + dirIndex)) {
                        // already visited before at the current index in the directions
                        // therefore from now on it will simply loop around
                        break;
                    } else {
                        // first time visiting this node at this index in the directions
                        visitedSuccessfully.add(c.cur + " " + dirIndex);
                        times.add(time);
                    }
                }

                if (direction.charAt(dirIndex) == 'L') {
                    c = nodes.get(c.left);
                } else {
                    c = nodes.get(c.right);
                }

                dirIndex++;
                time++;
            }
        }

        System.out.println(timesWhenVisitedEnd);

        // this is sketchy and only works for inputs like the test input...
        // the test input lets us just take the lcm of the first time we visit the end,
        // since there is only ever one time we visit the end in a full cycle.
        long lcm = timesWhenVisitedEnd.get(0).get(0);
        for (int i = 1; i < timesWhenVisitedEnd.size(); i++) {
            lcm = lcm(timesWhenVisitedEnd.get(i).get(0), lcm);
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