package io.vrap.rmf.raml.test;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.vrap.rmf.raml.model.RamlModelBuilder;
import io.vrap.rmf.raml.model.RamlModelResult;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class RamlTckTest implements ResourceFixtures {
    private class TckParseException extends Exception {
        public TckParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    private static final String TCK_VERSION = "1.1";
    private static final String RAML_VERSION = "1.0";
    private static final String RAML_TCK_VERSION = Optional.ofNullable(System.getenv("RAML_TCK_VERSION")).orElse(TCK_VERSION);
    private final static URL tckURL = RamlTckTest.class.getResource("/raml-tck-" + RAML_TCK_VERSION + "/tests/raml-" + RAML_VERSION);

    @Test
    @UseDataProvider("allTckRamlFiles")
    public void tckFilesParse(final File f) throws TckParseException {
        tckParse(f);
    }

    @Test
    @UseDataProvider("allTckInvalidRamlFiles")
    public void tckInvalidRaml(final File f) throws TckParseException  {
        tckParse(f, false);
    }


    @Test
    @UseDataProvider("allTckValidRamlFiles")
    public void tckValidRaml(final File f) throws TckParseException  {
        tckParse(f, true);
    }

    @Test
    @UseDataProvider("allTckApiRamlFiles")
    public void tckTest(final File f) throws TckParseException  {
        tckParse(f);
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
                .filter(path -> path.toString().toLowerCase().endsWith("invalid.raml")).map(Path::toFile).collect(Collectors.toList());
    }

    @DataProvider
    public static List<File> allTckValidRamlFiles() throws IOException {
        return Files.walk(Paths.get(tckURL.getPath()))
                .filter(Files::isRegularFile)
                .filter(path -> !path.toString().toLowerCase().endsWith("invalid.raml") && path.toString().toLowerCase().endsWith("valid.raml")).map(Path::toFile).collect(Collectors.toList());
    }

    @DataProvider
    public static List<File> allTckApiRamlFiles() throws IOException {
        return Files.walk(Paths.get(tckURL.getPath()))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith("api.raml")).map(Path::toFile).collect(Collectors.toList());
    }

    public void tckParse(final File f, final Boolean valid) throws TckParseException {
        final URI fileURI = URI.createURI(f.toURI().toString());
        try {
            final RamlModelResult<EObject> result = new RamlModelBuilder().build(fileURI);
            if (valid) {
                assertThat(result.getValidationResults())
                        .describedAs(fileURI.toFileString().replace(tckURL.getPath(), "") + "(" + f.getName() + ":0)")
                        .isEmpty();
            } else {
                assertThat(result.getValidationResults())
                        .describedAs(fileURI.toFileString().replace(tckURL.getPath(), "") + "(" + f.getName() + ":0)")
                        .isNotEmpty();
            }
        } catch (Throwable e) {
            throw new TckParseException(f.toString() + "(" + f.getName() + ":0)", e);
        }
    }

    public void tckParse(final File f) throws TckParseException {
        final URI fileURI = URI.createURI(f.toURI().toString());
        try {
            final RamlModelResult<EObject> result = new RamlModelBuilder().build(fileURI);
            assertThat(result.getRootObject() != null);
        } catch (Throwable e) {
            throw new TckParseException(f.toString() + "(" + f.getName() + ":0)", e);
        }
    }
}
