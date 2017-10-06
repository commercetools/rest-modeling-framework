package io.vrap.rmf.raml.model;

import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.ApiExtension;
import io.vrap.rmf.raml.model.modules.util.ModulesSwitch;
import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.model.resources.util.ResourcesSwitch;
import io.vrap.rmf.raml.model.responses.BodyType;
import io.vrap.rmf.raml.model.responses.util.ResponsesSwitch;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.util.StringTemplate;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
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
        final EObject rootObject = resource.getContents().get(0);
        final Api apiCopy = new ApiResolver().doSwitch(rootObject);
        return apiCopy;
    }

    private static class ApiResolver extends ModulesSwitch<Api> {

        @Override
        public Api caseApi(final Api api) {
            final Resource resource = api.eResource();
            final ResourceSet resourceSet = resource.getResourceSet();
            final Api resolvedApi = copy(api);
            final URI resolvedApiUri = resource.getURI().appendQuery("resolved=true");
            final Resource resolvedResource = resourceSet.createResource(resolvedApiUri);

            resolvedResource.getContents().add(resolvedApi);
            resolvedResource.getErrors().addAll(resource.getErrors());

            final ResourceResolver resourceResolver = new ResourceResolver();
            final BodyContentTypeResolver bodyContentTypeResolver = new BodyContentTypeResolver(api.getMediaType());

            resolvedApi.eAllContents().forEachRemaining(eObject -> { resourceResolver.doSwitch(eObject); bodyContentTypeResolver.doSwitch(eObject); });

            return resolvedApi;
        }

        @Override
        public Api caseApiExtension(final ApiExtension apiExtension) {
            final Api resolvedApi = caseApi(apiExtension.getExtends());
            return resolvedApi;
        }

        /**
         * Copies the given object and shallow copies all adapters.
         */
        private static <T extends EObject> T copy(T eObject)
        {
            EcoreUtil.Copier copier = new EcoreUtil.Copier() {
                @Override
                public EObject copy(EObject eObject) {
                    final EObject copy = super.copy(eObject);
                    eObject.eAdapters().forEach(adapter -> copy.eAdapters().add(adapter));
                    return copy;
                }
            };
            EObject result = copier.copy(eObject);
            copier.copyReferences();

            @SuppressWarnings("unchecked")T t = (T)result;
            return t;
        }
    }

    private static class ResourceResolver extends ResourcesSwitch<EObject> {

        @Override
        public EObject caseResource(final io.vrap.rmf.raml.model.resources.Resource resource) {
            final ResourceTypeApplication resourceTypeApplication = resource.getType();
            if (resourceTypeApplication != null) {
                final ResourceTypeResolver resourceTypeResolver = new ResourceTypeResolver(resource, resourceTypeApplication.getParameters());
                resourceTypeResolver.resolve(resourceTypeApplication.getType());
            }
            return resource;
        }
    }

    private static class BodyContentTypeResolver extends ResponsesSwitch<EObject> {
        private final List<String> defaultMediaTypes;

        public BodyContentTypeResolver(final List<String> defaultMediaTypes) {
            this.defaultMediaTypes = defaultMediaTypes;
        }

        @Override
        public EObject caseBodyType(final BodyType body) {
            if (body.getContentTypes().isEmpty()) {
                body.getContentTypes().addAll(defaultMediaTypes);
            }
            return body;
        }
    }

    private static class ResourceTypeResolver  {
        private final io.vrap.rmf.raml.model.resources.Resource resource;
        private final Map<String, String> parameters;
        private final TypedElementResolver typedElementResolver;

        public ResourceTypeResolver(final io.vrap.rmf.raml.model.resources.Resource resource, final List<Parameter> parameters) {
            this.resource = resource;
            this.parameters = parameters.stream()
                    .filter(p -> p.getValue() instanceof StringInstance)
                    .collect(Collectors.toMap(Parameter::getName, p -> ((StringInstance) p.getValue()).getValue()));
            typedElementResolver = new TypedElementResolver(resource.eResource(), this.parameters);
        }

        public io.vrap.rmf.raml.model.resources.Resource resolve(final ResourceType resourceType) {
            for (final UriParameter uriParameter : resourceType.getUriParameters()) {
                final UriParameter resolvedUriParameter = EcoreUtil.copy(uriParameter);
                typedElementResolver.resolve(resolvedUriParameter);
                resource.getUriParameters().add(resolvedUriParameter);
            }
            for (final Method method : resourceType.getMethods()) {
                final Method resolvedMethod = EcoreUtil.copy(method);
                typedElementResolver.resolveAll(resolvedMethod);
                for (final TraitApplication traitApplication : method.getIs()) {
                    new TraitResolver(resolvedMethod, traitApplication.getParameters()).resolve(traitApplication.getTrait());
                }
                for (final TraitApplication traitApplication : resourceType.getIs()) {
                    new TraitResolver(resolvedMethod, traitApplication.getParameters()).resolve(traitApplication.getTrait());
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

    private static class TraitResolver extends ResourcesSwitch<Method> {
        private final Method method;
        private final Map<String, String> parameters;
        private final TypedElementResolver typedElementResolver;

        public TraitResolver(final Method method, final List<Parameter> parameters) {
            this.method = method;
            this.parameters = parameters.stream()
                    .filter(p -> p.getValue() instanceof StringInstance)
                    .collect(Collectors.toMap(Parameter::getName, p -> ((StringInstance) p.getValue()).getValue()));
            typedElementResolver = new TypedElementResolver(method.eResource(), this.parameters);
        }

        public Method resolve(final Trait trait) {
            for (final Header header : trait.getHeaders()) {
                final Header resolvedHeader = EcoreUtil.copy(header);
                typedElementResolver.resolveAll(resolvedHeader);
                method.getHeaders().add(resolvedHeader);
            }
            for (final QueryParameter queryParameter : trait.getQueryParameters()) {
                final QueryParameter resolvedQueryParameter = EcoreUtil.copy(queryParameter);
                typedElementResolver.resolveAll(resolvedQueryParameter);
                method.getQueryParameters().add(resolvedQueryParameter);
            }

            return method;
        }
    }

    private static class TypedElementResolver {
        private final Resource resource;
        private final Map<String, String> parameters;

        public TypedElementResolver(final Resource resource, final Map<String, String> parameters) {
            this.parameters = parameters;
            this.resource = resource;
        }

        public void resolveAll(final EObject eObject) {
            final TreeIterator<EObject> allContents = EcoreUtil.getAllContents(eObject, true);
            while (allContents.hasNext()) {
                final EObject next = allContents.next();
                if (next instanceof TypedElement) {
                    resolve((TypedElement) next);
                }
            }
        }

        public TypedElement resolve(final TypedElement typedElement) {
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
