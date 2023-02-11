package com.github.deetree.mantra;

/**
 * System properties for being reused easily.
 *
 * @author Mariusz Bal
 */
public enum SystemProperty {
    USER_DIR(System.getProperty("user.dir")),
    USER_HOME(System.getProperty("user.home")),
    TMP_DIR(System.getProperty("java.io.tmpdir"));

    private final String property;

    SystemProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return property;
    }
}
