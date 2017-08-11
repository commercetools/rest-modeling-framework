package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.Generator;
import io.vrap.rmf.raml.model.modules.Api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class PhpGenerator implements Generator {

    public void generate(final Api api, final File outputPath) throws IOException {
        if (outputPath.exists()) {
            File[] files = outputPath.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        Files.deleteIfExists(file.toPath());
                    }
                }
            }
        } else {
            Files.createDirectories(outputPath.toPath());
        }

        TypesGenerator generator = new TypesGenerator();
        generator.generate(api.getTypes(), new File(outputPath, "target") );

        List<File> files = Lists.newArrayList(
            new File(Resources.getResource("./templates/php/composer.json").getFile())
        );

        files.forEach(file -> {
            try {
                Files.copy(file.toPath(), new File(outputPath, file.getName()).toPath());
            } catch (IOException e) {
                System.out.println(e);
            }
        });
    }
}
