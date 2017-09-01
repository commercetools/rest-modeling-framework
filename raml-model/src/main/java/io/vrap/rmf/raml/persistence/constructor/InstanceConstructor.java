package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.facets.*;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.ecore.EObject;

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
    public Object visitSimpleInstance(RAMLParser.SimpleInstanceContext simpleInstance) {
        final String text = simpleInstance.value.getText();

        final Instance instance;
        switch (text) {
            case "true":
            case "false":
                final BooleanInstance typeInstance = FacetsFactory.eINSTANCE.createBooleanInstance();
                typeInstance.setValue(Boolean.valueOf(text));
                instance = typeInstance;
                break;
            default:
                final StringInstance stringInstance = FacetsFactory.eINSTANCE.createStringInstance();
                stringInstance.setValue(text);
                instance = stringInstance;
        }
        scope.setValue(instance, simpleInstance.getStart());
        return instance;
    }

    @Override
    public Object visitObjectInstance(RAMLParser.ObjectInstanceContext ctx) {
        final ObjectInstance objectInstance = FacetsFactory.eINSTANCE.createObjectInstance();
        scope.setValue(objectInstance, ctx.getStart());

        return withinScope(scope.with(objectInstance, OBJECT_INSTANCE__PROPERTY_VALUES), propertyValuesScope -> {
            ctx.instanceProperty().forEach(this::visitInstanceProperty);
            return objectInstance;
        });
    }

    @Override
    public Object visitInstanceProperty(RAMLParser.InstancePropertyContext ctx) {
        final PropertyValue propertyValue = FacetsFactory.eINSTANCE.createPropertyValue();
        scope.setValue(propertyValue, ctx.getStart());

        propertyValue.setName(ctx.name.getText());

        return withinScope(scope.with(propertyValue, PROPERTY_VALUE__VALUE), propertyValueScope -> {
            visitInstance(ctx.value);
            return propertyValue;
        });
    }

    @Override
    public Object visitArrayInstance(RAMLParser.ArrayInstanceContext ctx) {
        final ArrayInstance arrayInstance = FacetsFactory.eINSTANCE.createArrayInstance();
        scope.setValue(arrayInstance, ctx.getStart());

        return withinScope(scope.with(arrayInstance, ARRAY_INSTANCE__VALUES), arrayInstanceScope -> {
            ctx.instance().forEach(this::visitInstance);

            return arrayInstance;
        });
    }
}
