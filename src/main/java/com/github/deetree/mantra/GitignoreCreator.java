package com.github.deetree.mantra;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author Mariusz Bal
 */
class GitignoreCreator implements FileCreator {

    private final Path projectPath;

    GitignoreCreator(Path projectPath) {this.projectPath = projectPath;}

    @Override
    public void create() {
        final String fileName = "gitignore";
        InputStream gitignore = ResourceFileLoader.load(fileName);
        try {
            Files.copy(gitignore, projectPath.resolve("." + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ActionException("An error occurred during .gitignore file creation");
        }
    }
}
