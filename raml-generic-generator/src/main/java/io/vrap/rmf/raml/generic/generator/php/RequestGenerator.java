package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.model.resources.util.ResourcesSwitch;
import io.vrap.rmf.raml.model.responses.BodyType;
import io.vrap.rmf.raml.model.responses.Response;
import io.vrap.rmf.raml.model.types.*;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class RequestGenerator extends AbstractTemplateGenerator {
    private static final String resourcesPath = "./templates/php/";
    static final String TYPE_REQUEST = "request";
    static final String TYPE_RESOURCE = "resource";
    static final String PACKAGE_NAME = "request";
    private final String vendorName;
    private final AnyAnnotationType packageAnnotationType;

    RequestGenerator(final String vendorName, final AnyAnnotationType packageAnnotationType)
    {
        this.vendorName = vendorName;
        this.packageAnnotationType = packageAnnotationType;
    }

    public List<File> generate(final List<Resource> resources, final File outputPath) throws IOException {

        final List<File> f = Lists.newArrayList();
        f.addAll(generateResources(outputPath, resources));
        f.addAll(generateRequests(outputPath, resources));

        return f;
    }

    private List<File> generateResources(final File outputPath, final List<Resource> resources) throws IOException {
        final List<Resource> flatResources = flattenResources(resources);

        final ResourceGeneratingVisitor resourceGeneratingVisitor = createVisitor(PACKAGE_NAME, TYPE_RESOURCE, flatResources);

        final List<File> f = Lists.newArrayList();
        final File requestFile = new File(outputPath, "RequestBuilder.php");
        f.add(generateFile(generateBuilder(resources), requestFile));
        for (final Resource resource : flatResources) {
            final Integer index = flatResources.indexOf(resource);
            final File resourceFile = new File(outputPath, "Resource" + index + ".php");

            f.add(generateFile(generateResource(resourceGeneratingVisitor, resource), resourceFile));
        }
        return f;
    }

    private List<File> generateRequests(final File outputPath, final List<Resource> resources) throws IOException {
        final List<Resource> flatResources = flattenResources(resources);

        final List<File> f = Lists.newArrayList();
        for (final Resource resource : flatResources) {
            for(final Method method : resource.getMethods()) {
                final UriTemplate uri = absoluteUri(resource);
                final String requestName = toRequestName(uri, method);
                final File resourceFile = new File(outputPath, requestName + ".php");
                f.add(generateFile(generateRequest(resource, method, requestName), resourceFile));
            }
        }
        return f;
    }

    private UriTemplate absoluteUri(final Resource resource)
    {
        final UriTemplate uri = ResourcesFactory.eINSTANCE.createUriTemplate();
        uri.getParts().addAll(absoluteUriParts(resource));
        return uri;
    }

    private List<UriTemplatePart> absoluteUriParts(final Resource resource)
    {
        if (!(resource.eContainer() instanceof Resource)) {
            return (List<UriTemplatePart>)EcoreUtil.copyAll(resource.getRelativeUri().getParts());
        }
        final List<UriTemplatePart> parts = absoluteUriParts((Resource)resource.eContainer());
        parts.addAll(EcoreUtil.copyAll(resource.getRelativeUri().getParts()));
        return parts;
    }

    private List<Resource> flattenResources(final List<Resource> resources)
    {
        final List<Resource> r = Lists.newArrayList();
        for (final Resource resource : resources) {
            r.add(resource);
            if (resource.getResources() != null) {
                r.addAll(flattenResources(resource.getResources()));
            }
        }
        return r;
    }

    String generateBuilder(final List<Resource> resources) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_RESOURCE + ".stg"));
        final ST st = stGroup.getInstanceOf("builder");
        st.add("vendorName", vendorName);
        st.add("package", PACKAGE_NAME);
        final List<Resource> nonParamResources = resources.stream()
                .filter(resource1 -> resource1.getRelativeUri().getParts().size() == 1).collect(Collectors.toList());
        st.add("resources", nonParamResources);
        st.add("resourcesIndex", nonParamResources.stream().map(resources::indexOf).collect(Collectors.toList()));
        final List<Resource> paramResources = resources.stream()
                .filter(resource1 -> resource1.getRelativeUri().getParts().size() > 1).collect(Collectors.toList());
        st.add("resourcesWithParams", paramResources);
        final List<Integer> collect = paramResources.stream().map(resources::indexOf).collect(Collectors.toList());
        st.add("resourcesWithParamsIndex", collect);
        return st.render();
    }

    String generateRequest(final Resource resource, final Method method, final String requestName) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_RESOURCE + ".stg"));
        final ST st = stGroup.getInstanceOf("request");
        st.add("vendorName", vendorName);
        st.add("package", PACKAGE_NAME);
        st.add("requestName", requestName);
        final UriTemplate uri = absoluteUri(resource);
        final Map<String, Object> params = uri.getParts().stream()
                .filter(uriTemplatePart -> uriTemplatePart instanceof UriTemplateExpression)
                .flatMap(uriTemplatePart -> ((UriTemplateExpression)uriTemplatePart).getVariables().stream())
                .collect(Collectors.toMap(o -> o, o -> "%s"));
        st.add("absoluteUri", uri);
        st.add("params", params.entrySet());
        st.add("method", method);
        final BodyType firstBodyType = method.getBodies().stream().findFirst().orElse(null);
        if (firstBodyType != null) {
            if (firstBodyType.getType() instanceof FileType) {
                st.add("fileBody", firstBodyType.getType());
            }
        }

        Response response = method.getResponses().stream().filter(response1 -> response1.getStatusCode().matches("^2[0-9]{2}$")).findFirst().orElse(null);
        if (response != null) {
            BodyType bodyType = response.getBodies().stream()
                    .filter(bodyType1 -> bodyType1.getContentTypes().size() == 0 || bodyType1.getContentTypes().contains("application/json"))
                    .findFirst().orElse(null);
            final AnyType returnType;
            if (bodyType != null && bodyType.getInlineTypes().isEmpty() && !BuiltinType.of(bodyType.getType().getName()).isPresent()) {
                returnType = bodyType.getType();
            } else if (bodyType != null && !bodyType.getInlineTypes().isEmpty() && !BuiltinType.of(bodyType.getType().getName()).isPresent()) {
                returnType = bodyType.getType().getType();
            } else {
                returnType = null;
            }
            if (returnType instanceof ObjectType) {
                st.add("returnType", returnType);
                st.add("returnPackage", getPackageFolder(returnType, "\\"));
            }
        }
        return st.render();
    }

    private String getPackageFolder(AnyType anyType, final String glue) {
        return anyType.getAnnotations().stream().filter(annotation -> annotation.getType().equals(packageAnnotationType))
                .map(annotation -> ((StringInstance)annotation.getValue()).getValue() + glue).findFirst().orElse("");
    }

    String generateResource(final ResourceGeneratingVisitor requestGeneratingVisitor, final Resource resource) {
        return requestGeneratingVisitor.doSwitch(resource);
    }

    @VisibleForTesting
    ResourceGeneratingVisitor createVisitor(final String packageName, final String type, final List<Resource> resources) {
        return new ResourceGeneratingVisitor(vendorName, packageName, createSTGroup(Resources.getResource(resourcesPath + type + ".stg")), type, resources);
    }

    protected String toParamName(final UriTemplate uri, final String delimiter) {
        return StringUtils.capitalize(uri.getParts().stream().map(
                uriTemplatePart -> {
                    if (uriTemplatePart instanceof UriTemplateExpression) {
                        return ((UriTemplateExpression)uriTemplatePart).getVariables().stream()
                                .map(s -> delimiter + StringUtils.capitalize(s)).collect(Collectors.joining());
                    }
                    return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, uriTemplatePart.toString().replace("/", "-"));
                }
        ).collect(Collectors.joining())).replaceAll("[^\\p{L}\\p{Nd}]+", "");
    }

    protected String toRequestName(UriTemplate uri, Method method) {
        return toParamName(uri, "By") + StringUtils.capitalize(method.getMethod().toString());
    }

    @Override
    protected STGroupFile createSTGroup(final URL resource) {
        final STGroupFile stGroup = super.createSTGroup(resource);
        stGroup.registerRenderer(Method.class,
                (arg, formatString, locale) -> {
                    final Method method = (Method)arg;
                    final BodyType firstBodyType = method.getBodies().stream().findFirst().orElse(null);
                    switch (Strings.nullToEmpty(formatString)) {
                        case "bodyType":
                            if (firstBodyType != null) {
                                if (firstBodyType.getType() instanceof FileType) {
                                    return "UploadedFileInterface ";
                                }
                                if (!BuiltinType.of(firstBodyType.getType().getName()).isPresent()) {
                                    final String t = (new TypesGenerator.PropertyTypeVisitor()).doSwitch(firstBodyType.getType());
                                    if (!Lists.newArrayList("mixed", "null", "bool", "string", "float", "int").contains(t)) {
                                        return t + " ";
                                    }
                                }
                            }
                            return "";
                        case "optionalBody":
                            if (method.getMethod().equals(HttpMethod.POST)) {
                                return "";
                            }
                            return " = null";
                        case "ensureHeader":
                            if (firstBodyType != null) {
                                if (firstBodyType.getType() instanceof FileType) {
                                    return "$headers = $this->ensureHeader($headers, 'Content-Type', $body->getClientMediaType());";
                                }
                            }
                            return "";
                        case "useBody":
                            if (firstBodyType != null) {
                                if (firstBodyType.getType() instanceof FileType) {
                                    return "use Psr\\Http\\Message\\UploadedFileInterface;";
                                }
                                if (!BuiltinType.of(firstBodyType.getType().getName()).isPresent()) {
                                    final String typePackage = getPackageFolder(firstBodyType.getType() , "\\");
                                    final String t = (new TypesGenerator.PropertyTypeVisitor()).doSwitch(firstBodyType.getType());
                                    if (!Lists.newArrayList("mixed", "null", "bool", "array", "string", "float", "int").contains(t)) {
                                        return "use " + vendorName + "\\" +
                                                capitalize(TypesGenerator.PACKAGE_NAME) + "\\" +
                                                typePackage + t + ";";
                                    }
                                }
                            }
                            return "";
                        case "serialize":
                            if (firstBodyType != null) {
                                if (firstBodyType.getType() instanceof FileType) {
                                    return "$body->getStream()";
                                }
                            }
                            return "!is_null($body) ? json_encode($body) : null";
                        case "requestName":
                            return toRequestName(absoluteUri((Resource)method.eContainer()), method);
                        default:
                            return arg.toString();
                    }
                }
        );
        stGroup.registerRenderer(UriTemplate.class,
                (arg, formatString, locale) -> {
                    final List<UriTemplateExpression> parts = ((UriTemplate)arg).getParts().stream()
                            .filter(uriTemplatePart -> uriTemplatePart instanceof UriTemplateExpression)
                            .map(uriTemplatePart -> (UriTemplateExpression)uriTemplatePart)
                            .collect(Collectors.toList());
                    switch (Strings.nullToEmpty(formatString)) {
                        case "methodName":
                            if (parts.size() > 0) {
                                return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, toParamName((UriTemplate)arg, "With"));
                            }

                            final String uri = arg.toString();
                            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, uri.replaceFirst("/", ""));
                        case "params":
                            if (parts.size() > 0) {
                                return parts.stream().map(
                                        uriTemplateExpression -> uriTemplateExpression.getVariables().stream().collect(Collectors.joining(", $"))
                                ).collect(Collectors.joining(", $"));
                            }
                            return "";
                        case "paramArray":
                            if (parts.size() > 0) {
                                return parts.stream().map(
                                        uriTemplateExpression -> uriTemplateExpression.getVariables().stream().map(s -> "'" + s + "' => $" + s).collect(Collectors.joining(", "))
                                ).collect(Collectors.joining(", "));
                            }
                            return "";
                        case "sprintf":
                            final Map<String, Object> params = parts.stream()
                                    .flatMap(uriTemplatePart -> uriTemplatePart.getVariables().stream())
                                    .collect(Collectors.toMap(o -> o, o -> "%s"));
                            return ((UriTemplate)arg).toString(params);
                        case "uri":
                            if (parts.size() > 0) {
                                return ((UriTemplate)arg).getParts().stream().map(uriTemplatePart -> {
                                    if (uriTemplatePart instanceof UriTemplateExpression) {
                                        Map<String, Object> t = Maps.newHashMap();
                                        ((UriTemplateExpression) uriTemplatePart).getVariables().forEach(s -> {
                                            t.put(s, "' . $" + s + " . '");
                                        });
                                        return uriTemplatePart.toString(t);
                                    }
                                    return uriTemplatePart.toString();
                                }).collect(Collectors.joining());
                            }
                            return arg.toString();
                        default:
                            return arg.toString();
                    }
                });
        return stGroup;
    }

    private class ResourceGeneratingVisitor extends ResourcesSwitch<String> {
        private final String vendorName;
        private final String packageName;
        private final STGroupFile stGroup;
        private final String type;
        private final List<Resource> resources;

        ResourceGeneratingVisitor(final String namespace, final String packageName, final STGroupFile stGroup, final String type, final List<Resource> resources) {
            this.stGroup = stGroup;
            this.vendorName = namespace;
            this.packageName = packageName;
            this.type = type;
            this.resources = resources;
        }

        @Override
        public String caseResource(final Resource resource) {
            final ST st = stGroup.getInstanceOf(type);
            st.add("vendorName", vendorName);
            st.add("package", packageName);
            st.add("resource", resource);
            st.add("index", resources.indexOf(resource));
            final UriTemplate uri = absoluteUri(resource);
            final Map<String, Object> params = uri.getParts().stream()
                    .filter(uriTemplatePart -> uriTemplatePart instanceof UriTemplateExpression)
                    .flatMap(uriTemplatePart -> ((UriTemplateExpression)uriTemplatePart).getVariables().stream())
                    .collect(Collectors.toMap(o -> o, o -> "%s"));
            st.add("params", params.entrySet());
            if (resource.getResources() != null) {
                final List<Resource> nonParamResources = resource.getResources().stream()
                        .filter(resource1 -> resource1.getRelativeUri().getParts().size() == 1).collect(Collectors.toList());
                st.add("resources", nonParamResources);
                st.add("resourcesIndex", nonParamResources.stream().map(resources::indexOf).collect(Collectors.toList()));
                final List<Resource> paramResources = resource.getResources().stream()
                        .filter(resource1 -> resource1.getRelativeUri().getParts().size() > 1).collect(Collectors.toList());
                st.add("resourcesWithParams", paramResources);
                final List<Integer> collect = paramResources.stream().map(resources::indexOf).collect(Collectors.toList());
                st.add("resourcesWithParamsIndex", collect);
            }
            return st.render();
        }
    }
}
