package com.github.deetree.mantra.config;

import com.github.deetree.mantra.Result;

/**
 * @author Mariusz Bal
 */
public interface Configuration {
    ConfigValues load();

    Result createConfigFile();
}
