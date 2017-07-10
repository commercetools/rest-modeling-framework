package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.DataType;
import io.vrap.rmf.raml.model.types.Library;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationsTest implements ResourceFixtures {

    @Test
    public void annotations() throws IOException {
        final Resource resource = fromClasspath("/annotations/annotations.raml");
        assertThat(resource.getErrors()).hasSize(0);

        final Library library = getRootObject(resource);

        assertThat(library.getAnnotationTypes()).hasSize(1);
        assertThat(library.getTypes()).hasSize(1);

        final DataType annotatedType = library.getTypes().get(0);
        assertThat(annotatedType.getAnnotations()).hasSize(1);
    }
}
