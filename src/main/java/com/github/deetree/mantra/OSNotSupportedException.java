package com.github.deetree.mantra;

/**
 * Exception used when the machine's operating system is not supported by the app.
 *
 * @author Mariusz Bal
 */
class OSNotSupportedException extends RuntimeException {

    OSNotSupportedException(String message) {
        super(message);
    }
}
