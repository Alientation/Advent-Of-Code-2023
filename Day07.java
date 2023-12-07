import java.util.ArrayList;
import java.util.Arrays;
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
    private static final String TEST_FILES_DIRECTORY = "files\\tests";
    private static final String INPUT_FILES_DIRECTORY = "files\\inputs";

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
            int type = -1;
            HashMap<Character, Integer> counts = new HashMap<>();
            for (char c : hand) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
            }
            int max = 0;
            for (char c : counts.keySet()) {
                max = Math.max(max, counts.get(c));
            }

            if (max == 5) {
                type = 7;
            } else if (max == 4) {
                type = 6;
            } else if (max == 3 && counts.size() == 2) {
                type = 5;
            } else if (max == 3 && counts.size() == 3) {
                type = 3;
            } else if (max == 2 && counts.size() == 3) {
                type = 2;
            } else if (max == 2 && counts.size() == 4) {
                type = 1;
            } else {
                type = 0;
            }
            int bid = console.nextInt();

            hands.add(new Hand(bid, hand, type));
            console.nextLine();
        }
        for (Hand hand : hands) {
            System.out.println(hand.bid + " " + hand.type + " " + Arrays.toString(hand.cards));
        }

        String val = "AKQJT98765432";

        Collections.sort(hands, new Comparator<Hand>() {
            @Override
            public int compare(Hand o1, Hand o2) {
                if (o1.type != o2.type) {
                    return o2.type - o1.type;
                } else {
                    for (int i = 0; i < o1.cards.length; i++) {
                        if (o1.cards[i] != o2.cards[i]) {
                            return val.indexOf(o1.cards[i]) - val.indexOf(o2.cards[i]);
                        }
                    }
                }
                return 0;
            }
        });

        int tot = 0;
        for (int i = 0; i < hands.size(); i++) {
            tot += (hands.size() - i) * hands.get(i).bid;
        }
        System.out.println(tot);
    }

    private static void part2(Scanner console) {
        ArrayList<Hand> hands = new ArrayList<Hand>();
        while (console.hasNextLine()) {
            char[] hand = console.next().toCharArray();
            int type = -1;
            HashMap<Character, Integer> counts = new HashMap<>();
            for (char c : hand) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
            }
            int max = 0;
            for (char c : counts.keySet()) {
                if (c == 'J') {
                    continue;
                }
                max = Math.max(max, counts.get(c));
            }

            int countJ = counts.getOrDefault('J', 0);
            int size = counts.size() - (countJ == 0 ? 0 : 1);
            // System.out.println("J: " + countJ + " | max: " + max);
            if (max + countJ == 5) {
                type = 7;
            } else if (max + countJ == 4) {
                type = 6;
            } else if (max <= 3 && max + countJ >= 3 && size <= 2 && size + (max + countJ) - 3 >= 2) {
                type = 5;
            } else if (max <= 3 && max + countJ >= 3 && size <= 3 && size + (max + countJ) - 3 >= 3) {
                type = 3;
            } else if (max <= 2 && max + countJ >= 2 && size <= 3 && size + (max + countJ) - 2 >= 3) {
                type = 2;
            } else if (max <= 2 && max + countJ >= 2 && size <= 4 && size + (max + countJ) - 2 >= 4) {
                type = 1;
            } else {
                type = 0;
            }
            int bid = console.nextInt();

            hands.add(new Hand(bid, hand, type));
            console.nextLine();
        }

        // for (Hand hand : hands) {
        // System.out.println(hand.bid + " " + hand.type + " " +
        // Arrays.toString(hand.cards));
        // }

        String val = "AKQT98765432J";

        Collections.sort(hands, new Comparator<Hand>() {
            @Override
            public int compare(Hand o1, Hand o2) {
                if (o1.type != o2.type) {
                    return o2.type - o1.type;
                } else {
                    for (int i = 0; i < o1.cards.length; i++) {
                        if (o1.cards[i] != o2.cards[i]) {
                            return val.indexOf(o1.cards[i]) - val.indexOf(o2.cards[i]);
                        }
                    }
                }
                return 0;
            }
        });

        int tot = 0;
        for (int i = 0; i < hands.size(); i++) {
            tot += (hands.size() - i) * hands.get(i).bid;
        }
        System.out.println(tot);
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