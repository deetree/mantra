package com.github.deetree.mantra.config;

import com.github.deetree.mantra.Result;

/**
 * Mantra configuration API.
 *
 * @author Mariusz Bal
 */
public interface Configuration {

    /**
     * Load configuration from file.
     *
     * @return configuration file defaults
     */
    ConfigValues load();

    /**
     * Create configuration file.
     *
     * @return result of config file creating
     */
    Result createConfigFile();

    /**
     * Configure defaults interactively.
     *
     * @return result of interactive config creating
     */
    Result configureDefaults();
}
