package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.persistence.antlr.RAMLBaseVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.function.Function;

/**
 * Abstract base visitor which provides scoping.
 */
abstract class AbstractScopedVisitor<T> extends RAMLBaseVisitor<T> {
    protected Scope scope;

    public  <T> T withinScope(final Scope scope, final Function<Scope, T> within) {
        pushScope(scope);

        T value = within.apply(this.scope);

        this.scope = popScope();

        return value;
    }

    private Scope pushScope(final Scope scope) {
        return this.scope = scope;
    }

    private Scope popScope() {
        return scope.getParent();
    }

    protected <T extends EObject> T create(final EClass eClass, final ParserRuleContext ruleContext) {
        final T newEObject = (T) EcoreUtil.create(eClass);
        final RamlParserAdapter adapter = RamlParserAdapter.of(ruleContext);
        newEObject.eAdapters().add(adapter);
        return newEObject;
    }
}
