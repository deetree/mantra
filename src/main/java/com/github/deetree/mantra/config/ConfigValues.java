package com.github.deetree.mantra.config;

/**
 * Configuration defaults for transferring them between classes.
 *
 * @author Mariusz Bal
 */
public record ConfigValues(
        String directory,
        String group,
        String artifact,
        String main,
        String username,
        String email,
        String launcher
) {}
