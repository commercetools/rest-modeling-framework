package io.vrap.rmf.raml.model.util;

import com.damnhandy.uri.template.UriTemplate;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.types.PatternProperty;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
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

    public static boolean testPattern(final PatternProperty property, final String value) {
        return property.getPattern().test(value);
    }

    public static UriTemplate fullUri(final Resource resource) {
        final UriTemplate relativeUri = resource.getRelativeUri();

        final UriTemplate fullUriTemplate;
        if (relativeUri == null) {
            fullUriTemplate = null;
        } else {
            final Stack<String> uris = new Stack<>();
            uris.push(relativeUri.getTemplate());

            for (Resource parent = resource.getParent(); parent != null; parent = parent.getParent()) {
                uris.push(parent.getRelativeUri().getTemplate());
            }

            final StringBuffer stringBuffer = new StringBuffer();
            while (!uris.empty()) {
                stringBuffer.append(uris.pop());
            }

            final String fullUri = stringBuffer.toString();
            fullUriTemplate = UriTemplate.fromTemplate(fullUri);
        }
        return fullUriTemplate;
    }

    public static List<Resource> allContainedResources(final Resource resource) {
        final List<Resource> allContainedResources = new ArrayList<>(resource.getResources());

        allContainedResources.addAll(resource.getResources().stream()
                .flatMap(r -> allContainedResources(r).stream())
                .collect(Collectors.toList()));

        return allContainedResources;
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
}
