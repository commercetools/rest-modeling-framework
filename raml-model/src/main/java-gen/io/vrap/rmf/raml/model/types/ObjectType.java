/**
 */
package io.vrap.rmf.raml.model.types;

import io.vrap.rmf.raml.model.facets.ObjectTypeFacet;
import io.vrap.rmf.raml.model.facets.Property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object Type</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getObjectType()
 * @model
 * @generated
 */
public interface ObjectType extends AnyType, ObjectTypeFacet<AnyType, Property<AnyType>> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" nameUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%io.vrap.rmf.raml.model.facets.Property%><<%io.vrap.rmf.raml.model.types.AnyType%>>> _properties = this.getProperties();\nfor (final <%io.vrap.rmf.raml.model.facets.Property%><<%io.vrap.rmf.raml.model.types.AnyType%>> property : _properties)\n{\n\t<%java.lang.String%> _name = property.getName();\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(name, _name);\n\tif (_equals)\n\t{\n\t\treturn property;\n\t}\n}\nreturn null;'"
	 * @generated
	 */
	Property<AnyType> getProperty(String name);

} // ObjectType
