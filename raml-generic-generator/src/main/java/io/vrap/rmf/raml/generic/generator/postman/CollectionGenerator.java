package io.vrap.rmf.raml.generic.generator.postman;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.model.modules.Api;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class CollectionGenerator extends AbstractTemplateGenerator {

    public List<File> generate(final File outputPath, Api api) throws IOException {
        final File resourcePath = new File(Resources.getResource("templates/postman/").getFile());
        final Collection<File> files = FileUtils.listFiles(resourcePath, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        final ObjectMapper mapper = new ObjectMapper();

        final List<File> f = Lists.newArrayList();
        for (File staticFile : files) {
            final String content = generateContent(staticFile, api);
            final String outputFileName = staticFile.toString()
                    .replace(".stg", "")
                    .replace(resourcePath.toString(), "");
            try {
                mapper.readTree(content);

                final File outputFile = new File(
                        outputPath,
                        outputFileName
                );
                f.add(generateFile(content, outputFile));
            } catch (JsonParseException e) {
                System.out.println("Error generating " + outputFileName + ": Invalid JSON");
                System.out.println(e);
            }
        }

        return f;
    }

    @VisibleForTesting
    String generateContent(final File staticFile, final Api api) throws IOException {
        final STGroupFile stGroup = createSTGroup(staticFile.toURI().toURL());

        final ST st = stGroup.getInstanceOf("main");
        st.add("api", new ApiGenModel(api));
        if (staticFile.getName().equals("collection.json.stg")) {
            st.add("id", "f367b534-c9ea-e7c5-1f46-7a27dc6a30ba");
        }
        if (staticFile.getName().equals("template.json.stg"))  {
            st.add("id", "c5566d48-02e5-742c-ff0a-a3b948c46502");
        }
        final String t = staticFile.getName();
        return st.render();
    }
}
