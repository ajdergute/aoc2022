package day09;

import util.PuzzleInput;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput("day09");
        // List<String> lines = PuzzleInput.getTestInput("day09");

        Set<Position> positions = new HashSet<>();
        Position headPos = new Position(0, 0);
        Position tailPos = new Position(0, 0);
        positions.add(new Position(tailPos.getX(), tailPos.getY()));

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

                if (tailPos.distance(headPos) >= 2) {
                    Position move = tailPos.moveTail(headPos);
                    tailPos = tailPos.translate(move.getX(), move.getY());
                    positions.add(new Position(tailPos.getX(), tailPos.getY()));
                }

                if (tailPos.distance(headPos) > 1) {
                    System.out.println("head: " + headPos + ", tail: " + tailPos);
                }
            }
        }

        System.out.println("Visited positions: " + positions.size());
    }

    private static void moveHead(Position headPos, int x, int y) {
        headPos.translate(x, y);
    }
}