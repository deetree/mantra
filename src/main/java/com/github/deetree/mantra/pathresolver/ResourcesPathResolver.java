package com.github.deetree.mantra.pathresolver;

import java.nio.file.Path;

/**
 * Resources files directory path resolver based on parent ({@link Directory#MAIN} or {@link Directory#TEST}).
 *
 * @author Mariusz Bal
 */
class ResourcesPathResolver implements Resolver {

    private final Path sourcesPath;
    private final Directory parent;

    ResourcesPathResolver(Path sourcesPath, Directory parent) {
        this.sourcesPath = sourcesPath;
        this.parent = parent;
    }

    @Override
    public Path resolve() {
        return sourcesPath.resolve(parent.toString()).resolve(Directory.RESOURCES.toString());
    }
}
