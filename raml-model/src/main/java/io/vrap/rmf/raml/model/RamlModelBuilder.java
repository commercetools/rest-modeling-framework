package io.vrap.rmf.raml.model;

import io.vrap.rmf.nodes.*;
import io.vrap.rmf.nodes.antlr.NodeToken;
import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
import io.vrap.rmf.nodes.util.NodesSwitch;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.ApiBase;
import io.vrap.rmf.raml.model.modules.ApiExtension;
import io.vrap.rmf.raml.model.modules.util.ModulesSwitch;
import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.model.resources.util.ResourcesSwitch;
import io.vrap.rmf.raml.model.responses.Body;
import io.vrap.rmf.raml.model.responses.util.ResponsesSwitch;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.model.types.StringInstance;
import io.vrap.rmf.raml.model.util.UriFragmentBuilder;
import io.vrap.rmf.raml.model.values.StringTemplate;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.antlr.RamlNodeTokenSource;
import io.vrap.rmf.raml.persistence.constructor.ApiConstructor;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.vrap.rmf.nodes.NodeElementCopier.copy;

/**
 * This class is the main interface for accessing RAML models.
 */
public class RamlModelBuilder {

    /**
     * Builds a resolved api from the RAML file given by the uri.
     *
     * @param uri the uri to build the api from
     * @return a resolved api model result
     */
    public RamlModelResult<Api> buildApi(final URI uri) {
        return build(uri);
    }

    /**
     * Builds a root object from the RAML file given by the uri.
     *
     * @param uri the uri to build the api from
     * @return a model result
     */
    public <T extends EObject> RamlModelResult<T> build(final URI uri) {
        final Resource resource = load(uri);
        final EObject rootObject = resource.getContents().isEmpty() ?
                null :
                resource.getContents().get(0);
        final EObject resolved = rootObject instanceof ApiBase ?
                resolveToApi(rootObject) :
                rootObject;
        return RamlModelResult.of(resource.getErrors(), resolved);
    }

    private Resource load(final URI uri) {
        final RamlResourceSet resourceSet = new RamlResourceSet();
        final Resource resource = resourceSet.getResource(uri, true);
        return resource;
    }

    private Api resolveToApi(final EObject rootObject) {
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

            resolvedApi.getResources().forEach(resourceResolver::doSwitch);

            resolvedApi.eAllContents().forEachRemaining(bodyContentTypeResolver::doSwitch);

            return resolvedApi;
        }

