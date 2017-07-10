/**
 */
package io.vrap.rmf.raml.model.facets;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see io.vrap.rmf.raml.model.facets.FacetsFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelDirectory='raml-model/src/main/xcore-gen' complianceLevel='8.0' basePackage='io.vrap.rmf.raml.model'"
 * @generated
 */
public interface FacetsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "facets";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.vrap.io/raml/facets";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "facets";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FacetsPackage eINSTANCE = io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl.init();

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.facets.EnumFacet <em>Enum Facet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.facets.EnumFacet
	 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getEnumFacet()
	 * @generated
	 */
	int ENUM_FACET = 0;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FACET__ENUM = 0;

	/**
	 * The number of structural features of the '<em>Enum Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FACET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Enum Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_FACET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet <em>Object Type Facet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.facets.ObjectTypeFacet
	 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getObjectTypeFacet()
	 * @generated
	 */
	int OBJECT_TYPE_FACET = 1;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE_FACET__PROPERTIES = 0;

	/**
	 * The feature id for the '<em><b>Min Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE_FACET__MIN_PROPERTIES = 1;

	/**
	 * The feature id for the '<em><b>Max Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE_FACET__MAX_PROPERTIES = 2;

	/**
	 * The feature id for the '<em><b>Additional Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE_FACET__ADDITIONAL_PROPERTIES = 3;

	/**
	 * The feature id for the '<em><b>Discriminator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE_FACET__DISCRIMINATOR = 4;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE_FACET__DISCRIMINATOR_VALUE = 5;

	/**
	 * The number of structural features of the '<em>Object Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE_FACET_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Object Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TYPE_FACET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.facets.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.facets.impl.PropertyImpl
	 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__REQUIRED = 2;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet <em>Array Type Facet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.facets.ArrayTypeFacet
	 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getArrayTypeFacet()
	 * @generated
	 */
	int ARRAY_TYPE_FACET = 3;

	/**
	 * The feature id for the '<em><b>Unique Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE_FACET__UNIQUE_ITEMS = 0;

	/**
	 * The feature id for the '<em><b>Items</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE_FACET__ITEMS = 1;

	/**
	 * The feature id for the '<em><b>Min Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE_FACET__MIN_ITEMS = 2;

	/**
	 * The feature id for the '<em><b>Max Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE_FACET__MAX_ITEMS = 3;

	/**
	 * The number of structural features of the '<em>Array Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE_FACET_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Array Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE_FACET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.facets.StringTypeFacet <em>String Type Facet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.facets.StringTypeFacet
	 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getStringTypeFacet()
	 * @generated
	 */
	int STRING_TYPE_FACET = 4;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE_FACET__PATTERN = 0;

	/**
	 * The feature id for the '<em><b>Min Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE_FACET__MIN_LENGTH = 1;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE_FACET__MAX_LENGTH = 2;

	/**
	 * The number of structural features of the '<em>String Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE_FACET_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>String Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE_FACET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet <em>Number Type Facet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.facets.NumberTypeFacet
	 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getNumberTypeFacet()
	 * @generated
	 */
	int NUMBER_TYPE_FACET = 5;

	/**
	 * The feature id for the '<em><b>Minimum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE_FACET__MINIMUM = 0;

	/**
	 * The feature id for the '<em><b>Maximum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE_FACET__MAXIMUM = 1;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE_FACET__FORMAT = 2;

	/**
	 * The feature id for the '<em><b>Multiple Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE_FACET__MULTIPLE_OF = 3;

	/**
	 * The number of structural features of the '<em>Number Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE_FACET_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Number Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMBER_TYPE_FACET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.facets.DateTimeTypeFacet <em>Date Time Type Facet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.facets.DateTimeTypeFacet
	 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getDateTimeTypeFacet()
	 * @generated
	 */
	int DATE_TIME_TYPE_FACET = 6;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE_FACET__FORMAT = 0;

	/**
	 * The number of structural features of the '<em>Date Time Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE_FACET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Date Time Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_TYPE_FACET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.facets.FileTypeFacet <em>File Type Facet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.facets.FileTypeFacet
	 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getFileTypeFacet()
	 * @generated
	 */
	int FILE_TYPE_FACET = 7;

	/**
	 * The feature id for the '<em><b>File Types</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE_FACET__FILE_TYPES = 0;

	/**
	 * The feature id for the '<em><b>Min Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE_FACET__MIN_LENGTH = 1;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE_FACET__MAX_LENGTH = 2;

	/**
	 * The number of structural features of the '<em>File Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE_FACET_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>File Type Facet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_TYPE_FACET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.facets.NumberFormat <em>Number Format</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.facets.NumberFormat
	 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getNumberFormat()
	 * @generated
	 */
	int NUMBER_FORMAT = 8;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.facets.DateTimeFormat <em>Date Time Format</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.facets.DateTimeFormat
	 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getDateTimeFormat()
	 * @generated
	 */
	int DATE_TIME_FORMAT = 9;


	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.facets.EnumFacet <em>Enum Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Facet</em>'.
	 * @see io.vrap.rmf.raml.model.facets.EnumFacet
	 * @generated
	 */
	EClass getEnumFacet();

	/**
	 * Returns the meta object for the attribute list '{@link io.vrap.rmf.raml.model.facets.EnumFacet#getEnum <em>Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Enum</em>'.
	 * @see io.vrap.rmf.raml.model.facets.EnumFacet#getEnum()
	 * @see #getEnumFacet()
	 * @generated
	 */
	EAttribute getEnumFacet_Enum();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet <em>Object Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Type Facet</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ObjectTypeFacet
	 * @generated
	 */
	EClass getObjectTypeFacet();

	/**
	 * Returns the meta object for the containment reference list '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getProperties()
	 * @see #getObjectTypeFacet()
	 * @generated
	 */
	EReference getObjectTypeFacet_Properties();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getMinProperties <em>Min Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Properties</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getMinProperties()
	 * @see #getObjectTypeFacet()
	 * @generated
	 */
	EAttribute getObjectTypeFacet_MinProperties();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getMaxProperties <em>Max Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Properties</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getMaxProperties()
	 * @see #getObjectTypeFacet()
	 * @generated
	 */
	EAttribute getObjectTypeFacet_MaxProperties();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getAdditionalProperties <em>Additional Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Additional Properties</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getAdditionalProperties()
	 * @see #getObjectTypeFacet()
	 * @generated
	 */
	EAttribute getObjectTypeFacet_AdditionalProperties();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getDiscriminator <em>Discriminator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getDiscriminator()
	 * @see #getObjectTypeFacet()
	 * @generated
	 */
	EAttribute getObjectTypeFacet_Discriminator();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getDiscriminatorValue <em>Discriminator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Value</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ObjectTypeFacet#getDiscriminatorValue()
	 * @see #getObjectTypeFacet()
	 * @generated
	 */
	EAttribute getObjectTypeFacet_DiscriminatorValue();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.facets.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see io.vrap.rmf.raml.model.facets.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.Property#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see io.vrap.rmf.raml.model.facets.Property#getName()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Name();

	/**
	 * Returns the meta object for the reference '{@link io.vrap.rmf.raml.model.facets.Property#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see io.vrap.rmf.raml.model.facets.Property#getType()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Type();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.Property#getRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Required</em>'.
	 * @see io.vrap.rmf.raml.model.facets.Property#getRequired()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Required();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet <em>Array Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Type Facet</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ArrayTypeFacet
	 * @generated
	 */
	EClass getArrayTypeFacet();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getUniqueItems <em>Unique Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Items</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getUniqueItems()
	 * @see #getArrayTypeFacet()
	 * @generated
	 */
	EAttribute getArrayTypeFacet_UniqueItems();

	/**
	 * Returns the meta object for the reference '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Items</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getItems()
	 * @see #getArrayTypeFacet()
	 * @generated
	 */
	EReference getArrayTypeFacet_Items();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getMinItems <em>Min Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Items</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getMinItems()
	 * @see #getArrayTypeFacet()
	 * @generated
	 */
	EAttribute getArrayTypeFacet_MinItems();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getMaxItems <em>Max Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Items</em>'.
	 * @see io.vrap.rmf.raml.model.facets.ArrayTypeFacet#getMaxItems()
	 * @see #getArrayTypeFacet()
	 * @generated
	 */
	EAttribute getArrayTypeFacet_MaxItems();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.facets.StringTypeFacet <em>String Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Type Facet</em>'.
	 * @see io.vrap.rmf.raml.model.facets.StringTypeFacet
	 * @generated
	 */
	EClass getStringTypeFacet();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.StringTypeFacet#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pattern</em>'.
	 * @see io.vrap.rmf.raml.model.facets.StringTypeFacet#getPattern()
	 * @see #getStringTypeFacet()
	 * @generated
	 */
	EAttribute getStringTypeFacet_Pattern();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.StringTypeFacet#getMinLength <em>Min Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Length</em>'.
	 * @see io.vrap.rmf.raml.model.facets.StringTypeFacet#getMinLength()
	 * @see #getStringTypeFacet()
	 * @generated
	 */
	EAttribute getStringTypeFacet_MinLength();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.StringTypeFacet#getMaxLength <em>Max Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Length</em>'.
	 * @see io.vrap.rmf.raml.model.facets.StringTypeFacet#getMaxLength()
	 * @see #getStringTypeFacet()
	 * @generated
	 */
	EAttribute getStringTypeFacet_MaxLength();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet <em>Number Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Number Type Facet</em>'.
	 * @see io.vrap.rmf.raml.model.facets.NumberTypeFacet
	 * @generated
	 */
	EClass getNumberTypeFacet();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMinimum <em>Minimum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Minimum</em>'.
	 * @see io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMinimum()
	 * @see #getNumberTypeFacet()
	 * @generated
	 */
	EAttribute getNumberTypeFacet_Minimum();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMaximum <em>Maximum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Maximum</em>'.
	 * @see io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMaximum()
	 * @see #getNumberTypeFacet()
	 * @generated
	 */
	EAttribute getNumberTypeFacet_Maximum();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see io.vrap.rmf.raml.model.facets.NumberTypeFacet#getFormat()
	 * @see #getNumberTypeFacet()
	 * @generated
	 */
	EAttribute getNumberTypeFacet_Format();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMultipleOf <em>Multiple Of</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiple Of</em>'.
	 * @see io.vrap.rmf.raml.model.facets.NumberTypeFacet#getMultipleOf()
	 * @see #getNumberTypeFacet()
	 * @generated
	 */
	EAttribute getNumberTypeFacet_MultipleOf();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.facets.DateTimeTypeFacet <em>Date Time Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Time Type Facet</em>'.
	 * @see io.vrap.rmf.raml.model.facets.DateTimeTypeFacet
	 * @generated
	 */
	EClass getDateTimeTypeFacet();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.DateTimeTypeFacet#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see io.vrap.rmf.raml.model.facets.DateTimeTypeFacet#getFormat()
	 * @see #getDateTimeTypeFacet()
	 * @generated
	 */
	EAttribute getDateTimeTypeFacet_Format();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.facets.FileTypeFacet <em>File Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Type Facet</em>'.
	 * @see io.vrap.rmf.raml.model.facets.FileTypeFacet
	 * @generated
	 */
	EClass getFileTypeFacet();

	/**
	 * Returns the meta object for the attribute list '{@link io.vrap.rmf.raml.model.facets.FileTypeFacet#getFileTypes <em>File Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>File Types</em>'.
	 * @see io.vrap.rmf.raml.model.facets.FileTypeFacet#getFileTypes()
	 * @see #getFileTypeFacet()
	 * @generated
	 */
	EAttribute getFileTypeFacet_FileTypes();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.FileTypeFacet#getMinLength <em>Min Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Length</em>'.
	 * @see io.vrap.rmf.raml.model.facets.FileTypeFacet#getMinLength()
	 * @see #getFileTypeFacet()
	 * @generated
	 */
	EAttribute getFileTypeFacet_MinLength();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.facets.FileTypeFacet#getMaxLength <em>Max Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Length</em>'.
	 * @see io.vrap.rmf.raml.model.facets.FileTypeFacet#getMaxLength()
	 * @see #getFileTypeFacet()
	 * @generated
	 */
	EAttribute getFileTypeFacet_MaxLength();

	/**
	 * Returns the meta object for enum '{@link io.vrap.rmf.raml.model.facets.NumberFormat <em>Number Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Number Format</em>'.
	 * @see io.vrap.rmf.raml.model.facets.NumberFormat
	 * @generated
	 */
	EEnum getNumberFormat();

	/**
	 * Returns the meta object for enum '{@link io.vrap.rmf.raml.model.facets.DateTimeFormat <em>Date Time Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Date Time Format</em>'.
	 * @see io.vrap.rmf.raml.model.facets.DateTimeFormat
	 * @generated
	 */
	EEnum getDateTimeFormat();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FacetsFactory getFacetsFactory();

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
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.facets.EnumFacet <em>Enum Facet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.facets.EnumFacet
		 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getEnumFacet()
		 * @generated
		 */
		EClass ENUM_FACET = eINSTANCE.getEnumFacet();

		/**
		 * The meta object literal for the '<em><b>Enum</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_FACET__ENUM = eINSTANCE.getEnumFacet_Enum();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet <em>Object Type Facet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.facets.ObjectTypeFacet
		 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getObjectTypeFacet()
		 * @generated
		 */
		EClass OBJECT_TYPE_FACET = eINSTANCE.getObjectTypeFacet();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_TYPE_FACET__PROPERTIES = eINSTANCE.getObjectTypeFacet_Properties();

		/**
		 * The meta object literal for the '<em><b>Min Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECT_TYPE_FACET__MIN_PROPERTIES = eINSTANCE.getObjectTypeFacet_MinProperties();

		/**
		 * The meta object literal for the '<em><b>Max Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECT_TYPE_FACET__MAX_PROPERTIES = eINSTANCE.getObjectTypeFacet_MaxProperties();

		/**
		 * The meta object literal for the '<em><b>Additional Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECT_TYPE_FACET__ADDITIONAL_PROPERTIES = eINSTANCE.getObjectTypeFacet_AdditionalProperties();

		/**
		 * The meta object literal for the '<em><b>Discriminator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECT_TYPE_FACET__DISCRIMINATOR = eINSTANCE.getObjectTypeFacet_Discriminator();

		/**
		 * The meta object literal for the '<em><b>Discriminator Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECT_TYPE_FACET__DISCRIMINATOR_VALUE = eINSTANCE.getObjectTypeFacet_DiscriminatorValue();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.facets.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.facets.impl.PropertyImpl
		 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__NAME = eINSTANCE.getProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__TYPE = eINSTANCE.getProperty_Type();

		/**
		 * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__REQUIRED = eINSTANCE.getProperty_Required();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet <em>Array Type Facet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.facets.ArrayTypeFacet
		 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getArrayTypeFacet()
		 * @generated
		 */
		EClass ARRAY_TYPE_FACET = eINSTANCE.getArrayTypeFacet();

		/**
		 * The meta object literal for the '<em><b>Unique Items</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_TYPE_FACET__UNIQUE_ITEMS = eINSTANCE.getArrayTypeFacet_UniqueItems();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARRAY_TYPE_FACET__ITEMS = eINSTANCE.getArrayTypeFacet_Items();

		/**
		 * The meta object literal for the '<em><b>Min Items</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_TYPE_FACET__MIN_ITEMS = eINSTANCE.getArrayTypeFacet_MinItems();

		/**
		 * The meta object literal for the '<em><b>Max Items</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_TYPE_FACET__MAX_ITEMS = eINSTANCE.getArrayTypeFacet_MaxItems();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.facets.StringTypeFacet <em>String Type Facet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.facets.StringTypeFacet
		 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getStringTypeFacet()
		 * @generated
		 */
		EClass STRING_TYPE_FACET = eINSTANCE.getStringTypeFacet();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TYPE_FACET__PATTERN = eINSTANCE.getStringTypeFacet_Pattern();

		/**
		 * The meta object literal for the '<em><b>Min Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TYPE_FACET__MIN_LENGTH = eINSTANCE.getStringTypeFacet_MinLength();

		/**
		 * The meta object literal for the '<em><b>Max Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TYPE_FACET__MAX_LENGTH = eINSTANCE.getStringTypeFacet_MaxLength();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet <em>Number Type Facet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.facets.NumberTypeFacet
		 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getNumberTypeFacet()
		 * @generated
		 */
		EClass NUMBER_TYPE_FACET = eINSTANCE.getNumberTypeFacet();

		/**
		 * The meta object literal for the '<em><b>Minimum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMBER_TYPE_FACET__MINIMUM = eINSTANCE.getNumberTypeFacet_Minimum();

		/**
		 * The meta object literal for the '<em><b>Maximum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMBER_TYPE_FACET__MAXIMUM = eINSTANCE.getNumberTypeFacet_Maximum();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMBER_TYPE_FACET__FORMAT = eINSTANCE.getNumberTypeFacet_Format();

		/**
		 * The meta object literal for the '<em><b>Multiple Of</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMBER_TYPE_FACET__MULTIPLE_OF = eINSTANCE.getNumberTypeFacet_MultipleOf();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.facets.DateTimeTypeFacet <em>Date Time Type Facet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.facets.DateTimeTypeFacet
		 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getDateTimeTypeFacet()
		 * @generated
		 */
		EClass DATE_TIME_TYPE_FACET = eINSTANCE.getDateTimeTypeFacet();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATE_TIME_TYPE_FACET__FORMAT = eINSTANCE.getDateTimeTypeFacet_Format();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.facets.FileTypeFacet <em>File Type Facet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.facets.FileTypeFacet
		 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getFileTypeFacet()
		 * @generated
		 */
		EClass FILE_TYPE_FACET = eINSTANCE.getFileTypeFacet();

		/**
		 * The meta object literal for the '<em><b>File Types</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_TYPE_FACET__FILE_TYPES = eINSTANCE.getFileTypeFacet_FileTypes();

		/**
		 * The meta object literal for the '<em><b>Min Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_TYPE_FACET__MIN_LENGTH = eINSTANCE.getFileTypeFacet_MinLength();

		/**
		 * The meta object literal for the '<em><b>Max Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_TYPE_FACET__MAX_LENGTH = eINSTANCE.getFileTypeFacet_MaxLength();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.facets.NumberFormat <em>Number Format</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.facets.NumberFormat
		 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getNumberFormat()
		 * @generated
		 */
		EEnum NUMBER_FORMAT = eINSTANCE.getNumberFormat();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.facets.DateTimeFormat <em>Date Time Format</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.facets.DateTimeFormat
		 * @see io.vrap.rmf.raml.model.facets.impl.FacetsPackageImpl#getDateTimeFormat()
		 * @generated
		 */
		EEnum DATE_TIME_FORMAT = eINSTANCE.getDateTimeFormat();

	}

} //FacetsPackage
