package day09;

import java.util.Objects;

public class Position {

    private int x;

    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position moveTail(Position head) {
        int dx = head.getX() - this.getX();
        int dy = head.getY() - this.getY();
        if (dx != 0) {
            dx = dx / Math.abs(dx);
        }
        if (dy != 0) {
            dy = dy / Math.abs(dy);
        }
        return new Position(dx, dy);
    }

    public int distance(Position pt) {
        int px = pt.getX() - this.getX();
        int py = pt.getY() - this.getY();
        return Math.max(Math.abs(px), Math.abs(py));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
