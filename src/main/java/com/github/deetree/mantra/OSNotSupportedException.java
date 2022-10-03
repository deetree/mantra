package com.github.deetree.mantra;

/**
 * Exception used when the machine's operating system is not supported by the app.
 *
 * @author Mariusz Bal
 */
class OSNotSupportedException extends RuntimeException {

    /**
     * Create exception when the OS is unsupported.
     *
     * @param message message
     */
    OSNotSupportedException(String message) {
        super(message);
    }
}
