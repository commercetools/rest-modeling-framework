package io.vrap.rmf.raml.test;

import io.vrap.rmf.raml.persistence.RamlResourceSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Resource related test fixtures.
 */
public interface ResourceFixtures {

    /**
     * Gets a resource with the given name from the classpath.
     *
     * @param name the name of the resource
     * @return the resource
     * @throws IOException
     */
    default Resource fromClasspath(final String name) throws IOException {
        final URI uri = uriFromClasspath(name);
        return fromUri(uri);
    }

    default URI uriFromClasspath(final String name) {
        final URL url = RamlApiReferenceTest.class.getResource(name);
        return URI.createURI(url.toString());
    }

    /**
     * Gets a resource from the given uri.
     *
     * @param uri the resource uri
     * @return the resource
     */
    default Resource fromUri(final URI uri) {
        return new RamlResourceSet().getResource(uri, true);
    }

    @SuppressWarnings("unchecked")
	default <T> T getRootObject(final Resource resource) {
        final EList<EObject> contents = resource.getContents();

        assertThat(contents).hasSize(1);

        return (T) contents.get(0);
    }
}
