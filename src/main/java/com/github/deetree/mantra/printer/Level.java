package com.github.deetree.mantra.printer;

/**
 * Level of severity for printing out different tags and colors related to them.
 *
 * @author Mariusz Bal
 */
public enum Level {
    ERROR(Color.RED),
    WARNING(Color.YELLOW),
    SUCCESS(Color.GREEN),
    INFO(Color.BLUE);

    private final Color color;

    /**
     * Level representation.
     *
     * @param color ansii color representation
     */
    Level(Color color) {
        this.color = color;
    }

    Color color() {
        return color;
    }
}
