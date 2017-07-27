package io.vrap.rmf.raml.model.util;

import io.vrap.rmf.raml.model.types.AnyType;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ANY_TYPE__TYPE;

/**
 * This cross references calculates the sub types of a given type.
 */
public class SuperTypeCrossReferencer extends EcoreUtil.UsageCrossReferencer {
    protected SuperTypeCrossReferencer(final Resource resource) {
        super(resource);
    }

    @Override
    protected boolean crossReference(final EObject eObject, final EReference eReference, final EObject crossReferencedEObject) {
        return ANY_TYPE__TYPE.equals(eReference);
    }

    /**
     * Returns the sub types of the given super type.
     *
     * @param superType the super type
     * @return list of sub types
     */
    public static EList<AnyType> getSubTypes(final AnyType superType) {
        final SuperTypeCrossReferencer superTypeCrossReferencer = new SuperTypeCrossReferencer(superType.eResource());
        return ECollections.asEList(superTypeCrossReferencer.findUsage(superType).stream()
                .map(setting -> setting.getEObject())
                .map(AnyType.class::cast)
                .collect(Collectors.toList()));
    }
}
