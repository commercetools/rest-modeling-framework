package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.types.Annotation;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import io.vrap.rmf.raml.model.values.*;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;

import java.math.BigDecimal;

import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;
import static io.vrap.rmf.raml.model.values.ValuesPackage.Literals.*;

/**
 * Constructs {@link Instance}s.
 */
public class InstanceConstructor extends AbstractScopedVisitor<Instance> {

    public Instance construct(RAMLParser parser, Scope scope) {
        return withinScope(scope,
                s -> visitInstance(parser.instance()));
    }

    @Override
    public Instance visitSimpleInstance(final RAMLParser.SimpleInstanceContext ctx) {
        final Instance instance = super.visitSimpleInstance(ctx);
        scope.setValue(instance, ctx.getStart());
        return instance;
    }

    @Override
    public Instance visitAnnotatedSimpleInstance(RAMLParser.AnnotatedSimpleInstanceContext ctx) {
        final Instance instance = super.visitAnnotatedSimpleInstance(ctx);
        scope.setValue(instance, ctx.getStart());
        return instance;
    }

    @Override
    public Instance visitAnnotatedObjectInstance(RAMLParser.AnnotatedObjectInstanceContext ctx) {
        final ObjectInstance objectInstance = (ObjectInstance)visitObjectInstance(ctx.objectInstance(0));
        scope.setValue(objectInstance, ctx.getStart());

        return withinScope(scope.with(objectInstance), objectInstanceScope -> {
            ctx.annotationFacet().forEach(this::visitAnnotationFacet);
            return objectInstance;
        });
    }

    @Override
    public Instance visitAnnotatedArrayInstance(RAMLParser.AnnotatedArrayInstanceContext ctx) {
        final ArrayInstance arrayInstance = (ArrayInstance)visitArrayInstance(ctx.arrayInstance(0));
        scope.setValue(arrayInstance, ctx.getStart());

        return withinScope(scope.with(arrayInstance), arrayInstanceScope -> {
            ctx.annotationFacet().forEach(this::visitAnnotationFacet);
            return arrayInstance;
        });
    }

    @Override
    public Instance visitAnnotatedBooleanInstance(RAMLParser.AnnotatedBooleanInstanceContext ctx) {
        final BooleanInstance booleanInstance = create(BOOLEAN_INSTANCE, ctx);
        booleanInstance.setValue(Boolean.valueOf(ctx.BOOL(0).getText()));
        return withinScope(scope.with(booleanInstance), booleanInstanceScope -> {
            ctx.annotationFacet().forEach(this::visitAnnotationFacet);
            return booleanInstance;
        });
    }

    @Override
    public Instance visitAnnotatedRelativeUriInstance(RAMLParser.AnnotatedRelativeUriInstanceContext ctx) {
        final StringInstance stringInstance = create(STRING_INSTANCE, ctx);
        stringInstance.setValue(ctx.RELATIVE_URI(0).getText());
        return withinScope(scope.with(stringInstance), stringInstanceScope -> {
            ctx.annotationFacet().forEach(this::visitAnnotationFacet);
            return stringInstance;
        });
    }

    @Override
    public Instance visitAnnotatedStringInstance(RAMLParser.AnnotatedStringInstanceContext ctx) {
        final StringInstance stringInstance = create(STRING_INSTANCE, ctx);
        stringInstance.setValue(ctx.id(0).getText());
        return withinScope(scope.with(stringInstance), stringInstanceScope -> {
            ctx.annotationFacet().forEach(this::visitAnnotationFacet);
            return stringInstance;
        });
    }

    @Override
    public Instance visitAnnotatedIntegerInstance(RAMLParser.AnnotatedIntegerInstanceContext ctx) {
        Instance instance;
        try {
            final int value = Integer.parseInt(ctx.INT(0).getText());
            final IntegerInstance integerInstance = create(INTEGER_INSTANCE, ctx);
            integerInstance.setValue(value);
            instance = integerInstance;
        } catch (NumberFormatException e) {
            final StringInstance stringInstance = create(STRING_INSTANCE, ctx);
            stringInstance.setValue(ctx.INT(0).getText());
            instance = stringInstance;
        }

        final Instance integerInstance = instance;
        return withinScope(scope.with(integerInstance), integerInstanceScope -> {
            ctx.annotationFacet().forEach(this::visitAnnotationFacet);
            return integerInstance;
        });
    }

