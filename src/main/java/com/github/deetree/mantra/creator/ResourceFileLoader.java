package com.github.deetree.mantra.creator;

import java.io.InputStream;

/**
 * Resource file loader.
 *
 * @author Mariusz Bal
 */
interface ResourceFileLoader {

    /**
     * Load file template from resources.
     *
     * @param fileName resources file name
     * @return resource file's input stream
     */
    static InputStream load(String fileName) {
        return ResourceFileLoader.class.getResourceAsStream("/" + fileName);
    }
}
