package day07;

import util.PuzzleInput;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> lines = PuzzleInput.getPuzzleInput("day07");

        Directory root = new Directory("/", null);
        Directory workingDir = root;

        for (String line : lines) {
            String[] parts = line.split(" ");
            if ("$".equals(parts[0])) {
                if ("cd".equals(parts[1])) {
                    if ("/".equals(parts[2])) {
                        continue;
                    }
                    if ("..".equals(parts[2])) {
                        workingDir = workingDir.switchToParent();
                    } else {
                        workingDir = workingDir.switchDirectory(parts[2]);
                    }
                }
            } else if ("dir".equals(parts[0])) {
                workingDir.addDirectory(new Directory(parts[1], workingDir));
            } else {
                workingDir.addFile(new File(parts[1], Integer.parseInt(parts[0])));
            }
        }

        var deletionCandidates = root.getDeletionCandidates(100000);
        var sum = deletionCandidates.stream()
                .map(d -> d.getSize())
                .reduce(0, Integer::sum);

        System.out.println("Sum of sizes from directories to delete: " + sum);

        var sizeToBeFreed = 30000000 - (70000000 - root.getSize());
        var dir = root.getClosestCandidateToDelete(sizeToBeFreed);

        System.out.println("Directory to delete: " + dir.getSize());
    }
}