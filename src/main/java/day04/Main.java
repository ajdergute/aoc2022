package day04;

import org.apache.commons.lang3.Range;
import util.PuzzleInput;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput("day04");

        int countFullyContains = 0;
        for (String line : lines) {
            String[] pairs = line.split(",");
            String[] minMax1 = pairs[0].split("-");
            var range1 = Range.between(Integer.parseInt(minMax1[0]), Integer.parseInt(minMax1[1]));
            String[] minMax2 = pairs[1].split("-");
            var range2 = Range.between(Integer.parseInt(minMax2[0]), Integer.parseInt(minMax2[1]));
            if (range1.containsRange(range2) || range2.containsRange(range1)) {
                countFullyContains++;
            }
        }
        System.out.println("Number of ranges fully containing the other: " + countFullyContains);
    }
}