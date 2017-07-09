/**
 */
package io.vrap.rmf.raml.model.types.impl;

import com.google.common.base.Objects;

import io.vrap.rmf.raml.model.types.AnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.Api;
import io.vrap.rmf.raml.model.types.Instance;
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

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Api</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getUses <em>Uses</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getAnnotationTypes <em>Annotation Types</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getBaseUri <em>Base Uri</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getProtocols <em>Protocols</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getMediaType <em>Media Type</em>}</li>
 *   <li>{@link io.vrap.rmf.raml.model.types.impl.ApiImpl#getSecuredBy <em>Secured By</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ApiImpl extends MinimalEObjectImpl.Container implements Api {
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
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

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
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getBaseUri() <em>Base Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseUri()
	 * @generated
	 * @ordered
	 */
	protected static final String BASE_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBaseUri() <em>Base Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseUri()
	 * @generated
	 * @ordered
	 */
	protected String baseUri = BASE_URI_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProtocols() <em>Protocols</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocols()
	 * @generated
	 * @ordered
	 */
	protected EList<String> protocols;

	/**
	 * The cached value of the '{@link #getMediaType() <em>Media Type</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMediaType()
	 * @generated
	 * @ordered
	 */
	protected EList<String> mediaType;

	/**
	 * The cached value of the '{@link #getSecuredBy() <em>Secured By</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecuredBy()
	 * @generated
	 * @ordered
	 */
	protected EList<String> securedBy;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApiImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.API;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instance<?>> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Instance<?>>(Instance.class, this, TypesPackage.API__ANNOTATIONS);
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
			uses = new EObjectContainmentEList<LibraryUse>(LibraryUse.class, this, TypesPackage.API__USES);
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
			types = new EObjectContainmentEList<AnyType>(AnyType.class, this, TypesPackage.API__TYPES);
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
			annotationTypes = new EObjectContainmentEList<AnnotationType>(AnnotationType.class, this, TypesPackage.API__ANNOTATION_TYPES);
		}
		return annotationTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.API__TITLE, oldTitle, title));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.API__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.API__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBaseUri() {
		return baseUri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseUri(String newBaseUri) {
		String oldBaseUri = baseUri;
		baseUri = newBaseUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.API__BASE_URI, oldBaseUri, baseUri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getProtocols() {
		if (protocols == null) {
			protocols = new EDataTypeEList<String>(String.class, this, TypesPackage.API__PROTOCOLS);
		}
		return protocols;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getMediaType() {
		if (mediaType == null) {
			mediaType = new EDataTypeEList<String>(String.class, this, TypesPackage.API__MEDIA_TYPE);
		}
		return mediaType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getSecuredBy() {
		if (securedBy == null) {
			securedBy = new EDataTypeEList<String>(String.class, this, TypesPackage.API__SECURED_BY);
		}
		return securedBy;
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
			case TypesPackage.API__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case TypesPackage.API__USES:
				return ((InternalEList<?>)getUses()).basicRemove(otherEnd, msgs);
			case TypesPackage.API__TYPES:
				return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
			case TypesPackage.API__ANNOTATION_TYPES:
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
			case TypesPackage.API__ANNOTATIONS:
				return getAnnotations();
			case TypesPackage.API__USES:
				return getUses();
			case TypesPackage.API__TYPES:
				return getTypes();
			case TypesPackage.API__ANNOTATION_TYPES:
				return getAnnotationTypes();
			case TypesPackage.API__TITLE:
				return getTitle();
			case TypesPackage.API__DESCRIPTION:
				return getDescription();
			case TypesPackage.API__VERSION:
				return getVersion();
			case TypesPackage.API__BASE_URI:
				return getBaseUri();
			case TypesPackage.API__PROTOCOLS:
				return getProtocols();
			case TypesPackage.API__MEDIA_TYPE:
				return getMediaType();
			case TypesPackage.API__SECURED_BY:
				return getSecuredBy();
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
			case TypesPackage.API__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Instance<?>>)newValue);
				return;
			case TypesPackage.API__USES:
				getUses().clear();
				getUses().addAll((Collection<? extends LibraryUse>)newValue);
				return;
			case TypesPackage.API__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends AnyType>)newValue);
				return;
			case TypesPackage.API__ANNOTATION_TYPES:
				getAnnotationTypes().clear();
				getAnnotationTypes().addAll((Collection<? extends AnnotationType>)newValue);
				return;
			case TypesPackage.API__TITLE:
				setTitle((String)newValue);
				return;
			case TypesPackage.API__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TypesPackage.API__VERSION:
				setVersion((String)newValue);
				return;
			case TypesPackage.API__BASE_URI:
				setBaseUri((String)newValue);
				return;
			case TypesPackage.API__PROTOCOLS:
				getProtocols().clear();
				getProtocols().addAll((Collection<? extends String>)newValue);
				return;
			case TypesPackage.API__MEDIA_TYPE:
				getMediaType().clear();
				getMediaType().addAll((Collection<? extends String>)newValue);
				return;
			case TypesPackage.API__SECURED_BY:
				getSecuredBy().clear();
				getSecuredBy().addAll((Collection<? extends String>)newValue);
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
			case TypesPackage.API__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case TypesPackage.API__USES:
				getUses().clear();
				return;
			case TypesPackage.API__TYPES:
				getTypes().clear();
				return;
			case TypesPackage.API__ANNOTATION_TYPES:
				getAnnotationTypes().clear();
				return;
			case TypesPackage.API__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case TypesPackage.API__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TypesPackage.API__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case TypesPackage.API__BASE_URI:
				setBaseUri(BASE_URI_EDEFAULT);
				return;
			case TypesPackage.API__PROTOCOLS:
				getProtocols().clear();
				return;
			case TypesPackage.API__MEDIA_TYPE:
				getMediaType().clear();
				return;
			case TypesPackage.API__SECURED_BY:
				getSecuredBy().clear();
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
			case TypesPackage.API__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case TypesPackage.API__USES:
				return uses != null && !uses.isEmpty();
			case TypesPackage.API__TYPES:
				return types != null && !types.isEmpty();
			case TypesPackage.API__ANNOTATION_TYPES:
				return annotationTypes != null && !annotationTypes.isEmpty();
			case TypesPackage.API__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case TypesPackage.API__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TypesPackage.API__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case TypesPackage.API__BASE_URI:
				return BASE_URI_EDEFAULT == null ? baseUri != null : !BASE_URI_EDEFAULT.equals(baseUri);
			case TypesPackage.API__PROTOCOLS:
				return protocols != null && !protocols.isEmpty();
			case TypesPackage.API__MEDIA_TYPE:
				return mediaType != null && !mediaType.isEmpty();
			case TypesPackage.API__SECURED_BY:
				return securedBy != null && !securedBy.isEmpty();
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
				case TypesPackage.API__USES: return TypesPackage.TYPE_CONTAINER__USES;
				case TypesPackage.API__TYPES: return TypesPackage.TYPE_CONTAINER__TYPES;
				case TypesPackage.API__ANNOTATION_TYPES: return TypesPackage.TYPE_CONTAINER__ANNOTATION_TYPES;
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
				case TypesPackage.TYPE_CONTAINER__USES: return TypesPackage.API__USES;
				case TypesPackage.TYPE_CONTAINER__TYPES: return TypesPackage.API__TYPES;
				case TypesPackage.TYPE_CONTAINER__ANNOTATION_TYPES: return TypesPackage.API__ANNOTATION_TYPES;
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
			case TypesPackage.API___GET_ANNOTATION__ANYTYPE:
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
		result.append(" (title: ");
		result.append(title);
		result.append(", description: ");
		result.append(description);
		result.append(", version: ");
		result.append(version);
		result.append(", baseUri: ");
		result.append(baseUri);
		result.append(", protocols: ");
		result.append(protocols);
		result.append(", mediaType: ");
		result.append(mediaType);
		result.append(", securedBy: ");
		result.append(securedBy);
		result.append(')');
		return result.toString();
	}

} //ApiImpl
