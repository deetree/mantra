package com.github.deetree.mantra;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class ResourcesPathResolver implements PathResolver {

    private final Path sourcesPath;
    private final Directory parent;

    public ResourcesPathResolver(Path sourcesPath, Directory parent) {
        this.sourcesPath = sourcesPath;
        this.parent = parent;
    }

    @Override
    public Path resolve() {
        return sourcesPath.resolve(parent.toString()).resolve(Directory.RESOURCES.toString());
    }
}
