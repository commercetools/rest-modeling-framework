package io.vrap.rmf.raml.model;

import org.eclipse.emf.ecore.resource.Resource;

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
}
