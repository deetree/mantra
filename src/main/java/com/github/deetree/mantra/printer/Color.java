package com.github.deetree.mantra.printer;

/**
 * ANSI colors for printing colored output.
 *
 * @author Mariusz Bal
 */
enum Color {
    BLUE("\033[1;34m"),
    CYAN("\033[1;36m"),
    GREEN("\033[1;32m"),
    RED("\033[1;31m"),
    RESET("\033[0m"),
    YELLOW("\033[1;33m");

    private final String ansiiColor;

    /**
     * Color representation.
     *
     * @param ansiiColor ansii color formatting
     */
    Color(String ansiiColor) {
        this.ansiiColor = ansiiColor;
    }

    @Override
    public String toString() {
        return ansiiColor;
    }
}
