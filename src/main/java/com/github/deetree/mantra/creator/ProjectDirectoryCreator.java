package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;

import java.nio.file.Path;

/**
 * Whole project directory creator.
 *
 * @author Mariusz Bal
 */
class ProjectDirectoryCreator implements DirectoryCreator {

    private final Path projectPath;

    /**
     * Instantiate project directory creator.
     *
     * @param projectPath project directory path
     */
    ProjectDirectoryCreator(Path projectPath) {this.projectPath = projectPath;}

    @Override
    public String makePreExecuteStatus() {
        return "Creating project directory";
    }

    @Override
    public Result create() {
        return createDirectories(projectPath);
    }

    @Override
    public String makePostExecuteSuccessStatus() {
        return "Project directory created";
    }

    @Override
    public String makePostExecuteErrorStatus() {
        return "Could not create project directory";
    }
}
