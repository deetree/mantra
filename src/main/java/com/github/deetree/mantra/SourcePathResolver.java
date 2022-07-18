package com.github.deetree.mantra;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class SourcePathResolver implements PathResolver {

    private final Path projectPath;

    SourcePathResolver(Path projectPath) {this.projectPath = projectPath;}

    @Override
    public Path resolve() {
        return projectPath.resolve(Directory.SRC.toString());
    }
}
