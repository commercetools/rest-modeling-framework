package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.values.ProtocolsFacet;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class ProtocolFacetsValidator {
    private static final Predicate<String> VALID_PROTOCOL = Pattern.compile("(http)|(https)|(HTTP)|(HTTPS)").asPredicate();
    private final BiFunction<String, EObject, Diagnostic> errorCreator;

    public ProtocolFacetsValidator(final BiFunction<String, EObject, Diagnostic> errorCreator) {
        this.errorCreator = errorCreator;
    }

    public List<Diagnostic> validate(final ProtocolsFacet protocolsFacet) {
        final List<String> invalidProtocols = protocolsFacet.getProtocols().stream()
                .filter(VALID_PROTOCOL.negate())
                .collect(Collectors.toList());
        return invalidProtocols.stream()
                .map(invalidProtocol -> errorCreator.apply("Invalid protocol " + invalidProtocol, protocolsFacet))
                .collect(Collectors.toList());
    }
}
