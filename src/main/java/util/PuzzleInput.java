package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PuzzleInput {
    public static List<String> getPuzzleInput(String day) {
        Path path = Paths.get("src/main/java/", day, "/input.txt");
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getPuzzleInput(int day) {
        return getPuzzleInput("day" + day);
    }

    public static List<String> getTestInput(String day) {
        Path path = Paths.get("src/main/java/", day, "/testInput.txt");
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getTestInput(int day) {
        return getTestInput("day" + day);
    }
}
