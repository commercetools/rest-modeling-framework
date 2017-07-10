package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.types.Library;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.Optional;

import static io.vrap.functional.utils.Classes.as;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

/**
 * Construct the library uses {@link Library#getUses()} referenced objects.
 */
public class LibraryUsesConstructor extends AbstractIdentifiableElementsConstructor {

    @Override
    protected EObject create(final NodeTuple nodeTuple, final Scope libraryScope, final String name) {
        final Optional<String> libraryReference = as(ScalarNode.class, nodeTuple.getValueNode()).map(ScalarNode::getValue);
        final EObject library;
        if (libraryReference.isPresent()) {
            library = create(nodeTuple, libraryScope, name, libraryReference.get());
            libraryScope.setValue(library);
        } else {
            libraryScope.addError("Library uses {0} is invalid", nodeTuple.getKeyNode());
            library = null;
        }
        return library;
    }

    private EObject create(final NodeTuple nodeTuple, final Scope libraryScope, final String name, final String libraryReference) {
        final Resource libraryResource = libraryScope.getResource(libraryReference);
        final EList<EObject> contents = libraryResource.getContents();
        final EObject libraryUse;

        if (contents.size() == 1) {
            libraryUse = EcoreUtil.create(LIBRARY_USE);
            final EObject usedLibrary = contents.get(0);
            libraryScope.with(libraryUse, LIBRARY_USE__NAME).setValue(name);
            libraryScope.with(libraryUse, LIBRARY_USE__LIBRARY).setValue(usedLibrary);
        } else {
            libraryScope.addError("Library {0} couldn't be loaded from {1}", libraryReference, nodeTuple.getKeyNode());
            libraryUse = null;
        }

        return libraryUse;
    }
}
