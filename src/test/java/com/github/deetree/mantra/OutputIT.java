package com.github.deetree.mantra;

import com.github.deetree.mantra.printer.Level;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class OutputIT {

    private ByteArrayOutputStream output;
    private ByteArrayOutputStream error;
    private final String mockConfigInput = IntStream.range(0, 7).mapToObj(i -> System.lineSeparator())
            .collect(Collectors.joining());
    private final String mockInteractiveInput = IntStream.range(0, 11).mapToObj(i -> System.lineSeparator())
            .collect(Collectors.joining());

    @BeforeMethod
    public void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @BeforeMethod(onlyForGroups = "config")
    public void setUpMockConfigInput() {
        System.setIn(new ByteArrayInputStream(mockConfigInput.getBytes()));
    }

    @BeforeMethod(onlyForGroups = "interactive")
    public void setUpMockInteractiveInput() {
        System.setIn(new ByteArrayInputStream(mockInteractiveInput.getBytes()));
    }

    @BeforeMethod(onlyForGroups = "error")
    public void setUpErrorStream() {
        error = new ByteArrayOutputStream();
        System.setErr(new PrintStream(error));
    }

    @AfterMethod
    public void cleanUp() {
        System.setOut(System.out);
    }

    @AfterMethod(onlyForGroups = "config")
    public void cleanUpMockConfigInput() {
        System.setIn(System.in);
    }

    @AfterMethod(onlyForGroups = "interactive")
    public void cleanUpMockInteractiveInput() {
        System.setIn(System.in);
    }

    @AfterMethod(onlyForGroups = "error")
    public void cleanUpErrorStream() {
        System.setErr(System.err);
    }

    public void shouldPrintOutputHelperFlag() {
        //g
        String[] args = new String[]{"-h"};
        //w
        Main.main(args);
        //t
        assertFalse(output.toString().isEmpty(), "When helper flag is provided, " +
                "the output should be printed out");
    }

    @Test(groups = "config")
    public void shouldPrintOutputConfigFlag() {
        //g
        String[] args = new String[]{"-c"};
        //w
        Main.main(args);
        //t
        assertFalse(output.toString().isEmpty(), "When config flag is provided, " +
                "the output should be printed out");
    }

    @Test(groups = "config")
    public void shouldPrintOutputConfigSilentFlag() {
        //g
        String[] args = new String[]{"-c", "-s"};
        //w
        Main.main(args);
        //t
        assertFalse(output.toString().isEmpty(), "When config and silent flag is provided, " +
                "the output should be printed out");
    }

    public void shouldNotPrintOutputSilentFlag() throws IOException {
        //g
        String name = "testProjectShouldNotPrintSilentFlag";
        String[] args = new String[]{"-d", SystemProperty.TMP_DIR.toString(), name, "-s", "-l",
                "-u", "user", "-e", "email"};
        //w
        Main.main(args);
        //t
        assertTrue(output.toString().isEmpty(), "When silent flag is provided, " +
                "the output should not be printed out");
        Remover.deleteDirectory(Path.of(SystemProperty.TMP_DIR.toString(), name));
    }

    public void shouldPrintErrorsSilentFlag() throws IOException {
        //g
        String name = "testProjectShouldPrintErrorsSilentFlag";
        String[] args = new String[]{"-d", SystemProperty.TMP_DIR.toString(), name, "-s", "-l"};
        //w
        Main.main(args);
        Main.main(args);
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertFalse(output.toString().isEmpty());
        sa.assertTrue(output.toString().contains(Level.ERROR.toString()));
        sa.assertFalse(output.toString().contains(Level.INFO.toString()));
        sa.assertAll("When silent flag is provided only errors should be printed out");
        Remover.deleteDirectory(Path.of(SystemProperty.TMP_DIR.toString(), name));
    }

    public void shouldPrintOutputWithoutSilentFlag() throws IOException {
        //g
        String name = "testProjectShouldPrintOutputWithoutSilentFlag";
        String[] args = new String[]{"-d", SystemProperty.TMP_DIR.toString(), name, "-l"};
        //w
        Main.main(args);
        //t
        assertFalse(output.toString().isEmpty(), "When no silent flag is provided, " +
                "the output should be printed out");
        Remover.deleteDirectory(Path.of(SystemProperty.TMP_DIR.toString(), name));
    }

    @Test(groups = "error")
    public void shouldOnlyPrintToStandardOutputWhenHelpRequested() {
        //g
        String[] args = new String[]{"-h"};
        //w
        Main.main(args);
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertFalse(output.toString().isEmpty(), "When help flag is provided the info " +
                "should be written to the standard output stream");
        sa.assertTrue(error.toString().isEmpty(), "When help flag is provided nothing " +
                "should be written to the error stream");
        sa.assertAll();
    }

    @Test(groups = "error")
    public void shouldPrintUnknownOptionWithUsageWhenWrongFlag() {
        //g
        String[] args = new String[]{"-c", "-x"};
        //w
        Main.main(args);
        //t
        String errorStreamOutput = error.toString();
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(errorStreamOutput.contains("Unknown option"), "When unknown option is provided " +
                "the appropriate error should be printed out to the error stream");
        sa.assertTrue(errorStreamOutput.contains("Usage:"), "When unknown option is provided " +
                "the usage help should be printed out to the error stream");
        sa.assertAll();
    }

    @Test(groups = "error")
    public void shouldOnlyPrintToErrorStreamWhenWrongFlag() {
        //g
        String[] args = new String[]{"-d", SystemProperty.TMP_DIR.toString(), "test", "-x"};
        //w
        Main.main(args);
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertFalse(error.toString().isEmpty(), "When wrong flag is provided the info " +
                "should be written to the error stream");
        sa.assertTrue(output.toString().isEmpty(), "When wrong flag is provided nothing " +
                "should be written to the standard output stream");
        sa.assertAll();
    }

    @Test(groups = "error")
    public void shouldPrintUsageHelpToOutputStreamIfHelpAndInteractiveFlag() {
        //g
        String[] args = new String[]{"-h", "-i"};
        //w
        Main.main(args);
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(output.toString().contains("Usage"), "When help and interactive flags are provided " +
                "the app should not enter interactive mode and just print usage help");
        sa.assertFalse(output.toString().isEmpty(), "When help flag is provided the info " +
                "should be written to the standard output stream");
        sa.assertTrue(error.toString().isEmpty(), "When help flag is provided nothing " +
                "should be written to the error stream");
        sa.assertAll();
    }

    @Test(groups = "error")
    public void shouldPrintVersionHelpToOutputStreamIfHelpAndInteractiveFlag() {
        //g
        String[] args = new String[]{"-V", "-i"};
        //w
        Main.main(args);
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(output.toString().contains("version"), "When help and interactive flags are provided " +
                "the app should not enter interactive mode and just print version help");
        sa.assertFalse(output.toString().isEmpty(), "When help flag is provided the info " +
                "should be written to the standard output stream");
        sa.assertTrue(error.toString().isEmpty(), "When help flag is provided nothing " +
                "should be written to the error stream");
        sa.assertAll();
    }

    @Test(groups = "interactive")
    public void shouldPrintPromptsWhenInteractiveFlag() {
        //g
        String[] args = new String[]{"-i"};
        //w
        Main.main(args);
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(output.toString().toLowerCase().contains("interactive"), "When interactive " +
                "mode flag is provided the app should prompt for properties");
        sa.assertTrue(output.toString().contains(Level.SYSTEM.name()), "The prompts should be printed on " +
                "SYSTEM level");
        sa.assertAll();
    }
}
