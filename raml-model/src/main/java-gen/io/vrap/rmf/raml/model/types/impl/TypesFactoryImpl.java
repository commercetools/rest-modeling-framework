/**
 */
package io.vrap.rmf.raml.model.types.impl;

import io.vrap.rmf.raml.model.types.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesFactoryImpl extends EFactoryImpl implements TypesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TypesFactory init() {
		try {
			TypesFactory theTypesFactory = (TypesFactory)EPackage.Registry.INSTANCE.getEFactory(TypesPackage.eNS_URI);
			if (theTypesFactory != null) {
				return theTypesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TypesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TypesPackage.API: return createApi();
			case TypesPackage.LIBRARY: return createLibrary();
			case TypesPackage.LIBRARY_USE: return createLibraryUse();
			case TypesPackage.ANY_ANNOTATION_TYPE: return createAnyAnnotationType();
			case TypesPackage.TIME_ONLY_ANNOTATION_TYPE: return createTimeOnlyAnnotationType();
			case TypesPackage.DATE_TIME_ANNOTATION_TYPE: return createDateTimeAnnotationType();
			case TypesPackage.DATE_TIME_ONLY_ANNOTATION_TYPE: return createDateTimeOnlyAnnotationType();
			case TypesPackage.DATE_ONLY_ANNOTATION_TYPE: return createDateOnlyAnnotationType();
			case TypesPackage.NUMBER_ANNOTATION_TYPE: return createNumberAnnotationType();
			case TypesPackage.INTEGER_ANNOTATION_TYPE: return createIntegerAnnotationType();
			case TypesPackage.BOOLEAN_ANNOTATION_TYPE: return createBooleanAnnotationType();
			case TypesPackage.STRING_ANNOTATION_TYPE: return createStringAnnotationType();
			case TypesPackage.NIL_ANNOTATION_TYPE: return createNilAnnotationType();
			case TypesPackage.FILE_ANNOTATION_TYPE: return createFileAnnotationType();
			case TypesPackage.OBJECT_ANNOTATION_TYPE: return createObjectAnnotationType();
			case TypesPackage.ARRAY_ANNOTATION_TYPE: return createArrayAnnotationType();
			case TypesPackage.ANY_TYPE: return createAnyType();
			case TypesPackage.OBJECT_TYPE: return createObjectType();
			case TypesPackage.ARRAY_TYPE: return createArrayType();
			case TypesPackage.STRING_TYPE: return createStringType();
			case TypesPackage.NUMBER_TYPE: return createNumberType();
			case TypesPackage.INTEGER_TYPE: return createIntegerType();
			case TypesPackage.BOOLEAN_TYPE: return createBooleanType();
			case TypesPackage.DATE_ONLY_TYPE: return createDateOnlyType();
			case TypesPackage.TIME_ONLY_TYPE: return createTimeOnlyType();
			case TypesPackage.DATE_TIME_ONLY_TYPE: return createDateTimeOnlyType();
			case TypesPackage.DATE_TIME_TYPE: return createDateTimeType();
			case TypesPackage.FILE_TYPE: return createFileType();
			case TypesPackage.NIL_TYPE: return createNilType();
			case TypesPackage.INSTANCE: return createInstance();
			case TypesPackage.STRING_INSTANCE: return createStringInstance();
			case TypesPackage.BOOLEAN_INSTANCE: return createBooleanInstance();
			case TypesPackage.NUMBER_INSTANCE: return createNumberInstance();
			case TypesPackage.INTEGER_INSTANCE: return createIntegerInstance();
			case TypesPackage.OBJECT_INSTANCE: return createObjectInstance();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TypesPackage.ANNOTATION_TARGET:
				return createAnnotationTargetFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TypesPackage.ANNOTATION_TARGET:
				return convertAnnotationTargetToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Api createApi() {
		ApiImpl api = new ApiImpl();
		return api;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Library createLibrary() {
		LibraryImpl library = new LibraryImpl();
		return library;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryUse createLibraryUse() {
		LibraryUseImpl libraryUse = new LibraryUseImpl();
		return libraryUse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnyAnnotationType createAnyAnnotationType() {
		AnyAnnotationTypeImpl anyAnnotationType = new AnyAnnotationTypeImpl();
		return anyAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeOnlyAnnotationType createTimeOnlyAnnotationType() {
		TimeOnlyAnnotationTypeImpl timeOnlyAnnotationType = new TimeOnlyAnnotationTypeImpl();
		return timeOnlyAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DateTimeAnnotationType createDateTimeAnnotationType() {
		DateTimeAnnotationTypeImpl dateTimeAnnotationType = new DateTimeAnnotationTypeImpl();
		return dateTimeAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DateTimeOnlyAnnotationType createDateTimeOnlyAnnotationType() {
		DateTimeOnlyAnnotationTypeImpl dateTimeOnlyAnnotationType = new DateTimeOnlyAnnotationTypeImpl();
		return dateTimeOnlyAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DateOnlyAnnotationType createDateOnlyAnnotationType() {
		DateOnlyAnnotationTypeImpl dateOnlyAnnotationType = new DateOnlyAnnotationTypeImpl();
		return dateOnlyAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumberAnnotationType createNumberAnnotationType() {
		NumberAnnotationTypeImpl numberAnnotationType = new NumberAnnotationTypeImpl();
		return numberAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerAnnotationType createIntegerAnnotationType() {
		IntegerAnnotationTypeImpl integerAnnotationType = new IntegerAnnotationTypeImpl();
		return integerAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanAnnotationType createBooleanAnnotationType() {
		BooleanAnnotationTypeImpl booleanAnnotationType = new BooleanAnnotationTypeImpl();
		return booleanAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringAnnotationType createStringAnnotationType() {
		StringAnnotationTypeImpl stringAnnotationType = new StringAnnotationTypeImpl();
		return stringAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NilAnnotationType createNilAnnotationType() {
		NilAnnotationTypeImpl nilAnnotationType = new NilAnnotationTypeImpl();
		return nilAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileAnnotationType createFileAnnotationType() {
		FileAnnotationTypeImpl fileAnnotationType = new FileAnnotationTypeImpl();
		return fileAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectAnnotationType createObjectAnnotationType() {
		ObjectAnnotationTypeImpl objectAnnotationType = new ObjectAnnotationTypeImpl();
		return objectAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayAnnotationType createArrayAnnotationType() {
		ArrayAnnotationTypeImpl arrayAnnotationType = new ArrayAnnotationTypeImpl();
		return arrayAnnotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnyType createAnyType() {
		AnyTypeImpl anyType = new AnyTypeImpl();
		return anyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectType createObjectType() {
		ObjectTypeImpl objectType = new ObjectTypeImpl();
		return objectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayType createArrayType() {
		ArrayTypeImpl arrayType = new ArrayTypeImpl();
		return arrayType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringType createStringType() {
		StringTypeImpl stringType = new StringTypeImpl();
		return stringType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumberType createNumberType() {
		NumberTypeImpl numberType = new NumberTypeImpl();
		return numberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerType createIntegerType() {
		IntegerTypeImpl integerType = new IntegerTypeImpl();
		return integerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanType createBooleanType() {
		BooleanTypeImpl booleanType = new BooleanTypeImpl();
		return booleanType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DateOnlyType createDateOnlyType() {
		DateOnlyTypeImpl dateOnlyType = new DateOnlyTypeImpl();
		return dateOnlyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeOnlyType createTimeOnlyType() {
		TimeOnlyTypeImpl timeOnlyType = new TimeOnlyTypeImpl();
		return timeOnlyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DateTimeOnlyType createDateTimeOnlyType() {
		DateTimeOnlyTypeImpl dateTimeOnlyType = new DateTimeOnlyTypeImpl();
		return dateTimeOnlyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DateTimeType createDateTimeType() {
		DateTimeTypeImpl dateTimeType = new DateTimeTypeImpl();
		return dateTimeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileType createFileType() {
		FileTypeImpl fileType = new FileTypeImpl();
		return fileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NilType createNilType() {
		NilTypeImpl nilType = new NilTypeImpl();
		return nilType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <T extends AnyType> Instance<T> createInstance() {
		InstanceImpl<T> instance = new InstanceImpl<T>();
		return instance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringInstance createStringInstance() {
		StringInstanceImpl stringInstance = new StringInstanceImpl();
		return stringInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanInstance createBooleanInstance() {
		BooleanInstanceImpl booleanInstance = new BooleanInstanceImpl();
		return booleanInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumberInstance createNumberInstance() {
		NumberInstanceImpl numberInstance = new NumberInstanceImpl();
		return numberInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerInstance createIntegerInstance() {
		IntegerInstanceImpl integerInstance = new IntegerInstanceImpl();
		return integerInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectInstance createObjectInstance() {
		ObjectInstanceImpl objectInstance = new ObjectInstanceImpl();
		return objectInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnnotationTarget createAnnotationTargetFromString(EDataType eDataType, String initialValue) {
		AnnotationTarget result = AnnotationTarget.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAnnotationTargetToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesPackage getTypesPackage() {
		return (TypesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TypesPackage getPackage() {
		return TypesPackage.eINSTANCE;
	}

} //TypesFactoryImpl
