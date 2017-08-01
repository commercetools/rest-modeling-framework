package io.vrap.rmf.raml.persistence;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
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
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
@RunWith(DataProviderRunner.class)
public class RamlTckTest implements ResourceFixtures {

    private static final String TCK_VERSION = "1.1";
    private static final String RAML_VERSION = "1.0";
    private static final String RAML_TCK_VERSION = Optional.ofNullable(System.getenv("RAML_TCK_VERSION")).orElse(TCK_VERSION);
    private final static URL tckURL = RamlTckTest.class.getResource("/raml-tck-" + RAML_TCK_VERSION + "/tests/raml-" + RAML_VERSION);

    @Test
    @UseDataProvider("allTckRamlFiles")
    public void tckFilesParse(final File f) throws IOException {
        final URI fileURI = URI.createURI(f.toURI().toString());
        final Resource resource = fromUri(fileURI);
        assertThat(resource).isInstanceOf(Resource.class)
                .overridingErrorMessage("Failed to parse: " + f.toString());
    }

    @Test
    @UseDataProvider("allTckInvalidRamlFiles")
    public void tckInvalidRaml(final File f) throws IOException {
        final URI fileURI = URI.createURI(f.toURI().toString());
        final Resource resource = fromUri(fileURI);
        assertThat(resource.getErrors())
            .overridingErrorMessage("No errors found parsing invalid raml: " + f.toString())
            .isNotEmpty();
    }


    @Test
    @UseDataProvider("allTckValidRamlFiles")
    public void tckValidRaml(final File f) throws IOException {
        final URI fileURI = URI.createURI(f.toURI().toString());
        final Resource resource = fromUri(fileURI);
        assertThat(resource.getErrors())
            .overridingErrorMessage("Errors found parsing valid raml: " + f.toString())
            .isEmpty();
    }

    @Test
    @UseDataProvider("allTckApiRamlFiles")
    public void tckTest(final File f) throws IOException {
        final URI fileURI = URI.createURI(f.toURI().toString());
        final Resource resource = fromUri(fileURI);
        if (!resource.getErrors().isEmpty()) {
            String file = fileURI.toString();
            System.out.println(file);
        }
    }

    @DataProvider
    public static List<File> allTestRamlFiles() throws IOException {
        return Files.walk(Paths.get(tckURL.getPath()))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".raml")).map(Path::toFile).collect(Collectors.toList());
    }

    @DataProvider
    public static List<File> allTckRamlFiles() throws IOException {
        return Files.walk(Paths.get(tckURL.getPath()))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".raml")).map(Path::toFile).collect(Collectors.toList());
    }

    @DataProvider
    public static List<File> allTckInvalidRamlFiles() throws IOException {
        return Files.walk(Paths.get(tckURL.getPath()))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith("Invalid.raml")).map(Path::toFile).collect(Collectors.toList());
    }

    @DataProvider
    public static List<File> allTckValidRamlFiles() throws IOException {
        return Files.walk(Paths.get(tckURL.getPath()))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith("Valid.raml")).map(Path::toFile).collect(Collectors.toList());
    }

    @DataProvider
    public static List<File> allTckApiRamlFiles() throws IOException {
        return Files.walk(Paths.get(tckURL.getPath()))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith("api.raml")).map(Path::toFile).collect(Collectors.toList());
    }
}
