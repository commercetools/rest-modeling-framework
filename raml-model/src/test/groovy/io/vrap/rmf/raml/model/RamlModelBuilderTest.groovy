package io.vrap.rmf.raml.model

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.resources.Resource
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
        Resource userResource = api.resources[0]
        userResource.methods.size() == 2

        Method getMethod = userResource.getMethod(HttpMethod.GET)
        getMethod != null
        getMethod.headers.size() == 1
        getMethod.headers[0].name == 'X-Correlation-Id'
        getMethod.queryParameters.size() == 1
        getMethod.queryParameters[0].name == 'expand'
        getMethod.responses.size() == 1
        getMethod.responses[0].statusCode == "200"
        getMethod.responses[0].bodies.size() == 1
        getMethod.responses[0].bodies[0].contentTypes == api.mediaType
        getMethod.responses[0].bodies[0].type == user

        Method postMethod = userResource.getMethod(HttpMethod.POST)
        postMethod != null
        postMethod.headers.size() == 1
        postMethod.headers[0].name == 'X-Correlation-Id'
        postMethod.bodies.size() == 1
        postMethod.bodies[0].type == userDraft
        postMethod.responses.size() == 1
        postMethod.responses[0].statusCode == "201"
        postMethod.responses[0].bodies.size() == 1
        postMethod.responses[0].bodies[0].type == user

        userResource.resources.size() == 1
        Resource singleUserResource = userResource.resources[0]
        singleUserResource.uriParameters.size() == 1
        singleUserResource.uriParameters[0].name == 'ID'
    }

    def "should resolve ct api"() {
        when:
        URI uri = uriFromClasspath("/commercetools-api-reference-master/api.raml")
        Api api = modelBuilder.buildApi(uri)
        then:
        api != null
    }

}
