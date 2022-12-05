package day05;

import util.PuzzleInput;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput("day05");

        Map<Integer, Stack> crates = new HashMap<>();
        initializeStacks(lines, crates);

        moveCratesWithCrateMover9000(lines, crates);

        System.out.println("Message from crates: " + getMessageFromCrates(crates));

        Map<Integer, Stack> crates9001 = new HashMap<>();
        initializeStacks(lines, crates9001);

        moveCratesWithCrateMover9001(lines, crates9001);

        System.out.println("Message from crates - reorderd: " + getMessageFromCrates(crates9001));
    }

    private static String getMessageFromCrates(Map<Integer, Stack> crates) {
        StringBuilder builder = new StringBuilder();

        for (Stack stack : crates.values()) {
            builder.append(stack.getTopCrate());
        }

        return builder.toString();
    }

    private static void initializeStacks(List<String> lines, Map<Integer, Stack> crates) {
        for (int i = 7; i >= 0; i--) {
            String line = lines.get(i);
            int idx = 1;
            for (int s = 1; s < 36; s += 4) {
                if (i == 7) {
                    crates.put(idx, new Stack());
                }
                Stack localStack = crates.get(idx);

                crates.put(idx, localStack.putCrate(line.charAt(s)));
                idx++;
            }
        }
    }

    private static void moveCratesWithCrateMover9000(List<String> lines, Map<Integer, Stack> crates) {
        for (int i = 10; i < lines.size(); i++) {
            String[] actions = lines.get(i).split(" ");
            for (int num = 0; num < Integer.parseInt(actions[1]); num++) {
                Character crate = crates.get(Integer.parseInt(actions[3])).pullCrate();
                crates.get(Integer.parseInt(actions[5])).putCrate(crate);
            }
        }
    }

    private static void moveCratesWithCrateMover9001(List<String> lines, Map<Integer, Stack> crates) {
        for (int i = 10; i < lines.size(); i++) {
            String[] actions = lines.get(i).split(" ");
            List<Character> temporaryStack = new ArrayList<>();
            for (int num = 0; num < Integer.parseInt(actions[1]); num++) {
                Character crate = crates.get(Integer.parseInt(actions[3])).pullCrate();
                temporaryStack.add(crate);
            }
            crates.get(Integer.parseInt(actions[5])).putStack(temporaryStack);
        }
    }
}