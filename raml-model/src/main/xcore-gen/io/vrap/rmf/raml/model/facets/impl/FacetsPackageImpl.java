/**
 */
package io.vrap.rmf.raml.model.facets.impl;

import io.vrap.rmf.raml.model.facets.ArrayTypeFacet;
import io.vrap.rmf.raml.model.facets.DateTimeFormat;
import io.vrap.rmf.raml.model.facets.DateTimeTypeFacet;
import io.vrap.rmf.raml.model.facets.EnumFacet;
import io.vrap.rmf.raml.model.facets.FacetsFactory;
import io.vrap.rmf.raml.model.facets.FacetsPackage;
import io.vrap.rmf.raml.model.facets.FileTypeFacet;
import io.vrap.rmf.raml.model.facets.NumberFormat;
import io.vrap.rmf.raml.model.facets.NumberTypeFacet;
import io.vrap.rmf.raml.model.facets.ObjectTypeFacet;
import io.vrap.rmf.raml.model.facets.Property;
import io.vrap.rmf.raml.model.facets.StringTypeFacet;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FacetsPackageImpl extends EPackageImpl implements FacetsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumFacetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectTypeFacetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayTypeFacetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringTypeFacetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numberTypeFacetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeTypeFacetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileTypeFacetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum numberFormatEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dateTimeFormatEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see io.vrap.rmf.raml.model.facets.FacetsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private FacetsPackageImpl() {
		super(eNS_URI, FacetsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link FacetsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static FacetsPackage init() {
		if (isInited) return (FacetsPackage)EPackage.Registry.INSTANCE.getEPackage(FacetsPackage.eNS_URI);

		// Obtain or create and register package
		FacetsPackageImpl theFacetsPackage = (FacetsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof FacetsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new FacetsPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theFacetsPackage.createPackageContents();

		// Initialize created meta-data
		theFacetsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFacetsPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(FacetsPackage.eNS_URI, theFacetsPackage);
		return theFacetsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumFacet() {
		return enumFacetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumFacet_Enum() {
		return (EAttribute)enumFacetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectTypeFacet() {
		return objectTypeFacetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectTypeFacet_Properties() {
		return (EReference)objectTypeFacetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObjectTypeFacet_MinProperties() {
		return (EAttribute)objectTypeFacetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObjectTypeFacet_MaxProperties() {
		return (EAttribute)objectTypeFacetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObjectTypeFacet_AdditionalProperties() {
		return (EAttribute)objectTypeFacetEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObjectTypeFacet_Discriminator() {
		return (EAttribute)objectTypeFacetEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObjectTypeFacet_DiscriminatorValue() {
		return (EAttribute)objectTypeFacetEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Name() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_Type() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Required() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArrayTypeFacet() {
		return arrayTypeFacetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArrayTypeFacet_UniqueItems() {
		return (EAttribute)arrayTypeFacetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArrayTypeFacet_Items() {
		return (EReference)arrayTypeFacetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArrayTypeFacet_MinItems() {
		return (EAttribute)arrayTypeFacetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArrayTypeFacet_MaxItems() {
		return (EAttribute)arrayTypeFacetEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringTypeFacet() {
		return stringTypeFacetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringTypeFacet_Pattern() {
		return (EAttribute)stringTypeFacetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringTypeFacet_MinLength() {
		return (EAttribute)stringTypeFacetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringTypeFacet_MaxLength() {
		return (EAttribute)stringTypeFacetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumberTypeFacet() {
		return numberTypeFacetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNumberTypeFacet_Minimum() {
		return (EAttribute)numberTypeFacetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNumberTypeFacet_Maximum() {
		return (EAttribute)numberTypeFacetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNumberTypeFacet_Format() {
		return (EAttribute)numberTypeFacetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNumberTypeFacet_MultipleOf() {
		return (EAttribute)numberTypeFacetEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDateTimeTypeFacet() {
		return dateTimeTypeFacetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDateTimeTypeFacet_Format() {
		return (EAttribute)dateTimeTypeFacetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileTypeFacet() {
		return fileTypeFacetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileTypeFacet_FileTypes() {
		return (EAttribute)fileTypeFacetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileTypeFacet_MinLength() {
		return (EAttribute)fileTypeFacetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileTypeFacet_MaxLength() {
		return (EAttribute)fileTypeFacetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getNumberFormat() {
		return numberFormatEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDateTimeFormat() {
		return dateTimeFormatEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FacetsFactory getFacetsFactory() {
		return (FacetsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		enumFacetEClass = createEClass(ENUM_FACET);
		createEAttribute(enumFacetEClass, ENUM_FACET__ENUM);

		objectTypeFacetEClass = createEClass(OBJECT_TYPE_FACET);
		createEReference(objectTypeFacetEClass, OBJECT_TYPE_FACET__PROPERTIES);
		createEAttribute(objectTypeFacetEClass, OBJECT_TYPE_FACET__MIN_PROPERTIES);
		createEAttribute(objectTypeFacetEClass, OBJECT_TYPE_FACET__MAX_PROPERTIES);
		createEAttribute(objectTypeFacetEClass, OBJECT_TYPE_FACET__ADDITIONAL_PROPERTIES);
		createEAttribute(objectTypeFacetEClass, OBJECT_TYPE_FACET__DISCRIMINATOR);
		createEAttribute(objectTypeFacetEClass, OBJECT_TYPE_FACET__DISCRIMINATOR_VALUE);

		propertyEClass = createEClass(PROPERTY);
		createEAttribute(propertyEClass, PROPERTY__NAME);
		createEReference(propertyEClass, PROPERTY__TYPE);
		createEAttribute(propertyEClass, PROPERTY__REQUIRED);

		arrayTypeFacetEClass = createEClass(ARRAY_TYPE_FACET);
		createEAttribute(arrayTypeFacetEClass, ARRAY_TYPE_FACET__UNIQUE_ITEMS);
		createEReference(arrayTypeFacetEClass, ARRAY_TYPE_FACET__ITEMS);
		createEAttribute(arrayTypeFacetEClass, ARRAY_TYPE_FACET__MIN_ITEMS);
		createEAttribute(arrayTypeFacetEClass, ARRAY_TYPE_FACET__MAX_ITEMS);

		stringTypeFacetEClass = createEClass(STRING_TYPE_FACET);
		createEAttribute(stringTypeFacetEClass, STRING_TYPE_FACET__PATTERN);
		createEAttribute(stringTypeFacetEClass, STRING_TYPE_FACET__MIN_LENGTH);
		createEAttribute(stringTypeFacetEClass, STRING_TYPE_FACET__MAX_LENGTH);

		numberTypeFacetEClass = createEClass(NUMBER_TYPE_FACET);
		createEAttribute(numberTypeFacetEClass, NUMBER_TYPE_FACET__MINIMUM);
		createEAttribute(numberTypeFacetEClass, NUMBER_TYPE_FACET__MAXIMUM);
		createEAttribute(numberTypeFacetEClass, NUMBER_TYPE_FACET__FORMAT);
		createEAttribute(numberTypeFacetEClass, NUMBER_TYPE_FACET__MULTIPLE_OF);

		dateTimeTypeFacetEClass = createEClass(DATE_TIME_TYPE_FACET);
		createEAttribute(dateTimeTypeFacetEClass, DATE_TIME_TYPE_FACET__FORMAT);

		fileTypeFacetEClass = createEClass(FILE_TYPE_FACET);
		createEAttribute(fileTypeFacetEClass, FILE_TYPE_FACET__FILE_TYPES);
		createEAttribute(fileTypeFacetEClass, FILE_TYPE_FACET__MIN_LENGTH);
		createEAttribute(fileTypeFacetEClass, FILE_TYPE_FACET__MAX_LENGTH);

		// Create enums
		numberFormatEEnum = createEEnum(NUMBER_FORMAT);
		dateTimeFormatEEnum = createEEnum(DATE_TIME_FORMAT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters
		ETypeParameter objectTypeFacetEClass_T = addETypeParameter(objectTypeFacetEClass, "T");
		ETypeParameter objectTypeFacetEClass_P = addETypeParameter(objectTypeFacetEClass, "P");
		ETypeParameter propertyEClass_T = addETypeParameter(propertyEClass, "T");
		ETypeParameter arrayTypeFacetEClass_T = addETypeParameter(arrayTypeFacetEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(this.getProperty());
		EGenericType g2 = createEGenericType(objectTypeFacetEClass_T);
		g1.getETypeArguments().add(g2);
		objectTypeFacetEClass_P.getEBounds().add(g1);

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(enumFacetEClass, EnumFacet.class, "EnumFacet", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumFacet_Enum(), theEcorePackage.getEString(), "enum", null, 0, -1, EnumFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(objectTypeFacetEClass, ObjectTypeFacet.class, "ObjectTypeFacet", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(objectTypeFacetEClass_P);
		initEReference(getObjectTypeFacet_Properties(), g1, null, "properties", null, 0, -1, ObjectTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObjectTypeFacet_MinProperties(), theEcorePackage.getEIntegerObject(), "minProperties", null, 0, 1, ObjectTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObjectTypeFacet_MaxProperties(), theEcorePackage.getEIntegerObject(), "maxProperties", null, 0, 1, ObjectTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObjectTypeFacet_AdditionalProperties(), theEcorePackage.getEBooleanObject(), "additionalProperties", "true", 0, 1, ObjectTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObjectTypeFacet_Discriminator(), theEcorePackage.getEString(), "discriminator", null, 0, 1, ObjectTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObjectTypeFacet_DiscriminatorValue(), theEcorePackage.getEString(), "discriminatorValue", null, 0, 1, ObjectTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProperty_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(propertyEClass_T);
		initEReference(getProperty_Type(), g1, null, "type", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Required(), theEcorePackage.getEBooleanObject(), "required", "true", 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arrayTypeFacetEClass, ArrayTypeFacet.class, "ArrayTypeFacet", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArrayTypeFacet_UniqueItems(), theEcorePackage.getEBooleanObject(), "uniqueItems", null, 0, 1, ArrayTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(arrayTypeFacetEClass_T);
		initEReference(getArrayTypeFacet_Items(), g1, null, "items", null, 0, 1, ArrayTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArrayTypeFacet_MinItems(), theEcorePackage.getEIntegerObject(), "minItems", "0", 0, 1, ArrayTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArrayTypeFacet_MaxItems(), theEcorePackage.getEIntegerObject(), "maxItems", "2147483647", 0, 1, ArrayTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringTypeFacetEClass, StringTypeFacet.class, "StringTypeFacet", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringTypeFacet_Pattern(), theEcorePackage.getEString(), "pattern", null, 0, 1, StringTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringTypeFacet_MinLength(), theEcorePackage.getEIntegerObject(), "minLength", "0", 0, 1, StringTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringTypeFacet_MaxLength(), theEcorePackage.getEIntegerObject(), "maxLength", "2147483647", 0, 1, StringTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(numberTypeFacetEClass, NumberTypeFacet.class, "NumberTypeFacet", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNumberTypeFacet_Minimum(), theEcorePackage.getEBigDecimal(), "minimum", null, 0, 1, NumberTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNumberTypeFacet_Maximum(), theEcorePackage.getEBigDecimal(), "maximum", null, 0, 1, NumberTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNumberTypeFacet_Format(), this.getNumberFormat(), "format", null, 0, 1, NumberTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNumberTypeFacet_MultipleOf(), theEcorePackage.getEIntegerObject(), "multipleOf", null, 0, 1, NumberTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dateTimeTypeFacetEClass, DateTimeTypeFacet.class, "DateTimeTypeFacet", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDateTimeTypeFacet_Format(), this.getDateTimeFormat(), "format", null, 0, 1, DateTimeTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fileTypeFacetEClass, FileTypeFacet.class, "FileTypeFacet", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFileTypeFacet_FileTypes(), theEcorePackage.getEString(), "fileTypes", null, 0, -1, FileTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileTypeFacet_MinLength(), theEcorePackage.getEIntegerObject(), "minLength", "0", 0, 1, FileTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileTypeFacet_MaxLength(), theEcorePackage.getEIntegerObject(), "maxLength", "2147483647", 0, 1, FileTypeFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(numberFormatEEnum, NumberFormat.class, "NumberFormat");
		addEEnumLiteral(numberFormatEEnum, NumberFormat.INT16);
		addEEnumLiteral(numberFormatEEnum, NumberFormat.INT8);
		addEEnumLiteral(numberFormatEEnum, NumberFormat.INT32);
		addEEnumLiteral(numberFormatEEnum, NumberFormat.INT64);
		addEEnumLiteral(numberFormatEEnum, NumberFormat.INT);
		addEEnumLiteral(numberFormatEEnum, NumberFormat.LONG);
		addEEnumLiteral(numberFormatEEnum, NumberFormat.FLOAT);
		addEEnumLiteral(numberFormatEEnum, NumberFormat.DOUBLE);

		initEEnum(dateTimeFormatEEnum, DateTimeFormat.class, "DateTimeFormat");
		addEEnumLiteral(dateTimeFormatEEnum, DateTimeFormat.RFC3339);
		addEEnumLiteral(dateTimeFormatEEnum, DateTimeFormat.RFC2616);

		// Create resource
		createResource(eNS_URI);
	}

} //FacetsPackageImpl
