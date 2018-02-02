package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.persistence.antlr.ParserRuleContextProvider;
import io.vrap.rmf.raml.persistence.antlr.RamlToken;
import io.vrap.rmf.raml.persistence.antlr.RamlTokenProvider;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * EMF adapter for {@link RamlTokenProvider}.
 */
class RAMLParserAdapter extends AdapterImpl implements RamlTokenProvider, ParserRuleContextProvider {
    private final ParserRuleContext parserRuleContext;

    private RAMLParserAdapter(final ParserRuleContext parserRuleContext) {
        this.parserRuleContext = parserRuleContext;
    }

    @Override
    public boolean isAdapterForType(final Object type) {
        return type == RamlTokenProvider.class;
    }

    /**
     * @return the raml token associated with the adaptee
     */
    public RamlToken getToken() {
        return (RamlToken) parserRuleContext.getStart();
    }

    @Override
    public ParserRuleContext getParserRuleContext() {
        return parserRuleContext;
    }

    public static final RAMLParserAdapter of(final ParserRuleContext parserRuleContext) {
        final RAMLParserAdapter adapter = new RAMLParserAdapter(parserRuleContext);
        return adapter;
    }
}
