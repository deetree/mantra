package com.github.deetree.mantra.printer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

/**
 * @author Mariusz Bal
 */
class ConsolePrinter implements Printer {

    private boolean printingSuspended;
    private final Collection<String> suspendedMessages = new ArrayList<>();

    @Override
    public void print(Level level, String text) {
        print("[%s%s%s] %s%n".formatted(level.color().toString(), level.name(), Color.RESET, text));
    }

    @Override
    public void print(String text) {
        if (printingSuspended)
            suspendedMessages.add(text);
        else
            printout(text);
    }

    private void printout(String text) {
        System.out.print(text);
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
}
