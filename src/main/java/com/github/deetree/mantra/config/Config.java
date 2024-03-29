package com.github.deetree.mantra.config;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Reader;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.io.File;
import java.util.Properties;

import static com.github.deetree.mantra.config.PropertyName.LAUNCHER;

/**
 * Entry point for mantra configuration creating.
 * Allows to set the default values that will be used in every app run.
 *
 * @author Mariusz Bal
 */
public class Config implements Configuration {

    private final File configFile;
    private final ConfigValues configValues;
    private final OS os;
    private final Printer printer;

    /**
     * Create configuration object for performing config related tasks.
     *
     * @param configFile   configuration file
     * @param configValues configuration defaults transferring object
     * @param os           operating system
     * @param printer      printer for user output printing
     */
    public Config(File configFile, ConfigValues configValues, OS os, Printer printer) {
        this.configFile = configFile;
        this.configValues = configValues;
        this.os = os;
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
            properties.setProperty(LAUNCHER.toString(), configValues.launcher());
            return new ConfigWriter(configFile).createConfig(properties);
        }
        return Result.ERROR;
    }

    @Override
    public Result configureDefaults() {
        Properties properties = new Properties();
        Thread launcherFindThread = createLauncherFindingThread(properties);
        launcherFindThread.setDaemon(true);
        launcherFindThread.start();
        return new InteractiveConfiguration(Reader.getDefault(), configFile, properties, printer).configure();
    }

    private Thread createLauncherFindingThread(Properties properties) {
        return new Thread(() -> {
            String launcherPath = new IdeLauncherAutoSave(os, printer).findPath();
            properties.setProperty(LAUNCHER.toString(), properties.getProperty(LAUNCHER.toString(), launcherPath));
        }, "launcher-find-thread");
    }

    private boolean configExists() {
        return configFile.exists();
    }
}
