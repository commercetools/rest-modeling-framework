package io.vrap.rmf.raml.model;

import io.vrap.rmf.nodes.antlr.NodeToken;
import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
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
        this.location = location == null ? "<unkown-location>" : location;
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
        return String.format("%s (%s,%d,%d)", message, location, line, column);
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
            final NodeTokenProvider nodeTokenProvider = (NodeTokenProvider) EcoreUtil.getExistingAdapter(eObject, NodeTokenProvider.class);
            if (nodeTokenProvider != null) {
                final NodeToken nodeToken = nodeTokenProvider.getStart();
                line = nodeToken.getLine();
                column = nodeToken.getCharPositionInLine();
                source = nodeToken.getLocation();
            }
        }
        return of(diagnostic.getMessage(), source, line, column);
    }
}
