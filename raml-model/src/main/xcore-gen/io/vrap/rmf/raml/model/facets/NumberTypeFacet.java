/**
 */
package io.vrap.rmf.raml.model.facets;

import java.math.BigDecimal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Number Type Facet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMinimum <em>Minimum</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMaximum <em>Maximum</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getFormat <em>Format</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMultipleOf <em>Multiple Of</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getNumberTypeFacet()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface NumberTypeFacet extends EObject {
	/**
	 * Returns the value of the '<em><b>Minimum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The minimum value of the parameter. Applicable only to parameters of type number or integer.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Minimum</em>' attribute.
	 * @see #setMinimum(BigDecimal)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getNumberTypeFacet_Minimum()
	 * @model unique="false"
	 * @generated
	 */
	BigDecimal getMinimum();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMinimum <em>Minimum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minimum</em>' attribute.
	 * @see #getMinimum()
	 * @generated
	 */
	void setMinimum(BigDecimal value);

	/**
	 * Returns the value of the '<em><b>Maximum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The maximum value of the parameter. Applicable only to parameters of type number or integer.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Maximum</em>' attribute.
	 * @see #setMaximum(BigDecimal)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getNumberTypeFacet_Maximum()
	 * @model unique="false"
	 * @generated
	 */
	BigDecimal getMaximum();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMaximum <em>Maximum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Maximum</em>' attribute.
	 * @see #getMaximum()
	 * @generated
	 */
	void setMaximum(BigDecimal value);

	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * The literals are from the enumeration {@link io.vrap.rmf.raml.model.facets.NumberFormat}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The format of the value.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see io.vrap.rmf.raml.model.facets.NumberFormat
	 * @see #setFormat(NumberFormat)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getNumberTypeFacet_Format()
	 * @model unique="false"
	 * @generated
	 */
	NumberFormat getFormat();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see io.vrap.rmf.raml.model.facets.NumberFormat
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(NumberFormat value);

	/**
	 * Returns the value of the '<em><b>Multiple Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A numeric instance is valid against "multipleOf" if the result of dividing the instance
	 * by this keyword's value is an integer.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Multiple Of</em>' attribute.
	 * @see #setMultipleOf(Integer)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getNumberTypeFacet_MultipleOf()
	 * @model unique="false"
	 * @generated
	 */
	Integer getMultipleOf();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMultipleOf <em>Multiple Of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiple Of</em>' attribute.
	 * @see #getMultipleOf()
	 * @generated
	 */
	void setMultipleOf(Integer value);

} // NumberTypeFacet
