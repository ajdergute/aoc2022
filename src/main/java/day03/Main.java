package day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<String> lines = getPuzzleInput();

//        List<String> lines = new ArrayList<>();
//        lines.add("vJrwpWtwJgWrhcsFMMfFFhFp");
//        lines.add("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL");
//        lines.add("PmmdzqPrVvPwwTWBwg");
//        lines.add("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn");
//        lines.add("ttgJtRGJQctTZtZT");
//        lines.add("CrZsJsPPZsGzwwsLwLmpwMDw");

        int sum = sameTypeInBothCompartments(lines);
        System.out.println("Sum of priorities: " + sum);

        List<Group> groups = getGroupsFromPuzzleInput(lines);
        int sumGroupBadges = sumOfGroupBadges(groups);
        System.out.println("Sum of priorities: " + sumGroupBadges);
    }

    private static int sumOfGroupBadges(List<Group> groups) {
        int sum = 0;
        for (Group group : groups) {
            Set<Integer> items = new HashSet<>();
            for (int i = 0; i < group.first().length(); i++) {
                char currentChar = group.first().charAt(i);
                int indexSecond = group.second().indexOf(currentChar);
                int indexThird = group.third().indexOf(currentChar);
                if (-1 != indexSecond && -1 != indexThird) {
                    int priority = computePriority(currentChar);
                    items.add(priority);
                }
            }
            sum += items.stream().reduce(0, Integer::sum);
        }
        return sum;
    }

    private static List<Group> getGroupsFromPuzzleInput(List<String> lines) {
        List<Group> list = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 3) {
            list.add(new Group(lines.get(i), lines.get(i + 1), lines.get(i + 2)));
        }
        return list;
    }

    private static int sameTypeInBothCompartments(List<String> lines) {
        int sumOfPriorities = 0;
        for (String line : lines) {
            int middle = line.length() / 2;
            Set<Integer> items = new HashSet<>();
            for (int i = 0; i < middle; i++) {
                int index = line.indexOf(line.charAt(i), middle);
                if (-1 != index) {
                    char item = line.charAt(index);
                    int priority = computePriority(item);
                    items.add(priority);
                }
            }
            sumOfPriorities += items.stream().reduce(0, Integer::sum);
        }
        return sumOfPriorities;
    }

    private static int computePriority(int item) {
        int priority;
        if (item >= 97) {
            priority = item - 96;
        } else {
            priority = item - 64 + 26;
        }
        return priority;
    }

    private static List<String> getPuzzleInput() {
        Path path = Paths.get("src/main/java/day03/input.txt");
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
