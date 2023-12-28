package Day25;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day25 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "25";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = true;

    private static class Graph {
        private static class Edge {
            String start;
            String end;

            public Edge(String start, String end) {
                this.start = start;
                this.end = end;
            }

            public boolean contains(String vertex) {
                return start.equals(vertex) || end.equals(vertex);
            }

            public void replace(String vertex, String replacement) {
                if (start.equals(vertex)) {
                    start = replacement;
                }
                if (end.equals(vertex)) {
                    end = replacement;
                }
            }

            public String toString() {
                return start + " <--> " + end;
            }
        }

        ArrayList<Edge> edges;
        HashSet<String> vertices;
        HashSet<String> addedEdges;

        HashMap<String, HashSet<String>> contracted;

        public Graph() {
            edges = new ArrayList<>();
            vertices = new HashSet<>();
            addedEdges = new HashSet<>();
            contracted = new HashMap<>();
        }

        public Graph(Graph other) {
            edges = new ArrayList<>();
            for (Edge edge : other.edges) {
                edges.add(new Edge(edge.start, edge.end));
            }
            vertices = new HashSet<>();
            for (String vertex : other.vertices) {
                vertices.add(vertex);
            }

            addedEdges = new HashSet<>();
            contracted = new HashMap<>();
        }

        public void addEdge(String start, String end) {
            vertices.add(start);
            vertices.add(end);

            if (addedEdges.contains(start + "@" + end)) {
                return;
            }
            addedEdges.add(start + "@" + end);
            edges.add(new Edge(start, end));
        }

        public void contractEdge(String start, String end) {
            for (int i = 0; i < edges.size(); i++) {
                if (edges.get(i).contains(end)) {
                    if (edges.get(i).contains(start)) {
                        edges.remove(i);
                        i--;
                    } else {
                        edges.get(i).replace(end, start);
                    }
                }
            }
            vertices.remove(end);

            if (!contracted.containsKey(start)) {
                contracted.put(start, new HashSet<>());
            }
            if (contracted.containsKey(end)) {
                contracted.get(start).addAll(contracted.get(end));
            }
            contracted.get(start).add(end);
            contracted.remove(end);
        }

    }

    private static void part1(Scanner console) {
        Graph g = new Graph();
        HashMap<String, HashSet<String>> vertices = new HashMap<>();
        while (console.hasNextLine()) {
            String line = console.nextLine();
            String[] split = line.split(": ");
            String node = split[0];
            String[] connections = split[1].split(" ");

            if (!vertices.containsKey(node)) {
                vertices.put(node, new HashSet<>());
            }

            for (String connection : connections) {
                g.addEdge(node, connection);
                g.addEdge(connection, node);

                vertices.get(node).add(connection);
                if (!vertices.containsKey(connection)) {
                    vertices.put(connection, new HashSet<>());
                }
                vertices.get(connection).add(node);
            }
        }

        int sum = 0;
        while (true) {
            Graph copy = new Graph(g);

            while (copy.vertices.size() > 2) {
                int i = (int) (Math.random() * copy.edges.size());
                copy.contractEdge(copy.edges.get(i).start, copy.edges.get(i).end);
            }

            int numConnections = copy.edges.size();
            System.out.println("numConnections: " + numConnections);

            if (numConnections == 6) {
                HashSet<String> avoid = new HashSet<>();
                for (Graph.Edge e : copy.edges) {
                    avoid.add(e.start);
                    avoid.add(e.end);
                }

                sum = 1;
                for (String vertex : copy.contracted.keySet()) {
                    sum *= copy.contracted.get(vertex).size() + 1;
                }

                break;
            }
        }
        System.out.println(sum);
    }

    private static void part2(Scanner console) {

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