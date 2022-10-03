package com.github.deetree.mantra.printer;

/**
 * ANSII colors for printing colored output.
 *
 * @author Mariusz Bal
 */
enum Color {
    RED("\033[1;31m"),
    YELLOW("\033[1;33m"),
    BLUE("\033[1;34m"),
    GREEN("\033[1;32m"),
    RESET("\033[0m");

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
