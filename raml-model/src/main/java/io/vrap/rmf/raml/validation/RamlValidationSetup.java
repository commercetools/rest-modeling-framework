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
import org.eclipse.emf.ecore.impl.EValidatorRegistryImpl;
import org.eclipse.emf.ecore.util.Diagnostician;

import java.util.*;
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
    static Diagnostician setup(List<RamlValidator> customValidators) {
        final EValidator.Registry registry = new EValidatorRegistryImpl();
        PACKAGES.forEach(registry::remove);
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
        return new Diagnostician(registry);
    }

    static void setup() {
        final EValidator.Registry registry = EValidator.Registry.INSTANCE;
        PACKAGES.forEach(registry::remove);
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
            registry.put(ePackage, compositeValidator);
        }
    }
}
