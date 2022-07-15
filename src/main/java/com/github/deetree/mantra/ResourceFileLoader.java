package com.github.deetree.mantra;

import java.io.InputStream;

/**
 * @author Mariusz Bal
 */
interface ResourceFileLoader {

    static InputStream load(String fileName) {
        return ResourceFileLoader.class.getResourceAsStream(fileName);
    }
}
