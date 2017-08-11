package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.generic.generator.Generator;
import io.vrap.rmf.raml.model.modules.Api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PhpGenerator implements Generator {

    public void generate(final Api api, final File outputPath) throws IOException {
        if (outputPath.exists()) {
            for (File file : outputPath.listFiles()) {
                Files.deleteIfExists(file.toPath());
            }
        } else {
            Files.createDirectories(outputPath.toPath());
        }

        TypesGenerator generator = new TypesGenerator();
        generator.generate(api.getTypes(), outputPath);
    }
}
