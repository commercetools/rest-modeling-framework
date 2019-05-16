package io.vrap.antlr.utils;

import org.antlr.v4.runtime.Vocabulary;

import java.util.HashMap;
import java.util.Map;

public interface AntlrUtils {

    /**
     * Creates a map from the literal token names of the given vocabulary to token types.
     *
     * @param vocabulary the vocabulary
     * @return a map from literal token names to token types
     */
    static Map<String, Integer> literalToTokenType(final Vocabulary vocabulary) {
        final Map<String, Integer> literalTokenTypes = new HashMap<>();
        for (int tokenType = 0; tokenType <= vocabulary.getMaxTokenType(); tokenType++) {
            final String literalName = vocabulary.getLiteralName(tokenType);
            if (literalName != null) {
                final String literalText = literalName.substring(1, literalName.length() - 1);
                literalTokenTypes.put(literalText, tokenType);
            }
        }
        return literalTokenTypes;
    }
}
