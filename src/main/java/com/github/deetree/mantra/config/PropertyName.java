package com.github.deetree.mantra.config;

/**
 * Properties used to store defaults in config file.
 *
 * @author Mariusz Bal
 */
enum PropertyName {
    DIRECTORY("directory"),
    GROUP("groupId"),
    ARTIFACT("artifactId"),
    MAIN("mainClass"),
    USERNAME("git.username"),
    EMAIL("git.email"),
    LAUNCHER("ide.launcher");

    private final String name;

    PropertyName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
