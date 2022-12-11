package day11;

import java.util.List;
import java.util.Objects;

public class Monkey implements Comparable {
    private int inspectedItems;

    private List<Item> items;

    private Operation operation;

    private Integer divisibleBy;

    private Integer throwToTrue;

    private Integer throwToFalse;

    public Monkey(List<Item> items, Operation operation,
                  Integer divisibleBy, Integer throwToTrue, Integer throwToFalse) {
        this.items = items;
        this.operation = operation;
        this.divisibleBy = divisibleBy;
        this.throwToTrue = throwToTrue;
        this.throwToFalse = throwToFalse;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public Integer getNewWorryLevel(Integer item) {
        inspectedItems++;

        Integer operand1;
        if ("old".equals(operation.getOperand1())) {
            operand1 = item;
        } else {
            operand1 = Integer.parseInt(operation.getOperand1());
        }
        Integer operand2;
        if ("old".equals(operation.getOperand2())) {
            operand2 = item;
        } else {
            operand2 = Integer.parseInt(operation.getOperand2());
        }

        int worryLevel;
        switch (operation.getOperator()) {
            case "+" -> worryLevel = operand1 + operand2;
            case "-" -> worryLevel = operand1 - operand2;
            case "*" -> worryLevel = operand1 * operand2;
            case "/" -> worryLevel = operand1 / operand2;
            default -> throw new IllegalArgumentException("unknown operator: " + operation.getOperator());
        }
        return worryLevel;
    }

    public Integer throwToMonkey(Integer worryLevel) {
        if (worryLevel % divisibleBy == 0) {
            return throwToTrue;
        } else {
            return throwToFalse;
        }
    }

    public int getInspectedItems() {
        return inspectedItems;
    }

    public boolean removeItems(List<Item> removedItems) {
        return items.removeAll(removedItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monkey monkey = (Monkey) o;
        return inspectedItems == monkey.inspectedItems && Objects.equals(items, monkey.items) && Objects.equals(operation, monkey.operation) && Objects.equals(divisibleBy, monkey.divisibleBy) && Objects.equals(throwToTrue, monkey.throwToTrue) && Objects.equals(throwToFalse, monkey.throwToFalse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inspectedItems, items, operation, divisibleBy, throwToTrue, throwToFalse);
    }

    @Override
    public String toString() {
        return "Monkey{" +
                "inspectedItems=" + inspectedItems +
                ", items=" + items +
                ", operation=" + operation +
                ", divisibleBy=" + divisibleBy +
                ", throwToTrue=" + throwToTrue +
                ", throwToFalse=" + throwToFalse +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Monkey) {
            Monkey monkey = (Monkey) o;
            if (monkey.inspectedItems > this.inspectedItems) {
                return 1;
            } else if (monkey.inspectedItems == this.inspectedItems) {
                return 0;
            } else {
                return -1;
            }
        }
        throw new IllegalArgumentException("no monkey object given");
    }
}
