package io.vrap.rmf.raml.generic.generator.postman;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.generic.generator.Helper;
import io.vrap.rmf.raml.model.modules.Api;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CollectionGenerator extends AbstractTemplateGenerator {

    public List<File> generate(final File outputPath, Api api) throws IOException {
        final URL resourcePath = Resources.getResource("templates/postman/");
        final List<URL> files = Helper.getTemplatesFromDirectory("templates/postman/");
        final ObjectMapper mapper = new ObjectMapper();

        final List<File> f = Lists.newArrayList();
        for (URL staticFile : files) {
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
    String generateContent(final URL staticFile, final Api api) throws IOException {
        final STGroupFile stGroup = createSTGroup(staticFile);

        final ST st = stGroup.getInstanceOf("main");
        final String fileName = new File(staticFile.getPath()).getName();
        st.add("api", new ApiGenModel(api));
        if (fileName.equals("collection.json.stg")) {
            st.add("id", "f367b534-c9ea-e7c5-1f46-7a27dc6a30ba");
        }
        if (fileName.equals("template.json.stg"))  {
            st.add("id", "5bb74f05-5e78-4aee-b59e-492c947bc160");
        }
        return st.render();
    }
}
