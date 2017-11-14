package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.MethodBase;
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
import java.util.stream.Collectors;

class ResourcesValidator extends AbstractRamlValidator {
    private final ProtocolFacetsValidator protocolFacetsValidator =
            new ProtocolFacetsValidator(this::error);
    private final ResourcesValidatingVisitor visitor = new ResourcesValidatingVisitor();

    @Override
    public boolean validate(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        final List<Diagnostic> validationErrors = new ArrayList<>();

        validationErrors.addAll(visitor.doSwitch(eObject));

        validationErrors.forEach(diagnostics::add);

        return validationErrors.isEmpty();
    }

    private class ResourcesValidatingVisitor extends ResourcesSwitch<List<Diagnostic>> {
        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseMethodBase(final MethodBase methodBase) {
            return protocolFacetsValidator.validate(methodBase);
        }

        @Override
        public List<Diagnostic> caseResource(final Resource resource) {
            final List<Diagnostic> validationErrors = noOptionalMethodsAllowed(resource);

            return validationErrors;
        }

        private List<Diagnostic> noOptionalMethodsAllowed(Resource resource) {
            final List<Method> optionalMethods = resource.getMethods().stream()
                    .filter(method -> !method.isRequired())
                    .collect(Collectors.toList());

            return optionalMethods.stream()
                    .map(method -> error("Optional method '" + method + "' not allowed", resource))
                    .collect(Collectors.toList());
        }
    }
}
