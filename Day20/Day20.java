package Day20;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day20 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "20";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static class Module {
        char type;
        String input;
        TreeSet<String> allInputs;
        String[] outputs;
        boolean isOn;

        public Module(char type, String input, String[] outputs) {
            this.type = type;
            this.input = input;
            this.outputs = outputs;

            this.allInputs = new TreeSet<>();
        }
    }

    private static class Pulse {
        String target;
        boolean isHigh;

        public Pulse(String target, boolean isHigh) {
            this.target = target;
            this.isHigh = isHigh;
        }
    }

    private static void part1(Scanner console) {
        ArrayList<Module> modules = new ArrayList<>();
        HashMap<String, Module> moduleMap = new HashMap<>();
        TreeMap<String, Boolean> initialValues = new TreeMap<>();

        HashMap<String, long[]> previousValues = new HashMap<>();
        long sumLow = 0L;
        long sumHigh = 0L;

        while (console.hasNextLine()) {
            String line = console.nextLine();
            char type = ' ';
            String input = "none";

            if (line.startsWith("broadcaster")) {
                type = 'b';
                input = "broadcaster";
            } else {
                type = line.charAt(0);
                input = line.substring(1, line.indexOf(" ->"));
            }

            String[] outputs = line.substring(line.indexOf("-> ") + 3).split(", ");
            modules.add(new Module(type, input, outputs));
            moduleMap.put(input, modules.get(modules.size() - 1));
        }

        for (Module m : modules) {
            for (String dest : m.outputs) {
                initialValues.put(dest, false);
                if (moduleMap.containsKey(dest) && moduleMap.get(dest).type == '&') {
                    moduleMap.get(dest).allInputs.add(m.input);
                }
            }
            initialValues.put(m.input, false);
        }

        boolean found = false;
        for (long i = 1; i <= 1000; i++) {
            // System.out.println(initialValues);
            System.out.println("=====" + i + "====== " + sumLow + " " + sumHigh);
            if (!found && previousValues.containsKey(initialValues.toString())) {
                long[] prev = previousValues.get(initialValues.toString());
                long prevI = prev[0];
                long prevLow = prev[1];
                long prevHigh = prev[2];

                long cycleLength = i - prevI;
                long numCycles = ((1000L - i + 1) / cycleLength);
                i += numCycles * cycleLength;
                sumLow += numCycles * (sumLow - prevLow);
                sumHigh += numCycles * (sumHigh - prevHigh);

                System.out.println("prevLow " + prevLow);
                System.out.println("prevHigh " + prevHigh);
                System.out.println("cycleLength " + cycleLength);
                System.out.println("numCycles " + numCycles);
                System.out.println("nextI " + i);
                found = true;
            } else {
                previousValues.put(initialValues.toString(), new long[] { i, sumLow, sumHigh });
            }

            ArrayList<Pulse> initialPulses = new ArrayList<>();
            sumLow++;
            for (String next : moduleMap.get("broadcaster").outputs) {
                initialPulses.add(new Pulse(next, false));
                sumLow++;
            }

            while (initialPulses.size() > 0) {
                TreeMap<String, Boolean> nextValues = new TreeMap<>();
                for (String n : initialValues.keySet()) {
                    nextValues.put(n, initialValues.get(n));
                }

                ArrayList<Pulse> nextPulses = new ArrayList<>();
                for (Pulse p : initialPulses) {
                    if (!moduleMap.containsKey(p.target)) {
                        nextValues.put(p.target, p.isHigh);
                        continue;
                    }

                    Module m = moduleMap.get(p.target);
                    if (m.type == '%') {
                        if (!p.isHigh) {
                            m.isOn = !m.isOn;
                            nextValues.put(m.input, m.isOn);
                            for (String dest : m.outputs) {
                                nextPulses.add(new Pulse(dest, m.isOn));
                                // nextValues.put(dest, m.isOn);
                                if (m.isOn) {
                                    sumHigh++;
                                    // System.out.println("%" + p.target + " => " + dest + " becomes high");
                                } else {
                                    sumLow++;
                                    // System.out.println("%" + p.target + " => " + dest + " becomes low");
                                }
                            }
                        } else {
                            nextValues.put(m.input, initialValues.getOrDefault(m.input, false));
                        }
                    } else {
                        boolean and = true;
                        for (String input : m.allInputs) {
                            if (!initialValues.getOrDefault(input, false)) {
                                and = false;
                                break;
                            }
                        }

                        nextValues.put(m.input, !and);
                        for (String dest : m.outputs) {
                            nextPulses.add(new Pulse(dest, !and));
                            // nextValues.put(dest, !and);
                            if (!and) {
                                sumHigh++;
                                // System.out.println("&" + p.target + " => " + dest + " becomes high");
                            } else {
                                sumLow++;
                                // System.out.println("&" + p.target + " => " + dest + " becomes low");
                            }
                        }
                    }
                }

                initialPulses = nextPulses;
                initialValues = nextValues;
                // System.out.println();
            }
        }

        System.out.println(sumLow + "L " + sumHigh + "H");
        System.out.println(sumLow * sumHigh);
    }

    private static void part2(Scanner console) {
        ArrayList<Module> modules = new ArrayList<>();
        HashMap<String, Module> moduleMap = new HashMap<>();
        TreeMap<String, Boolean> initialValues = new TreeMap<>();

        while (console.hasNextLine()) {
            String line = console.nextLine();
            char type = ' ';
            String input = "none";

            if (line.startsWith("broadcaster")) {
                type = 'b';
                input = "broadcaster";
            } else {
                type = line.charAt(0);
                input = line.substring(1, line.indexOf(" ->"));
            }

            String[] outputs = line.substring(line.indexOf("-> ") + 3).split(", ");
            modules.add(new Module(type, input, outputs));
            moduleMap.put(input, modules.get(modules.size() - 1));
        }

        for (Module m : modules) {
            for (String dest : m.outputs) {
                initialValues.put(dest, false);
                if (moduleMap.containsKey(dest) && moduleMap.get(dest).type == '&') {
                    moduleMap.get(dest).allInputs.add(m.input);
                }
            }
            initialValues.put(m.input, false);
        }

        String[] targets = new String[4];
        String output = "";
        for (Module m : modules) {
            if (m.outputs.length > 0 && m.outputs[0].equals("rx")) {
                output = m.input;
            }
        }

        int ti = 0;
        for (Module m : modules) {
            if (m.outputs.length > 0 && m.outputs[0].equals(output)) {
                targets[ti++] = m.input;
            }
        }
        System.out.println(Arrays.toString(targets));

        HashMap<String, ArrayList<Long>> times = new HashMap<>();

        ArrayList<String> print = new ArrayList<>();
        print.add("gn");
        print.add("tn");
        print.add("pf");
        print.add("gd");
        print.add("gc");
        print.add("pv");
        print.add("ps");
        print.add("rf");
        print.add("nm");
        print.add("gt");
        print.add("pp");
        print.add("gv");
        print.add("kb");

        for (long i = 1; i <= 10000; i++) {
            System.out.print("===" + i + "===    {");
            for (String p : print) {
                System.out.print(p + "=" + initialValues.get(p) + ", ");
            }
            System.out.println("}");
            if (times.size() == 4) {
                break;
            }

            ArrayList<Pulse> initialPulses = new ArrayList<>();
            for (String next : moduleMap.get("broadcaster").outputs) {
                initialPulses.add(new Pulse(next, false));
            }

            while (initialPulses.size() > 0) {
                TreeMap<String, Boolean> nextValues = new TreeMap<>();
                for (String n : initialValues.keySet()) {
                    nextValues.put(n, initialValues.get(n));
                }

                for (String s : targets) {
                    if (initialValues.getOrDefault(s, false)) {
                        if (!times.containsKey(s)) {
                            times.put(s, new ArrayList<>());
                        }

                        times.put(s, times.get(s)).add(i);
                    }
                }

                ArrayList<Pulse> nextPulses = new ArrayList<>();
                for (Pulse p : initialPulses) {
                    if (!moduleMap.containsKey(p.target)) {
                        nextValues.put(p.target, p.isHigh);
                        continue;
                    }

                    Module m = moduleMap.get(p.target);
                    if (m.type == '%') {
                        if (!p.isHigh) {
                            m.isOn = !m.isOn;
                            nextValues.put(m.input, m.isOn);
                            for (String dest : m.outputs) {
                                nextPulses.add(new Pulse(dest, m.isOn));
                            }
                        }
                    } else {
                        boolean and = true;
                        for (String input : m.allInputs) {
                            if (!initialValues.getOrDefault(input, false)) {
                                and = false;
                                break;
                            }
                        }

                        nextValues.put(m.input, !and);
                        for (String dest : m.outputs) {
                            nextPulses.add(new Pulse(dest, !and));
                        }
                    }
                }

                initialPulses = nextPulses;
                initialValues = nextValues;
            }
        }

        for (String pieces : times.keySet()) {
            System.out.println(pieces + " " + times.get(pieces));
        }
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