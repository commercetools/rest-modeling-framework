package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.generic.generator.java.JavaGenerator;
import io.vrap.rmf.raml.generic.generator.md.MdGenerator;
import io.vrap.rmf.raml.generic.generator.php.PhpGenerator;
import io.vrap.rmf.raml.generic.generator.postman.PostmanGenerator;
import io.vrap.rmf.raml.model.RamlDiagnostic;
import io.vrap.rmf.raml.model.RamlModelBuilder;
import io.vrap.rmf.raml.model.RamlModelResult;
import io.vrap.rmf.raml.model.modules.Api;
import org.apache.commons.cli.*;
import org.eclipse.emf.common.util.URI;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class CodeGenerator {
    public static void main(String... args) throws Exception {

        final GeneratorOptions options = new GeneratorOptions(args);

        final long startTimeMillis = System.currentTimeMillis();

        final URI fileURI = URI.createFileURI(options.getRamlPath().toString());
        final File generateTo = new File(options.getOutputPath().toString() + "/" + options.getLanguage());

        final RamlModelResult<Api> apiRamlModelResult = new RamlModelBuilder().buildApi(fileURI);
        final List<RamlDiagnostic> validationResults = apiRamlModelResult.getValidationResults();
        if (validationResults.isEmpty()) {
            Generator generator = of(options.getLanguage(), options.getVendorName());

            generator.generate(apiRamlModelResult.getRootObject(), generateTo);

            final long endTimeMillis = System.currentTimeMillis();
            final Duration duration = Duration.ofMillis(endTimeMillis - startTimeMillis);
            System.out.println("Generation took:" + duration);
        } else {
            validationResults.forEach(diagnostic -> System.err.println(diagnostic.getMessage()));
        }
    }

    private static Generator of(String language, String vendorName) throws Exception {
        switch (language) {
            case "php":
                return new PhpGenerator(vendorName);
            case "md":
                return new MdGenerator();
            case "postman":
                return new PostmanGenerator();
            case "java":
                return new JavaGenerator();
            default:
                throw new Exception("unknown language");
        }
    }

    static class GeneratorOptions {
        private Path ramlPath;
        private Path outputPath;
        private String language;
        @Nullable
        private String vendorName;
        private final Options options;

        GeneratorOptions(String[] args)
        {
            final CommandLine cmd;
            final CommandLineParser parser = new DefaultParser();

            options = getOptions();

            try {
                cmd = parser.parse(options, args);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                printHelp();

                System.exit(1);
                return;
            }

            language = cmd.getOptionValue(getLangOption().getOpt(), "php");
            vendorName = cmd.getOptionValue(getVendorNameOption().getOpt());
            outputPath = Paths.get(cmd.getOptionValue(getOutputPathOption().getOpt(), ".")).toAbsolutePath();

            if (cmd.hasOption(getHelpOption().getOpt())) {
                printHelp();
                System.exit(0);
            }
            if (cmd.getArgs().length == 0) {
                System.err.println("Missing file input argument.");
                System.exit(1);
            }

            ramlPath = Paths.get(cmd.getArgs()[0]).toAbsolutePath();
        }

        private Options getOptions()
        {
            final Options options = new Options();
            options.addOption(getOutputPathOption());
            options.addOption(getLangOption());

            return options;
        }

        private Option getOutputPathOption()
        {
            return Option.builder("o")
                    .longOpt("output")
                    .argName("outputPath")
                    .desc("output path")
                    .hasArg(true)
                    .required(false)
                    .build();
        }

        private Option getLangOption()
        {
            return Option.builder("l")
                    .longOpt("lang")
                    .argName("language")
                    .desc("output language")
                    .hasArg(true)
                    .required(false)
                    .build();
        }

        private Option getVendorNameOption()
        {
            return Option.builder("v")
                    .longOpt("vendor")
                    .argName("vendorName")
                    .desc("base vendorName")
                    .hasArg(true)
                    .required(false)
                    .build();
        }

        private Option getHelpOption() {
            return Option.builder("h")
                    .longOpt("help")
                    .desc("display help")
                    .hasArg(false)
                    .required(false)
                    .build();
        }

        private void printHelp()
        {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("codegen [OPTIONS] <file.raml>", options);
        }

        Path getRamlPath() {
            return ramlPath;
        }
        Path getOutputPath() {
            return outputPath;
        }
        String getLanguage() {
            return language;
        }
        @Nullable
        String getVendorName() {
            return vendorName;
        }
    }
}
