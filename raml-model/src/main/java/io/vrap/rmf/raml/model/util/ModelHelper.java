package io.vrap.rmf.raml.model.util;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.net.MediaType;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.resources.ResourceContainer;
import io.vrap.rmf.raml.model.resources.ResourcesFactory;
import io.vrap.rmf.raml.model.resources.UriParameter;
import io.vrap.rmf.raml.model.responses.Body;
import io.vrap.rmf.raml.model.responses.BodyContainer;
import io.vrap.rmf.raml.model.types.*;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides helper methods used in our xcore files.
 *
 * We need this because we wrap some java value classes in EMF data types and
 * EMF datatypes can"t expose any methods defined on the wrapped java type.
 */
public class ModelHelper {
    private ModelHelper() {
    }

    public static boolean testPattern(final TypedElement typedElement, final String value) {
        return typedElement.getPattern().test(value);
    }

    public static UriTemplate fullUri(final Resource resource) {
        final UriTemplate relativeUri = resource.getRelativeUri();

        final UriTemplate fullUriTemplate;
        if (relativeUri == null) {
            fullUriTemplate = null;
        } else {
            final Resource resourceParent = resource.getParent();
            final UriTemplate fullParentUri = resourceParent != null ? fullUri(resourceParent) : null;

            final StringBuffer stringBuffer = new StringBuffer();
            if (fullParentUri != null && !fullParentUri.getTemplate().equals("/")) {
                stringBuffer.append(fullParentUri.getTemplate());
            }
            stringBuffer.append(relativeUri.getTemplate());

            final String fullUri = stringBuffer.toString();
            fullUriTemplate = UriTemplate.fromTemplate(fullUri);
        }
        return fullUriTemplate;
    }

    public static List<Resource> allContainedResources(final ResourceContainer resourceContainer) {
        final List<Resource> allContainedResources = new ArrayList<>(resourceContainer.getResources());

        allContainedResources.addAll(resourceContainer.getResources().stream()
                .flatMap(r -> allContainedResources(r).stream())
                .collect(Collectors.toList()));

        return allContainedResources;
    }

    /**
     * Returns all implicitly (in the uri template) and explicitly defined {@link UriParameter}s of the
     * given resource.
     *  
     * @param resource the resource
     * @return the list of all uri parameter of the given resource
     */
    public static List<UriParameter> fullUriParameters(final Resource resource) {
    	final List<UriParameter> allUriParameters = new ArrayList<>();
    	
    	final UriTemplate fullUri = resource.getFullUri();
    	if (fullUri != null) {
            for (final String parameter : fullUri.getVariables()) {
                UriParameter uriParameter = resource.getUriParameter(parameter);
                if (uriParameter == null) {
                    uriParameter = ResourcesFactory.eINSTANCE.createUriParameter();
                    uriParameter.setName(parameter);
                    uriParameter.setType(BuiltinType.STRING.getType(resource.eResource().getResourceSet()));
                }
                allUriParameters.add(uriParameter);
            }
        }
    	
    	return allUriParameters;
    }

    /**
     * Returns implicitly (in the uri template) and explicitly defined {@link UriParameter}s of the
     * given resource.
     *
     * @param resource the resource
     * @return the list of all uri parameter of the given resource
     */
    public static List<UriParameter> relativeUriParameters(final Resource resource) {
        final List<UriParameter> uriParameters = new ArrayList<>();

        final UriTemplate relativeUri = resource.getRelativeUri();
        if (relativeUri != null) {
            for (final String parameter : relativeUri.getVariables()) {
                UriParameter uriParameter = resource.getUriParameter(parameter);
                if (uriParameter == null) {
                    uriParameter = ResourcesFactory.eINSTANCE.createUriParameter();
                    uriParameter.setName(parameter);
                    uriParameter.setType(BuiltinType.STRING.getType(resource.eResource().getResourceSet()));
                }
                uriParameters.add(uriParameter);
            }
        }

        return uriParameters;
    }
    
