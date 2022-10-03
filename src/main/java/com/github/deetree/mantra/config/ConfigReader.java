package com.github.deetree.mantra.config;

import java.util.Properties;

import static com.github.deetree.mantra.config.PropertyName.*;

/**
 * Reads the config values from properties
 *
 * @author Mariusz Bal
 */
class ConfigReader {

    private final Properties properties;
    private final ConfigValues configValues;

    ConfigReader(Properties properties, ConfigValues configValues) {
        this.properties = properties;
        this.configValues = configValues;
    }

    /**
     * Read the configuration properties.
     *
     * @return configuration values that were matched in config file or app defaults otherwise
     */
    ConfigValues read() {
        return new ConfigValues(
                properties.getProperty(DIRECTORY.toString(), configValues.directory()),
                properties.getProperty(GROUP.toString(), configValues.group()),
                properties.getProperty(ARTIFACT.toString(), configValues.artifact()),
                properties.getProperty(MAIN.toString(), configValues.main()),
                properties.getProperty(USERNAME.toString(), configValues.username()),
                properties.getProperty(EMAIL.toString(), configValues.email()),
                properties.getProperty(LAUNCHER.toString(), configValues.launcher())
        );
    }
}
