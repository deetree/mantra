package com.github.deetree.mantra.printer;

/**
 * Message class for user output.
 *
 * @author Mariusz Bal
 */
public record Message(Level level, String text) {

    @Override
    public String toString() {
        return "[%s%s%s] %s".formatted(level().color().toString(), level().name(), Color.RESET, text());
    }
}
