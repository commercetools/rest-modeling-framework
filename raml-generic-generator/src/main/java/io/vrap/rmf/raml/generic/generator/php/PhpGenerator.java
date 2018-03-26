package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.generic.generator.Generator;
import io.vrap.rmf.raml.generic.generator.Helper;
import io.vrap.rmf.raml.generic.generator.TypeGenModel;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import io.vrap.rmf.raml.model.util.StringCaseFormat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PhpGenerator implements Generator {
    static final String SRC_DIR = "src";
    private final String vendorName;

    public PhpGenerator(final String vendorName) {
        this.vendorName = vendorName;
    }

    public void generate(final Api api, final File outputPath) throws IOException {
        String title = StringCaseFormat.UPPER_CAMEL_CASE.apply(api.getTitle());
        String vendorName = Optional.ofNullable(this.vendorName).orElse(title);

        Helper.ensureDirectory(outputPath);

        AnyAnnotationType placeholderParamAnnotation = api.getAnnotationType("placeholderParam");
        TypesGenerator generator = new TypesGenerator(vendorName);
        List<File> f = generator.generate(api.getTypes(), new File(outputPath, SRC_DIR + "/" + TypeGenModel.TYPES));

        StaticGenerator staticGenerator = new StaticGenerator(vendorName);
        f.addAll(staticGenerator.generate(outputPath, api));

        RequestGenerator requestGenerator = new RequestGenerator(vendorName, placeholderParamAnnotation);
        f.addAll(requestGenerator.generate(api.getResources(), outputPath));

        BuilderGenerator builderGenerator = new BuilderGenerator(vendorName);
        f.addAll(builderGenerator.generate(api, new File(outputPath, SRC_DIR + "/" + BuilderGenerator.BUILDER)));

        ReadmeGenerator readmeGenerator = new ReadmeGenerator(vendorName);
        f.addAll(readmeGenerator.generate(api, outputPath));

        Helper.deleteObsoleteFiles(outputPath, f);
    }
}
