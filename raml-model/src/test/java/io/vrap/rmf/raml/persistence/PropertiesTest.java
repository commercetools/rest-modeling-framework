package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.model.types.Library;
import io.vrap.rmf.raml.model.types.ObjectType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesTest implements ResourceFixtures {

    @Ignore
    @Test
    public void inlineTypeDeclaration() throws IOException {
        final Resource resource = fromClasspath("/properties/inline-type-declaration.raml");
        assertThat(resource.getErrors()).hasSize(0);

        final EList<EObject> contents = resource.getContents();

        assertThat(contents).hasSize(1);
    }

    @Test
    public void requiredProperties() throws IOException {
        final Resource resource = fromClasspath("/properties/required-properties.raml");
        assertThat(resource.getErrors()).hasSize(0);

        final Library library = getRootObject(resource);
        assertThat(library.getTypes()).hasSize(1);
        assertThat(library.getTypes().get(0)).isInstanceOf(ObjectType.class);

        final ObjectType objectType = (ObjectType) library.getTypes().get(0);
        assertThat(objectType.getProperty("optional").getRequired()).isFalse();
        assertThat(objectType.getProperty("optional?").getRequired()).isFalse();

        assertThat(objectType.getProperty("required").getRequired()).isTrue();
        assertThat(objectType.getProperty("required?").getRequired()).isTrue();

        assertThat(objectType.getProperty("??").getRequired()).isFalse();
    }
}
