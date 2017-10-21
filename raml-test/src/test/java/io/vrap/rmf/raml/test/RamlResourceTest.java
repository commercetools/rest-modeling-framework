package io.vrap.rmf.raml.test;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.types.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

public class RamlResourceTest implements ResourceFixtures {
    private final static URL tckURL = RamlResourceTest.class.getResource("/raml-tck-wip-2.0.0/tests");

    @Test
    public void typesWithEmptyInclude() {
        final URI uri = URI.createURI(tckURL.toString() + "/spec-examples/raml-1.0/resourcetypes-traits-parameterfunctions.raml");
        final Resource resource = fromUri(uri);
        assertThat(resource.getErrors()).isEmpty();
    }

    @Test
    public void api() {
        final URI apiUri = uriFromClasspath("/commercetools-api-reference-master/api.raml");
        final Resource resource = fromUri(apiUri);
        assertThat(resource).isNotNull();
        assertThat(resource.getErrors()).isEmpty();

        final EList<EObject> contents = resource.getContents();
        final Api api = (Api) contents.get(0);

        final EList<AnyType> types = api.getTypes();
        for (final AnyType anyType : types) {
            assertThat(anyType.eIsProxy()).isFalse();
        }
    }

    @Test
    public void apiSuperCrossReferencer() {
        final File apiFile = new File("/Users/mkoester/Development/commercetools-api-reference/api.raml");
        assumeTrue(apiFile.exists());

        final URI fileURI = URI.createURI(apiFile.toURI().toString());
        final Resource resource = fromUri(fileURI);
        assertThat(resource).isNotNull();
        assertThat(resource.getErrors()).isEmpty();

        final EList<EObject> contents = resource.getContents();
        final Api api = (Api) contents.get(0);
        final Optional<AnyType> optionalDestinationType = api.getTypes().stream()
                .filter(anyType -> "Destination".equals(anyType.getName()))
                .findFirst();

        assertThat(optionalDestinationType.isPresent());
        final AnyType destinationType = optionalDestinationType.get();
        final List<AnyType> subTypes = destinationType.subTypes();
        assertThat(subTypes).hasSize(3);
    }

    @Ignore
    @Test
    public void apiViaHttp() {
        final URI uri = URI.createURI("http://localhost:5050/api-raml/");
        final Resource resource = fromUri(uri);
        resource.getContents();
        assertThat(resource).isNotNull();
    }
}
