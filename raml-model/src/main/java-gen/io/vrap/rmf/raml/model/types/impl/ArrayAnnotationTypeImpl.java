/**
 */
package io.vrap.rmf.raml.model.types.impl;

import com.google.common.base.Objects;

import io.vrap.rmf.raml.model.elements.DocumentableElement;
import io.vrap.rmf.raml.model.elements.ElementsPackage;
import io.vrap.rmf.raml.model.elements.IdentifiableElement;

import io.vrap.rmf.raml.model.types.AnnotationTarget;
import io.vrap.rmf.raml.model.types.AnnotationType;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.ArrayAnnotationType;
import io.vrap.rmf.raml.model.types.Type;
import io.vrap.rmf.raml.model.types.TypesPackage;

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
 * An implementation of the model object '<em><b>Array Annotation Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl#getUniqueItems <em>Unique Items</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl#getItems <em>Items</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl#getMinItems <em>Min Items</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl#getMaxItems <em>Max Items</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl#getBaseClass <em>Base Class</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl#getAllowedTargets <em>Allowed Targets</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArrayAnnotationTypeImpl extends MinimalEObjectImpl.Container implements ArrayAnnotationType {
	/**
	 * The default value of the '{@link #getUniqueItems() <em>Unique Items</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueItems()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean UNIQUE_ITEMS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUniqueItems() <em>Unique Items</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueItems()
	 * @generated
	 * @ordered
	 */
	protected Boolean uniqueItems = UNIQUE_ITEMS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getItems() <em>Items</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItems()
	 * @generated
	 * @ordered
	 */
	protected AnyAnnotationType items;

	/**
	 * The default value of the '{@link #getMinItems() <em>Min Items</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinItems()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MIN_ITEMS_EDEFAULT = new Integer(0);

	/**
	 * The cached value of the '{@link #getMinItems() <em>Min Items</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinItems()
	 * @generated
	 * @ordered
	 */
	protected Integer minItems = MIN_ITEMS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxItems() <em>Max Items</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxItems()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MAX_ITEMS_EDEFAULT = new Integer(2147483647);

	/**
	 * The cached value of the '{@link #getMaxItems() <em>Max Items</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxItems()
	 * @generated
	 * @ordered
	 */
	protected Integer maxItems = MAX_ITEMS_EDEFAULT;

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
	protected ArrayAnnotationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.ARRAY_ANNOTATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getUniqueItems() {
		return uniqueItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUniqueItems(Boolean newUniqueItems) {
		Boolean oldUniqueItems = uniqueItems;
		uniqueItems = newUniqueItems;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ARRAY_ANNOTATION_TYPE__UNIQUE_ITEMS, oldUniqueItems, uniqueItems));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnyAnnotationType getItems() {
		if (items != null && ((EObject)items).eIsProxy()) {
			InternalEObject oldItems = (InternalEObject)items;
			items = (AnyAnnotationType)eResolveProxy(oldItems);
			if (items != oldItems) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.ARRAY_ANNOTATION_TYPE__ITEMS, oldItems, items));
			}
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnyAnnotationType basicGetItems() {
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setItems(AnyAnnotationType newItems) {
		AnyAnnotationType oldItems = items;
		items = newItems;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ARRAY_ANNOTATION_TYPE__ITEMS, oldItems, items));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMinItems() {
		return minItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinItems(Integer newMinItems) {
		Integer oldMinItems = minItems;
		minItems = newMinItems;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ARRAY_ANNOTATION_TYPE__MIN_ITEMS, oldMinItems, minItems));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMaxItems() {
		return maxItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxItems(Integer newMaxItems) {
		Integer oldMaxItems = maxItems;
		maxItems = newMaxItems;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ARRAY_ANNOTATION_TYPE__MAX_ITEMS, oldMaxItems, maxItems));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.ARRAY_ANNOTATION_TYPE__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ARRAY_ANNOTATION_TYPE__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ARRAY_ANNOTATION_TYPE__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ARRAY_ANNOTATION_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ARRAY_ANNOTATION_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AnnotationTarget> getAllowedTargets() {
		if (allowedTargets == null) {
			allowedTargets = new EDataTypeEList<AnnotationTarget>(AnnotationTarget.class, this, TypesPackage.ARRAY_ANNOTATION_TYPE__ALLOWED_TARGETS);
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
			case TypesPackage.ARRAY_ANNOTATION_TYPE__UNIQUE_ITEMS:
				return getUniqueItems();
			case TypesPackage.ARRAY_ANNOTATION_TYPE__ITEMS:
				if (resolve) return getItems();
				return basicGetItems();
			case TypesPackage.ARRAY_ANNOTATION_TYPE__MIN_ITEMS:
				return getMinItems();
			case TypesPackage.ARRAY_ANNOTATION_TYPE__MAX_ITEMS:
				return getMaxItems();
			case TypesPackage.ARRAY_ANNOTATION_TYPE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case TypesPackage.ARRAY_ANNOTATION_TYPE__BASE_CLASS:
				return getBaseClass();
			case TypesPackage.ARRAY_ANNOTATION_TYPE__DESCRIPTION:
				return getDescription();
			case TypesPackage.ARRAY_ANNOTATION_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case TypesPackage.ARRAY_ANNOTATION_TYPE__NAME:
				return getName();
			case TypesPackage.ARRAY_ANNOTATION_TYPE__ALLOWED_TARGETS:
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
			case TypesPackage.ARRAY_ANNOTATION_TYPE__UNIQUE_ITEMS:
				setUniqueItems((Boolean)newValue);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__ITEMS:
				setItems((AnyAnnotationType)newValue);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__MIN_ITEMS:
				setMinItems((Integer)newValue);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__MAX_ITEMS:
				setMaxItems((Integer)newValue);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__TYPE:
				setType((AnyAnnotationType)newValue);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__NAME:
				setName((String)newValue);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__ALLOWED_TARGETS:
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
			case TypesPackage.ARRAY_ANNOTATION_TYPE__UNIQUE_ITEMS:
				setUniqueItems(UNIQUE_ITEMS_EDEFAULT);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__ITEMS:
				setItems((AnyAnnotationType)null);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__MIN_ITEMS:
				setMinItems(MIN_ITEMS_EDEFAULT);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__MAX_ITEMS:
				setMaxItems(MAX_ITEMS_EDEFAULT);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__TYPE:
				setType((AnyAnnotationType)null);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__ALLOWED_TARGETS:
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
			case TypesPackage.ARRAY_ANNOTATION_TYPE__UNIQUE_ITEMS:
				return UNIQUE_ITEMS_EDEFAULT == null ? uniqueItems != null : !UNIQUE_ITEMS_EDEFAULT.equals(uniqueItems);
			case TypesPackage.ARRAY_ANNOTATION_TYPE__ITEMS:
				return items != null;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__MIN_ITEMS:
				return MIN_ITEMS_EDEFAULT == null ? minItems != null : !MIN_ITEMS_EDEFAULT.equals(minItems);
			case TypesPackage.ARRAY_ANNOTATION_TYPE__MAX_ITEMS:
				return MAX_ITEMS_EDEFAULT == null ? maxItems != null : !MAX_ITEMS_EDEFAULT.equals(maxItems);
			case TypesPackage.ARRAY_ANNOTATION_TYPE__TYPE:
				return type != null;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__BASE_CLASS:
				return getBaseClass() != null;
			case TypesPackage.ARRAY_ANNOTATION_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TypesPackage.ARRAY_ANNOTATION_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case TypesPackage.ARRAY_ANNOTATION_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TypesPackage.ARRAY_ANNOTATION_TYPE__ALLOWED_TARGETS:
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
				case TypesPackage.ARRAY_ANNOTATION_TYPE__TYPE: return TypesPackage.TYPE__TYPE;
				case TypesPackage.ARRAY_ANNOTATION_TYPE__BASE_CLASS: return TypesPackage.TYPE__BASE_CLASS;
				default: return -1;
			}
		}
		if (baseClass == DocumentableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.ARRAY_ANNOTATION_TYPE__DESCRIPTION: return ElementsPackage.DOCUMENTABLE_ELEMENT__DESCRIPTION;
				case TypesPackage.ARRAY_ANNOTATION_TYPE__DISPLAY_NAME: return ElementsPackage.DOCUMENTABLE_ELEMENT__DISPLAY_NAME;
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.ARRAY_ANNOTATION_TYPE__NAME: return ElementsPackage.IDENTIFIABLE_ELEMENT__NAME;
				default: return -1;
			}
		}
		if (baseClass == AnnotationType.class) {
			switch (derivedFeatureID) {
				case TypesPackage.ARRAY_ANNOTATION_TYPE__ALLOWED_TARGETS: return TypesPackage.ANNOTATION_TYPE__ALLOWED_TARGETS;
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
				case TypesPackage.TYPE__TYPE: return TypesPackage.ARRAY_ANNOTATION_TYPE__TYPE;
				case TypesPackage.TYPE__BASE_CLASS: return TypesPackage.ARRAY_ANNOTATION_TYPE__BASE_CLASS;
				default: return -1;
			}
		}
		if (baseClass == DocumentableElement.class) {
			switch (baseFeatureID) {
				case ElementsPackage.DOCUMENTABLE_ELEMENT__DESCRIPTION: return TypesPackage.ARRAY_ANNOTATION_TYPE__DESCRIPTION;
				case ElementsPackage.DOCUMENTABLE_ELEMENT__DISPLAY_NAME: return TypesPackage.ARRAY_ANNOTATION_TYPE__DISPLAY_NAME;
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseFeatureID) {
				case ElementsPackage.IDENTIFIABLE_ELEMENT__NAME: return TypesPackage.ARRAY_ANNOTATION_TYPE__NAME;
				default: return -1;
			}
		}
		if (baseClass == AnnotationType.class) {
			switch (baseFeatureID) {
				case TypesPackage.ANNOTATION_TYPE__ALLOWED_TARGETS: return TypesPackage.ARRAY_ANNOTATION_TYPE__ALLOWED_TARGETS;
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
		result.append(" (uniqueItems: ");
		result.append(uniqueItems);
		result.append(", minItems: ");
		result.append(minItems);
		result.append(", maxItems: ");
		result.append(maxItems);
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

} //ArrayAnnotationTypeImpl
