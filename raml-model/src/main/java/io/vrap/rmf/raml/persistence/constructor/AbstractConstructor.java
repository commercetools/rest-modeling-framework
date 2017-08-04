package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.facets.FacetsFactory;
import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.security.SecurityFactory;
import io.vrap.rmf.raml.model.security.SecurityScheme;
import io.vrap.rmf.raml.model.security.SecuritySchemeSettings;
import io.vrap.rmf.raml.model.security.SecuritySchemeType;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;
import static io.vrap.rmf.raml.model.security.SecurityPackage.Literals.*;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

/**
 * Abstract base class for antlr based constructors.
 */
public abstract class AbstractConstructor extends AbstractScopedVisitor<Object> {
    private static final FacetsFactory FACETS_FACTORY = FacetsFactory.eINSTANCE;
    private static final TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;
    private final TypeExpressionConstructor typeExpressionConstructor = new TypeExpressionConstructor();

    public abstract EObject construct(final RAMLParser parser, final Scope scope);

    @Override
    public Object visitSecuritySchemeFacet(RAMLParser.SecuritySchemeFacetContext securitySchemeFacet) {
        final SecurityScheme securityScheme;
        if (securitySchemeFacet.securitySchemeTypeFacet() == null) {
            scope.addError("Missing type for security scheme", securitySchemeFacet.getStart());
            securityScheme = null;
        } else {
            securityScheme = SecurityFactory.eINSTANCE.createSecurityScheme();
            final String name = securitySchemeFacet.name.getText();
            securityScheme.setName(name);
            withinScope(scope.with(securityScheme), securitySchemeScope -> {
                securitySchemeFacet.attributeFacet().forEach(this::visitAttributeFacet);

                withinScope(securitySchemeScope.with(SECURITY_SCHEME__TYPE), s ->
                        securitySchemeFacet.securitySchemeTypeFacet().stream()
                                .map(this::visitSecuritySchemeTypeFacet)
                                .collect(Collectors.toList()));
                SecuritySchemeSettings securitySchemeSettings = null;
                switch (securityScheme.getType()) {
                    case OAUTH_10:
                        securitySchemeSettings = SecurityFactory.eINSTANCE.createOAuth10Settings();
                        break;
                    case OAUTH_20:
                        securitySchemeSettings = SecurityFactory.eINSTANCE.createOAuth20Settings();
                        break;
                    default:
                        if (securitySchemeFacet.securitySchemeSettingsFacet() != null) {
                            scope.addError("Settings not supported for type {0}", securityScheme.getType());
                        }
                }
                if (securitySchemeSettings != null) {
                    scope.with(SECURITY_SCHEME__SETTINGS).setValue(securitySchemeSettings, securitySchemeFacet.getStart());
                    withinScope(scope.with(securitySchemeSettings), settingsScope ->
                            securitySchemeFacet.securitySchemeSettingsFacet().stream()
                                    .map(this::visitSecuritySchemeSettingsFacet)
                                    .collect(Collectors.toList()));
                }
                return securitySchemeScope.eObject();
            });
            scope.with(SECURITY_SCHEME_CONTAINER__SECURITY_SCHEMES).setValue(securityScheme, securitySchemeFacet.getStart());
        }
        return securityScheme;
    }

    @Override
    public Object visitSecuritySchemeSettingsFacet(RAMLParser.SecuritySchemeSettingsFacetContext securitySchemeSettingsFacet) {
        securitySchemeSettingsFacet.attributeFacet().forEach(this::visitAttributeFacet);
        return scope.eObject();
    }

    @Override
    public Object visitSecuritySchemeTypeFacet(RAMLParser.SecuritySchemeTypeFacetContext securitySchemeTypeFacet) {
        final String securityTypeText = securitySchemeTypeFacet.type.getText();
        try {
            final SecuritySchemeType securitySchemeType = (SecuritySchemeType) SecurityFactory.eINSTANCE.createFromString(SECURITY_SCHEME_TYPE, securityTypeText);
            scope.setValue(securitySchemeType, securitySchemeTypeFacet.getStart());
            return securitySchemeType;
        } catch (IllegalArgumentException e) {
            scope.addError(e.getMessage(), securitySchemeTypeFacet.getStart());
            return null;
        }
    }

    @Override
    public Object visitSecuredByFacet(RAMLParser.SecuredByFacetContext securedByFacet) {
        return withinScope(scope.with(SECURED_BY_FACET__SECURED_BY), securedByScope -> {
            EList<EObject> securedBySchemes = ECollections.asEList(securedByFacet.securitySchemes.stream()
                    .map(name -> scope.getEObjectByName(name.getText()))
                    .collect(Collectors.toList()));
            scope.setValue(securedBySchemes, securedByFacet.getStart());
            return securedBySchemes;
        });
    }

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
                        annotationTypeScope.getEObjectByName(annotationTypeRef);

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

