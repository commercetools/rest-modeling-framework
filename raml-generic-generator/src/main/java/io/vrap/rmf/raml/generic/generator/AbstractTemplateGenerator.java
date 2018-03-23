package io.vrap.rmf.raml.generic.generator;

import com.damnhandy.uri.template.Expression;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.impl.VarSpec;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.hypertino.inflector.English;
import io.vrap.rmf.raml.model.util.StringCaseFormat;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractTemplateGenerator {
    protected File generateFile(final String content, final File outputFile) throws IOException {
        if (content != null) {
            if (!outputFile.exists()) {
                Files.createDirectories(outputFile.getParentFile().toPath());
                Files.createFile(outputFile.toPath());
            }
            return Files.write(outputFile.toPath(), content.getBytes(StandardCharsets.UTF_8)).toFile();
        }
        return null;
    }

    protected File copyFile(final InputStream file, final File outputFile) throws IOException {
        Files.copy(file, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return outputFile;
    }

    protected STGroupFile createSTGroup(final URL resource) {
        final STGroupFile stGroup = new STGroupFile(resource, "UTF-8", '<', '>');
        stGroup.load();
        stGroup.registerRenderer(String.class,
                (arg, formatString, locale) -> {
                    switch (Strings.nullToEmpty(formatString)) {
                        case "capitalize":
                            return StringUtils.capitalize(arg.toString());
                        case "singularize":
                            return English.singular(arg.toString());
                        case "pluralize":
                            return English.plural(arg.toString());
                        case "upperUnderscore":
                            return StringCaseFormat.UPPER_UNDERSCORE_CASE.apply(arg.toString());
                        case "lowerHyphen":
                            return StringCaseFormat.LOWER_HYPHEN_CASE.apply(arg.toString());
                        case "lowercase":
                            return StringUtils.lowerCase(arg.toString());
                        case "uppercase":
                            return StringUtils.upperCase(arg.toString());
                        case "lowercamel":
                            return StringCaseFormat.LOWER_CAMEL_CASE.apply(arg.toString().replace(".", "-"));
                        case "uppercamel":
                            return StringCaseFormat.UPPER_CAMEL_CASE.apply(arg.toString().replace(".", "-"));
                        case "jsonescape":
                            return StringEscapeUtils.escapeJson(arg.toString());
                        default:
                            return arg.toString();
                    }
                });
        stGroup.registerRenderer(UriTemplate.class,
                (arg, formatString, locale) -> {
                    final List<Expression> parts = ((UriTemplate)arg).getComponents().stream()
                            .filter(uriTemplatePart -> uriTemplatePart instanceof Expression)
                            .map(uriTemplatePart -> (Expression)uriTemplatePart)
                            .collect(Collectors.toList());
                    switch (Strings.nullToEmpty(formatString)) {
                        case "methodName":
                            if (parts.size() > 0) {
                                return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, GeneratorHelper.toParamName((UriTemplate)arg, "With", "Value"));
                            }

                            final String uri = ((UriTemplate) arg).getTemplate();
                            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, uri.replaceFirst("/", ""));
                        case "paramVars":
                            if (parts.size() > 0) {
                                return parts.stream().map(
                                        uriTemplateExpression -> uriTemplateExpression.getVarSpecs().stream().map(VarSpec::getVariableName).collect(Collectors.joining(" = null, $"))
                                ).collect(Collectors.joining(", $"));
                            }
                            return "";
                        case "params":
                            if (parts.size() > 0) {
                                return parts.stream().map(
                                        uriTemplateExpression -> uriTemplateExpression.getVarSpecs().stream().map(VarSpec::getVariableName).collect(Collectors.joining(" = null, $"))
                                ).collect(Collectors.joining(" = null, $"));
                            }
                            return "";
                        case "paramArray":
                            if (parts.size() > 0) {
                                return parts.stream().map(
                                        uriTemplateExpression -> uriTemplateExpression.getVarSpecs().stream().map(VarSpec::getVariableName).map(s -> "'" + s + "' => $" + s).collect(Collectors.joining(", "))
                                ).collect(Collectors.joining(", "));
                            }
                            return "";
                        case "sprintf":
                            final Map<String, Object> params = parts.stream()
                                    .flatMap(uriTemplatePart -> uriTemplatePart.getVarSpecs().stream().map(VarSpec::getVariableName))
                                    .collect(Collectors.toMap(o -> o, o -> "%s"));
                            return ((UriTemplate)arg).expand(params).replace("%25s", "%s");
                        case "uri":
                            if (parts.size() > 0) {
                                return ((UriTemplate)arg).getComponents().stream().map(uriTemplatePart -> {
                                    if (uriTemplatePart instanceof Expression) {
                                        return ((Expression) uriTemplatePart).getVarSpecs().stream().map(VarSpec::getVariableName).map(s -> "' . $" + s + " . '").collect(Collectors.joining());
                                    }
                                    return uriTemplatePart.toString();
                                }).collect(Collectors.joining());
                            }
                            return arg.toString();
                        default:
                            return arg.toString();
                    }
                });
        return stGroup;
    }
}
