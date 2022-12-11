package day11;

import util.PuzzleInput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static int MEASURE = 20;

    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput("day11");
        // List<String> lines = PuzzleInput.getTestInput("day11");

        List<Monkey> monkeys = new ArrayList<>();

        for (int i = 0; i < lines.size(); i += 7) {
            List<Item> items = getItems(lines.get(i + 1));
            Operation operation = getOperation(lines.get(i + 2));
            Integer divisableBy = Integer.parseInt(lines.get(i + 3).substring(21));
            Integer monkeyIfTrue = Integer.parseInt(lines.get(i + 4).substring(29));
            Integer monkeyIfFalse = Integer.parseInt(lines.get(i + 5).substring(30));
            Monkey monkey = new Monkey(items, operation, divisableBy, monkeyIfTrue, monkeyIfFalse);
            monkeys.add(monkey);
        }

        for (int round = 1; round <= 20; round++) {
            for (Monkey currentMonkey : monkeys) {
                List<Item> removedItems = new ArrayList<>();
                currentMonkey.getItems().forEach(item -> makeTurn(monkeys, currentMonkey, removedItems, item));
                currentMonkey.removeItems(removedItems);
            }
        }

        Collections.sort(monkeys);

        System.out.println("Level of monkey business: " + monkeys.get(0).getInspectedItems() * monkeys.get(1).getInspectedItems() );
    }

    private static void makeTurn(List<Monkey> monkeys, Monkey currentMonkey, List<Item> removedItems, Item item) {
        int newWorryLevel = currentMonkey.getNewWorryLevel(item.getWorryLevel());
        newWorryLevel = (int) Math.floor(newWorryLevel / 3.0);
        int throwToMonkey = currentMonkey.throwToMonkey(newWorryLevel);
        item.setWorryLevel(newWorryLevel);
        monkeys.get(throwToMonkey).addItem(item);
        removedItems.add(item);
    }

    private static Operation getOperation(String line) {
        String[] s = line.substring(13).split(" ");
        return new Operation(s[0], s[2], s[3], s[4]);
    }

    private static List<Item> getItems(String line) {
        String[] split = line.substring(18).split(", ");
        return Arrays.stream(split).map(Integer::parseInt).map(i -> new Item(i, i)).collect(Collectors.toList());
    }

    private static void writeOutput(String[][] crtScreen) {
        BufferedWriter outputWriter;
        try {
            outputWriter = new BufferedWriter(new FileWriter("src/main/java/day11/output.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String[] strings : crtScreen) {
            try {
                String line = String.join("", strings);
                outputWriter.write(line);
                outputWriter.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            outputWriter.flush();
            outputWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}