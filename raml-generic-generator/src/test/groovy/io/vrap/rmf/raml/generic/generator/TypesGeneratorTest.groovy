package io.vrap.rmf.raml.generic.generator

import com.google.common.io.Files
import com.google.common.io.Resources
import io.vrap.rmf.raml.model.types.ObjectType
import io.vrap.rmf.raml.model.types.Property
import io.vrap.rmf.raml.model.types.StringType
import io.vrap.rmf.raml.model.types.TypesFactory
import org.stringtemplate.v4.STGroupFile
import spock.lang.Specification

class TypesGeneratorTest extends Specification {
    def "generate"() {
        when:
        TypesGenerator generator = new TypesGenerator("test", Files.createTempDir())

        ObjectType localizedStringType = TypesFactory.eINSTANCE.createObjectType();
        localizedStringType.name = 'LocalizedString'

        ObjectType objectType = TypesFactory.eINSTANCE.createObjectType()
        objectType.name = 'Person'

        Property property = TypesFactory.eINSTANCE.createProperty();
        property.name = 'name'
        property.type = localizedStringType

        objectType.properties.add(property)
        then:
        String result = generator.generate(objectType)
        result != null
    }
}
