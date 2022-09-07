package com.github.deetree.mantra.pathresolver;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class JavaFilesPathResolver implements Resolver {

    private final Path sourcesPath;
    private final Directory parent;
    private final Path packagePath;

    JavaFilesPathResolver(Path sourcesPath, Directory parent, Path packagePath) {
        this.sourcesPath = sourcesPath;
        this.parent = parent;
        this.packagePath = packagePath;
    }

    @Override
    public Path resolve() {
        return sourcesPath.resolve(parent.toString()).resolve(Directory.JAVA.toString()).resolve(packagePath);
    }
}