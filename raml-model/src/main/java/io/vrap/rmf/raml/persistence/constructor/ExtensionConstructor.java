package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.ApiExtension;
import io.vrap.rmf.raml.model.modules.Extension;
import io.vrap.rmf.raml.model.modules.ModulesPackage;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.util.function.Predicate;

public class ExtensionConstructor extends ApiConstructor {
    @Override
    public EObject construct(final RAMLParser parser, final Scope scope) {
        final DeclarationResolver declarationResolver = new DeclarationResolver();
        final RAMLParser.ExtensionContext extensionContext = parser.extension();
        declarationResolver.resolve(extensionContext, scope);

        final Extension extension = (Extension) withinScope(scope,
                s -> visitExtension(extensionContext));
        return extension;
    }

    @Override
    public Object visitExtension(final RAMLParser.ExtensionContext ctx) {
        final EObject rootObject = scope.getResource().getContents().get(0);

        return withinScope(scope.with(rootObject), rootScope -> {
            if (ctx.extendsFacet().isEmpty()) {
                scope.addError("Required 'extends' facet missing");
            } else {
                ctx.extendsFacet().forEach(this::visitExtendsFacet);
            }
            final Predicate<RAMLParser.TypeContainerFacetsContext> isSecuritySchemesFacet =
                    typeContainerFacets -> typeContainerFacets.securitySchemesFacet() != null;

            // TODO move to first pass
            // order is relevant here: first create security schemes
            ctx.typeContainerFacets().stream()
                    .filter(isSecuritySchemesFacet)
                    .forEach(this::visitTypeContainerFacets);

            ctx.typeContainerFacets().stream()
                    .filter(isSecuritySchemesFacet.negate())
                    .forEach(this::visitTypeContainerFacets);

            ctx.apiFacets().forEach(this::visitApiFacets);

            return rootObject;
        });
    }

    @Override
    public Object visitExtendsFacet(final RAMLParser.ExtendsFacetContext extendsFacet) {
        final String extendsUri = extendsFacet.uri.getText();
        final Resource extendsResource = scope.getResource(extendsUri);
        final EList<org.eclipse.emf.ecore.resource.Resource.Diagnostic> errors = extendsResource.getErrors();
        if (errors.isEmpty()) {
            final EList<EObject> contents = extendsResource.getContents();
            if (contents.size() != 1) {
                scope.addErrorWithLocation("Extended api definition is invalid",
                        extendsFacet.getStart());
            } else {
                final EObject extendsEObject = contents.get(0);
                if (extendsEObject instanceof Api || extendsEObject instanceof ApiExtension) {
                    scope.setValue(ModulesPackage.Literals.API_EXTENSION__EXTENDS, extendsEObject, extendsFacet.uri.getStart());
                } else {
                    scope.addErrorWithLocation("Extended api definition has invalid type ''{0}''",
                            extendsFacet.getStart(), extendsEObject.eClass().getName());
                }
            }
        } else {
            scope.getResource().getErrors().addAll(errors); // TODO really necessary?
        }
        return super.visitExtendsFacet(extendsFacet);
    }
}
