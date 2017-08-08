package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.types.*
import io.vrap.rmf.raml.persistence.ResourceFixtures
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import spock.lang.Shared
import spock.lang.Specification

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES

/**
 * Unit tests for {@link TypeExpressionConstructor}.
 */
class TypeExpressionConstructorTest extends Specification implements ResourceFixtures {
    @Shared
    TypeExpressionConstructor parser = new TypeExpressionConstructor();
    @Shared
    Resource builtinTypesResource = fromUri(BuiltinType.RESOURCE_URI);
    @Shared
    Scope typedElementScope = Scope.of(builtinTypesResource)
            .with(TYPE_CONTAINER__TYPES)
            .with(TypesFactory.eINSTANCE.createProperty());

    def "AnyType"() {
        when:
        AnyType anyType = parse('string');

        then:
        anyType instanceof StringType
    }

    def "ArrayType"() {
        when:
        ArrayType arrayType = parse('string[]')

        then:
        arrayType.name == null
        arrayType.items instanceof StringType
        arrayType.items.name == 'string'
    }

    def "Multi-dimensional ArrayType"() {
        when:
        ArrayType arrayType = parse('string[][]')

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
        UnionType unionType = parse('string|number')
        then:
        unionType.oneOf.size() == 2
        unionType.oneOf[0].type instanceof StringType
        unionType.oneOf[0].type.name == 'string'
        unionType.oneOf[1].type instanceof NumberType
        unionType.oneOf[1].type.name == 'number'
    }

    def "TypeTemplate"() {
        when:
        TypeTemplate typeTemplate = parse('<<resourcePath>>Draft')
        then:
        typeTemplate.name == '<<resourcePath>>Draft'
    }

    def "TypeTemplate array"() {
        when:
        ArrayType typeTemplateArray = parse('<<resourcePath>>Draft[]')
        then:
        typeTemplateArray.items instanceof TypeTemplate
        typeTemplateArray.items.name == '<<resourcePath>>Draft'
    }

    def "Parens"() {
        when:
        ArrayType arrayType = parse('(string|number)[]')
        then:
        arrayType.items instanceof UnionType
        UnionType unionType = arrayType.items
        unionType.oneOf.size() == 2
        unionType.oneOf[0].type instanceof StringType
        unionType.oneOf[1].type instanceof NumberType
    }

    EObject parse(String typeExpression) {
        parser.parse(typeExpression, typedElementScope);
    }
}
