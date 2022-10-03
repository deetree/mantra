package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Create a file with given content.
 *
 * @author Mariusz Bal
 */
class FileWriter {

    private final Path toPath;
    private final String text;

    FileWriter(Path toPath, String text) {
        this.toPath = toPath;
        this.text = text;
    }

    /**
     * Write content to the file
     *
     * @return result of file writing
     * @throws IOException if an I/O error occurs while writing to or creating the file
     */
    Result write() throws IOException {
        Files.write(toPath, text.getBytes());
        return Result.OK;
    }
}
