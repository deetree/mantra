package com.github.deetree.mantra;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Test
public class TestClassCreatorIT {

    public void shouldContainSubstitutedValues() throws IOException {
        //g
        Path path = Path.of(System.getProperty("user.home"));
        String group = "testGroup";
        String artifact = "testArtifact";
        String main = "MainClass";
        Path testClassFile = path.resolve(main + "Test.java");
        //w
        new TestClassCreator(path, group, artifact, main).create();
        //t
        String content = Files.readString(testClassFile);
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(content.contains("package %s.%s;".formatted(group, artifact)),
                "The file should contain proper package name");
        sa.assertTrue(content.contains("class %sTest".formatted(main)),
                "The file should contain proper class name");
        sa.assertTrue(content.contains("assertTrue(true);"),
                "The file should contain sample assertion");
        sa.assertAll();
        Files.deleteIfExists(testClassFile);
    }
}
