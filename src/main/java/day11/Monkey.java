package day11;

import java.util.List;
import java.util.Objects;

public class Monkey implements Comparable<Monkey> {
    private int inspectedItems;

    private final List<Long> items;

    private final Operation operation;

    private final Integer divisibleBy;

    private final Integer throwToTrue;

    private final Integer throwToFalse;

    public Monkey(List<Long> items, Operation operation,
                  Integer divisibleBy, Integer throwToTrue, Integer throwToFalse) {
        this.items = items;
        this.operation = operation;
        this.divisibleBy = divisibleBy;
        this.throwToTrue = throwToTrue;
        this.throwToFalse = throwToFalse;
    }

    public List<Long> getItems() {
        return items;
    }

    public void addItem(Long item) {
        items.add(item);
    }

    public Integer getDivisibleBy() {
        return divisibleBy;
    }

    public Long getNewWorryLevel(Long item, Integer divisor) {
        Long operand1;
        if ("old".equals(operation.getOperand1())) {
            operand1 = item;
        } else {
            operand1 = Long.parseLong(operation.getOperand1());
        }
        Long operand2;
        if ("old".equals(operation.getOperand2())) {
            operand2 = item;
        } else {
            operand2 = Long.parseLong(operation.getOperand2());
        }

        long worryLevel;
        switch (operation.getOperator()) {
            case "+" -> worryLevel = operand1 + operand2;
            case "-" -> worryLevel = operand1 - operand2;
            case "*" -> worryLevel = operand1 * operand2;
            case "/" -> worryLevel = operand1 / operand2;
            default -> throw new IllegalArgumentException("unknown operator: " + operation.getOperator());
        }

        if (3L == divisor) {
            worryLevel = worryLevel / divisor;
        } else {
            worryLevel = worryLevel % divisor;
        }

        inspectedItems++;

        return worryLevel;
    }

    public Integer throwToMonkey(Long worryLevel) {
        if (worryLevel % divisibleBy == 0L) {
            return throwToTrue;
        } else {
            return throwToFalse;
        }
    }

    public Integer getInspectedItems() {
        return inspectedItems;
    }

    public void clearItems() {
        items.clear();
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
    public int compareTo(Monkey o) {
        return Integer.compare(o.inspectedItems, this.inspectedItems);
    }
}
