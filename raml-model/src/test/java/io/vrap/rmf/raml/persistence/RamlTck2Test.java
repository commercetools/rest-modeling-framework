package io.vrap.rmf.raml.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.assertj.core.util.Lists;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
@RunWith(DataProviderRunner.class)
public class RamlTck2Test implements ResourceFixtures {
    private class TckParseException extends Exception {
        public TckParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    private final static URL tckURL = RamlTck2Test.class.getResource("/raml-tck-wip-2.0.0/tests");

    @Test
    @UseDataProvider("tckValidSemanticFiles")
    public void validSemantic(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        tckParse(f, valid, description);
    }

    @Test
    @UseDataProvider("tckValidSpecExampleFiles")
    public void validSpecExamples(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        tckParse(f, valid, description);
    }

    @Test
    @UseDataProvider("tckValidSyntaxFiles")
    public void validSyntax(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        tckParse(f, valid, description);
    }

    @Test
    @UseDataProvider("tckInvalidSemanticFiles")
    public void invalidSemantic(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        tckParse(f, valid, description);
    }

    @Test
    @UseDataProvider("tckInvalidSpecExampleFiles")
    public void invalidSpecExamples(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        tckParse(f, valid, description);
    }

    @Test
    @UseDataProvider("tckInvalidSyntaxFiles")
    public void invalidSyntax(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        tckParse(f, valid, description);
    }

    public void tckParse(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        final Resource resource;
        final URI fileURI = URI.createURI(f.toURI().toString());
        try {
            resource = fromUri(fileURI);
        } catch (Exception e) {
            throw new TckParseException(f.toString() + "(" + f.getName() + ":0)", e);
        }
        if (valid) {
            assertThat(resource.getErrors()).describedAs(fileURI.toFileString().replace(tckURL.getPath(), "") + "(" + f.getName() + ":0) " + description).isEmpty();
        } else {
            assertThat(resource.getErrors()).describedAs(fileURI.toFileString().replace(tckURL.getPath(), "") + "(" + f.getName() + ":0) " + description).isNotEmpty();
        }
    }

    @DataProvider
    public static List<List<Object>> tckValidSemanticFiles() throws IOException {
        return testFiles("semantic", true);
    }

    @DataProvider
    public static List<List<Object>> tckValidSpecExampleFiles() throws IOException {
        return testFiles("spec-examples", true);
    }

    @DataProvider
    public static List<List<Object>> tckValidSyntaxFiles() throws IOException {
        return testFiles("syntax", true);
    }

    @DataProvider
    public static List<List<Object>> tckInvalidSemanticFiles() throws IOException {
        return testFiles("semantic", false);
    }

    @DataProvider
    public static List<List<Object>> tckInvalidSpecExampleFiles() throws IOException {
        return testFiles("spec-examples", false);
    }

    @DataProvider
    public static List<List<Object>> tckInvalidSyntaxFiles() throws IOException {
        return testFiles("syntax", false);
    }

    @DataProvider
    public static List<List<Object>> testFiles(final String testSuite, final Boolean valid) throws IOException {
        final List<File> files = Files.walk(Paths.get(tckURL.getPath()))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().contains("/" + testSuite + "/"))
                .filter(path -> path.toString().endsWith("test-config.json")).map(Path::toFile).collect(Collectors.toList());
        return files.stream().map(file -> {
            List<List<Object>> map = Lists.newArrayList();
            ObjectMapper mapper = new ObjectMapper();
            try {
                final TestConfig testConfig = mapper.readValue(file, TestConfig.class);
                if (testConfig.getTests() != null) {
                    testConfig.getTests().forEach(testDescription -> {
                        if (testDescription.getValid() != valid) {
                            return;
                        }
                        final File testRamlFile = new File(file.getParentFile().getPath() + "/" + testDescription.getInput() + ".raml");
                        if (testRamlFile.exists()) {
                            List<Object> a = Lists.newArrayList(
                                    testRamlFile,
                                    testDescription.getValid(),
                                    testDescription.getDescription()
                            );
                            map.add(a);
                        }
                    });
                }
            } catch (Exception e) {

            }
            return map;
        }).flatMap(List::stream).collect(Collectors.toList());
    }
}
