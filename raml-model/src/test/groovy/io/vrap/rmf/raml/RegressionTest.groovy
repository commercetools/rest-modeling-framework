package io.vrap.rmf.raml

import com.google.common.base.Charsets
import io.vrap.rmf.raml.model.RamlModelBuilder
import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.util.StringCaseFormat
import io.vrap.rmf.raml.persistence.ResourceFixtures
import org.eclipse.emf.common.util.URI
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class RegressionTest extends Specification implements ResourceFixtures {
    Path featureFile
    RamlModelBuilder modelBuilder = new RamlModelBuilder()

    def setup() {
        String testFileName = StringCaseFormat.LOWER_HYPHEN_CASE.apply(specificationContext.currentFeature.getName());
        featureFile = Paths.get("./tmp-${testFileName}");
    }

    def cleanup() {
        if (featureFile.toFile().exists()) {
            featureFile.toFile().delete()
        }
    }

    def "baseuriparameter-with-invalid-type.raml"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri: http://{a}.myapi.org
        baseUriParameters:
            a:
                displayName: A
                description: This is A
                type: X
        ''')
        then:
        ramlModelResult.validationResults.size() == 1
        Api api = ramlModelResult.rootObject
        api.types.size() == 0
        api.baseUriParameters.size() == 1
        api.baseUriParameters.get(0).type != null
        api.baseUriParameters.get(0).type.type.eIsProxy() == true
    }

    RamlModelResult<Api> constructApi(String input) {
        Files.write(featureFile, input.stripIndent().getBytes(Charsets.UTF_8));
        URI i = URI.createURI(featureFile.toAbsolutePath().toUri().toString())
        return modelBuilder.buildApi(i)
    }
}
