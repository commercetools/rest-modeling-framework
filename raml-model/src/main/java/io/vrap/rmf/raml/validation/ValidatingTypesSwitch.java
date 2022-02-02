package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

import java.util.Collections;
import java.util.List;

abstract class ValidatingTypesSwitch extends TypesSwitch<List<Diagnostic>> implements DiagnosticsCreator {

    @Override
    public final List<Diagnostic> defaultCase(EObject object) {
        return Collections.emptyList();
    }
}
