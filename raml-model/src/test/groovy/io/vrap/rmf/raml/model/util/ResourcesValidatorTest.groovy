package io.vrap.rmf.raml.model.util

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.modules.ModulesFactory
import io.vrap.rmf.raml.model.modules.util.ModulesValidator
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.resources.Resource
import io.vrap.rmf.raml.model.resources.ResourceType
import io.vrap.rmf.raml.model.resources.ResourcesFactory
import org.eclipse.emf.common.util.BasicDiagnostic
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.ecore.util.Diagnostician
import spock.lang.Specification

/**
 * Unit tests for {@link io.vrap.rmf.raml.model.resources.util.ResourcesValidator}
 */
class ResourcesValidatorTest extends Specification {

    def "should report optional methods defined in a resource"() {
        when:
        Resource resource = ResourcesFactory.eINSTANCE.createResource()
        Method optionalMethod = ResourcesFactory.eINSTANCE.createMethod()
        optionalMethod.setRequired(false)
        resource.methods.add(optionalMethod)
        BasicDiagnostic diagnostic = new BasicDiagnostic()
        then:
        Diagnostician.INSTANCE.validate(resource, diagnostic, new HashMap<Object, Object>()) == false
        diagnostic.severity != Diagnostic.OK
    }

    def "should accept optional methods defined in a resource type"() {
        when:
        ResourceType resourceType = ResourcesFactory.eINSTANCE.createResourceType()
        Method optionalMethod = ResourcesFactory.eINSTANCE.createMethod()
        optionalMethod.setRequired(false)
        resourceType.methods.add(optionalMethod)
        BasicDiagnostic diagnostic = new BasicDiagnostic()
        then:
        Diagnostician.INSTANCE.validate(resourceType, diagnostic, new HashMap<Object, Object>()) == true
        diagnostic.severity == Diagnostic.OK
    }
}
