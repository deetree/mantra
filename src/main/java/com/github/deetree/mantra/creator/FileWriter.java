package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class FileWriter {

    private final Path toPath;
    private final String text;

    FileWriter(Path toPath, String text) {
        this.toPath = toPath;
        this.text = text;
    }

    Result write() throws IOException {
        Files.write(toPath, text.getBytes());
        return Result.OK;
    }
}
