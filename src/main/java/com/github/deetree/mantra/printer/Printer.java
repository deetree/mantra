package com.github.deetree.mantra.printer;

/**
 * @author Mariusz Bal
 */
public interface Printer {

    static Printer getDefault() {
        return new ConsolePrinter();
    }

    void print(Level level, String text);

    void print(String text);
}
