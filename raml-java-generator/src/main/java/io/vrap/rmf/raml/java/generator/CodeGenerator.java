package io.vrap.rmf.raml.java.generator;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.TypeContainer;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class CodeGenerator {
    private final static URL apiUrl = TypesGenerator.class.getResource("/commercetools-api-reference-master/api.raml");

    public static void main(String... args) {
        final long startTimeMillis = System.currentTimeMillis();
        final File generateTo = new File("./demo/src/main/java-gen");

        final URI fileURI = URI.createFileURI("/Users/mkoester/Development/commercetools-api-reference/api.raml");

        final Resource resource = new RamlResourceSet()
                .getResource(fileURI, true);
        final EList<EObject> contents = resource.getContents();
        final EList<Resource.Diagnostic> errors = resource.getErrors();

        if (errors.isEmpty() && contents.size() == 1) {
            final EObject rootObject = contents.get(0);
            if (rootObject instanceof TypeContainer) {
                final TypeContainer typeContainer = (TypeContainer) rootObject;
                final TypesGenerator typesGenerator = new TypesGenerator("types", generateTo);
                typesGenerator.generate(typeContainer);
                final long endTimeMillis = System.currentTimeMillis();

                final Duration duration = Duration.ofMillis(endTimeMillis - startTimeMillis);
                System.out.println("Generation took:" + duration);
            } else {
                System.err.println("Invalid root object:" + rootObject.eClass().getName());
            }
            if (rootObject instanceof Api) {
                final Api api = (Api) rootObject;
                if (api.getResources().size() == 1) {
                    QueriesGenerator queriesGenerator = new QueriesGenerator("queries", generateTo);
                    queriesGenerator.generate(api.getResources().get(0));
                }
            }
        } else if (contents.isEmpty()) {
            System.err.println("File '" + fileURI + "' is empty");
        } else {
            errors.forEach(diagnostic -> System.err.println(diagnostic.getMessage()));
        }
    }
}
