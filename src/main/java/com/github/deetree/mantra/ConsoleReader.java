package com.github.deetree.mantra;

import java.io.InputStream;
import java.util.Scanner;

/**
 * System's text console input reader.
 *
 * @author Mariusz Bal
 */
class ConsoleReader implements Reader {

    private final Scanner scanner;

    /**
     * Create console user input reader.
     *
     * @param inputStream input stream which is used to read the input
     */
    ConsoleReader(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    /**
     * Read line from console.
     *
     * @return line that has been read
     */
    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
