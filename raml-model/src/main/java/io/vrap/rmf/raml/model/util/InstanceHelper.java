package io.vrap.rmf.raml.model.util;

import io.vrap.rmf.nodes.Node;
import io.vrap.rmf.nodes.NodeModelBuilder;
import io.vrap.rmf.nodes.antlr.NodeToken;
import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
import io.vrap.rmf.raml.model.RamlDiagnostic;
import io.vrap.rmf.raml.model.RamlModelResult;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.Instance;
import io.vrap.rmf.raml.validation.InstanceValidator;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides helper methods for working with {@link Instance}s.
 */
public interface InstanceHelper {

    /**
     * Parses the instance given as JSON text and validates it against the given type.
     *
     * @param text the JSON serialized instance
     * @param type the type to validate the instance against
     *
     * @return the parsed and validated instance
     */
    static RamlModelResult<Instance> parseAndValidate(final String text, final AnyType type) {
        final Instance instance = parse(text, resourceFile(type));

        final List<Resource.Diagnostic> validationResults = validate(instance, type).stream()
                .map(RamlDiagnostic::of)
                .collect(Collectors.toList());

        return RamlModelResult.of(validationResults, instance);
    }

    static Instance parse(final String text) {
        return parse(text, null);
    }

    static Instance parse(final String text, String resource) {
        final NodeModelBuilder nodeModelBuilder = new NodeModelBuilder();
        final Node node = resource == null ?
                nodeModelBuilder.parseYaml(text) : nodeModelBuilder.parseJson(text);
        return new NodeToInstanceTransformation().doSwitch(node);
    }

    static Instance parseJson(final String text) {
        return parseJson(text, null);
    }

    static Instance parseJson(final String text, final String resource) {
        final NodeModelBuilder nodeModelBuilder = new NodeModelBuilder();
        final Node node = nodeModelBuilder.parseJson(text);
        return new NodeToInstanceTransformation().doSwitch(node);
    }

    static List<Diagnostic> validate(final Instance instance, final AnyType type) {
        return new InstanceValidator().validate(instance, type);
    }

    static String resourceFile(final EObject object) {
        String source = null;
        final NodeTokenProvider ramlTokenProvider =
                (NodeTokenProvider) EcoreUtil.getExistingAdapter(object, NodeTokenProvider.class);
        if (ramlTokenProvider != null) {
            final NodeToken nodeToken = ramlTokenProvider.getStart();
            source = nodeToken.getLocation();
        }

        return source;
    }
}
