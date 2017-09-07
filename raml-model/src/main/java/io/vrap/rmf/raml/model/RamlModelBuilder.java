package io.vrap.rmf.raml.model;

import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.resources.*;
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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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
            typeTemplateResolver = new TypeTemplateResolver(resource.eResource(), this.parameters);
        }

        @Override
        public io.vrap.rmf.raml.model.resources.Resource caseResourceType(final ResourceType resourceType) {
            for (final Method method : resourceType.getMethods()) {
                final Method resolvedMethod = EcoreUtil.copy(method);
                final TreeIterator<EObject> allContents = EcoreUtil.getAllContents(resolvedMethod, true);
                while (allContents.hasNext()) {
                    final EObject next = allContents.next();
                    if (next instanceof TypedElement) {
                        typeTemplateResolver.caseTypedElement((TypedElement) next);
                    }
                }
                mergeMethod(resolvedMethod);
            }
            return resource;
        }

        private void mergeMethod(final Method resolvedMethod) {
            final Method existingMethod = resource.getMethod(resolvedMethod.getMethod());
            if (existingMethod == null) {
                if (resolvedMethod.isRequired()) {
                    resource.getMethods().add(resolvedMethod);
                }
            } else {
                final EList<EAttribute> allAttributes = ResourcesPackage.Literals.METHOD.getEAllAttributes();
                final Consumer<EAttribute> copyAttribute = attribute -> existingMethod.eSet(attribute, resolvedMethod.eGet(attribute));
                allAttributes.stream()
                        .filter(attribute -> !existingMethod.eIsSet(attribute))
                        .filter(attribute -> resolvedMethod.eIsSet(attribute))
                        .forEach(copyAttribute);
                final Consumer<EReference> copyReference = eReference -> {
                    final Object value;
                    if (eReference.isContainment()) {
                        if (eReference.isMany()) {
                            value = EcoreUtil.copyAll((List) resolvedMethod.eGet(eReference));
                        } else {
                            value = EcoreUtil.copy((EObject) resolvedMethod.eGet(eReference));
                        }
                    } else {
                        value = resolvedMethod.eGet(eReference);
                    }
                    existingMethod.eSet(eReference, value);
                };
                ResourcesPackage.Literals.METHOD.getEAllReferences().stream()
                        .filter(reference -> !existingMethod.eIsSet(reference))
                        .filter(reference -> resolvedMethod.eIsSet(reference))
                        .forEach(copyReference);
            }
        }
    }

    private static class TypeTemplateResolver extends TypesSwitch<TypedElement> {
        private final Resource resource;
        private final Map<String, String> parameters;

        public TypeTemplateResolver(final Resource resource, final Map<String, String> parameters) {
            this.parameters = parameters;
            this.resource = resource;
        }

        @Override
        public TypedElement caseTypedElement(final TypedElement typedElement) {
            final AnyType type = typedElement.getType();
            if (type instanceof TypeTemplate) {
                final String template = type.getName();
                final String typeName = StringTemplate.of(template).render(parameters);
                final String uriFragment = "/types/" + typeName;
                final AnyType resolvedType = (AnyType) resource.getEObject(uriFragment);
                typedElement.setType(resolvedType);
            }
            return typedElement;
        }
    }
}
