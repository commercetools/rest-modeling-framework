package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class StaticGenerator extends AbstractGenerator {
    private final String vendorName;

    StaticGenerator(final String namespace) {
        this.vendorName = namespace;
    }

    public void generate(final File outputPath) throws IOException {
        Map<String, URL> files = ImmutableMap.<String, URL>builder()
                .put("composer.json", Resources.getResource("templates/php/statics/composer.json.stg"))
                .build();

        for (Map.Entry<String, URL> entry : files.entrySet()) {
            final STGroupFile stGroup = createSTGroup(entry.getValue());

            final ST st = stGroup.getInstanceOf("main");
            st.add("vendorName", vendorName);
            final String content = st.render();
            generateFile(content, new File(outputPath, entry.getKey()));
        }
    }
}
