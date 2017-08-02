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
    @UseDataProvider("tckSemanticFiles")
    public void semantic(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        tckParse(f, valid, description);
    }

    @Test
    @UseDataProvider("tckSpecExampleFiles")
    public void specExamples(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        tckParse(f, valid, description);
    }

    @Test
    @UseDataProvider("tckSyntaxFiles")
    public void syntax(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        tckParse(f, valid, description);
    }

    public void tckParse(final File f, final Boolean valid, final String description) throws IOException, TckParseException {
        final Resource resource;
        final URI fileURI = URI.createURI(f.toURI().toString());
        try {
            resource = fromUri(fileURI);
        } catch (Exception e) {
            throw new TckParseException(fileURI.toString(), e);
        }
        if (valid) {
            assertThat(resource.getErrors()).describedAs(fileURI.toFileString().replace(tckURL.getPath(), "") + "(" + f.getName() + ":0) " + description).isEmpty();
        } else {
            assertThat(resource.getErrors()).describedAs(fileURI.toFileString().replace(tckURL.getPath(), "") + "(" + f.getName() + ":0) " + description).isNotEmpty();
        }
    }

    @DataProvider
    public static List<List<Object>> tckSemanticFiles() throws IOException {
        return testFiles("semantic");
    }

    @DataProvider
    public static List<List<Object>> tckSpecExampleFiles() throws IOException {
        return testFiles("spec-examples");
    }

    @DataProvider
    public static List<List<Object>> tckSyntaxFiles() throws IOException {
        return testFiles("syntax");
    }

    @DataProvider
    public static List<List<Object>> testFiles(final String testSuite) throws IOException {
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
                        List<Object> a = Lists.newArrayList(
                                new File(file.getParentFile().getPath() + "/" + testDescription.getInput()),
                                testDescription.getValid(),
                                testDescription.getDescription()
                        );
                        map.add(a);
                    });
                }
            } catch (Exception e) {

            }
            return map;
        }).flatMap(List::stream).collect(Collectors.toList());
    }
}
