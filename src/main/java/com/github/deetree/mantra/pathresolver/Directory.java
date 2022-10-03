package com.github.deetree.mantra.pathresolver;

/**
 * Directory names used within the project.
 *
 * @author Mariusz Bal
 */
enum Directory {
    SRC("src"),
    MAIN("main"),
    TEST("test"),
    JAVA("java"),
    RESOURCES("resources");

    private final String parent;

    /**
     * Instantiate directory name entry.
     *
     * @param parent name of the directory that will be a parent for other files or directories
     */
    Directory(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return parent;
    }
}
