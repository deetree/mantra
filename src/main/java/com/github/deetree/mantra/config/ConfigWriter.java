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

    Result createBasicConfig() {
        Properties properties = new Properties();
        properties.setProperty(PropertyNames.LAUNCHER.toString(), "");
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
