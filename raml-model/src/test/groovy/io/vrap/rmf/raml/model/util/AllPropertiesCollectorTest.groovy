package io.vrap.rmf.raml.model.util

import io.vrap.rmf.raml.model.types.BuiltinType
import io.vrap.rmf.raml.model.types.IntegerType
import io.vrap.rmf.raml.model.types.ObjectType
import io.vrap.rmf.raml.model.types.Property
import io.vrap.rmf.raml.model.types.TypesFactory
import io.vrap.rmf.raml.persistence.RamlResourceSet
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.resource.ResourceSet
import spock.lang.Shared
import spock.lang.Specification

/**
 * Unit tests for {@link AllPropertiesCollector}.
 */
class AllPropertiesCollectorTest extends Specification {
    @Shared
    ResourceSet resourceSet = new RamlResourceSet()


    def "return most specific property"() {
        when:
        ObjectType parent = objectTypeWithProperty("age", BuiltinType.NUMBER)
        ObjectType child = objectTypeWithProperty("age", BuiltinType.INTEGER)
        child.setType(parent)

        then:
        EList<Property> allProperties = AllPropertiesCollector.getAllProperties(child);
        allProperties.size() == 1
        allProperties[0].name == 'age'
        allProperties[0].type instanceof IntegerType
    }

    ObjectType objectTypeWithProperty(String propertyName, BuiltinType propertyType) {
        Property property = TypesFactory.eINSTANCE.createProperty()
        property.name = propertyName
        property.type = propertyType.getEObject(resourceSet)

        ObjectType objectType = TypesFactory.eINSTANCE.createObjectType()
        objectType.properties.add(property)

        return objectType;
    }
}
