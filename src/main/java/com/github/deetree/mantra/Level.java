package com.github.deetree.mantra;

/**
 * @author Mariusz Bal
 */
enum Level {
    ERROR(Color.RED),
    WARNING(Color.YELLOW),
    SUCCESS(Color.GREEN),
    INFO(Color.BLUE);

    private final Color color;

    Level(Color color) {
        this.color = color;
    }

    Color color() {
        return color;
    }
}
