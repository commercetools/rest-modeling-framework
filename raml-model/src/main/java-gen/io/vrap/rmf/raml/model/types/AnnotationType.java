/**
 */
package io.vrap.rmf.raml.model.types;

import io.vrap.rmf.raml.model.elements.DocumentableElement;
import io.vrap.rmf.raml.model.elements.IdentifiableElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Annotation types are declared using the OPTIONAL root-level annotationTypes node.
 * The value of the annotationsType node is a map whose keys define annotation type names,
 * also referred to as annotations, and whose values are key-value pairs called annotation type declarations.
 * An annotation type declaration has the same syntax as a data type declaration,
 * and its facets have the same syntax as the corresponding ones for data types, but with the addition of the allowedTargets facet.
 * An annotation type declaration constrains the value of an annotation of that type just as a data type declaration constrains
 * the value of a URI parameter, query parameter, header, or body of that type.
 * The allowedTargets node restricts the kinds of locations where the annotation can be applied. Annotation types, like data types,
 * can extend other data types, but annotation types themselves can neither be extended nor used anywhere data types can be used.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.AnnotationType#getAllowedTargets <em>Allowed Targets</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getAnnotationType()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface AnnotationType extends Type<AnyAnnotationType>, DocumentableElement, IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Allowed Targets</b></em>' attribute list.
	 * The list contents are of type {@link io.vrap.rmf.raml.model.types.AnnotationTarget}.
	 * The literals are from the enumeration {@link io.vrap.rmf.raml.model.types.AnnotationTarget}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The locations to which annotations are restricted.
	 * If this node is specified, annotations of this type may be applied only on a node corresponding to one of the locations.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Allowed Targets</em>' attribute list.
	 * @see io.vrap.rmf.raml.model.types.AnnotationTarget
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getAnnotationType_AllowedTargets()
	 * @model unique="false"
	 * @generated
	 */
	EList<AnnotationTarget> getAllowedTargets();

} // AnnotationType
