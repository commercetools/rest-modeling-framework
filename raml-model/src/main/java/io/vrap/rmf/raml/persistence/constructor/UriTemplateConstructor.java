package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.*;
import io.vrap.rmf.raml.persistence.antlr.URIBaseVisitor;
import io.vrap.rmf.raml.persistence.antlr.URILexer;
import io.vrap.rmf.raml.persistence.antlr.URIParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class parses an uri template and transforms it to an {@link io.vrap.rmf.raml.model.modules.UriTemplate}.
 */
public class UriTemplateConstructor {
    private final static UriTemplateBuilder BUILDER = new UriTemplateBuilder();

    public UriTemplate parse(final String uriTemplateStr) {
        final CharStream charStream = CharStreams.fromString(uriTemplateStr);
        final URILexer lexer = new URILexer(charStream);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final URIParser uriParser = new URIParser(tokenStream);

        final UriTemplate uriTemplate = (UriTemplate) BUILDER.visitUriTemplate(uriParser.uriTemplate());
        return uriTemplate;
    }

    private static class UriTemplateBuilder extends URIBaseVisitor<EObject> {
        private final static ModulesFactory MODULES_FACTORY = ModulesFactory.eINSTANCE;

        @Override
        public EObject visitUriTemplate(URIParser.UriTemplateContext ctx) {
            final UriTemplate uriTemplate = MODULES_FACTORY.createUriTemplate();
            final EList<UriTemplatePart> parts = ECollections.toEList(ctx.parts.stream()
                    .map(this::visitUriTemplatePart)
                    .map(UriTemplatePart.class::cast)
                    .collect(Collectors.toList()));
            uriTemplate.getParts().addAll(parts);

            return uriTemplate;
        }

        @Override
        public EObject visitLiteral(URIParser.LiteralContext ctx) {
            final UriTemplateLiteral literal = MODULES_FACTORY.createUriTemplateLiteral();
            literal.setLiteral(ctx.LITERAL().getText());
            return literal;
        }

        @Override
        public EObject visitExpression(URIParser.ExpressionContext ctx) {
            final UriTemplateExpression expression = MODULES_FACTORY.createUriTemplateExpression();
            if (ctx.operator != null) {
                expression.setOperator(ctx.operator.getText());
            }
            final List<String> parameters = ctx.variables.stream()
                    .map(varSpec -> varSpec.getText())
                    .collect(Collectors.toList());
            expression.getVariables().addAll(parameters);
            return expression;
        }
    }
}
