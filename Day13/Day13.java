package Day13;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day13 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "13";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static void part1(Scanner console) {
        int sum = 0;
        while (console.hasNextLine()) {
            ArrayList<String> lines = new ArrayList<>();
            String line = console.nextLine();
            while (console.hasNextLine() && line.length() > 0) {
                lines.add(line);
                line = console.nextLine();
            }

            ArrayList<String> cols = new ArrayList<>();
            for (int i = 0; i < lines.get(0).length(); i++) {
                String col = "";
                for (int j = 0; j < lines.size(); j++) {
                    col += lines.get(j).charAt(i);
                }
                cols.add(col);
            }

            int sumRows = 0;
            for (int aboveRows = 0; aboveRows < lines.size() - 1; aboveRows++) {
                boolean isReflection = true;
                int belowRows = aboveRows + 1;
                for (int distance = 0; aboveRows - distance >= 0 && belowRows + distance < lines.size(); distance++) {
                    if (!lines.get(aboveRows - distance).equals(lines.get(belowRows + distance))) {
                        isReflection = false;
                        break;
                    }
                }

                if (isReflection) {
                    sumRows += aboveRows + 1;
                }
            }
            System.out.println(sumRows);

            int sumCols = 0;
            for (int leftCols = 0; leftCols < cols.size() - 1; leftCols++) {
                boolean isReflection = true;
                int rightCols = leftCols + 1;
                for (int distance = 0; leftCols - distance >= 0 && rightCols + distance < cols.size(); distance++) {
                    if (!cols.get(leftCols - distance).equals(cols.get(rightCols + distance))) {
                        isReflection = false;
                        break;
                    }
                }

                if (isReflection) {
                    sumCols += leftCols + 1;
                }
            }
            System.out.println(sumCols);
            sum += sumCols + 100 * sumRows;
        }
        System.out.println(sum);
    }

    private static void part2(Scanner console) {
        int sum = 0;
        while (console.hasNextLine()) {
            ArrayList<String> lines = new ArrayList<>();
            String line = console.nextLine();
            while (console.hasNextLine() && line.length() > 0) {
                lines.add(line);
                line = console.nextLine();
            }

            ArrayList<String> cols = new ArrayList<>();
            for (int i = 0; i < lines.get(0).length(); i++) {
                String col = "";
                for (int j = 0; j < lines.size(); j++) {
                    col += lines.get(j).charAt(i);
                }
                cols.add(col);
            }

            int sumRows = 0;
            for (int aboveRows = 0; aboveRows < lines.size() - 1; aboveRows++) {
                int belowRows = aboveRows + 1;
                int difference = 0;
                for (int distance = 0; aboveRows - distance >= 0 && belowRows + distance < lines.size(); distance++) {
                    for (int i = 0; i < lines.get(aboveRows - distance).length(); i++) {
                        if (lines.get(aboveRows - distance).charAt(i) != lines.get(belowRows + distance).charAt(i)) {
                            difference++;
                        }
                    }
                }

                if (difference == 1) {
                    sumRows += aboveRows + 1;
                    break;
                }
            }
            System.out.println(sumRows);

            int sumCols = 0;
            for (int leftCols = 0; leftCols < cols.size() - 1; leftCols++) {
                if (sumRows != 0) {
                    break;
                }

                int difference = 0;
                int rightCols = leftCols + 1;
                for (int distance = 0; leftCols - distance >= 0 && rightCols + distance < cols.size(); distance++) {
                    for (int i = 0; i < cols.get(leftCols - distance).length(); i++) {
                        if (cols.get(leftCols - distance).charAt(i) != cols.get(rightCols + distance).charAt(i)) {
                            difference++;
                        }
                    }
                }

                if (difference == 1) {
                    sumCols += leftCols + 1;
                    break;
                }
            }
            System.out.println(sumCols);
            sum += sumCols + 100 * sumRows;
        }
        System.out.println(sum);
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