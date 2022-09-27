package com.github.deetree.mantra.printer;

/**
 * @author Mariusz Bal
 */
public interface Printer {
    boolean locked = false;
    static Printer getDefault() {
        return new ConsolePrinter();
    }

    void print(Level level, String text);

    void print(String text);

    void suspendPrinting();

    void resumePrinting();
}
