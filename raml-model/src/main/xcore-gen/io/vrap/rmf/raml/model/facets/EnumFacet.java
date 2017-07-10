/**
 */
package io.vrap.rmf.raml.model.facets;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Facet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.facets.EnumFacet#getEnum <em>Enum</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getEnumFacet()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface EnumFacet extends EObject {
	/**
	 * Returns the value of the '<em><b>Enum</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An enumeration of all the possible values of instances of this type.
	 * The value is an array containing representations of these possible values.
	 * An instance of this type MUST be equal to one of these values.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enum</em>' attribute list.
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getEnumFacet_Enum()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getEnum();

} // EnumFacet
