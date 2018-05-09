package io.vrap.rmf.raml.persistence.constructor;

import com.google.common.base.Strings;
import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.model.responses.Body;
import io.vrap.rmf.raml.model.responses.Response;
import io.vrap.rmf.raml.model.security.*;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.values.RegExp;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.NAMED_ELEMENT__NAME;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.*;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.*;
import static io.vrap.rmf.raml.model.responses.ResponsesPackage.Literals.*;
import static io.vrap.rmf.raml.model.security.SecurityPackage.Literals.*;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

public abstract class BaseConstructor extends AbstractScopedVisitor<Object> {
    private final InstanceConstructor instanceConstructor = new InstanceConstructor();
    private final TypeExpressionResolver typeExpressionResolver = new TypeExpressionResolver();

    @Override
    public Object visitSecuredBy(RAMLParser.SecuredByContext ctx) {
        final SecuredBy securedBy = create(SECURED_BY, ctx);
        scope.setValue(securedBy, ctx.getStart());

        final SecurityScheme scheme = (SecurityScheme) scope.getEObjectByName(ctx.name.getText(), SECURITY_SCHEME);
        securedBy.setScheme(scheme);

        if (ctx.parameters != null) {
            instanceConstructor.withinScope(scope.with(securedBy, SECURED_BY__PARAMETERS), securedByParametersScope -> {
                final ObjectInstance parameters = (ObjectInstance) instanceConstructor.visitObjectInstance(ctx.parameters);
                return  parameters;
            });
        }

        return securedBy;
    }

    @Override
    public Object visitEnumFacet(RAMLParser.EnumFacetContext enumFacet) {
        return instanceConstructor.withinScope(scope.with(ANY_TYPE_FACET__ENUM), enumScope ->
                enumFacet.instance().stream()
                        .map(instanceConstructor::visitInstance)
                        .collect(Collectors.toList()));
    }

    @Override
    public Object visitInstance(RAMLParser.InstanceContext instance) {
        return instanceConstructor.withinScope(scope, instanceScope ->
            instanceConstructor.visitInstance(instance));
    }

    @Override
    public Object visitDefaultFacet(RAMLParser.DefaultFacetContext defaultFacet) {
        return instanceConstructor.withinScope(scope.with(ANY_TYPE_FACET__DEFAULT), defaultScope ->
                instanceConstructor.visitInstance(defaultFacet.instance()));
    }

    @Override
    public Object visitExampleFacet(RAMLParser.ExampleFacetContext exampleFacet) {
        final Example example = (Example)visitExample(exampleFacet.example());
        example.setName("");
        return withinScope(scope.with(ANY_TYPE__EXAMPLES), exampleScope -> {
            exampleScope.setValue(example, exampleFacet.getStart());
            return example;
        });
    }

    @Override
    public Object visitExample(RAMLParser.ExampleContext ctx) {
        final Example example = create(EXAMPLE, ctx);

        return withinScope(scope.with(example), exampleScope -> {
            ctx.displayNameFacet().forEach(this::visitDisplayNameFacet);
            ctx.descriptionFacet().forEach(this::visitDescriptionFacet);
            ctx.strictFacet().forEach(this::visitStrictFacet);
            ctx.annotationFacet().forEach(this::visitAnnotationFacet);

            if (ctx.strictFacet().isEmpty()) {
                BooleanInstance strict = create(BOOLEAN_INSTANCE, ctx);
                strict.setValue(true);
                example.setStrict(strict);
            }

            instanceConstructor.withinScope(scope.with(example, VALUE_INSTANCE_FACET__VALUE), exampleValueScope ->
                    instanceConstructor.visitBaseInstance(ctx.value)
            );

            return example;
        });
    }

    @Override
    public Object visitStrictFacet(RAMLParser.StrictFacetContext ctx) {
        return instanceConstructor.withinScope(scope.with(STRICT_FACET__STRICT), strictScope -> {
            final Instance strict = instanceConstructor.visitAnnotatedBooleanInstance(ctx.annotatedBooleanInstance());
            return strictScope.setValue(strict, ctx.getStart());
        });
    }

    @Override
    public Object visitDescriptionFacet(RAMLParser.DescriptionFacetContext ctx) {
        return instanceConstructor.withinScope(scope.with(DESCRIPTION_FACET__DESCRIPTION), descriptionScope -> {
            final Instance description = instanceConstructor.visitAnnotatedStringInstance(ctx.annotatedStringInstance());
            return descriptionScope.setValue(description, ctx.getStart());
        });
    }

