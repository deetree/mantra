package com.github.deetree.mantra;

/**
 * @author Mariusz Bal
 */
class OperatingSystem {

    OS identify() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows"))
            return OS.WINDOWS;
        else if (os.contains("nux") || os.contains("nix"))
            return OS.LINUX;
        else throw new OSNotSupportedException("This operating system (%s) is not supported".formatted(os));
    }
}
