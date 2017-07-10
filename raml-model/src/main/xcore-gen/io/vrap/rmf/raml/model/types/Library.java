/**
 */
package io.vrap.rmf.raml.model.types;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.Library#getUsage <em>Usage</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getLibrary()
 * @model
 * @generated
 */
public interface Library extends Annotatable, TypeContainer {
	/**
	 * Returns the value of the '<em><b>Usage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Usage</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Usage</em>' attribute.
	 * @see #setUsage(String)
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getLibrary_Usage()
	 * @model unique="false"
	 * @generated
	 */
	String getUsage();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.types.Library#getUsage <em>Usage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Usage</em>' attribute.
	 * @see #getUsage()
	 * @generated
	 */
	void setUsage(String value);

} // Library
