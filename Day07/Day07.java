package Day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day07 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "07";
    private static final boolean RUN_TEST_INPUT = true;
    private static final boolean RUN_PART_1 = false;

    private static class Hand {
        char[] cards;
        int type;
        int bid;

        public Hand(int bid, char[] cards, int type) {
            this.cards = cards;
            this.type = type;
            this.bid = bid;
        }
    }

    private static void part1(Scanner console) {
        ArrayList<Hand> hands = new ArrayList<Hand>();
        while (console.hasNextLine()) {
            char[] hand = console.next().toCharArray();
            int handType = -1;
            HashMap<Character, Integer> counts = new HashMap<>();
            for (char c : hand) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
            }
            int maxCardCount = 0;
            for (char c : counts.keySet()) {
                maxCardCount = Math.max(maxCardCount, counts.get(c));
            }

            if (maxCardCount == 5) { // five of a kind
                handType = 7;
            } else if (maxCardCount == 4) { // four of a kind
                handType = 6;
            } else if (maxCardCount == 3 && counts.size() == 2) { // full house
                handType = 5;
            } else if (maxCardCount == 3 && counts.size() == 3) { // three of a kind
                handType = 3;
            } else if (maxCardCount == 2 && counts.size() == 3) { // two pair
                handType = 2;
            } else if (maxCardCount == 2 && counts.size() == 4) { // one pair
                handType = 1;
            } else { // high card
                handType = 0;
            }
            int bid = console.nextInt();

            hands.add(new Hand(bid, hand, handType));
            console.nextLine();
        }

        String val = "AKQJT98765432"; // order of cards

        Collections.sort(hands, new Comparator<Hand>() {
            @Override
            public int compare(Hand h1, Hand h2) {
                if (h1.type != h2.type) {
                    return h2.type - h1.type; // sort by highest hand type
                } else {
                    for (int i = 0; i < h1.cards.length; i++) {
                        if (h1.cards[i] != h2.cards[i]) {
                            // sort by highest card type (closest to the left in the order of cards)
                            return val.indexOf(h1.cards[i]) - val.indexOf(h2.cards[i]);
                        }
                    }
                }

                return 0; // equal hands
            }
        });

        // sum of bids
        int sum = 0;
        for (int i = 0; i < hands.size(); i++) {
            sum += (hands.size() - i) * hands.get(i).bid;
        }
        System.out.println(sum);
    }

    private static void part2(Scanner console) {
        ArrayList<Hand> hands = new ArrayList<Hand>();
        while (console.hasNextLine()) {
            char[] hand = console.next().toCharArray();
            int handType = -1;
            HashMap<Character, Integer> counts = new HashMap<>();
            for (char c : hand) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
            }
            int maxCardCount = 0;
            for (char c : counts.keySet()) {
                if (c == 'J') {
                    continue;
                }
                maxCardCount = Math.max(maxCardCount, counts.get(c));
            }

            int countJ = counts.getOrDefault('J', 0);
            int sizeExcludingJ = counts.size() - (countJ == 0 ? 0 : 1);

            int maxCardCountWithJ = maxCardCount + countJ; // max card count if J becomes the card
            int maxUniqueCardsWithJ = sizeExcludingJ + (maxCardCount + countJ);

            if (maxCardCountWithJ == 5) { // five of a kind
                handType = 7;
            } else if (maxCardCountWithJ == 4) { // four of a kind
                handType = 6;
            } else if (maxCardCount <= 3 && maxCardCountWithJ >= 3 &&
                    sizeExcludingJ <= 2 && maxUniqueCardsWithJ - 3 >= 2) { // full house
                handType = 5;
            } else if (maxCardCount <= 3 && maxCardCountWithJ >= 3 &&
                    sizeExcludingJ <= 3 && maxUniqueCardsWithJ - 3 >= 3) { // three of a kind
                handType = 3;
            } else if (maxCardCount <= 2 && maxCardCountWithJ >= 2 &&
                    sizeExcludingJ <= 3 && maxUniqueCardsWithJ - 2 >= 3) { // two pair
                handType = 2;
            } else if (maxCardCount <= 2 && maxCardCountWithJ >= 2 &&
                    sizeExcludingJ <= 4 && maxUniqueCardsWithJ - 2 >= 4) { // one pair
                handType = 1;
            } else { // high card
                handType = 0;
            }
            int bid = console.nextInt();

            hands.add(new Hand(bid, hand, handType));
            console.nextLine();
        }

        String val = "AKQJT98765432"; // order of cards

        Collections.sort(hands, new Comparator<Hand>() {
            @Override
            public int compare(Hand h1, Hand h2) {
                if (h1.type != h2.type) {
                    return h2.type - h1.type; // sort by highest hand type
                } else {
                    for (int i = 0; i < h1.cards.length; i++) {
                        if (h1.cards[i] != h2.cards[i]) {
                            // sort by highest card type (closest to the left in the order of cards)
                            return val.indexOf(h1.cards[i]) - val.indexOf(h2.cards[i]);
                        }
                    }
                }

                return 0; // equal hands
            }
        });

        // sum of bids
        int sum = 0;
        for (int i = 0; i < hands.size(); i++) {
            sum += (hands.size() - i) * hands.get(i).bid;
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