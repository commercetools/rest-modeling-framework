package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.model.annotations.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class BuiltinTypeTest implements ResourceFixtures {
    @Test
    public void load() throws IOException {
        final Resource builtinTypesResource = fromUri(BuiltinType.RESOURCE_URI);
        assertThat(builtinTypesResource.getErrors()).hasSize(0);

        assertThat(builtinTypesResource).isNotNull();
        for (final BuiltinType builtinType : BuiltinType.values()) {
            final EObject anyType = builtinTypesResource.getEObject("/types/" + builtinType.getName());

            assertThat(anyType).isNotNull();
            assertThat(anyType).isInstanceOf(AnyType.class);
            final AnyType type = (AnyType) anyType;
            final AnyType superType = type.getType();
            assertThat(superType).isEqualTo(type)
                    .describedAs("The type of the builtin type points to itself.");

            final EObject anyAnnotationType = builtinTypesResource.getEObject("/annotationTypes/" + builtinType.getName());

            assertThat(anyAnnotationType).isNotNull();
            assertThat(anyAnnotationType).isInstanceOf(AnyAnnotationType.class);
            final AnyAnnotationType annotationType = (AnyAnnotationType) anyAnnotationType;
            final AnyAnnotationType superAnnotationType = annotationType.getType();
            assertThat(superAnnotationType).isEqualTo(annotationType)
                    .describedAs("The type of the builtin type points to itself.");

        }
    }
}
