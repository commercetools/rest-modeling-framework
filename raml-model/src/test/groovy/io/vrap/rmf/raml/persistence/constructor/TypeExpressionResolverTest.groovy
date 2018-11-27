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
    TypeExpressionResolver resolver = new TypeExpressionResolver();
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
        with(arrayType) {
            name == null
            items instanceof StringType
            items.name == 'string'
        }
    }

    def "Multi-dimensional ArrayType"() {
        when:
        ArrayType arrayType = resolve('string[][]')

        then:
        with(arrayType) {
            name == null
            items instanceof ArrayType
            ArrayType nestedArrayType = arrayType.items
            with(nestedArrayType) {
                name == null
                items instanceof StringType
                items.name == 'string'
            }
        }
    }

    def "UnionType"() {
        when:
        UnionType unionType = resolve('string|number|boolean')
        then:
        with(unionType) {
            oneOf.size() == 3
            oneOf[0] instanceof StringType
            oneOf[0].name == 'string'
            oneOf[1] instanceof NumberType
            oneOf[1].name == 'number'
            oneOf[2] instanceof BooleanType
            oneOf[2].name == 'boolean'
        }
    }

    def "IntersectionType"() {
        when:
        IntersectionType intersectionType = resolve('[string,number,boolean]')
        then:
        with(intersectionType) {
            allOf.size() == 3
            allOf[0] instanceof StringType
            allOf[0].name == 'string'
            allOf[1] instanceof NumberType
            allOf[1].name == 'number'
            allOf[2] instanceof BooleanType
            allOf[2].name == 'boolean'
        }
    }

    def "IntersectionType with UnionType"() {
        when:
        IntersectionType intersectionType = resolve('[string,number|boolean]')
        then:
        with(intersectionType) {
            allOf.size() == 2
            allOf[0] instanceof StringType
            allOf[0].name == 'string'
            allOf[1] instanceof UnionType
            with(allOf[1] as UnionType) {
                oneOf.size() == 2
                oneOf[0] instanceof NumberType
                oneOf[0].name == 'number'
                oneOf[1] instanceof BooleanType
                oneOf[1].name == 'boolean'
            }
        }
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
        with(typeTemplateArray) {
            items instanceof TypeTemplate
            items.name == '<<resourcePath>>Draft'
        }
    }

    def "Parens"() {
        when:
        ArrayType arrayType = resolve('(string|number|boolean)[]')
        then:
        arrayType.items instanceof UnionType
        UnionType unionType = arrayType.items
        with(unionType) {
            oneOf.size() == 3
            oneOf[0] instanceof StringType
            oneOf[1] instanceof NumberType
            oneOf[2] instanceof BooleanType
        }
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
        with(arrayUnionType.items as UnionType) {
            oneOf.size() == 2
            oneOf[0] instanceof NumberType
            oneOf[1] instanceof BooleanType
        }
    }

    EObject resolve(String typeExpression) {
        EcoreUtil.resolve(resolver.resolve(typeExpression, typedElementScope), typedElementScope.getResource())
    }
}
