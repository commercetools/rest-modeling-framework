/**
 */
package io.vrap.rmf.raml.model.types.impl;

import com.google.common.base.Objects;

import io.vrap.rmf.raml.model.facets.FacetsPackage;
import io.vrap.rmf.raml.model.facets.ObjectTypeFacet;
import io.vrap.rmf.raml.model.facets.Property;

import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.TypesPackage;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectTypeImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectTypeImpl#getMinProperties <em>Min Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectTypeImpl#getMaxProperties <em>Max Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectTypeImpl#getAdditionalProperties <em>Additional Properties</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectTypeImpl#getDiscriminator <em>Discriminator</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ObjectTypeImpl#getDiscriminatorValue <em>Discriminator Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ObjectTypeImpl extends AnyTypeImpl implements ObjectType {
	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<Property<AnyType>> properties;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ObjectTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.OBJECT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property<AnyType>> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList<Property<AnyType>>(Property.class, this, TypesPackage.OBJECT_TYPE__PROPERTIES);
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_TYPE__MIN_PROPERTIES, oldMinProperties, minProperties));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_TYPE__MAX_PROPERTIES, oldMaxProperties, maxProperties));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_TYPE__ADDITIONAL_PROPERTIES, oldAdditionalProperties, additionalProperties));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_TYPE__DISCRIMINATOR, oldDiscriminator, discriminator));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.OBJECT_TYPE__DISCRIMINATOR_VALUE, oldDiscriminatorValue, discriminatorValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property<AnyType> getProperty(final String name) {
		EList<Property<AnyType>> _properties = this.getProperties();
		for (final Property<AnyType> property : _properties) {
			String _name = property.getName();
			boolean _equals = Objects.equal(name, _name);
			if (_equals) {
				return property;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.OBJECT_TYPE__PROPERTIES:
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
			case TypesPackage.OBJECT_TYPE__PROPERTIES:
				return getProperties();
			case TypesPackage.OBJECT_TYPE__MIN_PROPERTIES:
				return getMinProperties();
			case TypesPackage.OBJECT_TYPE__MAX_PROPERTIES:
				return getMaxProperties();
			case TypesPackage.OBJECT_TYPE__ADDITIONAL_PROPERTIES:
				return getAdditionalProperties();
			case TypesPackage.OBJECT_TYPE__DISCRIMINATOR:
				return getDiscriminator();
			case TypesPackage.OBJECT_TYPE__DISCRIMINATOR_VALUE:
				return getDiscriminatorValue();
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
			case TypesPackage.OBJECT_TYPE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends Property<AnyType>>)newValue);
				return;
			case TypesPackage.OBJECT_TYPE__MIN_PROPERTIES:
				setMinProperties((Integer)newValue);
				return;
			case TypesPackage.OBJECT_TYPE__MAX_PROPERTIES:
				setMaxProperties((Integer)newValue);
				return;
			case TypesPackage.OBJECT_TYPE__ADDITIONAL_PROPERTIES:
				setAdditionalProperties((Boolean)newValue);
				return;
			case TypesPackage.OBJECT_TYPE__DISCRIMINATOR:
				setDiscriminator((String)newValue);
				return;
			case TypesPackage.OBJECT_TYPE__DISCRIMINATOR_VALUE:
				setDiscriminatorValue((String)newValue);
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
			case TypesPackage.OBJECT_TYPE__PROPERTIES:
				getProperties().clear();
				return;
			case TypesPackage.OBJECT_TYPE__MIN_PROPERTIES:
				setMinProperties(MIN_PROPERTIES_EDEFAULT);
				return;
			case TypesPackage.OBJECT_TYPE__MAX_PROPERTIES:
				setMaxProperties(MAX_PROPERTIES_EDEFAULT);
				return;
			case TypesPackage.OBJECT_TYPE__ADDITIONAL_PROPERTIES:
				setAdditionalProperties(ADDITIONAL_PROPERTIES_EDEFAULT);
				return;
			case TypesPackage.OBJECT_TYPE__DISCRIMINATOR:
				setDiscriminator(DISCRIMINATOR_EDEFAULT);
				return;
			case TypesPackage.OBJECT_TYPE__DISCRIMINATOR_VALUE:
				setDiscriminatorValue(DISCRIMINATOR_VALUE_EDEFAULT);
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
			case TypesPackage.OBJECT_TYPE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case TypesPackage.OBJECT_TYPE__MIN_PROPERTIES:
				return MIN_PROPERTIES_EDEFAULT == null ? minProperties != null : !MIN_PROPERTIES_EDEFAULT.equals(minProperties);
			case TypesPackage.OBJECT_TYPE__MAX_PROPERTIES:
				return MAX_PROPERTIES_EDEFAULT == null ? maxProperties != null : !MAX_PROPERTIES_EDEFAULT.equals(maxProperties);
			case TypesPackage.OBJECT_TYPE__ADDITIONAL_PROPERTIES:
				return ADDITIONAL_PROPERTIES_EDEFAULT == null ? additionalProperties != null : !ADDITIONAL_PROPERTIES_EDEFAULT.equals(additionalProperties);
			case TypesPackage.OBJECT_TYPE__DISCRIMINATOR:
				return DISCRIMINATOR_EDEFAULT == null ? discriminator != null : !DISCRIMINATOR_EDEFAULT.equals(discriminator);
			case TypesPackage.OBJECT_TYPE__DISCRIMINATOR_VALUE:
				return DISCRIMINATOR_VALUE_EDEFAULT == null ? discriminatorValue != null : !DISCRIMINATOR_VALUE_EDEFAULT.equals(discriminatorValue);
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
		if (baseClass == ObjectTypeFacet.class) {
			switch (derivedFeatureID) {
				case TypesPackage.OBJECT_TYPE__PROPERTIES: return FacetsPackage.OBJECT_TYPE_FACET__PROPERTIES;
				case TypesPackage.OBJECT_TYPE__MIN_PROPERTIES: return FacetsPackage.OBJECT_TYPE_FACET__MIN_PROPERTIES;
				case TypesPackage.OBJECT_TYPE__MAX_PROPERTIES: return FacetsPackage.OBJECT_TYPE_FACET__MAX_PROPERTIES;
				case TypesPackage.OBJECT_TYPE__ADDITIONAL_PROPERTIES: return FacetsPackage.OBJECT_TYPE_FACET__ADDITIONAL_PROPERTIES;
				case TypesPackage.OBJECT_TYPE__DISCRIMINATOR: return FacetsPackage.OBJECT_TYPE_FACET__DISCRIMINATOR;
				case TypesPackage.OBJECT_TYPE__DISCRIMINATOR_VALUE: return FacetsPackage.OBJECT_TYPE_FACET__DISCRIMINATOR_VALUE;
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
		if (baseClass == ObjectTypeFacet.class) {
			switch (baseFeatureID) {
				case FacetsPackage.OBJECT_TYPE_FACET__PROPERTIES: return TypesPackage.OBJECT_TYPE__PROPERTIES;
				case FacetsPackage.OBJECT_TYPE_FACET__MIN_PROPERTIES: return TypesPackage.OBJECT_TYPE__MIN_PROPERTIES;
				case FacetsPackage.OBJECT_TYPE_FACET__MAX_PROPERTIES: return TypesPackage.OBJECT_TYPE__MAX_PROPERTIES;
				case FacetsPackage.OBJECT_TYPE_FACET__ADDITIONAL_PROPERTIES: return TypesPackage.OBJECT_TYPE__ADDITIONAL_PROPERTIES;
				case FacetsPackage.OBJECT_TYPE_FACET__DISCRIMINATOR: return TypesPackage.OBJECT_TYPE__DISCRIMINATOR;
				case FacetsPackage.OBJECT_TYPE_FACET__DISCRIMINATOR_VALUE: return TypesPackage.OBJECT_TYPE__DISCRIMINATOR_VALUE;
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.OBJECT_TYPE___GET_PROPERTY__STRING:
				return getProperty((String)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(')');
		return result.toString();
	}

} //ObjectTypeImpl
