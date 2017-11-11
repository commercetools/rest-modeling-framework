package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.facets.*;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypesValidator extends AbstractRamlValidator {
    private final TypesValidatingVisitor typesValidatingVisitor = new TypesValidatingVisitor();
    private final ExamplesValidatingVisitor examplesValidatingVisitor = new ExamplesValidatingVisitor();

    @Override
    public boolean validate(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        final Diagnostic diagnostic = typesValidatingVisitor.doSwitch(eObject);
        if (diagnostic != null) {
            diagnostics.add(diagnostic);
            return false;
        } else {
            final List<Diagnostic> examplesDiagnostics = examplesValidatingVisitor.doSwitch(eObject);
            examplesDiagnostics.forEach(diagnostics::add);
            return examplesDiagnostics.isEmpty();
        }
    }

    private class TypesValidatingVisitor extends TypesSwitch<Diagnostic> {

        @Override
        public Diagnostic defaultCase(EObject object) {
            return null;
        }

        @Override
        public Diagnostic caseArrayTypeFacet(final ArrayTypeFacet arrayType) {
            boolean rangeIsValid = arrayType.getMinItems() == null
                    || arrayType.getMaxItems() == null
                    || arrayType.getMinItems() <= arrayType.getMaxItems();

            return rangeIsValid ? null : error("Facet 'minItems' must be <= 'maxItems'", arrayType);
        }

        @Override
        public Diagnostic caseStringTypeFacet(final StringTypeFacet stringType) {
            boolean rangeIsValid = stringType.getMinLength() == null
                    || stringType.getMaxLength() == null
                    || stringType.getMinLength() <= stringType.getMaxLength();

            return rangeIsValid ? null : error("Facet 'minLength' must be <= 'maxLength'", stringType);
        }

        @Override
        public Diagnostic caseNumberTypeFacet(final NumberTypeFacet numberType) {
            boolean rangeIsValid = numberType.getMinimum() == null
                    || numberType.getMaximum() == null
                    || numberType.getMinimum().compareTo(numberType.getMaximum()) <= 0;

            return rangeIsValid ? null : error("Facet 'minimum' must be <= 'maximum'", numberType);
        }

        @Override
        public Diagnostic caseIntegerTypeFacet(final IntegerTypeFacet integerType) {
            boolean rangeIsValid = integerType.getMinimum() == null
                    || integerType.getMaximum() == null
                    || integerType.getMinimum().compareTo(integerType.getMaximum()) <= 0;

            return rangeIsValid ? null : error("Facet 'minimum' must be <= 'maximum'", integerType);
        }

        @Override
        public Diagnostic caseFileTypeFacet(final FileTypeFacet fileType) {
            boolean rangeIsValid = fileType.getMinLength() == null
                    || fileType.getMaxLength() == null
                    || fileType.getMinLength() <= fileType.getMaxLength();

            return rangeIsValid ? null : error("Facet 'minLength' must be <= 'maxLength'", fileType);
        }
    }

    private static class ExamplesValidatingVisitor extends TypesSwitch<List<Diagnostic>> {
        private InstanceValidator instanceValidator = new InstanceValidator();

        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseAnyType(final AnyType anyType) {
            final List<Diagnostic> validationResults = anyType.getExamples().stream()
                    .flatMap(example -> instanceValidator.validate(example.getValue(), anyType).stream())
                    .collect(Collectors.toList());
            return validationResults;
        }
    }
}
