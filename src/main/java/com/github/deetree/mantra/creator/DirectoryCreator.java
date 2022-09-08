package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.Status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
interface DirectoryCreator extends Creator, Status {

    default Result createDirectories(Path path) {
        try {
            Files.createDirectories(path);
            return Result.OK;
        } catch (IOException e) {
            throw new ActionException("An error occurred while creating directories structure. Check the path.");
        }
    }
}
