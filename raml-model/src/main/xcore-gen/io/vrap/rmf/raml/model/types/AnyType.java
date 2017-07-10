/**
 */
package io.vrap.rmf.raml.model.types;

import io.vrap.rmf.raml.model.elements.DocumentableElement;
import io.vrap.rmf.raml.model.elements.IdentifiableElement;

import io.vrap.rmf.raml.model.facets.EnumFacet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Any Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Every type, whether built-in or user-defined, has the any type at the root of its inheritance tree.
 * By definition, the any type is a type which imposes no restrictions, i.e. any instance of data is
 * valid against it.
 * <!-- end-model-doc -->
 *
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getAnyType()
 * @model
 * @generated
 */
public interface AnyType extends DataType, Annotatable, DocumentableElement, IdentifiableElement, EnumFacet {
} // AnyType
