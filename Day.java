import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Day {
    private static final String TEST_FILES_DIRECTORY = "files\\tests";
    private static final String INPUT_FILES_DIRECTORY = "files\\inputs";

    private static final String DAY = "";
    private static final boolean RUN_TEST_INPUT = true;
    private static final boolean RUN_PART_1 = true;

    private static void part1(Scanner console) {
        
    }

    private static void part2(Scanner console) {
        
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