package io.vrap.rmf.raml.persistence.constructor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;

import java.util.Optional;

import static io.vrap.functional.utils.Classes.as;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

/**
 * Constructs a list of properties {@link io.vrap.rmf.raml.model.types.Property} from a {@link MappingNode}.
 *
 * Structure:
 * {@code <name>: <Type?>}
 * {@code <name>:
 *              type?: <TYPE>
 *              required: boolean}
 */
public class PropertiesConstructor extends AbstractIdentifiableElementsConstructor {

    public PropertiesConstructor() {
        PROPERTY.getEAllAttributes().stream()
                .filter(a -> a != PROPERTY__NAME)
                .forEach(this::addConstructor);
        addConstruct(PROPERTY__TYPE, new TypeReferenceConstructor());
        addConstructor(new AnnotationsConstructor());
    }

    @Override
    protected EObject create(final NodeTuple nodeTuple, final Scope container, final String name) {
        final EObject instance = EcoreUtil.create(PROPERTY);
        container.setValue(instance);
        final Scope scope = container.with(instance, PROPERTIES_FACET__PROPERTIES);
        return constructProperty(nodeTuple, name, scope);
    }

    private EObject constructProperty(final NodeTuple nodeTuple, final String name, final Scope propertyScope) {
        setParsedName(name, nodeTuple, propertyScope);
        final Optional<MappingNode> optionalMappingNode = as(MappingNode.class, nodeTuple.getValueNode());
        if (optionalMappingNode.isPresent()) {
            optionalMappingNode.get().getValue().stream()
                    .forEach(featureNodeTuple -> constructFeature(featureNodeTuple, propertyScope));
        }
        final EObject property = propertyScope.eObject();
        if (!property.eIsSet(PROPERTY__TYPE)) {
            final Scope propertyTypeScope = propertyScope.with(PROPERTY__TYPE);
            constructor(PROPERTY__TYPE).apply(nodeTuple, propertyTypeScope);
        }
        return property;
    }

    protected void constructFeature(final NodeTuple featureNodeTuple, final Scope propertyScope) {
        final Optional<Constructor<NodeTuple>> optionalConstructor = constructor(PROPERTY, featureNodeTuple);

        if (optionalConstructor.isPresent()) {
            final Optional<EStructuralFeature> optionalFeature = feature(PROPERTY, featureNodeTuple);
            final Constructor<NodeTuple> constructor = optionalConstructor.get();

            if (optionalFeature.isPresent()) {
                final EStructuralFeature feature = optionalFeature.get();
                constructor.apply(featureNodeTuple, propertyScope.with(feature));
            } else {
                constructor.apply(featureNodeTuple, propertyScope);
            }
        } else {
            propertyScope.addError("No constructor for {0} found", featureNodeTuple.getKeyNode());
        }
    }

    private void setParsedName(final String name, final NodeTuple nodeTuple, final Scope propertyScope) {
        final Scope requiredScope = propertyScope.with(PROPERTY__REQUIRED);
        final Object requiredValue = getNodeTuple(nodeTuple.getValueNode(), PROPERTY__REQUIRED)
                .map(requiredTuple -> constructor(PROPERTY__REQUIRED).apply(requiredTuple, requiredScope))
                .orElse(null);

        final String parsedName;
        if (requiredValue == null) {
            final boolean isRequired = !name.endsWith("?");
            propertyScope.with(PROPERTY__REQUIRED).setValue(isRequired);
            parsedName = isRequired ? name : name.substring(0, name.length() - 1);
        } else {
            parsedName = name;
        }

        propertyScope.with(PROPERTY__NAME).setValue(parsedName);
    }
}
