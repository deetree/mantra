package com.github.deetree.mantra;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Test
public class MainClassCreatorIT {

    public void shouldContainSubstitutedValues() throws IOException {
        //g
        Path path = Path.of(System.getProperty("user.home"));
        String group = "testGroup";
        String artifact = "testArtifact";
        String main = "MainClass";
        Path mainClassFile = path.resolve(main + ".java");
        //w
        new MainClassCreator(path, group, artifact, main).create();
        //t
        String content = Files.readString(mainClassFile);
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(content.contains("package %s.%s;".formatted(group, artifact)),
                "The file should contain proper package name");
        sa.assertTrue(content.contains("class %s".formatted(main)),
                "The file should contain proper class name");
        sa.assertAll();
        Files.deleteIfExists(mainClassFile);
    }
}
