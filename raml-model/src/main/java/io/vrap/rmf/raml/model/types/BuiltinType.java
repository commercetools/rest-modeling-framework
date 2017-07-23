package io.vrap.rmf.raml.model.types;

import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;

import java.util.Optional;
import java.util.stream.Stream;

import static io.vrap.functional.utils.Classes.as;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

/**
 * Enumeration of the RAML built-in types together with the their type declaration type
 * {@link #getTypeDeclarationType()} and the type declaration type
 * of their corresponding annotation type {@link #getAnnotationTypeDeclarationType()}.
 */
public enum BuiltinType {
    ANY("any", ANY_TYPE, ANY_ANNOTATION_TYPE),
    TIME_ONLY("time-only", TIME_ONLY_TYPE, TIME_ONLY_ANNOTATION_TYPE),
    DATE_TIME("datetime", DATE_TIME_TYPE, DATE_TIME_ANNOTATION_TYPE),
    DATE_TIME_ONLY("datetime-only", DATE_TIME_ONLY_TYPE, DATE_TIME_ONLY_ANNOTATION_TYPE),
    DATE_ONLY("date-only", DATE_ONLY_TYPE, DATE_ONLY_ANNOTATION_TYPE),
    NUMBER("number", NUMBER_TYPE, NUMBER_ANNOTATION_TYPE),
    INTEGER("integer", INTEGER_TYPE, INTEGER_ANNOTATION_TYPE),
    BOOLEAN("boolean", BOOLEAN_TYPE, BOOLEAN_ANNOTATION_TYPE),
    STRING("string", STRING_TYPE, STRING_ANNOTATION_TYPE),
    NIL("nil", NIL_TYPE, NIL_ANNOTATION_TYPE),
    FILE("file", FILE_TYPE, FILE_ANNOTATION_TYPE),
    ARRAY("array", ARRAY_TYPE, ARRAY_ANNOTATION_TYPE),
    OBJECT("object", OBJECT_TYPE, OBJECT_ANNOTATION_TYPE);

    private final EClass annotationTypeDeclarationType;
    private final EClass typeDeclarationType;
    private final String name;

    public final static URI RESOURCE_URI = URI.createURI("http://raml.org/raml/1.0/builtin-types.raml");

    BuiltinType(final String name, final EClass typeDeclarationType, final EClass annotationTypeDeclarationType) {
        assert ANY_ANNOTATION_TYPE.isSuperTypeOf(annotationTypeDeclarationType);

        this.typeDeclarationType = typeDeclarationType;
        this.annotationTypeDeclarationType = annotationTypeDeclarationType;
        this.name = name;
    }

    public EObject getEObject(final ResourceSet resourceSet) {
        return resourceSet.getResource(RESOURCE_URI, true)
                .getEObject("/types/" + name);
    }

    public EClass getTypeDeclarationType() {
        return typeDeclarationType;
    }

    public EClass getAnnotationTypeDeclarationType() {
        return annotationTypeDeclarationType;
    }

    /**
     * Returns the type for the given type declaration type.
     *
     * If the type declaration type is a subtype of {@link AnyAnnotationType},
     * the {@link #getAnnotationTypeDeclarationType()} will be returned.
     *
     * Otherwise {@link #getTypeDeclarationType()} will be returned
     *
     * @param typeDeclarationType the type declaration type
     *
     * @return the type for the given type declaration type
     */
    public EClass typeFor(final EClass typeDeclarationType) {
        return ANY_ANNOTATION_TYPE.isSuperTypeOf(typeDeclarationType) ?
                getAnnotationTypeDeclarationType() :
                getTypeDeclarationType();
    }

    /**
     * Returns the meta type of this type based on the given containment feature.
     *
     * @param scope the scope
     * @return the meta type (either {@link #getTypeDeclarationType()} or {@link #getAnnotationTypeDeclarationType()}
     */
    public EClass getScopedMetaType(final Scope scope) {
        final boolean isAnnotationType = as(EReference.class, scope.eFeature())
                .map(EReference::getEReferenceType)
                .map(ANY_ANNOTATION_TYPE::isSuperTypeOf).orElse(false);
        return isAnnotationType ? annotationTypeDeclarationType : typeDeclarationType;
    }

    public String getName() {
        return name;
    }

    public static Optional<BuiltinType> of(final String name) {
        return Stream.of(BuiltinType.values())
                .filter(builtinMetaType -> builtinMetaType.getName().equals(name))
                .findFirst();
    }
}
