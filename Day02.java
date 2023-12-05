import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Day02 {
    private static final String TEST_FILES_DIRECTORY = "files\\tests";
    private static final String INPUT_FILES_DIRECTORY = "files\\inputs";

    private static final String DAY = "02";
    private static final boolean RUN_TEST_INPUT = true;
    private static final boolean RUN_PART_1 = true;

    private static void part1(Scanner console) {
        int sum = 0;

        // each game
        while (console.hasNextLine()) {
            String line = console.nextLine();
            if (line.length() == 0) {
                break;
            }

            // extract information about the game
            String[] game = line.split(": ");
            int gameID = Integer.parseInt(game[0].split(" ")[1]);
            String[] rounds = game[1].split("; ");

            // check if game was possible
            boolean possible = true;
            for (String round : rounds) {
                String[] ballsShown = round.split(", ");

                int b = 0;
                int r = 0;
                int g = 0;

                // count number of balls shown of each color in this round
                for (String s : ballsShown) {
                    String[] color = s.split(" ");
                    int n = Integer.parseInt(color[0]);
                    if (color[1].equals("red")) {
                        r += n;
                    } else if (color[1].equals("blue")) {
                        b += n;
                    } else if (color[1].equals("green")) {
                        g += n;
                    }
                }

                // if exceeds the limit, game is not possible
                if (r > 12 || g > 13 || b > 14) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                sum += gameID;
            }
        }

        System.out.println(sum);
    }

    private static void part2(Scanner console) {
        long sum = 0;

        // for each game
        while (console.hasNextLine()) {
            String line = console.nextLine();
            if (line.length() == 0) {
                break;
            }

            // extract game info
            String[] game = line.split(": ");
            String[] rounds = game[1].split("; ");

            int b = 0;
            int r = 0;
            int g = 0;

            // find maximum number of balls shown of each color
            // this is the minimum number of balls of each color that exists in the bag
            for (String round : rounds) {
                String[] ballsShown = round.split(", ");

                for (String s : ballsShown) {
                    String[] color = s.split(" ");
                    int n = Integer.parseInt(color[0]);
                    if (color[1].equals("red")) {
                        r = Math.max(r, n);
                    } else if (color[1].equals("blue")) {
                        b = Math.max(b, n);
                    } else if (color[1].equals("green")) {
                        g = Math.max(g, n);
                    }
                }
            }

            sum += r * b * g;
        }

        System.out.println(sum);
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