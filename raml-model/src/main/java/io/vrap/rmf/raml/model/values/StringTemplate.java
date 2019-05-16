package io.vrap.rmf.raml.model.values;

import com.google.common.collect.Lists;
import com.hypertino.inflector.English;
import io.vrap.rmf.raml.model.util.StringCaseFormat;
import io.vrap.rmf.raml.persistence.antlr.StringTemplateBaseVisitor;
import io.vrap.rmf.raml.persistence.antlr.StringTemplateLexer;
import io.vrap.rmf.raml.persistence.antlr.StringTemplateParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represent a string template used in {@link io.vrap.rmf.raml.model.resources.Trait}s,
 * {@link io.vrap.rmf.raml.model.resources.ResourceType}s and {@link io.vrap.rmf.raml.model.types.TypeTemplate}s.
 */
public class StringTemplate {
    private final List<Part> parts;
    private final Set<String> parameters;

    private StringTemplate(final List<Part> parts) {
        this.parts = parts;
        parameters = parts.stream()
                .filter(Expression.class::isInstance).map(Expression.class::cast)
                .map(Expression::getParam)
                .collect(Collectors.toSet());
    }

    /**
     * Returns the parameters of this instance.
     *
     * @return the parameter names
     */
    public Set<String> getParameters() {
        return parameters;
    }

    /**
     * Renders this instance with parameters replaced with the given values.
     *
     * @param values the parameter values
     * @return a string with all params replaced with the given values
     */
    public String render(final Map<String, String> values) {
        return parts.stream()
                .map(p -> p.render(values))
                .collect(Collectors.joining());
    }

    public String toString() {
        return parts.stream()
                .map(p -> p.toString())
                .collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringTemplate that = (StringTemplate) o;
        return Objects.equals(parts, that.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parts);
    }

    public static StringTemplate of(final List<Part> parts) {
        return new StringTemplate(parts);
    }

    /**
     * Returns a parsed string template if the given template has template parameters.
     * Returns null otherwise.
     *
     * @param value the value to parse as a string template
     * @return the parsed string template or null
     */
    public static StringTemplate of(final String value) {
        if (value.contains("<<")) {
            final StringTemplateLexer lexer = new StringTemplateLexer(CharStreams.fromString(value));
            final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            final StringTemplateParser.StringTemplateContext stringTemplateContext =
                    new StringTemplateParser(tokenStream).stringTemplate();
            final List<StringTemplate.Part> parts = new StringTemplateVisitor().visitStringTemplate(stringTemplateContext);
            return parts.stream().anyMatch(Expression.class::isInstance) ? of(parts) : null;
        } else {
            return null;
        }
    }

    private interface Part {
        /**
         * Renders this instance with parameters replaced with the given values.
         *
         * @param values the parameter values
         * @return a string with all params replaced with the given values
         */
        String render(Map<String, String> values);
    }

    private static class Literal implements Part {
        private final String literal;

        private Literal(final String literal) {
            this.literal = literal;
        }

        @Override
        public String render(Map<String, String> values) {
            return toString();
        }

        @Override
        public String toString() {
            return literal;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Literal literal1 = (Literal) o;
            return Objects.equals(literal, literal1.literal);
        }

        @Override
        public int hashCode() {
            return Objects.hash(literal);
        }

        public static Literal of(final String literal) {
            return new Literal(literal);
        }
    }

    private static final Map<String, Function<String, String>> TRANSFORMATIONS = new HashMap<>();
    static {
        final Function<StringCaseFormat, String> toTransformationName = stringCaseFormat ->
                stringCaseFormat.name().replace("_", "").toLowerCase();
        Stream.of(StringCaseFormat.values())
                .forEach(stringCaseFormat -> TRANSFORMATIONS.put(toTransformationName.apply(stringCaseFormat), stringCaseFormat));
        TRANSFORMATIONS.put("lowercase", String::toLowerCase);
        TRANSFORMATIONS.put("uppercase", String::toUpperCase);
        TRANSFORMATIONS.put("singularize", English::singular);
        TRANSFORMATIONS.put("pluralize", English::plural);
    }

    private static class Expression implements Part {
        private final String param;
        private final List<String> transformations;
        private final Function<String, String> transformation;

        private Expression(final String param, final List<String> transformations) {
            this.param = param;
            this.transformations = transformations;
            this.transformation = transformations.stream()
                    .map(TRANSFORMATIONS::get)
                    .collect(Collectors.reducing(Function.identity(), Function::compose));
        }

        public String getParam() {
            return param;
        }

        @Override
        public String render(final Map<String, String> values) {
            final String value = values.get(param);
            return value == null ? toString() : transformation.apply(value);
        }

        @Override
        public String toString() {
            return transformations.isEmpty() ? "<<" + param + ">>" : "<<" + param + transformations.stream().collect(Collectors.joining("|", "|", ">>"));
        }

        public static Expression of(final String param, final List<String> transformations) {
            return new Expression(param, transformations);
        }
    }

    private static class StringTemplateVisitor extends StringTemplateBaseVisitor<List<Part>> {
        @Override
        protected List<Part> defaultResult() {
            return new ArrayList<>();
        }

        @Override
        protected List<StringTemplate.Part> aggregateResult(final List<StringTemplate.Part> aggregate, final List<StringTemplate.Part> nextResult) {
            if (aggregate != null) {
                aggregate.addAll(nextResult);
                return aggregate;
            } else {
                return nextResult;
            }
        }

        @Override
        public List<StringTemplate.Part> visitExpression(StringTemplateParser.ExpressionContext ctx) {
            final List<String> transformations = ctx.fnApplication().stream()
                    .map(c -> c.fn.getText()).collect(Collectors.toList());
            return Lists.newArrayList(StringTemplate.Expression.of(ctx.ID().getText(), transformations));
        }

        @Override
        public List<StringTemplate.Part> visitLiteral(final StringTemplateParser.LiteralContext ctx) {
            return Lists.newArrayList(StringTemplate.Literal.of(ctx.getText()));
        }
    }
}
