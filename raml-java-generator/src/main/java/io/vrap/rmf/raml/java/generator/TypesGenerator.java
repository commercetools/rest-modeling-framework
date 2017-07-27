package io.vrap.rmf.raml.java.generator;

import com.squareup.javapoet.*;
import io.vrap.rmf.raml.model.modules.TypeContainer;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;

/**
 * This class generates java interfaces from a type container {@link TypeContainer}.
 * It uses javapoet to generate the java source code.
 */
public class TypesGenerator {
    private String packageName;
    private final File generateTo;

    private TypeMappingVisitor typeMappingVisitor = new TypeMappingVisitor();
    private PropertyGeneratingVisitor propertyGeneratingVisitor = new PropertyGeneratingVisitor();
    private TypeGeneratingVisitor typeGeneratingVisitor = new TypeGeneratingVisitor();

    /**
     * Creates a new types generator.
     *
     * @param packageName  the package name
     * @param genSourceDir the source dir
     */
    public TypesGenerator(final String packageName, final File genSourceDir) {
        this.packageName = packageName;
        this.generateTo = genSourceDir;
    }

    /**
     * Generates java source code for the types of the given type container.
     *
     * @param typeContainer the type container
     */
    public void generate(final TypeContainer typeContainer) {
        final List<TypeSpec> typeSpecs = typeContainer.getTypes().stream()
                .map(typeGeneratingVisitor::doSwitch)
                .collect(Collectors.toList());

        typeSpecs.stream()
                .filter(Objects::nonNull)
                .forEach(this::generateFile);
    }

    public static void main(String... args) {
        final long startTimeMillis = System.currentTimeMillis();
        final File generateTo = new File("./build/raml-gen");
        final File apiFile = new File("/Users/mkoester/Development/commercetools-api-reference/api.raml");

        final URI fileURI = URI.createURI(apiFile.toURI().toString());

        final Resource resource = new RamlResourceSet()
                .getResource(fileURI, true);
        final EList<EObject> contents = resource.getContents();
        final EList<Resource.Diagnostic> errors = resource.getErrors();

        if (errors.isEmpty() && contents.size() == 1) {
            final EObject rootObject = contents.get(0);
            if (rootObject instanceof TypeContainer) {
                final TypeContainer typeContainer = (TypeContainer) rootObject;
                final TypesGenerator typesGenerator = new TypesGenerator("types", generateTo);
                typesGenerator.generate(typeContainer);
                final long endTimeMillis = System.currentTimeMillis();

                final Duration duration = Duration.ofMillis(endTimeMillis - startTimeMillis);
                System.out.println("Generation took:" + duration);
            } else {
                System.err.println("Invalid root object:" + rootObject.eClass().getName());
            }
        } else if (contents.isEmpty()) {
            System.err.println("File '" + fileURI + "' is empty");
        } else {
            errors.forEach(diagnostic -> System.err.println(diagnostic.getMessage()));
        }

    }

    private void generateFile(final TypeSpec typeSpec) {
        final JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                .build();
        try {
            javaFile.writeTo(generateTo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class TypeGeneratingVisitor extends TypesSwitch<TypeSpec> {

        @Override
        public TypeSpec caseObjectType(final ObjectType objectType) {
            final TypeSpec.Builder interfaceBuilder;
            if (objectType.getName() == null) {
                interfaceBuilder = null;
            } else {
                interfaceBuilder = TypeSpec.interfaceBuilder(objectType.getName());
                final TypeName typeName = typeMappingVisitor.doSwitch(objectType.getType());
                final String superTypeName = objectType.getType().getName();
                if (!BuiltinType.of(superTypeName).isPresent()) {
                    interfaceBuilder.addSuperinterface(typeName);
                }
                final List<MethodSpec> getterMethods = objectType.getProperties().stream()
                        .filter(property -> !property.getName().startsWith("/"))
                        .map(propertyGeneratingVisitor::caseProperty)
                        .collect(Collectors.toList());

                interfaceBuilder.addMethods(getterMethods);
            }

            return interfaceBuilder.build();
        }
    }

    private class PropertyGeneratingVisitor extends TypesSwitch<MethodSpec> {
        @Override
        public MethodSpec caseProperty(final Property property) {
            final String getterName = "get" + capitalize(property.getName());
            final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(getterName);
            methodBuilder.addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC);

            final TypeName propertyTypeName = typeMappingVisitor.doSwitch(property.getType());
            methodBuilder.returns(propertyTypeName);

            return methodBuilder.build();
        }
    }

    private class TypeMappingVisitor extends TypesSwitch<TypeName> {
        @Override
        public TypeName caseAnyType(final AnyType anyType) {
            return ClassName.get(Object.class);
        }

        @Override
        public TypeName caseStringType(final StringType stringType) {
            return ClassName.get(String.class);
        }

        @Override
        public TypeName caseBooleanType(final BooleanType booleanType) {
            return ClassName.get(Boolean.class);
        }

        @Override
        public TypeName caseNumberType(final NumberType numberType) {
            return ClassName.get(Number.class);
        }

        @Override
        public TypeName caseIntegerType(final IntegerType integerType) {
            return ClassName.get(Integer.class);
        }

        @Override
        public TypeName caseObjectType(final ObjectType objectType) {
            return ClassName.get(packageName, objectType.getName());
        }

        @Override
        public TypeName caseDateTimeType(final DateTimeType object) {
            return ClassName.get(ZonedDateTime.class);
        }

        @Override
        public TypeName caseArrayType(final ArrayType arrayType) {
            final AnyType items = arrayType.getItems();
            final TypeName itemTypeName = items != null ?
                    doSwitch(items) :
                    ClassName.get(Object.class);

            final ClassName listClassName = ClassName.get(List.class);
            return ParameterizedTypeName.get(listClassName, itemTypeName);
        }
    }
}
