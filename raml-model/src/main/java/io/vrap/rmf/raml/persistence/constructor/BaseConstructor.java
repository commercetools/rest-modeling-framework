package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.types.Example;
import io.vrap.rmf.raml.model.types.TypesFactory;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.common.util.ECollections;

import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

public abstract class BaseConstructor extends AbstractConstructor {
    private final InstanceConstructor instanceConstructor = new InstanceConstructor();

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
        final Example example = TypesFactory.eINSTANCE.createExample();
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
        final Example example = TypesFactory.eINSTANCE.createExample();
        example.setName(namedExample.name.getText());
        scope.setValue(example, namedExample.getStart());
        return instanceConstructor.withinScope(scope.with(example, EXAMPLE__VALUE), exampleValueScope ->
                instanceConstructor.visitInstance(namedExample.instance())
        );
    }
}
