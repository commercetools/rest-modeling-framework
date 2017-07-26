package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.facets.FacetsFactory;
import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.typeexpressions.TypeExpressionsParser;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

/**
 * Abstract base class for antlr based constructors.
 */
public abstract class AbstractConstructor extends AbstractScopedVisitor<Object> {
    private static final FacetsFactory FACETS_FACTORY = FacetsFactory.eINSTANCE;
    private static final TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;
    private final TypeExpressionsParser typeExpressionsParser = new TypeExpressionsParser();

    public abstract EObject construct(final RAMLParser parser, final Scope scope);

    @Override
    public Object visitAnnotationFacet(final RAMLParser.AnnotationFacetContext annotationFacet) {
        final RAMLParser.AnnotationTupleContext annotationTuple = annotationFacet.annotationTuple();
        return withinScope(scope.with(ANNOTATIONS_FACET__ANNOTATIONS), annotationsScope -> {
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
            annotationsScope.setValue(ANNOTATIONS_FACET__ANNOTATIONS, annotation, (CommonToken) annotationFacet.getStart());

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
        final EClass eClass = scope.eObject().eClass();
        final EStructuralFeature typesFeature = eClass.getEStructuralFeature(typesReferenceName);

        return withinScope(scope.with(typesFeature), typesScope -> {
            final List<Object> types = typesFacet.types.stream()
                    .map(this::visitTypeDeclarationFacet)
                    .collect(Collectors.toList());

            return types;
        });
    }

    /**
     * Constructs a type expression from a {@link RAMLParser.TypeFacetContext}.
     */
    @Override
    public Object visitTypeFacet(final RAMLParser.TypeFacetContext ctx) {
        final String typeExpression = ctx.SCALAR().getText();

        return typeExpressionsParser.parse(typeExpression, scope);
    }

    @Override
    public Object visitTypeDeclarationTuple(final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple) {
        final EObject declaredType = scope.getImportedTypeById(typeDeclarationTuple.name.getText());
        return declaredType;
    }

    /**
     * Constructs a type {@link AnyType} or an annotation type {@link AnyAnnotationType}
     * from a type declaration {@link RAMLParser.TypeDeclarationMapContext}.
     */
    @Override
    public Object visitTypeDeclarationMap(final RAMLParser.TypeDeclarationMapContext typeDeclarationMap) {
        final EObject declaredType = scope.getImportedTypeById(typeDeclarationMap.name.getText());

        return withinScope(scope.with(declaredType), typeScope -> {
            typeDeclarationMap.annotationFacet().forEach(this::visitAnnotationFacet);
            typeDeclarationMap.attributeFacet().forEach(this::visitAttributeFacet);
            typeDeclarationMap.propertiesFacet().forEach(this::visitPropertiesFacet);

            return declaredType;
        });
    }

    /**
     * Constructs properties for the given properties facet.
     *
     * @param propertiesFacet the properties facet
     * @return list of properties
     */
    @Override
    public Object visitPropertiesFacet(final RAMLParser.PropertiesFacetContext propertiesFacet) {
        return withinScope(scope.with(PROPERTIES_FACET__PROPERTIES), propertiesScope -> {
            final List<Object> properties = propertiesFacet.propertyFacets.stream()
                    .map(this::visitPropertyFacet)
                    .collect(Collectors.toList());

            return properties;
        });
    }

    @Override
    public Object visitPropertyFacet(final RAMLParser.PropertyFacetContext propertyFacet) {
        final Property property = TYPES_FACTORY.createProperty();
        scope.setValue(property, propertyFacet.getStart());

        return withinScope(scope.with(property), propertyScope ->
                super.visitPropertyFacet(propertyFacet));
    }

    @Override
    public Object visitPropertyTuple(final RAMLParser.PropertyTupleContext propertyTuple) {
        final Token type = propertyTuple.type;
        final String name = propertyTuple.name.getText();

        final EObject propertyType = type == null ?
                scope.getImportedTypeById(BuiltinType.STRING.getName()) :
                typeExpressionsParser.parse(type.getText(), scope);
        final boolean isRequired = !name.endsWith("?");
        scope.setValue(PROPERTY__REQUIRED, isRequired, propertyTuple.getStart());
        final String parsedName = isRequired ? name : name.substring(0, name.length() - 1);

        scope.setValue(PROPERTY__NAME, parsedName, propertyTuple.getStart());
        scope.setValue(PROPERTY__TYPE, propertyType, propertyTuple.getStart());

        return scope.eObject();
    }

    @Override
    public Object visitPropertyMap(final RAMLParser.PropertyMapContext propertyMap) {
        final String name = propertyMap.name.getText();
        final Boolean requiredValue = propertyMap.requiredFacet().size() == 1 ?
                Boolean.parseBoolean(propertyMap.requiredFacet().get(0).required.getText()) : // TODO handle exception
                null;

        final String parsedName;
        if (requiredValue == null) {
            final boolean isRequired = !name.endsWith("?");
            scope.setValue(PROPERTY__REQUIRED, isRequired, propertyMap.getStart());
            parsedName = isRequired ? name : name.substring(0, name.length() - 1);
        } else {
            parsedName = name;
            scope.setValue(PROPERTY__REQUIRED, requiredValue, propertyMap.getStart());
        }

        scope.setValue(PROPERTY__NAME, parsedName, propertyMap.getStart());

        EObject propertyType;
        if (propertyMap.typeFacet().size() > 0) {
            final RAMLParser.TypeFacetContext typeFacet = propertyMap.typeFacet().get(0);
            propertyType = (EObject) withinScope(scope.with(PROPERTY__TYPE),
                    propertyTypeScope -> visitTypeFacet(typeFacet));
        } else if (propertyMap.propertiesFacet().size() == 1) {
            propertyType = scope.getImportedTypeById(BuiltinType.OBJECT.getName());
        } else {
            propertyType = scope.getImportedTypeById(BuiltinType.STRING.getName());
        }

        // inline type declaration
        if (propertyMap.attributeFacet().size() > 0) {
            propertyType = EcoreUtil.create(propertyType.eClass());
            withinScope(scope.with(propertyType),
                    inlineTypeDeclarationScope ->
                            propertyMap.attributeFacet().stream()
                                    .map(this::visitAttributeFacet)
                                    .collect(Collectors.toList()));
        }

        propertyMap.annotationFacet().forEach(this::visitAnnotationFacet);
        scope.setValue(PROPERTY__TYPE, propertyType, propertyMap.getStart());

        return scope.eObject();
    }

    @Override
    public Object visitAttributeFacet(final RAMLParser.AttributeFacetContext attributeFacet) {
        final Object value = setAttribute(attributeFacet, scope.eObject());
        return value;
    }
}
