/**
 */
package io.vrap.rmf.raml.model.types;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Api</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.Api#getTitle <em>Title</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.Api#getDescription <em>Description</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.Api#getVersion <em>Version</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.Api#getBaseUri <em>Base Uri</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.Api#getProtocols <em>Protocols</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.Api#getMediaType <em>Media Type</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.Api#getSecuredBy <em>Secured By</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getApi()
 * @model
 * @generated
 */
public interface Api extends Annotatable, TypeContainer {
	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A short, plain-text label for the API. Its value is a string.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getApi_Title()
	 * @model unique="false"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.types.Api#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A substantial, human-friendly description of the API.
	 * Its value is a string and MAY be formatted using markdown.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getApi_Description()
	 * @model unique="false"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.types.Api#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The version of the API, for example "v1".
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getApi_Version()
	 * @model unique="false"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.types.Api#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Base Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A URI that serves as the base for URIs of all resources. Often used as the base
	 * of the URL of each resource containing the location of the API. Can be a template URI.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Base Uri</em>' attribute.
	 * @see #setBaseUri(String)
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getApi_BaseUri()
	 * @model unique="false"
	 * @generated
	 */
	String getBaseUri();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.types.Api#getBaseUri <em>Base Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Uri</em>' attribute.
	 * @see #getBaseUri()
	 * @generated
	 */
	void setBaseUri(String value);

	/**
	 * Returns the value of the '<em><b>Protocols</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The protocols supported by the API.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Protocols</em>' attribute list.
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getApi_Protocols()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getProtocols();

	/**
	 * Returns the value of the '<em><b>Media Type</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The default media types to use for request and response bodies (payloads),
	 * for example "application/json".
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Media Type</em>' attribute list.
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getApi_MediaType()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getMediaType();

	/**
	 * Returns the value of the '<em><b>Secured By</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The security schemes that apply to every resource and method in the API.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Secured By</em>' attribute list.
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#getApi_SecuredBy()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getSecuredBy();

} // Api
