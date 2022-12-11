package day08;

import util.PuzzleInput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput("day08");
        // List<String> lines = PuzzleInput.getTestInput("day08");

        Integer[][] trees = new Integer[lines.size()][lines.size()];
        int i = 0;
        for (String line : lines) {
            var list = line.chars().boxed().map(c -> c - 48).toList();
            trees[i] = list.toArray(new Integer[]{});
            i++;
        }

        int size = trees.length;
        Integer[][] visibleTrees = new Integer[size][size];
        for (int j = 0; j < size; j++) {
            Arrays.fill(visibleTrees[j], -1);
        }

        calculateVisibleTrees(trees, size, visibleTrees);

        long countOfVisibleTrees = Arrays.stream(visibleTrees).flatMap(Stream::of).filter(t -> t != -1).count();

        System.out.println("Trees visible from outside: " + countOfVisibleTrees);

        int scenicScore = calculateMaxScenicScore(visibleTrees, trees, size);

        System.out.println("Scenic score is: " + scenicScore);
    }

    private static int calculateMaxScenicScore(Integer[][] visibleTrees, Integer[][] trees, int size) {
        int scenicScore = 0;
        for (int x = 1; x < size - 1; x++) {
            for (int y = 1; y < size - 1; y++) {
                if (visibleTrees[x][y] != -1) {
                    int score = calculateScenicScore(trees, x, y);
                    scenicScore = Math.max(score, scenicScore);
                }
            }
        }
        return scenicScore;
    }

    private static int calculateScenicScore(Integer[][] visibleTrees, int x, int y) {
        // point -> right
        int distanceR = 1;
        for (int i = y; i < visibleTrees.length - 2; i++) {
            if (visibleTrees[x][y] <= visibleTrees[x][i + 1]) {
                break;
            }
            distanceR++;
        }
        // point -> bottom
        int distanceB = 1;
        for (int i = x; i < visibleTrees.length - 2; i++) {
            if (visibleTrees[x][y] <= visibleTrees[i + 1][y]) {
                break;
            }
            distanceB++;
        }
        // point -> left
        int distanceL = 1;
        for (int i = y; i > 1; i--) {
            if (visibleTrees[x][y] <= visibleTrees[x][i - 1]) {
                break;
            }
            distanceL++;
        }
        // point -> top
        int distanceT = 1;
        for (int i = x; i > 1; i--) {
            if (visibleTrees[x][y] <= visibleTrees[i - 1][y]) {
                break;
            }
            distanceT++;
        }
        return distanceR * distanceL * distanceB * distanceT;
    }

    private static void calculateVisibleTrees(Integer[][] trees, int size, Integer[][] visibleTrees) {
        // top => bottom
        visibleTrees[0] = trees[0];
        for (int y = 1; y < size - 1; y++) {
            int treeHeight = visibleTrees[0][y];
            for (int x = 1; x < size; x++) {
                int height = treeIsVisible(treeHeight, trees[x][y]);
                if (height > -1) {
                    visibleTrees[x][y] = height;
                    treeHeight = height;
                }
            }
        }
        writeTestFile(visibleTrees);

        // left => right
        for (int x = 1; x < size - 1; x++) {
            visibleTrees[x][0] = trees[x][0];
            int treeHeight = visibleTrees[x][0];
            for (int y = 1; y < size; y++) {
                int height = treeIsVisible(treeHeight, trees[x][y]);
                if (height > -1) {
                    visibleTrees[x][y] = height;
                    treeHeight = height;
                }
            }
        }
        writeTestFile(visibleTrees);

        // bottom => top
        visibleTrees[size - 1] = trees[size - 1];
        for (int y = 1; y < size - 1; y++) {
            int treeHeight = visibleTrees[size - 1][y];
            for (int x = size - 2; x > 0; x--) {
                int height = treeIsVisible(treeHeight, trees[x][y]);
                if (height > -1) {
                    visibleTrees[x][y] = height;
                    treeHeight = height;
                }
            }
        }
        writeTestFile(visibleTrees);

        // right => left
        visibleTrees[0][size - 1] = trees[0][size - 1];
        for (int x = 1; x < size - 1; x++) {
            visibleTrees[x][size - 1] = trees[x][size - 1];
            int treeHeight = visibleTrees[x][size - 1];
            for (int y = size - 2; y > 0; y--) {
                int height = treeIsVisible(treeHeight, trees[x][y]);
                if (height > -1) {
                    visibleTrees[x][y] = height;
                    treeHeight = height;
                }
            }
            visibleTrees[x][size - 1] = trees[x][size - 1];
        }
        writeTestFile(visibleTrees);
    }

    private static void writeTestFile(Integer[][] visibleTrees) {
        BufferedWriter outputWriter;
        try {
            outputWriter = new BufferedWriter(new FileWriter("src/main/java/day08/output.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Integer[] visibleTree : visibleTrees) {
            try {
                String line = Arrays.stream(visibleTree)
                        .map(i -> i == -1 ? "_" : i.toString())
                        .collect(Collectors.joining(""));
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

    private static int treeIsVisible(int treeHeightNeigbour, int value) {
        if (treeHeightNeigbour != -1 && treeHeightNeigbour < value) {
            return value;
        } else {
            return -1;
        }
    }
}