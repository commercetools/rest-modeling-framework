package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.model.modules.TypeContainer;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.apache.commons.cli.*;

public class CodeGenerator {
    public static void main(String... args) throws IOException {

        final GeneratorOptions options = new GeneratorOptions(args);

        final long startTimeMillis = System.currentTimeMillis();

        final URI fileURI = URI.createFileURI(options.getRamlPath().toString());
        final File generateTo = new File(options.getOutputPath().toString() + "/" + options.getLanguage());
        final Resource resource = new RamlResourceSet()
                .getResource(fileURI, true);
        final EList<EObject> contents = resource.getContents();
        final EList<Resource.Diagnostic> errors = resource.getErrors();

        if (errors.isEmpty() && contents.size() == 1) {
            final EObject rootObject = contents.get(0);
            if (rootObject instanceof TypeContainer) {
                final TypeContainer typeContainer = (TypeContainer) rootObject;
                final TypesGenerator typesGenerator = new TypesGenerator("types", generateTo);
                typesGenerator.generate(typeContainer);
                final long endTimeMillis = System.currentTimeMillis();

                final Duration duration = Duration.ofMillis(endTimeMillis - startTimeMillis);
                System.out.println("Generation took:" + duration);
            } else {
                System.err.println("Invalid root object:" + rootObject.eClass().getName());
            }
        } else if (contents.isEmpty()) {
            System.err.println("File '" + fileURI + "' is empty");
        } else {
            errors.forEach(diagnostic -> System.err.println(diagnostic.getMessage()));
        }
    }

    static class GeneratorOptions {
        private Path ramlPath;
        private Path outputPath;
        private String language;
        private final Options options;

        public GeneratorOptions(String[] args)
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
            outputPath = Paths.get(cmd.getOptionValue(getOutputPathOption().getOpt(), "build")).toAbsolutePath();

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
            formatter.printHelp("vrap [OPTIONS] <file.raml>", options);
        }

        public Path getRamlPath() {
            return ramlPath;
        }
        public Path getOutputPath() {
            return outputPath;
        }
        public String getLanguage() {
            return language;
        }
    }
}
