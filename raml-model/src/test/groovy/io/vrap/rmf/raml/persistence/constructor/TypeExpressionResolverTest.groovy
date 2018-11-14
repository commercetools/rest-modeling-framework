package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.types.*
import io.vrap.rmf.raml.persistence.ResourceFixtures
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import spock.lang.Shared
import spock.lang.Specification

import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.TYPED_ELEMENT__TYPE

/**
 * Unit tests for {@link TypeExpressionResolver}.
 */
class TypeExpressionResolverTest extends Specification implements ResourceFixtures {
    @Shared
    TypeExpressionResolver constructor = new TypeExpressionResolver();
    @Shared
    Resource builtinTypesResource = fromUri(BuiltinType.RESOURCE_URI);
    @Shared
    Scope typedElementScope = Scope.of(builtinTypesResource)
            .with(TypesFactory.eINSTANCE.createProperty(), TYPED_ELEMENT__TYPE);

    def "StringType"() {
        when:
        AnyType anyType = resolve('string');

        then:
        anyType instanceof StringType
    }

    def "ArrayType"() {
        when:
        ArrayType arrayType = resolve('string[]')

        then:
        arrayType.name == null
        arrayType.items instanceof StringType
        arrayType.items.name == 'string'
    }

    def "Multi-dimensional ArrayType"() {
        when:
        ArrayType arrayType = resolve('string[][]')

        then:
        arrayType.name == null
        arrayType.items instanceof  ArrayType
        ArrayType nestedArrayType = arrayType.items
        nestedArrayType.name == null
        nestedArrayType.items instanceof StringType
        nestedArrayType.items.name == 'string'
    }

    def "UnionType"() {
        when:
        UnionType unionType = resolve('string|number|boolean')
        then:
        unionType.oneOf.size() == 3
        unionType.oneOf[0] instanceof StringType
        unionType.oneOf[0].name == 'string'
        unionType.oneOf[1] instanceof NumberType
        unionType.oneOf[1].name == 'number'
        unionType.oneOf[2] instanceof BooleanType
        unionType.oneOf[2].name == 'boolean'
    }

    def "IntersectionType"() {
        when:
        IntersectionType intersectionType = resolve('[string,number,boolean]')
        then:
        intersectionType.allOf.size() == 3
        intersectionType.allOf[0] instanceof StringType
        intersectionType.allOf[0].name == 'string'
        intersectionType.allOf[1] instanceof NumberType
        intersectionType.allOf[1].name == 'number'
        intersectionType.allOf[2] instanceof BooleanType
        intersectionType.allOf[2].name == 'boolean'
    }

    def "TypeTemplate"() {
        when:
        TypeTemplate typeTemplate = resolve('<<resourcePath>>Draft')
        then:
        typeTemplate.name == '<<resourcePath>>Draft'
    }

    def "TypeTemplate with transformations"() {
        when:
        TypeTemplate typeTemplate = resolve('<<resourcePath|!singularize|!uppercase>>Draft')
        then:
        typeTemplate.name == '<<resourcePath|!singularize|!uppercase>>Draft'
    }

    def "TypeTemplate array"() {
        when:
        ArrayType typeTemplateArray = resolve('<<resourcePath>>Draft[]')
        then:
        typeTemplateArray.items instanceof TypeTemplate
        typeTemplateArray.items.name == '<<resourcePath>>Draft'
    }

    def "Parens"() {
        when:
        ArrayType arrayType = resolve('(string|number|boolean)[]')
        then:
        arrayType.items instanceof UnionType
        UnionType unionType = arrayType.items
        unionType.oneOf.size() == 3
        unionType.oneOf[0] instanceof StringType
        unionType.oneOf[1] instanceof NumberType
        unionType.oneOf[2] instanceof BooleanType
    }

    def "Complex UnionType"() {
        when:
        UnionType unionType = resolve('string[]|(number|boolean)[]')
        then:
        unionType.oneOf.size() == 2
        unionType.oneOf[0] instanceof ArrayType
        ArrayType simpleArrayType = unionType.oneOf[0]
        simpleArrayType.items instanceof StringType
        unionType.oneOf[1] instanceof ArrayType
        ArrayType arrayUnionType = unionType.oneOf[1]
        arrayUnionType.items instanceof UnionType
        UnionType items = arrayUnionType.items
        items.oneOf.size() == 2
        items.oneOf[0] instanceof NumberType
        items.oneOf[1] instanceof BooleanType
    }

    EObject resolve(String typeExpression) {
        EcoreUtil.resolve(constructor.resolve(typeExpression, typedElementScope), typedElementScope.getResource())
    }
}
