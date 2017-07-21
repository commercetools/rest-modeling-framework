package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.annotations.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import io.vrap.rmf.raml.persistence.typeexpressions.TypeExpressionsParser;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;

/**
 * Abstract base class for antlr based constructors.
 */
public abstract class AbstractConstructor extends RAMLBaseVisitor<Object> {
    private final Stack<Scope> scope = new Stack<>();
    private final TypeExpressionsParser typeExpressionsParser = new TypeExpressionsParser();

    /**
     * Constructs a type expression from a {@link RAMLParser.TypeFacetContext}.
     */
    @Override
    public Object visitTypeFacet(final RAMLParser.TypeFacetContext ctx) {
        final String typeExpression = ctx.SCALAR().getText();

        return typeExpressionsParser.parse(typeExpression, peekScope());
    }

    /**
     * Constructs a type {@link AnyType} or an annotation type {@link AnyAnnotationType}
     * from a type declaration {@link RAMLParser.TypeDeclarationContext}.
     */
    @Override
    public Object visitTypeDeclaration(final RAMLParser.TypeDeclarationContext ctx) {
        final BuiltinType baseType;
        final EObject superType;

        if (ctx.typeFacet().size() > 0) {
            final RAMLParser.TypeFacetContext typeFacet = ctx.typeFacet().get(0);
            superType = (EObject) visitTypeFacet(typeFacet);
            final String typeName = (String) superType.eGet(IDENTIFIABLE_ELEMENT__NAME); // TODO handle arrays
            baseType = BuiltinType.of(typeName)
                    .orElse(BuiltinType.OBJECT);
        } else {
            baseType = BuiltinType.OBJECT;
            superType = peekScope().getImportedTypeById(baseType.getName());
        }

        final EClass scopedMetaType = baseType.getScopedMetaType(peekScope());
        final EObject declaredType = EcoreUtil.create(scopedMetaType);
        final EStructuralFeature typeReference = scopedMetaType.getEStructuralFeature("type");
        declaredType.eSet(typeReference, superType);

        final String name = ctx.name.getText();
        declaredType.eSet(IDENTIFIABLE_ELEMENT__NAME, name);

        for (RAMLParser.AttributeFacetContext attributeFacet : ctx.attributeFacet()) {
            setAttribute(attributeFacet, declaredType);
        }

        return declaredType;
    }

    protected Scope pushScope(final Scope scope) {
        return this.scope.push(scope);
    }

    protected Scope popScope() {
        return this.scope.pop();
    }

    protected Scope peekScope() {
        return scope.peek();
    }

    /**
     * Sets an attribute given by the attribute facet on the given eobject.
     *
     * @param attributeFacet the attribute facet
     * @param eObject        the object to set the attribute
     */
    protected void setAttribute(final RAMLParser.AttributeFacetContext attributeFacet, final EObject eObject) {
        final EClass eClass = eObject.eClass();
        final String attributeName = attributeFacet.facet.getText();
        final EAttribute eAttribute = eClass.getEAllAttributes().stream()
                .filter(a -> a.getName().equals(attributeName))
                .findFirst()
                .orElse(null); // TODO: handle unknown attribute

        if (attributeFacet.facetValue().value != null) {
            setAttribute(eObject, eAttribute, attributeFacet.facetValue().value);
        } else {
            setAttribute(eObject, eAttribute, attributeFacet.facetValue().values);
        }
    }

    private void setAttribute(final EObject eObject, final EAttribute eAttribute, final List<Token> valueTokens) {
        final List<Object> values = valueTokens.stream()
                .map(Token::getText)
                .map(v -> EcoreUtil.createFromString(eAttribute.getEAttributeType(), v))
                .collect(Collectors.toList());

        eObject.eSet(eAttribute, values);
    }

    private void setAttribute(final EObject eObject, final EAttribute eAttribute, final Token valueToken) {
        final Object value = EcoreUtil.createFromString(eAttribute.getEAttributeType(), valueToken.getText());

        if (eAttribute.isMany()) {
            eObject.eSet(eAttribute, Collections.singletonList(value));
        } else {
            eObject.eSet(eAttribute, value);
        }
    }
}
