package io.vrap.rmf.raml.generic.generator;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.generic.generator.postman.CollectionGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<URL> getTemplatesFromDirectory(final String dir)
    {
        final List<URL> files = Lists.newArrayList();
        try {
            URI uri = CollectionGenerator.class.getClassLoader().getResource(dir).toURI();
            Path myPath;
            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                myPath = fileSystem.getPath(dir);
            } else {
                myPath = Paths.get(uri);
            }
            Stream<Path> walk = Files.walk(myPath, 10);
            for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
                final String name = it.next().toString();
                final File f = new File(name);
                if (f.getName().endsWith(".stg")) {
                    String resourcePath = f.getPath().substring(f.getPath().indexOf(dir));
                    files.add(CollectionGenerator.class.getClassLoader().getResource(resourcePath));
                }
            }
        } catch (Exception e) {}
        return files;
    }
}
