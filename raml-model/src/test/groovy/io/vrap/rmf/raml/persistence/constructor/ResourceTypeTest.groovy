package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.ApiFixtures
import io.vrap.rmf.raml.model.modules.Api
import spock.lang.Specification

class ResourceTypeTest extends Specification implements ApiFixtures {

    def "resource type with uri parameters"() {
        when:
        Api api = constructApi(
                '''\
        resourceTypes:
            resource:
                uriParameters:
                    ID:
        ''')
        then:
        api.resourceTypes.size() == 1
        api.resourceTypes[0].uriParameters.size() == 1
    }

}
