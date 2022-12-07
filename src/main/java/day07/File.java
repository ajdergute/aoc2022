package day07;

public class File {
    private int size;

    private String name;

    public File(String name, int size) {
        this.size = size;
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
