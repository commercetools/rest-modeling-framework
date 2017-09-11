package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.facets.ObjectInstance;
import io.vrap.rmf.raml.model.security.SecuredBy;
import io.vrap.rmf.raml.model.security.SecurityFactory;
import io.vrap.rmf.raml.model.security.SecurityScheme;
import io.vrap.rmf.raml.model.types.Example;
import io.vrap.rmf.raml.model.types.TypesFactory;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.common.util.ECollections;

import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.security.SecurityPackage.Literals.SECURED_BY__PARAMETERS;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

public abstract class BaseConstructor extends AbstractConstructor {
    private final InstanceConstructor instanceConstructor = new InstanceConstructor();


    @Override
    public Object visitSecuredBy(RAMLParser.SecuredByContext ctx) {
        final SecuredBy securedBy = SecurityFactory.eINSTANCE.createSecuredBy();
        scope.setValue(securedBy, ctx.getStart());

        final SecurityScheme scheme = (SecurityScheme) scope.getEObjectByName(ctx.name.getText());
        securedBy.setScheme(scheme);

        if (ctx.parameters != null) {
            instanceConstructor.withinScope(scope.with(securedBy, SECURED_BY__PARAMETERS), securedByParametersScope -> {
                final ObjectInstance parameters = (ObjectInstance) instanceConstructor.visitObjectInstance(ctx.parameters);
                return  parameters;
            });
        }

        return securedBy;
    }

    @Override
    public Object visitEnumFacet(RAMLParser.EnumFacetContext enumFacet) {
        return instanceConstructor.withinScope(scope.with(ENUM_FACET__ENUM), enumScope ->
                enumFacet.instance().stream()
                        .map(instanceConstructor::visitInstance)
                        .collect(Collectors.toList()));
    }

    @Override
    public Object visitInstance(RAMLParser.InstanceContext instance) {
        return instanceConstructor.withinScope(scope, instanceScope ->
            instanceConstructor.visitInstance(instance));
    }

    @Override
    public Object visitDefaultFacet(RAMLParser.DefaultFacetContext defaultFacet) {
        return instanceConstructor.withinScope(scope.with(ANY_TYPE__DEFAULT), defaultScope ->
                instanceConstructor.visitInstance(defaultFacet.instance()));
    }

    @Override
    public Object visitExampleFacet(RAMLParser.ExampleFacetContext exampleFacet) {
        final Example example = create(EXAMPLE, exampleFacet.getStart());
        return withinScope(scope.with(ANY_TYPE__EXAMPLE), exampleScope -> {
            scope.setValue(example, exampleFacet.getStart());
            instanceConstructor.withinScope(exampleScope.with(example, EXAMPLE__VALUE), exampleValueScope ->
                    instanceConstructor.visitInstance(exampleFacet.instance()));
            return example;
        });
    }

    @Override
    public Object visitExamplesFacet(RAMLParser.ExamplesFacetContext examplesFacet) {
        return withinScope(scope.with(ANY_TYPE__EXAMPLES), examplesScope ->
                ECollections.asEList(examplesFacet.namedExample().stream()
                        .map(this::visitNamedExample)
                        .collect(Collectors.toList()))
        );
    }

    @Override
    public Object visitNamedExample(RAMLParser.NamedExampleContext namedExample) {
        final Example example = create(EXAMPLE, namedExample.getStart());
        example.setName(namedExample.name.getText());
        scope.setValue(example, namedExample.getStart());
        return instanceConstructor.withinScope(scope.with(example, EXAMPLE__VALUE), exampleValueScope ->
                instanceConstructor.visitInstance(namedExample.instance())
        );
    }
}
