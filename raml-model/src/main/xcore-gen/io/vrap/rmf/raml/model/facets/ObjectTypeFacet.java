/**
 */
package io.vrap.rmf.raml.model.facets;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object Type Facet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getProperties <em>Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getMinProperties <em>Min Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getMaxProperties <em>Max Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getAdditionalProperties <em>Additional Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getDiscriminator <em>Discriminator</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getDiscriminatorValue <em>Discriminator Value</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getObjectTypeFacet()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ObjectTypeFacet<T, P extends Property<T>> extends EObject {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The properties that instances of this type can or must have.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getObjectTypeFacet_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<P> getProperties();

	/**
	 * Returns the value of the '<em><b>Min Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The minimum number of properties allowed for instances of this type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min Properties</em>' attribute.
	 * @see #setMinProperties(Integer)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getObjectTypeFacet_MinProperties()
	 * @model unique="false"
	 * @generated
	 */
	Integer getMinProperties();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getMinProperties <em>Min Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Properties</em>' attribute.
	 * @see #getMinProperties()
	 * @generated
	 */
	void setMinProperties(Integer value);

	/**
	 * Returns the value of the '<em><b>Max Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The maximum number of properties allowed for instances of this type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Properties</em>' attribute.
	 * @see #setMaxProperties(Integer)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getObjectTypeFacet_MaxProperties()
	 * @model unique="false"
	 * @generated
	 */
	Integer getMaxProperties();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getMaxProperties <em>Max Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Properties</em>' attribute.
	 * @see #getMaxProperties()
	 * @generated
	 */
	void setMaxProperties(Integer value);

	/**
	 * Returns the value of the '<em><b>Additional Properties</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Boolean that indicates if an object instance has additional properties.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Additional Properties</em>' attribute.
	 * @see #setAdditionalProperties(Boolean)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getObjectTypeFacet_AdditionalProperties()
	 * @model default="true" unique="false"
	 * @generated
	 */
	Boolean getAdditionalProperties();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getAdditionalProperties <em>Additional Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Properties</em>' attribute.
	 * @see #getAdditionalProperties()
	 * @generated
	 */
	void setAdditionalProperties(Boolean value);

	/**
	 * Returns the value of the '<em><b>Discriminator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Determines the concrete type of an individual object at runtime when, for example,
	 * payloads contain ambiguous types due to unions or inheritance.
	 * 	 * The value must match the name of one of the declared properties of a type.
	 * Unsupported practices are inline type declarations and using discriminator with non-scalar
	 * properties.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Discriminator</em>' attribute.
	 * @see #setDiscriminator(String)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getObjectTypeFacet_Discriminator()
	 * @model unique="false"
	 * @generated
	 */
	String getDiscriminator();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getDiscriminator <em>Discriminator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator</em>' attribute.
	 * @see #getDiscriminator()
	 * @generated
	 */
	void setDiscriminator(String value);

	/**
	 * Returns the value of the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Identifies the declaring type. Requires including a discriminator facet in the type declaration.
	 * A valid value is an actual value that might identify the type of an individual object and is
	 * unique in the hierarchy of the type.
	 * Inline type declarations are not supported.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Discriminator Value</em>' attribute.
	 * @see #setDiscriminatorValue(String)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getObjectTypeFacet_DiscriminatorValue()
	 * @model unique="false"
	 * @generated
	 */
	String getDiscriminatorValue();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getDiscriminatorValue <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Value</em>' attribute.
	 * @see #getDiscriminatorValue()
	 * @generated
	 */
	void setDiscriminatorValue(String value);

} // ObjectTypeFacet
