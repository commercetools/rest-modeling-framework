/**
 */
package io.vrap.rmf.raml.model.types;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.ObjectInstance#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getObjectInstance()
 * @model
 * @generated
 */
public interface ObjectInstance extends Instance<ObjectType> {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference list.
	 * The list contents are of type {@link io.vrap.rmf.raml.model.types.Instance}&lt;?>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference list.
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getObjectInstance_Value()
	 * @model containment="true"
	 * @generated
	 */
	EList<Instance<?>> getValue();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns the property of the given type if it exists.
	 * Otherwise returns null.
	 * <!-- end-model-doc -->
	 * @model unique="false" valueTypeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='V _xblockexpression = null;\n{\n\t<%org.eclipse.emf.common.util.EList%><<%io.vrap.rmf.raml.model.types.Instance%><?>> _value = this.getValue();\n\tfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%io.vrap.rmf.raml.model.types.Instance%><?>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%io.vrap.rmf.raml.model.types.Instance%><?>, <%java.lang.Boolean%>>()\n\t{\n\t\tpublic <%java.lang.Boolean%> apply(final <%io.vrap.rmf.raml.model.types.Instance%><?> it)\n\t\t{\n\t\t\t<%io.vrap.rmf.raml.model.types.AnyType%> _type = it.getType();\n\t\t\treturn <%java.lang.Boolean%>.valueOf(<%com.google.common.base.Objects%>.equal(_type, valueType));\n\t\t}\n\t};\n\t<%io.vrap.rmf.raml.model.types.Instance%><?> p = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%io.vrap.rmf.raml.model.types.Instance%><?>>findFirst(_value, _function);\n\tV _xifexpression = null;\n\tboolean _notEquals = (!<%com.google.common.base.Objects%>.equal(p, null));\n\tif (_notEquals)\n\t{\n\t\t_xifexpression = ((V) p);\n\t}\n\telse\n\t{\n\t\t_xifexpression = null;\n\t}\n\t_xblockexpression = _xifexpression;\n}\nreturn _xblockexpression;'"
	 * @generated
	 */
	<T extends AnyType, V extends Instance<T>> V getPropertyy(T valueType);

} // ObjectInstance
