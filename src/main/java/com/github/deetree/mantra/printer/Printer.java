package com.github.deetree.mantra.printer;

/**
 * Output printing API.
 *
 * @author Mariusz Bal
 */
public interface Printer {

    /**
     * Default printer object creating.
     *
     * @return default printer object
     */
    static Printer getDefault() {
        return new ConsolePrinter();
    }

    /**
     * Print colorful message with level of severity.
     *
     * @param level level of severity
     * @param text  message to be printed out
     */
    void print(Message message);

    /**
     * Suspend output printing temporarily.
     */
    void suspendPrinting();

    /**
     * Resume output printing.
     */
    void resumePrinting();

    void printErrors();
}
