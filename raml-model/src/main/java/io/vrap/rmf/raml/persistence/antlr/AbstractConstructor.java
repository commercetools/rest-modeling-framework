package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.annotations.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import io.vrap.rmf.raml.persistence.typeexpressions.TypeExpressionsParser;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
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
     * Constructor types or annotation types from the given {@link RAMLParser.TypesFacetContext}.
     *
     * @param typesFacet the types/annotation types facet
     * @return list of types/annotation types
     */
    @Override
    public Object visitTypesFacet(final RAMLParser.TypesFacetContext typesFacet) {
        final String typesReferenceName = typesFacet.facet.getText();
        final EClass eClass = peekScope().eObject().eClass();
        final EStructuralFeature typesFeature = eClass.getEStructuralFeature(typesReferenceName);

        final Scope typesScope = pushScope(peekScope().with(typesFeature));

        final List<Object> types = typesFacet.types.stream()
                .map(this::visitTypeDeclaration)
                .collect(Collectors.toList());

        final EList<Object> value = ECollections.asEList(types);
        typesScope.setValue(value);

        popScope();

        return value;
    }

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

    @Override
    public Object visitAttributeFacet(final RAMLParser.AttributeFacetContext attributeFacet) {
        final Object value = setAttribute(attributeFacet, peekScope().eObject());
        return value;
    }

    /**
     * Sets an attribute given by the attribute facet on the given eobject.
     *
     * @param attributeFacet the attribute facet
     * @param eObject        the object to set the attribute
     */
    protected Object setAttribute(final RAMLParser.AttributeFacetContext attributeFacet, final EObject eObject) {
        final EClass eClass = eObject.eClass();
        final String attributeName = attributeFacet.facet.getText();
        final EAttribute eAttribute = eClass.getEAllAttributes().stream()
                .filter(a -> a.getName().equals(attributeName))
                .findFirst()
                .orElse(null);

        final Object value;
        if (eAttribute == null) {
            peekScope().addError("Unknown attribute {0}", attributeName);
            value = null;
        } else {
            value = attributeFacet.facetValue().value == null ?
                    attributeFacet.facetValue().values :
                    attributeFacet.facetValue().value;

            if (attributeFacet.facetValue().value != null) {
                setAttribute(eObject, eAttribute, attributeFacet.facetValue().value);
            } else {
                setAttribute(eObject, eAttribute, attributeFacet.facetValue().values);
            }
        }
        return value;
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
