package Day04;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.ArrayList;

public class Day04 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "04";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = true;

    private static void part1(Scanner console) {
        int sumPts = 0;

        // read in each card
        while (console.hasNextLine()) {
            console.next(); // 'Card'
            console.next(); // '#:'

            // read in the winning numbers
            HashSet<Integer> winningNumbers = new HashSet<>();
            while (console.hasNextInt()) {
                winningNumbers.add(console.nextInt());
            }
            console.next(); // '|'

            // read in card numbers
            int countMatches = 0; // how many winning numbers are on the card
            while (console.hasNextInt()) {
                int n = console.nextInt();
                if (winningNumbers.contains(n)) {
                    countMatches++;
                }
            }

            // calculate points earned
            sumPts += (1 << countMatches) >> 1;
        }

        System.out.println(sumPts);
    }

    private static void part2(Scanner console) {
        ArrayList<Integer> cardCounts = new ArrayList<>();
        int curCard = 0;

        // for each card
        while (console.hasNextLine()) {
            if (curCard == cardCounts.size()) {
                cardCounts.add(1); // default one card
            }

            console.next(); // 'Card'
            console.next(); // '#:'

            // read in winning card numbers
            HashSet<Integer> winningNumbers = new HashSet<>();
            while (console.hasNextInt()) {
                winningNumbers.add(console.nextInt());
            }
            console.next(); // '|'

            int countMatches = 0;
            while (console.hasNextInt()) {
                int n = console.nextInt();
                if (winningNumbers.contains(n)) {
                    countMatches++;
                }
            }

            // add duplicate cards
            for (int nextCard = curCard + 1; nextCard < curCard + 1 + countMatches; nextCard++) {
                if (nextCard >= cardCounts.size()) {
                    cardCounts.add(cardCounts.get(curCard) + 1); // haven't populated yet
                } else {
                    cardCounts.set(nextCard, cardCounts.get(nextCard) + cardCounts.get(curCard));
                }
            }

            curCard++;
        }

        int sumCards = 0;
        for (int cardCount : cardCounts) {
            sumCards += cardCount;
        }
        System.out.println(sumCards);
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