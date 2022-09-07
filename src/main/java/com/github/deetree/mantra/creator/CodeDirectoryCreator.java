package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class CodeDirectoryCreator implements DirectoryCreator {

    private final Path javaPath;
    private final Path resourcesPath;

    CodeDirectoryCreator(Path javaPath, Path resourcesPath) {
        this.javaPath = javaPath;
        this.resourcesPath = resourcesPath;
    }

    @Override
    public Result create() {
        if (createJavaDirectory() && createResourcesDirectory())
            return Result.OK;
        return Result.ERROR;
    }

    private boolean createJavaDirectory() {
        return createDirectories(javaPath) == Result.OK;
    }

    private boolean createResourcesDirectory() {
        return createDirectories(resourcesPath) == Result.OK;
    }
}
