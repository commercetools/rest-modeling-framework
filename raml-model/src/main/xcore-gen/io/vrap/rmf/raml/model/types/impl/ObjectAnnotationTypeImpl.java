/**
 */
package io.vrap.rmf.raml.model.types.impl;

import com.google.common.base.Objects;

import io.vrap.rmf.raml.model.elements.DocumentableElement;
import io.vrap.rmf.raml.model.elements.ElementsPackage;
import io.vrap.rmf.raml.model.elements.IdentifiableElement;

import io.vrap.rmf.raml.model.facets.Property;

import io.vrap.rmf.raml.model.types.AnnotationTarget;
import io.vrap.rmf.raml.model.types.AnnotationType;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.ObjectAnnotationType;
import io.vrap.rmf.raml.model.types.Type;
import io.vrap.rmf.raml.model.types.TypesPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object Annotation Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getMinProperties <em>Min Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getMaxProperties <em>Max Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getAdditionalProperties <em>Additional Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getDiscriminator <em>Discriminator</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getDiscriminatorValue <em>Discriminator Value</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getBaseClass <em>Base Class</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl#getAllowedTargets <em>Allowed Targets</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ObjectAnnotationTypeImpl extends MinimalEObjectImpl.Container implements ObjectAnnotationType {
	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<Property<AnyAnnotationType>> properties;

	/**
	 * The default value of the '{@link #getMinProperties() <em>Min Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinProperties()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MIN_PROPERTIES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMinProperties() <em>Min Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinProperties()
	 * @generated
	 * @ordered
	 */
	protected Integer minProperties = MIN_PROPERTIES_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxProperties() <em>Max Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxProperties()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MAX_PROPERTIES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMaxProperties() <em>Max Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxProperties()
	 * @generated
	 * @ordered
	 */
	protected Integer maxProperties = MAX_PROPERTIES_EDEFAULT;

	/**
	 * The default value of the '{@link #getAdditionalProperties() <em>Additional Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalProperties()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean ADDITIONAL_PROPERTIES_EDEFAULT = Boolean.TRUE;

	/**
	 * The cached value of the '{@link #getAdditionalProperties() <em>Additional Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalProperties()
	 * @generated
	 * @ordered
	 */
	protected Boolean additionalProperties = ADDITIONAL_PROPERTIES_EDEFAULT;

	/**
	 * The default value of the '{@link #getDiscriminator() <em>Discriminator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminator()
	 * @generated
	 * @ordered
	 */
	protected static final String DISCRIMINATOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiscriminator() <em>Discriminator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminator()
	 * @generated
	 * @ordered
	 */
	protected String discriminator = DISCRIMINATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getDiscriminatorValue() <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DISCRIMINATOR_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiscriminatorValue() <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected String discriminatorValue = DISCRIMINATOR_VALUE_EDEFAULT;

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
	protected ObjectAnnotationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.OBJECT_ANNOTATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property<AnyAnnotationType>> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList<Property<AnyAnnotationType>>(Property.class, this, TypesPackage.OBJECT_ANNOTATION_TYPE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMinProperties() {
		return minProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinProperties(Integer newMinProperties) {
		Integer oldMinProperties = minProperties;
		minProperties = newMinProperties;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_ANNOTATION_TYPE__MIN_PROPERTIES, oldMinProperties, minProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMaxProperties() {
		return maxProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxProperties(Integer newMaxProperties) {
		Integer oldMaxProperties = maxProperties;
		maxProperties = newMaxProperties;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_ANNOTATION_TYPE__MAX_PROPERTIES, oldMaxProperties, maxProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getAdditionalProperties() {
		return additionalProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdditionalProperties(Boolean newAdditionalProperties) {
		Boolean oldAdditionalProperties = additionalProperties;
		additionalProperties = newAdditionalProperties;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_ANNOTATION_TYPE__ADDITIONAL_PROPERTIES, oldAdditionalProperties, additionalProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDiscriminator() {
		return discriminator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiscriminator(String newDiscriminator) {
		String oldDiscriminator = discriminator;
		discriminator = newDiscriminator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_ANNOTATION_TYPE__DISCRIMINATOR, oldDiscriminator, discriminator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDiscriminatorValue() {
		return discriminatorValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiscriminatorValue(String newDiscriminatorValue) {
		String oldDiscriminatorValue = discriminatorValue;
		discriminatorValue = newDiscriminatorValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_ANNOTATION_TYPE__DISCRIMINATOR_VALUE, oldDiscriminatorValue, discriminatorValue));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.OBJECT_ANNOTATION_TYPE__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_ANNOTATION_TYPE__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_ANNOTATION_TYPE__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_ANNOTATION_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_ANNOTATION_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AnnotationTarget> getAllowedTargets() {
		if (allowedTargets == null) {
			allowedTargets = new EDataTypeEList<AnnotationTarget>(AnnotationTarget.class, this, TypesPackage.OBJECT_ANNOTATION_TYPE__ALLOWED_TARGETS);
		}
		return allowedTargets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.OBJECT_ANNOTATION_TYPE__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.OBJECT_ANNOTATION_TYPE__PROPERTIES:
				return getProperties();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__MIN_PROPERTIES:
				return getMinProperties();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__MAX_PROPERTIES:
				return getMaxProperties();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__ADDITIONAL_PROPERTIES:
				return getAdditionalProperties();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISCRIMINATOR:
				return getDiscriminator();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISCRIMINATOR_VALUE:
				return getDiscriminatorValue();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__BASE_CLASS:
				return getBaseClass();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DESCRIPTION:
				return getDescription();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__NAME:
				return getName();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__ALLOWED_TARGETS:
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
			case TypesPackage.OBJECT_ANNOTATION_TYPE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends Property<AnyAnnotationType>>)newValue);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__MIN_PROPERTIES:
				setMinProperties((Integer)newValue);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__MAX_PROPERTIES:
				setMaxProperties((Integer)newValue);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__ADDITIONAL_PROPERTIES:
				setAdditionalProperties((Boolean)newValue);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISCRIMINATOR:
				setDiscriminator((String)newValue);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISCRIMINATOR_VALUE:
				setDiscriminatorValue((String)newValue);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__TYPE:
				setType((AnyAnnotationType)newValue);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__NAME:
				setName((String)newValue);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__ALLOWED_TARGETS:
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
			case TypesPackage.OBJECT_ANNOTATION_TYPE__PROPERTIES:
				getProperties().clear();
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__MIN_PROPERTIES:
				setMinProperties(MIN_PROPERTIES_EDEFAULT);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__MAX_PROPERTIES:
				setMaxProperties(MAX_PROPERTIES_EDEFAULT);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__ADDITIONAL_PROPERTIES:
				setAdditionalProperties(ADDITIONAL_PROPERTIES_EDEFAULT);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISCRIMINATOR:
				setDiscriminator(DISCRIMINATOR_EDEFAULT);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISCRIMINATOR_VALUE:
				setDiscriminatorValue(DISCRIMINATOR_VALUE_EDEFAULT);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__TYPE:
				setType((AnyAnnotationType)null);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__ALLOWED_TARGETS:
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
			case TypesPackage.OBJECT_ANNOTATION_TYPE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case TypesPackage.OBJECT_ANNOTATION_TYPE__MIN_PROPERTIES:
				return MIN_PROPERTIES_EDEFAULT == null ? minProperties != null : !MIN_PROPERTIES_EDEFAULT.equals(minProperties);
			case TypesPackage.OBJECT_ANNOTATION_TYPE__MAX_PROPERTIES:
				return MAX_PROPERTIES_EDEFAULT == null ? maxProperties != null : !MAX_PROPERTIES_EDEFAULT.equals(maxProperties);
			case TypesPackage.OBJECT_ANNOTATION_TYPE__ADDITIONAL_PROPERTIES:
				return ADDITIONAL_PROPERTIES_EDEFAULT == null ? additionalProperties != null : !ADDITIONAL_PROPERTIES_EDEFAULT.equals(additionalProperties);
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISCRIMINATOR:
				return DISCRIMINATOR_EDEFAULT == null ? discriminator != null : !DISCRIMINATOR_EDEFAULT.equals(discriminator);
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISCRIMINATOR_VALUE:
				return DISCRIMINATOR_VALUE_EDEFAULT == null ? discriminatorValue != null : !DISCRIMINATOR_VALUE_EDEFAULT.equals(discriminatorValue);
			case TypesPackage.OBJECT_ANNOTATION_TYPE__TYPE:
				return type != null;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__BASE_CLASS:
				return getBaseClass() != null;
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TypesPackage.OBJECT_ANNOTATION_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case TypesPackage.OBJECT_ANNOTATION_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TypesPackage.OBJECT_ANNOTATION_TYPE__ALLOWED_TARGETS:
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
				case TypesPackage.OBJECT_ANNOTATION_TYPE__TYPE: return TypesPackage.TYPE__TYPE;
				case TypesPackage.OBJECT_ANNOTATION_TYPE__BASE_CLASS: return TypesPackage.TYPE__BASE_CLASS;
				default: return -1;
			}
		}
		if (baseClass == DocumentableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.OBJECT_ANNOTATION_TYPE__DESCRIPTION: return ElementsPackage.DOCUMENTABLE_ELEMENT__DESCRIPTION;
				case TypesPackage.OBJECT_ANNOTATION_TYPE__DISPLAY_NAME: return ElementsPackage.DOCUMENTABLE_ELEMENT__DISPLAY_NAME;
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.OBJECT_ANNOTATION_TYPE__NAME: return ElementsPackage.IDENTIFIABLE_ELEMENT__NAME;
				default: return -1;
			}
		}
		if (baseClass == AnnotationType.class) {
			switch (derivedFeatureID) {
				case TypesPackage.OBJECT_ANNOTATION_TYPE__ALLOWED_TARGETS: return TypesPackage.ANNOTATION_TYPE__ALLOWED_TARGETS;
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
				case TypesPackage.TYPE__TYPE: return TypesPackage.OBJECT_ANNOTATION_TYPE__TYPE;
				case TypesPackage.TYPE__BASE_CLASS: return TypesPackage.OBJECT_ANNOTATION_TYPE__BASE_CLASS;
				default: return -1;
			}
		}
		if (baseClass == DocumentableElement.class) {
			switch (baseFeatureID) {
				case ElementsPackage.DOCUMENTABLE_ELEMENT__DESCRIPTION: return TypesPackage.OBJECT_ANNOTATION_TYPE__DESCRIPTION;
				case ElementsPackage.DOCUMENTABLE_ELEMENT__DISPLAY_NAME: return TypesPackage.OBJECT_ANNOTATION_TYPE__DISPLAY_NAME;
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseFeatureID) {
				case ElementsPackage.IDENTIFIABLE_ELEMENT__NAME: return TypesPackage.OBJECT_ANNOTATION_TYPE__NAME;
				default: return -1;
			}
		}
		if (baseClass == AnnotationType.class) {
			switch (baseFeatureID) {
				case TypesPackage.ANNOTATION_TYPE__ALLOWED_TARGETS: return TypesPackage.OBJECT_ANNOTATION_TYPE__ALLOWED_TARGETS;
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
		result.append(" (minProperties: ");
		result.append(minProperties);
		result.append(", maxProperties: ");
		result.append(maxProperties);
		result.append(", additionalProperties: ");
		result.append(additionalProperties);
		result.append(", discriminator: ");
		result.append(discriminator);
		result.append(", discriminatorValue: ");
		result.append(discriminatorValue);
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

} //ObjectAnnotationTypeImpl
