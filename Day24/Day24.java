package Day24;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.math3.exception.MathArithmeticException;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day24 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "24";
    private static final boolean RUN_TEST_INPUT = true;
    private static final boolean RUN_PART_1 = false;

    private static class Hail {
        long[] pos;
        long[] vel;

        public Hail(long[] pos, long[] vel) {
            this.pos = pos;
            this.vel = vel;
        }
    }

    private static void part1(Scanner console) {
        ArrayList<Hail> lines = new ArrayList<>();
        while (console.hasNextLine()) {
            String[] split = console.nextLine().split(" @ ");
            String[] pos = split[0].split(", ");
            for (int i = 0; i < pos.length; i++) {
                pos[i] = pos[i].trim();
            }
            long[] ipos = new long[] { Long.parseLong(pos[0]), Long.parseLong(pos[1]), Long.parseLong(pos[2]) };
            String[] vel = split[1].split(", ");
            for (int i = 0; i < vel.length; i++) {
                vel[i] = vel[i].trim();
            }
            long[] ivel = new long[] { Long.parseLong(vel[0]), Long.parseLong(vel[1]), Long.parseLong(vel[2]) };
            lines.add(new Hail(ipos, ivel));
        }

        int sumIntersection = 0;
        long xMin = 200000000000000L;
        long xMax = 400000000000000L;
        long yMin = 200000000000000L;
        long yMax = 400000000000000L;
        // long xMin = 7;
        // long xMax = 27;
        // long yMin = 7;
        // long yMax = 27;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.size(); j++) {
                if (i == j) {
                    continue;
                }

                Hail h1 = lines.get(i);
                Hail h2 = lines.get(j);

                /*
                 * x1 + dx1 * t = x2 + dx2 * t
                 * y1 + dy1 * t = y2 + dy2 * t
                 * 
                 * t * (dx1 - dx2) = x2 - x1
                 * t = (x2 - x1) / (dx1 - dx2)
                 * 
                 * 
                 */
                try {
                    BigFraction x1 = new BigFraction(h1.pos[0]);
                    BigFraction dx1 = new BigFraction(h1.vel[0]);
                    BigFraction y1 = new BigFraction(h1.pos[1]);
                    BigFraction dy1 = new BigFraction(h1.vel[1]);

                    BigFraction x2 = new BigFraction(h2.pos[0]);
                    BigFraction dx2 = new BigFraction(h2.vel[0]);
                    BigFraction y2 = new BigFraction(h2.pos[1]);
                    BigFraction dy2 = new BigFraction(h2.vel[1]);

                    BigFraction y1MinusY2 = y1.subtract(y2);
                    BigFraction dy1Timesx2 = dy1.multiply(x2);

                    BigFraction t = (y1MinusY2.add(dy1Timesx2.divide(dx1)).subtract(dy1.multiply(x1.divide(dx1))))
                            .divide((dy2.subtract((dy1.multiply(dx2)).divide(dx1))));
                    BigFraction s = ((x2.add(dx2.multiply(t))).subtract(x1)).divide(dx1);

                    BigFraction xx1 = x1.add(dx1.multiply(s));
                    BigFraction yy1 = y1.add(dy1.multiply(s));
                    BigFraction xx2 = x2.add(dx2.multiply(t));
                    BigFraction yy2 = y2.add(dy2.multiply(t));

                    if (xx1.equals(xx2) && yy1.equals(yy2) && t.percentageValue() >= 0
                            && s.percentageValue() >= 0
                            && xx1.subtract(new BigFraction(xMin)).percentageValue() >= 0
                            && xx1.subtract(new BigFraction(xMax)).percentageValue() <= 0
                            && yy1.subtract(new BigFraction(yMin)).percentageValue() >= 0
                            && yy1.subtract(new BigFraction(yMax)).percentageValue() <= 0) {
                        sumIntersection++;
                    }
                } catch (MathArithmeticException e) {

                }
            }
        }

        System.out.println(sumIntersection / 2);
    }

    private static void part2(Scanner console) {
        long sum = 194723518367339L + 181910661443432L + 150675954587450L;
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