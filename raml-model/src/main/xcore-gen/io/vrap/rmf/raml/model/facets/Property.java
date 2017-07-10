/**
 */
package io.vrap.rmf.raml.model.facets;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.facets.Property#getName <em>Name</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.Property#getType <em>Type</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.Property#getRequired <em>Required</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getProperty()
 * @model
 * @generated
 */
public interface Property<T> extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name of this property.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getProperty_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.Property#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type of this property.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Object)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getProperty_Type()
	 * @model kind="reference"
	 * @generated
	 */
	T getType();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.Property#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(T value);

	/**
	 * Returns the value of the '<em><b>Required</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies that the property is required or not.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Required</em>' attribute.
	 * @see #setRequired(Boolean)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getProperty_Required()
	 * @model default="true" unique="false"
	 * @generated
	 */
	Boolean getRequired();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.Property#getRequired <em>Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required</em>' attribute.
	 * @see #getRequired()
	 * @generated
	 */
	void setRequired(Boolean value);

} // Property
