package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.facets.*;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.ecore.EObject;

import java.math.BigDecimal;

import static io.vrap.rmf.raml.model.facets.FacetsPackage.Literals.*;

/**
 * Constructs {@link Instance}s.
 */
public class InstanceConstructor extends AbstractConstructor {
    @Override
    public EObject construct(RAMLParser parser, Scope scope) {
        final Instance instance = (Instance) withinScope(scope,
                s ->visitInstance(parser.instance()));
        return instance;
    }

    @Override
    public Object visitSimpleInstance(final RAMLParser.SimpleInstanceContext ctx) {
        final Instance instance = (Instance) super.visitSimpleInstance(ctx);
        scope.setValue(instance, ctx.getStart());
        return instance;
    }

    @Override
    public Object visitStringInstance(final RAMLParser.StringInstanceContext ctx) {
        final StringInstance stringInstance = create(STRING_INSTANCE, ctx);
        stringInstance.setValue(ctx.getText());
        return stringInstance;
    }

    @Override
    public Object visitBooleanInstance(final RAMLParser.BooleanInstanceContext ctx) {
        final BooleanInstance booleanInstance = create(BOOLEAN_INSTANCE, ctx);
        booleanInstance.setValue(Boolean.valueOf(ctx.getText()));
        return booleanInstance;
    }

    @Override
    public Object visitIntegerInstance(final RAMLParser.IntegerInstanceContext ctx) {
        final IntegerInstance integerInstance = create(INTEGER_INSTANCE, ctx);
        integerInstance.setValue(Integer.parseInt(ctx.getText()));
        return integerInstance;
    }

    @Override
    public Object visitNumberInstance(final RAMLParser.NumberInstanceContext ctx) {
        final NumberInstance numberInstance = create(NUMBER_INSTANCE, ctx);
        numberInstance.setValue(new BigDecimal(ctx.getText()));
        return numberInstance;
    }

    @Override
    public Object visitObjectInstance(RAMLParser.ObjectInstanceContext ctx) {
        final ObjectInstance objectInstance = create(OBJECT_INSTANCE, ctx);
        scope.setValue(objectInstance, ctx.getStart());

        return withinScope(scope.with(objectInstance, OBJECT_INSTANCE__PROPERTY_VALUES), propertyValuesScope -> {
            ctx.instanceProperty().forEach(this::visitInstanceProperty);
            return objectInstance;
        });
    }

    @Override
    public Object visitInstanceProperty(RAMLParser.InstancePropertyContext ctx) {
        final PropertyValue propertyValue = create(PROPERTY_VALUE, ctx);
        scope.setValue(propertyValue, ctx.getStart());

        propertyValue.setName(ctx.name.getText());

        return withinScope(scope.with(propertyValue, PROPERTY_VALUE__VALUE), propertyValueScope -> {
            visitInstance(ctx.value);
            return propertyValue;
        });
    }

    @Override
    public Object visitArrayInstance(RAMLParser.ArrayInstanceContext ctx) {
        final ArrayInstance arrayInstance = create(ARRAY_INSTANCE, ctx);
        scope.setValue(arrayInstance, ctx.getStart());

        return withinScope(scope.with(arrayInstance, ARRAY_INSTANCE__VALUES), arrayInstanceScope -> {
            ctx.instance().forEach(this::visitInstance);

            return arrayInstance;
        });
    }
}
