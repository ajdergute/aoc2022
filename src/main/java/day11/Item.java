package day11;

import java.util.Objects;

public class Item {
    private int number;
    private int worryLevel;

    public Item(int number, int worryLevel) {
        this.number = number;
        this.worryLevel = worryLevel;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getWorryLevel() {
        return worryLevel;
    }

    public void setWorryLevel(int worryLevel) {
        this.worryLevel = worryLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return number == item.number && worryLevel == item.worryLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, worryLevel);
    }

    @Override
    public String toString() {
        return "Item{" +
                "number=" + number +
                ", worryLevel=" + worryLevel +
                '}';
    }
}
