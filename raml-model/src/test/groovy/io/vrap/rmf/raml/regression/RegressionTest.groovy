package io.vrap.rmf.raml.regression

import com.google.common.base.Charsets
import io.vrap.rmf.raml.model.RamlModelBuilder
import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.util.StringCaseFormat
import io.vrap.rmf.raml.persistence.ResourceFixtures
import org.eclipse.emf.common.util.URI
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

abstract class RegressionTest extends Specification implements ResourceFixtures {
    List<Path> featureFiles = new ArrayList<>()

    def cleanup() {
        featureFiles.each {
            if (it.toFile().exists()) {
                it.toFile().delete()
            }
        }
        featureFiles.clear()
    }



    RamlModelResult<Api> constructApi(String input) {
        constructApi('api.raml', input)
    }

    RamlModelResult<Api> constructApi(String fileName, String input) {
        constructApi(fileName, null, input)
    }

    RamlModelResult<Api> constructApi(List<String> usesFiles, String input) {
        constructApi('api.raml', usesFiles, input)
    }

    RamlModelResult<Api> constructApi(String fileName, List<String> usesFiles, String input) {
        URI i = writeFile(fileName, usesFiles, input)
        return new RamlModelBuilder().buildApi(i)
    }

    URI writeFile(String fileName, String input) {
        return writeFile(fileName, null, input);
    }

    URI writeFile(String fileName, List<String> usesFiles, String input) {
        Path featureFile = Paths.get("./" + getTmpFileName(fileName));

        if (usesFiles != null) {
            for (String file: usesFiles) {
                input = input.replace(file, getTmpFileName(file))
            }
        }

        Files.write(featureFile, input.stripIndent().getBytes(Charsets.UTF_8));
        featureFiles.add(featureFile)
        return URI.createURI(featureFile.toAbsolutePath().toUri().toString())
    }

    String getTmpFileName(String fileName) {
        String fileExtension = fileName.contains(".") ? fileName.split("[.]").last() : "raml";
        String file = fileName.replace("." + fileExtension, "");
        String testFileName = StringCaseFormat.LOWER_HYPHEN_CASE.apply(specificationContext.currentFeature.getName()).replace("." + fileExtension, "");
        return "tmp-${file}-${testFileName}." + fileExtension;
    }
}
