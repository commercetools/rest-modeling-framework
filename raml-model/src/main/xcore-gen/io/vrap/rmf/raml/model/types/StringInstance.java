/**
 */
package io.vrap.rmf.raml.model.types;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>String Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.StringInstance#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getStringInstance()
 * @model
 * @generated
 */
public interface StringInstance extends Instance<StringType> {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getStringInstance_Value()
	 * @model unique="false"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.types.StringInstance#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // StringInstance
