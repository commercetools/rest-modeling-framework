package io.vrap.rmf.raml.test

import io.vrap.rmf.raml.model.RamlModelBuilder
import io.vrap.rmf.raml.model.modules.Api
import org.eclipse.emf.common.util.URI
import spock.lang.Shared
import spock.lang.Specification

/**
 * Unit tests for {@link io.vrap.rmf.raml.model.RamlModelBuilder}.
 */
class RamlModelBuilderTest extends Specification implements ResourceFixtures {
    @Shared
    RamlModelBuilder modelBuilder = new RamlModelBuilder()

    def "should resolve ct api"() {
        when:
        URI uri = uriFromClasspath("/commercetools-api-reference-master/api.raml")
        Api api = modelBuilder.buildApi(uri)
        then:
        api.eResource().errors.empty == true
    }

    def "should resolve ct api extension"() {
        when:
        URI uri = uriFromClasspath("/commercetools-api-reference-master/update-actions.raml")
        Api api = modelBuilder.buildApi(uri)
        then:
        api.eResource().errors.empty == true
    }
}
