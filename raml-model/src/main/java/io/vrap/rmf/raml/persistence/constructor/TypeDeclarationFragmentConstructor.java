package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.RamlFragmentKind;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.vrap.functional.utils.Classes.asOptional;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

/**
 * Constructs a type declaration for a type fragment of either {@link RamlFragmentKind#DATA_TYPE}
 * or {@link RamlFragmentKind#ANNOTATION_TYPE_DECLARATION}
 */
public class TypeDeclarationFragmentConstructor extends Constructor<MappingNode> {
    private final Map<EClass, Constructor<MappingNode>> metaTypeConstructors = new HashMap<>();
    private final EClass typeDeclarationType;
    private final EReference typeReference;
    
    public TypeDeclarationFragmentConstructor(final RamlFragmentKind fragmentKind) {
        this.typeDeclarationType = fragmentKind.getType();
        this.typeReference = ANY_ANNOTATION_TYPE.isSuperTypeOf(typeDeclarationType) ?
        		ANY_ANNOTATION_TYPE__TYPE :
        		ANY_TYPE__TYPE;
        for (final BuiltinType metaType : BuiltinType.values()) {
            metaTypeConstructors.put(metaType.getTypeDeclarationType(),
                    new TypeDeclarationConstructor(metaType.getTypeDeclarationType()));
            metaTypeConstructors.put(metaType.getAnnotationTypeDeclarationType(),
                    new TypeDeclarationConstructor(metaType.getAnnotationTypeDeclarationType()));
        }
    }

    @Override
    public Object apply(final MappingNode mappingNode, final Scope rootScope) {
        final Optional<Node> value = getNodeTuple(mappingNode, typeReference)
                .map(NodeTuple::getValueNode);
        final BuiltinType builtinType = asOptional(ScalarNode.class, value)
                .map(ScalarNode::getValue)
                .flatMap(BuiltinType::of)
                .orElse(BuiltinType.OBJECT);
        final EObject typeDeclaration = EcoreUtil.create(builtinType.typeFor(typeDeclarationType));
        rootScope.getResource().getContents().add(typeDeclaration);
        constructTypeDeclaration(mappingNode, rootScope.with(typeDeclaration));

        return typeDeclaration;
    }

    private Object constructTypeDeclaration(final MappingNode mappingNode, final Scope typeDeclarationScope) {
        final EClass eClass = typeDeclarationScope.eObject().eClass();
        final Constructor<MappingNode> typeDeclarationConstructor = metaTypeConstructors.get(eClass);
        return typeDeclarationConstructor.apply(mappingNode, typeDeclarationScope);
    }
}
