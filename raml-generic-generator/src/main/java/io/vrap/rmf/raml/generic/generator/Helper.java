package io.vrap.rmf.raml.generic.generator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Lists;
import io.vrap.rmf.raml.generic.generator.postman.CollectionGenerator;
import io.vrap.rmf.raml.generic.generator.postman.ItemGenModel;
import io.vrap.rmf.raml.model.types.*;
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
                        FileFilterUtils.or(
                            FileFilterUtils.and(
                                    FileFilterUtils.directoryFileFilter(),
                                    FileFilterUtils.nameFileFilter("vendor")
                            ),
                            FileFilterUtils.and(
                                    FileFilterUtils.directoryFileFilter(),
                                    FileFilterUtils.nameFileFilter(".git")
                            )
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

    public static String toJson(Instance instance) {
        String example = "";
        final ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(ObjectInstance.class, new ObjectInstanceSerializer());
        module.addSerializer(ArrayInstance.class, new InstanceSerializer());
        module.addSerializer(IntegerInstance.class, new InstanceSerializer());
        module.addSerializer(BooleanInstance.class, new InstanceSerializer());
        module.addSerializer(StringInstance.class, new InstanceSerializer());
        module.addSerializer(NumberInstance.class, new InstanceSerializer());
        mapper.registerModule(module);

        if (instance instanceof StringInstance) {
            example = ((StringInstance)instance).getValue();
        } else if (instance instanceof ObjectInstance) {
            try {
                example = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(instance);
            } catch (JsonProcessingException e) {
            }
        }

        return example;
    }

    private static class InstanceSerializer extends StdSerializer<Instance> {
        public InstanceSerializer() {
            this(null);
        }

        public InstanceSerializer(Class<Instance> t) {
            super(t);
        }

        @Override
        public void serialize(Instance value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeObject(value.getValue());
        }
    }

    private static class ObjectInstanceSerializer extends StdSerializer<ObjectInstance> {
        public ObjectInstanceSerializer() {
            this(null);
        }

        public ObjectInstanceSerializer(Class<ObjectInstance> t) {
            super(t);
        }

        @Override
        public void serialize(ObjectInstance value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            List<PropertyValue> properties = value.getValue();
            gen.writeStartObject();
            for(PropertyValue v : properties) {
                gen.writeObjectField(v.getName(), v.getValue());
            }
            gen.writeEndObject();
        }
    }
}
