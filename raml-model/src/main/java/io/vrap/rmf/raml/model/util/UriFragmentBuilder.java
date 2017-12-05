package io.vrap.rmf.raml.model.util;

import com.google.common.net.MediaType;
import io.vrap.functional.utils.TypeSwitch;
import io.vrap.rmf.raml.model.elements.NamedElement;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.responses.Body;
import io.vrap.rmf.raml.model.responses.Response;
import io.vrap.rmf.raml.model.types.Annotation;
import io.vrap.rmf.raml.model.values.StringInstance;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UriFragmentBuilder {
    private static final Collector<CharSequence, ?, String> SEGMENT_JOINER =
            Collectors.joining("/", "/", "");

    private final TypeSwitch<EObject, List<String>> uriFragmentsBuilderSwitch = new TypeSwitch<EObject, List<String>>()
            .on(Body.class, this::body)
            .on(NamedElement.class, this::namedElement)
            .on(Annotation.class, this::annotation)
            .on(Method.class, this::method)
            .on(Resource.class, this::resource)
            .on(Response.class, this::response)
            .on(StringInstance.class, this::stringInstance)
            .fallthrough(eObject -> new ArrayList<>());

    public String getURIFragment(final EObject eObject) {
        return uriFragmentsBuilderSwitch.apply(eObject).stream().collect(SEGMENT_JOINER);
    }

    private List<String> namedElement(final NamedElement identifiableElement) {
        final List<String> segments = new ArrayList<>();
        segments.add(identifiableElement.eContainmentFeature().getName());
        segments.add(identifiableElement.getName());

        return segments;
    }

    private List<String> annotation(final Annotation annotation) {
        if (annotation.eContainer() != null && annotation.getType() != null) {
            final List<String> segments = uriFragmentsBuilderSwitch.apply(annotation.eContainer());
            segments.add(annotation.eContainmentFeature().getName());
            segments.add(annotation.getType().getName());
            return segments;
        }
        return new ArrayList<>();
    }

    private List<String> method(final Method method) {
        if (method.eContainer() != null) {
            final List<String> segments = uriFragmentsBuilderSwitch.apply(method.eContainer());
            segments.add(method.eContainmentFeature().getName());
            segments.add(method.getMethod().getLiteral());
            return segments;
        }
        return new ArrayList<>();
    }

    private List<String> response(final Response response) {
        if (response.eContainer() != null) {
            final List<String> segments = uriFragmentsBuilderSwitch.apply(response.eContainer());
            segments.add(response.eContainmentFeature().getName());
            segments.add(response.getStatusCode());
            return segments;
        }
        return new ArrayList<>();
    }

    private List<String> body(final Body body) {
        if (body.eContainer() != null) {
            final List<String> segments = uriFragmentsBuilderSwitch.apply(body.eContainer());
            segments.add(body.eContainmentFeature().getName());
            segments.add(body.getContentTypes().stream()
                    .map(MediaType::toString)
                    .collect(Collectors.joining(",")));
            return segments;
        }
        return new ArrayList<>();
    }

        private List<String> resource(final Resource resource) {
        final List<String> segments = new ArrayList<>();
        segments.add(resource.eContainmentFeature().getName());
        segments.add(resource.getRelativeUri().getTemplate());

        if (resource.eContainer() instanceof Resource) {
            final List<String> parentSegments = uriFragmentsBuilderSwitch.apply(resource.eContainer());
            parentSegments.addAll(segments);
            return parentSegments;
        } else {
            return segments;
        }
    }

    private List<String> stringInstance(final StringInstance stringInstance) {
        return uriFragmentsBuilderSwitch.apply(stringInstance.eContainer());
    }
}
