package com.github.deetree.mantra.config;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.Result;

import java.io.*;
import java.util.Properties;

/**
 * @author Mariusz Bal
 */
class ConfigWriter {

    private final File configFile;

    ConfigWriter(File configFile) {
        this.configFile = configFile;
    }

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
