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

public class BuilderGenerator extends AbstractTemplateGenerator {
    public static String BUILDER = "Builder";
    private static final String resourcesPath = "./templates/php/";
    static final String TYPE_BUILDER = "builder";
    private final String vendorName;

    BuilderGenerator(final String vendorName)
    {
        this.vendorName = vendorName;
    }

    public List<File> generate(Api api, final File outputPath) throws IOException {

        final List<File> f = Lists.newArrayList();

        final List<BuilderGenModel> builders = Lists.newArrayList();

        builders.addAll(
                GeneratorHelper.flattenResources(api.getResources()).stream()
                        .filter(resourceGenModel -> resourceGenModel.getUpdateBuilder() != null)
                        .map(ResourceGenModel::getUpdateBuilder)
                .collect(Collectors.toList()));

        f.addAll(generateBuilders(new File(outputPath, PhpGenerator.SRC_DIR + "/" + BUILDER), builders));
        f.addAll(generateBuilderTests(new File(outputPath, "test/unit/" + BUILDER), builders));

        return f;
    }


    private List<File> generateBuilders(final File outputPath, List<BuilderGenModel> builders) throws IOException {
        final List<File> f = Lists.newArrayList();
        for (final BuilderGenModel builder : builders) {

            final File builderFile = new File(outputPath, builder.getUpdateType().getName().concat("Builder.php"));

            f.add(generateFile(generateBuilder(builder), builderFile));
//            for(final TypeGenModel updateAction : builder.getUpdates()) {
//                final File actionBuilderFile = new File(outputPath, updateAction.getName().concat("Builder.php"));
//                f.add(generateFile(generateActionBuilder(updateAction), actionBuilderFile));
//            }
        }
        return f;
    }

    private List<File> generateBuilderTests(final File outputPath, List<BuilderGenModel> builders) throws IOException {
        final List<File> f = Lists.newArrayList();
        for (final BuilderGenModel builder : builders) {

            final File builderFile = new File(outputPath, builder.getUpdateType().getName().concat("BuilderTest.php"));

            f.add(generateFile(generateBuilderTest(builder), builderFile));
        }
        return f;
    }

    @VisibleForTesting
    String generateBuilder(BuilderGenModel builder) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_BUILDER + ".stg"));
        final ST st = stGroup.getInstanceOf("updateBuilder");
        st.add("vendorName", vendorName);
        st.add("builder", builder);
        return st.render();
    }

    @VisibleForTesting
    String generateBuilderTest(BuilderGenModel builder) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_BUILDER + ".stg"));
        final ST st = stGroup.getInstanceOf("builderTest");
        st.add("vendorName", vendorName);
        st.add("builder", builder);
        return st.render();
    }

//    @VisibleForTesting
//    String generateActionBuilder(TypeGenModel updateAction) {
//        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_BUILDER + ".stg"));
//        final ST st = stGroup.getInstanceOf("actionBuilder");
//        st.add("vendorName", vendorName);
//        st.add("type", updateAction);
//        return st.render();
//    }
}
