package io.vrap.rmf.raml.generic.generator;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.model.modules.TypeContainer;
import io.vrap.rmf.raml.model.types.*;
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

/**
 * This class generates source files from a type container {@link TypeContainer}.
 * It uses stringtemplate to generate the source code.
 */
public class TypesGenerator {
    private final STGroup interfaceSTGroup;
    private final File generateTo;

    private final TypeGeneratingVisitor typeGeneratingVisitor;

    /**
     * Creates a new types generator.
     *
     * @param packageName  the package name
     * @param genSourceDir the source dir
     */
    public TypesGenerator(final String packageName, final File genSourceDir) {
        this.generateTo = genSourceDir.getAbsoluteFile();
        final URL resource = Resources.getResource("./templates/php/interface.stg");
        interfaceSTGroup = new STGroupFile(resource, "UTF-8", '<', '>');
        interfaceSTGroup.load();
        interfaceSTGroup.registerRenderer(String.class,
                (arg, formatString, locale) ->
                        "capitalize".equals(formatString) ?
                                StringUtils.capitalize(arg.toString()) :
                                arg.toString());
        typeGeneratingVisitor = new TypeGeneratingVisitor(packageName);
    }

    public void generate(final TypeContainer typeContainer) throws IOException {
        for (final AnyType anyType : typeContainer.getTypes()) {
            final String generate = generate(anyType);
            if (generate != null) {
                final File sourceFile = new File(generateTo, anyType.getName().concat(".php"));
                Files.createFile(sourceFile.toPath());
                Files.write(sourceFile.toPath(), generate.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    @VisibleForTesting
    String generate(final AnyType type) {
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
            } else {
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
                interfaceST.add("package", packageName);
                return interfaceST.render();
            }
        }
    }
}
