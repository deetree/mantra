package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;

import java.io.File;
import java.nio.file.Path;

/**
 * {@code Main} and {@code Test } code directory creator
 *
 * @author Mariusz Bal
 */
class CodeDirectoryCreator implements DirectoryCreator {

    private final Path javaPath;
    private final Path resourcesPath;

    /**
     * Instantiate code directory creator.
     *
     * @param javaPath      java files directory path
     * @param resourcesPath resources directory path
     */
    CodeDirectoryCreator(Path javaPath, Path resourcesPath) {
        this.javaPath = javaPath;
        this.resourcesPath = resourcesPath;
    }

    @Override
    public String makePreExecuteStatus() {
        return "Creating %s files directory".formatted(isTestPath() ? "test" : "main");
    }

    @Override
    public Result create() {
        if (createJavaDirectory() && createResourcesDirectory())
            return Result.OK;
        return Result.ERROR;
    }

    @Override
    public String makePostExecuteSuccessStatus() {
        return "Created %s files directory".formatted(isTestPath() ? "test" : "main");
    }

    @Override
    public String makePostExecuteErrorStatus() {
        return "Could not create %s files directory".formatted(isTestPath() ? "test" : "main");
    }

    private boolean isTestPath() {
        return javaPath.toString().contains(File.separatorChar + "test" + File.separatorChar);
    }

    private boolean createJavaDirectory() {
        return createDirectories(javaPath) == Result.OK;
    }

    private boolean createResourcesDirectory() {
        return createDirectories(resourcesPath) == Result.OK;
    }
}
