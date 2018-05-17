package io.vrap.rmf.raml.model;

import com.google.common.collect.Lists;
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

import static io.vrap.rmf.nodes.NodeCopier.copy;

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

    private static class ResourceResolver extends ResourcesSwitch<io.vrap.rmf.raml.model.resources.Resource> {
        private final ResourceMerger resourceMerger = new ResourceMerger();

        @Override
        public io.vrap.rmf.raml.model.resources.Resource caseResource(final io.vrap.rmf.raml.model.resources.Resource resource) {
            final List<io.vrap.rmf.raml.model.resources.Resource> resources = Lists.newArrayList(resource.getResources());

            final io.vrap.rmf.raml.model.resources.Resource resolvedResource = resourceMerger.resolve(resource);
            for (final io.vrap.rmf.raml.model.resources.Resource child : resources) {
                final io.vrap.rmf.raml.model.resources.Resource childResource = doSwitch(child);
                resolvedResource.getResources().add(childResource);
            }
            return resolvedResource;
        }
    }

    private static class ResourceNodeMerge extends ResourcesSwitch<PropertyNode> {
        private final NodeMerger nodeMerger = new NodeMerger(true); // TODO really true?
        private final NodeMerger resourceTypeNodeMerger = new NodeMerger(false); // TODO really true?

        @Override
        public PropertyNode caseResource(final io.vrap.rmf.raml.model.resources.Resource resource) {
            final PropertyNode resourceProperty = copy(getPropertyContainer(resource));

            ObjectNode resourceValueNode = (ObjectNode) resourceProperty.getValue();
            resourceValueNode.getProperties().clear();

            for (final Method method : resource.getMethods()) {
                final PropertyNode methodNode = doSwitch(method);
                resourceValueNode.getProperties().add(methodNode);
            }
            if (resource.getType() != null) {
                final PropertyNode resourceTypeNode = doSwitch(resource.getType());
                final Node resourceTypeValueNode = resourceTypeNode.getValue();
                resourceValueNode = (ObjectNode) nodeMerger.merge(resourceTypeValueNode, resourceValueNode);
                resourceProperty.setValue(resourceValueNode);
            }
            return resourceProperty;
        }

        @Override
        public PropertyNode caseResourceTypeApplication(final ResourceTypeApplication resourceTypeApplication) {
            final PropertyNode resourceTypeNode = doSwitch(resourceTypeApplication.getType());

            final StringTemplateResolver stringTemplateResolver = getStringTemplateResolver(resourceTypeApplication);
            final TreeIterator<EObject> allProperContents = EcoreUtil.getAllProperContents(resourceTypeNode, false);
            allProperContents.forEachRemaining(stringTemplateResolver::resolve);

            return resourceTypeNode;
        }

        @Override
        public PropertyNode caseResourceType(final ResourceType resourceType) {
            final PropertyNode resourceTypeNode = copy(getPropertyContainer(resourceType));
            final ObjectNode resourceTypeValueNode = (ObjectNode) resourceTypeNode.getValue();

            for (final Method method : resourceType.getMethods()) {

                PropertyNode methodNode = doSwitch(method);
                Node methodValueNode = methodNode.getValue();
                for (final TraitApplication traitApplication : resourceType.getIs()) {
                    final PropertyNode traitApplicationNode = doSwitch(traitApplication);
                    final Node traitApplicationValueNode = traitApplicationNode.getValue();
                    if (traitApplicationValueNode != null) {
                        methodValueNode = resourceTypeNodeMerger.merge(traitApplicationValueNode, methodValueNode);
                        final StringTemplateResolver stringTemplateResolver = getStringTemplateResolver(traitApplication, method);
                        final TreeIterator<EObject> allProperContents = EcoreUtil.getAllProperContents(methodValueNode, false);
                        allProperContents.forEachRemaining(stringTemplateResolver::resolve);
                    }
                }
                methodNode.setValue(methodValueNode);
                final PropertyNode methodPropertyContainer = getPropertyContainer(method);
                final int index = methodPropertyContainer.eContainer().eContents().indexOf(methodPropertyContainer);
                resourceTypeValueNode.getProperties().set(index, methodNode);
            }
            return resourceTypeNode;
        }

        @Override
        public PropertyNode caseMethod(final Method method) {
            final PropertyNode methodNode = copy(getPropertyContainer(method));

            Node methodValueNode = methodNode.getValue();
            for (final TraitApplication traitApplication : method.getIs()) {
                final PropertyNode traitApplicationNode = doSwitch(traitApplication);
                final Node traitApplicationValueNode = traitApplicationNode.getValue();
                methodValueNode = nodeMerger.merge(traitApplicationValueNode, methodValueNode);

                final StringTemplateResolver stringTemplateResolver = getStringTemplateResolver(traitApplication, method);
                final TreeIterator<EObject> allProperContents = EcoreUtil.getAllProperContents(methodValueNode, false);
                allProperContents.forEachRemaining(stringTemplateResolver::resolve);
            }
            methodNode.setValue(methodValueNode);

            return methodNode;
        }

        @Override
        public PropertyNode caseTraitApplication(final TraitApplication traitApplication) {
            return doSwitch(traitApplication.getTrait());
        }

        @Override
        public PropertyNode caseTrait(final Trait trait) {
            final PropertyNode traitNode = copy(getPropertyContainer(trait));

            Node traitValueNode = traitNode.getValue();
            for (final TraitApplication traitApplication : trait.getIs()) {
                final Node traitApplicationNode = doSwitch(traitApplication);
                traitValueNode = nodeMerger.merge(traitApplicationNode, traitValueNode);

                final StringTemplateResolver stringTemplateResolver = getStringTemplateResolver(traitApplication);
                final TreeIterator<EObject> allProperContents = EcoreUtil.getAllProperContents(traitValueNode, false);
                allProperContents.forEachRemaining(stringTemplateResolver::resolve);
            }
            traitNode.setValue(traitValueNode);

            return traitNode;
        }

        private StringTemplateResolver getStringTemplateResolver(final ParameterizedApplication application, final io.vrap.rmf.raml.model.resources.Resource resource) {
            final Map<String, String> allParameters = application.getParameters().stream()
                    .filter(p -> p.getValue() instanceof StringInstance)
                    .collect(Collectors.toMap(Parameter::getName, p -> ((StringInstance) p.getValue()).getValue()));
            allParameters.put("resourcePath", resource.getResourcePath());
            allParameters.put("resourcePathName", resource.getResourcePathName());

            return new StringTemplateResolver(allParameters);
        }

        private StringTemplateResolver getStringTemplateResolver(final ParameterizedApplication application, final Method method) {
            final Map<String, String> allParameters = application.getParameters().stream()
                    .filter(p -> p.getValue() instanceof StringInstance)
                    .collect(Collectors.toMap(Parameter::getName, p -> ((StringInstance) p.getValue()).getValue()));
            allParameters.put("methodName", method.getMethodName());

            return new StringTemplateResolver(allParameters);
        }

        private StringTemplateResolver getStringTemplateResolver(final ParameterizedApplication application) {
            final Map<String, String> allParameters = application.getParameters().stream()
                    .filter(p -> p.getValue() instanceof StringInstance)
                    .collect(Collectors.toMap(Parameter::getName, p -> ((StringInstance) p.getValue()).getValue()));

            return new StringTemplateResolver(allParameters);
        }

        private PropertyNode getPropertyContainer(final EObject eObject) {
            final NodeTokenProvider nodeTokenProvider =
                    (NodeTokenProvider) EcoreUtil.getExistingAdapter(eObject, NodeTokenProvider.class);
            return nodeTokenProvider.getPropertyContainer();
        }
    }

    private static class ResourceMerger {
        private ResourceNodeMerge resourceNodeMerge = new ResourceNodeMerge();

        public io.vrap.rmf.raml.model.resources.Resource resolve(final io.vrap.rmf.raml.model.resources.Resource resource) {
            final PropertyNode resourceNode = resourceNodeMerge.doSwitch(resource);

            final URI uri = resource.eResource() == null ? null : resource.eResource().getURI();
            final RamlNodeTokenSource lexer = new RamlNodeTokenSource(uri, resourceNode);
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

    }

    /**
     * This class replaces string templates inside of a {@link StringNode#getValue()}.
     */
    private static class StringTemplateResolver extends NodesSwitch<EObject> {
        private final Map<String, String> parameters;

        public StringTemplateResolver(final Map<String, String> parameters) {
            this.parameters = parameters;
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
            if (body.getContentType() == null) {
                if (defaultMediaTypes.size() >= 1) {
                    final String firstMediaType = defaultMediaTypes.get(0);
                    body.setContentType(firstMediaType);

                    for (int i = 1; i < defaultMediaTypes.size(); i++) {
                        final Body copy = EcoreUtil.copy(body);
                        copy.setContentType(defaultMediaTypes.get(i));
                        body.eContainer().eContents().add(copy);
                    }
                }
            }
            return body;
        }
    }
}
