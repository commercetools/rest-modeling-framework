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
import org.stringtemplate.v4.STGroup;
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
    private final STGroup interfaceSTGroup;
    private final TypeGeneratingVisitor typeGeneratingVisitor;

    public TypesGenerator() throws IOException
    {
        final URL resource = Resources.getResource("./templates/php/interface.stg");
        interfaceSTGroup = new STGroupFile(resource, "UTF-8", '<', '>');
        interfaceSTGroup.load();
        interfaceSTGroup.registerRenderer(String.class,
                (arg, formatString, locale) ->
                        "capitalize".equals(formatString) ?
                                StringUtils.capitalize(arg.toString()) :
                                arg.toString());
        typeGeneratingVisitor = new TypeGeneratingVisitor("types");
    }

    public void generate(final List<AnyType> types, final File outputPath) throws IOException {
        for (final AnyType anyType : types) {
            final String generate = generateFile(anyType);
            if (generate != null) {
                final File sourceFile = new File(outputPath, anyType.getName().concat(".php"));
                if (!sourceFile.exists()) {
                    Files.createFile(sourceFile.toPath());
                }
                Files.write(sourceFile.toPath(), generate.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    @VisibleForTesting
    String generateFile(final AnyType type) {
        return typeGeneratingVisitor.doSwitch(type);
    }

    private class TypeGeneratingVisitor extends TypesSwitch<String> {
        private final String packageName;

        public TypeGeneratingVisitor(String packageName) {
            this.packageName = packageName;
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
                final ST interfaceST = interfaceSTGroup.getInstanceOf("interface");
                interfaceST.add("type", objectType);
                final List<String> builtInTypes = Arrays.stream(BuiltinType.values()).map(BuiltinType::getName).collect(Collectors.toList());;
                final Boolean builtInParentType = objectType.getType() == null || builtInTypes.contains(objectType.getType().getName());
                interfaceST.add("builtInParent", builtInParentType);
                interfaceST.add("package", packageName);
                return interfaceST.render();
            }
        }
    }
}
