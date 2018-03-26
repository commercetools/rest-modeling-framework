package io.vrap.rmf.raml.generic.generator.php;

import com.damnhandy.uri.template.Expression;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.impl.VarSpec;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.generic.generator.GeneratorHelper;
import io.vrap.rmf.raml.model.resources.HttpMethod;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.responses.Body;
import io.vrap.rmf.raml.model.types.*;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class RequestGenerator extends AbstractTemplateGenerator {
    private static final String resourcesPath = "./templates/php/";
    static final String TYPE_RESOURCE = "resource";
    private final String vendorName;
    private final AnyAnnotationType placeholderParamAnnotationType;

    RequestGenerator(final String vendorName, final AnyAnnotationType placeholderParamAnnotationType)
    {
        this.vendorName = vendorName;
        this.placeholderParamAnnotationType = placeholderParamAnnotationType;
    }

    public List<File> generate(final List<Resource> resources, final File outputPath) throws IOException {

        final List<File> f = Lists.newArrayList();
        f.addAll(generateResources(new File(outputPath, PhpGenerator.SRC_DIR + "/Request"), resources));
        f.addAll(generateRequests(new File(outputPath, PhpGenerator.SRC_DIR + "/Request"), resources));
        f.add(generateTest(outputPath, resources));

        return f;
    }

    private File generateTest(final File outputPath, final List<Resource> resources) throws IOException {
        final File requestTestFile = new File(outputPath, "test/unit/Request/RequestBuilderTest.php");
        return generateFile(generateRequestTest(resources), requestTestFile);
    }

    private List<File> generateResources(final File outputPath, final List<Resource> resources) throws IOException {
        final List<ResourceGenModel> flatResources = GeneratorHelper.flattenResources(resources);

        final List<File> f = Lists.newArrayList();
        final File requestFile = new File(outputPath, "RequestBuilder.php");
        final RootResourceGenModel root = new RootResourceGenModel(flatResources.stream().filter(resourceGenModel -> resources.contains(resourceGenModel.getResource())).collect(Collectors.toList()));
        f.add(generateFile(generateBuilder(root), requestFile));

        for (final ResourceGenModel resource : flatResources) {
            final File resourceFile = new File(outputPath, "Resource" + resource.getIndex() + ".php");

            f.add(generateFile(generateResource(resource), resourceFile));
        }
        return f;
    }

    private List<File> generateRequests(final File outputPath, final List<Resource> resources) throws IOException {
        final List<ResourceGenModel> flatResources = GeneratorHelper.flattenResources(resources);

        final List<File> f = Lists.newArrayList();
        for (final ResourceGenModel resource : flatResources) {
            for(final RequestGenModel request : resource.getMethods()) {
                final File resourceFile = new File(outputPath, request.getName() + ".php");
                f.add(generateFile(generateRequest(request), resourceFile));
            }
        }

        return f;
    }

    String generateRequestTest(final List<Resource> resources) throws IOException {
        final List<ResourceGenModel> flatResources = GeneratorHelper.flattenResources(resources);

        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_RESOURCE + ".stg"));
        final ST st = stGroup.getInstanceOf("builderTest");
        st.add("vendorName", vendorName);
        st.add("resources", flatResources.stream().filter(resourceGenModel -> resourceGenModel.getMethods().size() > 0).collect(Collectors.toList()));
        return st.render();
    }

    String generateBuilder(final RootResourceGenModel resource) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_RESOURCE + ".stg"));
        final ST st = stGroup.getInstanceOf("builder");
        st.add("vendorName", vendorName);
        st.add("resource", resource);
        return st.render();
    }

    String generateRequest(final RequestGenModel request) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_RESOURCE + ".stg"));
        final ST st = stGroup.getInstanceOf("request");
        st.add("vendorName", vendorName);
        st.add("request", request);
        return st.render();
    }

    String generateResource(final ResourceGenModel resource) {
        final STGroup stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_RESOURCE + ".stg"));
        final ST st = stGroup.getInstanceOf("resource");
        st.add("vendorName", vendorName);
        st.add("resource", resource);


        return st.render();
    }

    private String camelize(String arg)
    {
        return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, arg).replace(".", "-"));
    }

    @Override
    protected STGroupFile createSTGroup(final URL resource) {
        final STGroupFile stGroup = super.createSTGroup(resource);
        stGroup.registerRenderer(QueryParameter.class,
                (arg, formatString, locale) -> {
                    final QueryParameter param = (QueryParameter) arg;
                    Annotation anno = param.getAnnotation(placeholderParamAnnotationType);
                    switch (Strings.nullToEmpty(formatString)) {
                        case "methodParam":
                            if (anno != null) {
                                ObjectInstance o = (ObjectInstance)anno.getValue();
                                StringInstance placeholder = (StringInstance)o.getValue().stream().filter(propertyValue -> propertyValue.getName().equals("placeholder")).findFirst().orElse(null).getValue();
                                StringInstance paramName = (StringInstance)o.getValue().stream().filter(propertyValue -> propertyValue.getName().equals("paramName")).findFirst().orElse(null).getValue();
                                return "$" + camelize(placeholder.getValue()) + ", $" + paramName.getValue();
                            }
                            return "$" + camelize(param.getName());
                        case "methodName":
                            if (anno != null) {
                                ObjectInstance o = (ObjectInstance)anno.getValue();
                                StringInstance paramName = (StringInstance)o.getValue().stream().filter(propertyValue -> propertyValue.getName().equals("paramName")).findFirst().orElse(null).getValue();
                                return "with" + capitalize(paramName.getValue());
                            }
                            return "with" + capitalize(camelize(param.getName()));
                        case "paramName":
                            if (anno != null) {
                                ObjectInstance o = (ObjectInstance)anno.getValue();
                                StringInstance paramName = (StringInstance)o.getValue().stream().filter(propertyValue -> propertyValue.getName().equals("paramName")).findFirst().orElse(null).getValue();
                                return "$" + paramName.getValue();
                            }
                            return "$" + camelize(param.getName());
                        case "template":
                            if (anno != null) {
                                ObjectInstance o = (ObjectInstance) anno.getValue();
                                StringInstance template = (StringInstance) o.getValue().stream().filter(propertyValue -> propertyValue.getName().equals("template")).findFirst().orElse(null).getValue();
                                StringInstance placeholder = (StringInstance) o.getValue().stream().filter(propertyValue -> propertyValue.getName().equals("placeholder")).findFirst().orElse(null).getValue();
                                return "sprintf('" + template.getValue().replace("<<" + placeholder.getValue() + ">>", "%s") + "', $" + placeholder.getValue() + ")";
                            }
                            return "'" + param.getName() + "'";
                        default:
                            return camelize(param.getName());
                    }
                });
        stGroup.registerRenderer(Method.class,
                (arg, formatString, locale) -> {
                    final Method method = (Method)arg;
                    final Body firstBodyType = method.getBodies().stream().findFirst().orElse(null);
                    switch (Strings.nullToEmpty(formatString)) {
                        case "optionalBody":
                            if (method.getMethod().equals(HttpMethod.POST)) {
                                return " = null";
                            }
                            return " = null";
                        case "ensureHeader":
                            if (firstBodyType != null) {
                                if (firstBodyType.getType() instanceof FileType) {
                                    return "if (!is_null($body)) { $headers = $this->ensureHeader($headers, 'Content-Type', $body->getClientMediaType()); }";
                                }
                            }
                            return "";
                        case "serialize":
                            if (firstBodyType != null) {
                                if (firstBodyType.getType() instanceof FileType) {
                                    return "!is_null($body) ? $body->getStream() : null";
                                }
                            }
                            return "!is_null($body) ? json_encode($body) : null";
                        default:
                            return arg.toString();
                    }
                }
        );
        return stGroup;
    }
}
