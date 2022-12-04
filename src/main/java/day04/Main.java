package day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("src/main/java/day04/input.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            int totalScore = 0;

            for (String line : lines) {
                String[] round = line.split(" ");
                char player1 = round[0].charAt(0);
                char player2 = (char) (round[1].charAt(0) - 23);
                int localScorePlayer1 = (int) player1 - 64;
                int localScorePlayer2 = (int) player2 - 64;
                if (player1 == player2) {
                    totalScore += localScorePlayer2 + 3;
                } else if (localScorePlayer2 == 1 && localScorePlayer1 == 3) {
                    totalScore += localScorePlayer2 + 6;
                } else if (localScorePlayer2 == 3 && localScorePlayer1 == 2) {
                    totalScore += localScorePlayer2 + 6;
                } else if (localScorePlayer2 == 2 && localScorePlayer1 == 1) {
                    totalScore += localScorePlayer2 + 6;
                } else {
                    totalScore += localScorePlayer2;
                }
            }
            System.out.println("Total score according strategy guide: " + totalScore);

            totalScore = 0;
            for (String line : lines) {
                String[] round = line.split(" ");
                char player1 = round[0].charAt(0);
                char player2 = (char) (round[1].charAt(0) - 23);
                int localScorePlayer1 = (int) player1 - 64;
                int localScorePlayer2 = (int) player2 - 64;
                if (localScorePlayer2 == 2) {
                    totalScore += localScorePlayer1 + 3;
                } else if (localScorePlayer2 == 3) {
                    switch (localScorePlayer1) {
                        case 1 -> totalScore += 2 + 6;
                        case 2 -> totalScore += 3 + 6;
                        case 3 -> totalScore += 1 + 6;
                    }
                } else {
                    switch (localScorePlayer1) {
                        case 1 -> totalScore += 3;
                        case 2 -> totalScore += 1;
                        case 3 -> totalScore += 2;
                    }
                }
            }
            System.out.println("Total score according strategy guide 2: " + totalScore);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}