package io.vrap.rmf.raml.generic.generator.postman;

import com.google.common.base.CaseFormat;
import io.vrap.rmf.raml.generic.generator.Generator;
import io.vrap.rmf.raml.generic.generator.Helper;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.util.StringCaseFormat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PostmanGenerator implements Generator {
    private final String vendorName;

    public PostmanGenerator(final String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public void generate(Api api, File outputPath) throws IOException {
        String title = StringCaseFormat.UPPER_CAMEL_CASE.apply(api.getTitle());
        String vendorName = Optional.ofNullable(this.vendorName).orElse(title);

        Helper.ensureDirectory(outputPath);

        CollectionGenerator generator = new CollectionGenerator(vendorName);
        List<File> f = generator.generate(outputPath, api);

        Helper.deleteObsoleteFiles(outputPath, f);
    }
}
