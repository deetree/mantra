package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class ProjectDirectoryCreator implements DirectoryCreator {

    private final Path projectPath;

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
