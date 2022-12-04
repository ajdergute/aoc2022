package day03;

import java.util.Objects;

public record Group(String first, String second, String third) {
    public Group {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(third);
    }}
