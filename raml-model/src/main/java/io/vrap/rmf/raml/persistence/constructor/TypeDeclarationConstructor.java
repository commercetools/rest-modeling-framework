package io.vrap.rmf.raml.persistence.constructor;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;

import java.util.List;
import java.util.Optional;

import static io.vrap.rmf.raml.model.facets.FacetsPackage.Literals.*;
import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.*;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

public class TypeDeclarationConstructor extends Constructor<MappingNode> {
    private final EClass eClass;

    public TypeDeclarationConstructor(final EClass eClass) {
        this.eClass = eClass;
        eClass.getEAllAttributes().stream()
                .filter(eAttribute -> eAttribute != IDENTIFIABLE_ELEMENT__NAME)
                .forEach(this::addConstructor);
        final EReference typeReference = ANY_ANNOTATION_TYPE.isSuperTypeOf(eClass) ? ANY_ANNOTATION_TYPE__TYPE : ANY_TYPE__TYPE;
		addConstruct(typeReference, new TypeReferenceConstructor());
        addConstructor(OBJECT_TYPE_FACET__PROPERTIES, new PropertiesConstructor());
        addConstructor(new AnnotationsConstructor());
    }

    @Override
    public Object apply(final MappingNode node, final Scope typeDeclarationScope) {
        final List<NodeTuple> nodeTuples = node.getValue();
        nodeTuples.stream()
                .forEach(nodeTuple -> constructFeature(nodeTuple, typeDeclarationScope));

        return typeDeclarationScope.eObject();
    }

    protected void constructFeature(final NodeTuple featureNodeTuple, final Scope typeDeclarationScope) {
        final Optional<EStructuralFeature> feature = feature(eClass, featureNodeTuple);
        final Optional<Constructor<NodeTuple>> constructor = constructor(eClass, featureNodeTuple);

        if (constructor.isPresent()) {
            final Scope featureScope = feature.map(f -> typeDeclarationScope.with(f)).orElse(typeDeclarationScope);
            constructor.get().apply(featureNodeTuple, featureScope);
        } else {
            typeDeclarationScope.addError("No constructor for {0} found", featureNodeTuple.getKeyNode());
        }
    }
}
