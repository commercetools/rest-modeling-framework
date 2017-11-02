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
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import io.vrap.rmf.raml.model.util.StringTemplate;
import io.vrap.rmf.raml.model.util.UriFragmentBuilder;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import io.vrap.rmf.raml.persistence.constructor.TypeExpressionConstructor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.*;
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
     * @return a resolved api
     */
    public Api buildApi(final URI uri) {
        final RamlResourceSet resourceSet = new RamlResourceSet();
        final Resource resource = resourceSet.getResource(uri, true);
        final EObject rootObject = resource.getContents().get(0);
        final ApiResolver apiResolver = new ApiResolver();
        final Api resolvedApi;
        if (rootObject instanceof ApiExtension) {
            resolvedApi = apiResolver.caseApiExtension((ApiExtension) rootObject);
        } else {
            resolvedApi = apiResolver.caseApi((Api) rootObject);
        }
        return resolvedApi;
    }

    private static class ApiResolver extends ModulesSwitch<Api> {
        private final UriFragmentBuilder uriFragmentBuilder = new UriFragmentBuilder();

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

            resolvedApi.eAllContents().forEachRemaining(eObject -> {
                resourceResolver.doSwitch(eObject);
                bodyContentTypeResolver.doSwitch(eObject);
            });

            return resolvedApi;
        }

        @Override
        public Api caseApiExtension(final ApiExtension apiExtension) {
            final Api resolvedApi = caseApi(apiExtension.getExtends());
            merge(apiExtension, resolvedApi);
            return resolvedApi;
        }

        private void merge(final ApiExtension apiExtension, final Api api) {
            final TreeIterator<EObject> allProperContents = EcoreUtil.getAllProperContents(api, false);
            final Map<String, EObject> idToEObject = new HashMap<>();
            while (allProperContents.hasNext()) {
                final EObject eObject = allProperContents.next();
                final String uriFragment = uriFragmentBuilder.getURIFragment(eObject);
                idToEObject.put(uriFragment, eObject);
            }
            merge(apiExtension, api, idToEObject);
        }

        private void merge(final EObject extension, final EObject extendsEObject, final Map<String, EObject> idToEObject) {
            mergeAttributes(extension, extendsEObject);
            mergeCrossReferences(extension, extendsEObject, idToEObject);
            for (final EObject extensionChild : extension.eContents()) {
                final String uriFragment = uriFragmentBuilder.getURIFragment(extensionChild);
                if (idToEObject.containsKey(uriFragment)) {
                    final EObject extendsChild = idToEObject.get(uriFragment);
                    merge(extensionChild, extendsChild, idToEObject);
                } else {
                    final EObject copy = copy(extensionChild);
                    final EReference containmentFeature = extensionChild.eContainmentFeature();
                    if (containmentFeature.isMany()) {
                        final List<EObject> containment = (List<EObject>) extendsEObject.eGet(containmentFeature);
                        containment.add(copy);
                    } else {
                        extendsEObject.eSet(containmentFeature, copy);
                    }
                }
            }
        }

        private void mergeAttributes(final EObject extension, final EObject extendsEObject) {
            final List<EAttribute> commonAttributes = new ArrayList<>();
            commonAttributes.addAll(extension.eClass().getEAllAttributes());
            commonAttributes.retainAll(extendsEObject.eClass().getEAllAttributes());

            final List<EAttribute> nonDerivedAttributes = commonAttributes.stream()
                    .filter(a -> !a.isDerived())
                    .collect(Collectors.toList());
            for (final EAttribute attribute : nonDerivedAttributes) {
                if (extension.eIsSet(attribute)) {
                    final Object attributeValue = extension.eGet(attribute);
                    if (attribute.isMany()) {
                        final List<Object> values = (List<Object>) extendsEObject.eGet(attribute);
                        final List<Object> mergeAttributeValues = (List<Object>) attributeValue;
                        for (final Object mergeAttributeValue : mergeAttributeValues) {
                            if (!values.contains(mergeAttributeValue)) {
                                values.add(mergeAttributeValue);
                            }
                        }
                    } else {
                        extendsEObject.eSet(attribute, attributeValue);
                    }
                }
            }
        }

        private void mergeCrossReferences(final EObject extension, final EObject extendsEObject, final Map<String, EObject> idToEObject) {
            final List<EReference> allNonContainmentReferences = extendsEObject.eClass().getEAllReferences()
                    .stream().filter(reference -> !reference.isContainment())
                    .collect(Collectors.toList());
            for (final EReference reference : allNonContainmentReferences) {
                if (extension.eIsSet(reference)) {
                    if (reference.isMany()) {

                    } else {
                        final EObject referee = (EObject) extension.eGet(reference);
                        if (referee.eResource().getURI().equals(BuiltinType.RESOURCE_URI)) {
                            extendsEObject.eSet(reference, referee);
                        } else {
                            final String uriFragment = uriFragmentBuilder.getURIFragment(referee);
                            final EObject extendsReferee = idToEObject.get(uriFragment);
                            extendsEObject.eSet(reference, extendsReferee);
                        }
                    }
                }
            }
        }

        /**
         * Copies the given object and shallow copies all adapters.
         */
        private static <T extends EObject> T copy(T eObject) {
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

            @SuppressWarnings("unchecked") T t = (T) result;
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

    private static class ResourceTypeResolver {
        private final io.vrap.rmf.raml.model.resources.Resource resource;
        private final Map<String, String> parameters;
        private final TypedElementResolver typedElementResolver;
        private final StringTemplateResolver stringTemplateResolver;

        public ResourceTypeResolver(final io.vrap.rmf.raml.model.resources.Resource resource, final List<Parameter> parameters) {
            this.resource = resource;
            this.parameters = parameters.stream()
                    .filter(p -> p.getValue() instanceof StringInstance)
                    .collect(Collectors.toMap(Parameter::getName, p -> ((StringInstance) p.getValue()).getValue()));
            this.parameters.put("resourcePath", resource.getResourcePath());
            this.parameters.put("resourcePathName", resource.getResourcePathName());
            typedElementResolver = new TypedElementResolver(resource.eResource(), this.parameters);
            stringTemplateResolver = new StringTemplateResolver(this.parameters);
        }

        private List<io.vrap.rmf.raml.model.resources.Resource> getResourcesToRoot() {
            final LinkedList<io.vrap.rmf.raml.model.resources.Resource> resourcesToRoot = new LinkedList<>();

            io.vrap.rmf.raml.model.resources.Resource currentResoure = resource;
            while (currentResoure != null) {
                resourcesToRoot.addFirst(currentResoure);
                currentResoure = currentResoure.eContainer() instanceof io.vrap.rmf.raml.model.resources.Resource ?
                        (io.vrap.rmf.raml.model.resources.Resource) currentResoure.eContainer() :
                        null;
            }
            return resourcesToRoot;
        }

        public io.vrap.rmf.raml.model.resources.Resource resolve(final ResourceType resourceType) {
            for (final UriParameter uriParameter : resourceType.getUriParameters()) {
                final UriParameter resolvedUriParameter = EcoreUtil.copy(uriParameter);
                typedElementResolver.resolve(resolvedUriParameter);
                resource.getUriParameters().add(resolvedUriParameter);
            }
            for (final Method method : resourceType.getMethods()) {
                final Method resolvedMethod = EcoreUtil.copy(method);
                for (final TraitApplication traitApplication : method.getIs()) {
                    new TraitResolver(resolvedMethod, traitApplication.getParameters()).resolve(traitApplication.getTrait());
                }
                for (final TraitApplication traitApplication : resourceType.getIs()) {
                    new TraitResolver(resolvedMethod, traitApplication.getParameters()).resolve(traitApplication.getTrait());
                }

                mergeMethod(resolvedMethod);
            }
            final TreeIterator<EObject> allProperContents = EcoreUtil.getAllProperContents(resource, false);
            allProperContents.forEachRemaining(stringTemplateResolver::resolve);
            return resource;
        }


        private void mergeMethod(final Method resolvedMethod) {
            final Method existingMethod = resource.getMethod(resolvedMethod.getMethod());
            if (existingMethod == null) {
                if (resolvedMethod.isRequired()) {
                    resource.getMethods().add(resolvedMethod);
                    typedElementResolver.resolveAll(resolvedMethod);
                }
            } else {
                typedElementResolver.resolveAll(resolvedMethod);
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
            this.parameters.put("methodName", method.getMethod().getLiteral());
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

    private static class StringTemplateResolver {
        private final Map<String, String> parameters;

        public StringTemplateResolver(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        public EObject resolve(final EObject eObject) {
            final List<EAttribute> stringAttributes = eObject.eClass().getEAllAttributes().stream()
                    .filter(attribute -> attribute.getEType() == EcorePackage.Literals.ESTRING)
                    .collect(Collectors.toList());
            for (final EAttribute attribute : stringAttributes) {
                if (eObject.eIsSet(attribute)) {
                    if (attribute.isMany()) {

                    } else {
                        final String template = (String) eObject.eGet(attribute);
                        final StringTemplate stringTemplate = StringTemplate.of(template);
                        if (stringTemplate.getParameters().size() > 0) {
                            final String render = stringTemplate.render(parameters);
                            eObject.eSet(attribute, render);
                        }
                    }
                }
            }
            return eObject;
        }
    }

    private static class TypedElementResolver extends TypesSwitch<AnyType> {
        private final Resource resource;
        private final Map<String, String> parameters;
        private final TypeExpressionConstructor typeExpressionConstructor;
        private Scope scope;

        public TypedElementResolver(final Resource resource, final Map<String, String> parameters) {
            this.parameters = parameters;
            this.resource = resource;
            this.typeExpressionConstructor = new TypeExpressionConstructor();
        }

        public void resolveAll(final EObject eObject) {
            final TreeIterator<EObject> allContents = EcoreUtil.getAllContents(eObject, true);
            while (allContents.hasNext()) {
                final EObject next = allContents.next();
                if (next instanceof TypedElement) {
                    scope = Scope.of(resource).with(next, next.eContainmentFeature());
                    resolve((TypedElement) next);
                }
            }
        }

        private TypedElement resolve(final TypedElement typedElement) {
            final AnyType resolvedType = doSwitch(typedElement.getType());
            typedElement.setType(resolvedType);

            return typedElement;
        }

        @Override
        public AnyType caseAnyType(final AnyType anyType) {
            return anyType;
        }

        @Override
        public AnyType caseTypeTemplate(final TypeTemplate typeTemplate) {
            final String template = typeTemplate.getName();
            final String typeName = StringTemplate.of(template).render(parameters);
            final AnyType resolvedType = (AnyType) typeExpressionConstructor.parse(typeName, scope);
            EcoreUtil.remove(typeTemplate);
            return resolvedType;
        }

        @Override
        public AnyType caseArrayType(final ArrayType arrayType) {
            final AnyType resolvedItems = doSwitch(arrayType.getItems());
            arrayType.setItems(resolvedItems);
            return arrayType;
        }

        @Override
        public AnyType caseUnionType(final UnionType unionType) {
            final List<AnyType> resolvedOneOf = unionType.getOneOf().stream()
                    .map(this::doSwitch)
                    .collect(Collectors.toList());
            unionType.getOneOf().clear();
            unionType.getOneOf().addAll(resolvedOneOf);
            return unionType;
        }
    }

    /**
     * This class adds the default media types to any body type that has no
     * content type defined.
     */
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
}
