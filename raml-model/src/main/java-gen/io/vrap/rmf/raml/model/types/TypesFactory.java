/**
 */
package io.vrap.rmf.raml.model.types;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see io.vrap.rmf.raml.model.types.TypesPackage
 * @generated
 */
public interface TypesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TypesFactory eINSTANCE = io.vrap.rmf.raml.model.types.impl.TypesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Api</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Api</em>'.
	 * @generated
	 */
	Api createApi();

	/**
	 * Returns a new object of class '<em>Library</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Library</em>'.
	 * @generated
	 */
	Library createLibrary();

	/**
	 * Returns a new object of class '<em>Library Use</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Library Use</em>'.
	 * @generated
	 */
	LibraryUse createLibraryUse();

	/**
	 * Returns a new object of class '<em>Any Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Annotation Type</em>'.
	 * @generated
	 */
	AnyAnnotationType createAnyAnnotationType();

	/**
	 * Returns a new object of class '<em>Time Only Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Only Annotation Type</em>'.
	 * @generated
	 */
	TimeOnlyAnnotationType createTimeOnlyAnnotationType();

	/**
	 * Returns a new object of class '<em>Date Time Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Time Annotation Type</em>'.
	 * @generated
	 */
	DateTimeAnnotationType createDateTimeAnnotationType();

	/**
	 * Returns a new object of class '<em>Date Time Only Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Time Only Annotation Type</em>'.
	 * @generated
	 */
	DateTimeOnlyAnnotationType createDateTimeOnlyAnnotationType();

	/**
	 * Returns a new object of class '<em>Date Only Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Only Annotation Type</em>'.
	 * @generated
	 */
	DateOnlyAnnotationType createDateOnlyAnnotationType();

	/**
	 * Returns a new object of class '<em>Number Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Number Annotation Type</em>'.
	 * @generated
	 */
	NumberAnnotationType createNumberAnnotationType();

	/**
	 * Returns a new object of class '<em>Integer Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Annotation Type</em>'.
	 * @generated
	 */
	IntegerAnnotationType createIntegerAnnotationType();

	/**
	 * Returns a new object of class '<em>Boolean Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Annotation Type</em>'.
	 * @generated
	 */
	BooleanAnnotationType createBooleanAnnotationType();

	/**
	 * Returns a new object of class '<em>String Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Annotation Type</em>'.
	 * @generated
	 */
	StringAnnotationType createStringAnnotationType();

	/**
	 * Returns a new object of class '<em>Nil Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Nil Annotation Type</em>'.
	 * @generated
	 */
	NilAnnotationType createNilAnnotationType();

	/**
	 * Returns a new object of class '<em>File Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>File Annotation Type</em>'.
	 * @generated
	 */
	FileAnnotationType createFileAnnotationType();

	/**
	 * Returns a new object of class '<em>Object Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Object Annotation Type</em>'.
	 * @generated
	 */
	ObjectAnnotationType createObjectAnnotationType();

	/**
	 * Returns a new object of class '<em>Array Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Annotation Type</em>'.
	 * @generated
	 */
	ArrayAnnotationType createArrayAnnotationType();

	/**
	 * Returns a new object of class '<em>Any Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Type</em>'.
	 * @generated
	 */
	AnyType createAnyType();

	/**
	 * Returns a new object of class '<em>Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Object Type</em>'.
	 * @generated
	 */
	ObjectType createObjectType();

	/**
	 * Returns a new object of class '<em>Array Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Type</em>'.
	 * @generated
	 */
	ArrayType createArrayType();

	/**
	 * Returns a new object of class '<em>String Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Type</em>'.
	 * @generated
	 */
	StringType createStringType();

	/**
	 * Returns a new object of class '<em>Number Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Number Type</em>'.
	 * @generated
	 */
	NumberType createNumberType();

	/**
	 * Returns a new object of class '<em>Integer Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Type</em>'.
	 * @generated
	 */
	IntegerType createIntegerType();

	/**
	 * Returns a new object of class '<em>Boolean Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Type</em>'.
	 * @generated
	 */
	BooleanType createBooleanType();

	/**
	 * Returns a new object of class '<em>Date Only Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Only Type</em>'.
	 * @generated
	 */
	DateOnlyType createDateOnlyType();

	/**
	 * Returns a new object of class '<em>Time Only Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Only Type</em>'.
	 * @generated
	 */
	TimeOnlyType createTimeOnlyType();

	/**
	 * Returns a new object of class '<em>Date Time Only Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Time Only Type</em>'.
	 * @generated
	 */
	DateTimeOnlyType createDateTimeOnlyType();

	/**
	 * Returns a new object of class '<em>Date Time Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Time Type</em>'.
	 * @generated
	 */
	DateTimeType createDateTimeType();

	/**
	 * Returns a new object of class '<em>File Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>File Type</em>'.
	 * @generated
	 */
	FileType createFileType();

	/**
	 * Returns a new object of class '<em>Nil Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Nil Type</em>'.
	 * @generated
	 */
	NilType createNilType();

	/**
	 * Returns a new object of class '<em>Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Instance</em>'.
	 * @generated
	 */
	<T extends AnyType> Instance<T> createInstance();

	/**
	 * Returns a new object of class '<em>String Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Instance</em>'.
	 * @generated
	 */
	StringInstance createStringInstance();

	/**
	 * Returns a new object of class '<em>Boolean Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Instance</em>'.
	 * @generated
	 */
	BooleanInstance createBooleanInstance();

	/**
	 * Returns a new object of class '<em>Number Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Number Instance</em>'.
	 * @generated
	 */
	NumberInstance createNumberInstance();

	/**
	 * Returns a new object of class '<em>Integer Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Instance</em>'.
	 * @generated
	 */
	IntegerInstance createIntegerInstance();

	/**
	 * Returns a new object of class '<em>Object Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Object Instance</em>'.
	 * @generated
	 */
	ObjectInstance createObjectInstance();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TypesPackage getTypesPackage();

} //TypesFactory
