/**
 */
package io.vrap.rmf.raml.model.types;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.TypeContainer#getUses <em>Uses</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.TypeContainer#getTypes <em>Types</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.TypeContainer#getAnnotationTypes <em>Annotation Types</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getTypeContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface TypeContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Uses</b></em>' containment reference list.
	 * The list contents are of type {@link io.vrap.rmf.raml.model.types.LibraryUse}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uses</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uses</em>' containment reference list.
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getTypeContainer_Uses()
	 * @model containment="true"
	 * @generated
	 */
	EList<LibraryUse> getUses();

	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link io.vrap.rmf.raml.model.types.AnyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getTypeContainer_Types()
	 * @model containment="true"
	 * @generated
	 */
	EList<AnyType> getTypes();

	/**
	 * Returns the value of the '<em><b>Annotation Types</b></em>' containment reference list.
	 * The list contents are of type {@link io.vrap.rmf.raml.model.types.AnnotationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Annotation Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotation Types</em>' containment reference list.
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getTypeContainer_AnnotationTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<AnnotationType> getAnnotationTypes();

} // TypeContainer
