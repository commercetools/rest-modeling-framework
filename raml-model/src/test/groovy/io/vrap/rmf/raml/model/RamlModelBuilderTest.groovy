package io.vrap.rmf.raml.model

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
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
        api.types.size() == 1
        AnyType userDraft = api.types[0]
        api.resources.size() == 1
        api.resources[0].methods.size() == 1
        api.resources[0].methods[0].method == HttpMethod.GET
        api.resources[0].methods[0].responses.size() == 1
        api.resources[0].methods[0].responses[0].statusCode == "200"
        api.resources[0].methods[0].responses[0].bodies.size() == 1
        api.resources[0].methods[0].responses[0].bodies[0].type == userDraft
    }

    @PendingFeature
    def "should resolve ct api"() {
        when:
        URI uri = uriFromClasspath("/commercetools-api-reference-master/api.raml")
        Api api = modelBuilder.buildApi(uri)
        then:
        api != null
    }

}
