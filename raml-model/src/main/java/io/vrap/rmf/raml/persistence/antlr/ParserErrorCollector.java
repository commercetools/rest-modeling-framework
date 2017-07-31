package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.RamlError;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

public class ParserErrorCollector extends BaseErrorListener {
    private final List<RamlError> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        errors.add(RamlError.of(msg, "", line, charPositionInLine));
    }

    public List<RamlError> getErrors() {
        return errors;
    }
}