    @Override
    public Instance visitAnnotatedNumberInstance(RAMLParser.AnnotatedNumberInstanceContext ctx) {
        final NumberInstance numberInstance = create(NUMBER_INSTANCE, ctx);
        numberInstance.setValue(new BigDecimal(ctx.FLOAT(0).getText()));
        return withinScope(scope.with(numberInstance), numberInstanceScope -> {
            ctx.annotationFacet().forEach(this::visitAnnotationFacet);
            return numberInstance;
        });
    }

    @Override
    public Instance visitRelativeUriInstance(RAMLParser.RelativeUriInstanceContext ctx) {
        final StringInstance stringInstance = create(STRING_INSTANCE, ctx);
        stringInstance.setValue(ctx.getText());
        return stringInstance;
    }

    @Override
    public Instance visitStringInstance(final RAMLParser.StringInstanceContext ctx) {
        final StringInstance stringInstance = create(STRING_INSTANCE, ctx);
        stringInstance.setValue(ctx.getText());
        return stringInstance;
    }

    @Override
    public Instance visitBooleanInstance(final RAMLParser.BooleanInstanceContext ctx) {
        final BooleanInstance booleanInstance = create(BOOLEAN_INSTANCE, ctx);
        booleanInstance.setValue(Boolean.valueOf(ctx.getText()));
        return booleanInstance;
    }

    @Override
    public Instance visitIntegerInstance(final RAMLParser.IntegerInstanceContext ctx) {
        Instance instance;
        try {
            final int value = Integer.parseInt(ctx.getText());
            final IntegerInstance integerInstance = create(INTEGER_INSTANCE, ctx);
            integerInstance.setValue(value);
            instance = integerInstance;
        } catch (NumberFormatException e) {
            final StringInstance stringInstance = create(STRING_INSTANCE, ctx);
            stringInstance.setValue(ctx.getText());
            instance = stringInstance;
        }
        return instance;
    }

    @Override
    public Instance visitNumberInstance(final RAMLParser.NumberInstanceContext ctx) {
        final NumberInstance numberInstance = create(NUMBER_INSTANCE, ctx);
        numberInstance.setValue(new BigDecimal(ctx.getText()));
        return numberInstance;
    }

    @Override
    public Instance visitObjectInstance(RAMLParser.ObjectInstanceContext ctx) {
        final ObjectInstance objectInstance = create(OBJECT_INSTANCE, ctx);
        scope.setValue(objectInstance, ctx.getStart());

        return withinScope(scope.with(objectInstance, OBJECT_INSTANCE__VALUE), propertyValuesScope -> {
            ctx.instanceProperty().forEach(this::createInstanceProperty);
            return objectInstance;
        });
    }

    private void createInstanceProperty(RAMLParser.InstancePropertyContext ctx) {
        final PropertyValue propertyValue = create(PROPERTY_VALUE, ctx);
        scope.setValue(propertyValue, ctx.getStart());

        propertyValue.setName(ctx.name.getText());

        withinScope(scope.with(propertyValue, PROPERTY_VALUE__VALUE), propertyValueScope -> {
            visitBaseInstance(ctx.value);
            return propertyValue;
        });
    }

    @Override
    public Instance visitArrayInstance(RAMLParser.ArrayInstanceContext ctx) {
        final ArrayInstance arrayInstance = create(ARRAY_INSTANCE, ctx);
        scope.setValue(arrayInstance, ctx.getStart());

        return withinScope(scope.with(arrayInstance, ARRAY_INSTANCE__VALUE), arrayInstanceScope -> {
            ctx.instance().forEach(this::visitInstance);

            return arrayInstance;
        });
    }

    @Override
    public Instance visitAnnotationFacet(final RAMLParser.AnnotationFacetContext annotationFacet) {
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

            return (Instance)annotationsScope.getEObject();
        });
    }
}
