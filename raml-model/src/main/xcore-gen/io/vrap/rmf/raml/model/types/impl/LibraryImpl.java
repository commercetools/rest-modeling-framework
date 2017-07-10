/**
 */
package io.vrap.rmf.raml.model.types.impl;

import com.google.common.base.Objects;

import io.vrap.rmf.raml.model.types.AnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.Instance;
import io.vrap.rmf.raml.model.types.Library;
import io.vrap.rmf.raml.model.types.LibraryUse;
import io.vrap.rmf.raml.model.types.TypeContainer;
import io.vrap.rmf.raml.model.types.TypesPackage;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.LibraryImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.LibraryImpl#getUses <em>Uses</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.LibraryImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.LibraryImpl#getAnnotationTypes <em>Annotation Types</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.LibraryImpl#getUsage <em>Usage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LibraryImpl extends MinimalEObjectImpl.Container implements Library {
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
	 * The cached value of the '{@link #getUses() <em>Uses</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUses()
	 * @generated
	 * @ordered
	 */
	protected EList<LibraryUse> uses;

	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<AnyType> types;

	/**
	 * The cached value of the '{@link #getAnnotationTypes() <em>Annotation Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotationTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<AnnotationType> annotationTypes;

	/**
	 * The default value of the '{@link #getUsage() <em>Usage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsage()
	 * @generated
	 * @ordered
	 */
	protected static final String USAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUsage() <em>Usage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsage()
	 * @generated
	 * @ordered
	 */
	protected String usage = USAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instance<?>> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Instance<?>>(Instance.class, this, TypesPackage.LIBRARY__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LibraryUse> getUses() {
		if (uses == null) {
			uses = new EObjectContainmentEList<LibraryUse>(LibraryUse.class, this, TypesPackage.LIBRARY__USES);
		}
		return uses;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AnyType> getTypes() {
		if (types == null) {
			types = new EObjectContainmentEList<AnyType>(AnyType.class, this, TypesPackage.LIBRARY__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AnnotationType> getAnnotationTypes() {
		if (annotationTypes == null) {
			annotationTypes = new EObjectContainmentEList<AnnotationType>(AnnotationType.class, this, TypesPackage.LIBRARY__ANNOTATION_TYPES);
		}
		return annotationTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUsage() {
		return usage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUsage(String newUsage) {
		String oldUsage = usage;
		usage = newUsage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.LIBRARY__USAGE, oldUsage, usage));
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
			case TypesPackage.LIBRARY__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case TypesPackage.LIBRARY__USES:
				return ((InternalEList<?>)getUses()).basicRemove(otherEnd, msgs);
			case TypesPackage.LIBRARY__TYPES:
				return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
			case TypesPackage.LIBRARY__ANNOTATION_TYPES:
				return ((InternalEList<?>)getAnnotationTypes()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.LIBRARY__ANNOTATIONS:
				return getAnnotations();
			case TypesPackage.LIBRARY__USES:
				return getUses();
			case TypesPackage.LIBRARY__TYPES:
				return getTypes();
			case TypesPackage.LIBRARY__ANNOTATION_TYPES:
				return getAnnotationTypes();
			case TypesPackage.LIBRARY__USAGE:
				return getUsage();
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
			case TypesPackage.LIBRARY__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Instance<?>>)newValue);
				return;
			case TypesPackage.LIBRARY__USES:
				getUses().clear();
				getUses().addAll((Collection<? extends LibraryUse>)newValue);
				return;
			case TypesPackage.LIBRARY__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends AnyType>)newValue);
				return;
			case TypesPackage.LIBRARY__ANNOTATION_TYPES:
				getAnnotationTypes().clear();
				getAnnotationTypes().addAll((Collection<? extends AnnotationType>)newValue);
				return;
			case TypesPackage.LIBRARY__USAGE:
				setUsage((String)newValue);
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
			case TypesPackage.LIBRARY__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case TypesPackage.LIBRARY__USES:
				getUses().clear();
				return;
			case TypesPackage.LIBRARY__TYPES:
				getTypes().clear();
				return;
			case TypesPackage.LIBRARY__ANNOTATION_TYPES:
				getAnnotationTypes().clear();
				return;
			case TypesPackage.LIBRARY__USAGE:
				setUsage(USAGE_EDEFAULT);
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
			case TypesPackage.LIBRARY__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case TypesPackage.LIBRARY__USES:
				return uses != null && !uses.isEmpty();
			case TypesPackage.LIBRARY__TYPES:
				return types != null && !types.isEmpty();
			case TypesPackage.LIBRARY__ANNOTATION_TYPES:
				return annotationTypes != null && !annotationTypes.isEmpty();
			case TypesPackage.LIBRARY__USAGE:
				return USAGE_EDEFAULT == null ? usage != null : !USAGE_EDEFAULT.equals(usage);
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
		if (baseClass == TypeContainer.class) {
			switch (derivedFeatureID) {
				case TypesPackage.LIBRARY__USES: return TypesPackage.TYPE_CONTAINER__USES;
				case TypesPackage.LIBRARY__TYPES: return TypesPackage.TYPE_CONTAINER__TYPES;
				case TypesPackage.LIBRARY__ANNOTATION_TYPES: return TypesPackage.TYPE_CONTAINER__ANNOTATION_TYPES;
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
		if (baseClass == TypeContainer.class) {
			switch (baseFeatureID) {
				case TypesPackage.TYPE_CONTAINER__USES: return TypesPackage.LIBRARY__USES;
				case TypesPackage.TYPE_CONTAINER__TYPES: return TypesPackage.LIBRARY__TYPES;
				case TypesPackage.TYPE_CONTAINER__ANNOTATION_TYPES: return TypesPackage.LIBRARY__ANNOTATION_TYPES;
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
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.LIBRARY___GET_ANNOTATION__ANYTYPE:
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
		result.append(" (usage: ");
		result.append(usage);
		result.append(')');
		return result.toString();
	}

} //LibraryImpl
