package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.TypeContainer;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import sun.print.resources.serviceui;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class CodeGenerator {
    public static void main(String... args) throws IOException {
        final long startTimeMillis = System.currentTimeMillis();

        if (args.length == 2) {
            final URI fileURI = URI.createFileURI(args[0]);
            final File generateTo = new File(args[1]);
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
            } else if (contents.isEmpty()) {
                System.err.println("File '" + fileURI + "' is empty");
            } else {
                errors.forEach(diagnostic -> System.err.println(diagnostic.getMessage()));
            }
        } else {
            System.err.println("Usage: <api.raml> <targetDir>");
        }
    }
}
