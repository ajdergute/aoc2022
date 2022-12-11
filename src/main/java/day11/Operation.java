package day11;

public class Operation {
    private String result;

    private String operand1;

    private String operator;

    private String operand2;

    public Operation(String result, String operand1, String operator, String operand2) {
        this.result = result;
        this.operand1 = operand1;
        this.operator = operator;
        this.operand2 = operand2;
    }

    public String getResult() {
        return result;
    }

    public String getOperand1() {
        return operand1;
    }

    public String getOperator() {
        return operator;
    }

    public String getOperand2() {
        return operand2;
    }
}
