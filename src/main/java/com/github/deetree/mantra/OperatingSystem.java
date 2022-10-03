package com.github.deetree.mantra;

/**
 * Computer's operating system detector.
 *
 * @author Mariusz Bal
 */
public class OperatingSystem {

    /**
     * Identify operating system on which the app is being run.
     *
     * @return operating system identified
     */
    public OS identify() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows"))
            return OS.WINDOWS;
        else if (os.contains("nux") || os.contains("nix"))
            return OS.LINUX;
        else throw new OSNotSupportedException("This operating system (%s) is not supported".formatted(os));
    }
}
