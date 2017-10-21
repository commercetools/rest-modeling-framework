package io.vrap.rmf.raml.model.types

import spock.lang.Specification

/**
 * Unit test for classes that implement {@link AnnotationsFacet}.
 */
class AnnotationsFacetTest extends Specification {

    def "get annotation by type"() {
        when:
        StringAnnotationType annotationType = TypesFactory.eINSTANCE.createStringAnnotationType();
        AnyType anyType = TypesFactory.eINSTANCE.createAnyType();
        Annotation annotation = TypesFactory.eINSTANCE.createAnnotation()
        annotation.type = annotationType
        anyType.annotations.add(annotation)
        then:
        anyType.getAnnotation(annotationType) == annotation
    }

    def "get annotation by name"() {
        when:
        StringAnnotationType annotationType = TypesFactory.eINSTANCE.createStringAnnotationType();
        annotationType.setName('identifier')
        AnyType anyType = TypesFactory.eINSTANCE.createAnyType();
        Annotation annotation = TypesFactory.eINSTANCE.createAnnotation()
        annotation.type = annotationType
        anyType.annotations.add(annotation)
        then:
        anyType.getAnnotation('identifier') == annotation
    }
}
