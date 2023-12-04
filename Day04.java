import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.ArrayList;

public class Day04 {
    private static final String TEST_FILES_DIRECTORY = "files\\tests";
    private static final String INPUT_FILES_DIRECTORY = "files\\inputs";

    private static final String DAY = "04";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        int sum = 0;
        while (console.hasNextLine()) {
            console.next();
            console.next();
            HashSet<Integer> win = new HashSet<>();
            while (console.hasNextInt()) {
                win.add(console.nextInt());
            }
            console.next();
            
            int c = 1;
            while (console.hasNextInt()) {
                int n = console.nextInt();
                if (win.contains(n)) {
                    c *= 2;
                }
            }

            sum += c / 2;
        }

        System.out.println(sum);
    }

    private static void part2(Scanner console) {
        ArrayList<Integer> s = new ArrayList<>();
        int cur = 0;
        while (console.hasNextLine()) {
            if (s.size() < cur + 1) {
                s.add(1);
            }

            console.next();
            console.next();
            HashSet<Integer> win = new HashSet<>();
            while (console.hasNextInt()) {
                win.add(console.nextInt());
            }
            console.next();

            int count = 0;
             while (console.hasNextInt()) {
                int n = console.nextInt();
                if (win.contains(n)) {
                    count++;
                }
            }

            for (int i = cur + 1; i < cur + 1 + count; i++) {
                if (i >= s.size()) {
                    s.add(s.get(cur) + 1);
                } else {
                    s.set(i, s.get(i) + s.get(cur));
                }
            }

            cur++;
        }
        
        int sum = 0;
        for (int i : s) {
            sum += i;
        }
        System.out.println(sum);
    }


    public static void main(String[] args) throws FileNotFoundException {
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
        

        Scanner console = new Scanner(new FileReader(file));
        
        if (RUN_PART_1) {
            part1(console);
        } else {
            part2(console);
        }
        console.close();
    }
}