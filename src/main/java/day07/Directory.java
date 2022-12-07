package day07;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Directory {
    private List<File> files = new ArrayList<>();

    private List<Directory> dirs = new ArrayList<>();

    private String name;

    private Directory parent;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    public void addFile(File file) {
        files.add(file);
    }

    public void addDirectory(Directory directory) {
        dirs.add(directory);
    }

    public void list() {
        //
    }

    public Directory switchDirectory(String subDir) {
        return dirs.stream()
                .filter(d -> subDir.equals(d.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("wrong directory name"));
    }

    public Directory switchToParent() {
        return this.parent;
    }

    public int getSize() {
        return dirs.stream()
                .map(Directory::getSize)
                .reduce(0, Integer::sum) + files.stream()
                .map(File::getSize)
                .reduce(0, Integer::sum);
    }

    public List<Directory> getDeletionCandidates(int maxSize) {
        var result = new ArrayList<Directory>();
        for (Directory dir : dirs) {
            if (dir.getSize() < maxSize) {
                result.add(dir);
            }
            result.addAll(dir.getDeletionCandidates(maxSize));
        }
        return result;
    }

    public Directory getClosestCandidateToDelete(int minSize) {
        var result = this;
        for (Directory dir : dirs) {
            if (dir.getSize() > minSize && dir.getSize() < result.getSize()) {
                result = dir;
            }
            var subdir = dir.getClosestCandidateToDelete(minSize);
            if (subdir.getSize() > minSize && subdir.getSize() < result.getSize()) {
                result = subdir;
            }
        }
        return result;
    }
}
