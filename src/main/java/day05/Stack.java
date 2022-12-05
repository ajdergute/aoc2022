package day05;

import org.apache.commons.lang3.CharUtils;

import java.util.*;

public class Stack {
    private final Deque<Character> stack = new ArrayDeque<>();

    public Stack putCrate(char crate) {
        if (CharUtils.isAsciiAlphaUpper(crate)) {
            stack.push(crate);
        }
        return this;
    }

    public Character pullCrate() {
        return stack.pop();
    }

    public String getTopCrate() {
        return Objects.requireNonNull(stack.peek()).toString();
    }

    public void putStack(List<Character> temporaryStack) {
        Collections.reverse(temporaryStack);
        temporaryStack.forEach(stack::push);
    }
}
