package com.github.deetree.mantra.config;

import com.github.deetree.mantra.Reader;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.io.File;
import java.util.Properties;

/**
 * @author Mariusz Bal
 */
public class Config implements Configuration {

    private final File configFile;
    private final ConfigValues configValues;
    private final Printer printer;

    public Config(File configFile, ConfigValues configValues, Printer printer) {
        this.configFile = configFile;
        this.configValues = configValues;
        this.printer = printer;
    }

    @Override
    public ConfigValues load() {
        Properties properties = new ConfigLoader(configFile).load();
        return new ConfigReader(properties, configValues).read();
    }

    @Override
    public Result createConfigFile() {
        if (!configExists()) {
            Properties properties = new Properties();
            properties.setProperty(PropertyName.LAUNCHER.toString(), "");
            return new ConfigWriter(configFile).createConfig(properties);
        }
        return Result.ERROR;
    }

    @Override
    public Result configureDefaults() {
        return new InteractiveConfiguration(Reader.getDefault(), configFile, printer).configure();
    }

    private boolean configExists() {
        return configFile.exists();
    }
}
