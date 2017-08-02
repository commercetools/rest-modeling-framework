package io.vrap.rmf.raml.persistence.constructor;


import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.persistence.ResourceFixtures;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeExpressionConstructorTest implements ResourceFixtures {
    private final TypeExpressionConstructor parser = new TypeExpressionConstructor();
    private final Resource builtinTypesResource = fromUri(BuiltinType.RESOURCE_URI);
    private final Scope typedElementScope = Scope.of(builtinTypesResource)
            .with(TYPE_CONTAINER__TYPES)
            .with(TypesFactory.eINSTANCE.createProperty());

    @Test
    public void typeReference() {
        final AnyType parsedType = (AnyType) parse("string");

        assertThat(parsedType).isNotNull();
        assertThat(parsedType.getName()).isEqualTo("string");
    }

    @Test
    public void arrayType() {
        String typeExpression = "string[]";
        final EObject parsedType = parse(typeExpression);

        assertThat(parsedType).isInstanceOf(ArrayType.class);
        final ArrayType arrayType = (ArrayType) parsedType;
        assertThat(arrayType.getName()).isNull();
        assertThat(arrayType.getItems()).isNotNull();
        assertThat(arrayType.getItems().getName()).isEqualTo("string");
    }

    @Test
    public void multiDimArrayType() {
        final EObject parsedType = parse("string[][]");

        assertThat(parsedType).isInstanceOf(ArrayType.class);

        final ArrayType outerArrayType = (ArrayType) parsedType;
        assertThat(outerArrayType.getItems()).isInstanceOf(ArrayType.class);

        final ArrayType innerArrayType = (ArrayType) outerArrayType.getItems();
        final AnyType itemsType = innerArrayType.getItems();
        assertThat(itemsType.getName()).isEqualTo("string");
    }

    @Test
    public void unionType() {
        final EObject parsedType = parse("string|number");

        assertThat(parsedType).isInstanceOf(UnionType.class);
        final UnionType unionType = (UnionType) parsedType;

        final EList<AnyType> oneOf = unionType.getOneOf();
        assertThat(oneOf).hasSize(2);
        assertThat(oneOf.get(0).getName()).isEqualTo("string");
        assertThat(oneOf.get(1).getName()).isEqualTo("number");
    }

    @Test
    public void unionTypeParens() {
        final EObject parsedType = parse("(string|number)");

        assertThat(parsedType).isInstanceOf(UnionType.class);
        final UnionType unionType = (UnionType) parsedType;

        final EList<AnyType> oneOf = unionType.getOneOf();
        assertThat(oneOf).hasSize(2);
        assertThat(oneOf.get(0).getName()).isEqualTo("string");
        assertThat(oneOf.get(1).getName()).isEqualTo("number");
    }


    private EObject parse(String typeExpression) {
        return parser.parse(typeExpression, typedElementScope);
    }
}