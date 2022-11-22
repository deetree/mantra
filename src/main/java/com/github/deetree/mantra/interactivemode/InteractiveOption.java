package com.github.deetree.mantra.interactivemode;

/**
 * Option that is used to interactively create the project along with prompts and expected types.
 *
 * @author Mariusz Bal
 */
record InteractiveOption(String flag, String prompt, Class<?> type) {

    @Override
    public String toString() {
        return "%s [%s]".formatted(prompt, type == Boolean.class ? "y/N" : type.getSimpleName());
    }
}
