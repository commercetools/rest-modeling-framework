package io.vrap.rmf.raml.generic.generator.postman;

import com.google.common.collect.Lists;

import java.net.URI;
import java.util.List;

public class UriGenModel {
    private final URI uri;

    public UriGenModel(final URI uri) {
        this.uri = uri;
    }

    public UriGenModel(final String uri) {
        this(URI.create(uri));
    }

    public URI getUri()
    {
        return uri;
    }

    public String getPath()
    {
        return uri.getPath();
    }

    public String getHost()
    {
        return uri.getHost();
    }

    public List<String> getPathElements()
    {
        return Lists.newArrayList(getUri().getPath().split("/"));
    }
}
