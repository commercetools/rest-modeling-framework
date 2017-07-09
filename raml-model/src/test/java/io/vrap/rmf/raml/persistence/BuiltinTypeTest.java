package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.model.types.AnyType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class BuiltinTypeTest implements ResourceFixtures{
    @Test
    public void load() throws IOException {
        final Resource builtinTypesResource = fromUri(BuiltinType.RESOURCE_URI);
        assertThat(builtinTypesResource.getErrors()).hasSize(0);

        assertThat(builtinTypesResource).isNotNull();
        for (final BuiltinType builtinType : BuiltinType.values()) {
            final EObject eObject = builtinTypesResource.getEObject("/types/" + builtinType.getName());

            assertThat(eObject).isNotNull();
            assertThat(eObject).isInstanceOf(AnyType.class);
            final AnyType type = (AnyType) eObject;
            final AnyType superType = type.getType();
            assertThat(superType).isEqualTo(type)
                    .describedAs("The type of the builtin type points to itself.");
        }
    }
}
