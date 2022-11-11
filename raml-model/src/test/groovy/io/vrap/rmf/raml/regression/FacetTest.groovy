
package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.types.ArrayInstance
import io.vrap.rmf.raml.model.types.ObjectInstance
import io.vrap.rmf.raml.model.types.StringInstance

class FacetTest extends RegressionTest {
    def "facet-value"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''
        #%RAML 1.0
        title: "Test"
        
        types:
            Foo:
                type: string
                facets:
                  foo?: string
            Bar:
                type: Foo
                foo: test
                ''')
        then:

        ramlModelResult.validationResults.size() == 0
        with(ramlModelResult.rootObject) {
            with(getType("Foo")) {
                facetTypes.size() == 1
                facetTypes[0].getType().name == "string"
                getFacetType("foo").getType().name == "string"
                getFacetType("foo").required == false
            }
            with(getType("Bar")) {
                facetTypes.size() == 0
            }
        }
    }
}
