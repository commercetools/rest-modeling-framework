package io.vrap.rmf.raml.model.resources

import spock.lang.Specification

/**
 * Unit tests for {@link UriTemplate}.
 */
class UriTemplateTest extends Specification {

    def "toString() should return uri template as string"() {
        when:
        UriTemplate uriTemplate = ResourcesFactory.eINSTANCE.createUriTemplate();

        UriTemplateLiteral uriTemplateLiteral = ResourcesFactory.eINSTANCE.createUriTemplateLiteral()
        uriTemplateLiteral.literal = 'http://localhost/api/'
        uriTemplate.parts.add(uriTemplateLiteral)

        UriTemplateExpression uriTemplateExpression = ResourcesFactory.eINSTANCE.createUriTemplateExpression()
        uriTemplateExpression.variables.add('var1')
        uriTemplate.parts.add(uriTemplateExpression)

        then:
        uriTemplate.toString() == 'http://localhost/api/{var1}'
    }

    def "toString(Map<string, Object>) should return uri template as string"() {
        when:
        UriTemplate uriTemplate = ResourcesFactory.eINSTANCE.createUriTemplate();

        UriTemplateLiteral uriTemplateLiteral = ResourcesFactory.eINSTANCE.createUriTemplateLiteral()
        uriTemplateLiteral.literal = 'http://localhost/api/'
        uriTemplate.parts.add(uriTemplateLiteral)

        UriTemplateExpression uriTemplateExpression = ResourcesFactory.eINSTANCE.createUriTemplateExpression()
        uriTemplateExpression.variables.add('var1')
        uriTemplate.parts.add(uriTemplateExpression)

        uriTemplateLiteral = ResourcesFactory.eINSTANCE.createUriTemplateLiteral()
        uriTemplateLiteral.literal = '/path/'
        uriTemplate.parts.add(uriTemplateLiteral)

        uriTemplateExpression = ResourcesFactory.eINSTANCE.createUriTemplateExpression()
        uriTemplateExpression.variables.add('var2')
        uriTemplate.parts.add(uriTemplateExpression)

        then:
        uriTemplate.toString(["var1": "value1", "var2" : 10]) == 'http://localhost/api/value1/path/10'
    }
}
