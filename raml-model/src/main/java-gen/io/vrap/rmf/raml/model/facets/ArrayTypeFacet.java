/**
 */
package io.vrap.rmf.raml.model.facets;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Array Type Facet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getUniqueItems <em>Unique Items</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getItems <em>Items</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getMinItems <em>Min Items</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getMaxItems <em>Max Items</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getArrayTypeFacet()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ArrayTypeFacet<T> extends EObject {
	/**
	 * Returns the value of the '<em><b>Unique Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Boolean value that indicates if items in the array MUST be unique.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Unique Items</em>' attribute.
	 * @see #setUniqueItems(Boolean)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getArrayTypeFacet_UniqueItems()
	 * @model unique="false"
	 * @generated
	 */
	Boolean getUniqueItems();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getUniqueItems <em>Unique Items</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique Items</em>' attribute.
	 * @see #getUniqueItems()
	 * @generated
	 */
	void setUniqueItems(Boolean value);

	/**
	 * Returns the value of the '<em><b>Items</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates the type all items in the array are inherited from.
	 * Can be a reference to an existing type or an inline type declaration.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Items</em>' reference.
	 * @see #setItems(Object)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getArrayTypeFacet_Items()
	 * @model kind="reference"
	 * @generated
	 */
	T getItems();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getItems <em>Items</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Items</em>' reference.
	 * @see #getItems()
	 * @generated
	 */
	void setItems(T value);

	/**
	 * Returns the value of the '<em><b>Min Items</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Minimum amount of items in array. Value MUST be equal to or greater than 0.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min Items</em>' attribute.
	 * @see #setMinItems(Integer)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getArrayTypeFacet_MinItems()
	 * @model default="0" unique="false"
	 * @generated
	 */
	Integer getMinItems();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getMinItems <em>Min Items</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Items</em>' attribute.
	 * @see #getMinItems()
	 * @generated
	 */
	void setMinItems(Integer value);

	/**
	 * Returns the value of the '<em><b>Max Items</b></em>' attribute.
	 * The default value is <code>"2147483647"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * aximum amount of items in array. Value MUST be equal to or greater than 0.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Items</em>' attribute.
	 * @see #setMaxItems(Integer)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getArrayTypeFacet_MaxItems()
	 * @model default="2147483647" unique="false"
	 * @generated
	 */
	Integer getMaxItems();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getMaxItems <em>Max Items</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Items</em>' attribute.
	 * @see #getMaxItems()
	 * @generated
	 */
	void setMaxItems(Integer value);

} // ArrayTypeFacet
