package day10;

import util.PuzzleInput;

import java.util.List;

public class Main {
    private static int MEASURE = 20;

    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput("day10");
        // List<String> lines = PuzzleInput.getTestInput("day10");

        int registerX = 1;
        int cycle = 0;
        int signalStrength = 0;

        for (String line : lines) {
            String[] split = line.split(" ");

            cycle++;
            switch(split[0]) {
                case "noop":
                    signalStrength += getSignalStrength(cycle, registerX);
                    break;
                case "addx":
                    signalStrength += getSignalStrength(cycle, registerX);
                    cycle++;
                    signalStrength += getSignalStrength(cycle, registerX);
                    registerX += Integer.parseInt(split[1]);
                    break;
            }
        }

        System.out.println("Signal strength is: " + signalStrength);
    }

    private static int getSignalStrength(int currentCycle, int registerX) {
        int signalStrength = 0;

        if (currentCycle == MEASURE) {
            signalStrength = currentCycle * registerX;
            MEASURE += 40;
        }

        return signalStrength;
    }
}