package day11;

import util.PuzzleInput;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput("day11");
        // List<String> lines = PuzzleInput.getTestInput("day11");

        List<Monkey> monkeys = new ArrayList<>();
        initializeMonkeys(lines, monkeys);
        int rounds = 20;
        play(monkeys, rounds, 3L);
        Collections.sort(monkeys);
        System.out.println("Level of monkey business: " + monkeys.get(0).getInspectedItems()
                * monkeys.get(1).getInspectedItems());

        monkeys = new ArrayList<>();
        initializeMonkeys(lines, monkeys);
        rounds = 10000;
        play(monkeys, rounds, 1L);
        Collections.sort(monkeys);
        System.out.println("Level of monkey business: " + monkeys.get(0).getInspectedItems().longValue()
                * monkeys.get(1).getInspectedItems().longValue());
    }

    private static void initializeMonkeys(List<String> lines, List<Monkey> monkeys) {
        for (int i = 0; i < lines.size(); i += 7) {
            List<Long> items = getItems(lines.get(i + 1));
            Operation operation = getOperation(lines.get(i + 2));
            Integer divisableBy = Integer.parseInt(lines.get(i + 3).substring(21));
            Integer monkeyIfTrue = Integer.parseInt(lines.get(i + 4).substring(29));
            Integer monkeyIfFalse = Integer.parseInt(lines.get(i + 5).substring(30));
            Monkey monkey = new Monkey(items, operation, divisableBy, monkeyIfTrue, monkeyIfFalse);
            monkeys.add(monkey);
        }
    }

    private static void play(List<Monkey> monkeys, int rounds, long divideBy) {
        Integer divisor = 3;
        if (divideBy == 1L) {
            Optional<Integer> reduce = monkeys.stream().map(Monkey::getDivisibleBy).reduce((acc, l) -> acc * l);
            if (reduce.isPresent()) {
                divisor = reduce.get();
            }
        }

        for (int round = 1; round <= rounds; round++) {
            for (Monkey currentMonkey : monkeys) {
                for (Long item : currentMonkey.getItems()) {
                    makeTurn(monkeys, currentMonkey, item, divisor);
                }
                currentMonkey.clearItems();
            }
        }
    }

    private static void makeTurn(List<Monkey> monkeys, Monkey currentMonkey, Long item, Integer divisor) {
        Long newWorryLevel = currentMonkey.getNewWorryLevel(item, divisor);
        int throwToMonkey = currentMonkey.throwToMonkey(newWorryLevel);
        monkeys.get(throwToMonkey).addItem(newWorryLevel);
    }

    private static Operation getOperation(String line) {
        String[] s = line.substring(13).split(" ");
        return new Operation(s[0], s[2], s[3], s[4]);
    }

    private static List<Long> getItems(String line) {
        String[] split = line.substring(18).split(", ");
        return Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
    }
}