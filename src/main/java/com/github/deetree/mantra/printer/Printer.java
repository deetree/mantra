package com.github.deetree.mantra.printer;

/**
 * @author Mariusz Bal
 */
public interface Printer {

    void print(Level level, String text);
    void print(String text);

    static Printer getDefault() {
        return new ConsolePrinter();
    }
}
