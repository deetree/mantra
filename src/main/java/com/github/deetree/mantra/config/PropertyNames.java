package com.github.deetree.mantra.config;

/**
 * @author Mariusz Bal
 */
enum PropertyNames {
    DIRECTORY("directory"),
    GROUP("groupId"),
    ARTIFACT("artifactId"),
    MAIN("mainClass"),
    USERNAME("git.username"),
    EMAIL("git.email"),
    LAUNCHER("ide.launcher");

    private final String name;

    PropertyNames(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
