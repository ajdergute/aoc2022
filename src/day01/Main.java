package day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("src/day01/input.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            List<Integer> calories = new ArrayList<>();
            // find simple max
            int sum = 0;
            for (String line : lines) {
                if (line.isEmpty()) {
                    calories.add(sum);
                    sum = 0;
                } else {
                    sum = sum + Integer.parseInt(line);
                }
            }
            List<Integer> sorted = calories.stream().sorted().toList();
            System.out.println("Max Calories: " + sorted.get(sorted.size() - 1));

            int sumTop3 = sorted.get(sorted.size() - 1) + sorted.get(sorted.size() - 2) + sorted.get(sorted.size() - 3);
            System.out.println("Sum of top 3 Calories: " + sumTop3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}