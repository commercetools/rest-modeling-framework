package io.vrap.rmf.raml.persistence.antlr;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the generated antlr parser {@link TypeExpressionParser}.
 */
public class TypeExpressionParserTest implements TypeExpressionFixtures {

    @Test
    public void typeReference() {
        final TypeExpressionParser.Type_exprContext myType = parse("MyType");

        assertThat(myType.children).hasSize(1);
        final ParseTree parseTree = myType.getChild(0);
        assertThat(parseTree.getText()).isEqualTo("MyType");
    }

    @Test
    public void arrayType() {
        final TypeExpressionParser.Type_exprContext myType = parse("MyType[]");

        assertThat(myType.children).hasSize(2);
        ParseTree parseTree = myType.getChild(0);
        assertThat(parseTree.getText()).isEqualTo("MyType");

        parseTree = myType.getChild(1);
        assertThat(parseTree.getText()).isEqualTo("[]");
    }

    @Test
    public void multiDimArrayType() {
        final TypeExpressionParser.Type_exprContext myType = parse("MultiDim[][]");

        assertThat(myType.getChildCount()).isEqualTo(2);

        final ParseTree outerArrayTree = myType.getChild(1);
        assertThat(outerArrayTree.getChildCount()).isEqualTo(0);
        assertThat(outerArrayTree.getText()).isEqualTo("[]");

        final ParseTree outerArrayTreeChild = myType.getChild(0);
        assertThat(outerArrayTreeChild.getChildCount()).isEqualTo(2);
        assertThat(outerArrayTreeChild.getText()).isEqualTo("MultiDim[]");
        assertThat(outerArrayTreeChild.getChildCount()).isEqualTo(2);

        final ParseTree itemsTree = outerArrayTreeChild.getChild(0);
        assertThat(itemsTree.getText()).isEqualTo("MultiDim");

        final ParseTree innerArrayTree = outerArrayTreeChild.getChild(1);
        assertThat(innerArrayTree.getText()).isEqualTo("[]");
    }

    @Test
    public void unionType() {
        final TypeExpressionParser.Type_exprContext myType = parse("MyType|YourType");

        assertThat(myType.children).hasSize(3);
        ParseTree parseTree = myType.getChild(0);
        assertThat(parseTree.getText()).isEqualTo("MyType");

        parseTree = myType.getChild(1);
        assertThat(parseTree.getText()).isEqualTo("|");

        parseTree = myType.getChild(2);
        assertThat(parseTree.getText()).isEqualTo("YourType");
    }

    @Test
    public void parens() {
        final TypeExpressionParser.Type_exprContext complexType = parse("(MyType[]|YourType)[]");

        assertThat(complexType.children).hasSize(2);
        ParseTree parseTree = complexType.getChild(0);
        assertThat(parseTree.getText()).isEqualTo("(MyType[]|YourType)");

        assertThat(parseTree.getChildCount()).isEqualTo(3);
        final ParseTree unionType = parseTree.getChild(1);
        assertThat(unionType.getChildCount()).isEqualTo(3);

        final ParseTree myType = unionType.getChild(0);
        assertThat(myType.getText()).isEqualTo("MyType[]");

        final ParseTree yourType = unionType.getChild(2);
        assertThat(yourType.getText()).isEqualTo("YourType");

        parseTree = complexType.getChild(1);
        assertThat(parseTree.getText()).isEqualTo("[]");
    }
}
