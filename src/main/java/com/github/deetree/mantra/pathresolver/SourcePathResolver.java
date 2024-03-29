package com.github.deetree.mantra.pathresolver;

import java.nio.file.Path;

/**
 * Sources directory path resolver.
 *
 * @author Mariusz Bal
 */
class SourcePathResolver implements Resolver {

    private final Path projectPath;

    /**
     * Create sources directory path resolver.
     *
     * @param projectPath project directory path
     */
    SourcePathResolver(Path projectPath) {this.projectPath = projectPath;}

    @Override
    public Path resolve() {
        return projectPath.resolve(Directory.SRC.toString());
    }
}
