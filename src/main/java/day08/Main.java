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
        // List<String> lines = PuzzleInput.getPuzzleInput("day08");
        List<String> lines = PuzzleInput.getTestInput("day08");

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
        for (int y = 1; y < size; y++) {
            for (int x = 1; x < size; x++) {
                if (visibleTrees[x - 1][y] == -1) {
                    treeOfSameHeightNotVisibleX(visibleTrees, x - 2, y);
                    break;
                }
                visibleTrees[x][y] = treeIsVisible(visibleTrees[x - 1][y], trees[x][y]);
            }
        }
        writeTestFile(visibleTrees);

        // left => right
        for (int x = 1; x < size; x++) {
            visibleTrees[x][0] = trees[x][0];
            for (int y = 1; y < size; y++) {
                if (visibleTrees[x][y - 1] == -1) {
                    treeOfSameHeightNotVisibleY(visibleTrees, x, y - 2);
                    break;
                }
                if (visibleTrees[x][y] == null || visibleTrees[x][y] == -1) {
                    visibleTrees[x][y] = treeIsVisible(visibleTrees[x][y - 1], trees[x][y]);
                }
            }
            visibleTrees[x][size - 1] = trees[x][size - 1];
        }
        writeTestFile(visibleTrees);

        // bottom => top
        visibleTrees[size - 1] = trees[size - 1];
        for (int y = 1; y < size; y++) {
            for (int x = size - 2; x > 1; x--) {
                if (visibleTrees[x + 1][y] == -1) {
                    break;
                }
                if (visibleTrees[x][y] == null || visibleTrees[x][y] == -1) {
                    visibleTrees[x][y] = treeIsVisible(visibleTrees[x + 1][y], trees[x][y]);
                }
            }
        }
        writeTestFile(visibleTrees);

        // right => left
        visibleTrees[0][size - 1] = trees[0][size - 1];
        for (int x = 1; x < size; x++) {
            visibleTrees[x][size - 1] = trees[x][size - 1];
            for (int y = size - 2; y > 1; y--) {
                if (visibleTrees[x][y + 1] == -1) {
                    break;
                }
                if (visibleTrees[x][y] == null || visibleTrees[x][y] == -1) {
                    visibleTrees[x][y] = treeIsVisible(visibleTrees[x][y + 1], trees[x][y]);

                }
            }
            visibleTrees[x][size - 1] = trees[x][size - 1];
        }
        writeTestFile(visibleTrees);

        long countOfVisibleTrees = Arrays.stream(visibleTrees).flatMap(Stream::of).filter(t -> t != -1).count();

        System.out.println("Visible trees: " + countOfVisibleTrees);
    }

    private static void treeOfSameHeightNotVisibleX(Integer[][] visibleTrees, int x, int y) {
        for (int xA = x - 1; xA > 1; xA--) {
            if (visibleTrees[x][y].intValue() == visibleTrees[xA][y].intValue()) {
                visibleTrees[xA][y] = -1;
            }
        }
    }

    private static void treeOfSameHeightNotVisibleY(Integer[][] visibleTrees, int x, int y) {
        for (int yA = x - 1; yA > 1; yA--) {
            if (visibleTrees[x][y].intValue() == visibleTrees[x][yA].intValue()) {
                visibleTrees[x][yA] = -1;
            }
        }
    }

    private static void writeTestFile(Integer[][] visibleTrees) {
        BufferedWriter outputWriter = null;
        try {
            outputWriter = new BufferedWriter(new FileWriter("src/main/java/day08/output.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int l = 0; l < visibleTrees.length; l++) {
            try {
                String line = Arrays.stream(visibleTrees[l])
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

    private static int treeIsVisible(int neighbour, int value) {
        if (neighbour != -1 && neighbour <= value) {
            return value;
        } else {
            return -1;
        }
    }
}