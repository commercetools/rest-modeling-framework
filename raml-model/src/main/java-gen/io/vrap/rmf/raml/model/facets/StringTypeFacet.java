/**
 */
package io.vrap.rmf.raml.model.facets;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>String Type Facet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.facets.StringTypeFacet#getPattern <em>Pattern</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.StringTypeFacet#getMinLength <em>Min Length</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.StringTypeFacet#getMaxLength <em>Max Length</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getStringTypeFacet()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface StringTypeFacet extends EObject {
	/**
	 * Returns the value of the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Regular expression that this string SHOULD match.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Pattern</em>' attribute.
	 * @see #setPattern(String)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getStringTypeFacet_Pattern()
	 * @model unique="false"
	 * @generated
	 */
	String getPattern();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.StringTypeFacet#getPattern <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern</em>' attribute.
	 * @see #getPattern()
	 * @generated
	 */
	void setPattern(String value);

	/**
	 * Returns the value of the '<em><b>Min Length</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Minimum length of the string. Value MUST be equal to or greater than 0.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min Length</em>' attribute.
	 * @see #setMinLength(Integer)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getStringTypeFacet_MinLength()
	 * @model default="0" unique="false"
	 * @generated
	 */
	Integer getMinLength();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.StringTypeFacet#getMinLength <em>Min Length</em>}' attribute.
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
	 * Maximum length of the string. Value MUST be equal to or greater than 0.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Length</em>' attribute.
	 * @see #setMaxLength(Integer)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getStringTypeFacet_MaxLength()
	 * @model default="2147483647" unique="false"
	 * @generated
	 */
	Integer getMaxLength();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.StringTypeFacet#getMaxLength <em>Max Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Length</em>' attribute.
	 * @see #getMaxLength()
	 * @generated
	 */
	void setMaxLength(Integer value);

} // StringTypeFacet
