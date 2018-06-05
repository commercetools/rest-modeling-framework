package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.nodes.antlr.NodeToken;
import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * EMF adapter for {@link io.vrap.rmf.nodes.antlr.NodeTokenProvider}.
 */
class RAMLParserAdapter extends AdapterImpl implements NodeTokenProvider {
    private final ParserRuleContext parserRuleContext;

    private RAMLParserAdapter(final ParserRuleContext parserRuleContext) {
        this.parserRuleContext = parserRuleContext;
    }

    @Override
    public boolean isAdapterForType(final Object type) {
        return type == NodeTokenProvider.class;
    }

    public static final RAMLParserAdapter of(final ParserRuleContext parserRuleContext) {
        final RAMLParserAdapter adapter = new RAMLParserAdapter(parserRuleContext);
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
}
