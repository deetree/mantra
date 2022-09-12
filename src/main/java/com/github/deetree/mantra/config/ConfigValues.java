package com.github.deetree.mantra.config;

/**
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
