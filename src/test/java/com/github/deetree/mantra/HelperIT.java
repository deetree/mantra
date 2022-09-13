package com.github.deetree.mantra;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.testng.Assert.assertTrue;

@Test
public class HelperIT {

    private final Helper versionHelper = new VersionHelper(new CLIParser(new String[]{"-V"},
            new Arguments()).parse());
    private final Helper usageHelper = new UsageHelper(new CLIParser(new String[]{"-h"},
            new Arguments()).parse());
    private ByteArrayOutputStream output;

    @BeforeMethod
    private void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterMethod
    private void cleanUp() {
        System.setOut(System.out);
    }

    public void shouldPrintUsageHelp() {
        usageHelper.printHelp();
        assertTrue(output.toString().contains("Usage"));
    }

    public void shouldPrintVersionHelp() {
        versionHelper.printHelp();
        assertTrue(output.toString().contains("Mantra version"));
    }
}
