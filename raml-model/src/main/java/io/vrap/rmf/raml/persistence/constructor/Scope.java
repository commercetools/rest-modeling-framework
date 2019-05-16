package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.RamlDiagnostic;
import io.vrap.rmf.raml.model.modules.ApiExtension;
import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.LibraryUse;
import io.vrap.rmf.raml.model.modules.TypeContainer;
import io.vrap.rmf.raml.model.types.BuiltinType;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vrap.emf.utils.URIUtils.normalize;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.*;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.RESOURCE_TYPE;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.TRAIT;
import static io.vrap.rmf.raml.model.security.SecurityPackage.Literals.SECURITY_SCHEME;
import static io.vrap.rmf.raml.model.security.SecurityPackage.Literals.SECURITY_SCHEME_CONTAINER__SECURITY_SCHEMES;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ANY_ANNOTATION_TYPE;

/**
 * Represents the current scope during the construction phase.
 */
public class Scope {
    private final Scope parent;
    private final Resource resource;
    private final URI uri;
    private final EObject eObject;
    private final EStructuralFeature feature;

    private Scope(final Scope parent, final Resource resource, final URI uri,
                  final EObject eObject, final EStructuralFeature feature) {
        this.parent = parent;
        this.resource = resource;
        this.uri = uri;
        this.eObject = eObject;
        this.feature = feature;
    }

    public Resource getResource() {
        return resource;
    }

    public ResourceSet getResourceSet() {
        return resource.getResourceSet();
    }

    public EObject getEObject() {
        return eObject;
    }

    public EStructuralFeature getFeature() {
        return feature;
    }

    private URI getBaseUri() {
        return uri.trimSegments(1);
    }

    public Resource getResource(final String relativePath) {
        final URI uri = resolve(relativePath);
        return getResourceSet().getResource(uri, true);
    }

    private URI resolve(final String relativePath) {
        final String[] segments = URI.createURI(relativePath).segments();
        final URI resolved = getBaseUri().appendSegments(segments);
        return normalize(resolved);
    }

    public EObject getEObjectByName(final String name) {
        final EClass type = (EClass) feature.getEType();
        return getEObjectByName(name, type);
    }

    public EObject getEObjectByName(final String name, final EClass type) {
        final String uriFragment = getUriFragment(name, type);

        final Resource builtinTypeResource = getResourceSet().getResource(BuiltinType.RESOURCE_URI, true);
        final EObject resolvedType = Optional.ofNullable(builtinTypeResource.getEObject(uriFragment))
                .orElseGet(() -> getEObjectByName(this.resource, name, type));
        return resolvedType;
    }

    private String getUriFragment(final String id, final EClass type) {
        // TODO replace ternary with visitor
        final String fragment = ANY_ANNOTATION_TYPE.isSuperTypeOf(type) ?
                TYPE_CONTAINER__ANNOTATION_TYPES.getName() :
                SECURITY_SCHEME.isSuperTypeOf(type) ?
                        SECURITY_SCHEME_CONTAINER__SECURITY_SCHEMES.getName() :
                        TRAIT.isSuperTypeOf(type) ?
                                TYPE_CONTAINER__TRAITS.getName() :
                                    RESOURCE_TYPE.isSuperTypeOf(type) ?
                                            TYPE_CONTAINER__RESOURCE_TYPES.getName() :
                                            TYPE_CONTAINER__TYPES.getName();
        return Stream.of(fragment, id)
                .collect(Collectors.joining("/", "/", ""));
    }

    private EObject getEObjectByName(final Resource resource, final String name, final EClass type) {
        final EObject resolvedType;
        final String[] segments = name.split("\\.");
        if (segments.length == 1) {
            final String uriFragment = getUriFragment(name, type);
            resolvedType = getEObject(resource, type, uriFragment);
        } else if (segments.length == 2) {
            final String libraryName = segments[0];
            final Library usedLibrary = getUsedLibrary(libraryName);
            if (usedLibrary == null) {
                addError("Library named ''{0}'' doesn't exist in ''{1}''",
                        libraryName, resource.getURI());
                resolvedType = null;
            } else {
                final String resolvedId = segments[1];
                final Scope usedLibraryScope = with(usedLibrary.eResource());
                resolvedType = usedLibraryScope.getEObjectByName(resolvedId);
            }
        } else {
            addError("Name has invalid format ''{0}''", name);
            resolvedType = null;
        }
        return resolvedType;
    }

    private EObject getEObject(final Resource resource, final EClass type, final String uriFragment) {
        EObject resolvedType;
        final EObject eObject = getEObject(uriFragment);
        if (eObject != null) {
            resolvedType = eObject;
        } else {
            final InternalEObject internalEObject = (InternalEObject) EcoreUtil.create(type);
            internalEObject.eSetProxyURI(resource.getURI().appendFragment(uriFragment));
            resolvedType = internalEObject;
        }
        return resolvedType;
    }

    private EObject getEObject(final String uriFragment) {
        final EObject directEObject = resource.getEObject(uriFragment);
        if (directEObject == null) {
            final Optional<Resource> extendsResource = getExtendsResource();
            final EObject extendsEObject = extendsResource.map(resource -> resource.getEObject(uriFragment)).orElse(null);
            return extendsEObject;
        }
        return directEObject;
    }

