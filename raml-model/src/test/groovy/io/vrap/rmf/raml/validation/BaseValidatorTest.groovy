package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.validation.RamlValidationSetup
import org.eclipse.emf.common.util.BasicDiagnostic
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.Diagnostician
import spock.lang.Specification

/**
 * Base class for validator test, which ensures that the validators are setup correctly.
 */
abstract class BaseValidatorTest extends Specification {
    protected Diagnostic diagnostic

    def setupSpec() {
        RamlValidationSetup.setup()
    }

    def setup() {
        diagnostic = new BasicDiagnostic()
    }

    def validate(EObject eObject) {
        Diagnostician.INSTANCE.validate(eObject, diagnostic, new HashMap<Object, Object>())
    }
}
