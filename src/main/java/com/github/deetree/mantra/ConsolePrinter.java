package com.github.deetree.mantra;

/**
 * @author Mariusz Bal
 */
class ConsolePrinter implements Printer {

    @Override
    public void print(Level level, String text) {
        System.out.printf("[%s%s%s] %s%n", level.color().toString(), level.name(), Color.RESET, text);
    }

    @Override
    public void print(String text) {
        System.out.println(text);
    }
}
