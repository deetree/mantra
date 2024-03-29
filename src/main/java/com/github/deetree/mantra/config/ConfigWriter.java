package com.github.deetree.mantra.config;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.Result;

import java.io.*;
import java.util.Properties;

/**
 * Writes the configuration file.
 *
 * @author Mariusz Bal
 */
class ConfigWriter {

    private final File configFile;

    /**
     * Create configuration file writer.
     *
     * @param configFile a file where the configuration will be stored
     */
    ConfigWriter(File configFile) {
        this.configFile = configFile;
    }

    /**
     * Create configuration file with given properties.
     *
     * @param properties properties to be stored in the config file
     * @return result of config writing
     */
    Result createConfig(Properties properties) {
        Result result = applyProperties(properties);
        if (properties.isEmpty())
            throw new ActionException("No config defaults have been provided");
        return result;
    }

    private Result applyProperties(Properties properties) {
        try {
            OutputStream outputStream = new FileOutputStream(configFile);
            properties.store(outputStream, "Mantra global configuration");
        } catch (FileNotFoundException e) {
            throw new ActionException("An error occurred during config file creating");
        } catch (IOException e) {
            throw new ActionException("An error occurred during config file content writing");
        }
        return Result.OK;
    }
}
