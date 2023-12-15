
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day15 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "15";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        String[] strings = console.nextLine().split(",");
        int sum = 0;
        for (String string : strings) {
            int hash = 0;
            for (char c : string.toCharArray()) {
                if (c == '\n') {
                    continue;
                }
                hash += c;
                hash *= 17;
                hash %= 256;
            }
            sum += hash;
        }
        System.out.println(sum);
    }

    private static class Pair {
        String key;
        int hash;
        int value;

        public Pair(String key, int hash, int value) {
            this.key = key;
            this.hash = hash;
            this.value = value;
        }
    }

    private static void part2(Scanner console) {
        String[] strings = console.nextLine().split(",");
        HashMap<Integer, ArrayList<Pair>> map = new HashMap<>();
        for (String string : strings) {
            String word = "";
            boolean isAdd = true;
            if (string.contains("=")) {
                word = string.substring(0, string.indexOf("="));
            } else {
                isAdd = false;
                word = string.substring(0, string.indexOf("-"));
            }

            int hash = 0;
            for (char c : word.toCharArray()) {
                if (c == '\n') {
                    continue;
                }
                hash += c;
                hash *= 17;
                hash %= 256;
            }

            if (isAdd && map.containsKey(hash)) {
                boolean exists = false;
                for (Pair p : map.get(hash)) {
                    if (p.key.equals(word)) {
                        exists = true;
                        p.value = Integer.parseInt(string.substring(string.indexOf("=") + 1));
                        break;
                    }
                }

                if (!exists) {
                    map.get(hash)
                            .add(new Pair(word, hash, Integer.parseInt(string.substring(string.indexOf("=") + 1))));
                }
            } else if (isAdd) {
                ArrayList<Pair> list = new ArrayList<>();
                list.add(new Pair(word, hash, Integer.parseInt(string.substring(string.indexOf("=") + 1))));
                map.put(hash, list);
            } else {
                for (Pair p : map.getOrDefault(hash, new ArrayList<>())) {
                    if (p.key.equals(word)) {
                        map.get(hash).remove(p);
                        break;
                    }
                }
            }
        }

        int power = 0;
        for (ArrayList<Pair> list : map.values()) {
            int index = 1;
            for (Pair p : list) {
                int mult = 1 + p.hash;
                mult *= index;
                mult *= p.value;
                power += mult;
                index++;
            }
        }
        System.out.println(power);
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