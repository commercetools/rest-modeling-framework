package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.LibraryUse;
import io.vrap.rmf.raml.model.types.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

public class RamlResourceTest implements ResourceFixtures {
    @Test
    public void generatorLibrary() throws IOException {
        final Resource resource = fromClasspath("/generator.raml");
        assertThat(resource.getErrors()).hasSize(0);

        final EList<EObject> contents = resource.getContents();

        assertThat(contents).hasSize(1);
        final EObject rootObject = contents.get(0);
        assertThat(rootObject).isInstanceOf(Library.class);

        final Library library = (Library) rootObject;
        final EList<AnyAnnotationType> annotationTypes = library.getAnnotationTypes();
        assertThat(annotationTypes).hasSize(1);

        assertThat(annotationTypes.get(0)).isInstanceOf(StringAnnotationType.class);
        final StringAnnotationType annotationStringType = (StringAnnotationType) annotationTypes.get(0);
        assertThat(annotationStringType.getAllowedTargets()).containsExactly(AnnotationTarget.LIBRARY);
    }

    @Test
    public void localeLibrary() throws IOException {
        final Resource resource = fromClasspath("/locale.raml");
        assertThat(resource.getErrors()).hasSize(0);

        final Library library = getRootObject(resource);

        final EList<AnyType> types = library.getTypes();
        assertThat(types).hasSize(3);
    }

    @Test
    public void localeUseLibrary() throws IOException {
        final Resource resource = fromClasspath("/locale-use.raml");
        assertThat(resource.getErrors()).hasSize(0);

        final Library library = getRootObject(resource);

        final EList<LibraryUse> uses = library.getUses();
        assertThat(uses).hasSize(1);

        final LibraryUse localeLibraryUse = uses.get(0);
        assertThat(localeLibraryUse.getName()).isEqualTo("locale");

        final Library usedLibrary = localeLibraryUse.getLibrary();
        assertThat(usedLibrary.getTypes()).hasSize(3);

        final EList<AnyType> types = library.getTypes();
        assertThat(types).hasSize(1);
        assertThat(types.get(0)).isInstanceOf(ObjectType.class);

        final ObjectType withLocalizedNameType = (ObjectType) types.get(0);
        final EList<Property> properties = withLocalizedNameType.getProperties();
        assertThat(properties).hasSize(1);

        final Property nameProperty = properties.get(0);
        assertThat(nameProperty.getName()).isEqualTo("name");

        final AnyType localizedStringType = usedLibrary.getTypes().get(0);
        assertThat(nameProperty.getType()).isEqualTo(localizedStringType);
    }

    @Test
    public void library() throws IOException {
        final Resource resource = fromClasspath("/library.raml");
        assertThat(resource.getErrors()).hasSize(0);

        final Library library = getRootObject(resource);

        final EList<AnyType> types = library.getTypes();
        assertThat(types).hasSize(6);

        final AnyType type1 = types.get(0);
        assertThat(type1).isInstanceOf(StringType.class);
        final StringType stringType = (StringType) type1;
        assertThat(stringType.getName()).isEqualTo("StringType");
        assertThat(stringType.getMinLength()).isEqualTo(10);

        final AnyType type2 = types.get(1);
        assertThat(type2).isInstanceOf(StringType.class);
        assertThat(type2.getName()).isEqualTo("EmptyObject");

        final AnyType type3 = types.get(2);
        assertThat(type3).isInstanceOf(ObjectType.class);
        final ObjectType objectType2 = (ObjectType) type3;
        assertThat(objectType2.getName()).isEqualTo("WithProperties");

        final EList<Property> properties = objectType2.getProperties();
        assertThat(properties).hasSize(2);

        final Property optionalStringProperty = properties.get(0);
        assertThat(optionalStringProperty.getName()).isEqualTo("name");
        assertThat(optionalStringProperty.getRequired()).isEqualTo(false);

        final AnyType type4 = types.get(3);
        assertThat(type4).isInstanceOf(ObjectType.class);
        final ObjectType superType = (ObjectType) type4;
        assertThat(superType.getName()).isEqualTo("SuperType");

        final AnyType type5 = types.get(4);
        assertThat(type5).isInstanceOf(ObjectType.class);
        final ObjectType subType = (ObjectType) type5;
        assertThat(subType.getName()).isEqualTo("SubType");
        assertThat((EObject) subType.getType()).isEqualTo(superType);

        final AnyType type6 = types.get(5);
        assertThat(type6).isInstanceOf(StringType.class);
        final StringType enumType = (StringType) type6;
        assertThat(enumType.getEnum()).hasSize(2);

        final AnyType type = properties.get(1).getType();
        assertThat(type).isEqualTo(superType);
    }

    @Ignore
    @Test
    public void apiViaHttp() {
        final URI uri = URI.createURI("http://localhost:5050/api-raml/");
        final Resource resource = fromUri(uri);
        resource.getContents();
        assertThat(resource).isNotNull();
    }
}
