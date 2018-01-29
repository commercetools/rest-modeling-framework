package io.vrap.rmf.raml.model;

import io.vrap.rmf.raml.persistence.antlr.RamlToken;
import io.vrap.rmf.raml.persistence.antlr.RamlTokenProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class is used to report validation results as {@link Resource.Diagnostic}.
 */
public class RamlDiagnostic implements Resource.Diagnostic {
    private final String message;
    private final String location;
    private final int line;
    private final int column;

    private RamlDiagnostic(final String message, final String location, final int line, final int column) {
        this.message = message;
        this.location = location;
        this.line = line;
        this.column = column;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "RamlDiagnostic{" +
                "message='" + message + '\'' +
                ", location='" + location + '\'' +
                ", line=" + line +
                ", column=" + column +
                '}';
    }

    public static RamlDiagnostic of(final String message, final String location, final int line, final int column) {
        return new RamlDiagnostic(message, location, line, column);
    }

    public static RamlDiagnostic of(org.eclipse.emf.common.util.Diagnostic diagnostic) {
        int line = -1;
        int column = -1;
        String source = diagnostic.getSource();
        if (diagnostic.getData().size() > 0 && diagnostic.getData().get(0) instanceof EObject) {
            final EObject eObject = (EObject) diagnostic.getData().get(0);
            final RamlTokenProvider ramlTokenProvider = (RamlTokenProvider) EcoreUtil.getExistingAdapter(eObject, RamlTokenProvider.class);
            if (ramlTokenProvider != null) {
                final RamlToken ramlToken = ramlTokenProvider.get();
                line = ramlToken.getLine();
                column = ramlToken.getCharPositionInLine();
                source = ramlToken.getLocation();
            }
        }
        return of(diagnostic.getMessage(), source, line, column);
    }
}
