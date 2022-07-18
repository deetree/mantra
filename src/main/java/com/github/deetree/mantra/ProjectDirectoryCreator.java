package com.github.deetree.mantra;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class ProjectDirectoryCreator implements DirectoryCreator {

    private final Path projectPath;

    ProjectDirectoryCreator(Path projectPath) {this.projectPath = projectPath;}

    @Override
    public Result create() {
        return createDirectories(projectPath);
    }
}
