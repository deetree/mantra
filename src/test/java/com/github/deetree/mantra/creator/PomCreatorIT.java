package com.github.deetree.mantra.creator;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.testng.Assert.assertEquals;

@Test
public class PomCreatorIT {

    private final Path path = Path.of(System.getProperty("user.home"));
    private final Path pomPath = path.resolve("pom.xml");
    private final String group = "testGroup";
    private final String artifact = "testArtifact";
    private final String main = "TestMainClass";
    private final String java = "15";

    @BeforeClass
    private void setUp() {
        new PomCreator(path, group, artifact, main, java).create();
    }

    @AfterClass
    private void cleanUp() throws IOException {
        Files.deleteIfExists(pomPath);
    }

    public void shouldCreatePomFile() {
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(Files.exists(pomPath), "The pom.xml file should exist");
        sa.assertTrue(Files.isRegularFile(pomPath), "The pom.xml should be a file");
        sa.assertAll();
    }

    @Test(dependsOnMethods = "shouldCreatePomFile")
    public void shouldContainSubstitutedGroupId() {
        String actual = readPomValue("groupId");
        assertEquals(actual, group, "The groupId is not as expected");
    }

    @Test(dependsOnMethods = "shouldCreatePomFile")
    public void shouldContainSubstitutedArtifactId() {
        String actual = readPomValue("artifactId");
        assertEquals(actual, artifact, "The artifactId is not as expected");
    }

    @Test(dependsOnMethods = "shouldCreatePomFile")
    public void shouldContainSubstitutedJavaVersion() {
        String actual = readPomValue("java.version");
        assertEquals(actual, java, "The java version is not as expected");
    }

    @Test(dependsOnMethods = "shouldCreatePomFile")
    public void shouldContainSubstitutedMainClass() {
        String actual = readPomValue("mainClass");
        assertEquals(actual, "%s.%s.%s".formatted(group, artifact, main),
                "The main class is not as expected");
    }

    private String readPomValue(String tag) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(pomPath.toFile());
            return document.getElementsByTagName(tag).item(0).getTextContent();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            return "";
        }
    }
}