/**
 */
package io.vrap.rmf.raml.model.types;

import io.vrap.rmf.raml.model.facets.FacetsPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see io.vrap.rmf.raml.model.types.TypesFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelDirectory='/raml-model/src/main/xcore-gen' complianceLevel='8.0' basePackage='io.vrap.rmf.raml.model'"
 * @generated
 */
public interface TypesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "types";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.vrap.io/raml/types";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "types";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TypesPackage eINSTANCE = io.vrap.rmf.raml.model.types.impl.TypesPackageImpl.init();

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.Annotatable <em>Annotatable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.Annotatable
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getAnnotatable()
	 * @generated
	 */
	int ANNOTATABLE = 21;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATABLE__ANNOTATIONS = 0;

	/**
	 * The number of structural features of the '<em>Annotatable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATABLE_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATABLE___GET_ANNOTATION__ANYTYPE = 0;

	/**
	 * The number of operations of the '<em>Annotatable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATABLE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.ApiImpl <em>Api</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.ApiImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getApi()
	 * @generated
	 */
	int API = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__ANNOTATIONS = ANNOTATABLE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Uses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__USES = ANNOTATABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__TYPES = ANNOTATABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotation Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__ANNOTATION_TYPES = ANNOTATABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__TITLE = ANNOTATABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__DESCRIPTION = ANNOTATABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__VERSION = ANNOTATABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Base Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__BASE_URI = ANNOTATABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Protocols</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__PROTOCOLS = ANNOTATABLE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Media Type</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__MEDIA_TYPE = ANNOTATABLE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Secured By</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API__SECURED_BY = ANNOTATABLE_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Api</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API_FEATURE_COUNT = ANNOTATABLE_FEATURE_COUNT + 10;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API___GET_ANNOTATION__ANYTYPE = ANNOTATABLE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Api</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API_OPERATION_COUNT = ANNOTATABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.TypeContainer <em>Type Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.TypeContainer
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getTypeContainer()
	 * @generated
	 */
	int TYPE_CONTAINER = 1;

	/**
	 * The feature id for the '<em><b>Uses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONTAINER__USES = 0;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONTAINER__TYPES = 1;

	/**
	 * The feature id for the '<em><b>Annotation Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONTAINER__ANNOTATION_TYPES = 2;

	/**
	 * The number of structural features of the '<em>Type Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONTAINER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Type Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.LibraryImpl <em>Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.LibraryImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getLibrary()
	 * @generated
	 */
	int LIBRARY = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__ANNOTATIONS = ANNOTATABLE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Uses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__USES = ANNOTATABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__TYPES = ANNOTATABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotation Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__ANNOTATION_TYPES = ANNOTATABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Usage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__USAGE = ANNOTATABLE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = ANNOTATABLE_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY___GET_ANNOTATION__ANYTYPE = ANNOTATABLE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_OPERATION_COUNT = ANNOTATABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.LibraryUseImpl <em>Library Use</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.LibraryUseImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getLibraryUse()
	 * @generated
	 */
	int LIBRARY_USE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_USE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Library</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_USE__LIBRARY = 1;

	/**
	 * The number of structural features of the '<em>Library Use</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_USE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Library Use</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_USE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.Type <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.Type
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__BASE_CLASS = 1;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.DataType <em>Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.DataType
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__TYPE = TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__BASE_CLASS = TYPE__BASE_CLASS;

	/**
	 * The number of structural features of the '<em>Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_OPERATION_COUNT = TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.AnnotationType <em>Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.AnnotationType
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getAnnotationType()
	 * @generated
	 */
	int ANNOTATION_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_TYPE__TYPE = TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_TYPE__BASE_CLASS = TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_TYPE__DESCRIPTION = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_TYPE__DISPLAY_NAME = TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_TYPE__NAME = TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_TYPE__ALLOWED_TARGETS = TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_TYPE_OPERATION_COUNT = TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.AnyAnnotationTypeImpl <em>Any Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.AnyAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getAnyAnnotationType()
	 * @generated
	 */
	int ANY_ANNOTATION_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ANNOTATION_TYPE__TYPE = ANNOTATION_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ANNOTATION_TYPE__BASE_CLASS = ANNOTATION_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ANNOTATION_TYPE__DESCRIPTION = ANNOTATION_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ANNOTATION_TYPE__DISPLAY_NAME = ANNOTATION_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ANNOTATION_TYPE__NAME = ANNOTATION_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ANNOTATION_TYPE__ALLOWED_TARGETS = ANNOTATION_TYPE__ALLOWED_TARGETS;

	/**
	 * The number of structural features of the '<em>Any Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ANNOTATION_TYPE_FEATURE_COUNT = ANNOTATION_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Any Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ANNOTATION_TYPE_OPERATION_COUNT = ANNOTATION_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.TimeOnlyAnnotationTypeImpl <em>Time Only Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.TimeOnlyAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getTimeOnlyAnnotationType()
	 * @generated
	 */
	int TIME_ONLY_ANNOTATION_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_ANNOTATION_TYPE__TYPE = ANNOTATION_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_ANNOTATION_TYPE__BASE_CLASS = ANNOTATION_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_ANNOTATION_TYPE__DESCRIPTION = ANNOTATION_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_ANNOTATION_TYPE__DISPLAY_NAME = ANNOTATION_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_ANNOTATION_TYPE__NAME = ANNOTATION_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_ANNOTATION_TYPE__ALLOWED_TARGETS = ANNOTATION_TYPE__ALLOWED_TARGETS;

	/**
	 * The number of structural features of the '<em>Time Only Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_ANNOTATION_TYPE_FEATURE_COUNT = ANNOTATION_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Time Only Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_ANNOTATION_TYPE_OPERATION_COUNT = ANNOTATION_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.DateTimeAnnotationTypeImpl <em>Date Time Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.DateTimeAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateTimeAnnotationType()
	 * @generated
	 */
	int DATE_TIME_ANNOTATION_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ANNOTATION_TYPE__FORMAT = FacetsPackage.DATE_TIME_TYPE_FACET__FORMAT;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ANNOTATION_TYPE__TYPE = FacetsPackage.DATE_TIME_TYPE_FACET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ANNOTATION_TYPE__BASE_CLASS = FacetsPackage.DATE_TIME_TYPE_FACET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ANNOTATION_TYPE__DESCRIPTION = FacetsPackage.DATE_TIME_TYPE_FACET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ANNOTATION_TYPE__DISPLAY_NAME = FacetsPackage.DATE_TIME_TYPE_FACET_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ANNOTATION_TYPE__NAME = FacetsPackage.DATE_TIME_TYPE_FACET_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ANNOTATION_TYPE__ALLOWED_TARGETS = FacetsPackage.DATE_TIME_TYPE_FACET_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Date Time Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ANNOTATION_TYPE_FEATURE_COUNT = FacetsPackage.DATE_TIME_TYPE_FACET_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Date Time Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ANNOTATION_TYPE_OPERATION_COUNT = FacetsPackage.DATE_TIME_TYPE_FACET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.DateTimeOnlyAnnotationTypeImpl <em>Date Time Only Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.DateTimeOnlyAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateTimeOnlyAnnotationType()
	 * @generated
	 */
	int DATE_TIME_ONLY_ANNOTATION_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_ANNOTATION_TYPE__TYPE = ANNOTATION_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_ANNOTATION_TYPE__BASE_CLASS = ANNOTATION_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_ANNOTATION_TYPE__DESCRIPTION = ANNOTATION_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_ANNOTATION_TYPE__DISPLAY_NAME = ANNOTATION_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_ANNOTATION_TYPE__NAME = ANNOTATION_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_ANNOTATION_TYPE__ALLOWED_TARGETS = ANNOTATION_TYPE__ALLOWED_TARGETS;

	/**
	 * The number of structural features of the '<em>Date Time Only Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_ANNOTATION_TYPE_FEATURE_COUNT = ANNOTATION_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Date Time Only Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_ANNOTATION_TYPE_OPERATION_COUNT = ANNOTATION_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.DateOnlyAnnotationTypeImpl <em>Date Only Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.DateOnlyAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateOnlyAnnotationType()
	 * @generated
	 */
	int DATE_ONLY_ANNOTATION_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_ANNOTATION_TYPE__TYPE = ANNOTATION_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_ANNOTATION_TYPE__BASE_CLASS = ANNOTATION_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_ANNOTATION_TYPE__DESCRIPTION = ANNOTATION_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_ANNOTATION_TYPE__DISPLAY_NAME = ANNOTATION_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_ANNOTATION_TYPE__NAME = ANNOTATION_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_ANNOTATION_TYPE__ALLOWED_TARGETS = ANNOTATION_TYPE__ALLOWED_TARGETS;

	/**
	 * The number of structural features of the '<em>Date Only Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_ANNOTATION_TYPE_FEATURE_COUNT = ANNOTATION_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Date Only Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_ANNOTATION_TYPE_OPERATION_COUNT = ANNOTATION_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl <em>Number Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getNumberAnnotationType()
	 * @generated
	 */
	int NUMBER_ANNOTATION_TYPE = 12;

	/**
	 * The feature id for the '<em><b>Minimum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE__MINIMUM = FacetsPackage.NUMBER_TYPE_FACET__MINIMUM;

	/**
	 * The feature id for the '<em><b>Maximum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE__MAXIMUM = FacetsPackage.NUMBER_TYPE_FACET__MAXIMUM;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE__FORMAT = FacetsPackage.NUMBER_TYPE_FACET__FORMAT;

	/**
	 * The feature id for the '<em><b>Multiple Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE__MULTIPLE_OF = FacetsPackage.NUMBER_TYPE_FACET__MULTIPLE_OF;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE__TYPE = FacetsPackage.NUMBER_TYPE_FACET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE__BASE_CLASS = FacetsPackage.NUMBER_TYPE_FACET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE__DESCRIPTION = FacetsPackage.NUMBER_TYPE_FACET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE__DISPLAY_NAME = FacetsPackage.NUMBER_TYPE_FACET_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE__NAME = FacetsPackage.NUMBER_TYPE_FACET_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE__ALLOWED_TARGETS = FacetsPackage.NUMBER_TYPE_FACET_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Number Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE_FEATURE_COUNT = FacetsPackage.NUMBER_TYPE_FACET_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Number Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_ANNOTATION_TYPE_OPERATION_COUNT = FacetsPackage.NUMBER_TYPE_FACET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.IntegerAnnotationTypeImpl <em>Integer Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.IntegerAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getIntegerAnnotationType()
	 * @generated
	 */
	int INTEGER_ANNOTATION_TYPE = 13;

	/**
	 * The feature id for the '<em><b>Minimum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE__MINIMUM = NUMBER_ANNOTATION_TYPE__MINIMUM;

	/**
	 * The feature id for the '<em><b>Maximum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE__MAXIMUM = NUMBER_ANNOTATION_TYPE__MAXIMUM;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE__FORMAT = NUMBER_ANNOTATION_TYPE__FORMAT;

	/**
	 * The feature id for the '<em><b>Multiple Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE__MULTIPLE_OF = NUMBER_ANNOTATION_TYPE__MULTIPLE_OF;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE__TYPE = NUMBER_ANNOTATION_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE__BASE_CLASS = NUMBER_ANNOTATION_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE__DESCRIPTION = NUMBER_ANNOTATION_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE__DISPLAY_NAME = NUMBER_ANNOTATION_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE__NAME = NUMBER_ANNOTATION_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE__ALLOWED_TARGETS = NUMBER_ANNOTATION_TYPE__ALLOWED_TARGETS;

	/**
	 * The number of structural features of the '<em>Integer Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE_FEATURE_COUNT = NUMBER_ANNOTATION_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Integer Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_ANNOTATION_TYPE_OPERATION_COUNT = NUMBER_ANNOTATION_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.BooleanAnnotationTypeImpl <em>Boolean Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.BooleanAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getBooleanAnnotationType()
	 * @generated
	 */
	int BOOLEAN_ANNOTATION_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_ANNOTATION_TYPE__TYPE = ANNOTATION_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_ANNOTATION_TYPE__BASE_CLASS = ANNOTATION_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_ANNOTATION_TYPE__DESCRIPTION = ANNOTATION_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_ANNOTATION_TYPE__DISPLAY_NAME = ANNOTATION_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_ANNOTATION_TYPE__NAME = ANNOTATION_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_ANNOTATION_TYPE__ALLOWED_TARGETS = ANNOTATION_TYPE__ALLOWED_TARGETS;

	/**
	 * The number of structural features of the '<em>Boolean Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_ANNOTATION_TYPE_FEATURE_COUNT = ANNOTATION_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Boolean Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_ANNOTATION_TYPE_OPERATION_COUNT = ANNOTATION_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.StringAnnotationTypeImpl <em>String Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.StringAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getStringAnnotationType()
	 * @generated
	 */
	int STRING_ANNOTATION_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE__PATTERN = FacetsPackage.STRING_TYPE_FACET__PATTERN;

	/**
	 * The feature id for the '<em><b>Min Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE__MIN_LENGTH = FacetsPackage.STRING_TYPE_FACET__MIN_LENGTH;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE__MAX_LENGTH = FacetsPackage.STRING_TYPE_FACET__MAX_LENGTH;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE__TYPE = FacetsPackage.STRING_TYPE_FACET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE__BASE_CLASS = FacetsPackage.STRING_TYPE_FACET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE__DESCRIPTION = FacetsPackage.STRING_TYPE_FACET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE__DISPLAY_NAME = FacetsPackage.STRING_TYPE_FACET_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE__NAME = FacetsPackage.STRING_TYPE_FACET_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE__ALLOWED_TARGETS = FacetsPackage.STRING_TYPE_FACET_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>String Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE_FEATURE_COUNT = FacetsPackage.STRING_TYPE_FACET_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>String Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE_OPERATION_COUNT = FacetsPackage.STRING_TYPE_FACET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.NilAnnotationTypeImpl <em>Nil Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.NilAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getNilAnnotationType()
	 * @generated
	 */
	int NIL_ANNOTATION_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_ANNOTATION_TYPE__TYPE = ANNOTATION_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_ANNOTATION_TYPE__BASE_CLASS = ANNOTATION_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_ANNOTATION_TYPE__DESCRIPTION = ANNOTATION_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_ANNOTATION_TYPE__DISPLAY_NAME = ANNOTATION_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_ANNOTATION_TYPE__NAME = ANNOTATION_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_ANNOTATION_TYPE__ALLOWED_TARGETS = ANNOTATION_TYPE__ALLOWED_TARGETS;

	/**
	 * The number of structural features of the '<em>Nil Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_ANNOTATION_TYPE_FEATURE_COUNT = ANNOTATION_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Nil Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_ANNOTATION_TYPE_OPERATION_COUNT = ANNOTATION_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.FileAnnotationTypeImpl <em>File Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.FileAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getFileAnnotationType()
	 * @generated
	 */
	int FILE_ANNOTATION_TYPE = 17;

	/**
	 * The feature id for the '<em><b>File Types</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE__FILE_TYPES = FacetsPackage.FILE_TYPE_FACET__FILE_TYPES;

	/**
	 * The feature id for the '<em><b>Min Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE__MIN_LENGTH = FacetsPackage.FILE_TYPE_FACET__MIN_LENGTH;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE__MAX_LENGTH = FacetsPackage.FILE_TYPE_FACET__MAX_LENGTH;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE__TYPE = FacetsPackage.FILE_TYPE_FACET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE__BASE_CLASS = FacetsPackage.FILE_TYPE_FACET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE__DESCRIPTION = FacetsPackage.FILE_TYPE_FACET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE__DISPLAY_NAME = FacetsPackage.FILE_TYPE_FACET_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE__NAME = FacetsPackage.FILE_TYPE_FACET_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE__ALLOWED_TARGETS = FacetsPackage.FILE_TYPE_FACET_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>File Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE_FEATURE_COUNT = FacetsPackage.FILE_TYPE_FACET_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>File Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ANNOTATION_TYPE_OPERATION_COUNT = FacetsPackage.FILE_TYPE_FACET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl <em>Object Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getObjectAnnotationType()
	 * @generated
	 */
	int OBJECT_ANNOTATION_TYPE = 18;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__PROPERTIES = FacetsPackage.OBJECT_TYPE_FACET__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Min Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__MIN_PROPERTIES = FacetsPackage.OBJECT_TYPE_FACET__MIN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Max Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__MAX_PROPERTIES = FacetsPackage.OBJECT_TYPE_FACET__MAX_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Additional Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__ADDITIONAL_PROPERTIES = FacetsPackage.OBJECT_TYPE_FACET__ADDITIONAL_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Discriminator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__DISCRIMINATOR = FacetsPackage.OBJECT_TYPE_FACET__DISCRIMINATOR;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__DISCRIMINATOR_VALUE = FacetsPackage.OBJECT_TYPE_FACET__DISCRIMINATOR_VALUE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__TYPE = FacetsPackage.OBJECT_TYPE_FACET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__BASE_CLASS = FacetsPackage.OBJECT_TYPE_FACET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__DESCRIPTION = FacetsPackage.OBJECT_TYPE_FACET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__DISPLAY_NAME = FacetsPackage.OBJECT_TYPE_FACET_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__NAME = FacetsPackage.OBJECT_TYPE_FACET_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE__ALLOWED_TARGETS = FacetsPackage.OBJECT_TYPE_FACET_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Object Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE_FEATURE_COUNT = FacetsPackage.OBJECT_TYPE_FACET_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Object Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ANNOTATION_TYPE_OPERATION_COUNT = FacetsPackage.OBJECT_TYPE_FACET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl <em>Array Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getArrayAnnotationType()
	 * @generated
	 */
	int ARRAY_ANNOTATION_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Unique Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE__UNIQUE_ITEMS = FacetsPackage.ARRAY_TYPE_FACET__UNIQUE_ITEMS;

	/**
	 * The feature id for the '<em><b>Items</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE__ITEMS = FacetsPackage.ARRAY_TYPE_FACET__ITEMS;

	/**
	 * The feature id for the '<em><b>Min Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE__MIN_ITEMS = FacetsPackage.ARRAY_TYPE_FACET__MIN_ITEMS;

	/**
	 * The feature id for the '<em><b>Max Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE__MAX_ITEMS = FacetsPackage.ARRAY_TYPE_FACET__MAX_ITEMS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE__TYPE = FacetsPackage.ARRAY_TYPE_FACET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE__BASE_CLASS = FacetsPackage.ARRAY_TYPE_FACET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE__DESCRIPTION = FacetsPackage.ARRAY_TYPE_FACET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE__DISPLAY_NAME = FacetsPackage.ARRAY_TYPE_FACET_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE__NAME = FacetsPackage.ARRAY_TYPE_FACET_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Allowed Targets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE__ALLOWED_TARGETS = FacetsPackage.ARRAY_TYPE_FACET_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Array Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE_FEATURE_COUNT = FacetsPackage.ARRAY_TYPE_FACET_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Array Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ANNOTATION_TYPE_OPERATION_COUNT = FacetsPackage.ARRAY_TYPE_FACET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.AnyTypeImpl <em>Any Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.AnyTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getAnyType()
	 * @generated
	 */
	int ANY_TYPE = 20;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__TYPE = DATA_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__BASE_CLASS = DATA_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__ANNOTATIONS = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__DESCRIPTION = DATA_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__DISPLAY_NAME = DATA_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__NAME = DATA_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__ENUM = DATA_TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Any Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE___GET_ANNOTATION__ANYTYPE = DATA_TYPE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Any Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE_OPERATION_COUNT = DATA_TYPE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.ObjectTypeImpl <em>Object Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.ObjectTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getObjectType()
	 * @generated
	 */
	int OBJECT_TYPE = 22;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__PROPERTIES = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Min Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__MIN_PROPERTIES = ANY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Max Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__MAX_PROPERTIES = ANY_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Additional Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__ADDITIONAL_PROPERTIES = ANY_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Discriminator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__DISCRIMINATOR = ANY_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE__DISCRIMINATOR_VALUE = ANY_TYPE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Object Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The operation id for the '<em>Get Property</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE___GET_PROPERTY__STRING = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Object Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.ArrayTypeImpl <em>Array Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.ArrayTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getArrayType()
	 * @generated
	 */
	int ARRAY_TYPE = 23;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The feature id for the '<em><b>Unique Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__UNIQUE_ITEMS = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Items</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__ITEMS = ANY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Min Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__MIN_ITEMS = ANY_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Max Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__MAX_ITEMS = ANY_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Array Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Array Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.StringTypeImpl <em>String Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.StringTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getStringType()
	 * @generated
	 */
	int STRING_TYPE = 24;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__PATTERN = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Min Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__MIN_LENGTH = ANY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__MAX_LENGTH = ANY_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>String Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>String Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.NumberTypeImpl <em>Number Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.NumberTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getNumberType()
	 * @generated
	 */
	int NUMBER_TYPE = 25;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The feature id for the '<em><b>Minimum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__MINIMUM = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Maximum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__MAXIMUM = ANY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__FORMAT = ANY_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Multiple Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE__MULTIPLE_OF = ANY_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Number Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Number Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.IntegerTypeImpl <em>Integer Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.IntegerTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getIntegerType()
	 * @generated
	 */
	int INTEGER_TYPE = 26;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__TYPE = NUMBER_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__BASE_CLASS = NUMBER_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__ANNOTATIONS = NUMBER_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__DESCRIPTION = NUMBER_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__DISPLAY_NAME = NUMBER_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__NAME = NUMBER_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__ENUM = NUMBER_TYPE__ENUM;

	/**
	 * The feature id for the '<em><b>Minimum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__MINIMUM = NUMBER_TYPE__MINIMUM;

	/**
	 * The feature id for the '<em><b>Maximum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__MAXIMUM = NUMBER_TYPE__MAXIMUM;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__FORMAT = NUMBER_TYPE__FORMAT;

	/**
	 * The feature id for the '<em><b>Multiple Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__MULTIPLE_OF = NUMBER_TYPE__MULTIPLE_OF;

	/**
	 * The number of structural features of the '<em>Integer Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE_FEATURE_COUNT = NUMBER_TYPE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE___GET_ANNOTATION__ANYTYPE = NUMBER_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Integer Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE_OPERATION_COUNT = NUMBER_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.BooleanTypeImpl <em>Boolean Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.BooleanTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getBooleanType()
	 * @generated
	 */
	int BOOLEAN_TYPE = 27;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The number of structural features of the '<em>Boolean Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Boolean Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.DateOnlyTypeImpl <em>Date Only Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.DateOnlyTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateOnlyType()
	 * @generated
	 */
	int DATE_ONLY_TYPE = 28;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The number of structural features of the '<em>Date Only Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Date Only Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_ONLY_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.TimeOnlyTypeImpl <em>Time Only Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.TimeOnlyTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getTimeOnlyType()
	 * @generated
	 */
	int TIME_ONLY_TYPE = 29;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The number of structural features of the '<em>Time Only Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Time Only Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_ONLY_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.DateTimeOnlyTypeImpl <em>Date Time Only Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.DateTimeOnlyTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateTimeOnlyType()
	 * @generated
	 */
	int DATE_TIME_ONLY_TYPE = 30;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The number of structural features of the '<em>Date Time Only Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Date Time Only Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_ONLY_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.DateTimeTypeImpl <em>Date Time Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.DateTimeTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateTimeType()
	 * @generated
	 */
	int DATE_TIME_TYPE = 31;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE__FORMAT = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Date Time Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Date Time Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.FileTypeImpl <em>File Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.FileTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getFileType()
	 * @generated
	 */
	int FILE_TYPE = 32;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The feature id for the '<em><b>File Types</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE__FILE_TYPES = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Min Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE__MIN_LENGTH = ANY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE__MAX_LENGTH = ANY_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>File Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>File Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.NilTypeImpl <em>Nil Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.NilTypeImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getNilType()
	 * @generated
	 */
	int NIL_TYPE = 33;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_TYPE__TYPE = ANY_TYPE__TYPE;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_TYPE__BASE_CLASS = ANY_TYPE__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_TYPE__ANNOTATIONS = ANY_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_TYPE__DESCRIPTION = ANY_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_TYPE__DISPLAY_NAME = ANY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_TYPE__ENUM = ANY_TYPE__ENUM;

	/**
	 * The number of structural features of the '<em>Nil Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_TYPE___GET_ANNOTATION__ANYTYPE = ANY_TYPE___GET_ANNOTATION__ANYTYPE;

	/**
	 * The number of operations of the '<em>Nil Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NIL_TYPE_OPERATION_COUNT = ANY_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.InstanceImpl <em>Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.InstanceImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getInstance()
	 * @generated
	 */
	int INSTANCE = 34;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.StringInstanceImpl <em>String Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.StringInstanceImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getStringInstance()
	 * @generated
	 */
	int STRING_INSTANCE = 35;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_INSTANCE__TYPE = INSTANCE__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_INSTANCE__VALUE = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_INSTANCE_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>String Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_INSTANCE_OPERATION_COUNT = INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.BooleanInstanceImpl <em>Boolean Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.BooleanInstanceImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getBooleanInstance()
	 * @generated
	 */
	int BOOLEAN_INSTANCE = 36;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_INSTANCE__TYPE = INSTANCE__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_INSTANCE__VALUE = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_INSTANCE_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Boolean Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_INSTANCE_OPERATION_COUNT = INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.NumberInstanceImpl <em>Number Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.NumberInstanceImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getNumberInstance()
	 * @generated
	 */
	int NUMBER_INSTANCE = 37;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_INSTANCE__TYPE = INSTANCE__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_INSTANCE__VALUE = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Number Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_INSTANCE_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Number Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_INSTANCE_OPERATION_COUNT = INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.IntegerInstanceImpl <em>Integer Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.IntegerInstanceImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getIntegerInstance()
	 * @generated
	 */
	int INTEGER_INSTANCE = 38;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_INSTANCE__TYPE = INSTANCE__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_INSTANCE__VALUE = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_INSTANCE_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Integer Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_INSTANCE_OPERATION_COUNT = INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.impl.ObjectInstanceImpl <em>Object Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.impl.ObjectInstanceImpl
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getObjectInstance()
	 * @generated
	 */
	int OBJECT_INSTANCE = 39;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_INSTANCE__TYPE = INSTANCE__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_INSTANCE__VALUE = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Object Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_INSTANCE_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Propertyy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_INSTANCE___GET_PROPERTYY__ANYTYPE = INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Object Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_INSTANCE_OPERATION_COUNT = INSTANCE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.types.AnnotationTarget <em>Annotation Target</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.types.AnnotationTarget
	 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getAnnotationTarget()
	 * @generated
	 */
	int ANNOTATION_TARGET = 40;


	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.Api <em>Api</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Api</em>'.
	 * @see io.vrap.rmf.raml.model.types.Api
	 * @generated
	 */
	EClass getApi();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.Api#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see io.vrap.rmf.raml.model.types.Api#getTitle()
	 * @see #getApi()
	 * @generated
	 */
	EAttribute getApi_Title();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.Api#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see io.vrap.rmf.raml.model.types.Api#getDescription()
	 * @see #getApi()
	 * @generated
	 */
	EAttribute getApi_Description();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.Api#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see io.vrap.rmf.raml.model.types.Api#getVersion()
	 * @see #getApi()
	 * @generated
	 */
	EAttribute getApi_Version();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.Api#getBaseUri <em>Base Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Uri</em>'.
	 * @see io.vrap.rmf.raml.model.types.Api#getBaseUri()
	 * @see #getApi()
	 * @generated
	 */
	EAttribute getApi_BaseUri();

	/**
	 * Returns the meta object for the attribute list '{@link io.vrap.rmf.raml.model.types.Api#getProtocols <em>Protocols</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Protocols</em>'.
	 * @see io.vrap.rmf.raml.model.types.Api#getProtocols()
	 * @see #getApi()
	 * @generated
	 */
	EAttribute getApi_Protocols();

	/**
	 * Returns the meta object for the attribute list '{@link io.vrap.rmf.raml.model.types.Api#getMediaType <em>Media Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Media Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.Api#getMediaType()
	 * @see #getApi()
	 * @generated
	 */
	EAttribute getApi_MediaType();

	/**
	 * Returns the meta object for the attribute list '{@link io.vrap.rmf.raml.model.types.Api#getSecuredBy <em>Secured By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Secured By</em>'.
	 * @see io.vrap.rmf.raml.model.types.Api#getSecuredBy()
	 * @see #getApi()
	 * @generated
	 */
	EAttribute getApi_SecuredBy();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.TypeContainer <em>Type Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Container</em>'.
	 * @see io.vrap.rmf.raml.model.types.TypeContainer
	 * @generated
	 */
	EClass getTypeContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link io.vrap.rmf.raml.model.types.TypeContainer#getUses <em>Uses</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Uses</em>'.
	 * @see io.vrap.rmf.raml.model.types.TypeContainer#getUses()
	 * @see #getTypeContainer()
	 * @generated
	 */
	EReference getTypeContainer_Uses();

	/**
	 * Returns the meta object for the containment reference list '{@link io.vrap.rmf.raml.model.types.TypeContainer#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see io.vrap.rmf.raml.model.types.TypeContainer#getTypes()
	 * @see #getTypeContainer()
	 * @generated
	 */
	EReference getTypeContainer_Types();

	/**
	 * Returns the meta object for the containment reference list '{@link io.vrap.rmf.raml.model.types.TypeContainer#getAnnotationTypes <em>Annotation Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotation Types</em>'.
	 * @see io.vrap.rmf.raml.model.types.TypeContainer#getAnnotationTypes()
	 * @see #getTypeContainer()
	 * @generated
	 */
	EReference getTypeContainer_AnnotationTypes();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.Library <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library</em>'.
	 * @see io.vrap.rmf.raml.model.types.Library
	 * @generated
	 */
	EClass getLibrary();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.Library#getUsage <em>Usage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Usage</em>'.
	 * @see io.vrap.rmf.raml.model.types.Library#getUsage()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Usage();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.LibraryUse <em>Library Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library Use</em>'.
	 * @see io.vrap.rmf.raml.model.types.LibraryUse
	 * @generated
	 */
	EClass getLibraryUse();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.LibraryUse#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see io.vrap.rmf.raml.model.types.LibraryUse#getName()
	 * @see #getLibraryUse()
	 * @generated
	 */
	EAttribute getLibraryUse_Name();

	/**
	 * Returns the meta object for the reference '{@link io.vrap.rmf.raml.model.types.LibraryUse#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Library</em>'.
	 * @see io.vrap.rmf.raml.model.types.LibraryUse#getLibrary()
	 * @see #getLibraryUse()
	 * @generated
	 */
	EReference getLibraryUse_Library();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the reference '{@link io.vrap.rmf.raml.model.types.Type#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.Type#getType()
	 * @see #getType()
	 * @generated
	 */
	EReference getType_Type();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.Type#getBaseClass <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Class</em>'.
	 * @see io.vrap.rmf.raml.model.types.Type#getBaseClass()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_BaseClass();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.DataType
	 * @generated
	 */
	EClass getDataType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.AnnotationType <em>Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.AnnotationType
	 * @generated
	 */
	EClass getAnnotationType();

	/**
	 * Returns the meta object for the attribute list '{@link io.vrap.rmf.raml.model.types.AnnotationType#getAllowedTargets <em>Allowed Targets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Allowed Targets</em>'.
	 * @see io.vrap.rmf.raml.model.types.AnnotationType#getAllowedTargets()
	 * @see #getAnnotationType()
	 * @generated
	 */
	EAttribute getAnnotationType_AllowedTargets();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.AnyAnnotationType <em>Any Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.AnyAnnotationType
	 * @generated
	 */
	EClass getAnyAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.TimeOnlyAnnotationType <em>Time Only Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Only Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.TimeOnlyAnnotationType
	 * @generated
	 */
	EClass getTimeOnlyAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.DateTimeAnnotationType <em>Date Time Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Time Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.DateTimeAnnotationType
	 * @generated
	 */
	EClass getDateTimeAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.DateTimeOnlyAnnotationType <em>Date Time Only Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Time Only Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.DateTimeOnlyAnnotationType
	 * @generated
	 */
	EClass getDateTimeOnlyAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.DateOnlyAnnotationType <em>Date Only Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Only Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.DateOnlyAnnotationType
	 * @generated
	 */
	EClass getDateOnlyAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.NumberAnnotationType <em>Number Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Number Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.NumberAnnotationType
	 * @generated
	 */
	EClass getNumberAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.IntegerAnnotationType <em>Integer Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.IntegerAnnotationType
	 * @generated
	 */
	EClass getIntegerAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.BooleanAnnotationType <em>Boolean Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.BooleanAnnotationType
	 * @generated
	 */
	EClass getBooleanAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.StringAnnotationType <em>String Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.StringAnnotationType
	 * @generated
	 */
	EClass getStringAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.NilAnnotationType <em>Nil Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nil Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.NilAnnotationType
	 * @generated
	 */
	EClass getNilAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.FileAnnotationType <em>File Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.FileAnnotationType
	 * @generated
	 */
	EClass getFileAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.ObjectAnnotationType <em>Object Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.ObjectAnnotationType
	 * @generated
	 */
	EClass getObjectAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.ArrayAnnotationType <em>Array Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Annotation Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.ArrayAnnotationType
	 * @generated
	 */
	EClass getArrayAnnotationType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.AnyType <em>Any Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.AnyType
	 * @generated
	 */
	EClass getAnyType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.Annotatable <em>Annotatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotatable</em>'.
	 * @see io.vrap.rmf.raml.model.types.Annotatable
	 * @generated
	 */
	EClass getAnnotatable();

	/**
	 * Returns the meta object for the containment reference list '{@link io.vrap.rmf.raml.model.types.Annotatable#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see io.vrap.rmf.raml.model.types.Annotatable#getAnnotations()
	 * @see #getAnnotatable()
	 * @generated
	 */
	EReference getAnnotatable_Annotations();

	/**
	 * Returns the meta object for the '{@link io.vrap.rmf.raml.model.types.Annotatable#getAnnotation(io.vrap.rmf.raml.model.types.AnyType) <em>Get Annotation</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Annotation</em>' operation.
	 * @see io.vrap.rmf.raml.model.types.Annotatable#getAnnotation(io.vrap.rmf.raml.model.types.AnyType)
	 * @generated
	 */
	EOperation getAnnotatable__GetAnnotation__AnyType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.ObjectType <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.ObjectType
	 * @generated
	 */
	EClass getObjectType();

	/**
	 * Returns the meta object for the '{@link io.vrap.rmf.raml.model.types.ObjectType#getProperty(java.lang.String) <em>Get Property</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Property</em>' operation.
	 * @see io.vrap.rmf.raml.model.types.ObjectType#getProperty(java.lang.String)
	 * @generated
	 */
	EOperation getObjectType__GetProperty__String();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.ArrayType <em>Array Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.ArrayType
	 * @generated
	 */
	EClass getArrayType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.StringType <em>String Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.StringType
	 * @generated
	 */
	EClass getStringType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.NumberType <em>Number Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Number Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.NumberType
	 * @generated
	 */
	EClass getNumberType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.IntegerType <em>Integer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.IntegerType
	 * @generated
	 */
	EClass getIntegerType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.BooleanType <em>Boolean Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.BooleanType
	 * @generated
	 */
	EClass getBooleanType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.DateOnlyType <em>Date Only Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Only Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.DateOnlyType
	 * @generated
	 */
	EClass getDateOnlyType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.TimeOnlyType <em>Time Only Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Only Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.TimeOnlyType
	 * @generated
	 */
	EClass getTimeOnlyType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.DateTimeOnlyType <em>Date Time Only Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Time Only Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.DateTimeOnlyType
	 * @generated
	 */
	EClass getDateTimeOnlyType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.DateTimeType <em>Date Time Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Time Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.DateTimeType
	 * @generated
	 */
	EClass getDateTimeType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.FileType <em>File Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.FileType
	 * @generated
	 */
	EClass getFileType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.NilType <em>Nil Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nil Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.NilType
	 * @generated
	 */
	EClass getNilType();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.Instance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance</em>'.
	 * @see io.vrap.rmf.raml.model.types.Instance
	 * @generated
	 */
	EClass getInstance();

	/**
	 * Returns the meta object for the reference '{@link io.vrap.rmf.raml.model.types.Instance#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see io.vrap.rmf.raml.model.types.Instance#getType()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Type();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.StringInstance <em>String Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Instance</em>'.
	 * @see io.vrap.rmf.raml.model.types.StringInstance
	 * @generated
	 */
	EClass getStringInstance();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.StringInstance#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see io.vrap.rmf.raml.model.types.StringInstance#getValue()
	 * @see #getStringInstance()
	 * @generated
	 */
	EAttribute getStringInstance_Value();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.BooleanInstance <em>Boolean Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Instance</em>'.
	 * @see io.vrap.rmf.raml.model.types.BooleanInstance
	 * @generated
	 */
	EClass getBooleanInstance();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.BooleanInstance#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see io.vrap.rmf.raml.model.types.BooleanInstance#getValue()
	 * @see #getBooleanInstance()
	 * @generated
	 */
	EAttribute getBooleanInstance_Value();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.NumberInstance <em>Number Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Number Instance</em>'.
	 * @see io.vrap.rmf.raml.model.types.NumberInstance
	 * @generated
	 */
	EClass getNumberInstance();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.NumberInstance#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see io.vrap.rmf.raml.model.types.NumberInstance#getValue()
	 * @see #getNumberInstance()
	 * @generated
	 */
	EAttribute getNumberInstance_Value();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.IntegerInstance <em>Integer Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Instance</em>'.
	 * @see io.vrap.rmf.raml.model.types.IntegerInstance
	 * @generated
	 */
	EClass getIntegerInstance();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.types.IntegerInstance#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see io.vrap.rmf.raml.model.types.IntegerInstance#getValue()
	 * @see #getIntegerInstance()
	 * @generated
	 */
	EAttribute getIntegerInstance_Value();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.types.ObjectInstance <em>Object Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Instance</em>'.
	 * @see io.vrap.rmf.raml.model.types.ObjectInstance
	 * @generated
	 */
	EClass getObjectInstance();

	/**
	 * Returns the meta object for the containment reference list '{@link io.vrap.rmf.raml.model.types.ObjectInstance#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see io.vrap.rmf.raml.model.types.ObjectInstance#getValue()
	 * @see #getObjectInstance()
	 * @generated
	 */
	EReference getObjectInstance_Value();

	/**
	 * Returns the meta object for the '{@link io.vrap.rmf.raml.model.types.ObjectInstance#getPropertyy(io.vrap.rmf.raml.model.types.AnyType) <em>Get Propertyy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Propertyy</em>' operation.
	 * @see io.vrap.rmf.raml.model.types.ObjectInstance#getPropertyy(io.vrap.rmf.raml.model.types.AnyType)
	 * @generated
	 */
	EOperation getObjectInstance__GetPropertyy__AnyType();

	/**
	 * Returns the meta object for enum '{@link io.vrap.rmf.raml.model.types.AnnotationTarget <em>Annotation Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Annotation Target</em>'.
	 * @see io.vrap.rmf.raml.model.types.AnnotationTarget
	 * @generated
	 */
	EEnum getAnnotationTarget();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TypesFactory getTypesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.ApiImpl <em>Api</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.ApiImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getApi()
		 * @generated
		 */
		EClass API = eINSTANCE.getApi();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute API__TITLE = eINSTANCE.getApi_Title();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute API__DESCRIPTION = eINSTANCE.getApi_Description();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute API__VERSION = eINSTANCE.getApi_Version();

		/**
		 * The meta object literal for the '<em><b>Base Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute API__BASE_URI = eINSTANCE.getApi_BaseUri();

		/**
		 * The meta object literal for the '<em><b>Protocols</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute API__PROTOCOLS = eINSTANCE.getApi_Protocols();

		/**
		 * The meta object literal for the '<em><b>Media Type</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute API__MEDIA_TYPE = eINSTANCE.getApi_MediaType();

		/**
		 * The meta object literal for the '<em><b>Secured By</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute API__SECURED_BY = eINSTANCE.getApi_SecuredBy();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.TypeContainer <em>Type Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.TypeContainer
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getTypeContainer()
		 * @generated
		 */
		EClass TYPE_CONTAINER = eINSTANCE.getTypeContainer();

		/**
		 * The meta object literal for the '<em><b>Uses</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_CONTAINER__USES = eINSTANCE.getTypeContainer_Uses();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_CONTAINER__TYPES = eINSTANCE.getTypeContainer_Types();

		/**
		 * The meta object literal for the '<em><b>Annotation Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_CONTAINER__ANNOTATION_TYPES = eINSTANCE.getTypeContainer_AnnotationTypes();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.LibraryImpl <em>Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.LibraryImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getLibrary()
		 * @generated
		 */
		EClass LIBRARY = eINSTANCE.getLibrary();

		/**
		 * The meta object literal for the '<em><b>Usage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__USAGE = eINSTANCE.getLibrary_Usage();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.LibraryUseImpl <em>Library Use</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.LibraryUseImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getLibraryUse()
		 * @generated
		 */
		EClass LIBRARY_USE = eINSTANCE.getLibraryUse();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_USE__NAME = eINSTANCE.getLibraryUse_Name();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_USE__LIBRARY = eINSTANCE.getLibraryUse_Library();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.Type <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.Type
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE__TYPE = eINSTANCE.getType_Type();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__BASE_CLASS = eINSTANCE.getType_BaseClass();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.DataType <em>Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.DataType
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDataType()
		 * @generated
		 */
		EClass DATA_TYPE = eINSTANCE.getDataType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.AnnotationType <em>Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.AnnotationType
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getAnnotationType()
		 * @generated
		 */
		EClass ANNOTATION_TYPE = eINSTANCE.getAnnotationType();

		/**
		 * The meta object literal for the '<em><b>Allowed Targets</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION_TYPE__ALLOWED_TARGETS = eINSTANCE.getAnnotationType_AllowedTargets();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.AnyAnnotationTypeImpl <em>Any Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.AnyAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getAnyAnnotationType()
		 * @generated
		 */
		EClass ANY_ANNOTATION_TYPE = eINSTANCE.getAnyAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.TimeOnlyAnnotationTypeImpl <em>Time Only Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.TimeOnlyAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getTimeOnlyAnnotationType()
		 * @generated
		 */
		EClass TIME_ONLY_ANNOTATION_TYPE = eINSTANCE.getTimeOnlyAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.DateTimeAnnotationTypeImpl <em>Date Time Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.DateTimeAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateTimeAnnotationType()
		 * @generated
		 */
		EClass DATE_TIME_ANNOTATION_TYPE = eINSTANCE.getDateTimeAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.DateTimeOnlyAnnotationTypeImpl <em>Date Time Only Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.DateTimeOnlyAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateTimeOnlyAnnotationType()
		 * @generated
		 */
		EClass DATE_TIME_ONLY_ANNOTATION_TYPE = eINSTANCE.getDateTimeOnlyAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.DateOnlyAnnotationTypeImpl <em>Date Only Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.DateOnlyAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateOnlyAnnotationType()
		 * @generated
		 */
		EClass DATE_ONLY_ANNOTATION_TYPE = eINSTANCE.getDateOnlyAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl <em>Number Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.NumberAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getNumberAnnotationType()
		 * @generated
		 */
		EClass NUMBER_ANNOTATION_TYPE = eINSTANCE.getNumberAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.IntegerAnnotationTypeImpl <em>Integer Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.IntegerAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getIntegerAnnotationType()
		 * @generated
		 */
		EClass INTEGER_ANNOTATION_TYPE = eINSTANCE.getIntegerAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.BooleanAnnotationTypeImpl <em>Boolean Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.BooleanAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getBooleanAnnotationType()
		 * @generated
		 */
		EClass BOOLEAN_ANNOTATION_TYPE = eINSTANCE.getBooleanAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.StringAnnotationTypeImpl <em>String Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.StringAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getStringAnnotationType()
		 * @generated
		 */
		EClass STRING_ANNOTATION_TYPE = eINSTANCE.getStringAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.NilAnnotationTypeImpl <em>Nil Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.NilAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getNilAnnotationType()
		 * @generated
		 */
		EClass NIL_ANNOTATION_TYPE = eINSTANCE.getNilAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.FileAnnotationTypeImpl <em>File Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.FileAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getFileAnnotationType()
		 * @generated
		 */
		EClass FILE_ANNOTATION_TYPE = eINSTANCE.getFileAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl <em>Object Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.ObjectAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getObjectAnnotationType()
		 * @generated
		 */
		EClass OBJECT_ANNOTATION_TYPE = eINSTANCE.getObjectAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl <em>Array Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.ArrayAnnotationTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getArrayAnnotationType()
		 * @generated
		 */
		EClass ARRAY_ANNOTATION_TYPE = eINSTANCE.getArrayAnnotationType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.AnyTypeImpl <em>Any Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.AnyTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getAnyType()
		 * @generated
		 */
		EClass ANY_TYPE = eINSTANCE.getAnyType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.Annotatable <em>Annotatable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.Annotatable
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getAnnotatable()
		 * @generated
		 */
		EClass ANNOTATABLE = eINSTANCE.getAnnotatable();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATABLE__ANNOTATIONS = eINSTANCE.getAnnotatable_Annotations();

		/**
		 * The meta object literal for the '<em><b>Get Annotation</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTATABLE___GET_ANNOTATION__ANYTYPE = eINSTANCE.getAnnotatable__GetAnnotation__AnyType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.ObjectTypeImpl <em>Object Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.ObjectTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getObjectType()
		 * @generated
		 */
		EClass OBJECT_TYPE = eINSTANCE.getObjectType();

		/**
		 * The meta object literal for the '<em><b>Get Property</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation OBJECT_TYPE___GET_PROPERTY__STRING = eINSTANCE.getObjectType__GetProperty__String();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.ArrayTypeImpl <em>Array Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.ArrayTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getArrayType()
		 * @generated
		 */
		EClass ARRAY_TYPE = eINSTANCE.getArrayType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.StringTypeImpl <em>String Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.StringTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getStringType()
		 * @generated
		 */
		EClass STRING_TYPE = eINSTANCE.getStringType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.NumberTypeImpl <em>Number Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.NumberTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getNumberType()
		 * @generated
		 */
		EClass NUMBER_TYPE = eINSTANCE.getNumberType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.IntegerTypeImpl <em>Integer Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.IntegerTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getIntegerType()
		 * @generated
		 */
		EClass INTEGER_TYPE = eINSTANCE.getIntegerType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.BooleanTypeImpl <em>Boolean Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.BooleanTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getBooleanType()
		 * @generated
		 */
		EClass BOOLEAN_TYPE = eINSTANCE.getBooleanType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.DateOnlyTypeImpl <em>Date Only Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.DateOnlyTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateOnlyType()
		 * @generated
		 */
		EClass DATE_ONLY_TYPE = eINSTANCE.getDateOnlyType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.TimeOnlyTypeImpl <em>Time Only Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.TimeOnlyTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getTimeOnlyType()
		 * @generated
		 */
		EClass TIME_ONLY_TYPE = eINSTANCE.getTimeOnlyType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.DateTimeOnlyTypeImpl <em>Date Time Only Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.DateTimeOnlyTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateTimeOnlyType()
		 * @generated
		 */
		EClass DATE_TIME_ONLY_TYPE = eINSTANCE.getDateTimeOnlyType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.DateTimeTypeImpl <em>Date Time Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.DateTimeTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getDateTimeType()
		 * @generated
		 */
		EClass DATE_TIME_TYPE = eINSTANCE.getDateTimeType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.FileTypeImpl <em>File Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.FileTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getFileType()
		 * @generated
		 */
		EClass FILE_TYPE = eINSTANCE.getFileType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.NilTypeImpl <em>Nil Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.NilTypeImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getNilType()
		 * @generated
		 */
		EClass NIL_TYPE = eINSTANCE.getNilType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.InstanceImpl <em>Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.InstanceImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getInstance()
		 * @generated
		 */
		EClass INSTANCE = eINSTANCE.getInstance();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE__TYPE = eINSTANCE.getInstance_Type();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.StringInstanceImpl <em>String Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.StringInstanceImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getStringInstance()
		 * @generated
		 */
		EClass STRING_INSTANCE = eINSTANCE.getStringInstance();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_INSTANCE__VALUE = eINSTANCE.getStringInstance_Value();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.BooleanInstanceImpl <em>Boolean Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.BooleanInstanceImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getBooleanInstance()
		 * @generated
		 */
		EClass BOOLEAN_INSTANCE = eINSTANCE.getBooleanInstance();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_INSTANCE__VALUE = eINSTANCE.getBooleanInstance_Value();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.NumberInstanceImpl <em>Number Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.NumberInstanceImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getNumberInstance()
		 * @generated
		 */
		EClass NUMBER_INSTANCE = eINSTANCE.getNumberInstance();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMBER_INSTANCE__VALUE = eINSTANCE.getNumberInstance_Value();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.IntegerInstanceImpl <em>Integer Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.IntegerInstanceImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getIntegerInstance()
		 * @generated
		 */
		EClass INTEGER_INSTANCE = eINSTANCE.getIntegerInstance();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_INSTANCE__VALUE = eINSTANCE.getIntegerInstance_Value();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.impl.ObjectInstanceImpl <em>Object Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.impl.ObjectInstanceImpl
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getObjectInstance()
		 * @generated
		 */
		EClass OBJECT_INSTANCE = eINSTANCE.getObjectInstance();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_INSTANCE__VALUE = eINSTANCE.getObjectInstance_Value();

		/**
		 * The meta object literal for the '<em><b>Get Propertyy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation OBJECT_INSTANCE___GET_PROPERTYY__ANYTYPE = eINSTANCE.getObjectInstance__GetPropertyy__AnyType();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.types.AnnotationTarget <em>Annotation Target</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.types.AnnotationTarget
		 * @see io.vrap.rmf.raml.model.types.impl.TypesPackageImpl#getAnnotationTarget()
		 * @generated
		 */
		EEnum ANNOTATION_TARGET = eINSTANCE.getAnnotationTarget();

	}

} //TypesPackage
