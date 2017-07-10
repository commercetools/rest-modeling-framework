/**
 */
package io.vrap.rmf.raml.model.types.impl;

import io.vrap.rmf.raml.model.facets.FacetsPackage;
import io.vrap.rmf.raml.model.facets.NumberFormat;
import io.vrap.rmf.raml.model.facets.NumberTypeFacet;

import io.vrap.rmf.raml.model.types.NumberType;
import io.vrap.rmf.raml.model.types.TypesPackage;

import java.math.BigDecimal;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Number Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberTypeImpl#getMinimum <em>Minimum</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberTypeImpl#getMaximum <em>Maximum</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberTypeImpl#getFormat <em>Format</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberTypeImpl#getMultipleOf <em>Multiple Of</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NumberTypeImpl extends AnyTypeImpl implements NumberType {
	/**
	 * The default value of the '{@link #getMinimum() <em>Minimum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinimum()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal MINIMUM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMinimum() <em>Minimum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinimum()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal minimum = MINIMUM_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaximum() <em>Maximum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaximum()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal MAXIMUM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMaximum() <em>Maximum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaximum()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal maximum = MAXIMUM_EDEFAULT;

	/**
	 * The default value of the '{@link #getFormat() <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormat()
	 * @generated
	 * @ordered
	 */
	protected static final NumberFormat FORMAT_EDEFAULT = NumberFormat.INT16;

	/**
	 * The cached value of the '{@link #getFormat() <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormat()
	 * @generated
	 * @ordered
	 */
	protected NumberFormat format = FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getMultipleOf() <em>Multiple Of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultipleOf()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MULTIPLE_OF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMultipleOf() <em>Multiple Of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultipleOf()
	 * @generated
	 * @ordered
	 */
	protected Integer multipleOf = MULTIPLE_OF_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NumberTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.NUMBER_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigDecimal getMinimum() {
		return minimum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinimum(BigDecimal newMinimum) {
		BigDecimal oldMinimum = minimum;
		minimum = newMinimum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_TYPE__MINIMUM, oldMinimum, minimum));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigDecimal getMaximum() {
		return maximum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaximum(BigDecimal newMaximum) {
		BigDecimal oldMaximum = maximum;
		maximum = newMaximum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_TYPE__MAXIMUM, oldMaximum, maximum));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumberFormat getFormat() {
		return format;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormat(NumberFormat newFormat) {
		NumberFormat oldFormat = format;
		format = newFormat == null ? FORMAT_EDEFAULT : newFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_TYPE__FORMAT, oldFormat, format));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMultipleOf() {
		return multipleOf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultipleOf(Integer newMultipleOf) {
		Integer oldMultipleOf = multipleOf;
		multipleOf = newMultipleOf;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_TYPE__MULTIPLE_OF, oldMultipleOf, multipleOf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.NUMBER_TYPE__MINIMUM:
				return getMinimum();
			case TypesPackage.NUMBER_TYPE__MAXIMUM:
				return getMaximum();
			case TypesPackage.NUMBER_TYPE__FORMAT:
				return getFormat();
			case TypesPackage.NUMBER_TYPE__MULTIPLE_OF:
				return getMultipleOf();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TypesPackage.NUMBER_TYPE__MINIMUM:
				setMinimum((BigDecimal)newValue);
				return;
			case TypesPackage.NUMBER_TYPE__MAXIMUM:
				setMaximum((BigDecimal)newValue);
				return;
			case TypesPackage.NUMBER_TYPE__FORMAT:
				setFormat((NumberFormat)newValue);
				return;
			case TypesPackage.NUMBER_TYPE__MULTIPLE_OF:
				setMultipleOf((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TypesPackage.NUMBER_TYPE__MINIMUM:
				setMinimum(MINIMUM_EDEFAULT);
				return;
			case TypesPackage.NUMBER_TYPE__MAXIMUM:
				setMaximum(MAXIMUM_EDEFAULT);
				return;
			case TypesPackage.NUMBER_TYPE__FORMAT:
				setFormat(FORMAT_EDEFAULT);
				return;
			case TypesPackage.NUMBER_TYPE__MULTIPLE_OF:
				setMultipleOf(MULTIPLE_OF_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TypesPackage.NUMBER_TYPE__MINIMUM:
				return MINIMUM_EDEFAULT == null ? minimum != null : !MINIMUM_EDEFAULT.equals(minimum);
			case TypesPackage.NUMBER_TYPE__MAXIMUM:
				return MAXIMUM_EDEFAULT == null ? maximum != null : !MAXIMUM_EDEFAULT.equals(maximum);
			case TypesPackage.NUMBER_TYPE__FORMAT:
				return format != FORMAT_EDEFAULT;
			case TypesPackage.NUMBER_TYPE__MULTIPLE_OF:
				return MULTIPLE_OF_EDEFAULT == null ? multipleOf != null : !MULTIPLE_OF_EDEFAULT.equals(multipleOf);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == NumberTypeFacet.class) {
			switch (derivedFeatureID) {
				case TypesPackage.NUMBER_TYPE__MINIMUM: return FacetsPackage.NUMBER_TYPE_FACET__MINIMUM;
				case TypesPackage.NUMBER_TYPE__MAXIMUM: return FacetsPackage.NUMBER_TYPE_FACET__MAXIMUM;
				case TypesPackage.NUMBER_TYPE__FORMAT: return FacetsPackage.NUMBER_TYPE_FACET__FORMAT;
				case TypesPackage.NUMBER_TYPE__MULTIPLE_OF: return FacetsPackage.NUMBER_TYPE_FACET__MULTIPLE_OF;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == NumberTypeFacet.class) {
			switch (baseFeatureID) {
				case FacetsPackage.NUMBER_TYPE_FACET__MINIMUM: return TypesPackage.NUMBER_TYPE__MINIMUM;
				case FacetsPackage.NUMBER_TYPE_FACET__MAXIMUM: return TypesPackage.NUMBER_TYPE__MAXIMUM;
				case FacetsPackage.NUMBER_TYPE_FACET__FORMAT: return TypesPackage.NUMBER_TYPE__FORMAT;
				case FacetsPackage.NUMBER_TYPE_FACET__MULTIPLE_OF: return TypesPackage.NUMBER_TYPE__MULTIPLE_OF;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (minimum: ");
		result.append(minimum);
		result.append(", maximum: ");
		result.append(maximum);
		result.append(", format: ");
		result.append(format);
		result.append(", multipleOf: ");
		result.append(multipleOf);
		result.append(')');
		return result.toString();
	}

} //NumberTypeImpl
