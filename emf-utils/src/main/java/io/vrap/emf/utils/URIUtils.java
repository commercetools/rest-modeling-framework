package io.vrap.emf.utils;

import org.eclipse.emf.common.util.URI;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface URIUtils {

    /**
     * Normalizes the uri path of the given uri to an absolute file path if it's a file uri.
     * @param uri the uri to normalize
     * @return the normalized uri
     */
    static URI normalize(final URI uri) {
        if (uri.isFile()) {
            final Path normalized = Paths.get(uri.path()).normalize();
            final URI fileURI = URI.createFileURI(normalized.toString());
            return fileURI;
        } else {
            return uri;
        }
    }
}
