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
    static final String TYPE_README = "readme";
    private final String vendorName;

    ReadmeGenerator(final String vendorName)
    {
        this.vendorName = vendorName;
    }

    public List<File> generate(Api api, final File outputPath) throws IOException {

        final List<File> f = Lists.newArrayList();

        f.addAll(generate(outputPath, new ReadmeGenModel(new ApiGenModel(api))));

        return f;
    }


    private List<File> generate(final File outputPath, final ReadmeGenModel readme) throws IOException {
        final List<File> f = Lists.newArrayList();

        f.add(generateFile(generateReadme(readme), new File(outputPath, "README.md")));
        return f;
    }

    @VisibleForTesting
    String generateReadme(ReadmeGenModel readme) {
        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + TYPE_README + ".stg"));
        final ST st = stGroup.getInstanceOf("main");
        st.add("vendorName", vendorName);
        st.add("readme", readme);
        return st.render();
    }
}
