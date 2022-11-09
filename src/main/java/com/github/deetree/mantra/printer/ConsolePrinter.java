package com.github.deetree.mantra.printer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Console output printer.
 *
 * @author Mariusz Bal
 */
class ConsolePrinter implements Printer {

    private final Collection<Message> suspendedMessages = new ArrayList<>();
    private boolean printingSuspended;

    @Override
    public void print(Message message) {
        if (printingSuspended)
            suspendedMessages.add(message);
        else
            printout(message);
    }

    private void printout(Message message) {
        System.out.println(message.toString());
    }

    @Override
    public void suspendPrinting() {
        printingSuspended = true;
    }

    @Override
    public void resumePrinting() {
        printingSuspended = false;
        suspendedMessages.forEach(this::printout);
        suspendedMessages.clear();
    }

    @Override
    public void printErrors() {
        suspendedMessages.stream().filter(m -> m.level() == Level.ERROR).forEach(this::printout);
    }
}
