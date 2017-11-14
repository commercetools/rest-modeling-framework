package io.vrap.rmf.raml.persistence

import io.vrap.rmf.raml.model.modules.Library
import io.vrap.rmf.raml.model.types.Annotation
import io.vrap.rmf.raml.model.types.AnyType
import io.vrap.rmf.raml.model.types.ObjectAnnotationType
import io.vrap.rmf.raml.model.types.StringAnnotationType
import io.vrap.rmf.raml.model.values.ObjectInstance
import io.vrap.rmf.raml.model.values.StringInstance
import org.eclipse.emf.ecore.resource.Resource
import spock.lang.Specification

/**
 * Unit tests for constructing {@link io.vrap.rmf.raml.model.types.Annotation}s.
 */
class AnnotationsTest extends Specification implements ResourceFixtures {

    def "/annotations/annotations.raml"() {
        when:
        Resource resource = fromClasspath("/annotations/annotations.raml");

        then:
        resource.errors.size() == 0

        Library library = getRootObject(resource);

        library.annotationTypes.size() == 2
        library.annotationTypes[0] instanceof StringAnnotationType
        StringAnnotationType packageNameAnnotationType = library.annotationTypes[0]
        library.annotationTypes[1] instanceof ObjectAnnotationType
        ObjectAnnotationType typeMappingAnnotationType = library.annotationTypes[1]

        library.annotations.size() == 1
        library.annotations[0].type instanceof StringAnnotationType
        library.annotations[0].value instanceof StringInstance

        library.types.size() == 1
        AnyType annotatedType = library.types[0]
        annotatedType.annotations.size() == 2
        annotatedType.annotations[0].type == packageNameAnnotationType

        annotatedType.annotations[1].type == typeMappingAnnotationType
        Annotation typeMappingAnnotation = annotatedType.annotations[1]
        typeMappingAnnotation.value instanceof ObjectInstance
        ObjectInstance objectInstance = typeMappingAnnotation.value
        objectInstance.value[0].name == typeMappingAnnotationType.properties[0].name
        objectInstance.value[1].name == typeMappingAnnotationType.properties[1].name
    }
}
