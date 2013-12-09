package com.brightgenerous.compress.zip;

public enum DeflateLevel {

    FASTEST(1),

    FAST(3),

    NORMAL(5),

    MAXIMUM(7),

    ULTRA(9);

    private final int level;

    private DeflateLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
