package com.github.deetree.mantra.config;

import com.github.deetree.mantra.Result;

import java.io.File;
import java.util.Properties;

/**
 * @author Mariusz Bal
 */
public class Config implements Configuration {

    private final File configFile;
    private final ConfigValues configValues;

    public Config(File configFile, ConfigValues configValues) {
        this.configFile = configFile;
        this.configValues = configValues;
    }

    @Override
    public ConfigValues load() {
        Properties properties = new ConfigLoader(configFile).load();
        return new ConfigReader(properties, configValues).read();
    }

    @Override
    public Result createConfigFile() {
        if (!configExists())
            return new ConfigWriter(configFile).createBasicConfig();
        return Result.ERROR;
    }

    private boolean configExists() {
        return configFile.exists();
    }
}
