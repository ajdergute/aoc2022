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

        /*
         * x1 -> y1 y2 y3
         * x2
         * x3
         */

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
                if (visibleTrees[x][y] == null || visibleTrees[x][y] == -1) {
                    int height = treeIsVisible(treeHeight, trees[x][y]);
                    if (height > -1) {
                        visibleTrees[x][y] = height;
                        treeHeight = height;
                    }
                }
            }
        }
        writeTestFile(visibleTrees);

        // bottom => top
        visibleTrees[size - 1] = trees[size - 1];
        for (int y = 1; y < size - 1; y++) {
            int treeHeight = visibleTrees[size - 1][y];
            for (int x = size - 2; x > 0; x--) {
                if (visibleTrees[x][y] == null || visibleTrees[x][y] == -1) {
                    int height = treeIsVisible(treeHeight, trees[x][y]);
                    if (height > -1) {
                        visibleTrees[x][y] = height;
                        treeHeight = height;
                    }
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
                if (visibleTrees[x][y] == null || visibleTrees[x][y] == -1) {
                    int height = treeIsVisible(treeHeight, trees[x][y]);
                    if (height > -1) {
                        visibleTrees[x][y] = height;
                        treeHeight = height;
                    }
                }
            }
            visibleTrees[x][size - 1] = trees[x][size - 1];
        }
        writeTestFile(visibleTrees);

        long countOfVisibleTrees = Arrays.stream(visibleTrees).flatMap(Stream::of).filter(t -> t != -1).count();

        System.out.println("Trees visible from outside: " + countOfVisibleTrees);
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