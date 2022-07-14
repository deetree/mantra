package com.github.deetree.mantra;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class ProjectPathResolver implements PathResolver {

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
