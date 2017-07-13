package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.model.annotations.StringAnnotationType;
import io.vrap.rmf.raml.model.facets.Property;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.ObjectType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for loading of fragments.
 */
public class TypedFragmentTest implements ResourceFixtures {

    @Test
    public void dataType() throws IOException {
        final Resource resource = fromClasspath("/data-type-fragment.raml");
        final ObjectType objectType = getRootObject(resource);

        assertThat(objectType.getName())
                .as("Name of a typed fragment")
                .isNull();

        assertThat(objectType.getDisplayName()).isEqualTo("Person");

        final EList<Property<AnyType>> properties = objectType.getProperties();
        assertThat(properties)
                .hasSize(1);

        final Property<AnyType> nameProperty = properties.get(0);
        assertThat(nameProperty.getName())
                .isEqualTo("age");
        assertThat(nameProperty.getType().getName())
                .isEqualTo("integer");
    }


    @Test
    public void annotationTypeDeclaration() throws IOException {
        final Resource resource = fromClasspath("/annotation-type-declaration-fragment.raml");
        final StringAnnotationType stringAnnotationType = getRootObject(resource);

        assertThat(stringAnnotationType.getName())
                .as("Name of a typed fragment")
                .isNull();

        assertThat(stringAnnotationType.getDisplayName())
                .isEqualTo("NameAnnotation");
    }
}
