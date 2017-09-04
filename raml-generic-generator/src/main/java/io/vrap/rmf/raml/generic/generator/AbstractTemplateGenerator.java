package io.vrap.rmf.raml.generic.generator;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.vrap.rmf.raml.model.resources.UriTemplate;
import io.vrap.rmf.raml.model.resources.UriTemplateExpression;
import io.vrap.rmf.raml.model.resources.UriTemplateLiteral;
import io.vrap.rmf.raml.model.resources.UriTemplatePart;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.Property;
import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
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
                        case "lowercamel":
                            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, arg.toString().replace(".", "-"));
                        case "uppercamel":
                            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, arg.toString().replace(".", "-"));
                        default:
                            return arg.toString();
                    }
                });
        return stGroup;
    }

    protected List<AnyType> getParentTypes(AnyType anyType) {
        if (BuiltinType.of(anyType.getName()).isPresent()) {
            return Lists.newArrayList();
        }
        List<AnyType> t = getParentTypes(anyType.getType());
        t.add(anyType);

        return t;
    }

    protected Property getBaseProperty(final Property property) {
        final AnyType anyType = (AnyType)property.eContainer();
        if (!(anyType instanceof ObjectType)) {
            return property;
        }
        final List<ObjectType> t = getParentTypes(anyType).stream().map(ObjectType.class::cast).collect(Collectors.toList());
        if (t.size() <= 1) {
            return property;
        }
        return t.stream()
                .filter(anyType1 -> anyType1.getProperty(property.getName()) != null)
                .map(objectType -> objectType.getProperty(property.getName()))
                .findFirst()
                .orElse(property);
    }
}
