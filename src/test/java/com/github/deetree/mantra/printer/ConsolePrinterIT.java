package com.github.deetree.mantra.printer;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Test
public class ConsolePrinterIT {

    private final Printer printer = new ConsolePrinter();
    private ByteArrayOutputStream output;

    @BeforeMethod
    public void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterMethod
    public void cleanUp() {
        System.setOut(System.out);
    }

    public void shouldPrintTextWithLevel() {
        //g
        Level level = Level.WARNING;
        String message = "testMessage";
        //w
        printer.print(new Message(level, message));
        //t
        String outputString = output.toString();
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(outputString.contains(level.name()));
        sa.assertTrue(outputString.contains(message));
        sa.assertAll();
    }
}