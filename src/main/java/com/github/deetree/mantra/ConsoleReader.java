package com.github.deetree.mantra;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Mariusz Bal
 */
class ConsoleReader implements Reader {

    private final Scanner scanner;

    ConsoleReader(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
