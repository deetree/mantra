package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.OperatingSystem;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.testng.Assert.assertTrue;

@Test
public class LocalGitUserConfigCommandIT {

    private final OS os = new OperatingSystem().identify();
    private Path dirPath;
    private Path gitPath;

    @BeforeMethod
    private void setUp() throws IOException {
        dirPath = Files.createTempDirectory(Path.of(System.getProperty("user.home")), "tempGitTest");
        gitPath = dirPath.resolve(".git");
        new InitializeGitCommand(os, dirPath).execute();
    }

    @AfterMethod
    private void cleanUp() throws IOException {
        Files.walk(dirPath)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @DataProvider
    private Object[][] gitConfigDataProvider() {
        return new Object[][]{
                {"name", "James Bond", null},
                {"email", "", "test@test.com"},
        };
    }

    @Test(dataProvider = "gitConfigDataProvider")
    public void shouldConfigureSingleGitUserInfo(String configElement, String name, String email) throws IOException {
        //g
        //w
        new LocalGitUserConfigCommand(os, dirPath, name, email).execute();
        //t
        assertTrue(contains(configElement, name),
                "The git config should contain user.%s entry".formatted(configElement));
    }

    public void shouldConfigureAllUserInfo() throws IOException {
        //g
        //w
        String name = "test user name";
        String email = "user@email.com";
        new LocalGitUserConfigCommand(os, dirPath, name, email).execute();
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(contains("name", name),
                "The git config should contain user.name entry");
        sa.assertTrue(contains("email", email),
                "The git config should contain user.email entry");
        sa.assertAll("All user info should be configured");
    }

    private Stream<String> readConfigFileLines() throws IOException {
        return Files.readAllLines(gitPath.resolve("config")).stream();
    }

    private boolean contains(CharSequence element, CharSequence value) throws IOException {
        return readConfigFileLines().anyMatch(l -> l.contains(element) && l.contains(value));
    }

}