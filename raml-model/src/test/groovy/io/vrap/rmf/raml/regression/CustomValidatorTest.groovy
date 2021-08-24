package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.modules.Library
import io.vrap.rmf.raml.model.modules.util.ModulesSwitch
import io.vrap.rmf.raml.model.types.AnyType
import io.vrap.rmf.raml.model.types.BuiltinType
import io.vrap.rmf.raml.model.types.util.TypesSwitch
import io.vrap.rmf.raml.validation.AbstractRamlValidator
import io.vrap.rmf.raml.validation.RamlValidator
import io.vrap.rmf.raml.validation.ResolvedRamlValidator
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.DiagnosticChain
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject

class CustomValidatorTest extends RegressionTest {
    class MyApiValidator extends AbstractRamlValidator implements RamlValidator {
        class ApiSwitch extends ModulesSwitch<List<Diagnostic>> {
            @Override
            List<Diagnostic> caseApi(Api object) {
                final List<Diagnostic> validationErrors = new ArrayList<>()

                validationErrors.add(error(object, "invalid: {0}", object.title))

                return validationErrors
            }

            @Override
            List<Diagnostic> defaultCase(EObject object) {
                return new ArrayList<Diagnostic>()
            }
        }

        private final ModulesSwitch<List<Diagnostic>> validator = new ApiSwitch()

        @Override
        boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
            final List<Diagnostic> validationErrors = new ArrayList<>();

            validationErrors.addAll(validator.doSwitch(eObject));
            validationErrors.forEach(diagnostics.&add);

            return validationErrors.isEmpty();

        }
    }

    class MyTypeValidator extends AbstractRamlValidator implements RamlValidator {
        class TypeSwitch extends TypesSwitch<List<Diagnostic>> {
            @Override
            List<Diagnostic> caseAnyType(AnyType object) {
                final List<Diagnostic> validationErrors = new ArrayList<>();

                if (object.eContainer() instanceof Library && !BuiltinType.of(object.name).isPresent()) {
                    validationErrors.add(error(object, "invalid: {0}", object.name))
                }

                return validationErrors;
            }

            @Override
            List<Diagnostic> defaultCase(EObject object) {
                return new ArrayList<Diagnostic>()
            }
        }

        private final TypesSwitch<List<Diagnostic>> validator = new TypeSwitch()

        @Override
        boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
            final List<Diagnostic> validationErrors = new ArrayList<>();

            validationErrors.addAll(validator.doSwitch(eObject));
            validationErrors.forEach(diagnostics.&add);

            return validationErrors.isEmpty();
        }
    }

    class MyResolvedTypeValidator extends AbstractRamlValidator implements ResolvedRamlValidator {
        class ResolvedTypeSwitch extends TypesSwitch<List<Diagnostic>> {
            @Override
            List<Diagnostic> caseAnyType(AnyType object) {
                final List<Diagnostic> validationErrors = new ArrayList<>();

                validationErrors.add(error(object, "invalid resolved: {0}", object.name))

                return validationErrors;
            }

            @Override
            List<Diagnostic> defaultCase(EObject object) {
                return new ArrayList<Diagnostic>()
            }
        }

        private final TypesSwitch<List<Diagnostic>> validator = new ResolvedTypeSwitch()

        @Override
        boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
            final List<Diagnostic> validationErrors = new ArrayList<>();

            validationErrors.addAll(validator.doSwitch(eObject));
            validationErrors.forEach(diagnostics.&add);

            return validationErrors.isEmpty();
        }
    }

    class MyLibraryValidator extends AbstractRamlValidator implements RamlValidator {
        class ApiSwitch extends ModulesSwitch<List<Diagnostic>> {
            @Override
            List<Diagnostic> caseLibrary(Library object) {
                final List<Diagnostic> validationErrors = new ArrayList<>()

                if (!object.eResource().getURI().toString().contains("builtin-types.raml")) {
                    validationErrors.add(error(object, "invalid: {0}", object.usage))
                }

                return validationErrors;
            }

            @Override
            List<Diagnostic> defaultCase(EObject object) {
                return new ArrayList<Diagnostic>()
            }
        }

        private final ModulesSwitch<List<Diagnostic>> validator = new ApiSwitch()

        @Override
        boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
            final List<Diagnostic> validationErrors = new ArrayList<>();

            validationErrors.addAll(validator.doSwitch(eObject));
            validationErrors.forEach(diagnostics.&add);

            return validationErrors.isEmpty();
        }
    }

    def "custom-validation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''
        #%RAML 1.0
        title: Some API
                ''', Arrays.asList(new MyApiValidator())
        )
        then:
        ramlModelResult.validationResults.size() == 1
        ramlModelResult.validationResults[0].message == "invalid: Some API"
    }

    def "custom-library-validation"() {
        when:
        writeFile(
                "lib.raml",
                '''\
                #%RAML 1.0 Library
                usage: defines types
                
                types:
                  invalidType: string
        ''')

        RamlModelResult<Api> ramlModelResult = constructApi(
                'api.raml',
                Arrays.asList("lib.raml"),
                '''
                #%RAML 1.0
                title: Some API
                
                uses:
                    lib: lib.raml
                    
                types:
                   invalidResolved: string
                ''',
                Arrays.asList(new MyLibraryValidator(), new MyTypeValidator(), new MyResolvedTypeValidator())
        )
        then:
        ramlModelResult.validationResults.size() == 2
        ramlModelResult.validationResults[0].message == "invalid: defines types"
        ramlModelResult.validationResults[1].message == "invalid: invalidType"
    }

    def "custom-resolved-validation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''
                #%RAML 1.0
                title: Some API
                
                types:
                   invalidResolved: string
                ''',
                Arrays.asList(new MyResolvedTypeValidator())
        )
        then:
        ramlModelResult.validationResults.size() == 1
        ramlModelResult.validationResults[0].message == "invalid resolved: invalidResolved"
    }
}
