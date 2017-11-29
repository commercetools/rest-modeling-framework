package io.vrap.rmf.raml.generic.generator;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.hypertino.inflector.English;
import io.vrap.rmf.raml.model.util.StringCaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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
                        case "lowercamel":
                            return StringCaseFormat.LOWER_CAMEL_CASE.apply(arg.toString().replace(".", "-"));
                        case "uppercamel":
                            return StringCaseFormat.UPPER_CAMEL_CASE.apply(arg.toString().replace(".", "-"));
                        default:
                            return arg.toString();
                    }
                });
        return stGroup;
    }
}
