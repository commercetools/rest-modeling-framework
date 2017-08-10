package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.model.modules.Api;

import java.io.File;
import java.io.IOException;

public interface Generator {
    void generate(final Api api, final File outputPath) throws IOException;
}