    private Optional<Resource> getExtendsResource() {
        final Optional<EObject> first = this.resource.getContents().stream().findFirst();
        if (first.isPresent()) {
            final EObject rootObject = first.get();
            if (rootObject instanceof ApiExtension) {
                final ApiExtension apiExtension = (ApiExtension) rootObject;
                if (apiExtension.getExtends() != null) {
                    return Optional.ofNullable(apiExtension.getExtends().eResource());
                }
            }
        }
        return Optional.empty();
    }

    private Library getUsedLibrary(final String name) {
        final Optional<LibraryUse> libraryUse = resource.getContents().stream()
                .filter(TypeContainer.class::isInstance)
                .map(TypeContainer.class::cast)
                .flatMap(typeContainer -> typeContainer.getUses().stream())
                .filter(LibraryUse.class::isInstance)
                .map(LibraryUse.class::cast)
                .filter(use -> use.getName().equals(name))
                .findFirst();

        return libraryUse.map(LibraryUse::getLibrary).orElse(null);
    }

    /**
     * Sets the given value on this scope.
     *
     * @param value the value, either a wrapper primitive wrapper object {@link Object},
     *              a {@link EObject} or a {@link List} of these types.
     * @return the value
     */
    @SuppressWarnings("unchecked")
    public <T> T setValue(final T value, final Token token) {
        final EStructuralFeature feature = getFeature();
        return feature == null ? setValue(resource, value, token) : setValue(feature, value, token);
    }

    public <T> T setValue(final Resource resource, final T value, final Token token) {
        assert resource != null;
        if (value instanceof Collection) {
            final Collection<EObject> values = (Collection<EObject>) value;
            resource.getContents().addAll(values);
        } else {
            resource.getContents().add((EObject) value);
        }
        return value;
    }

    /**
     * Sets the given value for the given feature on this scope.
     *
     * @param feature the feature to set
     * @param value   the value, either a wrapper primitive wrapper object {@link Object},
     *                a {@link EObject} or a {@link List} of these types.
     * @return the value
     */
    public <T> T setValue(final EStructuralFeature feature, final T value, final Token token) {
        final EList<EStructuralFeature> objectClass = eObject.eClass().getEAllStructuralFeatures();
        final boolean isValidValue = objectClass.contains(feature) && value != null;
        final boolean isCollectionValue = value instanceof Collection;
        final EClassifier eType = feature.getEType();

        if (isValidValue) {
            if (feature.isMany()) {
                if (isCollectionValue) {
                    final Collection collectionValue = (Collection) value;
                    if (!collectionValue.isEmpty()) {
                        for (final Object item : collectionValue) {
                            if (eType.isInstance(item)) {
                                addValue(feature, item);
                            } else {
                                addErrorWithLocation("Invalid value ''{0}'' for feature ''{1}'' of type ''{2}''",
                                        token, value, feature.getName(), eObject.eClass().getName());
                            }
                        }
                    }
                } else {
                    addValue(feature, value);
                }
            } else if (eType.isInstance(value)) {
                eObject.eSet(feature, value);
            } else {
                addErrorWithLocation("Invalid value ''{0}'' for feature ''{1}'' of type ''{2}''",
                        token, value, feature.getName(), eObject.eClass().getName());
            }
        } else {
            addErrorWithLocation("Invalid value ''{0}'' for feature ''{1}'' of type ''{2}''",
                    token, value, feature.getName(), eObject.eClass().getName());
        }
        return value;
    }

    public <T> void addValue(EStructuralFeature feature, T value) {
        assert feature.isMany();
        assert !(value instanceof Collection);

        final EList<T> eList = (EList<T>) eObject.eGet(feature);
        eList.add(value);
    }

    public void addError(final String messagePattern, final Object... arguments) {
        addErrorWithLocation(messagePattern, null, arguments);
    }


    public void addErrorWithLocation(final String messagePattern, final Token at, final Object... arguments) {
        final String message = MessageFormat.format(messagePattern, arguments);

        final int line = at != null ? at.getLine() : -1;
        final int column = at != null ? at.getCharPositionInLine() : -1;

        resource.getErrors()
                .add(RamlDiagnostic.of(message, at.getTokenSource().getSourceName(), line, column));
    }

    /**
     * Returns the parent scope.
     *
     * @return returns the parent scope
     */
    public Scope getParent() {
        return parent;
    }

    public Scope with(final EObject eObject) {
        return new Scope(this, resource, uri, eObject, feature);
    }

    public Scope with(final EObject eObject, final EStructuralFeature feature) {
        return new Scope(this, resource, uri, eObject, feature);
    }

    public Scope with(final EStructuralFeature feature) {
        return with(eObject, feature);
    }

    public Scope with(final Resource resource) {
        return new Scope(this, resource, uri, eObject, feature);
    }

    public static Scope of(final Resource resource) {
        return new Scope(null, resource, resource.getURI(), null, null);
    }

}