        return typeExpressionConstructor.parse(typeExpression, scope);
    }

    @Override
    public Object visitTypeDeclarationTuple(final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple) {
        final EObject declaredType = scope.getEObjectByName(typeDeclarationTuple.name.getText());
        return declaredType;
    }

    /**
     * Constructs a type {@link AnyType} or an annotation type {@link AnyAnnotationType}
     * from a type declaration {@link RAMLParser.TypeDeclarationMapContext}.
     */
    @Override
    public Object visitTypeDeclarationMap(final RAMLParser.TypeDeclarationMapContext typeDeclarationMap) {
        final EObject declaredType = scope.getEObjectByName(typeDeclarationMap.name.getText());

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
                    .map(this::visitTypedElementFacet)
                    .collect(Collectors.toList());

            return properties;
        });
    }

    @Override
    public Object visitTypedElementFacet(RAMLParser.TypedElementFacetContext typedElementFacet) {
        final EClass eType = (EClass) scope.eFeature().getEType();
        final EObject typedElement = EcoreUtil.create(eType);
        scope.setValue(typedElement, typedElementFacet.getStart());

        return withinScope(scope.with(typedElement), propertyScope ->
                super.visitTypedElementFacet(typedElementFacet));
    }


    @Override
    public Object visitTypedElementTuple(RAMLParser.TypedElementTupleContext typedeElementTuple) {
        final Token type = typedeElementTuple.type;
        final String name = typedeElementTuple.name.getText();

        final EObject propertyType = type == null ?
                scope.getEObjectByName(BuiltinType.STRING.getName()) :
                typeExpressionConstructor.parse(type.getText(), scope);
        final boolean isRequired = !name.endsWith("?");
        scope.setValue(TYPED_ELEMENT__REQUIRED, isRequired, typedeElementTuple.getStart());
        final String parsedName = isRequired ? name : name.substring(0, name.length() - 1);

        scope.setValue(IDENTIFIABLE_ELEMENT__NAME, parsedName, typedeElementTuple.getStart());
        scope.setValue(TYPED_ELEMENT__TYPE, propertyType, typedeElementTuple.getStart());

        return scope.eObject();
    }

    @Override
    public Object visitTypedElementMap(RAMLParser.TypedElementMapContext typedElementMap) {
        final String name = typedElementMap.name.getText();
        final Boolean requiredValue = typedElementMap.requiredFacet().size() == 1 ?
                Boolean.parseBoolean(typedElementMap.requiredFacet().get(0).required.getText()) : // TODO handle exception
                null;

        final String parsedName;
        if (requiredValue == null) {
            final boolean isRequired = !name.endsWith("?");
            scope.setValue(TYPED_ELEMENT__REQUIRED, isRequired, typedElementMap.getStart());
            parsedName = isRequired ? name : name.substring(0, name.length() - 1);
        } else {
            parsedName = name;
            scope.setValue(TYPED_ELEMENT__REQUIRED, requiredValue, typedElementMap.getStart());
        }

        scope.setValue(IDENTIFIABLE_ELEMENT__NAME, parsedName, typedElementMap.getStart());

        EObject typedElementType;
        if (typedElementMap.typeFacet().size() > 0) {
            final RAMLParser.TypeFacetContext typeFacet = typedElementMap.typeFacet().get(0);
            typedElementType = (EObject) withinScope(scope.with(TYPED_ELEMENT__TYPE),
                    propertyTypeScope -> visitTypeFacet(typeFacet));
        } else if (typedElementMap.propertiesFacet().size() == 1) {
            typedElementType = scope.getEObjectByName(BuiltinType.OBJECT.getName());
        } else {
            typedElementType = scope.getEObjectByName(BuiltinType.STRING.getName());
        }

        // inline type declaration
        if (typedElementMap.attributeFacet().size() > 0 || typedElementMap.propertiesFacet().size() > 0) {
            typedElementType = EcoreUtil.create(typedElementType.eClass());
            scope.addValue(INLINE_TYPE_CONTAINER__INLINE_TYPES, typedElementType);
            withinScope(scope.with(typedElementType),
                    inlineTypeDeclarationScope -> {
                            typedElementMap.attributeFacet().forEach(this::visitAttributeFacet);
                            typedElementMap.propertiesFacet().forEach(this::visitPropertiesFacet);
                            return inlineTypeDeclarationScope.eObject();
            });
        }

        typedElementMap.annotationFacet().forEach(this::visitAnnotationFacet);
        scope.setValue(TYPED_ELEMENT__TYPE, typedElementType, typedElementMap.getStart());

        return scope.eObject();
    }

    @Override
    public Object visitAttributeFacet(final RAMLParser.AttributeFacetContext attributeFacet) {
        final Object value = setAttribute(attributeFacet, scope.eObject());
        return value;
    }
}
