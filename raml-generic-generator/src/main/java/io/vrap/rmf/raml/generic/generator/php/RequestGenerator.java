package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.resources.util.ResourcesSwitch;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RequestGenerator extends AbstractTemplateGenerator {
    private static final String resourcesPath = "./templates/php/";
    static final String TYPE_REQUEST = "request";
    static final String TYPE_RESOURCE = "resource";
    static final String PACKAGE_NAME = "request";
    private final String vendorName;

    RequestGenerator(final String vendorName)
    {
        this.vendorName = vendorName;
    }

    public List<File> generate(final List<Resource> resources, final File outputPath) throws IOException {

        final List<File> f = Lists.newArrayList();
        f.addAll(generateResources(outputPath, resources));

        return f;
    }

    private List<File> generateResources(final File outputPath, List<Resource> resources) throws IOException {
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

    private List<Resource> flattenResources(List<Resource> resources)
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

    String generateBuilder(List<Resource> resources) {
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

    String generateResource(ResourceGeneratingVisitor requestGeneratingVisitor, Resource resource) {
        return requestGeneratingVisitor.doSwitch(resource);
    }

    @VisibleForTesting
    ResourceGeneratingVisitor createVisitor(final String packageName, final String type, final List<Resource> resources) {
        return new ResourceGeneratingVisitor(vendorName, packageName, createSTGroup(Resources.getResource(resourcesPath + type + ".stg")), type, resources);
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
        public String caseResource(Resource resource) {
            final ST st = stGroup.getInstanceOf(type);
            st.add("vendorName", vendorName);
            st.add("package", packageName);
            st.add("resource", resource);
            st.add("index", resources.indexOf(resource));
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
