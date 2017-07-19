package io.vrap.rmf.raml.persistence.typeexpressions;

import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.persistence.antlr.TypeExpressionBaseVisitor;
import io.vrap.rmf.raml.persistence.antlr.TypeExpressionLexer;
import io.vrap.rmf.raml.persistence.antlr.TypeExpressionParser;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import static io.vrap.rmf.raml.model.annotations.AnnotationsPackage.Literals.ANY_ANNOTATION_TYPE;
import static io.vrap.rmf.raml.model.annotations.AnnotationsPackage.Literals.ARRAY_ANNOTATION_TYPE;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ARRAY_TYPE;

/**
 * This class parses a type expression and transforms it to an {@link AnyType}.
 */
public class TypeExpressionsParser {

    public EObject parse(final String typeExpression, final Scope scope) {
        final EClass scopeType = (EClass) scope.eFeature().getEType();

        final CharStream charStream = CharStreams.fromString(typeExpression);
        final TypeExpressionLexer lexer = new TypeExpressionLexer(charStream);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final TypeExpressionParser typeExpressionParser = new TypeExpressionParser(tokenStream);
        final TypeExpressionParser.Type_exprContext typeExpr = typeExpressionParser.type_expr();

        final EClass arrayType = ANY_ANNOTATION_TYPE.isSuperTypeOf(scopeType) ?
                ARRAY_ANNOTATION_TYPE :
                ARRAY_TYPE;

        final EObject anyType = new TypeExpressionBuilder(scope, arrayType).visit(typeExpr);

        return anyType;
    }

    private final static class TypeExpressionBuilder extends TypeExpressionBaseVisitor<EObject> {
        private final Scope scope;
        private final EClass arrayType;
        private final EStructuralFeature itemsFeature;

        public TypeExpressionBuilder(final Scope scope, final EClass arrayType) {
            this.scope = scope;
            this.arrayType = arrayType;
            this.itemsFeature = arrayType.getEStructuralFeature("items");
        }

        @Override
        public EObject visitArrayType(final TypeExpressionParser.ArrayTypeContext ctx) {
            final EObject anyType = EcoreUtil.create(this.arrayType);

            final EObject itemsType = visit(ctx.type_expr());
            anyType.eSet(itemsFeature, itemsType);

            return anyType;
        }

        @Override
        public EObject visitUnionType(final TypeExpressionParser.UnionTypeContext ctx) {
            return super.visitUnionType(ctx);
        }

        @Override
        public EObject visitParens(final TypeExpressionParser.ParensContext ctx) {
            return super.visitParens(ctx);
        }

        @Override
        public EObject visitTypeReference(final TypeExpressionParser.TypeReferenceContext ctx) {
            final String typeName = ctx.getText();
            final EObject anyType = scope.getImportedTypeById(typeName);

            return anyType;
        }
    }
}
