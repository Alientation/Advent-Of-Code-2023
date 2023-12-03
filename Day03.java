import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Day03 {
    private static final String TEST_FILES_DIRECTORY = "files\\tests";
    private static final String INPUT_FILES_DIRECTORY = "files\\inputs";

    private static final String DAY = "03";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        ArrayList<String> lines = new ArrayList<String>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }
        
        // possible directions numbers could be relative to a symbol
        int[][] directions = new int[][] {
            {0,1}, {1,0}, {0,-1}, {-1,0}, {-1,-1}, {1,1}, {-1,1}, {1,-1}
        };

        int sum = 0;
        boolean[][] mark = new boolean[lines.size()][lines.get(0).length()]; // already parsed numbers
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                char c = lines.get(i).charAt(j);

                if (Character.isDigit(c) || c == '.') {
                    continue; // skip digits and dots
                }

                // in all directions from the symbol, add number if not visited
                for (int[] dir : directions) {
                    int newI = i + dir[0];
                    int newJ = j + dir[1];
                    if (newI >= 0 && newI < lines.size() && newJ >= 0 && newJ < lines.get(i).length()) {
                        char newC = lines.get(newI).charAt(newJ);
                        if (Character.isDigit(newC) && !mark[newI][newJ]) {
                            sum += extractNumber(mark[newI], lines.get(newI), newJ);
                        }
                    }
                }
            }
        }

        System.out.println(sum);
    }

    private static int extractNumber(boolean[] mark, String line, int index) {
        // finds the left most digit of the number
        while (index >= 0 && Character.isDigit(line.charAt(index))) {
            mark[index] = true;
            index--;
        }
        index++;

        // extracts the number starting from the left most digit
        int num = 0;
        for (; index < line.length() && Character.isDigit(line.charAt(index)); index++) {
            num = num * 10 + (line.charAt(index) - '0');
            mark[index] = true;
        }
        return num;
    }

    private static void part2(Scanner console) {
        ArrayList<String> lines = new ArrayList<String>();
        while (console.hasNextLine()) {
            lines.add(console.nextLine());
        }

        int[][] directions = new int[][] {
            {0,1}, {1,0}, {0,-1}, {-1,0}, {-1,-1}, {1,1}, {-1,1}, {1,-1}
        };

        int sum = 0;
        boolean[][] mark = new boolean[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                char c = lines.get(i).charAt(j);
                if (c != '*') {
                    continue; // skip non gear symbols
                }

                int possiblePower = 1;
                int found = 0;

                // in all directions of gear, find numbers
                for (int[] dir : directions) {
                    int newI = i + dir[0];
                    int newJ = j + dir[1];
                    if (newI >= 0 && newI < lines.size() && newJ >= 0 && newJ < lines.get(i).length()) {
                        char newC = lines.get(newI).charAt(newJ);
                        if (Character.isDigit(newC) && !mark[newI][newJ]) {
                            possiblePower *= extractNumber(mark[newI], lines.get(newI), newJ);
                            found++;
                            if (found > 2) {
                                break; // found more than 2 neighboring numbers
                            }
                        }
                    }
                }

                // if found only 2 neighboring numbers, add power to sum
                if (found == 2) {
                    sum += possiblePower;
                }
            }
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