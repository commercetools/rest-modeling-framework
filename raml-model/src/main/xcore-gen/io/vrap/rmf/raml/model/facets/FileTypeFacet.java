/**
 */
package io.vrap.rmf.raml.model.facets;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Type Facet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.facets.FileTypeFacet#getFileTypes <em>File Types</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.FileTypeFacet#getMinLength <em>Min Length</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.FileTypeFacet#getMaxLength <em>Max Length</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getFileTypeFacet()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface FileTypeFacet extends EObject {
	/**
	 * Returns the value of the '<em><b>File Types</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of valid content-type strings for the file.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>File Types</em>' attribute list.
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getFileTypeFacet_FileTypes()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getFileTypes();

	/**
	 * Returns the value of the '<em><b>Min Length</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies the minimum number of bytes for a parameter value. The value MUST be equal to or greater than 0.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min Length</em>' attribute.
	 * @see #setMinLength(Integer)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getFileTypeFacet_MinLength()
	 * @model default="0" unique="false"
	 * @generated
	 */
	Integer getMinLength();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.FileTypeFacet#getMinLength <em>Min Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Length</em>' attribute.
	 * @see #getMinLength()
	 * @generated
	 */
	void setMinLength(Integer value);

	/**
	 * Returns the value of the '<em><b>Max Length</b></em>' attribute.
	 * The default value is <code>"2147483647"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies the maximum number of bytes for a parameter value. The value MUST be equal to or greater than 0.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Length</em>' attribute.
	 * @see #setMaxLength(Integer)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getFileTypeFacet_MaxLength()
	 * @model default="2147483647" unique="false"
	 * @generated
	 */
	Integer getMaxLength();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.FileTypeFacet#getMaxLength <em>Max Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Length</em>' attribute.
	 * @see #getMaxLength()
	 * @generated
	 */
	void setMaxLength(Integer value);

} // FileTypeFacet
