package io.vrap.rmf.raml.generic.generator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    public static void ensureDirectory(File directory) throws IOException
    {
        if (!directory.exists()) {
            Files.createDirectories(directory.toPath());
        }
    }

    public static void deleteObsoleteFiles(File outputPath, List<File> generatedFiles) throws IOException
    {
        Collection<File> files = FileUtils.listFiles(
                outputPath,
                TrueFileFilter.INSTANCE,
                FileFilterUtils.notFileFilter(
                        FileFilterUtils.and(
                                FileFilterUtils.directoryFileFilter(),
                                FileFilterUtils.nameFileFilter("vendor")
                        )
                )
        ).stream().filter(file -> !generatedFiles.contains(file)).collect(Collectors.toSet());

        for (File file : files) {
            if (file.isFile()) {
                Files.deleteIfExists(file.toPath());
            }
        }
    }
}
