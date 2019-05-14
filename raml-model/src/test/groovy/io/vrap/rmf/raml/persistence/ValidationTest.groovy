package io.vrap.rmf.raml.persistence

import io.vrap.rmf.raml.model.RamlDiagnostic
import io.vrap.rmf.raml.model.RamlModelBuilder
import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import org.eclipse.emf.common.util.URI
import spock.lang.Specification

/**
 * Test for validation on raml files with libraries.
 */
class ValidationTest extends Specification implements ResourceFixtures {

    def "/api/api-using-library-with-errors.raml"() {
        when:
        URI uri = uriFromClasspath("/api/api-using-library-with-errors.raml")
        RamlModelResult<Api> result =  new RamlModelBuilder().buildApi(uri)

        then:
        result.validationResults.size() == 1
        RamlDiagnostic diagnostic = result.validationResults[0]
        diagnostic.message == "Value 'v3' is not defined in enum facet '[v1,v2]'"
    }

    def "/api/api-with-duplicate-resource.raml"() {
        when:
        URI uri = uriFromClasspath("/api/api-with-duplicate-resource.raml")
        RamlModelResult<Api> result =  new RamlModelBuilder().buildApi(uri)

        then:
        result.validationResults.size() == 1
        RamlDiagnostic diagnostic = result.validationResults[0]
        diagnostic.message == "Duplicate resource '/project/test'"
    }


    def "/api.raml"() {
        when:
        URI uri = URI.createFileURI("/Users/mkoester/Development/commercetools-api-reference/api.raml")
        RamlModelResult<Api> result =  new RamlModelBuilder().buildApi(uri)
        then:
        result.validationResults.size() == 0
    }

}