    public static String resourcePath(final Resource resource) {
        final UriTemplate fullUri = resource.getFullUri();
        return fullUri != null ? fullUri.getTemplate() : "";
    }

    public static String resourcePathName(final Resource resource) {
        final String[] fragments = resourcePath(resource).split("/");

        final LinkedList<String> nonExpressionFragments = Stream.of(fragments)
                .filter(fragment -> !fragment.contains("{"))
                .collect(Collectors.toCollection(LinkedList::new));
        return nonExpressionFragments.isEmpty() ? "" : nonExpressionFragments.getLast();
    }

    public static Body getBody(final BodyContainer container, final String contentType) {
        final MediaType parsedContentType = MediaType.parse(contentType);
        return container.getBodies().stream()
                .filter(body -> Optional.ofNullable(body.getContentMediaType()).filter(mediaType -> parsedContentType.is(mediaType)).isPresent())
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns all non-pattern properties of the given object type as map.
     *
     * @param objectTypeFacet the object type
     * @return map of none-pattern properties
     */
    public static Map<String, Property> getAllPropertiesAsMap(final ObjectTypeFacet objectTypeFacet) {
        final Predicate<Property> withoutPattern = p -> p.getPattern() == null;
        if (objectTypeFacet instanceof ObjectType) {
            final ObjectType objectType = (ObjectType) objectTypeFacet;
            return getAllPropertiesAsMapInternal(objectType, withoutPattern);
        } else {
            return getPropertiesAsMapInternal(objectTypeFacet, withoutPattern);
        }
    }

    public static EList<MediaType> getMediaTypes(final List<String> mediaTypes) {
        final List<MediaType> types = mediaTypes.stream().map(MediaType::parse).collect(Collectors.toList());
        return ECollections.toEList(types);
    }

    public static MediaType getMediaType(final String mediaType) {
        return MediaType.parse(mediaType);
    }

    /**
     * Returns all properties (with inherited) of the given object type.

     * If an object type specializes the type of an inherited property,
     * the specialize property will be returned by this method.
     *
     * @param objectType the object type
     * @return list of all properties ordered by inheritance
     */
    public static EList<Property> getAllProperties(final ObjectType objectType) {
        final Collection<Property> values = getAllPropertiesAsMapInternal(objectType, p -> true).values();
        return ECollections.toEList(values);
    }

    /**
     * Returns all properties (with inherited) of the given object type.

     * If an object type specializes the type of an inherited property,
     * the specialize property will be returned by this method.
     *
     * @param objectType the object type
     * @return list of all properties ordered by inheritance
     */
    public static EList<Property> getAllPatternProperties(final ObjectType objectType) {
        final Collection<Property> values = getAllPropertiesAsMapInternal(objectType, p -> p.getPattern() != null).values();
        return ECollections.toEList(values);
    }

    private static Map<String, Property> getAllPropertiesAsMapInternal(final ObjectType objectType, final Predicate<Property> filter) {
        final Map<String, Property> allPropertiesAsMap = new LinkedHashMap<>();
        if (objectType.getType() instanceof ObjectType) {
            final ObjectType parent = (ObjectType) objectType.getType();
            allPropertiesAsMap.putAll(getAllPropertiesAsMapInternal(parent, filter));
        }
        allPropertiesAsMap.putAll(getPropertiesAsMapInternal(objectType, filter));
        return allPropertiesAsMap;
    }

    private static Map<String, Property> getPropertiesAsMapInternal(final ObjectTypeFacet objectType, final Predicate<Property> filter) {
        final Map<String, Property> allPropertiesAsMap = new LinkedHashMap<>();
        if (objectType != null) {
            final Map<String, Property> filteredProperties = objectType.getProperties().stream()
                    .filter(filter)
                    .collect(Collectors.toMap(Property::getName, Function.identity()));
            allPropertiesAsMap.putAll(filteredProperties);
        }
        return allPropertiesAsMap;
    }
}
