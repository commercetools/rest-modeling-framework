/**
 */
package io.vrap.rmf.raml.model.types;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Integer Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.IntegerInstance#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getIntegerInstance()
 * @model
 * @generated
 */
public interface IntegerInstance extends Instance<IntegerType> {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Long)
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getIntegerInstance_Value()
	 * @model unique="false"
	 * @generated
	 */
	Long getValue();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.types.IntegerInstance#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Long value);

} // IntegerInstance
