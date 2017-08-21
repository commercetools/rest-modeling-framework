package io.vrap.rmf.raml.generic.generator;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public abstract class AbstractTemplateGenerator {
    protected void generateFile(final String content, final File outputFile) throws IOException {
        if (content != null) {
            if (!outputFile.exists()) {
                Files.createDirectories(outputFile.getParentFile().toPath());
                Files.createFile(outputFile.toPath());
            }
            Files.write(outputFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
        }
    }

    protected STGroupFile createSTGroup(final URL resource) {
        final STGroupFile stGroup = new STGroupFile(resource, "UTF-8", '<', '>');
        stGroup.load();
        stGroup.registerRenderer(String.class,
                (arg, formatString, locale) -> {
                    switch (Strings.nullToEmpty(formatString)) {
                        case "capitalize":
                            return StringUtils.capitalize(arg.toString());
                        case "upperUnderscore":
                            return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, arg.toString());
                        case "lowerHyphen":
                            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, arg.toString());
                        case "lowercase":
                            return StringUtils.lowerCase(arg.toString());
                        default:
                            return arg.toString();
                    }
                });
        return stGroup;
    }
}
