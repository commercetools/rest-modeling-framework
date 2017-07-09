/**
 */
package io.vrap.rmf.raml.model.elements;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Typed Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.elements.TypedElement#getTypeReference <em>Type Reference</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.elements.TypedElement#getTypeDeclaration <em>Type Declaration</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.elements.TypedElement#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.elements.ElementsPackage#getTypedElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface TypedElement<T> extends EObject {
	/**
	 * Returns the value of the '<em><b>Type Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type this element references.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Reference</em>' reference.
	 * @see #setTypeReference(Object)
	 * @see io.vrap.rmf.raml.model.elements.ElementsPackage#getTypedElement_TypeReference()
	 * @model kind="reference"
	 * @generated
	 */
	T getTypeReference();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.elements.TypedElement#getTypeReference <em>Type Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Reference</em>' reference.
	 * @see #getTypeReference()
	 * @generated
	 */
	void setTypeReference(T value);

	/**
	 * Returns the value of the '<em><b>Type Declaration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A typed element can have an anonymous inline
	 * type declaration.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Declaration</em>' containment reference.
	 * @see #setTypeDeclaration(Object)
	 * @see io.vrap.rmf.raml.model.elements.ElementsPackage#getTypedElement_TypeDeclaration()
	 * @model kind="reference" containment="true"
	 * @generated
	 */
	T getTypeDeclaration();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.elements.TypedElement#getTypeDeclaration <em>Type Declaration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Declaration</em>' containment reference.
	 * @see #getTypeDeclaration()
	 * @generated
	 */
	void setTypeDeclaration(T value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type of a typed element is the type that it declares
	 * or the type that it references.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see io.vrap.rmf.raml.model.elements.ElementsPackage#getTypedElement_Type()
	 * @model kind="reference" transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='T _xifexpression = null;\nT _typeDeclaration = this.getTypeDeclaration();\nboolean _notEquals = (!<%com.google.common.base.Objects%>.equal(_typeDeclaration, null));\nif (_notEquals)\n{\n\t_xifexpression = this.getTypeDeclaration();\n}\nelse\n{\n\t_xifexpression = this.getTypeReference();\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	T getType();

} // TypedElement
