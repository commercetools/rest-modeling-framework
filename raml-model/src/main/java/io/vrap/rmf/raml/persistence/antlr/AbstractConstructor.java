package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.facets.FacetsFactory;
import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import io.vrap.rmf.raml.persistence.typeexpressions.TypeExpressionsParser;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

/**
 * Abstract base class for antlr based constructors.
 */
public abstract class AbstractConstructor extends RAMLBaseVisitor<Object> {
    private static final FacetsFactory FACETS_FACTORY = FacetsFactory.eINSTANCE;
    private static final TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;
    private final Stack<Scope> scope = new Stack<>();
    private final TypeExpressionsParser typeExpressionsParser = new TypeExpressionsParser();

    @Override
    public Object visitAnnotationFacet(final RAMLParser.AnnotationFacetContext annotationFacet) {
        final RAMLParser.AnnotationTupleContext annotationTuple = annotationFacet.annotationTuple();
        return withinScope(peekScope().with(ANNOTATIONS_FACET__ANNOTATIONS), annotationsScope -> {
            final Annotation annotation;
            if (annotationTuple != null) {
                annotation = TYPES_FACTORY.createAnnotation();

                final String annotationTypeRef = annotationTuple.ANNOTATION_TYPE_REF().getText();
                final Scope annotationTypeScope = annotationsScope.with(ANNOTATION__TYPE);
                final AnyAnnotationType annotationType = (AnyAnnotationType)
                        annotationTypeScope.getImportedTypeById(annotationTypeRef);

                final StringInstance value = FACETS_FACTORY.createStringInstance();
                value.setValue(annotationTuple.value.getText());

                annotation.setType(annotationType);
                annotation.setValue(value);

            } else {
                annotation = null;
            }
            annotationsScope.setValue(annotation);

            return annotation;

        });
    }

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

        return withinScope(peekScope().with(typesFeature), typesScope -> {
            final List<Object> types = typesScope.setValue(typesFacet.types.stream()
                    .map(this::visitTypeDeclaration)
                    .collect(Collectors.toList()));

            return ECollections.asEList(types);
        });
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
    public Object visitTypeDeclaration(final RAMLParser.TypeDeclarationContext typeDeclaration) {
        final BuiltinType baseType;
        final EObject superType;

        if (typeDeclaration.typeFacet().size() > 0) {
            final RAMLParser.TypeFacetContext typeFacet = typeDeclaration.typeFacet().get(0);
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
        withinScope(peekScope().with(declaredType), typeScope -> {
            final EStructuralFeature typeReference = scopedMetaType.getEStructuralFeature("type");
            typeScope.with(typeReference).setValue(superType);

            final String name = typeDeclaration.name.getText();
            typeScope.with(IDENTIFIABLE_ELEMENT__NAME).setValue(name);


            typeDeclaration.annotationFacet().forEach(this::visitAnnotationFacet);
            typeDeclaration.attributeFacet().forEach(this::visitAttributeFacet);
            typeDeclaration.propertiesFacet().forEach(this::visitPropertiesFacet);

            return declaredType;
        });

        return declaredType;
    }

    /**
     * Constructs properties for the given properties facet.
     *
     * @param propertiesFacet the properties facet
     * @return list of properties
     */
    @Override
    public Object visitPropertiesFacet(final RAMLParser.PropertiesFacetContext propertiesFacet) {
        return withinScope(peekScope().with(PROPERTIES_FACET__PROPERTIES), propertiesScope -> {
            final List<Object> properties = propertiesFacet.propertyFacets.stream()
                    .map(this::visitPropertyFacet)
                    .collect(Collectors.toList());

            return properties;
        });
    }

    @Override
    public Object visitPropertyFacet(final RAMLParser.PropertyFacetContext propertyFacet) {
        final Property property = TYPES_FACTORY.createProperty();
        peekScope().setValue(property);

        return withinScope(peekScope().with(property), propertyScope -> {
            EObject propertyType;
            if (propertyFacet.propertyTuple() != null) {
                final Token type = propertyFacet.propertyTuple().type;
                propertyType = type == null ?
                        peekScope().getImportedTypeById(BuiltinType.STRING.getName()) :
                        peekScope().getImportedTypeById(type.getText());
            } else {
                final RAMLParser.PropertyMapContext propertyMap = propertyFacet.propertyMap();

                final String name = propertyMap.name.getText();
                final Boolean requiredValue = propertyMap.requiredFacet().size() == 1 ?
                        Boolean.valueOf(propertyMap.requiredFacet().get(0).getText()) : // TODO handle exception
                        null;

                final String parsedName;
                if (requiredValue == null) {
                    final boolean isRequired = !name.endsWith("?");
                    propertyScope.with(PROPERTY__REQUIRED).setValue(isRequired);
                    parsedName = isRequired ? name : name.substring(0, name.length() - 1);
                } else {
                    parsedName = name;
                    propertyScope.with(PROPERTY__REQUIRED).setValue(requiredValue);
                }

                propertyScope.with(PROPERTY__NAME).setValue(parsedName);

                if (propertyMap.typeFacet().size() > 0) {
                    final RAMLParser.TypeFacetContext typeFacet = propertyMap.typeFacet().get(0);
                    propertyType = (EObject) withinScope(peekScope().with(PROPERTY__TYPE),
                            propertyTypeScope -> visitTypeFacet(typeFacet));
                } else if (propertyMap.propertiesFacet().size() == 1) {
                    propertyType = peekScope().getImportedTypeById(BuiltinType.OBJECT.getName());
                } else {
                    propertyType = peekScope().getImportedTypeById(BuiltinType.STRING.getName());
                }

                // inline type declaration
                if (propertyMap.attributeFacet().size() > 0) {
                    propertyType = EcoreUtil.create(propertyType.eClass());
                    withinScope(propertyScope.with(propertyType),
                            inlineTypeDeclarationScope ->
                                    propertyMap.attributeFacet().stream()
                                            .map(this::visitAttributeFacet)
                                            .collect(Collectors.toList()));
                }

                propertyMap.annotationFacet().forEach(this::visitAnnotationFacet);
            }
            propertyScope.with(PROPERTY__TYPE).setValue(propertyType);

            return property;
        });
    }

    protected <T> T withinScope(final Scope scope, final Function<Scope, T> within) {
        pushScope(scope);

        T value = within.apply(scope);

        popScope();

        return value;
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
