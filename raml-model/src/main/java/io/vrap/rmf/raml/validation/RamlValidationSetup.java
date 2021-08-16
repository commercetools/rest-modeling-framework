package io.vrap.rmf.raml.validation;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.elements.ElementsPackage;
import io.vrap.rmf.raml.model.modules.ModulesPackage;
import io.vrap.rmf.raml.model.resources.ResourcesPackage;
import io.vrap.rmf.raml.model.responses.ResponsesPackage;
import io.vrap.rmf.raml.model.security.SecurityPackage;
import io.vrap.rmf.raml.model.types.TypesPackage;
import io.vrap.rmf.raml.model.values.ValuesPackage;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This interface provides methods to setup the RAML metamodel.
 */
public interface RamlValidationSetup {
    List<EPackage> PACKAGES = Arrays.asList(
            ElementsPackage.eINSTANCE, ValuesPackage.eINSTANCE, ModulesPackage.eINSTANCE, ResourcesPackage.eINSTANCE,
            ResponsesPackage.eINSTANCE, SecurityPackage.eINSTANCE, TypesPackage.eINSTANCE);

    /**
     * Registers validators.
     */
    static void setup(List<RamlValidator> customValidators) {
        final EValidator.Registry registry = EValidator.Registry.INSTANCE;
        final List<EValidator> eValidators = customValidators.stream().map(ramlValidator -> (EValidator)ramlValidator).collect(Collectors.toList());

        registry.put(TypesPackage.eINSTANCE, new TypesValidator());
        registry.put(ModulesPackage.eINSTANCE, new ModulesValidator());
        registry.put(ResourcesPackage.eINSTANCE, new ResourcesValidator());
        registry.put(ResponsesPackage.eINSTANCE, new ResponsesValidator());

        final RamlObjectValidator ramlObjectValidator = new RamlObjectValidator();
        for (final EPackage ePackage : PACKAGES) {
            final CompositeValidator compositeValidator = new CompositeValidator();
            compositeValidator.add(ramlObjectValidator);
            final EValidator validator = registry.getEValidator(ePackage);
            if (validator != null) {
                compositeValidator.add(validator);
            }
            if (eValidators.size() > 0) {
                compositeValidator.addAll(eValidators);
            }
            registry.put(ePackage, compositeValidator);
        }
    }

    static void setup() {
        setup(Lists.newArrayList());
    }
}
