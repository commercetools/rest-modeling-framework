package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.functional.utils.TypeSwitch;
import io.vrap.rmf.raml.model.types.BuiltinType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.vrap.functional.utils.Classes.asOptional;
import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.*;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

public class TypeDeclarationsConstructor extends AbstractIdentifiableElementsConstructor {
    private final Map<EClass, Constructor<MappingNode>> metaTypeConstructors = new HashMap<>();

    public TypeDeclarationsConstructor() {
        for (final BuiltinType metaType : BuiltinType.values()) {
            metaTypeConstructors.put(metaType.getTypeDeclarationType(), new TypeDeclarationConstructor(metaType.getTypeDeclarationType()));
            metaTypeConstructors.put(metaType.getAnnotationTypeDeclarationType(), new TypeDeclarationConstructor(metaType.getAnnotationTypeDeclarationType()));
        }
    }

    @Override
    protected EObject create(final NodeTuple nodeTuple, final Scope typesScope, final String name) {
        final Scope resolvedScope = resolve(nodeTuple.getValueNode(), typesScope);
        final Optional<Node> optionalTypeNode = getNodeTuple(resolvedScope.getValueNode(), ANY_TYPE__TYPE)
                .map(NodeTuple::getValueNode);

        final EClass typeDeclarationType = asOptional(ScalarNode.class, optionalTypeNode)
                .map(ScalarNode::getValue)
                .flatMap(BuiltinType::of)
                .orElse(BuiltinType.OBJECT)
                .getScopedMetaType(resolvedScope);

        final EObject typeDeclaration = new TypeSwitch<Node, EObject>()
                .on(MappingNode.class, mappingNode -> createTypeDeclaration(mappingNode, resolvedScope, typeDeclarationType))
                .fallthrough(n -> EcoreUtil.create(typeDeclarationType))
                .apply(resolvedScope.getValueNode());

        typeDeclaration.eSet(IDENTIFIABLE_ELEMENT__NAME, name);
        resolvedScope.setValue(typeDeclaration);

        return typeDeclaration;
    }

    private EObject createTypeDeclaration(final MappingNode mappingNode, final Scope typesScope, final EClass typeDeclarationType) {
        final EObject typeDeclaration = EcoreUtil.create(typeDeclarationType);
        constructFeatures(mappingNode, typesScope.with(typeDeclaration));
        return typeDeclaration;
    }

    private Object constructFeatures(final MappingNode mappingNode, final Scope typeDeclarationScope) {
        final EClass eClass = typeDeclarationScope.eObject().eClass();
        final Constructor<MappingNode> typeDeclarationConstructor = metaTypeConstructors.get(eClass);
        return typeDeclarationConstructor.apply(mappingNode, typeDeclarationScope);
    }
}
