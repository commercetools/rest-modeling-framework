package io.vrap.rmf.raml

import com.google.common.base.Charsets
import io.vrap.rmf.raml.model.RamlModelBuilder
import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.responses.Body
import io.vrap.rmf.raml.model.types.Annotation
import io.vrap.rmf.raml.model.types.Example
import io.vrap.rmf.raml.model.util.StringCaseFormat
import io.vrap.rmf.raml.model.values.ArrayInstance
import io.vrap.rmf.raml.model.values.ObjectInstance
import io.vrap.rmf.raml.persistence.ResourceFixtures
import org.eclipse.emf.common.util.URI
import spock.lang.Ignore
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

    def "simple-baseuri"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri: http://example.com
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        api.baseUri.expand() == "http://example.com"
    }

    def "baseuri-with-value"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri:
            value: http://example.com
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        api.baseUri.expand() == "http://example.com"
    }

    def "baseuri-with-annotation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        annotationTypes:
            condition : string
                
        baseUri:
            (condition): test
            value: http://example.com
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        api.baseUri.expand() == "http://example.com"
        api.baseUri.getAnnotation('condition').value.value == 'test'
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

    def "example" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Annotating Examples
                
                annotationTypes:
                    condition : string
                
                /request:
                    get:
                        body:
                            application/json:
                                example:
                                    (condition): test
                                    value: |
                                        {a:1}
                                examples:
                                    example1:
                                        (condition): test
                                        strict: true
                                        value: |
                                            {a:1}
                                    example2:
                                        value: |
                                            {a:2}
                                    example3:
                                        value: |
                                            a:3
                                    example4: "{a:4}"
                                    example5:
                                        name: foo
                                    example6:
                                        (condition): test
                                        value:
                                            name: foo
                                            value: bar
                                    example7:
                                        (condition): test
                                        value:
                                            - foo
                                            - bar
                                    example8:
                                        - foo
                                        - bar
                                    example9:
                                        value:
                                            - foo
                                            - bar
                                    example10:
                                        displayName: Example10
                                        description: Lorem ipsum
                                        strict: false
                                        (condition): test
                                        value:
                                            - foo
                                            - bar
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
        List<Example> examples = ramlModelResult.rootObject.resources[0].getMethod(HttpMethod.GET).getBody('application/json').type.examples;

        examples[1].instanceValue.trim() == "{a:1}".trim()
        examples[2].instanceValue.trim() == "{a:2}".trim()
        examples[3].instanceValue.trim() == "a:3".trim()
        examples[4].instanceValue.trim() == "{a:4}".trim()

        ((ObjectInstance)examples[5].value).value[0].name == "name";
        ((ObjectInstance)examples[5].value).value[0].value.value == "foo";

        ((ObjectInstance)examples[6].value).value[0].name == "name";
        ((ObjectInstance)examples[6].value).value[0].value.value == "foo";
        ((ObjectInstance)examples[6].value).value[1].name == "value";
        ((ObjectInstance)examples[6].value).value[1].value.value == "bar";

        ((ArrayInstance)examples[7].value).value[0].value.trim() == "foo";
        ((ArrayInstance)examples[7].value).value[1].value.trim() == "bar";
        ((ArrayInstance)examples[8].value).value[0].value.trim() == "foo";
        ((ArrayInstance)examples[8].value).value[1].value.trim() == "bar";
        ((ArrayInstance)examples[9].value).value[0].value.trim() == "foo";
        ((ArrayInstance)examples[9].value).value[1].value.trim() == "bar";

        ((ArrayInstance)examples[10].value).value[0].value.trim() == "foo";
        ((ArrayInstance)examples[10].value).value[1].value.trim() == "bar";
        examples[10].displayName.value == 'Example10'
        examples[10].description.value == 'Lorem ipsum'

        examples[0].strict.value
        examples[1].strict.value
        !examples[10].strict.value

        examples[1].getAnnotation('condition').value.value == 'test'
        examples[6].getAnnotation('condition').value.value == 'test'
        examples[7].getAnnotation('condition').value.value == 'test'
        examples[10].getAnnotation('condition').value.value == 'test'
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

    @Ignore
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

    @Ignore
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

    @Ignore
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

    def "multi-extension"() {
        when:
        writeFile("api.raml",
                '''\
                #%RAML 1.0
                title: Some API
                /category:
                    get:
                        description: Some API
                ''')
        writeFile("extend.raml",
                Arrays.asList("api.raml"),
                '''\
                #%RAML 1.0 Extension
                usage: extends api
                extends: api.raml
                /category:
                    post:
                        description: Extended API
                ''')
        RamlModelResult<Api> ramlModelResult = constructApi(
                "final.raml",
                Arrays.asList("extend.raml"),
                '''\
                #%RAML 1.0 Extension
                usage: final extension
                extends: extend.raml
                /category:
                    delete:
                        description: Final API
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources[0].getMethod(HttpMethod.GET).description == "Some API"
        ramlModelResult.rootObject.resources[0].getMethod(HttpMethod.POST).description == "Extended API"
        ramlModelResult.rootObject.resources[0].getMethod(HttpMethod.DELETE).description == "Final API"
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
