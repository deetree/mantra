package com.github.deetree.mantra.pathresolver;

import java.nio.file.Path;

/**
 * Project directory path resolver.
 *
 * @author Mariusz Bal
 */
class ProjectPathResolver implements Resolver {

    private final String directory;
    private final String name;

    ProjectPathResolver(String directory, String name) {
        this.directory = directory;
        this.name = name;
    }

    @Override
    public Path resolve() {
        return Path.of(directory, name);
    }
}
