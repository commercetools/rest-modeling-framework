/**
 */
package io.vrap.rmf.raml.model.types.impl;

import com.google.common.base.Objects;

import io.vrap.rmf.raml.model.elements.DocumentableElement;
import io.vrap.rmf.raml.model.elements.ElementsPackage;
import io.vrap.rmf.raml.model.elements.IdentifiableElement;

import io.vrap.rmf.raml.model.facets.NumberFormat;

import io.vrap.rmf.raml.model.types.AnnotationTarget;
import io.vrap.rmf.raml.model.types.AnnotationType;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.NumberAnnotationType;
import io.vrap.rmf.raml.model.types.Type;
import io.vrap.rmf.raml.model.types.TypesPackage;

import java.math.BigDecimal;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Number Annotation Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl#getMinimum <em>Minimum</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl#getMaximum <em>Maximum</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl#getFormat <em>Format</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl#getMultipleOf <em>Multiple Of</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl#getBaseClass <em>Base Class</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl#getAllowedTargets <em>Allowed Targets</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NumberAnnotationTypeImpl extends MinimalEObjectImpl.Container implements NumberAnnotationType {
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
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected AnyAnnotationType type;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAllowedTargets() <em>Allowed Targets</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllowedTargets()
	 * @generated
	 * @ordered
	 */
	protected EList<AnnotationTarget> allowedTargets;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NumberAnnotationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.NUMBER_ANNOTATION_TYPE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_ANNOTATION_TYPE__MINIMUM, oldMinimum, minimum));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_ANNOTATION_TYPE__MAXIMUM, oldMaximum, maximum));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_ANNOTATION_TYPE__FORMAT, oldFormat, format));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_ANNOTATION_TYPE__MULTIPLE_OF, oldMultipleOf, multipleOf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnyAnnotationType getType() {
		if (type != null && ((EObject)type).eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (AnyAnnotationType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.NUMBER_ANNOTATION_TYPE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnyAnnotationType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(AnyAnnotationType newType) {
		AnyAnnotationType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_ANNOTATION_TYPE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Class<?> getBaseClass() {
		Class<? extends Type> _class = this.getClass();
		boolean _equals = Objects.equal(_class, AnyType.class);
		if (_equals) {
			return null;
		}
		Class<?> baseClass = this.getClass();
		while ((!Objects.equal(baseClass.getSuperclass(), AnyType.class))) {
			Class<?> _superclass = baseClass.getSuperclass();
			baseClass = _superclass;
		}
		return baseClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_ANNOTATION_TYPE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_ANNOTATION_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.NUMBER_ANNOTATION_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AnnotationTarget> getAllowedTargets() {
		if (allowedTargets == null) {
			allowedTargets = new EDataTypeEList<AnnotationTarget>(AnnotationTarget.class, this, TypesPackage.NUMBER_ANNOTATION_TYPE__ALLOWED_TARGETS);
		}
		return allowedTargets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MINIMUM:
				return getMinimum();
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MAXIMUM:
				return getMaximum();
			case TypesPackage.NUMBER_ANNOTATION_TYPE__FORMAT:
				return getFormat();
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MULTIPLE_OF:
				return getMultipleOf();
			case TypesPackage.NUMBER_ANNOTATION_TYPE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case TypesPackage.NUMBER_ANNOTATION_TYPE__BASE_CLASS:
				return getBaseClass();
			case TypesPackage.NUMBER_ANNOTATION_TYPE__DESCRIPTION:
				return getDescription();
			case TypesPackage.NUMBER_ANNOTATION_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case TypesPackage.NUMBER_ANNOTATION_TYPE__NAME:
				return getName();
			case TypesPackage.NUMBER_ANNOTATION_TYPE__ALLOWED_TARGETS:
				return getAllowedTargets();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MINIMUM:
				setMinimum((BigDecimal)newValue);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MAXIMUM:
				setMaximum((BigDecimal)newValue);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__FORMAT:
				setFormat((NumberFormat)newValue);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MULTIPLE_OF:
				setMultipleOf((Integer)newValue);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__TYPE:
				setType((AnyAnnotationType)newValue);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__NAME:
				setName((String)newValue);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__ALLOWED_TARGETS:
				getAllowedTargets().clear();
				getAllowedTargets().addAll((Collection<? extends AnnotationTarget>)newValue);
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
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MINIMUM:
				setMinimum(MINIMUM_EDEFAULT);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MAXIMUM:
				setMaximum(MAXIMUM_EDEFAULT);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__FORMAT:
				setFormat(FORMAT_EDEFAULT);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MULTIPLE_OF:
				setMultipleOf(MULTIPLE_OF_EDEFAULT);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__TYPE:
				setType((AnyAnnotationType)null);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__ALLOWED_TARGETS:
				getAllowedTargets().clear();
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
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MINIMUM:
				return MINIMUM_EDEFAULT == null ? minimum != null : !MINIMUM_EDEFAULT.equals(minimum);
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MAXIMUM:
				return MAXIMUM_EDEFAULT == null ? maximum != null : !MAXIMUM_EDEFAULT.equals(maximum);
			case TypesPackage.NUMBER_ANNOTATION_TYPE__FORMAT:
				return format != FORMAT_EDEFAULT;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__MULTIPLE_OF:
				return MULTIPLE_OF_EDEFAULT == null ? multipleOf != null : !MULTIPLE_OF_EDEFAULT.equals(multipleOf);
			case TypesPackage.NUMBER_ANNOTATION_TYPE__TYPE:
				return type != null;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__BASE_CLASS:
				return getBaseClass() != null;
			case TypesPackage.NUMBER_ANNOTATION_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TypesPackage.NUMBER_ANNOTATION_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case TypesPackage.NUMBER_ANNOTATION_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TypesPackage.NUMBER_ANNOTATION_TYPE__ALLOWED_TARGETS:
				return allowedTargets != null && !allowedTargets.isEmpty();
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
		if (baseClass == Type.class) {
			switch (derivedFeatureID) {
				case TypesPackage.NUMBER_ANNOTATION_TYPE__TYPE: return TypesPackage.TYPE__TYPE;
				case TypesPackage.NUMBER_ANNOTATION_TYPE__BASE_CLASS: return TypesPackage.TYPE__BASE_CLASS;
				default: return -1;
			}
		}
		if (baseClass == DocumentableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.NUMBER_ANNOTATION_TYPE__DESCRIPTION: return ElementsPackage.DOCUMENTABLE_ELEMENT__DESCRIPTION;
				case TypesPackage.NUMBER_ANNOTATION_TYPE__DISPLAY_NAME: return ElementsPackage.DOCUMENTABLE_ELEMENT__DISPLAY_NAME;
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.NUMBER_ANNOTATION_TYPE__NAME: return ElementsPackage.IDENTIFIABLE_ELEMENT__NAME;
				default: return -1;
			}
		}
		if (baseClass == AnnotationType.class) {
			switch (derivedFeatureID) {
				case TypesPackage.NUMBER_ANNOTATION_TYPE__ALLOWED_TARGETS: return TypesPackage.ANNOTATION_TYPE__ALLOWED_TARGETS;
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
		if (baseClass == Type.class) {
			switch (baseFeatureID) {
				case TypesPackage.TYPE__TYPE: return TypesPackage.NUMBER_ANNOTATION_TYPE__TYPE;
				case TypesPackage.TYPE__BASE_CLASS: return TypesPackage.NUMBER_ANNOTATION_TYPE__BASE_CLASS;
				default: return -1;
			}
		}
		if (baseClass == DocumentableElement.class) {
			switch (baseFeatureID) {
				case ElementsPackage.DOCUMENTABLE_ELEMENT__DESCRIPTION: return TypesPackage.NUMBER_ANNOTATION_TYPE__DESCRIPTION;
				case ElementsPackage.DOCUMENTABLE_ELEMENT__DISPLAY_NAME: return TypesPackage.NUMBER_ANNOTATION_TYPE__DISPLAY_NAME;
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseFeatureID) {
				case ElementsPackage.IDENTIFIABLE_ELEMENT__NAME: return TypesPackage.NUMBER_ANNOTATION_TYPE__NAME;
				default: return -1;
			}
		}
		if (baseClass == AnnotationType.class) {
			switch (baseFeatureID) {
				case TypesPackage.ANNOTATION_TYPE__ALLOWED_TARGETS: return TypesPackage.NUMBER_ANNOTATION_TYPE__ALLOWED_TARGETS;
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
		result.append(", description: ");
		result.append(description);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", name: ");
		result.append(name);
		result.append(", allowedTargets: ");
		result.append(allowedTargets);
		result.append(')');
		return result.toString();
	}

} //NumberAnnotationTypeImpl
