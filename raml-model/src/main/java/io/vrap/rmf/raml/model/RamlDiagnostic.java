package io.vrap.rmf.raml.model;

import io.vrap.rmf.nodes.antlr.NodeToken;
import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.constructor.RamlParserAdapter;
import io.vrap.rmf.raml.validation.Source;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is used to report validation results as {@link Resource.Diagnostic}.
 */
public class RamlDiagnostic implements Resource.Diagnostic {
    private final int severity;
    private final String message;
    private final String location;
    private final int line;
    private final int column;
    private final List<?> data;

    private RamlDiagnostic(final int severity, final String message, final String location, final int line, final int column) {
        this(severity, message, location, line, column, Collections.emptyList());
    }

    private RamlDiagnostic(final int severity, final String message, final String location, final int line, final int column, final List<?> data) {
        this.severity = severity;
        this.message = message;
        this.location = location == null ? "<unkown-location>" : location;
        this.line = line;
        this.column = column;
        this.data = data;
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
        return line >=0 ? line + 1 : line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public int getSeverity() {
        return severity;
    }

    public List<?> getData() { return data; }

    @Override
    public String toString() {
        return String.format("%s (%s,%d,%d)", message, location, getLine(), column);
    }

    public static RamlDiagnostic of(final String message, final String location, final int line, final int column) {
        return new RamlDiagnostic(Diagnostic.ERROR, message, location, line, column);
    }
    public static RamlDiagnostic of(final int severity, final String message, final String location, final int line, final int column) {
        return new RamlDiagnostic(severity, message, location, line, column);
    }

    public static RamlDiagnostic of(final int severity, final String message, final String location, final int line, final int column, final List<?> data) {
        return new RamlDiagnostic(severity, message, location, line, column, data);
    }

    public static RamlDiagnostic of(org.eclipse.emf.common.util.Diagnostic diagnostic) {
        int line = -1;
        int column = -1;
        String source = diagnostic.getSource();
        if (diagnostic.getData().size() > 0 && diagnostic.getData().get(0) instanceof EObject) {
            final EObject eObject = (EObject) diagnostic.getData().get(0);
            final NodeTokenProvider nodeTokenProvider = (NodeTokenProvider) EcoreUtil.getExistingAdapter(eObject, NodeTokenProvider.class);
            if (nodeTokenProvider != null) {
                final NodeToken nodeToken;
                if (nodeTokenProvider instanceof RamlParserAdapter && ((RamlParserAdapter) nodeTokenProvider)
                        .getParserRuleContext() instanceof RAMLParser.TypeDeclarationMapContext) {
                    nodeToken = ((RamlParserAdapter) nodeTokenProvider)
                        .getParserRuleContext()
                        .children
                        .stream()
                        .filter(parseTree -> parseTree instanceof TerminalNode)
                        .findFirst()
                        .map(parseTree -> ((TerminalNode) parseTree).getSymbol() instanceof NodeToken ? (NodeToken)((TerminalNode) parseTree).getSymbol() : null)
                        .orElse(nodeTokenProvider.getStart());
                } else {
                    nodeToken = nodeTokenProvider.getStart();
                }

                line = nodeToken.getLine();
                column = nodeToken.getCharPositionInLine();
                source = nodeToken.getLocation();
            }
        }
        return of(diagnostic.getSeverity(), diagnostic.getMessage(), source, line, column, diagnostic.getData() == null ? Collections.emptyList() : diagnostic.getData() );
    }
}
