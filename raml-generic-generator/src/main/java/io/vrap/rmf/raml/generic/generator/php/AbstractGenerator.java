package io.vrap.rmf.raml.generic.generator.php;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

abstract class AbstractGenerator {
    void generateFile(final String content, final File outputFile) throws IOException {
        if (content != null) {
            if (!outputFile.exists()) {
                Files.createDirectories(outputFile.getParentFile().toPath());
                Files.createFile(outputFile.toPath());
            }
            Files.write(outputFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
        }
    }

    STGroupFile createSTGroup(final URL resource) {
        final STGroupFile stGroup = new STGroupFile(resource, "UTF-8", '<', '>');
        stGroup.load();
        stGroup.registerRenderer(String.class,
                (arg, formatString, locale) ->
                        "capitalize".equals(formatString) ?
                                StringUtils.capitalize(arg.toString()) :
                                arg.toString());
        return stGroup;
    }
}