    @Override
    public Object visitDisplayNameFacet(RAMLParser.DisplayNameFacetContext ctx) {
        return instanceConstructor.withinScope(scope.with(DISPLAY_NAME_FACET__DISPLAY_NAME), displayNameScope -> {
            final Instance displayName = instanceConstructor.visitAnnotatedStringInstance(ctx.annotatedStringInstance());
            return displayNameScope.setValue(displayName, ctx.getStart());
        });
    }

    @Override
    public Object visitExamplesFacet(RAMLParser.ExamplesFacetContext examplesFacet) {
        return withinScope(scope.with(ANY_TYPE__EXAMPLES), examplesScope ->
                ECollections.asEList(examplesFacet.namedExample().stream()
                        .map(this::visitNamedExample)
                        .collect(Collectors.toList()))
        );
    }

    @Override
    public Object visitNamedExample(RAMLParser.NamedExampleContext namedExample) {
        final Example example = (Example)visitExample(namedExample.example());
        example.setName(namedExample.name.getText());
        scope.setValue(example, namedExample.getStart());
        return example;
    }

    public abstract EObject construct(final RAMLParser parser, final Scope scope);

    @Override
    public Object visitTraitsFacet(RAMLParser.TraitsFacetContext traitsFacet) {
        return withinScope(scope.with(TYPE_CONTAINER__TRAITS), traitsScope ->
                traitsFacet.traitFacet().stream()
                        .map(this::visitTraitFacet)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Object visitTraitFacet(RAMLParser.TraitFacetContext traitFacet) {
        final Trait trait = (Trait) scope.getEObjectByName(traitFacet.name.getText());
        return withinScope(scope.with(trait), traitScope -> {
            traitFacet.methodBaseFacet().forEach(this::visitMethodBaseFacet);
            return trait;
        });
    }

    @Override
    public Object visitIsFacet(RAMLParser.IsFacetContext isFacet) {
        return withinScope(scope.with(APPLY_TRAITS_FACET__IS), isScope ->
                isFacet.traitApplication().stream()
                        .map(this::visitTraitApplication)
                        .collect(Collectors.toList()));

    }

    @Override
    public Object visitTraitApplication(RAMLParser.TraitApplicationContext ctx) {
        final TraitApplication traitApplication = create(TRAIT_APPLICATION, ctx);
        scope.setValue(traitApplication, ctx.getStart());
        final String traitName = ctx.id().getText();
        final Trait trait = (Trait) scope.with(TRAIT_APPLICATION__TRAIT).getEObjectByName(traitName);
        traitApplication.setTrait(trait);
        return withinScope(scope.with(traitApplication, PARAMETERIZED_APPLICATION__PARAMETERS),
                argumentsScope -> ctx.argument().stream()
                        .map(this::visitArgument)
                        .collect(Collectors.toList()));
    }

    @Override
    public Object visitArgument(RAMLParser.ArgumentContext ctx) {
        final Parameter traitParameter = create(PARAMETER, ctx);
        scope.setValue(traitParameter, ctx.getStart());

        traitParameter.setName(ctx.name.getText());
        withinScope(scope.with(traitParameter, PARAMETER__VALUE),
                valueScope -> this.visitInstance(ctx.instance()));

        return traitParameter;
    }

    @Override
    public Object visitSecuritySchemesFacet(final RAMLParser.SecuritySchemesFacetContext ctx) {
        return withinScope(scope.with(SECURITY_SCHEME_CONTAINER__SECURITY_SCHEMES),
                scope -> super.visitSecuritySchemesFacet(ctx));
    }

    @Override
    public Object visitSecuritySchemeFacet(RAMLParser.SecuritySchemeFacetContext securitySchemeFacet) {
        final SecurityScheme securityScheme = (SecurityScheme) scope.getEObjectByName(securitySchemeFacet.name.getText());
            return withinScope(scope.with(securityScheme), securitySchemeScope -> {
                securitySchemeFacet.attributeFacet().forEach(this::visitAttributeFacet);
                securitySchemeFacet.descriptionFacet().forEach(this::visitDescriptionFacet);
                securitySchemeFacet.displayNameFacet().forEach(this::visitDisplayNameFacet);
                securitySchemeFacet.describedByFacet().forEach(this::visitDescribedByFacet);

                withinScope(securitySchemeScope.with(SECURITY_SCHEME__TYPE), s ->
                        securitySchemeFacet.securitySchemeTypeFacet().stream()
                                .map(this::visitSecuritySchemeTypeFacet)
                                .collect(Collectors.toList()));
                SecuritySchemeSettings securitySchemeSettings = null;
                switch (securityScheme.getType()) {
                    case OAUTH_10:
                        securitySchemeSettings = create(OAUTH10_SETTINGS, securitySchemeFacet);
                        break;
                    case OAUTH_20:
                        securitySchemeSettings = create(OAUTH20_SETTINGS, securitySchemeFacet);
                        break;
                    default:
                        if (securitySchemeFacet.securitySchemeSettingsFacet().size() > 0) {
                            scope.addError("Settings not supported for type {0} at {0}",
                                    securityScheme.getType(), securitySchemeFacet.getStart());
                        }
                }
                if (securitySchemeSettings != null) {
                    scope.with(SECURITY_SCHEME__SETTINGS).setValue(securitySchemeSettings, securitySchemeFacet.getStart());
                    withinScope(scope.with(securitySchemeSettings), settingsScope ->
                            securitySchemeFacet.securitySchemeSettingsFacet().stream()
                                    .map(this::visitSecuritySchemeSettingsFacet)
                                    .collect(Collectors.toList()));
                }
                return securitySchemeScope.getEObject();
            });
    }

    @Override
    public Object visitDescribedByFacet(RAMLParser.DescribedByFacetContext describedByFacet) {
        final SecuritySchemeDescription securitySchemeDescription = create(SECURITY_SCHEME_DESCRIPTION, describedByFacet);
        scope.with(SECURITY_SCHEME__DESCRIBED_BY).setValue(securitySchemeDescription, describedByFacet.getStart());

        return withinScope(scope.with(securitySchemeDescription), securitySchemeDescriptionScope -> {
            describedByFacet.headersFacet().forEach(this::visitHeadersFacet);
            describedByFacet.responsesFacet().forEach(this::visitResponsesFacet);

            return null;
        });
    }

    @Override
    public Object visitResponsesFacet(RAMLParser.ResponsesFacetContext responsesFacetContext) {
        return withinScope(scope.with(RESPONSES_FACET__RESPONSES), responsesScope -> {
            final List<Object> responses = ECollections.asEList(responsesFacetContext.responseFacet().stream()
                    .map(this::visitResponseFacet)
                    .collect(Collectors.toList()));

            return responses;
        });
    }

    @Override
    public Object visitResponseFacet(RAMLParser.ResponseFacetContext responseFacet) {
        final Response response = create(RESPONSE, responseFacet);
        scope.setValue(response, responseFacet.getStart());
        response.setStatusCode(responseFacet.statusCode.getText());
        return withinScope(scope.with(response), responseScope -> {
            responseFacet.attributeFacet().forEach(this::visitAttributeFacet);
            responseFacet.descriptionFacet().forEach(this::visitDescriptionFacet);
            responseFacet.headersFacet().forEach(this::visitHeadersFacet);

            responseFacet.bodyFacet().forEach(this::visitBodyFacet);

            return response;
        });
    }

    @Override
    public Object visitBodyFacet(final RAMLParser.BodyFacetContext ctx) {
        return withinScope(scope.with(BODY_CONTAINER__BODIES),
                bodiesScope -> super.visitBodyFacet(ctx));
    }

    @Override
    public Object visitBodyContentTypeFacet(final RAMLParser.BodyContentTypeFacetContext bodyContentType) {
        final RAMLParser.BodyFacetsContext bodyFacets = bodyContentType.bodyFacets();

        final Body body;
        if (bodyFacets != null) {
            body = (Body) visitBodyFacets(bodyFacets);
        } else {
            body = create(BODY, bodyContentType);
            scope.setValue(body, bodyContentType.getStart());
        }
        if (bodyContentType.contentType != null) {
            body.getContentTypes().add(bodyContentType.contentType.getText());
        }
        return body;
    }

    @Override
    public Object visitBodyFacets(final RAMLParser.BodyFacetsContext bodyFacet) {
        final Body body = create(BODY, bodyFacet);
        scope.setValue(body, bodyFacet.getStart());

        return withinScope(scope.with(body), bodyScope -> {
            AnyType type = withinScope(scope.with(TYPED_ELEMENT__TYPE),
                    typedElementTypeScope -> {
                        AnyType anyType = null;
                        if (bodyFacet.typeFacet().size() == 1) {
                            anyType = (AnyType) visitTypeFacet(bodyFacet.typeFacet(0));
                        } else if (bodyFacet.propertiesFacet().size() == 1) {
                            anyType = BuiltinType.OBJECT.getType(scope.getResourceSet());
                        }
                        if (anyType == null) {
                            anyType = BuiltinType.ANY.getType(scope.getResourceSet());
                        }
                        return anyType;
                    });
            // inline type declaration
            final boolean isInlineTypeDeclaration =
                    bodyFacet.attributeFacet().size() > 0 || bodyFacet.propertiesFacet().size() > 0 ||
                            bodyFacet.exampleFacet().size() > 0 || bodyFacet.examplesFacet().size() > 0 ||
                            bodyFacet.defaultFacet().size() > 0 || bodyFacet.enumFacet().size() > 0 ||
                            bodyFacet.descriptionFacet().size() > 0 ||
                            bodyFacet.itemsFacet().size() > 0;
            if (isInlineTypeDeclaration) {
                type = inlineTypeDeclaration(type, bodyScope, bodyFacet);
                withinScope(scope.with(type),
                        inlineTypeDeclarationScope -> {
                            bodyFacet.attributeFacet().forEach(this::visitAttributeFacet);
                            bodyFacet.descriptionFacet().forEach(this::visitDescriptionFacet);
                            bodyFacet.propertiesFacet().forEach(this::visitPropertiesFacet);
                            bodyFacet.exampleFacet().forEach(this::visitExampleFacet);
                            bodyFacet.examplesFacet().forEach(this::visitExamplesFacet);
                            bodyFacet.defaultFacet().forEach(this::visitDefaultFacet);
                            bodyFacet.enumFacet().forEach(this::visitEnumFacet);
                            bodyFacet.itemsFacet().forEach(this::visitItemsFacet);

                            return inlineTypeDeclarationScope.getEObject();
                        });
            }
            bodyScope.with(TYPED_ELEMENT__TYPE).setValue(type, bodyFacet.getStart());

            bodyFacet.annotationFacet().forEach(this::visitAnnotationFacet);

            return body;
        });
    }

    private AnyType inlineTypeDeclaration(final AnyType type, final Scope scope, final ParserRuleContext ruleContext) {
        if (type.isInlineType()) {
            return type;
        } else {
            final AnyType inlinedType = (AnyType) createAndCopy(type, ruleContext);
            scope.with(inlinedType, ANY_TYPE__TYPE).setValue(type, ruleContext.getStart());
            scope.addValue(INLINE_TYPE_CONTAINER__INLINE_TYPES, inlinedType);
            return inlinedType;
        }
    }

    protected EObject createAndCopy(final EObject eObject, final ParserRuleContext ruleContext) {
        final EClass eClass = eObject.eClass();
        final EObject newEObject = create(eClass, ruleContext);
        final Consumer<EAttribute> copyAttribute = attribute -> newEObject.eSet(attribute, eObject.eGet(attribute));
        eClass.getEAllAttributes().forEach(copyAttribute);

        return newEObject;
    }

    @Override
    public Object visitHeadersFacet(RAMLParser.HeadersFacetContext headersFacet) {
        return withinScope(scope.with(HEADERS_FACET__HEADERS), headersScope -> {
            final List<Object> headers = ECollections.asEList(headersFacet.headerFacets.stream()
                    .map(this::visitTypedElementFacet)
                    .collect(Collectors.toList()));
            scope.setValue(headers, headersFacet.getStart());

            return headers;
        });
    }

    @Override
    public Object visitQueryParametersFacet(RAMLParser.QueryParametersFacetContext queryParametersFacet) {
        return withinScope(scope.with(QUERY_PARAMETERS_FACET__QUERY_PARAMETERS), queryParametersScope -> {
            final List<Object> queryParameters = ECollections.asEList(queryParametersFacet.queryParameters.stream()
                    .map(this::visitTypedElementFacet)
                    .collect(Collectors.toList()));
            scope.setValue(queryParameters, queryParametersFacet.getStart());

            return queryParameters;
        });
    }

    @Override
    public Object visitSecuritySchemeSettingsFacet(RAMLParser.SecuritySchemeSettingsFacetContext securitySchemeSettingsFacet) {
        securitySchemeSettingsFacet.attributeFacet().forEach(this::visitAttributeFacet);
        return scope.getEObject();
    }

    @Override
    public Object visitSecuritySchemeTypeFacet(RAMLParser.SecuritySchemeTypeFacetContext securitySchemeTypeFacet) {
        final String securityTypeText = securitySchemeTypeFacet.type.getText();
        try {
            final SecuritySchemeType securitySchemeType = (SecuritySchemeType) SecurityFactory.eINSTANCE.createFromString(SECURITY_SCHEME_TYPE, securityTypeText);
            scope.setValue(securitySchemeType, securitySchemeTypeFacet.getStart());
            return securitySchemeType;
        } catch (IllegalArgumentException e) {
            scope.addError("{0} at {1}", e.getMessage(), securitySchemeTypeFacet.getStart());
            return null;
        }
    }

    @Override
    public Object visitSecuredByFacet(RAMLParser.SecuredByFacetContext securedByFacet) {
        return withinScope(scope.with(SECURED_BY_FACET__SECURED_BY), securedByScope ->
                ECollections.asEList(securedByFacet.securedBy().stream()
                        .map(this::visitSecuredBy)
                        .collect(Collectors.toList())));
    }

    @Override
    public Object visitAnnotationFacet(final RAMLParser.AnnotationFacetContext annotationFacet) {
        return withinScope(scope.with(ANNOTATIONS_FACET__ANNOTATIONS), annotationsScope -> {
            final Annotation annotation = create(ANNOTATION, annotationFacet);
            scope.setValue(annotation, annotationFacet.getStart());

            final String annotationTypeRef = annotationFacet.ANNOTATION_TYPE_REF().getText();
            final Scope annotationTypeScope = annotationsScope.with(ANNOTATION__TYPE);
            final AnyAnnotationType annotationType = (AnyAnnotationType)
                    annotationTypeScope.getEObjectByName(annotationTypeRef);
            annotation.setType(annotationType);

            withinScope(annotationsScope.with(annotation, ANNOTATION__VALUE),
                    annotationValueScope -> visitInstance(annotationFacet.value));

            return annotation;
        });
    }

    /**
     * Constructor types from the given {@link RAMLParser.TypesFacetContext}.
     *
     * @param typesFacet the types/annotation types facet
     * @return list of types/annotation types
     */
    @Override
    public Object visitTypesFacet(final RAMLParser.TypesFacetContext typesFacet) {
        return withinScope(scope.with(TYPE_CONTAINER__TYPES), typesScope -> {
            final List<Object> types = typesFacet.types.stream()
                    .map(this::visitTypeDeclarationFacet)
                    .collect(Collectors.toList());

            return types;
        });
    }

    /**
     * Constructor annotation types from the given {@link RAMLParser.TypesFacetContext}.
     *
     * @param annotationTypesFacet the types/annotation types facet
     * @return list of types/annotation types
     */
    @Override
    public Object visitAnnotationTypesFacet(RAMLParser.AnnotationTypesFacetContext annotationTypesFacet) {
        return withinScope(scope.with(TYPE_CONTAINER__ANNOTATION_TYPES), typesScope -> {
            final List<Object> types = annotationTypesFacet.annotationTypes.stream()
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

        final EObject parsedTypeExpression = typeExpressionResolver.resolve(typeExpression, scope);
        return parsedTypeExpression;
    }

    @Override
    public Object visitItemsFacet(RAMLParser.ItemsFacetContext itemsFacet) {
        return withinScope(scope.with(ARRAY_TYPE_FACET__ITEMS), itemsScope -> {
            final EObject itemsType;
            if (itemsFacet.typeExpression != null) {
                final String typeExpression = itemsFacet.typeExpression.getText();
                itemsType = typeExpressionResolver.resolve(typeExpression, scope);
            } else {
                EObject typedElementType;
                if (itemsFacet.typeFacet().size() > 0) {
                    final RAMLParser.TypeFacetContext typeFacet = itemsFacet.typeFacet().get(0);
                    typedElementType = (EObject) withinScope(scope.with(TYPED_ELEMENT__TYPE),
                            propertyTypeScope -> visitTypeFacet(typeFacet));
                } else if (itemsFacet.propertiesFacet().size() == 1) {
                    typedElementType = BuiltinType.OBJECT.getType(scope.getResourceSet());
                } else {
                    typedElementType = BuiltinType.STRING.getType(scope.getResourceSet());
                }
                // inline type declaration
                final boolean isInlineTypeDeclaration =
                        itemsFacet.attributeFacet().size() > 0 || itemsFacet.propertiesFacet().size() > 0 ||
                                itemsFacet.exampleFacet().size() > 0 || itemsFacet.examplesFacet().size() > 0 ||
                                itemsFacet.defaultFacet().size() > 0 || itemsFacet.enumFacet().size() > 0 ||
                                itemsFacet.descriptionFacet().size() > 0 || itemsFacet.displayNameFacet().size() > 0 ||
                                itemsFacet.itemsFacet().size() > 0;
                if (isInlineTypeDeclaration) {
                    typedElementType = EcoreUtil.create(typedElementType.eClass());
                    scope.addValue(INLINE_TYPE_CONTAINER__INLINE_TYPES, typedElementType);
                    withinScope(scope.with(typedElementType),
                            inlineTypeDeclarationScope -> {
                                itemsFacet.attributeFacet().forEach(this::visitAttributeFacet);
                                itemsFacet.descriptionFacet().forEach(this::visitDescriptionFacet);
                                itemsFacet.displayNameFacet().forEach(this::visitDisplayNameFacet);
                                itemsFacet.propertiesFacet().forEach(this::visitPropertiesFacet);
                                itemsFacet.defaultFacet().forEach(this::visitDefaultFacet);
                                itemsFacet.exampleFacet().forEach(this::visitExampleFacet);
                                itemsFacet.examplesFacet().forEach(this::visitExamplesFacet);
                                itemsFacet.enumFacet().forEach(this::visitEnumFacet);
                                itemsFacet.itemsFacet().forEach(this::visitItemsFacet);

                                return inlineTypeDeclarationScope.getEObject();
                            });
                }
                itemsType = typedElementType;
            }
            scope.setValue(itemsType, itemsFacet.getStart());

            return itemsType;
        });
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
        final String text = typeDeclarationMap.name.getText();
        final EObject declaredType = scope.getEObjectByName(text);

        return withinScope(scope.with(declaredType), typeScope -> {
            typeDeclarationMap.annotationFacet().forEach(this::visitAnnotationFacet);
            typeDeclarationMap.attributeFacet().forEach(this::visitAttributeFacet);
            typeDeclarationMap.descriptionFacet().forEach(this::visitDescriptionFacet);
            typeDeclarationMap.displayNameFacet().forEach(this::visitDisplayNameFacet);
            typeDeclarationMap.propertiesFacet().forEach(this::visitPropertiesFacet);
            typeDeclarationMap.defaultFacet().forEach(this::visitDefaultFacet);
            typeDeclarationMap.exampleFacet().forEach(this::visitExampleFacet);
            typeDeclarationMap.examplesFacet().forEach(this::visitExamplesFacet);
            typeDeclarationMap.enumFacet().forEach(this::visitEnumFacet);

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
        return withinScope(scope.with(OBJECT_TYPE_FACET__PROPERTIES), propertiesScope -> {
            final List<Object> properties = propertiesFacet.propertyFacets.stream()
                    .map(this::visitTypedElementFacet)
                    .collect(Collectors.toList());

            return properties;
        });
    }

    @Override
    public Object visitTypedElementFacet(RAMLParser.TypedElementFacetContext typedElementFacet) {
        final EClass eType = (EClass) scope.getFeature().getEType();
        final EObject typedElement = create(eType, typedElementFacet);
        scope.setValue(typedElement, typedElementFacet.getStart());

        return withinScope(scope.with(typedElement, TYPED_ELEMENT__TYPE), propertyScope ->
                super.visitTypedElementFacet(typedElementFacet));
    }

    @Override
    public Object visitTypedElementTuple(RAMLParser.TypedElementTupleContext typedeElementTuple) {
        final Token type = typedeElementTuple.type;
        final String name = typedeElementTuple.name.getText();

        final EObject propertyType = Strings.isNullOrEmpty(type.getText()) ?
                BuiltinType.STRING.getType(scope.getResourceSet()) :
                typeExpressionResolver.resolve(type.getText(), scope);
        final boolean isRequired = !name.endsWith("?");
        scope.setValue(TYPED_ELEMENT__REQUIRED, isRequired, typedeElementTuple.getStart());
        final String parsedName = isRequired ? name : name.substring(0, name.length() - 1);

        scope.setValue(NAMED_ELEMENT__NAME, parsedName, typedeElementTuple.getStart());
        setTypedElementPattern(name, typedeElementTuple.getStart());

        scope.setValue(TYPED_ELEMENT__TYPE, propertyType, typedeElementTuple.getStart());

        return scope.getEObject();
    }

    private void setTypedElementPattern(final String name, final Token nameStart) {
        if (name.startsWith("/") && name.endsWith("/")) {
            final RegExp pattern = RegExp.of(name.substring(1, name.length() - 1));
            scope.setValue(TYPED_ELEMENT__PATTERN, pattern, nameStart);
        }
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

        scope.setValue(NAMED_ELEMENT__NAME, parsedName, typedElementMap.getStart());
        setTypedElementPattern(name, typedElementMap.getStart());

        AnyType typedElementType;
        if (typedElementMap.typeFacet().size() > 0) {
            final RAMLParser.TypeFacetContext typeFacet = typedElementMap.typeFacet().get(0);
            typedElementType = (AnyType) withinScope(scope.with(TYPED_ELEMENT__TYPE),
                    propertyTypeScope -> visitTypeFacet(typeFacet));
        } else if (typedElementMap.propertiesFacet().size() == 1) {
            typedElementType = BuiltinType.OBJECT.getType(scope.getResourceSet());
        } else {
            typedElementType = BuiltinType.STRING.getType(scope.getResourceSet());
        }

        // inline type declaration
        final boolean isInlineTypeDeclaration =
                typedElementMap.attributeFacet().size() > 0 || typedElementMap.propertiesFacet().size() > 0 ||
                        typedElementMap.exampleFacet().size() > 0 || typedElementMap.examplesFacet().size() > 0 ||
                        typedElementMap.defaultFacet().size() > 0 || typedElementMap.enumFacet().size() > 0 ||
                        typedElementMap.displayNameFacet().size() > 0 || typedElementMap.descriptionFacet().size() > 0 ||
                        typedElementMap.itemsFacet().size() > 0;
        if (isInlineTypeDeclaration) {
            typedElementType = inlineTypeDeclaration(typedElementType, scope, typedElementMap);
            scope.addValue(INLINE_TYPE_CONTAINER__INLINE_TYPES, typedElementType);
            withinScope(scope.with(typedElementType),
                    inlineTypeDeclarationScope -> {
                        typedElementMap.attributeFacet().forEach(this::visitAttributeFacet);
                        typedElementMap.descriptionFacet().forEach(this::visitDescriptionFacet);
                        typedElementMap.displayNameFacet().forEach(this::visitDisplayNameFacet);
                        typedElementMap.propertiesFacet().forEach(this::visitPropertiesFacet);
                        typedElementMap.defaultFacet().forEach(this::visitDefaultFacet);
                        typedElementMap.exampleFacet().forEach(this::visitExampleFacet);
                        typedElementMap.examplesFacet().forEach(this::visitExamplesFacet);
                        typedElementMap.enumFacet().forEach(this::visitEnumFacet);
                        typedElementMap.itemsFacet().forEach(this::visitItemsFacet);

                        return inlineTypeDeclarationScope.getEObject();
                    });
        }

        typedElementMap.annotationFacet().forEach(this::visitAnnotationFacet);
        scope.setValue(TYPED_ELEMENT__TYPE, typedElementType, typedElementMap.getStart());

        return scope.getEObject();
    }

    @Override
    public Object visitAttributeFacet(final RAMLParser.AttributeFacetContext attributeFacet) {
        final Object value = setAttribute(attributeFacet, scope.getEObject());
        return value;
    }

    @Override
    public Object visitResourceTypesFacet(RAMLParser.ResourceTypesFacetContext ctx) {
        return withinScope(scope.with(TYPE_CONTAINER__RESOURCE_TYPES),
                resourceTypeesScope -> super.visitResourceTypesFacet(ctx));
    }

    @Override
    public Object visitResourceTypeFacet(RAMLParser.ResourceTypeFacetContext ctx) {
        return withinScope(scope.with(RESOURCE_BASE__TYPE), resourceTypeScope ->
                visitResourceTypeApplication(ctx.resourceTypeApplication()));
    }

    @Override
    public Object visitResourceTypeApplication(RAMLParser.ResourceTypeApplicationContext ctx) {
        final ResourceTypeApplication resourceTypeApplication = create(RESOURCE_TYPE_APPLICATION, ctx);
        scope.setValue(resourceTypeApplication, ctx.getStart());
        final ResourceType resourceType = (ResourceType) scope.with(RESOURCE_TYPE_APPLICATION__TYPE).getEObjectByName(ctx.type.getText());
        resourceTypeApplication.setType(resourceType);
        return withinScope(scope.with(resourceTypeApplication, PARAMETERIZED_APPLICATION__PARAMETERS),
                argumentsScope -> ctx.argument().stream()
                        .map(this::visitArgument)
                        .collect(Collectors.toList()));
    }

    @Override
    public Object visitResourceTypeDeclarationFacet(RAMLParser.ResourceTypeDeclarationFacetContext resourceTypeDeclarationFacet) {
        final String type = resourceTypeDeclarationFacet.name.getText();
        final EObject resourceType = scope.getEObjectByName(type);
        return withinScope(scope.with(resourceType), resourceTypeScope -> {
            resourceTypeDeclarationFacet.resourceBaseFacet().forEach(this::visitResourceBaseFacet);

            return resourceType;
        });
    }

    @Override
    public Object visitMethodFacet(RAMLParser.MethodFacetContext methodFacet) {
        return withinScope(scope.with(RESOURCE_BASE__METHODS), methodsScope -> {
            final Method method = create(METHOD, methodFacet);
            String httpMethodText = methodFacet.httpMethod().getText();
            final boolean required = !httpMethodText.endsWith("?");
            method.setRequired(required);
            httpMethodText = required ?
                    httpMethodText :
                    httpMethodText.substring(0, httpMethodText.length() - 1) ;
            final HttpMethod httpMethod = (HttpMethod) ResourcesFactory.eINSTANCE.createFromString(HTTP_METHOD, httpMethodText);
            method.setMethod(httpMethod);
            methodsScope.setValue(method, methodFacet.getStart());

            withinScope(methodsScope.with(method), methodScope -> {
                methodFacet.methodBaseFacet().forEach(this::visitMethodBaseFacet);
                return method;
            });

            return method;
        });
    }

    /**
     * Sets an attribute given by the attribute facet on the given eobject.
     *
     * @param attributeFacet the attribute facet
     * @param eObject        the object to set the attribute
     */
    private Object setAttribute(final RAMLParser.AttributeFacetContext attributeFacet, final EObject eObject) {
        final EClass eClass = eObject.eClass();
        final String attributeName = attributeFacet.facet.getText();
        final EAttribute eAttribute = eClass.getEAllAttributes().stream()
                .filter(a -> a.getName().equals(attributeName))
                .findFirst()
                .orElse(null);

        final Object value;
        if (eAttribute == null) {
            scope.addError("Unknown attribute {0} at {1}", attributeName, attributeFacet.getStart());
            value = null;
        } else {
            value = attributeFacet.facetValue().value == null ?
                    attributeFacet.facetValue().values :
                    attributeFacet.facetValue().value;

            if (attributeFacet.facetValue().anyValue().size() == 1) {
                setAttribute(eObject, eAttribute, attributeFacet.facetValue().anyValue().get(0));
            } else {
                setAttribute(eObject, eAttribute, attributeFacet.facetValue().anyValue());
            }
        }
        return value;
    }

    private void setAttribute(final EObject eObject, final EAttribute eAttribute, final List<RAMLParser.AnyValueContext> valueTokens) {
        if (eAttribute.isMany()) {
            final List<Object> values = valueTokens.stream()
                    .map(v -> createFromString(eAttribute, v))
                    .collect(Collectors.toList());

            eObject.eSet(eAttribute, values);
        } else {
            final String messagePattern = "Trying to set attribute {0} with many values";
            if (valueTokens.isEmpty()) {
                scope.addError(messagePattern, eAttribute);
            } else {
                scope.addError(messagePattern + " at {1}", eAttribute, valueTokens.get(0).getStart());
            }
        }
    }

    private void setAttribute(final EObject eObject, final EAttribute eAttribute, final RAMLParser.AnyValueContext anyValueContext) {
        if (anyValueContext.getText().length() > 0) {
            final Object value = createFromString(eAttribute, anyValueContext);
            if (eAttribute.isMany()) {
                eObject.eSet(eAttribute, Collections.singletonList(value));
            } else {
                eObject.eSet(eAttribute, value);
            }
        }
    }

    private Object createFromString(final EAttribute eAttribute, final RAMLParser.AnyValueContext anyValueContext) {
        try {
            return EcoreUtil.createFromString(eAttribute.getEAttributeType(), anyValueContext.getText());
        } catch (IllegalArgumentException e) {
            scope.addError("{0} at {1}", e.getMessage(), anyValueContext.getStart());
            return null;
        }
    }
}
