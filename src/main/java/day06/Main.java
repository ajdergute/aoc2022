package day06;

import util.PuzzleInput;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput("day06");
        String inputLine = lines.get(0);

        // start-of-paket marker
        int startOffset = 4;
        int index = getIndexOfMarker(inputLine, startOffset);

        System.out.println("Number of character before first start-of-paket marker: " + index);

        // start-of-message marker
        startOffset = 14;
        index = getIndexOfMarker(inputLine, startOffset);

        System.out.println("Number of character before first start-of-message marker: " + index);
    }

    private static int getIndexOfMarker(String inputLine, int startOffset) {
        int i;
        for (i = startOffset; i < inputLine.length(); i++) {
            String marker = inputLine.substring(i - startOffset, i);
            List<Character> markerChars = marker.chars().mapToObj(e -> (char) e).toList();

            AtomicReference<Character> lastMarkerChar = new AtomicReference<>();
            int sum = markerChars.stream().sorted().mapToInt(e -> isGreaterThan(lastMarkerChar, e)).sum();

            if (startOffset == sum) {
                break;
            }
        }
        return i;
    }

    private static int isGreaterThan(AtomicReference<Character> lastMarkerChar, Character e) {
        if (lastMarkerChar.get() != null) {
            if (e > lastMarkerChar.get()) {
                lastMarkerChar.set(e);
                return 1;
            } else {
                return 0;
            }
        } else {
            lastMarkerChar.set(e);
            return 1;
        }
    }
}