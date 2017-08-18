package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.base.CaseFormat;
import io.vrap.rmf.raml.generic.generator.Generator;
import io.vrap.rmf.raml.model.modules.Api;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Optional;

public class PhpGenerator implements Generator {
    private final String vendorName;

    public PhpGenerator(final String vendorName) {
        this.vendorName = vendorName;
    }

    public void generate(final Api api, final File outputPath) throws IOException {
        String title = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, api.getTitle());
        String vendorName = Optional.ofNullable(this.vendorName).orElse(title);
        if (outputPath.exists()) {
            Collection<File> files = FileUtils.listFiles(
                    outputPath,
                    TrueFileFilter.INSTANCE,
                    FileFilterUtils.notFileFilter(
                            FileFilterUtils.and(
                                    FileFilterUtils.directoryFileFilter(),
                                    FileFilterUtils.nameFileFilter("vendor")
                            )
                    )
            );
            for (File file : files) {
                if (file.isFile()) {
                    Files.deleteIfExists(file.toPath());
                }
            }
        } else {
            Files.createDirectories(outputPath.toPath());
        }

        TypesGenerator generator = new TypesGenerator(vendorName);
        generator.generate(api.getTypes(), new File(outputPath, "target/Types") );

        StaticGenerator staticGenerator = new StaticGenerator(vendorName);
        staticGenerator.generate(outputPath, api);

//        List<File> files = Lists.newArrayList(
//        );
//
//        files.forEach(file -> {
//            try {
//                Files.copy(file.toPath(), new File(outputPath, file.getName()).toPath());
//            } catch (IOException e) {
//                System.out.println(e.toString());
//            }
//        });
    }
}
