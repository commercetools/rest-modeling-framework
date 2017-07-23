package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.persistence.ResourceFixtures;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link LibraryConstructor}.
 */
public class LibraryConstructorTest implements RAMLParserFixtures, ResourceFixtures {

    @Test
    public void library() throws IOException {
        final RAMLParser.LibraryContext libraryContext = parseFromClasspath("/libraries/library.raml").library();
        final Library library = (Library) LibraryConstructor.of(uriFromClasspath("/libraries/library.raml"))
                .visitLibrary(libraryContext);

        assertThat(library.getUsage()).isEqualTo("Test");
        final EList<AnyType> types = library.getTypes();
        assertThat(types).hasSize(8);

        assertThat(types.get(0).getName()).isEqualTo("StringType");
        assertThat(types.get(0)).isInstanceOf(StringType.class);
        final StringType stringType = (StringType) types.get(0);
        assertThat(stringType.getMinLength()).isEqualTo(10);

        assertThat(types.get(4).getName()).isEqualTo("SuperType");
        assertThat(types.get(4)).isInstanceOf(ObjectType.class);
        final ObjectType superType = (ObjectType) types.get(4);

        assertThat(types.get(3).getName()).isEqualTo("WithProperties");
        assertThat(types.get(3)).isInstanceOf(ObjectType.class);
        final ObjectType objectType = (ObjectType) types.get(3);
        assertThat(objectType.getProperties()).hasSize(2);
        assertThat(objectType.getProperty("super")).isNotNull();
        assertThat(objectType.getProperty("super").getType()).isEqualTo(superType);

        assertThat(types.get(6).getName()).isEqualTo("SubType");
        assertThat(types.get(6)).isInstanceOf(ObjectType.class);
        final ObjectType subType = (ObjectType) types.get(6);
        assertThat(subType.getType()).isEqualTo(superType);

        assertThat(types.get(7).getName()).isEqualTo("Enum");
        assertThat(types.get(7)).isInstanceOf(StringType.class);
        final StringType enumType = (StringType) types.get(7);
        assertThat(enumType.getEnum()).containsExactly("v1", "v2");
    }

    @Test
    public void generatorAnnotations() throws IOException {
        final RAMLParser.LibraryContext libraryContext = parseFromClasspath("/libraries/generator-annotations.raml").library();
        final Library library = (Library) LibraryConstructor.of(uriFromClasspath("/libraries/generator-annotations.raml"))
                .visitLibrary(libraryContext);

        assertThat(library.getUsage()).isEqualTo("Defines generator annotations.");
        final EList<AnyType> types = library.getTypes();
        assertThat(types).hasSize(0);

        final EList<AnyAnnotationType> annotationTypes = library.getAnnotationTypes();
        assertThat(annotationTypes).hasSize(1);

        assertThat(annotationTypes.get(0)).isInstanceOf(StringAnnotationType.class);
        final StringAnnotationType stringAnnotationType = (StringAnnotationType) annotationTypes.get(0);
        assertThat(stringAnnotationType.getName()).isEqualTo("package");
        assertThat(stringAnnotationType.getAllowedTargets()).containsExactly(AnnotationTarget.LIBRARY);
    }

    @Test
    public void annotations() throws IOException {
        final RAMLParser.LibraryContext libraryContext = parseFromClasspath("/annotations/annotations.raml").library();
        final Library library = (Library) LibraryConstructor.of(uriFromClasspath("/annotations/annotations.raml"))
                .visitLibrary(libraryContext);

        final EList<AnyAnnotationType> annotationTypes = library.getAnnotationTypes();
        assertThat(annotationTypes).hasSize(2);
        assertThat(annotationTypes.get(0)).isInstanceOf(StringAnnotationType.class);
        final StringAnnotationType stringAnnotationType = (StringAnnotationType) annotationTypes.get(0);

        assertThat(library.getAnnotations()).hasSize(1);
        final Annotation annotation = library.getAnnotations().get(0);
        assertThat(annotation.getType()).isEqualTo(stringAnnotationType);
        assertThat(annotation.getValue()).isInstanceOf(StringInstance.class);
        final StringInstance annotationValue = (StringInstance) annotation.getValue();
        assertThat(annotationValue.getValue()).isEqualTo("package");

        final EList<AnyType> types = library.getTypes();
        assertThat(types).hasSize(1);
        assertThat(types.get(0)).isInstanceOf(ObjectType.class);

        final ObjectType annotatedType = (ObjectType) types.get(0);
        assertThat(annotatedType.getAnnotations()).hasSize(1);
        final Property defaultProperty = annotatedType.getProperty("default");
        assertThat(defaultProperty).isNotNull();
        assertThat(defaultProperty.getAnnotations()).hasSize(1);
    }

    @Test
    public void propertiesInlineTypeDeclaration() throws IOException {
        final RAMLParser.LibraryContext libraryContext = parseFromClasspath("/properties/inline-type-declaration.raml").library();
        final Library library = (Library) LibraryConstructor.of(uriFromClasspath("/properties/inline-type-declaration.raml"))
                .visitLibrary(libraryContext);

        final EList<AnyType> types = library.getTypes();
        assertThat(types).hasSize(1);
        assertThat(types.get(0)).isInstanceOf(ObjectType.class);
        final ObjectType objectType = (ObjectType) types.get(0);

        final Property nameProperty = objectType.getProperty("name");
        assertThat(nameProperty).isNotNull();
        assertThat(nameProperty.getType()).isInstanceOf(StringType.class);
        final StringType inlineStringType = (StringType) nameProperty.getType();
        assertThat(inlineStringType.getName()).isNull();
        assertThat(inlineStringType.getMinLength()).isEqualTo(10);
    }

    @Test
    public void dataTypeInclude() throws IOException {
        final RAMLParser.LibraryContext libraryContext = parseFromClasspath("/data-type-include.raml").library();
        final Library library = (Library) LibraryConstructor.of(uriFromClasspath("/data-type-include.raml"))
                .visitLibrary(libraryContext);

        final EList<AnyType> types = library.getTypes();

        assertThat(types).hasSize(1);
        assertThat(types.get(0)).isInstanceOf(ObjectType.class);

        final ObjectType personType = (ObjectType) types.get(0);

        assertThat(personType.getName()).isEqualTo("Person");
        assertThat(personType.getDisplayName()).isEqualTo("Person");
        assertThat(personType.getProperties()).hasSize(1);

        final Property ageProperty = personType.getProperties().get(0);

        assertThat(ageProperty.getName()).isEqualTo("age");
        assertThat(ageProperty.getType().getName()).isEqualTo("integer");
    }

}
