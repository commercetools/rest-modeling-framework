/**
 */
package io.vrap.rmf.raml.model.types;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.Type#getType <em>Type</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.Type#getBaseClass <em>Base Class</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getType()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Type<T> extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type which this type extends.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Object)
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getType_Type()
	 * @model kind="reference"
	 * @generated
	 */
	T getType();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.types.Type#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(T value);

	/**
	 * Returns the value of the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The base class of a type is the super class
	 * of this type that directly extends the {@link AnyType}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Base Class</em>' attribute.
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getType_BaseClass()
	 * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='<%java.lang.Class%><? extends <%io.vrap.rmf.raml.model.types.Type%>> _class = this.getClass();\nboolean _equals = <%com.google.common.base.Objects%>.equal(_class, <%io.vrap.rmf.raml.model.types.AnyType%>.class);\nif (_equals)\n{\n\treturn null;\n}\n<%java.lang.Class%><?> baseClass = this.getClass();\nwhile ((!<%com.google.common.base.Objects%>.equal(baseClass.getSuperclass(), <%io.vrap.rmf.raml.model.types.AnyType%>.class)))\n{\n\t<%java.lang.Class%><?> _superclass = baseClass.getSuperclass();\n\tbaseClass = _superclass;\n}\nreturn baseClass;'"
	 * @generated
	 */
	Class<?> getBaseClass();

} // Type
