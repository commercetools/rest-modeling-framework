package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api

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

    def "type-name-with-reserved-name"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: My API

                annotationTypes:
                    type:
                        description: This annotation type use a reserved name as name.
                traits:
                    type:
                        description: This trait use a reserved name as name.
                        
                types:
                    type:
                        description: This type use a reserved name as name.
                
                resourceTypes:
                    type:
                        description: This resource type use a reserved name as name.
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.getAnnotationType("type") != null
        ramlModelResult.rootObject.getTrait("type") != null
        ramlModelResult.rootObject.getType("type") != null
        ramlModelResult.rootObject.getResourceType("type") != null
    }
}
