package io.vrap.rmf.raml.persistence;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class RamlTckTest implements ResourceFixtures {

    @Test
    public void tckFilesParse() throws IOException {
        Files.walk(Paths.get("./build/raml-tck-1.1/tests/raml-1.0"))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".raml"))
                .forEach((f)  -> {
                    final URI fileURI = URI.createURI(f.toFile().toURI().toString());
                    try {
                        final Resource resource = fromUri(fileURI);
                        assertThat(resource).isInstanceOf(Resource.class)
                                .overridingErrorMessage("Failed to parse: " + f.toString());
                    } catch (Exception e) {
                        System.out.println("Exception:" + e.getMessage());
                    }
                });
    }

    @Test
    public void tckInvalidRaml() throws IOException {
        Files.walk(Paths.get("./build/raml-tck-1.1/tests/raml-1.0"))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith("Invalid.raml"))
                .forEach((f)  -> {
                    final URI fileURI = URI.createURI(f.toFile().toURI().toString());
                    try {
                        final Resource resource = fromUri(fileURI);
                        assertThat(resource.getErrors())
                            .overridingErrorMessage("No errors found parsing invalid raml: " + f.toString())
                            .isNotEmpty();
                    } catch (Exception e) {
                        System.out.println("Exception:" + e.getMessage() + " (" + f.toString() + ")");
                    }
                });
    }


    @Test
    public void tckValidRaml() throws IOException {
        Files.walk(Paths.get("./build/raml-tck-1.1/tests/raml-1.0"))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith("Valid.raml"))
                .forEach((f)  -> {
                    final URI fileURI = URI.createURI(f.toFile().toURI().toString());
                    try {
                    final Resource resource = fromUri(fileURI);
                    assertThat(resource.getErrors())
                        .overridingErrorMessage("Errors found parsing valid raml: " + f.toString())
                        .isEmpty();
                    } catch (Exception e) {
                        System.out.println("Exception:" + e.getMessage() + " (" + f.toString() + ")");
                    }
                });
    }

    @Test
    public void tckTest() throws IOException {
        Files.walk(Paths.get("./build/raml-tck-1.1/tests/raml-1.0"))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith("api.raml"))
                .forEach((f)  -> {
                    final URI fileURI = URI.createURI(f.toFile().toURI().toString());
                    try {
                        final Resource resource = fromUri(fileURI);
                        if (!resource.getErrors().isEmpty()) {
                            String file = fileURI.toString();
                            System.out.println(file);
                        }
                    } catch (Exception e) {
                        System.out.println("Exception:" + e.getMessage() + " (" + f.toString() + ")");
                    }
                });
    }

}
