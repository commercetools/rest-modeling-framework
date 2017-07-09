/**
 */
package io.vrap.rmf.raml.model.facets;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Date Time Type Facet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.facets.DateTimeTypeFacet#getFormat <em>Format</em>}</li>
 * </ul>
 *
 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getDateTimeTypeFacet()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface DateTimeTypeFacet extends EObject {
	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * The literals are from the enumeration {@link io.vrap.rmf.raml.model.facets.DateTimeFormat}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A timestamp in one of the following formats: if the format is omitted or set to rfc3339,
	 * uses the "date-time" notation of RFC3339; if format is set to rfc2616, uses the format
	 * defined in RFC2616.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see io.vrap.rmf.raml.model.facets.DateTimeFormat
	 * @see #setFormat(DateTimeFormat)
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#getDateTimeTypeFacet_Format()
	 * @model unique="false"
	 * @generated
	 */
	DateTimeFormat getFormat();

	/**
	 * Sets the value of the '{@link io.vrap.rmf.raml.model.facets.DateTimeTypeFacet#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see io.vrap.rmf.raml.model.facets.DateTimeFormat
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(DateTimeFormat value);

} // DateTimeTypeFacet
