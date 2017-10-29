package io.vrap.rmf.raml.model.util;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.net.URL;

/**
 * This class provides functions to inflect a given word to its plural
 * and singular form..
 */
public class Inflector {
    private static ScriptEngine jsEngine;
    private static Bindings pluralizeBindings;

    static {
        final URL url = Inflector.class.getResource("/js/pluralize/pluralize.js");
        try {
            final String source = Resources.toString(url, Charsets.UTF_8);
            final ScriptEngineManager engineManager = new ScriptEngineManager();
            jsEngine = engineManager.getEngineByName("js");
            pluralizeBindings = jsEngine.createBindings();
            jsEngine.eval(source, pluralizeBindings);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the plural form of the given word.
     *
     * @param word the word
     * @return the pluralized word
     */
    public static String plural(final String word) {
        try {
            final Bindings bindings = newBindings(pluralizeBindings, "word", word);
            return (String) jsEngine.eval("pluralize.plural(word)", bindings);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the singular form of the given word.
     *
     * @param word the word
     * @return the singularized word
     */
    public static String singular(final String word) {
        try {
            final Bindings bindings = newBindings(pluralizeBindings, "word", word);
            return (String) jsEngine.eval("pluralize.singular(word)", bindings);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    private static Bindings newBindings(final Bindings bindings, final String var, final Object value) {
        final Bindings newBindings = jsEngine.createBindings();
        newBindings.putAll(bindings);
        newBindings.put(var, value);
        return newBindings;
    }
}
