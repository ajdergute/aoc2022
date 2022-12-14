package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ScreenUtil {
    public static void writeToScreen(String[][] array, int day) {
        BufferedWriter outputWriter;
        try {
            outputWriter = new BufferedWriter(new FileWriter("src/main/java/day" + day + "/output.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String[] strings : array) {
            try {
                String line = String.join("", strings);
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
