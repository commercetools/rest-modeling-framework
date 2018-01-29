package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.types.ProtocolsFacet;
import org.eclipse.emf.common.util.Diagnostic;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class ProtocolFacetsValidator implements DiagnosticsCreator {
    private static final Predicate<String> VALID_PROTOCOL = Pattern.compile("(http)|(https)|(HTTP)|(HTTPS)").asPredicate();


    public List<Diagnostic> validate(final ProtocolsFacet protocolsFacet) {
        final List<String> invalidProtocols = protocolsFacet.getProtocols().stream()
                .filter(VALID_PROTOCOL.negate())
                .collect(Collectors.toList());
        return invalidProtocols.stream()
                .map(invalidProtocol -> error(protocolsFacet, "Invalid protocol {0}", invalidProtocol))
                .collect(Collectors.toList());
    }
}
