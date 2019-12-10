package io.vrap.rmf.raml.generic.generator.md;

import io.vrap.rmf.raml.generic.generator.Generator;
import io.vrap.rmf.raml.model.modules.Api;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

public class MdGenerator implements Generator {

    @Override
    public void generate(Api api, File outputPath) throws IOException {
        if (outputPath.exists()) {
            Collection<File> files = FileUtils.listFiles(
                    outputPath,
                    TrueFileFilter.INSTANCE,
                    TrueFileFilter.INSTANCE
            );
            for (File file : files) {
                if (file.isFile()) {
                    Files.deleteIfExists(file.toPath());
                }
            }
        } else {
            Files.createDirectories(outputPath.toPath());
        }

        TypesGenerator generator = new TypesGenerator();
        generator.generate(api.getTypes(), new File(outputPath, "types") );

    }
}
