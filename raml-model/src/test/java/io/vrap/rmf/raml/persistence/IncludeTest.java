package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.Property;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@code !include} tags.
 */
public class IncludeTest implements ResourceFixtures {
    @Test
    public void load() throws IOException {
        final Resource resource = fromClasspath("/data-type-include.raml");
        assertThat(resource.getErrors()).hasSize(0);

        final Library library = getRootObject(resource);
        final EList<AnyType> types = library.getTypes();

        assertThat(types).hasSize(1);
        assertThat(types.get(0)).isInstanceOf(ObjectType.class);

        final ObjectType personType = (ObjectType) types.get(0);

        assertThat(personType.getName()).isEqualTo("Person");
        assertThat(personType.getDisplayName().getValue()).isEqualTo("Person");
        assertThat(personType.getProperties()).hasSize(1);

        final Property ageProperty = personType.getProperties().get(0);

        assertThat(ageProperty.getName()).isEqualTo("age");
        assertThat(ageProperty.getType().getName()).isEqualTo("integer");
    }
}
