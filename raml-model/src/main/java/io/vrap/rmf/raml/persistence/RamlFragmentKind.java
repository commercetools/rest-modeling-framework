package io.vrap.rmf.raml.persistence;

import org.eclipse.emf.ecore.EClass;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ANY_ANNOTATION_TYPE;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ANY_TYPE;

/**
 * Enumeration of the RAML typed fragment identifiers {@link #getIdentifier()}
 * and their type {@link #getType()}.
 */
public enum RamlFragmentKind {
    /**
     * A RAML api specification.
     */
    API("#%RAML 1.0", Literals.API),

    /**
     * A RAML library.
     */
    LIBRARY("Library", Literals.LIBRARY),
    /**
     * A data type declaration where the type node may be used.
     */
    DATA_TYPE("DataType", ANY_TYPE),
    /**
     * A single annotation type declaration.
     */
    ANNOTATION_TYPE_DECLARATION("AnnotationTypeDeclaration", ANY_ANNOTATION_TYPE);

    private final static Pattern HEADER_PATTERN = Pattern.compile("(#%RAML 1.0)(\\s(\\w+))?");

    private final String name;
    private final EClass type;

    RamlFragmentKind(final String name, final EClass type) {
        this.name = name;
        this.type = type;
    }

    public String getIdentifier() {
        return name;
    }

    public EClass getType() {
        return type;
    }

    /**
     * Returns the RAML fragment kind of the given identifier.
     *
     * @param identifier the RAML fragment identifier
     * @return the optional fragment kind of the identifier
     */
    public static Optional<RamlFragmentKind> of(final String identifier) {
        return Stream.of(RamlFragmentKind.values())
                .filter(fragmentKind -> fragmentKind.getIdentifier().equals(identifier))
                .findFirst();
    }

    /**
     * Returns the RAML fragment kind from the given RAML header.
     *
     * @param header the RAML file header
     * @return the optional fragment kind specified by the header
     */
    public static Optional<RamlFragmentKind> fromHeader(final String header) {
        final Matcher matcher = HEADER_PATTERN.matcher(header);

        if (matcher.matches()) {
            final String name = matcher.start(3) >= 0 ?
                    matcher.group(3) :
                    matcher.group(0);
            return of(name);
        } else {
            return Optional.empty();
        }
    }
}
