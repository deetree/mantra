package com.github.deetree.mantra;

/**
 * @author Mariusz Bal
 */
enum Directory {
    SRC("src"),
    MAIN("main"),
    TEST("test"),
    JAVA("java"),
    RESOURCES("resources");

    private final String parent;

    Directory(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return parent;
    }
}
