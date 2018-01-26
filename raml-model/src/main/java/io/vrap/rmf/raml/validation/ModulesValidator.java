package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.ApiBase;
import io.vrap.rmf.raml.model.modules.util.ModulesSwitch;
import io.vrap.rmf.raml.model.resources.Resource;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.util.*;
import java.util.stream.Collectors;

class ModulesValidator extends AbstractRamlValidator {
    private final ProtocolFacetsValidator protocolFacetsValidator =
            new ProtocolFacetsValidator();
    private final ModulesValidatingVisitor visitor = new ModulesValidatingVisitor();

    @Override
    public boolean validate(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        final List<Diagnostic> validationErrors = new ArrayList<>();

        validationErrors.addAll(visitor.doSwitch(eObject));

        validationErrors.forEach(diagnostics::add);

        return validationErrors.isEmpty();
    }

    private class ModulesValidatingVisitor extends ModulesSwitch<List<Diagnostic>> {
        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseApiBase(final ApiBase apiBase) {
            return protocolFacetsValidator.validate(apiBase);
        }

        @Override
        public List<Diagnostic> caseApi(final Api api) {
            final List<Diagnostic> validationErrors = new ArrayList<>();

            validationErrors.addAll(caseApiBase(api));
            validationErrors.addAll(noDuplicateResourcePaths(api));

            return validationErrors;
        }

        private List<Diagnostic> noDuplicateResourcePaths(final Api api) {
            final List<Resource> allResources = api.getResources().stream()
                    .flatMap(r -> r.getAllContainedResources().stream())
                    .collect(Collectors.toList());
            allResources.addAll(api.getResources());

            final Set<String> resourcePaths = new HashSet<>();

            return allResources.stream()
                    .filter(r -> {
                        final String resourcePath = r.getResourcePath();
                        return resourcePath.length() > 0 && !resourcePaths.add(resourcePath);
                    })
                    .map(r -> error(r,"Duplicate resource {0}", r.getFullUri().getTemplate()))
                    .collect(Collectors.toList());
        }
    }
}
