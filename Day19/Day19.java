import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Base template for each day's solution
 * Simply toggle the constants to run the correct part and files
 */
public class Day19 {
    private static final String TEST_FILES_DIRECTORY = "tests";
    private static final String INPUT_FILES_DIRECTORY = "inputs";

    private static final String DAY = "19";
    private static final boolean RUN_TEST_INPUT = false;
    private static final boolean RUN_PART_1 = false;

    private static class Rule {
        String ruleName;
        String[] conditions;
        String default_;

        public Rule(String ruleName, String[] conditions, String default_) {
            this.ruleName = ruleName;
            this.conditions = conditions;
            this.default_ = default_;
        }
    }

    private static void part1(Scanner console) {
        ArrayList<Rule> workflows = new ArrayList<>();
        HashMap<String, Rule> rules = new HashMap<>();

        String line = console.nextLine();
        while (line.length() > 0) {
            String[] split = line.split("\\{");

            String ruleName = split[0];
            split[1] = split[1].substring(0, split[1].length() - 1);
            String[] conditions = split[1].split(",");

            String default_ = conditions[conditions.length - 1];
            conditions = Arrays.copyOf(conditions, conditions.length - 1);

            Rule rule = new Rule(ruleName, conditions, default_);
            rules.put(ruleName, rule);
            workflows.add(rule);

            line = console.nextLine();
        }

        long sum = 0;
        while (console.hasNextLine()) {
            line = console.nextLine();
            line = line.substring(1, line.length() - 1);

            String[] values = line.split(",");
            HashMap<String, Integer> values_ = new HashMap<String, Integer>();
            values_.put("x", Integer.parseInt(values[0].split("=")[1]));
            values_.put("m", Integer.parseInt(values[1].split("=")[1]));
            values_.put("a", Integer.parseInt(values[2].split("=")[1]));
            values_.put("s", Integer.parseInt(values[3].split("=")[1]));

            if (isAccepted(values_, workflows, rules, rules.get("in"))) {
                sum += values_.get("x") + values_.get("m") + values_.get("a") + values_.get("s");
            }
        }
        System.out.println(sum);
    }

    private static boolean isAccepted(HashMap<String, Integer> values, ArrayList<Rule> workflows,
            HashMap<String, Rule> rules, Rule cur) {
        for (String condition : cur.conditions) {
            int value = values.get(condition.charAt(0) + "");
            int t = Integer.parseInt(condition.substring(2, condition.indexOf(":")));
            String dest = condition.substring(condition.indexOf(":") + 1);
            if (condition.contains(">")) {
                if (value > t) {
                    if (dest.equals("A")) {
                        return true;
                    } else if (dest.equals("R")) {
                        return false;
                    } else {
                        return isAccepted(values, workflows, rules, rules.get(dest));
                    }
                }
            } else {
                if (value < t) {
                    if (dest.equals("A")) {
                        return true;
                    } else if (dest.equals("R")) {
                        return false;
                    } else {
                        return isAccepted(values, workflows, rules, rules.get(dest));
                    }
                }
            }
        }

        if (cur.default_.equals("A")) {
            return true;
        } else if (cur.default_.equals("R")) {
            return false;
        } else {
            return isAccepted(values, workflows, rules, rules.get(cur.default_));
        }
    }

    private static class Condition {
        int target;
        char operator;
        int value;
        String dest;

        public Condition(int target, char operator, int value, String dest) {
            this.target = target;
            this.operator = operator;
            this.value = value;
            this.dest = dest;
        }
    }

    private static class NewRule {
        String ruleName;
        Condition[] conditions;

        public NewRule(String ruleName, Condition[] conditions) {
            this.ruleName = ruleName;
            this.conditions = conditions;
        }
    }

    private static void part2(Scanner console) {
        ArrayList<NewRule> workflows = new ArrayList<>();
        HashMap<String, NewRule> rules = new HashMap<>();

        String line = console.nextLine();
        while (line.length() > 0) {
            String[] split = line.split("\\{");

            String ruleName = split[0];
            split[1] = split[1].substring(0, split[1].length() - 1);
            String[] conditions = split[1].split(",");

            Condition[] con = new Condition[conditions.length];
            for (int i = 0; i < conditions.length; i++) {
                String condition = conditions[i];
                Condition c = new Condition(' ', ' ', -1, "");
                if (condition.contains(":")) {
                    c.target = switch (condition.charAt(0)) {
                        case 'x' -> 0;
                        case 'm' -> 1;
                        case 'a' -> 2;
                        case 's' -> 3;
                        default -> 4;
                    };
                    c.operator = condition.charAt(1);
                    c.value = Integer.parseInt(condition.substring(2, condition.indexOf(":")));
                    c.dest = condition.substring(condition.indexOf(":") + 1);
                } else {
                    c.dest = condition;
                }

                con[i] = c;
            }

            NewRule rule = new NewRule(ruleName, con);
            rules.put(ruleName, rule);
            workflows.add(rule);

            line = console.nextLine();
        }

        long sum = comb(rules.get("in"), workflows, rules,
                new long[][] { { 1, 4000 }, { 1, 4000 }, { 1, 4000 }, { 1, 4000 } });
        System.out.println(sum);
    }

    private static long sumRanges(long[][] ranges) {
        for (long[] r : ranges) {
            if (r[0] > r[1]) {
                return 0;
            }
        }

        return (ranges[0][1] - ranges[0][0] + 1) * (ranges[1][1] - ranges[1][0] + 1) * (ranges[2][1] - ranges[2][0] + 1)
                * (ranges[3][1] - ranges[3][0] + 1);
    }

    private static long comb(NewRule curRule, ArrayList<NewRule> workflows, HashMap<String, NewRule> rules,
            long[][] ranges) {
        long sum = 0;

        for (long[] r : ranges) {
            if (r[0] > r[1]) {
                return 0;
            }
        }

        for (Condition condition : curRule.conditions) {
            if (condition.value == -1) {
                if (condition.dest.equals("A")) {
                    long add = sumRanges(ranges);
                    sum += add;
                } else if (condition.dest.equals("R")) {

                } else {
                    long add = comb(rules.get(condition.dest), workflows, rules, ranges);
                    sum += add;
                }
            } else {
                long[][] clone = new long[4][2];
                long[][] remaining = new long[4][2];
                for (int i = 0; i < ranges.length; i++) {
                    clone[i] = Arrays.copyOf(ranges[i], ranges[i].length);
                    remaining[i] = Arrays.copyOf(ranges[i], ranges[i].length);
                }

                if (condition.operator == '>') {
                    clone[condition.target][0] = Math.max(clone[condition.target][0], condition.value + 1);
                    remaining[condition.target][1] = Math.min(remaining[condition.target][1], condition.value);
                } else {
                    clone[condition.target][1] = Math.min(clone[condition.target][1], condition.value - 1);
                    remaining[condition.target][0] = Math.max(remaining[condition.target][0], condition.value);
                }

                if (condition.dest.equals("A")) {
                    long add = sumRanges(clone);
                    sum += add;
                } else if (condition.dest.equals("R")) {

                } else {
                    long add = comb(rules.get(condition.dest), workflows, rules, clone);
                    sum += add;
                }
                ranges = remaining;
            }
        }

        return sum;
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