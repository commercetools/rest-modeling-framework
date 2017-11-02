package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.facets.ProtocolsFacet;
import io.vrap.rmf.raml.model.facets.util.FacetsSwitch;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.resources.util.ResourcesSwitch;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ResourcesValidator extends AbstractRamlValidator {
    private static final  Predicate<String> VALID_PROTOCOL = Pattern.compile("(http)|(https)|(HTTP)|(HTTPS)").asPredicate();

    @Override
    public boolean validate(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        final List<Diagnostic> validationErrors = new ArrayList<>();

        validationErrors.addAll(new ProtocolFacetsValidatingVisitor().doSwitch(eObject));
        validationErrors.addAll(new ResourcesValidatingVisitor().doSwitch(eObject));

        validationErrors.forEach(diagnostics::add);

        return validationErrors.isEmpty();
    }

    private class ProtocolFacetsValidatingVisitor extends FacetsSwitch<List<Diagnostic>> {
        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseProtocolsFacet(final ProtocolsFacet protocolsFacet) {
            final List<String> invalidProtocols = protocolsFacet.getProtocols().stream()
                    .filter(VALID_PROTOCOL.negate())
                    .collect(Collectors.toList());
            return invalidProtocols.stream()
                    .map(invalidProtocol -> error("Invalid protocol " + invalidProtocol, protocolsFacet))
                    .collect(Collectors.toList());
        }
    }

    private class ResourcesValidatingVisitor extends ResourcesSwitch<List<Diagnostic>> {
        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseResource(final Resource resource) {
            final List<Method> optionalMethods = resource.getMethods().stream()
                    .filter(method -> !method.isRequired())
                    .collect(Collectors.toList());

            return optionalMethods.stream()
                    .map(method -> error("Optional method '" + method + "' not allowed", resource))
                    .collect(Collectors.toList());
        }
    }
}
