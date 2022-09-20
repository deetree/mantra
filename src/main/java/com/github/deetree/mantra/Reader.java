package com.github.deetree.mantra;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
public interface Reader {

    static Reader getDefault() {
        return new ConsoleReader(System.in);
    }

    String readLine();
}
