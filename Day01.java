import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Day01 {
    private static final String TEST_FILES_DIRECTORY = "files\\tests";
    private static final String INPUT_FILES_DIRECTORY = "files\\inputs";

    private static final String DAY = "01"; // FILL IN DAY NUMBER
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        int sum = 0;

        // each line
        while (console.hasNextLine()){
            String line = console.nextLine();

            // find first digit
            for (int i = 0; i < line.length(); i++) {
                if (Character.isDigit(line.charAt(i))) {
                    sum += 10 * (line.charAt(i) - '0'); // ten's digit
                    break;
                }
            }

            // find last digit
            for (int i = line.length() - 1; i >= 0; i--) {
                if (Character.isDigit(line.charAt(i))) {
                    sum += (line.charAt(i) - '0'); // one's digit
                    break;
                }
            }
        }

        System.out.println(sum);
    }

    private static void part2(Scanner console) {
        int sum = 0;

        String[] map = new String[] {
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
        };

        // each line
        while (console.hasNextLine()){
            String line = console.nextLine();

            // replace first occurences of spelled out numbers with respective digit
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                boolean found = false;
                for (int j = 0; j < map.length; j++) {
                    if (line.startsWith(map[j], i)) {
                        sb.append('0' + (j + 1));
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    sb.append(line.charAt(i));
                }
            }

            // same parse as before
            String s = sb.toString();
            for (int i = 0; i < s.length(); i++) {
                if (Character.isDigit(s.charAt(i))) {
                    sum += 10 * (s.charAt(i) - '0');
                    break;
                }
            }

            for (int i = s.length() - 1; i >= 0; i--) {
                if (Character.isDigit(s.charAt(i))) {
                    sum += (s.charAt(i) - '0');
                    break;
                }
            }
        }

        System.out.println(sum);
    }


    public static void main(String[] args) throws FileNotFoundException {
        String file = "";
        if (RUN_TEST_INPUT) {
            file = TEST_FILES_DIRECTORY + "\\day"+ DAY;
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