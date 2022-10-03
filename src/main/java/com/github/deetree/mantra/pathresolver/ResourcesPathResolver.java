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

    /**
     * Instantiate resources directory path resolver
     *
     * @param sourcesPath sources directory path
     * @param parent      parent directory
     */
    ResourcesPathResolver(Path sourcesPath, Directory parent) {
        this.sourcesPath = sourcesPath;
        this.parent = parent;
    }

    @Override
    public Path resolve() {
        return sourcesPath.resolve(parent.toString()).resolve(Directory.RESOURCES.toString());
    }
}
