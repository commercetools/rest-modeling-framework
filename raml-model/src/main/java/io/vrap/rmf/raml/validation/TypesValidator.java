package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.facets.ArrayTypeFacet;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.util.Map;

public class TypesValidator extends AbstractRamlValidator {

    @Override
    public boolean validate(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        final boolean isValid = new TypesValidatingVisitor().doSwitch(eObject);
        return isValid;
    }

    private class TypesValidatingVisitor extends TypesSwitch<Boolean> {

        @Override
        public Boolean defaultCase(EObject object) {
            return true;
        }

        @Override
        public Boolean caseArrayTypeFacet(final ArrayTypeFacet arrayType) {
            boolean rangeIsValid = arrayType.getMinItems() == null
                    || arrayType.getMaxItems() == null
                    || arrayType.getMinItems() <= arrayType.getMaxItems();

            if (!rangeIsValid) {
                error("Facet 'minItems' must be <= 'maxItems'", arrayType);
            }
            return rangeIsValid;
        }
    }
}
