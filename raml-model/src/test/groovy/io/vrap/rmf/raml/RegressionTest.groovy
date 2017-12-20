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
    List<Path> featureFiles = new ArrayList<>()
    RamlModelBuilder modelBuilder = new RamlModelBuilder()

    def cleanup() {
        featureFiles.each {
            if (it.toFile().exists()) {
                it.toFile().delete()
            }
        }
        featureFiles.clear()
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
        api.baseUriParameters.get(0).type.type == null
    }

    def "number-multipleof.raml"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        types:
            MyCustomType:
                type: number
                multipleOf: 3
                example: 6
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }

    def "nested discriminator example validation.raml"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        types:
            UpdateAction:
                type: object
                discriminator: action
                properties:
                    action:
                        type: string
            CartDiscountChangeTargetAction:
                type: UpdateAction
                discriminatorValue: changeTarget
                example:
                    {
                        "action": "changeTarget",
                        "target": {
                            "type": "lineItems",
                            "predicate": "sku = \\"mySKU\\""
                        }
                    }
                properties:
                    target:
                        type: CartDiscountTarget
            CartDiscountTarget:
                type: object
                discriminator: type
                properties:
                    type:
                        type: string
            CartDiscountLineItemsTarget:
                type: CartDiscountTarget
                discriminatorValue: lineItems
                properties:
                    predicate:
                        type: string
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }

    RamlModelResult<Api> constructApi(String input) {
        constructApi('default', input)
    }

    RamlModelResult<Api> constructApi(String fileName, String input) {
        String testFileName = StringCaseFormat.LOWER_HYPHEN_CASE.apply(specificationContext.currentFeature.getName());
        Path featureFile = Paths.get("./tmp-${fileName}-${testFileName}");

        Files.write(featureFile, input.stripIndent().getBytes(Charsets.UTF_8));
        featureFiles.add(featureFile)
        URI i = URI.createURI(featureFile.toAbsolutePath().toUri().toString())
        return modelBuilder.buildApi(i)
    }
}
