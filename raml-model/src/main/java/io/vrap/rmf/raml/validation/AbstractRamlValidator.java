package io.vrap.rmf.raml.validation;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.InternalEObject;

import java.util.Map;

/**
 * Abstract base class for validators.
 */
abstract class AbstractRamlValidator implements RamlValidator {

    @Override
    public final boolean validate(final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return validate(eObject.eClass(), eObject, diagnostics, context);
    }

    @Override
    public boolean validate(final EDataType eDataType, final Object value,
                            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return true;
    }

    /**
     * Extracts the name from the given proxy (proxy.eIsProxy() == true)
     * @param proxy the proxy EObject
     * @return the name extracted from the proxy or null
     */
    protected String getNameFromProxy(final EObject proxy) {
        final String uriFragment = ((InternalEObject)proxy).eProxyURI().fragment();
        final String[] path = uriFragment.split("/");
        if (path.length == 3) {
            return path[2];
        }
        return null;
    }
}
