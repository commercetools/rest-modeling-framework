/**
 */
package io.vrap.rmf.raml.model.types.impl;

import io.vrap.rmf.raml.model.elements.ElementsPackage;

import io.vrap.rmf.raml.model.facets.FacetsPackage;

import io.vrap.rmf.raml.model.types.Annotatable;
import io.vrap.rmf.raml.model.types.AnnotationTarget;
import io.vrap.rmf.raml.model.types.AnnotationType;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.Api;
import io.vrap.rmf.raml.model.types.ArrayAnnotationType;
import io.vrap.rmf.raml.model.types.ArrayType;
import io.vrap.rmf.raml.model.types.BooleanAnnotationType;
import io.vrap.rmf.raml.model.types.BooleanInstance;
import io.vrap.rmf.raml.model.types.BooleanType;
import io.vrap.rmf.raml.model.types.DataType;
import io.vrap.rmf.raml.model.types.DateOnlyAnnotationType;
import io.vrap.rmf.raml.model.types.DateOnlyType;
import io.vrap.rmf.raml.model.types.DateTimeAnnotationType;
import io.vrap.rmf.raml.model.types.DateTimeOnlyAnnotationType;
import io.vrap.rmf.raml.model.types.DateTimeOnlyType;
import io.vrap.rmf.raml.model.types.DateTimeType;
import io.vrap.rmf.raml.model.types.FileAnnotationType;
import io.vrap.rmf.raml.model.types.FileType;
import io.vrap.rmf.raml.model.types.Instance;
import io.vrap.rmf.raml.model.types.IntegerAnnotationType;
import io.vrap.rmf.raml.model.types.IntegerInstance;
import io.vrap.rmf.raml.model.types.IntegerType;
import io.vrap.rmf.raml.model.types.Library;
import io.vrap.rmf.raml.model.types.LibraryUse;
import io.vrap.rmf.raml.model.types.NilAnnotationType;
import io.vrap.rmf.raml.model.types.NilType;
import io.vrap.rmf.raml.model.types.NumberAnnotationType;
import io.vrap.rmf.raml.model.types.NumberInstance;
import io.vrap.rmf.raml.model.types.NumberType;
import io.vrap.rmf.raml.model.types.ObjectAnnotationType;
import io.vrap.rmf.raml.model.types.ObjectInstance;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.StringAnnotationType;
import io.vrap.rmf.raml.model.types.StringInstance;
import io.vrap.rmf.raml.model.types.StringType;
import io.vrap.rmf.raml.model.types.TimeOnlyAnnotationType;
import io.vrap.rmf.raml.model.types.TimeOnlyType;
import io.vrap.rmf.raml.model.types.Type;
import io.vrap.rmf.raml.model.types.TypeContainer;
import io.vrap.rmf.raml.model.types.TypesFactory;
import io.vrap.rmf.raml.model.types.TypesPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
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
public class TypesPackageImpl extends EPackageImpl implements TypesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass apiEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass libraryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass libraryUseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeOnlyAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeOnlyAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateOnlyAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numberAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nilAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayAnnotationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotatableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numberTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateOnlyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeOnlyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeOnlyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nilTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numberInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum annotationTargetEEnum = null;

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
	 * @see io.vrap.rmf.raml.model.types.TypesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TypesPackageImpl() {
		super(eNS_URI, TypesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TypesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TypesPackage init() {
		if (isInited) return (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

		// Obtain or create and register package
		TypesPackageImpl theTypesPackage = (TypesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TypesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TypesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ElementsPackage.eINSTANCE.eClass();
		FacetsPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTypesPackage.createPackageContents();

		// Initialize created meta-data
		theTypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTypesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TypesPackage.eNS_URI, theTypesPackage);
		return theTypesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApi() {
		return apiEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApi_Title() {
		return (EAttribute)apiEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApi_Description() {
		return (EAttribute)apiEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApi_Version() {
		return (EAttribute)apiEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApi_BaseUri() {
		return (EAttribute)apiEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApi_Protocols() {
		return (EAttribute)apiEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApi_MediaType() {
		return (EAttribute)apiEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApi_SecuredBy() {
		return (EAttribute)apiEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTypeContainer() {
		return typeContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTypeContainer_Uses() {
		return (EReference)typeContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTypeContainer_Types() {
		return (EReference)typeContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTypeContainer_AnnotationTypes() {
		return (EReference)typeContainerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLibrary() {
		return libraryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLibrary_Usage() {
		return (EAttribute)libraryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLibraryUse() {
		return libraryUseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLibraryUse_Name() {
		return (EAttribute)libraryUseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLibraryUse_Library() {
		return (EReference)libraryUseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getType() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getType_Type() {
		return (EReference)typeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getType_BaseClass() {
		return (EAttribute)typeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataType() {
		return dataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAnnotationType() {
		return annotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAnnotationType_AllowedTargets() {
		return (EAttribute)annotationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAnyAnnotationType() {
		return anyAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeOnlyAnnotationType() {
		return timeOnlyAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDateTimeAnnotationType() {
		return dateTimeAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDateTimeOnlyAnnotationType() {
		return dateTimeOnlyAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDateOnlyAnnotationType() {
		return dateOnlyAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumberAnnotationType() {
		return numberAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerAnnotationType() {
		return integerAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanAnnotationType() {
		return booleanAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringAnnotationType() {
		return stringAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNilAnnotationType() {
		return nilAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileAnnotationType() {
		return fileAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectAnnotationType() {
		return objectAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArrayAnnotationType() {
		return arrayAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAnyType() {
		return anyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAnnotatable() {
		return annotatableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAnnotatable_Annotations() {
		return (EReference)annotatableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAnnotatable__GetAnnotation__AnyType() {
		return annotatableEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectType() {
		return objectTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getObjectType__GetProperty__String() {
		return objectTypeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArrayType() {
		return arrayTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringType() {
		return stringTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumberType() {
		return numberTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerType() {
		return integerTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanType() {
		return booleanTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDateOnlyType() {
		return dateOnlyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeOnlyType() {
		return timeOnlyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDateTimeOnlyType() {
		return dateTimeOnlyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDateTimeType() {
		return dateTimeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileType() {
		return fileTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNilType() {
		return nilTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstance() {
		return instanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstance_Type() {
		return (EReference)instanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringInstance() {
		return stringInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringInstance_Value() {
		return (EAttribute)stringInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanInstance() {
		return booleanInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanInstance_Value() {
		return (EAttribute)booleanInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumberInstance() {
		return numberInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNumberInstance_Value() {
		return (EAttribute)numberInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerInstance() {
		return integerInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerInstance_Value() {
		return (EAttribute)integerInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectInstance() {
		return objectInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectInstance_Value() {
		return (EReference)objectInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getObjectInstance__GetPropertyy__AnyType() {
		return objectInstanceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAnnotationTarget() {
		return annotationTargetEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesFactory getTypesFactory() {
		return (TypesFactory)getEFactoryInstance();
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
		apiEClass = createEClass(API);
		createEAttribute(apiEClass, API__TITLE);
		createEAttribute(apiEClass, API__DESCRIPTION);
		createEAttribute(apiEClass, API__VERSION);
		createEAttribute(apiEClass, API__BASE_URI);
		createEAttribute(apiEClass, API__PROTOCOLS);
		createEAttribute(apiEClass, API__MEDIA_TYPE);
		createEAttribute(apiEClass, API__SECURED_BY);

		typeContainerEClass = createEClass(TYPE_CONTAINER);
		createEReference(typeContainerEClass, TYPE_CONTAINER__USES);
		createEReference(typeContainerEClass, TYPE_CONTAINER__TYPES);
		createEReference(typeContainerEClass, TYPE_CONTAINER__ANNOTATION_TYPES);

		libraryEClass = createEClass(LIBRARY);
		createEAttribute(libraryEClass, LIBRARY__USAGE);

		libraryUseEClass = createEClass(LIBRARY_USE);
		createEAttribute(libraryUseEClass, LIBRARY_USE__NAME);
		createEReference(libraryUseEClass, LIBRARY_USE__LIBRARY);

		typeEClass = createEClass(TYPE);
		createEReference(typeEClass, TYPE__TYPE);
		createEAttribute(typeEClass, TYPE__BASE_CLASS);

		dataTypeEClass = createEClass(DATA_TYPE);

		annotationTypeEClass = createEClass(ANNOTATION_TYPE);
		createEAttribute(annotationTypeEClass, ANNOTATION_TYPE__ALLOWED_TARGETS);

		anyAnnotationTypeEClass = createEClass(ANY_ANNOTATION_TYPE);

		timeOnlyAnnotationTypeEClass = createEClass(TIME_ONLY_ANNOTATION_TYPE);

		dateTimeAnnotationTypeEClass = createEClass(DATE_TIME_ANNOTATION_TYPE);

		dateTimeOnlyAnnotationTypeEClass = createEClass(DATE_TIME_ONLY_ANNOTATION_TYPE);

		dateOnlyAnnotationTypeEClass = createEClass(DATE_ONLY_ANNOTATION_TYPE);

		numberAnnotationTypeEClass = createEClass(NUMBER_ANNOTATION_TYPE);

		integerAnnotationTypeEClass = createEClass(INTEGER_ANNOTATION_TYPE);

		booleanAnnotationTypeEClass = createEClass(BOOLEAN_ANNOTATION_TYPE);

		stringAnnotationTypeEClass = createEClass(STRING_ANNOTATION_TYPE);

		nilAnnotationTypeEClass = createEClass(NIL_ANNOTATION_TYPE);

		fileAnnotationTypeEClass = createEClass(FILE_ANNOTATION_TYPE);

		objectAnnotationTypeEClass = createEClass(OBJECT_ANNOTATION_TYPE);

		arrayAnnotationTypeEClass = createEClass(ARRAY_ANNOTATION_TYPE);

		anyTypeEClass = createEClass(ANY_TYPE);

		annotatableEClass = createEClass(ANNOTATABLE);
		createEReference(annotatableEClass, ANNOTATABLE__ANNOTATIONS);
		createEOperation(annotatableEClass, ANNOTATABLE___GET_ANNOTATION__ANYTYPE);

		objectTypeEClass = createEClass(OBJECT_TYPE);
		createEOperation(objectTypeEClass, OBJECT_TYPE___GET_PROPERTY__STRING);

		arrayTypeEClass = createEClass(ARRAY_TYPE);

		stringTypeEClass = createEClass(STRING_TYPE);

		numberTypeEClass = createEClass(NUMBER_TYPE);

		integerTypeEClass = createEClass(INTEGER_TYPE);

		booleanTypeEClass = createEClass(BOOLEAN_TYPE);

		dateOnlyTypeEClass = createEClass(DATE_ONLY_TYPE);

		timeOnlyTypeEClass = createEClass(TIME_ONLY_TYPE);

		dateTimeOnlyTypeEClass = createEClass(DATE_TIME_ONLY_TYPE);

		dateTimeTypeEClass = createEClass(DATE_TIME_TYPE);

		fileTypeEClass = createEClass(FILE_TYPE);

		nilTypeEClass = createEClass(NIL_TYPE);

		instanceEClass = createEClass(INSTANCE);
		createEReference(instanceEClass, INSTANCE__TYPE);

		stringInstanceEClass = createEClass(STRING_INSTANCE);
		createEAttribute(stringInstanceEClass, STRING_INSTANCE__VALUE);

		booleanInstanceEClass = createEClass(BOOLEAN_INSTANCE);
		createEAttribute(booleanInstanceEClass, BOOLEAN_INSTANCE__VALUE);

		numberInstanceEClass = createEClass(NUMBER_INSTANCE);
		createEAttribute(numberInstanceEClass, NUMBER_INSTANCE__VALUE);

		integerInstanceEClass = createEClass(INTEGER_INSTANCE);
		createEAttribute(integerInstanceEClass, INTEGER_INSTANCE__VALUE);

		objectInstanceEClass = createEClass(OBJECT_INSTANCE);
		createEReference(objectInstanceEClass, OBJECT_INSTANCE__VALUE);
		createEOperation(objectInstanceEClass, OBJECT_INSTANCE___GET_PROPERTYY__ANYTYPE);

		// Create enums
		annotationTargetEEnum = createEEnum(ANNOTATION_TARGET);
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
		ElementsPackage theElementsPackage = (ElementsPackage)EPackage.Registry.INSTANCE.getEPackage(ElementsPackage.eNS_URI);
		FacetsPackage theFacetsPackage = (FacetsPackage)EPackage.Registry.INSTANCE.getEPackage(FacetsPackage.eNS_URI);

		// Create type parameters
		ETypeParameter typeEClass_T = addETypeParameter(typeEClass, "T");
		ETypeParameter instanceEClass_T = addETypeParameter(instanceEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(this.getAnyType());
		instanceEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		apiEClass.getESuperTypes().add(this.getAnnotatable());
		apiEClass.getESuperTypes().add(this.getTypeContainer());
		libraryEClass.getESuperTypes().add(this.getAnnotatable());
		libraryEClass.getESuperTypes().add(this.getTypeContainer());
		g1 = createEGenericType(this.getType());
		EGenericType g2 = createEGenericType(this.getAnyType());
		g1.getETypeArguments().add(g2);
		dataTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getType());
		g2 = createEGenericType(this.getAnyAnnotationType());
		g1.getETypeArguments().add(g2);
		annotationTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theElementsPackage.getDocumentableElement());
		annotationTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theElementsPackage.getIdentifiableElement());
		annotationTypeEClass.getEGenericSuperTypes().add(g1);
		anyAnnotationTypeEClass.getESuperTypes().add(this.getAnnotationType());
		timeOnlyAnnotationTypeEClass.getESuperTypes().add(this.getAnnotationType());
		dateTimeAnnotationTypeEClass.getESuperTypes().add(theFacetsPackage.getDateTimeTypeFacet());
		dateTimeAnnotationTypeEClass.getESuperTypes().add(this.getAnnotationType());
		dateTimeOnlyAnnotationTypeEClass.getESuperTypes().add(this.getAnnotationType());
		dateOnlyAnnotationTypeEClass.getESuperTypes().add(this.getAnnotationType());
		numberAnnotationTypeEClass.getESuperTypes().add(theFacetsPackage.getNumberTypeFacet());
		numberAnnotationTypeEClass.getESuperTypes().add(this.getAnnotationType());
		integerAnnotationTypeEClass.getESuperTypes().add(this.getNumberAnnotationType());
		booleanAnnotationTypeEClass.getESuperTypes().add(this.getAnnotationType());
		stringAnnotationTypeEClass.getESuperTypes().add(theFacetsPackage.getStringTypeFacet());
		stringAnnotationTypeEClass.getESuperTypes().add(this.getAnnotationType());
		nilAnnotationTypeEClass.getESuperTypes().add(this.getAnnotationType());
		fileAnnotationTypeEClass.getESuperTypes().add(theFacetsPackage.getFileTypeFacet());
		fileAnnotationTypeEClass.getESuperTypes().add(this.getAnnotationType());
		g1 = createEGenericType(theFacetsPackage.getObjectTypeFacet());
		g2 = createEGenericType(this.getAnyAnnotationType());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theFacetsPackage.getProperty());
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(this.getAnyAnnotationType());
		g2.getETypeArguments().add(g3);
		objectAnnotationTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getAnnotationType());
		objectAnnotationTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theFacetsPackage.getArrayTypeFacet());
		g2 = createEGenericType(this.getAnyAnnotationType());
		g1.getETypeArguments().add(g2);
		arrayAnnotationTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getAnnotationType());
		arrayAnnotationTypeEClass.getEGenericSuperTypes().add(g1);
		anyTypeEClass.getESuperTypes().add(this.getDataType());
		anyTypeEClass.getESuperTypes().add(this.getAnnotatable());
		anyTypeEClass.getESuperTypes().add(theElementsPackage.getDocumentableElement());
		anyTypeEClass.getESuperTypes().add(theElementsPackage.getIdentifiableElement());
		anyTypeEClass.getESuperTypes().add(theFacetsPackage.getEnumFacet());
		g1 = createEGenericType(this.getAnyType());
		objectTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theFacetsPackage.getObjectTypeFacet());
		g2 = createEGenericType(this.getAnyType());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theFacetsPackage.getProperty());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType(this.getAnyType());
		g2.getETypeArguments().add(g3);
		objectTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getAnyType());
		arrayTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theFacetsPackage.getArrayTypeFacet());
		g2 = createEGenericType(this.getAnyType());
		g1.getETypeArguments().add(g2);
		arrayTypeEClass.getEGenericSuperTypes().add(g1);
		stringTypeEClass.getESuperTypes().add(this.getAnyType());
		stringTypeEClass.getESuperTypes().add(theFacetsPackage.getStringTypeFacet());
		numberTypeEClass.getESuperTypes().add(this.getAnyType());
		numberTypeEClass.getESuperTypes().add(theFacetsPackage.getNumberTypeFacet());
		integerTypeEClass.getESuperTypes().add(this.getNumberType());
		booleanTypeEClass.getESuperTypes().add(this.getAnyType());
		dateOnlyTypeEClass.getESuperTypes().add(this.getAnyType());
		timeOnlyTypeEClass.getESuperTypes().add(this.getAnyType());
		dateTimeOnlyTypeEClass.getESuperTypes().add(this.getAnyType());
		dateTimeTypeEClass.getESuperTypes().add(this.getAnyType());
		dateTimeTypeEClass.getESuperTypes().add(theFacetsPackage.getDateTimeTypeFacet());
		fileTypeEClass.getESuperTypes().add(this.getAnyType());
		fileTypeEClass.getESuperTypes().add(theFacetsPackage.getFileTypeFacet());
		nilTypeEClass.getESuperTypes().add(this.getAnyType());
		g1 = createEGenericType(this.getInstance());
		g2 = createEGenericType(this.getStringType());
		g1.getETypeArguments().add(g2);
		stringInstanceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getInstance());
		g2 = createEGenericType(this.getBooleanType());
		g1.getETypeArguments().add(g2);
		booleanInstanceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getInstance());
		g2 = createEGenericType(this.getIntegerType());
		g1.getETypeArguments().add(g2);
		numberInstanceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getInstance());
		g2 = createEGenericType(this.getIntegerType());
		g1.getETypeArguments().add(g2);
		integerInstanceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getInstance());
		g2 = createEGenericType(this.getObjectType());
		g1.getETypeArguments().add(g2);
		objectInstanceEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(apiEClass, Api.class, "Api", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getApi_Title(), theEcorePackage.getEString(), "title", null, 0, 1, Api.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApi_Description(), theEcorePackage.getEString(), "description", null, 0, 1, Api.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApi_Version(), theEcorePackage.getEString(), "version", null, 0, 1, Api.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApi_BaseUri(), theEcorePackage.getEString(), "baseUri", null, 0, 1, Api.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApi_Protocols(), theEcorePackage.getEString(), "protocols", null, 0, -1, Api.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApi_MediaType(), theEcorePackage.getEString(), "mediaType", null, 0, -1, Api.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApi_SecuredBy(), theEcorePackage.getEString(), "securedBy", null, 0, -1, Api.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeContainerEClass, TypeContainer.class, "TypeContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeContainer_Uses(), this.getLibraryUse(), null, "uses", null, 0, -1, TypeContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTypeContainer_Types(), this.getAnyType(), null, "types", null, 0, -1, TypeContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTypeContainer_AnnotationTypes(), this.getAnnotationType(), null, "annotationTypes", null, 0, -1, TypeContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(libraryEClass, Library.class, "Library", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLibrary_Usage(), theEcorePackage.getEString(), "usage", null, 0, 1, Library.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(libraryUseEClass, LibraryUse.class, "LibraryUse", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLibraryUse_Name(), theEcorePackage.getEString(), "name", null, 0, 1, LibraryUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLibraryUse_Library(), this.getLibrary(), null, "library", null, 0, 1, LibraryUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeEClass, Type.class, "Type", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(typeEClass_T);
		initEReference(getType_Type(), g1, null, "type", null, 0, 1, Type.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theEcorePackage.getEJavaClass());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEAttribute(getType_BaseClass(), g1, "baseClass", null, 0, 1, Type.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(dataTypeEClass, DataType.class, "DataType", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(annotationTypeEClass, AnnotationType.class, "AnnotationType", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAnnotationType_AllowedTargets(), this.getAnnotationTarget(), "allowedTargets", null, 0, -1, AnnotationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(anyAnnotationTypeEClass, AnyAnnotationType.class, "AnyAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(timeOnlyAnnotationTypeEClass, TimeOnlyAnnotationType.class, "TimeOnlyAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dateTimeAnnotationTypeEClass, DateTimeAnnotationType.class, "DateTimeAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dateTimeOnlyAnnotationTypeEClass, DateTimeOnlyAnnotationType.class, "DateTimeOnlyAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dateOnlyAnnotationTypeEClass, DateOnlyAnnotationType.class, "DateOnlyAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(numberAnnotationTypeEClass, NumberAnnotationType.class, "NumberAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(integerAnnotationTypeEClass, IntegerAnnotationType.class, "IntegerAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(booleanAnnotationTypeEClass, BooleanAnnotationType.class, "BooleanAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stringAnnotationTypeEClass, StringAnnotationType.class, "StringAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nilAnnotationTypeEClass, NilAnnotationType.class, "NilAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(fileAnnotationTypeEClass, FileAnnotationType.class, "FileAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(objectAnnotationTypeEClass, ObjectAnnotationType.class, "ObjectAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(arrayAnnotationTypeEClass, ArrayAnnotationType.class, "ArrayAnnotationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(anyTypeEClass, AnyType.class, "AnyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(annotatableEClass, Annotatable.class, "Annotatable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getInstance());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getAnnotatable_Annotations(), g1, null, "annotations", null, 0, -1, Annotatable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getAnnotatable__GetAnnotation__AnyType(), null, "getAnnotation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		ETypeParameter t1 = addETypeParameter(op, "T");
		g1 = createEGenericType(this.getAnyType());
		t1.getEBounds().add(g1);
		ETypeParameter t2 = addETypeParameter(op, "A");
		g1 = createEGenericType(this.getInstance());
		g2 = createEGenericType(t1);
		g1.getETypeArguments().add(g2);
		t2.getEBounds().add(g1);
		g1 = createEGenericType(t1);
		addEParameter(op, g1, "annotationType", 0, 1, !IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(t2);
		initEOperation(op, g1);

		initEClass(objectTypeEClass, ObjectType.class, "ObjectType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getObjectType__GetProperty__String(), null, "getProperty", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "name", 0, 1, !IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(theFacetsPackage.getProperty());
		g2 = createEGenericType(this.getAnyType());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(arrayTypeEClass, ArrayType.class, "ArrayType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stringTypeEClass, StringType.class, "StringType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(numberTypeEClass, NumberType.class, "NumberType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(integerTypeEClass, IntegerType.class, "IntegerType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(booleanTypeEClass, BooleanType.class, "BooleanType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dateOnlyTypeEClass, DateOnlyType.class, "DateOnlyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(timeOnlyTypeEClass, TimeOnlyType.class, "TimeOnlyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dateTimeOnlyTypeEClass, DateTimeOnlyType.class, "DateTimeOnlyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dateTimeTypeEClass, DateTimeType.class, "DateTimeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(fileTypeEClass, FileType.class, "FileType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nilTypeEClass, NilType.class, "NilType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(instanceEClass, Instance.class, "Instance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(instanceEClass_T);
		initEReference(getInstance_Type(), g1, null, "type", null, 0, 1, Instance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringInstanceEClass, StringInstance.class, "StringInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringInstance_Value(), theEcorePackage.getEString(), "value", null, 0, 1, StringInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanInstanceEClass, BooleanInstance.class, "BooleanInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanInstance_Value(), theEcorePackage.getEBooleanObject(), "value", null, 0, 1, BooleanInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(numberInstanceEClass, NumberInstance.class, "NumberInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNumberInstance_Value(), theEcorePackage.getEBigDecimal(), "value", null, 0, 1, NumberInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(integerInstanceEClass, IntegerInstance.class, "IntegerInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerInstance_Value(), theEcorePackage.getELongObject(), "value", null, 0, 1, IntegerInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(objectInstanceEClass, ObjectInstance.class, "ObjectInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getInstance());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getObjectInstance_Value(), g1, null, "value", null, 0, -1, ObjectInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getObjectInstance__GetPropertyy__AnyType(), null, "getPropertyy", 0, 1, !IS_UNIQUE, IS_ORDERED);
		t1 = addETypeParameter(op, "T");
		g1 = createEGenericType(this.getAnyType());
		t1.getEBounds().add(g1);
		t2 = addETypeParameter(op, "V");
		g1 = createEGenericType(this.getInstance());
		g2 = createEGenericType(t1);
		g1.getETypeArguments().add(g2);
		t2.getEBounds().add(g1);
		g1 = createEGenericType(t1);
		addEParameter(op, g1, "valueType", 0, 1, !IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(t2);
		initEOperation(op, g1);

		// Initialize enums and add enum literals
		initEEnum(annotationTargetEEnum, AnnotationTarget.class, "AnnotationTarget");
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.API);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.DOCUMENTATION_ITEM);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.RESOURCE);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.METHOD);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.RESPONSE);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.REQUEST_BODY);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.RESPONSE_BODY);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.TYPE_DECLARATION);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.EXAMPLE);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.RESOURCE_TYPE);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.TRAIT);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.SECURITY_SCHEME);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.SECURITY_SCHEME_SETTINGS);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.ANNOTATION_TYPE);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.LIBRARY);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.OVERLAY);
		addEEnumLiteral(annotationTargetEEnum, AnnotationTarget.EXTENSION);

		// Create resource
		createResource(eNS_URI);
	}

} //TypesPackageImpl
