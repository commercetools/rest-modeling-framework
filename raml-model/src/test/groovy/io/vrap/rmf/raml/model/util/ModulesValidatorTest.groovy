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
class ModulesValidatorTest extends BaseValidatorTest {
    Api api

    def setup() {
        api = ModulesFactory.eINSTANCE.createApi()
        api.title = 'Test Api'
    }

    def "should report missing title"() {
        when:
        api.title = title
        then:
        validate(api) == false
        where:
        title << [ null, '']
    }

    def "should report invalid protocols"() {
        when:
        api.protocols.add('ftp')
        then:
        validate(api) == false
        diagnostic.severity != Diagnostic.OK
    }

    def "should accept valid protocols"() {
        when:
        api.protocols.add(protocol)
        then:
        validate(api) == true
        diagnostic.severity == Diagnostic.OK
        where:
        protocol << ['http', 'HTTP', 'https', 'HTTPS']
    }
}