        @Override
        public Api caseApiExtension(final ApiExtension apiExtension) {
            final ApiBase extend = apiExtension.getExtends();
            final Api resolvedApi;
            if (extend instanceof ApiExtension) {
                resolvedApi = caseApiExtension((ApiExtension)extend);
            } else {
                resolvedApi = caseApi((Api)extend);
            }
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
                    .stream().filter(reference -> !reference.isContainment() && !reference.isDerived())
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

            io.vrap.rmf.raml.model.resources.Resource mergedResource = resource;
            if (resourceTypeApplication != null) {
                final ResourceMerger resourceMerger = new ResourceMerger(resource, resourceTypeApplication.getParameters());
                mergedResource = resourceMerger.resolve(resourceTypeApplication.getType());
            }
            mergedResource.getResources().forEach(this::doSwitch);

            return resource;
        }
    }

    private static class ResourceMerger {
        private final NodeMerger nodeMerger = new NodeMerger(true);
        private final io.vrap.rmf.raml.model.resources.Resource resource;
        private final StringTemplateResolver stringTemplateResolver;
        private final NodeTokenProvider resourceNodeTokenProvider;

        public ResourceMerger(final io.vrap.rmf.raml.model.resources.Resource resource, final List<Parameter> parameters) {
            this.resource = resource;
            stringTemplateResolver = new StringTemplateResolver(parameters);
            stringTemplateResolver.put("resourcePath", resource.getResourcePath());
            stringTemplateResolver.put("resourcePathName", resource.getResourcePathName());
            resourceNodeTokenProvider =
                    (NodeTokenProvider) EcoreUtil.getExistingAdapter(resource, NodeTokenProvider.class);
        }

        public io.vrap.rmf.raml.model.resources.Resource resolve(final ResourceType resourceType) {
            final Property resourceProperty = resourceNodeTokenProvider.getPropertyContainer();

            final Node resourceValueNode = resourceProperty.getValue();
            final Node resourceTypeNode = getMergedResourceTypeNode(resourceType);
            final Node mergedNode = nodeMerger.merge(resourceTypeNode, resourceValueNode);
            resourceProperty.setValue(mergedNode);

            final TreeIterator<EObject> allProperContents = EcoreUtil.getAllProperContents(mergedNode, false);
            allProperContents.forEachRemaining(stringTemplateResolver::resolve);

            final URI uri = resource.eResource() == null ? null : resource.eResource().getURI();
            final RamlNodeTokenSource lexer = new RamlNodeTokenSource(uri, resourceProperty);
            final TokenStream tokenStream = new CommonTokenStream(lexer);
            final RAMLParser parser = new RAMLParser(tokenStream);
            final RAMLParser.ResourceFacetContext resourceFacetContext = parser.resourceFacet();
            final ApiConstructor apiConstructor = new ApiConstructor();
            final io.vrap.rmf.raml.model.resources.Resource mergedResource =
                    apiConstructor.withinScope(Scope.of(resource.eResource()).with(resource.eContainer()),
                        scope -> (io.vrap.rmf.raml.model.resources.Resource) apiConstructor.visitResourceFacet(resourceFacetContext));
            mergedResource.getResources().clear();
            mergedResource.getResources().addAll(resource.getResources());
            EcoreUtil.remove(resource);
            return mergedResource;
        }

        private Node getMergedResourceTypeNode(final ResourceType resourceType) {
            final ObjectNode mergedResourceTypeNode = NodesFactory.eINSTANCE.createObjectNode();
            for (final Method method : resourceType.getMethods()) {
                final NodeTokenProvider methodNodeTokenProvider  =
                        (NodeTokenProvider) EcoreUtil.getExistingAdapter(method, NodeTokenProvider.class);

                final Property propertyContainer = methodNodeTokenProvider.getPropertyContainer();
                final Property mergedMethodProperty = copy(propertyContainer);
                final Node methodNode = propertyContainer.getValue();

                Node mergedMethodNode = methodNode;
                for (final TraitApplication traitApplication : resourceType.getIs()) {
                    mergedMethodNode = apply(traitApplication, mergedMethodNode);
                    new StringTemplateResolver(traitApplication.getParameters()).resolve(mergedMethodNode);
                }
                for (final TraitApplication traitApplication : method.getIs()) {
                    mergedMethodNode = apply(traitApplication, mergedMethodNode);
                    new StringTemplateResolver(traitApplication.getParameters()).resolve(mergedMethodNode);
                }
                mergedMethodProperty.setValue(mergedMethodNode);
                mergedResourceTypeNode.getProperties().add(mergedMethodProperty);
            }
            final NodeTokenProvider nodeTokenProvider =
                    (NodeTokenProvider) EcoreUtil.getExistingAdapter(resourceType, NodeTokenProvider.class);
            final Node value = nodeTokenProvider.getPropertyContainer().getValue();
            final Node result = nodeMerger.merge(value, mergedResourceTypeNode);
            return result;
        }

        private Node apply(final TraitApplication traitApplication, Node mergedMethodNode) {
            final Trait trait = traitApplication.getTrait();
            final NodeTokenProvider traitNodeTokenProvider =
                    (NodeTokenProvider) EcoreUtil.getExistingAdapter(trait, NodeTokenProvider.class);
            final Node traitNode = traitNodeTokenProvider.getPropertyContainer().getValue();
            mergedMethodNode = nodeMerger.merge(traitNode, mergedMethodNode);
            return mergedMethodNode;
        }
    }

    /**
     * This class replaces string templates inside of a {@link StringNode#getValue()}.
     */
    private static class StringTemplateResolver extends NodesSwitch<EObject> {
        private final Map<String, String> parameters;

        /**
         *
         * @param parameters the parameters to replace in the value of a {@link StringNode}
         */
        public StringTemplateResolver(final List<Parameter> parameters) {
            this.parameters = parameters.stream()
                    .filter(p -> p.getValue() instanceof StringInstance)
                    .collect(Collectors.toMap(Parameter::getName, p -> ((StringInstance) p.getValue()).getValue()));
        }

        public void put(final String key, final String value) {
            parameters.put(key, value);
        }

        @Override
        public EObject caseStringNode(final StringNode stringNode) {
            final StringTemplate stringTemplate = StringTemplate.of(stringNode.getValue());
            if (stringTemplate.getParameters().size() > 0) {
                final String render = stringTemplate.render(parameters);
                stringNode.setValue(render);
                final NodeTokenProvider existingAdapter =
                        (NodeTokenProvider) EcoreUtil.getExistingAdapter(stringNode, NodeTokenProvider.class);
                final NodeToken start = existingAdapter.getStart();
                final NodeToken nodeToken = start.withType(start.getType(), render);
                stringNode.eAdapters().remove(existingAdapter);
                stringNode.eAdapters().add(new NodeModelBuilder.NodeParserAdapter(nodeToken, nodeToken));
            }
            return stringNode;
        }

        public EObject resolve(final EObject eObject) {
            TreeIterator<EObject> allProperContents = EcoreUtil.getAllProperContents(eObject, false);
            allProperContents.forEachRemaining(this::doSwitch);
            return eObject;
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
        public EObject caseBody(final Body body) {
            if (body.getContentTypes().isEmpty()) {
                body.getContentTypes().addAll(defaultMediaTypes);
            }
            return body;
        }
    }
}
