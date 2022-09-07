package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * @author Mariusz Bal
 */
interface DirectoryCreator extends Creator {

    String preExecuteStatus();

    @Override
    Result create();

    String postExecuteStatus();

    default Result createDirectories(Path path) {
        try {
            Files.createDirectories(path);
            return Result.OK;
        } catch (IOException e) {
            throw new ActionException("An error occurred while creating directories structure. Check the path.");
        }
    }
}
