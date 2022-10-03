package com.github.deetree.mantra;

/**
 * API for user input reading.
 *
 * @author Mariusz Bal
 */
@FunctionalInterface
public interface Reader {

    /**
     * Create default reader instance.
     *
     * @return default reader instance
     */
    static Reader getDefault() {
        return new ConsoleReader(System.in);
    }

    /**
     * Read line of user input.
     *
     * @return user input line
     */
    String readLine();
}
