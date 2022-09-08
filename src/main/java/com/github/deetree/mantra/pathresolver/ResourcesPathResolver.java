package com.github.deetree.mantra.pathresolver;

import java.nio.file.Path;

/**
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
