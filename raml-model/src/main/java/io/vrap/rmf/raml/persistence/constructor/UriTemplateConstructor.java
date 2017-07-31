package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.persistence.antlr.ParserErrorCollector;
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
 * This class parses an uri template and transforms it to an {@link UriTemplate}.
 */
public class UriTemplateConstructor {
    private final static UriTemplateBuilder BUILDER = new UriTemplateBuilder();

    public UriTemplate parse(final String uriTemplateStr, final Scope scope) {
        final CharStream charStream = CharStreams.fromString(uriTemplateStr);
        final URILexer lexer = new URILexer(charStream);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final URIParser uriParser = new URIParser(tokenStream);

        lexer.removeErrorListeners();
        uriParser.removeErrorListeners();

        final ParserErrorCollector errorCollector = new ParserErrorCollector();
        lexer.addErrorListener(errorCollector);
        uriParser.addErrorListener(errorCollector);

        final UriTemplate uriTemplate = (UriTemplate) BUILDER.visitUriTemplate(uriParser.uriTemplate());
        scope.getResource().getErrors().addAll(errorCollector.getErrors());

        return uriTemplate;
    }

    private static class UriTemplateBuilder extends URIBaseVisitor<EObject> {

        @Override
        public EObject visitUriTemplate(URIParser.UriTemplateContext ctx) {
            final UriTemplate uriTemplate = ResourcesFactory.eINSTANCE.createUriTemplate();
            final EList<UriTemplatePart> parts = ECollections.toEList(ctx.parts.stream()
                    .map(this::visitUriTemplatePart)
                    .map(UriTemplatePart.class::cast)
                    .collect(Collectors.toList()));
            uriTemplate.getParts().addAll(parts);

            return uriTemplate;
        }

        @Override
        public EObject visitLiteral(URIParser.LiteralContext ctx) {
            final UriTemplateLiteral literal = ResourcesFactory.eINSTANCE.createUriTemplateLiteral();
            literal.setLiteral(ctx.LITERAL().getText());
            return literal;
        }

        @Override
        public EObject visitExpression(URIParser.ExpressionContext ctx) {
            final UriTemplateExpression expression = ResourcesFactory.eINSTANCE.createUriTemplateExpression();
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
