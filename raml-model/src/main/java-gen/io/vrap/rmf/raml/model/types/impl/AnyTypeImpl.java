/**
 */
package io.vrap.rmf.raml.model.types.impl;

import com.google.common.base.Objects;

import io.vrap.rmf.raml.model.elements.DocumentableElement;
import io.vrap.rmf.raml.model.elements.ElementsPackage;
import io.vrap.rmf.raml.model.elements.IdentifiableElement;

import io.vrap.rmf.raml.model.facets.EnumFacet;
import io.vrap.rmf.raml.model.facets.FacetsPackage;

import io.vrap.rmf.raml.model.types.Annotatable;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.Instance;
import io.vrap.rmf.raml.model.types.Type;
import io.vrap.rmf.raml.model.types.TypesPackage;

import java.lang.reflect.InvocationTargetException;

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

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Any Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.AnyTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.AnyTypeImpl#getBaseClass <em>Base Class</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.AnyTypeImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.AnyTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.AnyTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.AnyTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.AnyTypeImpl#getEnum <em>Enum</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnyTypeImpl extends MinimalEObjectImpl.Container implements AnyType {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected AnyType type;

	/**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<Instance<?>> annotations;

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
	 * The cached value of the '{@link #getEnum() <em>Enum</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnum()
	 * @generated
	 * @ordered
	 */
	protected EList<String> enum_;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnyTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.ANY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnyType getType() {
		if (type != null && ((EObject)type).eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (AnyType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.ANY_TYPE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnyType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(AnyType newType) {
		AnyType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ANY_TYPE__TYPE, oldType, type));
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
	public EList<Instance<?>> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Instance<?>>(Instance.class, this, TypesPackage.ANY_TYPE__ANNOTATIONS);
		}
		return annotations;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ANY_TYPE__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ANY_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ANY_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getEnum() {
		if (enum_ == null) {
			enum_ = new EDataTypeEList<String>(String.class, this, TypesPackage.ANY_TYPE__ENUM);
		}
		return enum_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <T extends AnyType, A extends Instance<T>> A getAnnotation(final T annotationType) {
		A _xblockexpression = null; {
			EList<Instance<?>> _annotations = this.getAnnotations();
			final Function1<Instance<?>, Boolean> _function = new Function1<Instance<?>, Boolean>() {
				public Boolean apply(final Instance<?> it) {
					AnyType _type = it.getType();
					return Boolean.valueOf(Objects.equal(_type, annotationType));
				}
			};
			Instance<?> a = IterableExtensions.<Instance<?>>findFirst(_annotations, _function);
			A _xifexpression = null;
			boolean _notEquals = (!Objects.equal(a, null));
			if (_notEquals) {
				_xifexpression = ((A) a);
			}
			else {
				_xifexpression = null;
			}
			_xblockexpression = _xifexpression;
		}
		return _xblockexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.ANY_TYPE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.ANY_TYPE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case TypesPackage.ANY_TYPE__BASE_CLASS:
				return getBaseClass();
			case TypesPackage.ANY_TYPE__ANNOTATIONS:
				return getAnnotations();
			case TypesPackage.ANY_TYPE__DESCRIPTION:
				return getDescription();
			case TypesPackage.ANY_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case TypesPackage.ANY_TYPE__NAME:
				return getName();
			case TypesPackage.ANY_TYPE__ENUM:
				return getEnum();
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
			case TypesPackage.ANY_TYPE__TYPE:
				setType((AnyType)newValue);
				return;
			case TypesPackage.ANY_TYPE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Instance<?>>)newValue);
				return;
			case TypesPackage.ANY_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TypesPackage.ANY_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case TypesPackage.ANY_TYPE__NAME:
				setName((String)newValue);
				return;
			case TypesPackage.ANY_TYPE__ENUM:
				getEnum().clear();
				getEnum().addAll((Collection<? extends String>)newValue);
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
			case TypesPackage.ANY_TYPE__TYPE:
				setType((AnyType)null);
				return;
			case TypesPackage.ANY_TYPE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case TypesPackage.ANY_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TypesPackage.ANY_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case TypesPackage.ANY_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TypesPackage.ANY_TYPE__ENUM:
				getEnum().clear();
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
			case TypesPackage.ANY_TYPE__TYPE:
				return type != null;
			case TypesPackage.ANY_TYPE__BASE_CLASS:
				return getBaseClass() != null;
			case TypesPackage.ANY_TYPE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case TypesPackage.ANY_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TypesPackage.ANY_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case TypesPackage.ANY_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TypesPackage.ANY_TYPE__ENUM:
				return enum_ != null && !enum_.isEmpty();
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
		if (baseClass == Annotatable.class) {
			switch (derivedFeatureID) {
				case TypesPackage.ANY_TYPE__ANNOTATIONS: return TypesPackage.ANNOTATABLE__ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == DocumentableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.ANY_TYPE__DESCRIPTION: return ElementsPackage.DOCUMENTABLE_ELEMENT__DESCRIPTION;
				case TypesPackage.ANY_TYPE__DISPLAY_NAME: return ElementsPackage.DOCUMENTABLE_ELEMENT__DISPLAY_NAME;
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.ANY_TYPE__NAME: return ElementsPackage.IDENTIFIABLE_ELEMENT__NAME;
				default: return -1;
			}
		}
		if (baseClass == EnumFacet.class) {
			switch (derivedFeatureID) {
				case TypesPackage.ANY_TYPE__ENUM: return FacetsPackage.ENUM_FACET__ENUM;
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
		if (baseClass == Annotatable.class) {
			switch (baseFeatureID) {
				case TypesPackage.ANNOTATABLE__ANNOTATIONS: return TypesPackage.ANY_TYPE__ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == DocumentableElement.class) {
			switch (baseFeatureID) {
				case ElementsPackage.DOCUMENTABLE_ELEMENT__DESCRIPTION: return TypesPackage.ANY_TYPE__DESCRIPTION;
				case ElementsPackage.DOCUMENTABLE_ELEMENT__DISPLAY_NAME: return TypesPackage.ANY_TYPE__DISPLAY_NAME;
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseFeatureID) {
				case ElementsPackage.IDENTIFIABLE_ELEMENT__NAME: return TypesPackage.ANY_TYPE__NAME;
				default: return -1;
			}
		}
		if (baseClass == EnumFacet.class) {
			switch (baseFeatureID) {
				case FacetsPackage.ENUM_FACET__ENUM: return TypesPackage.ANY_TYPE__ENUM;
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
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == Annotatable.class) {
			switch (baseOperationID) {
				case TypesPackage.ANNOTATABLE___GET_ANNOTATION__ANYTYPE: return TypesPackage.ANY_TYPE___GET_ANNOTATION__ANYTYPE;
				default: return -1;
			}
		}
		if (baseClass == DocumentableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == EnumFacet.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.ANY_TYPE___GET_ANNOTATION__ANYTYPE:
				return getAnnotation((AnyType)arguments.get(0));
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
		result.append(" (description: ");
		result.append(description);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", name: ");
		result.append(name);
		result.append(", enum: ");
		result.append(enum_);
		result.append(')');
		return result.toString();
	}

} //AnyTypeImpl
