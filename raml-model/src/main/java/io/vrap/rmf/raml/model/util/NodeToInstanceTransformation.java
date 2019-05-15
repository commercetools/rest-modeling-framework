package io.vrap.rmf.raml.model.util;

import io.vrap.rmf.nodes.*;
import io.vrap.rmf.nodes.util.NodesSwitch;
import io.vrap.rmf.raml.model.types.*;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Transforms the low level {@link Node} types to higher level {@link Instance} types.
 */
class NodeToInstanceTransformation extends NodesSwitch<Instance> {
    @Override
    public Instance caseStringNode(final StringNode stringNode) {
        final StringInstance stringInstance = TypesFactory.eINSTANCE.createStringInstance();
        stringInstance.setValue(stringNode.getValue());
        return copyAdapters(stringNode, stringInstance);
    }

    @Override
    public Instance caseBooleanNode(final BooleanNode booleanNode) {
        final BooleanInstance booleanInstance = TypesFactory.eINSTANCE.createBooleanInstance();
        booleanInstance.setValue(booleanNode.getValue());
        return copyAdapters(booleanNode, booleanInstance);
    }

    @Override
    public Instance caseIntegerNode(final IntegerNode integerNode) {
        final IntegerInstance integerInstance = TypesFactory.eINSTANCE.createIntegerInstance();
        integerInstance.setValue(integerNode.getValue());
        return copyAdapters(integerNode, integerInstance);
    }

    @Override
    public Instance caseNullNode(final NullNode object) {
        return TypesFactory.eINSTANCE.createNullInstance();
    }

    @Override
    public Instance caseNumberNode(final NumberNode numberNode) {
        final NumberInstance numberInstance = TypesFactory.eINSTANCE.createNumberInstance();
        numberInstance.setValue(numberNode.getValue());
        return copyAdapters(numberNode, numberInstance);
    }

    @Override
    public Instance caseArrayNode(final ArrayNode arrayNode) {
        final ArrayInstance arrayInstance = TypesFactory.eINSTANCE.createArrayInstance();
        final List<Instance> elements = arrayNode.getElements().stream()
                .map(this::doSwitch)
                .collect(Collectors.toList());
        arrayInstance.getValue().addAll(elements);
        return copyAdapters(arrayNode, arrayInstance);
    }

    @Override
    public Instance caseObjectNode(final ObjectNode objectNode) {
        final ObjectInstance objectInstance = TypesFactory.eINSTANCE.createObjectInstance();
        final List<PropertyValue> propertyValues = objectNode.getProperties().stream()
                .map(this::transform)
                .collect(Collectors.toList());
        objectInstance.getValue().addAll(propertyValues);
        return copyAdapters(objectNode, objectInstance);
    }

    private PropertyValue transform(final PropertyNode propertyNode) {
        final PropertyValue propertyValue = TypesFactory.eINSTANCE.createPropertyValue();
        propertyValue.setName(propertyNode.getKey().getValue().toString());
        propertyValue.setValue(doSwitch(propertyNode.getValue()));

        return copyAdapters(propertyNode, propertyValue);
    }

    private <T extends EObject> T copyAdapters(final EObject source, final T target) {
        source.eAdapters().forEach(a -> target.eAdapters().add(a));
        return target;
    }
}
