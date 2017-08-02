package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.UnionType;
import io.vrap.rmf.raml.persistence.antlr.ParserErrorCollector;
import io.vrap.rmf.raml.persistence.antlr.TypeExpressionBaseVisitor;
import io.vrap.rmf.raml.persistence.antlr.TypeExpressionLexer;
import io.vrap.rmf.raml.persistence.antlr.TypeExpressionParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

/**
 * This class parses a type expression and transforms it to an {@link AnyType}.
 *
 * This visitor works returns an {@link EObject} because it can parse types and
 * annotation types.
 */
public class TypeExpressionConstructor {

    public EObject parse(final String typeExpression, final Scope scope) {
        final CharStream charStream = CharStreams.fromString(typeExpression);
        final TypeExpressionLexer lexer = new TypeExpressionLexer(charStream);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final TypeExpressionParser typeExpressionParser = new TypeExpressionParser(tokenStream);

        lexer.removeErrorListeners();
        typeExpressionParser.removeErrorListeners();

        final ParserErrorCollector errorCollector = new ParserErrorCollector();
        lexer.addErrorListener(errorCollector);
        typeExpressionParser.addErrorListener(errorCollector);

        final TypeExpressionParser.Type_exprContext typeExpr = typeExpressionParser.type_expr();

        final EObject anyType = new TypeExpressionBuilder(scope, ARRAY_TYPE).visit(typeExpr);
        scope.getResource().getErrors().addAll(errorCollector.getErrors());

        return anyType;
    }


    private final static class TypeExpressionBuilder extends TypeExpressionBaseVisitor<EObject> {
        private final Scope scope;
        private final EClass arrayTypeDeclarationType;
        private final EStructuralFeature itemsFeature;

        public TypeExpressionBuilder(final Scope scope, final EClass arrayTypeDeclarationType) {
            this.scope = scope;
            this.arrayTypeDeclarationType = arrayTypeDeclarationType;
            this.itemsFeature = arrayTypeDeclarationType.getEStructuralFeature("items");
        }

        @Override
        public EObject visitArrayType(final TypeExpressionParser.ArrayTypeContext ctx) {
            final EObject arrayType = EcoreUtil.create(arrayTypeDeclarationType);
            scope.addValue(INLINE_TYPE_CONTAINER__INLINE_TYPES, arrayType);
            final EObject itemsType = visit(ctx.type_expr());
            arrayType.eSet(itemsFeature, itemsType);

            return arrayType;
        }

        @Override
        public EObject visitUnionType(final TypeExpressionParser.UnionTypeContext ctx) {
            final UnionType unionType = (UnionType) EcoreUtil.create(UNION_TYPE);
            scope.addValue(INLINE_TYPE_CONTAINER__INLINE_TYPES, unionType);
            final EList<AnyType> oneOfType = ECollections.asEList(ctx.type_expr().stream()
                    .map(this::visit)
                    .filter(AnyType.class::isInstance) // TODO report errors
                    .map(AnyType.class::cast)
                    .collect(Collectors.toList()));
            unionType.getOneOf().addAll(oneOfType);

            return unionType;
        }

        @Override
        public EObject visitParens(final TypeExpressionParser.ParensContext ctx) {
            return super.visit(ctx.type_expr());
        }

        @Override
        public EObject visitTypeReference(final TypeExpressionParser.TypeReferenceContext ctx) {
            final String typeName = ctx.getText();
            final EObject anyType = scope.getEObjectByName(typeName);

            return anyType;
        }
    }
}
