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
            final File outputFile = new File(
                    outputPath,
                    outputFileName
            );
            f.add(generateFile(content, outputFile));
            if (outputFileName.endsWith("json")) {
                try {
                    mapper.readTree(content);
                } catch (JsonParseException e) {
                    System.out.println("Error generating " + outputFileName + ": Invalid JSON");
                    System.out.println(e);
                }
            }
        }
        f.add(copyFile(Resources.getResource("templates/postman/oauth_settings.png").openStream(), new File(outputPath, "oauth_settings.png")));

        return f;
    }

    @VisibleForTesting
    String generateContent(final URL staticFile, final Api api) throws IOException {
        final ST st = getStGroup(staticFile);

        final String fileName = new File(staticFile.getPath()).getName();
        st.add("api", new ApiGenModel(api));
        if (fileName.equals("collection.json.stg")) {
            st.add("id", "f367b534-c9ea-e7c5-1f46-7a27dc6a30ba");
            final String readme = getStGroup(Resources.getResource("templates/postman/README.md.stg")).render();
            st.add("readme", readme);
        }
        if (fileName.equals("template.json.stg"))  {
            st.add("id", "5bb74f05-5e78-4aee-b59e-492c947bc160");
        }
        return st.render();
    }

    private ST getStGroup(final URL staticFile) {
        final STGroupFile stGroup = createSTGroup(staticFile);

        return stGroup.getInstanceOf("main");
    }
}
