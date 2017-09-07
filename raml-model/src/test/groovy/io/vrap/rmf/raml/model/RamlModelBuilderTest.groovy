package io.vrap.rmf.raml.model

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.types.AnyType
import io.vrap.rmf.raml.persistence.ResourceFixtures
import spock.lang.PendingFeature
import spock.lang.Shared
import org.eclipse.emf.common.util.URI
import spock.lang.Specification

/**
 * Unit tests for {@link RamlModelBuilder}.
 */
class RamlModelBuilderTest extends Specification implements ResourceFixtures {
    @Shared
    RamlModelBuilder modelBuilder = new RamlModelBuilder()

    def "should resolve resource types of a resource"() {
        when:
        URI uri = uriFromClasspath("/resourcetypes/api-with-resource-types.raml")
        Api api = modelBuilder.buildApi(uri)
        then:
        AnyType user = api.getType('User')
        AnyType userDraft = api.getType('UserDraft')
        api.resources.size() == 1
        api.resources[0].methods.size() == 2

        Method getMethod = api.resources[0].getMethod(HttpMethod.GET)
        getMethod != null
        getMethod.responses.size() == 1
        getMethod.responses[0].statusCode == "200"
        getMethod.responses[0].bodies.size() == 1
        getMethod.responses[0].bodies[0].type == user

        Method postMethod = api.resources[0].getMethod(HttpMethod.POST)
        postMethod != null
        postMethod.bodies.size() == 1
        postMethod.bodies[0].type == userDraft
        postMethod.responses.size() == 1
        postMethod.responses[0].statusCode == "201"
        postMethod.responses[0].bodies.size() == 1
        postMethod.responses[0].bodies[0].type == user
    }

    def "should resolve ct api"() {
        when:
        URI uri = uriFromClasspath("/commercetools-api-reference-master/api.raml")
        Api api = modelBuilder.buildApi(uri)
        then:
        api != null
    }

}
