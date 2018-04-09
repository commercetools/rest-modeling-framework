package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.generic.generator.GeneratorHelper;
import io.vrap.rmf.raml.model.modules.Api;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReadmeGenerator extends AbstractTemplateGenerator {
    private static final String resourcesPath = "./templates/php/";
    static final String TYPE_REQUESTBUILDER = "requestbuilder";
    static final String TYPE_UPDATEBUILDER = "updatebuilder";
    private final String vendorName;

    ReadmeGenerator(final String vendorName)
    {
        this.vendorName = vendorName;
    }

    public List<File> generate(Api api, final File outputPath) throws IOException {

        final List<File> f = Lists.newArrayList();

        f.addAll(generateRequest(outputPath, new ReadmeGenModel(new ApiGenModel(api))));

        final List<BuilderGenModel> builders = Lists.newArrayList();

        builders.addAll(
                GeneratorHelper.flattenResources(api.getResources()).stream()
                        .filter(resourceGenModel -> resourceGenModel.getUpdateBuilder() != null)
                        .map(ResourceGenModel::getUpdateBuilder)
                        .collect(Collectors.toList()));
        f.addAll(generateUpdates(outputPath, builders));
        f.addAll(generateUpdatesRst(outputPath, builders));
        f.add(generateIndexRst(outputPath, builders));
        return f;
    }

    File generateIndexRst(final File outputPath, List<BuilderGenModel> builders) throws IOException {
        return generateFile(generateIndexRst(builders), new File(outputPath, "docs/source/index.rst"));
    }

    @VisibleForTesting
    private String generateIndexRst(List<BuilderGenModel> builders) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + "index.rst.stg"));
        final ST st = stGroup.getInstanceOf("main");
        st.add("vendorName", vendorName);
        st.add("builders", builders);
        return st.render();
    }

    private List<File> generateRequest(final File outputPath, final ReadmeGenModel readme) throws IOException {
        final List<File> f = Lists.newArrayList();

        f.add(generateFile(generateRequestBuilder(readme), new File(outputPath, "docs/RequestBuilder.md")));
        f.add(generateFile(generateRequestBuilderRst(readme), new File(outputPath, "docs/source/requestbuilder.rst")));

        return f;
    }

    @VisibleForTesting
    String generateRequestBuilder(ReadmeGenModel readme) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_REQUESTBUILDER + "md.stg"));
        final ST st = stGroup.getInstanceOf("main");
        st.add("vendorName", vendorName);
        st.add("readme", readme);
        return st.render();
    }

    @VisibleForTesting
    String generateRequestBuilderRst(ReadmeGenModel readme) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_REQUESTBUILDER + "rst.stg"));
        final ST st = stGroup.getInstanceOf("main");
        st.add("vendorName", vendorName);
        st.add("readme", readme);
        return st.render();
    }

    private List<File> generateUpdates(final File outputPath, List<BuilderGenModel> builders) throws IOException {
        final List<File> f = Lists.newArrayList();
        for (final BuilderGenModel builder : builders) {

            final File builderFile = new File(outputPath, "docs/" + builder.getUpdateType().getName().concat("Builder.md"));

            f.add(generateFile(generateUpdateBuilder(builder), builderFile));
        }
        return f;
    }

    @VisibleForTesting
    String generateUpdateBuilder(BuilderGenModel builder) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_UPDATEBUILDER + "md.stg"));
        final ST st = stGroup.getInstanceOf("main");
        st.add("vendorName", vendorName);
        st.add("builder", builder);
        return st.render();
    }

    private List<File> generateUpdatesRst(final File outputPath, List<BuilderGenModel> builders) throws IOException {
        final List<File> f = Lists.newArrayList();
        for (final BuilderGenModel builder : builders) {

            final File builderFile = new File(outputPath, "docs/source/" + builder.getUpdateType().getName().concat("builder.rst").toLowerCase());

            f.add(generateFile(generateUpdateBuilderRst(builder), builderFile));
        }
        return f;
    }

    @VisibleForTesting
    String generateUpdateBuilderRst(BuilderGenModel builder) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_UPDATEBUILDER + "rst.stg"));
        final ST st = stGroup.getInstanceOf("main");
        st.add("vendorName", vendorName);
        st.add("builder", builder);
        return st.render();
    }
}
