package day10;

import util.PuzzleInput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static int MEASURE = 20;

    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput("day10");
        // List<String> lines = PuzzleInput.getTestInput("day10");

        int registerX = 1;
        int cycle = 0;
        int signalStrength = 0;

        String[][] crtScreen = new String[6][40];

        for (String line : lines) {
            String[] split = line.split(" ");

            drawOnScreen(crtScreen, cycle, registerX);
            cycle++;

            switch(split[0]) {
                case "noop":
                    signalStrength += getSignalStrength(cycle, registerX);
                    break;
                case "addx":
                    signalStrength += getSignalStrength(cycle, registerX);
                    drawOnScreen(crtScreen, cycle, registerX);
                    cycle++;
                    signalStrength += getSignalStrength(cycle, registerX);
                    registerX += Integer.parseInt(split[1]);
                    drawOnScreen(crtScreen, cycle, registerX);
                    break;
            }
        }

        writeCrtScreen(crtScreen);

        System.out.println("Signal strength is: " + signalStrength);
    }

    private static void drawOnScreen(String[][] crtScreen, int cycle, int registerX) {
        int length = crtScreen[0].length;
        int pos = cycle % length;
        int line = (cycle - pos) / length;
        if (registerX == pos || registerX - 1 == pos || registerX + 1 == pos) {
            crtScreen[line][pos] = "#";
        } else {
            crtScreen[line][pos] = ".";
        }
    }

    private static int getSignalStrength(int currentCycle, int registerX) {
        int signalStrength = 0;

        if (currentCycle == MEASURE) {
            signalStrength = currentCycle * registerX;
            MEASURE += 40;
        }

        return signalStrength;
    }

    private static void writeCrtScreen(String[][] crtScreen) {
        BufferedWriter outputWriter = null;
        try {
            outputWriter = new BufferedWriter(new FileWriter("src/main/java/day10/output.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int l = 0; l < crtScreen.length; l++) {
            try {
                String line = String.join("", crtScreen[l]);
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