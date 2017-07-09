/**
 */
package io.vrap.rmf.raml.model.types;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotatable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.Annotatable#getAnnotations <em>Annotations</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getAnnotatable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Annotatable extends EObject {
	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
	 * The list contents are of type {@link io.vrap.rmf.raml.model.types.Instance}&lt;?>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The annotations of this element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Annotations</em>' containment reference list.
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getAnnotatable_Annotations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Instance<?>> getAnnotations();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns the annotation of the given annotation type if it exists.
	 * Otherwise returns null.
	 * <!-- end-model-doc -->
	 * @model unique="false" annotationTypeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='A _xblockexpression = null;\n{\n\t<%org.eclipse.emf.common.util.EList%><<%io.vrap.rmf.raml.model.types.Instance%><?>> _annotations = this.getAnnotations();\n\tfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%io.vrap.rmf.raml.model.types.Instance%><?>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%io.vrap.rmf.raml.model.types.Instance%><?>, <%java.lang.Boolean%>>()\n\t{\n\t\tpublic <%java.lang.Boolean%> apply(final <%io.vrap.rmf.raml.model.types.Instance%><?> it)\n\t\t{\n\t\t\t<%io.vrap.rmf.raml.model.types.AnyType%> _type = it.getType();\n\t\t\treturn <%java.lang.Boolean%>.valueOf(<%com.google.common.base.Objects%>.equal(_type, annotationType));\n\t\t}\n\t};\n\t<%io.vrap.rmf.raml.model.types.Instance%><?> a = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%io.vrap.rmf.raml.model.types.Instance%><?>>findFirst(_annotations, _function);\n\tA _xifexpression = null;\n\tboolean _notEquals = (!<%com.google.common.base.Objects%>.equal(a, null));\n\tif (_notEquals)\n\t{\n\t\t_xifexpression = ((A) a);\n\t}\n\telse\n\t{\n\t\t_xifexpression = null;\n\t}\n\t_xblockexpression = _xifexpression;\n}\nreturn _xblockexpression;'"
	 * @generated
	 */
	<T extends AnyType, A extends Instance<T>> A getAnnotation(T annotationType);

} // Annotatable
