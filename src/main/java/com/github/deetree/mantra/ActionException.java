package com.github.deetree.mantra;

/**
 * Exception related to issues that occurred during performing different mantra tasks.
 *
 * @author Mariusz Bal
 */
public class ActionException extends RuntimeException {

    public ActionException(String message) {
        super(message);
    }
}
