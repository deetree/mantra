package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class BasicCreator implements Creator {

    private final Path projectPath;
    private final Path javaMainFilesPath;
    private final Path mainResourcesPath;
    private final Path javaTestFilesPath;
    private final Path testResourcesPath;

    BasicCreator(Path projectPath, Path javaMainFilesPath, Path mainResourcesPath,
                 Path javaTestFilesPath, Path testResourcesPath) {
        this.projectPath = projectPath;
        this.javaMainFilesPath = javaMainFilesPath;
        this.mainResourcesPath = mainResourcesPath;
        this.javaTestFilesPath = javaTestFilesPath;
        this.testResourcesPath = testResourcesPath;
    }

    @Override
    public Result create() {
        createDirectoryStructure();
        createBasicFiles();
        return Result.OK;//todo check
    }

    private void createDirectoryStructure() {
        new ProjectDirectoryCreator(projectPath).create();
        new CodeDirectoryCreator(javaMainFilesPath, mainResourcesPath).create();
        new CodeDirectoryCreator(javaTestFilesPath, testResourcesPath).create();
    }

    private void createBasicFiles() {

    }
}
