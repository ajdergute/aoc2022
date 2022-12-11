package day09;

import util.PuzzleInput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // List<String> lines = PuzzleInput.getPuzzleInput("day09");
        List<String> lines = PuzzleInput.getTestInput("day09");

        Set<Position> positions = ropeWalk(lines);
        System.out.println("Visited positions: " + positions.size());

        ropeWalk10(lines);
    }

    private static Set<Position> ropeWalk(List<String> lines) {
        Set<Position> positions = new HashSet<>();
        Position headPos = new Position(0, 0);
        Position tailPos = new Position(0, 0);
        positions.add(new Position(tailPos.getX(), tailPos.getY()));

        int maxDistance = 2;

        for (String line : lines) {
            String[] s = line.split(" ");
            int num = Integer.parseInt(s[1]);
            for (int i = 0; i < num; i++) {
                int x = 0;
                int y = 0;
                switch (s[0]) {
                    case "U" -> y = 1;
                    case "D" -> y = -1;
                    case "L" -> x = -1;
                    case "R" -> x = 1;
                }

                moveHead(headPos, x, y);

                if (tailPos.distance(headPos) >= maxDistance) {
                    tailPos = moveRope(tailPos, headPos);
                    positions.add(new Position(tailPos.getX(), tailPos.getY()));
                }
            }
        }
        return positions;
    }

    private static void ropeWalk10(List<String> lines) {
        Set<Position> positions = new HashSet<>();
        Position headPos = new Position(0, 0);
        Position tailPos = new Position(0, 0);
        LinkedList<Position> rope = new LinkedList<>();

        positions.add(new Position(tailPos.getX(), tailPos.getY()));
        rope.add(headPos);

        for (String line : lines) {
            String[] s = line.split(" ");
            int num = Integer.parseInt(s[1]);
            for (int i = 0; i < num; i++) {
                int x = 0;
                int y = 0;
                switch (s[0]) {
                    case "U" -> y = 1;
                    case "D" -> y = -1;
                    case "L" -> x = -1;
                    case "R" -> x = 1;
                }

                moveHead(headPos, x, y);
                Position lastElement = null;
                if (rope.size() == 9) {
                    lastElement = rope.poll();
                }
                rope.add(new Position(headPos.getX(), headPos.getY()));

                if (lastElement != null && tailPos.distance(lastElement) >= 2) {
                    tailPos = moveRope(tailPos, lastElement);
                    positions.add(new Position(tailPos.getX(), tailPos.getY()));
                }
            }
        }

        drawOnScreen(positions);
    }

    private static Position moveRope(Position tailPos, Position lastElement) {
        Position move = tailPos.moveTail(lastElement);
        tailPos = tailPos.translate(move.getX(), move.getY());
        return tailPos;
    }

    private static void moveHead(Position headPos, int x, int y) {
        headPos.translate(x, y);
    }

    private static void drawOnScreen(Set<Position> array) {
        BufferedWriter outputWriter;
        try {
            outputWriter = new BufferedWriter(new FileWriter("src/main/java/day09/output.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[][] field = new String[30][30];
        for (String[] strings : field) {
            Arrays.fill(strings, ".");
        }

        for (Position position : array) {
            field[position.getX()][position.getY()] = "#";
        }

        for (String[] strings : field) {
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