package io.vrap.rmf.raml

import com.google.common.base.Charsets
import io.vrap.rmf.raml.model.RamlModelBuilder
import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.types.Annotation
import io.vrap.rmf.raml.model.types.QueryParameter
import io.vrap.rmf.raml.model.util.StringCaseFormat
import io.vrap.rmf.raml.persistence.ResourceFixtures
import org.eclipse.emf.common.util.URI
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class RegressionTest extends Specification implements ResourceFixtures {
    List<Path> featureFiles = new ArrayList<>()

    def cleanup() {
        featureFiles.each {
            if (it.toFile().exists()) {
                it.toFile().delete()
            }
        }
        featureFiles.clear()
    }

    def "baseuriparameter-with-invalid-type"() {
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

    def "number-multipleof"() {
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

    def "nested-discriminator-example-validation"() {
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

    def "expand-traits-with-resource-type" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        traits:
            versioned:
                queryParameters:
                    version:
                        type: number
        resourceTypes:
            base:
                delete:
                    is:
                        - versioned
        /category:
            type: base
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.get(0).methods.get(0).queryParameters.size() == 1
    }

    def "expand-traits-without-resource-type" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        traits:
            versioned:
                queryParameters:
                    version:
                        type: number
        /category:
            delete:
                is:
                    - versioned
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.get(0).methods.get(0).queryParameters.size() == 1
    }

    def "extend-trait-with-annotation" () {
        when:
        writeFile(
                "api.raml",
                '''\
                #%RAML 1.0
                title: Some API
                resourceTypes:
                    base:
                        delete?:
                            is:
                                - versioned
                traits:
                    versioned:
                        queryParameters:
                            version:
                                type: number
                /category:
                    /{ID}:
                        type: base
                        delete:
        ''')
        RamlModelResult<Api> ramlModelResult = constructApi(
                "extend.raml",
                Arrays.asList("api.raml"),
                '''\
                #%RAML 1.0 Extension
                usage: Add postman test scripts
                extends: api.raml
                annotationTypes:
                    postman-default-value:
                        type: string
                        allowedTargets: TypeDeclaration
                traits:
                    versioned:
                        queryParameters:
                            version:
                                (postman-default-value): "{{version}}"
                '''
        )
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        Method method = api.resources.get(0).resources.get(0).getMethod(HttpMethod.DELETE)
        api.getAnnotationType("postman-default-value") != null
        Annotation annotation = method.queryParameters.get(0).getAnnotation("postman-default-value")
        annotation.value.value == "{{version}}"
    }

    def "extend-trait-with-annotation-multi-usage" () {
        when:
        writeFile(
                "api.raml",
                '''\
                #%RAML 1.0
                title: Some API
                resourceTypes:
                    base:
                        delete?:
                            is:
                                - versioned
                traits:
                    versioned:
                        queryParameters:
                            version:
                                type: number
                /category:
                    /{ID}:
                        type: base
                        delete:
                /customer:
                    /{ID}:
                        type: base
                        delete:
        ''')

        RamlModelResult<Api> ramlModelResult = constructApi(
                "extend.raml",
                Arrays.asList("api.raml"),
                '''\
                #%RAML 1.0 Extension
                usage: Add postman test scripts
                extends: api.raml
                annotationTypes:
                    postman-default-value:
                        type: string
                        allowedTargets: TypeDeclaration
                traits:
                    versioned:
                        queryParameters:
                            version:
                                (postman-default-value): "{{version}}"
                '''
        )
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        Method method = api.resources.get(0).resources.get(0).getMethod(HttpMethod.DELETE)
        api.getAnnotationType("postman-default-value") != null
        Annotation annotation = method.queryParameters.get(0).getAnnotation("postman-default-value")
        annotation.value.value == "{{version}}"
    }

    RamlModelResult<Api> constructApi(String input) {
        constructApi('api.raml', input)
    }

    RamlModelResult<Api> constructApi(String fileName, String input) {
        constructApi(fileName, null, input)
    }

    RamlModelResult<Api> constructApi(String fileName, List<String> usesFiles, String input) {
        URI i = writeFile(fileName, usesFiles, input)
        return new RamlModelBuilder().buildApi(i)
    }

    URI writeFile(String fileName, String input) {
        return writeFile(fileName, null, input);
    }

    URI writeFile(String fileName, List<String> usesFiles, String input) {
        Path featureFile = Paths.get("./" + getTmpFileName(fileName));

        if (usesFiles != null) {
            for (String file: usesFiles) {
                input = input.replace(file, getTmpFileName(file))
            }
        }

        Files.write(featureFile, input.stripIndent().getBytes(Charsets.UTF_8));
        featureFiles.add(featureFile)
        return URI.createURI(featureFile.toAbsolutePath().toUri().toString())
    }

    String getTmpFileName(String fileName) {
        String testFileName = StringCaseFormat.LOWER_HYPHEN_CASE.apply(specificationContext.currentFeature.getName()).replace(".raml", "");
        return "tmp-${fileName.replace(".raml", "")}-${testFileName}.raml";
    }
}
