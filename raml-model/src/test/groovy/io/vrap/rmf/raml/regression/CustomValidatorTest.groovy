package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.modules.util.ModulesSwitch
import io.vrap.rmf.raml.validation.AbstractRamlValidator
import io.vrap.rmf.raml.validation.RamlValidationSetup
import io.vrap.rmf.raml.validation.RamlValidator
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.DiagnosticChain
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject

class CustomValidatorTest extends RegressionTest {
    class MyApiValidator extends AbstractRamlValidator implements RamlValidator {
        class ApiSwitch extends ModulesSwitch<List<Diagnostic>> {
            @Override
            List<Diagnostic> caseApi(Api object) {
                final List<Diagnostic> validationErrors = new ArrayList<>();

                validationErrors.add(error(object, "invalid: {0}", object.title))

                return validationErrors;
            }
        }
        private final ModulesSwitch<List<Diagnostic>> validator = new ApiSwitch()
        @Override
        boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
            final List<Diagnostic> validationErrors = new ArrayList<>();

            validationErrors.addAll(validator.doSwitch(eObject));
            validationErrors.forEach(diagnostics.&add);

            return validationErrors.isEmpty();

        }
    }

    def "custom-validation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''
        #%RAML 1.0
        title: Some API
                ''', Arrays.asList(new MyApiValidator())
        )
        RamlValidationSetup.setup()
        then:
        ramlModelResult.validationResults.size() == 1
        ramlModelResult.validationResults[0].message == "invalid: Some API"
    }
}
