package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.StringType;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TypesGenerator {

    public static final URL RESOURCE = Resources.getResource("./templates/php/type.stg");

    public void generate(final List<AnyType> types, final File outputPath) throws IOException {
        final TypeGeneratingVisitor interfaceGeneratingVisitor = createVisitor("types", "interface");
        final TypeGeneratingVisitor modelGeneratingVisitor =  createVisitor("types", "model");
        for (final AnyType anyType : types) {
            generateFile(generateType(interfaceGeneratingVisitor, anyType), new File(outputPath, anyType.getName().concat(".php")));
            generateFile(generateType(modelGeneratingVisitor, anyType), new File(outputPath, anyType.getName().concat("Model.php")));
        }
    }

    private STGroupFile createSTGroup(final URL resource) {
        final STGroupFile stGroup = new STGroupFile(resource, "UTF-8", '<', '>');
        stGroup.load();
        stGroup.registerRenderer(String.class,
                (arg, formatString, locale) ->
                        "capitalize".equals(formatString) ?
                                StringUtils.capitalize(arg.toString()) :
                                arg.toString());
        return stGroup;
    }

    @VisibleForTesting
    TypeGeneratingVisitor createVisitor(final String packageName, final String type) {
        return new TypeGeneratingVisitor(packageName, createSTGroup(RESOURCE), type);
    }

    @VisibleForTesting
    String generateType(final TypeGeneratingVisitor visitor, final AnyType type) {
        return visitor.doSwitch(type);
    }

    private void generateFile(final String content, final File outputFile) throws IOException {
        if (content != null) {
            if (!outputFile.exists()) {
                Files.createFile(outputFile.toPath());
            }
            Files.write(outputFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
        }
    }

    private class TypeGeneratingVisitor extends TypesSwitch<String> {
        private final String packageName;
        private final STGroupFile stGroup;
        private final String type;

        public TypeGeneratingVisitor(final String packageName, final STGroupFile stGroup, final String type) {
            this.stGroup = stGroup;
            this.packageName = packageName;
            this.type = type;
        }

        @Override
        public String caseStringType(final StringType stringType) {
            if (stringType.getEnum().isEmpty()) {
                return null;
            }
            return null;
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (objectType.getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf(type);
                st.add("type", objectType);
                final List<String> builtInTypes = Arrays.stream(BuiltinType.values()).map(BuiltinType::getName).collect(Collectors.toList());;
                final Boolean builtInParentType = objectType.getType() == null || builtInTypes.contains(objectType.getType().getName());
                st.add("builtInParent", builtInParentType);
                st.add("package", packageName);
                return st.render();
            }
        }
    }
}
