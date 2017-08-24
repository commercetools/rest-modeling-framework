package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.security.OAuth20Settings;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StaticGenerator extends AbstractTemplateGenerator {
    private final String vendorName;

    StaticGenerator(final String namespace) {
        this.vendorName = namespace;
    }

    public List<File> generate(final File outputPath, Api api) throws IOException {
        File resourcePath = new File(Resources.getResource("templates/php/statics/").getFile());
        Collection<File> files = FileUtils.listFiles(resourcePath, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

        final List<File> f = Lists.newArrayList();
        for (File staticFile : files) {
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
    String generateContent(File staticFile, Api api) throws IOException {
        final STGroupFile stGroup = createSTGroup(staticFile.toURI().toURL());

        final ST st = stGroup.getInstanceOf("main");
        st.add("vendorName", vendorName);
        final String t = staticFile.getName();
        if (staticFile.getName().equals("Factory.php.stg")) {
            final String apiUri = api.getBaseUri().toString();
            final String authUri = api.getSecuritySchemes().stream()
                    .filter(securityScheme -> securityScheme.getSettings() instanceof OAuth20Settings)
                    .map(securityScheme -> ((OAuth20Settings)securityScheme.getSettings()).getAccessTokenUri())
                    .findFirst().orElse("");
            st.add("apiUri", apiUri);
            st.add("authUri", authUri);
        }
        return st.render();
    }
}
