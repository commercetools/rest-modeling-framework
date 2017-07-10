/**
 */
package io.vrap.rmf.raml.model.elements;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Documentable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This describes the properties of an element that can be documented.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.elements.DocumentableElement#getDescription <em>Description</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.elements.DocumentableElement#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.elements.ElementsPackage#getDocumentableElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface DocumentableElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A substantial, human-friendly description of the type. Its value is a string and MAY be formatted using markdown.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see io.vrap.rmf.raml.model.elements.ElementsPackage#getDocumentableElement_Description()
	 * @model unique="false"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.elements.DocumentableElement#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An alternate, human-friendly name for the type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see io.vrap.rmf.raml.model.elements.ElementsPackage#getDocumentableElement_DisplayName()
	 * @model unique="false"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.elements.DocumentableElement#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

} // DocumentableElement
