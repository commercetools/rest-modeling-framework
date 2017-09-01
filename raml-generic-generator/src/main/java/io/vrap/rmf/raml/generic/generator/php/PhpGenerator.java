package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.base.CaseFormat;
import io.vrap.rmf.raml.generic.generator.Generator;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PhpGenerator implements Generator {
    private final String vendorName;

    public PhpGenerator(final String vendorName) {
        this.vendorName = vendorName;
    }

    public void generate(final Api api, final File outputPath) throws IOException {
        String title = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, api.getTitle());
        String vendorName = Optional.ofNullable(this.vendorName).orElse(title);
        if (!outputPath.exists()) {
            Files.createDirectories(outputPath.toPath());
        }

        AnyAnnotationType packageAnnotation = api.getAnnotationTypes().stream().filter(anyAnnotationType -> anyAnnotationType.getName().equals("package")).findFirst().orElse(null);
        AnyAnnotationType identifierAnnotation = api.getAnnotationTypes().stream().filter(anyAnnotationType -> anyAnnotationType.getName().equals("identifier")).findFirst().orElse(null);
        TypesGenerator generator = new TypesGenerator(vendorName, packageAnnotation, identifierAnnotation);
        List<File> f = generator.generate(api.getTypes(), new File(outputPath, "src/Type"));

        StaticGenerator staticGenerator = new StaticGenerator(vendorName);
        f.addAll(staticGenerator.generate(outputPath, api));

        RequestGenerator requestGenerator = new RequestGenerator(vendorName);
        f.addAll(requestGenerator.generate(api.getResources(), new File(outputPath, "src/Request")));
        Collection<File> files = FileUtils.listFiles(
                outputPath,
                TrueFileFilter.INSTANCE,
                FileFilterUtils.notFileFilter(
                        FileFilterUtils.and(
                                FileFilterUtils.directoryFileFilter(),
                                FileFilterUtils.nameFileFilter("vendor")
                        )
                )
        ).stream().filter(file -> !f.contains(file)).collect(Collectors.toSet());

        for (File file : files) {
            if (file.isFile()) {
                Files.deleteIfExists(file.toPath());
            }
        }
    }
}
