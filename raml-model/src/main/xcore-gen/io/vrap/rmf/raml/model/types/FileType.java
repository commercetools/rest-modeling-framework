/**
 */
package io.vrap.rmf.raml.model.types;

import io.vrap.rmf.raml.model.facets.FileTypeFacet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The ​file​ type can constrain the content to send through forms. When this type is used in the
 * context of web forms it SHOULD be represented as a valid file upload in JSON format.
 * File content SHOULD be a base64-encoded string.
 * <!-- end-model-doc -->
 *
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getFileType()
 * @model
 * @generated
 */
public interface FileType extends AnyType, FileTypeFacet {
} // FileType
