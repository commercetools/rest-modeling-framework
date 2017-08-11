package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.model.modules.Api;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JavaGenerator implements Generator {
    @Override
    public void generate(Api api, File outputPath) throws IOException {
        final TypesGenerator typesGenerator = new TypesGenerator("types", outputPath);
        typesGenerator.generate(api.getTypes());

        if (api.getResources().size() == 1) {
            QueriesGenerator queriesGenerator = new QueriesGenerator("queries", outputPath);
            queriesGenerator.generate(api.getResources().get(0));
        }
    }
}
