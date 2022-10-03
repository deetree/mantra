package com.github.deetree.mantra;

/**
 * Exception related to issues that occurred during performing different mantra tasks.
 *
 * @author Mariusz Bal
 */
public class ActionException extends RuntimeException {

    /**
     * Create exception when an error occurs while performing task
     *
     * @param message exception message
     */
    public ActionException(String message) {
        super(message);
    }
}
