package io.vrap.rmf.raml.persistence.typeexpressions;


import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.ArrayType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.ResourceFixtures;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeExpressionsParserTest implements ResourceFixtures {
    private final TypeExpressionsParser parser = new TypeExpressionsParser();
    private final Resource builtinTypesResource = fromUri(BuiltinType.RESOURCE_URI);
    private final Scope builinTypeScope = Scope.of(builtinTypesResource).with(TYPE_CONTAINER__TYPES);

    @Test
    public void typeReference() {
        final AnyType myType = parser.parse("string", builinTypeScope);

        assertThat(myType).isNotNull();
        assertThat(myType.getName()).isEqualTo("string");
    }

    @Test
    public void arrayType() {
        final AnyType anyType = parser.parse("string[]", builinTypeScope);

        assertThat(anyType).isInstanceOf(ArrayType.class);
        final ArrayType arrayType = (ArrayType) anyType;
        assertThat(arrayType.getName()).isNull();
        assertThat(arrayType.getItems()).isNotNull();
        assertThat(arrayType.getItems().getName()).isEqualTo("string");
    }

    @Test
    public void multiDimArrayType() {
        final AnyType anyType = parser.parse("string[][]", builinTypeScope);

        assertThat(anyType).isInstanceOf(ArrayType.class);

        final ArrayType outerArrayType = (ArrayType) anyType;
        assertThat(outerArrayType.getItems()).isInstanceOf(ArrayType.class);

        final ArrayType innerArrayType = (ArrayType) outerArrayType.getItems();
        final AnyType itemsType = innerArrayType.getItems();
        assertThat(itemsType.getName()).isEqualTo("string");
    }
}