package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.responses.Body;
import io.vrap.rmf.raml.model.responses.util.ResponsesSwitch;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class ResponsesValidator extends AbstractRamlValidator {
    @Override
    public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        final  List<Diagnostic> validationErrors = new ResponsesValidatingVisitor().doSwitch(eObject);

        validationErrors.forEach(diagnostics::add);

        return validationErrors.isEmpty();
    }

    private class ResponsesValidatingVisitor extends ResponsesSwitch<List<Diagnostic>> {
        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseBody(final Body body) {
            final List<Diagnostic> validationErrors = contentTypeMustBeDefined(body);
            return validationErrors;
        }

        private List<Diagnostic> contentTypeMustBeDefined(Body body) {
            final List<Diagnostic> validationErrors = new ArrayList<>();
            final EObject rootContainer = EcoreUtil.getRootContainer(body);

            final boolean contentTypesDefined;
            if (rootContainer instanceof Api) {
                final Api api = (Api) rootContainer;
                contentTypesDefined = api.getMediaType().size() > 0 || body.getContentTypes().size() > 0;
            } else {
                contentTypesDefined = body.getContentTypes().size() > 0;
            }
            if (!contentTypesDefined) {
                validationErrors.add(error(body, "Content type undefined"));
            }
            return validationErrors;
        }
    }
}
