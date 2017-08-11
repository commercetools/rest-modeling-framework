package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.generic.generator.Generator;
import io.vrap.rmf.raml.model.modules.Api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PhpGenerator implements Generator {

    public void generate(final Api api, final File outputPath) throws IOException {
        if (outputPath.exists()) {
            File[] files = outputPath.listFiles();
            if (files != null) {
                for (File file : files) {
                    Files.deleteIfExists(file.toPath());
                }
            }
        } else {
            Files.createDirectories(outputPath.toPath());
        }

        TypesGenerator generator = new TypesGenerator();
        generator.generate(api.getTypes(), outputPath);
    }
}
