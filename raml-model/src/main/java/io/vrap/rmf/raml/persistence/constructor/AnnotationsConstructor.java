package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.facets.Instance;
import io.vrap.rmf.raml.model.types.Annotation;
import io.vrap.rmf.raml.model.types.TypesFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.regex.Pattern;

import static io.vrap.rmf.raml.model.facets.FacetsPackage.Literals.STRING_INSTANCE;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ANNOTATIONS_FACET__ANNOTATIONS;

public class AnnotationsConstructor extends KeyNodeMatchConstructor {
    private final static TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;
    private final static Pattern PATTERN = Pattern.compile("\\(.+\\)");

    public AnnotationsConstructor() {
        super(STRING_INSTANCE, ANNOTATIONS_FACET__ANNOTATIONS, PATTERN);
    }

    @Override
    protected Object construct(final String key, final Scope scope) {
        final Annotation annotation = TYPES_FACTORY.createAnnotation();
        final EObject annotationType = EcoreUtil.create(eClass);
        annotation.setValue((Instance) annotationType);
        final Scope annotationScope = scope.with(reference);
        annotationScope.setValue(annotation);

        return annotation;
    }
}
