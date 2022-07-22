package com.github.deetree.mantra;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
interface Printer {

    void print(Level level, String text);
}
