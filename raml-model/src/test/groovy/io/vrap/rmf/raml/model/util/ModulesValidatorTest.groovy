package io.vrap.rmf.raml.model.util

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.modules.ModulesFactory
import io.vrap.rmf.raml.model.modules.util.ModulesValidator
import org.eclipse.emf.common.util.BasicDiagnostic
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.ecore.util.Diagnostician
import spock.lang.Specification

/**
 * Unit tests for {@link ModulesValidator}
 */
class ModulesValidatorTest extends Specification {

    def "should report invalid protocols"() {
        when:
        Api api = ModulesFactory.eINSTANCE.createApi()
        api.protocols.add('ftp')
        BasicDiagnostic diagnostic = new BasicDiagnostic()
        then:
        Diagnostician.INSTANCE.validate(api, diagnostic, new HashMap<Object, Object>()) == false
        diagnostic.severity != Diagnostic.OK
    }

    def "should accept valid protocols"() {
        when:
        Api api = ModulesFactory.eINSTANCE.createApi()
        api.protocols.add(protocol)
        BasicDiagnostic diagnostic = new BasicDiagnostic()
        then:
        Diagnostician.INSTANCE.validate(api, diagnostic, new HashMap<Object, Object>()) == true
        diagnostic.severity == Diagnostic.OK
        where:
        protocol << ['http', 'HTTP', 'https', 'HTTPS']
    }
}
