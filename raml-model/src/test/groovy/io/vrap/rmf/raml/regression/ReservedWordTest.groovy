package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.types.AnyType
import io.vrap.rmf.raml.model.types.Instance
import io.vrap.rmf.raml.model.util.InstanceHelper
import io.vrap.rmf.raml.validation.InstanceValidator

class ReservedWordTest extends RegressionTest {
    def "uses-traits-library"() {
        when:
        writeFile(
                "traits.raml",
                '''\
                #%RAML 1.0 Library
                usage: My traits
                traits:
                    versioned:
                        queryParameters:
                            version:
                                type: number
        ''')
        RamlModelResult<Api> ramlModelResult = constructApi(
                "api.raml",
                Arrays.asList("traits.raml"),
                '''\
                #%RAML 1.0
                title: My API
                
                uses:
                    traits: traits.raml
                '''
        )
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.getUses().size() == 1
    }
}
