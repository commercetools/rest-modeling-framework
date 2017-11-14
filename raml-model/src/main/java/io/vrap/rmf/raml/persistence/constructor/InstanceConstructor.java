package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.values.*;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;

import java.math.BigDecimal;

import static io.vrap.rmf.raml.model.values.ValuesPackage.Literals.*;

/**
 * Constructs {@link Instance}s.
 */
public class InstanceConstructor extends AbstractScopedVisitor<Instance> {

    public Instance construct(RAMLParser parser, Scope scope) {
        return withinScope(scope,
                s ->visitInstance(parser.instance()));
    }

    @Override
    public Instance visitSimpleInstance(final RAMLParser.SimpleInstanceContext ctx) {
        final Instance instance = super.visitSimpleInstance(ctx);
        scope.setValue(instance, ctx.getStart());
        return instance;
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
            visitInstance(ctx.value);
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
}
