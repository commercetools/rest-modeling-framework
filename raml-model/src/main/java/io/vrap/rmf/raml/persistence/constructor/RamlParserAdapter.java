package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.nodes.antlr.NodeToken;
import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * EMF adapter for {@link io.vrap.rmf.nodes.antlr.NodeTokenProvider}.
 */
public class RamlParserAdapter extends AdapterImpl implements NodeTokenProvider {
    private final ParserRuleContext parserRuleContext;

    private RamlParserAdapter(final ParserRuleContext parserRuleContext) {
        this.parserRuleContext = parserRuleContext;
    }

    @Override
    public boolean isAdapterForType(final Object type) {
        return type == NodeTokenProvider.class;
    }

    public static final RamlParserAdapter of(final ParserRuleContext parserRuleContext) {
        final RamlParserAdapter adapter = new RamlParserAdapter(parserRuleContext);
        return adapter;
    }

    @Override
    public NodeToken getStart() {
        return (NodeToken) parserRuleContext.getStart();
    }

    @Override
    public NodeToken getStop() {
        return (NodeToken) parserRuleContext.getStop();
    }

    @Override
    public NodeTokenProvider copy() {
        return null;
    }

    public ParserRuleContext getParserRuleContext() {
        return parserRuleContext;
    }
}
