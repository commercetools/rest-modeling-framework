package io.vrap.rmf.raml.generic.generator.md;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TypesGenerator extends AbstractTemplateGenerator {

    private static final URL RESOURCE = Resources.getResource("./templates/md/type.stg");
    static final String TYPE_MODEL = "model";

    public void generate(final List<AnyType> types, final File outputPath) throws IOException {
        generateTypes(outputPath, types);
    }

    private void generateTypes(final File outputPath, List<AnyType> types) throws IOException {
        TypeGeneratingVisitor typeGeneratingVisitor = createVisitor(TYPE_MODEL);

        for (final AnyType anyType : types) {
            final File typeFile = new File(outputPath, anyType.getName().concat(".md"));

            generateFile(generateType(typeGeneratingVisitor, anyType), typeFile);
        }

    }

    @VisibleForTesting
    String generateType(final TypeGeneratingVisitor visitor, final AnyType type) {
        return visitor.doSwitch(type);
    }

    @VisibleForTesting
    TypeGeneratingVisitor createVisitor(final String type) {
        return new TypeGeneratingVisitor(createSTGroup(RESOURCE), type);
    }

    private class TypeGeneratingVisitor extends TypesSwitch<String> {
        private final STGroupFile stGroup;
        private final String type;

        TypeGeneratingVisitor(final STGroupFile stGroup, final String type) {
            this.stGroup = stGroup;
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
        public String caseArrayType(final ArrayType arrayType) {
            final AnyType items = arrayType.getItems();
            if (items == null || items.getName() == null) {
                return null;
            }
            final ST st = stGroup.getInstanceOf(type);
            st.add("type", items);
            final Boolean builtInParentType = items.getType() == null || BuiltinType.of(items.getName()).isPresent();
            st.add("builtInParent", builtInParentType);
            return st.render();
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (objectType.getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf(type);
                st.add("type", objectType);
                final Boolean builtInParentType = objectType.getType() == null || BuiltinType.of(objectType.getType().getName()).isPresent();
                st.add("builtInParent", builtInParentType);
                return st.render();
            }
        }
    }
}
