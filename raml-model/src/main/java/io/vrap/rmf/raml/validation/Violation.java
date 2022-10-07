package io.vrap.rmf.raml.validation;

import io.vrap.rmf.nodes.antlr.NodeToken;
import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.text.MessageFormat;

public class Violation {
    private final Class<DiagnosticsCreator> rule;
    private final EObject object;
    private final String messagePattern;
    private final Object[] messageArgs;
    private final Integer severity;
    private final Source source;
    private final String message;

    public Violation(Integer severity, Class<DiagnosticsCreator> rule, EObject object, String messagePattern, Object... messageArgs) {
        this.rule = rule;
        this.object = object;
        this.messagePattern = messagePattern;
        this.messageArgs = messageArgs;
        this.severity = severity;
        this.source = getSource(object);
        this.message = MessageFormat.format("{0}: {1}", rule.getSimpleName(), MessageFormat.format(messagePattern, messageArgs));
    }

    private Source getSource(EObject object) {
        final NodeTokenProvider nodeTokenProvider = (NodeTokenProvider) EcoreUtil.getExistingAdapter(object, NodeTokenProvider.class);
        if (nodeTokenProvider != null) {
            final NodeToken nodeToken = nodeTokenProvider.getStart();
            return new Source(nodeToken.getLocation(), nodeToken.getLine(), nodeToken.getCharPositionInLine());
        }
        return null;
    }

    public Class<DiagnosticsCreator> getRule() {
        return rule;
    }

    public EObject getObject() {
        return object;
    }

    public String getMessagePattern() {
        return messagePattern;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }

    public Integer getSeverity() {
        return severity;
    }

    public Source getSource() {
        return source;
    }

    public String getMessage() {
        return message;
    }
}
