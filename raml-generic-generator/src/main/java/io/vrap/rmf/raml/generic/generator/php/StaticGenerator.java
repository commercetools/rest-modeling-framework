package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.generic.generator.Helper;
import io.vrap.rmf.raml.generic.generator.TypeGenModel;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.security.OAuth20Settings;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class StaticGenerator extends AbstractTemplateGenerator {
    private final String vendorName;

    StaticGenerator(final String namespace) {
        this.vendorName = namespace;
    }

    public List<File> generate(final File outputPath, Api api) throws IOException {
        final URL resourcePath = Resources.getResource("templates/php/statics/");
        final List<URL> files = Helper.getTemplatesFromDirectory("templates/php/statics/");

        final List<File> f = Lists.newArrayList();
        for (URL staticFile : files) {
            final String content = generateContent(staticFile, api);
            final File outputFile = new File(
                    outputPath,
                    staticFile.toString()
                            .replace(".stg", "")
                            .replace(resourcePath.toString(), "")
            );
            f.add(generateFile(content, outputFile));
        }

        return f;
    }

    @VisibleForTesting
    String generateContent(URL staticFile, Api api) throws IOException {
        final STGroupFile stGroup = createSTGroup(staticFile);
        final String fileName = new File(staticFile.getPath()).getName();

        final ST st = stGroup.getInstanceOf("main");
        st.add("vendorName", vendorName);
        if (fileName.equals("ResourceClassMap.php.stg")) {
            st.add("package", TypeGenModel.TYPES);
        }
        if (fileName.equals("Config.php.stg")) {
            final String apiUri = api.getBaseUri().getTemplate();
            final String authUri = api.getSecuritySchemes().stream()
                    .filter(securityScheme -> securityScheme.getSettings() instanceof OAuth20Settings)
                    .map(securityScheme -> ((OAuth20Settings)securityScheme.getSettings()).getAccessTokenUri())
                    .findFirst().orElse("");
            st.add("api", new ApiGenModel(api));
        }
        return st.render();
    }
}
