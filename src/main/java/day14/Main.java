
package day14;

import util.PuzzleInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.ScreenUtil.writeToScreen;

public class Main {
    private static final int DAY = 14;

    private static final int offsetX = 0;
    private static final int offsetY = 0;

    private static int maxX = 0;
    private static int maxY = 0;

    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput(DAY);
        // List<String> lines = PuzzleInput.getTestInput(DAY);

        String[][] field = initField(lines);
        writeToScreen(field, DAY);

        int num = sandIsPouring(field);

        System.out.println("Units of sand dropped: " + num);

        field = initField(lines);
        writeToScreen(field, DAY);

        for (int i = 0; i < field[0].length; i++) {
            field[field.length - 1][i] = "#";
        }

        num = sandIsPouring(field);

        writeToScreen(field, DAY);

        System.out.println("Units of sand dropped until blocked: " + num);
    }

    private static int sandIsPouring(String[][] field) {
        boolean isStopped = false;
        int sand = 0;
        Position startingPoint = new Position(500 - offsetX, 0);

        while (!isStopped) {
            try {
                Position drawedPosition = drawSand(field, startingPoint.copy());
                if (drawedPosition.equals(new Position(500, 0))) {
                    isStopped = true;
                }
                sand++;
            } catch (IndexOutOfBoundsException ex) {
                isStopped = true;
            }
        }
        return sand;
    }

    private static Position drawSand(String[][] field, Position startingPoint) {
        Position result;
        String val = field[startingPoint.getY() + 1][startingPoint.getX()];

        if (val.equals(".")) {
            result = drawSand(field, startingPoint.translate(0, 1));
        } else if ("#".equals(val) || "o".equals(val)) {
            String left = field[startingPoint.getY() + 1][startingPoint.getX() - 1];
            String right = field[startingPoint.getY() + 1][startingPoint.getX() + 1];
            if (!".".equals(left) && !".".equals(right)) {
                field[startingPoint.getY()][startingPoint.getX()] = "o";
                result = new Position(startingPoint.getX(), startingPoint.getY());
            } else if (!".".equals(left)) {
                result = drawSand(field, startingPoint.translate(1, 1));
            } else {
                result = drawSand(field, startingPoint.translate(-1, 1));
            }
        } else {
            field[startingPoint.getY()][startingPoint.getX()] = "o";
            writeToScreen(field, DAY);
            result = new Position(startingPoint.getX(), startingPoint.getY());
        }
        return result;
    }

    private static String[][] initField(List<String> lines) {
        List<Position> stoneList = new ArrayList<>();

        for (String line : lines) {
            String[] coordinates = line.split(" -> ");
            Position lastPosition = null;
            for (String coordinate : coordinates) {
                String[] pos = coordinate.split(",");
                int posX = Integer.parseInt(pos[0]) - offsetX;
                int posY = Integer.parseInt(pos[1]) - offsetY;

                if (maxX < posX) {
                    maxX = posX;
                }
                if (maxY < posY) {
                    maxY = posY;
                }

                if (null != lastPosition) {
                    paintPath(stoneList, lastPosition, new Position(posX, posY));
                }
                stoneList.add(new Position(posX, posY));
                lastPosition = new Position(posX, posY);
            }
        }

        String[][] field = new String[maxY + 3][(maxX * 2) + 1];
        for (String[] strings : field) {
            Arrays.fill(strings, ".");
        }
        for (Position pos : stoneList) {
            field[pos.getY()][pos.getX()] = "#";
        }

        field[0][500 - offsetX] = "+";

        return field;
    }

    private static void paintPath(List<Position> list, Position from, Position to) {
        int dx = to.getX() - from.getX();
        if (dx > 0) {
            for (int i = from.getX(); i < from.getX() + dx; i++) {
                list.add(new Position(i, from.getY()));
            }
        }
        if (dx < 0) {
            for (int i = from.getX(); i > from.getX() + dx; i--) {
                list.add(new Position(i, from.getY()));
            }
        }

        int dy = to.getY() - from.getY();
        if (dy > 0) {
            for (int i = from.getY(); i < from.getY() + dy; i++) {
                list.add(new Position(from.getX(), i));
            }
        }
        if (dy < 0) {
            for (int i = from.getY(); i > from.getY() + dy; i--) {
                list.add(new Position(from.getX(), i));
            }
        }
    }
}