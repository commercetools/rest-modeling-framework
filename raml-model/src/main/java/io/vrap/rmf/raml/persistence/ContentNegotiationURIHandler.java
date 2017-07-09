package io.vrap.rmf.raml.persistence;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * This uri handler allows to specify the accepted content type.
 */
class ContentNegotiationURIHandler extends URIHandlerImpl {
    private final String acceptedContentTypes;

    public ContentNegotiationURIHandler(final String acceptedContentTypes) {
        this.acceptedContentTypes = acceptedContentTypes;
    }

    @Override
    public InputStream createInputStream(final URI uri, final Map<?, ?> options) throws IOException {
        try
        {
            URL url = new URL(uri.toString());
            final URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Accept", acceptedContentTypes);
            int timeout = getTimeout(options);
            if (timeout != 0)
            {
                urlConnection.setConnectTimeout(timeout);
                urlConnection.setReadTimeout(timeout);
            }
            InputStream result = urlConnection.getInputStream();
            Map<Object, Object> response = getResponse(options);
            if (response != null)
            {
                response.put(URIConverter.RESPONSE_TIME_STAMP_PROPERTY, urlConnection.getLastModified());
            }
            return result;
        }
        catch (RuntimeException exception)
        {
            throw new Resource.IOWrappedException(exception);
        }

    }
}
