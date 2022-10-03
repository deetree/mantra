package com.github.deetree.mantra.config;

import com.github.deetree.mantra.Reader;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.io.File;
import java.util.Arrays;
import java.util.Properties;

import static com.github.deetree.mantra.config.PropertyName.values;
import static com.github.deetree.mantra.printer.Level.INFO;

/**
 * Interactive configuration file creating.
 *
 * @author Mariusz Bal
 */
class InteractiveConfiguration {

    private final Reader reader;
    private final File configFile;
    private final Printer printer;
    private final Properties properties;

    /**
     * Create interactive configuration object.
     *
     * @param reader     user input reader
     * @param configFile configuration file
     * @param properties properties to be stored in the config file
     * @param printer    output printer
     */
    InteractiveConfiguration(Reader reader, File configFile, Properties properties, Printer printer) {
        this.reader = reader;
        this.configFile = configFile;
        this.properties = properties;
        this.printer = printer;
    }

    /**
     * Configure the defaults by prompting for each property.
     *
     * @return result of config file writing
     */
    Result configure() {
        printer.print(INFO, "Interactive config file creating mode. Existing configuration will be overridden.");
        printer.print(INFO, "Provide defaults for given parameters. Leave empty to skip.");
        Arrays.stream(values()).forEach(this::promptForProperty);
        return new ConfigWriter(configFile).createConfig(properties);
    }

    private void promptForProperty(PropertyName name) {
        printer.print(INFO, name.toString() + ":");
        printer.suspendPrinting();
        saveProperty(name, reader.readLine());
        printer.resumePrinting();
    }

    private boolean isEmpty(String input) {
        return input.trim().isEmpty();
    }

    private void saveProperty(PropertyName propertyName, String value) {
        if (!isEmpty(value))
            properties.setProperty(propertyName.toString(), value);
    }
}
