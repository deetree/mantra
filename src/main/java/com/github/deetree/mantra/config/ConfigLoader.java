package com.github.deetree.mantra.config;

import com.github.deetree.mantra.ActionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

/**
 * @author Mariusz Bal
 */
class ConfigLoader {

    private final File configFile;

    ConfigLoader(File configFile) {
        this.configFile = configFile;
    }

    Properties load() {
        try {
            FileInputStream propsInput = new FileInputStream(configFile);
            Properties properties = new Properties();
            properties.load(propsInput);
            return properties;
        } catch (FileNotFoundException e) {
            throw new ActionException("The config file could not be found");
        } catch (IOException e) {
            throw new ActionException("An error occurred during config file loading");
        }
    }
}
