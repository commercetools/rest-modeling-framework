package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.facets.ArrayTypeFacet;
import io.vrap.rmf.raml.model.facets.IntegerTypeFacet;
import io.vrap.rmf.raml.model.facets.NumberTypeFacet;
import io.vrap.rmf.raml.model.facets.StringTypeFacet;
import io.vrap.rmf.raml.model.types.IntegerType;
import io.vrap.rmf.raml.model.types.NumberType;
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

        @Override
        public Boolean caseStringTypeFacet(final StringTypeFacet stringType) {
            boolean rangeIsValid = stringType.getMinLength() == null
                    || stringType.getMaxLength() == null
                    || stringType.getMinLength() <= stringType.getMaxLength();

            if (!rangeIsValid) {
                error("Facet 'minLength' must be <= 'maxLength'", stringType);
            }
            return rangeIsValid;
        }

        @Override
        public Boolean caseNumberTypeFacet(final NumberTypeFacet numberType) {
            boolean rangeIsValid = numberType.getMinimum() == null
                    || numberType.getMaximum() == null
                    || numberType.getMinimum().compareTo(numberType.getMaximum()) <= 0;

            if (!rangeIsValid) {
                error("Facet 'minimum' must be <= 'maximum'", numberType);
            }
            return rangeIsValid;
        }

        @Override
        public Boolean caseIntegerTypeFacet(final IntegerTypeFacet integerType) {
            boolean rangeIsValid = integerType.getMinimum() == null
                    || integerType.getMaximum() == null
                    || integerType.getMinimum().compareTo(integerType.getMaximum()) <= 0;

            if (!rangeIsValid) {
                error("Facet 'minimum' must be <= 'maximum'", integerType);
            }
            return rangeIsValid;
        }
    }
}
