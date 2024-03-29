package com.github.deetree.mantra;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * Version provider for dynamic app version obtaining.
 * This version provider is used to display proper version help.
 * The version number is being read from JAR file's MANIFEST, where it is placed by Maven plugin.
 *
 * @author Mariusz Bal
 */
class VersionProvider implements CommandLine.IVersionProvider {

    @Override
    public String[] getVersion() {
        String versionHelp = "Mantra version %s";
        try (JarFile jarfile = new JarFile(new File(findRunningJarPath()))) {
            Attributes attributes = jarfile.getManifest().getMainAttributes();
            return new String[]{versionHelp.formatted(attributes.getValue("Implementation-Version"))};
        } catch (URISyntaxException | IOException e) {
            return new String[]{versionHelp.formatted("[unspecified]")};
        }
    }

    private String findRunningJarPath() throws URISyntaxException {
        return Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
    }
}
