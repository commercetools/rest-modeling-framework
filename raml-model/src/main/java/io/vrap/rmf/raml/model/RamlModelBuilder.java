package io.vrap.rmf.raml.model;

import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Parameter;
import io.vrap.rmf.raml.model.resources.ResourceType;
import io.vrap.rmf.raml.model.resources.ResourceTypeApplication;
import io.vrap.rmf.raml.model.resources.util.ResourcesSwitch;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.TypeTemplate;
import io.vrap.rmf.raml.model.types.TypedElement;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import io.vrap.rmf.raml.model.util.StringTemplate;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is the main interface for accessing RAML models.
 */
public class RamlModelBuilder {

    /**
     * Builds a resolved api from the RAML file given by the uri.
     *
     * @param uri the uri to build the api from
     *
     * @return a resolved api
     */
    public Api buildApi(final URI uri) {
        final RamlResourceSet resourceSet = new RamlResourceSet();
        final Resource resource = resourceSet.getResource(uri, true);
        final Api api = (Api) resource.getContents().get(0);
        final Api apiCopy = EcoreUtil.copy(api);
        final Resource resolvedResource = resourceSet.createResource(uri.appendQuery("resolved=true"));
        resolvedResource.getContents().add(apiCopy);
        final ResourceResolver resourceResolver = new ResourceResolver();
        apiCopy.eAllContents().forEachRemaining(resourceResolver::doSwitch);
        return apiCopy;
    }

    private static class ResourceResolver extends ResourcesSwitch<EObject> {

        @Override
        public EObject caseResource(final io.vrap.rmf.raml.model.resources.Resource resource) {
            final ResourceTypeApplication resourceTypeApplication = resource.getType();
            if (resourceTypeApplication != null) {
                final ResourceTypeResolver resourceTypeResolver = new ResourceTypeResolver(resource, resourceTypeApplication.getParameters());
                resourceTypeResolver.doSwitch(resourceTypeApplication.getType());
            }
            return resource;
        }
    }

    private static class ResourceTypeResolver extends ResourcesSwitch<io.vrap.rmf.raml.model.resources.Resource> {
        private final io.vrap.rmf.raml.model.resources.Resource resource;
        private final Map<String, String> parameters;
        private final TypeTemplateResolver typeTemplateResolver;

        public ResourceTypeResolver(final io.vrap.rmf.raml.model.resources.Resource resource, final List<Parameter> parameters) {
            this.resource = resource;
            this.parameters = parameters.stream()
                    .filter(p -> p.getValue() instanceof StringInstance)
                    .collect(Collectors.toMap(Parameter::getName, p -> ((StringInstance) p.getValue()).getValue()));
            typeTemplateResolver = new TypeTemplateResolver(this.parameters);
        }

        @Override
        public io.vrap.rmf.raml.model.resources.Resource caseResourceType(final ResourceType resourceType) {
            final EList<Method> methods = resource.getMethods();
            for (final Method method : resourceType.getMethods()) {
                final Method methodToResolve = EcoreUtil.copy(method);
                methods.add(methodToResolve);
                final TreeIterator<EObject> allContents = EcoreUtil.getAllContents(methodToResolve, true);
                while (allContents.hasNext()) {
                    final EObject next = allContents.next();
                    if (next instanceof TypedElement) {
                        typeTemplateResolver.caseTypedElement((TypedElement) next);
                    }
                }
            }
            return resource;
        }
    }

    private static class TypeTemplateResolver extends TypesSwitch<TypedElement> {
        private final Map<String, String> parameters;

        public TypeTemplateResolver(final Map<String, String> parameters) {
            this.parameters = parameters;
        }

        @Override
        public TypedElement caseTypedElement(final TypedElement typedElement) {
            if (typedElement.getType() instanceof TypeTemplate) {
                final String template = typedElement.getType().getName();
                final String typeName = StringTemplate.of(template).render(parameters);
                final String uriFragment = "/types/" + typeName;
                final Resource resource = typedElement.eResource();
                final AnyType resolvedType = (AnyType) resource.getEObject(uriFragment);
                typedElement.setType(resolvedType);
            }
            return typedElement;
        }
    }
}
