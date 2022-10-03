package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.Result;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Basic .gitignore file creator.
 *
 * @author Mariusz Bal
 */
class GitignoreCreator implements FileCreator {

    private final Path projectPath;

    GitignoreCreator(Path projectPath) {this.projectPath = projectPath;}

    @Override
    public String makePreExecuteStatus() {
        return "Creating basic .gitignore file";
    }

    @Override
    public Result create() {
        final String fileName = "gitignore";
        InputStream gitignore = ResourceFileLoader.load(fileName);
        try {
            Files.copy(gitignore, projectPath.resolve("." + fileName), StandardCopyOption.REPLACE_EXISTING);
            return Result.OK;
        } catch (IOException e) {
            throw new ActionException("An error occurred during .gitignore file creation");
        }
    }

    @Override
    public String makePostExecuteSuccessStatus() {
        return "Basic .gitignore file created";
    }

    @Override
    public String makePostExecuteErrorStatus() {
        return "Could not create .gitignore file";
    }
}
