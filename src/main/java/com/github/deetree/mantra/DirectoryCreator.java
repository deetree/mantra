package com.github.deetree.mantra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
interface DirectoryCreator {

    Result create();

    default Result createDirectories(Path path) {
        try {
            Files.createDirectories(path);
            return Result.OK;
        } catch (IOException e) {
            System.err.println("An error occurred while creating directories structure. Check the path.");
        }
        return Result.ERROR;
    }
}
