/**
 */
package io.vrap.rmf.raml.model.types.util;

import io.vrap.rmf.raml.model.elements.DocumentableElement;
import io.vrap.rmf.raml.model.elements.IdentifiableElement;

import io.vrap.rmf.raml.model.facets.ArrayTypeFacet;
import io.vrap.rmf.raml.model.facets.DateTimeTypeFacet;
import io.vrap.rmf.raml.model.facets.EnumFacet;
import io.vrap.rmf.raml.model.facets.FileTypeFacet;
import io.vrap.rmf.raml.model.facets.NumberTypeFacet;
import io.vrap.rmf.raml.model.facets.ObjectTypeFacet;
import io.vrap.rmf.raml.model.facets.Property;
import io.vrap.rmf.raml.model.facets.StringTypeFacet;

import io.vrap.rmf.raml.model.types.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see io.vrap.rmf.raml.model.types.TypesPackage
 * @generated
 */
public class TypesSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TypesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesSwitch() {
		if (modelPackage == null) {
			modelPackage = TypesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TypesPackage.API: {
				Api api = (Api)theEObject;
				T1 result = caseApi(api);
				if (result == null) result = caseAnnotatable(api);
				if (result == null) result = caseTypeContainer(api);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TYPE_CONTAINER: {
				TypeContainer typeContainer = (TypeContainer)theEObject;
				T1 result = caseTypeContainer(typeContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.LIBRARY: {
				Library library = (Library)theEObject;
				T1 result = caseLibrary(library);
				if (result == null) result = caseAnnotatable(library);
				if (result == null) result = caseTypeContainer(library);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.LIBRARY_USE: {
				LibraryUse libraryUse = (LibraryUse)theEObject;
				T1 result = caseLibraryUse(libraryUse);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TYPE: {
				Type<?> type = (Type<?>)theEObject;
				T1 result = caseType(type);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DATA_TYPE: {
				DataType dataType = (DataType)theEObject;
				T1 result = caseDataType(dataType);
				if (result == null) result = caseType(dataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ANNOTATION_TYPE: {
				AnnotationType annotationType = (AnnotationType)theEObject;
				T1 result = caseAnnotationType(annotationType);
				if (result == null) result = caseType(annotationType);
				if (result == null) result = caseDocumentableElement(annotationType);
				if (result == null) result = caseIdentifiableElement(annotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ANY_ANNOTATION_TYPE: {
				AnyAnnotationType anyAnnotationType = (AnyAnnotationType)theEObject;
				T1 result = caseAnyAnnotationType(anyAnnotationType);
				if (result == null) result = caseAnnotationType(anyAnnotationType);
				if (result == null) result = caseType(anyAnnotationType);
				if (result == null) result = caseDocumentableElement(anyAnnotationType);
				if (result == null) result = caseIdentifiableElement(anyAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TIME_ONLY_ANNOTATION_TYPE: {
				TimeOnlyAnnotationType timeOnlyAnnotationType = (TimeOnlyAnnotationType)theEObject;
				T1 result = caseTimeOnlyAnnotationType(timeOnlyAnnotationType);
				if (result == null) result = caseAnnotationType(timeOnlyAnnotationType);
				if (result == null) result = caseType(timeOnlyAnnotationType);
				if (result == null) result = caseDocumentableElement(timeOnlyAnnotationType);
				if (result == null) result = caseIdentifiableElement(timeOnlyAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DATE_TIME_ANNOTATION_TYPE: {
				DateTimeAnnotationType dateTimeAnnotationType = (DateTimeAnnotationType)theEObject;
				T1 result = caseDateTimeAnnotationType(dateTimeAnnotationType);
				if (result == null) result = caseDateTimeTypeFacet(dateTimeAnnotationType);
				if (result == null) result = caseAnnotationType(dateTimeAnnotationType);
				if (result == null) result = caseType(dateTimeAnnotationType);
				if (result == null) result = caseDocumentableElement(dateTimeAnnotationType);
				if (result == null) result = caseIdentifiableElement(dateTimeAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DATE_TIME_ONLY_ANNOTATION_TYPE: {
				DateTimeOnlyAnnotationType dateTimeOnlyAnnotationType = (DateTimeOnlyAnnotationType)theEObject;
				T1 result = caseDateTimeOnlyAnnotationType(dateTimeOnlyAnnotationType);
				if (result == null) result = caseAnnotationType(dateTimeOnlyAnnotationType);
				if (result == null) result = caseType(dateTimeOnlyAnnotationType);
				if (result == null) result = caseDocumentableElement(dateTimeOnlyAnnotationType);
				if (result == null) result = caseIdentifiableElement(dateTimeOnlyAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DATE_ONLY_ANNOTATION_TYPE: {
				DateOnlyAnnotationType dateOnlyAnnotationType = (DateOnlyAnnotationType)theEObject;
				T1 result = caseDateOnlyAnnotationType(dateOnlyAnnotationType);
				if (result == null) result = caseAnnotationType(dateOnlyAnnotationType);
				if (result == null) result = caseType(dateOnlyAnnotationType);
				if (result == null) result = caseDocumentableElement(dateOnlyAnnotationType);
				if (result == null) result = caseIdentifiableElement(dateOnlyAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.NUMBER_ANNOTATION_TYPE: {
				NumberAnnotationType numberAnnotationType = (NumberAnnotationType)theEObject;
				T1 result = caseNumberAnnotationType(numberAnnotationType);
				if (result == null) result = caseNumberTypeFacet(numberAnnotationType);
				if (result == null) result = caseAnnotationType(numberAnnotationType);
				if (result == null) result = caseType(numberAnnotationType);
				if (result == null) result = caseDocumentableElement(numberAnnotationType);
				if (result == null) result = caseIdentifiableElement(numberAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INTEGER_ANNOTATION_TYPE: {
				IntegerAnnotationType integerAnnotationType = (IntegerAnnotationType)theEObject;
				T1 result = caseIntegerAnnotationType(integerAnnotationType);
				if (result == null) result = caseNumberAnnotationType(integerAnnotationType);
				if (result == null) result = caseNumberTypeFacet(integerAnnotationType);
				if (result == null) result = caseAnnotationType(integerAnnotationType);
				if (result == null) result = caseType(integerAnnotationType);
				if (result == null) result = caseDocumentableElement(integerAnnotationType);
				if (result == null) result = caseIdentifiableElement(integerAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.BOOLEAN_ANNOTATION_TYPE: {
				BooleanAnnotationType booleanAnnotationType = (BooleanAnnotationType)theEObject;
				T1 result = caseBooleanAnnotationType(booleanAnnotationType);
				if (result == null) result = caseAnnotationType(booleanAnnotationType);
				if (result == null) result = caseType(booleanAnnotationType);
				if (result == null) result = caseDocumentableElement(booleanAnnotationType);
				if (result == null) result = caseIdentifiableElement(booleanAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.STRING_ANNOTATION_TYPE: {
				StringAnnotationType stringAnnotationType = (StringAnnotationType)theEObject;
				T1 result = caseStringAnnotationType(stringAnnotationType);
				if (result == null) result = caseStringTypeFacet(stringAnnotationType);
				if (result == null) result = caseAnnotationType(stringAnnotationType);
				if (result == null) result = caseType(stringAnnotationType);
				if (result == null) result = caseDocumentableElement(stringAnnotationType);
				if (result == null) result = caseIdentifiableElement(stringAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.NIL_ANNOTATION_TYPE: {
				NilAnnotationType nilAnnotationType = (NilAnnotationType)theEObject;
				T1 result = caseNilAnnotationType(nilAnnotationType);
				if (result == null) result = caseAnnotationType(nilAnnotationType);
				if (result == null) result = caseType(nilAnnotationType);
				if (result == null) result = caseDocumentableElement(nilAnnotationType);
				if (result == null) result = caseIdentifiableElement(nilAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.FILE_ANNOTATION_TYPE: {
				FileAnnotationType fileAnnotationType = (FileAnnotationType)theEObject;
				T1 result = caseFileAnnotationType(fileAnnotationType);
				if (result == null) result = caseFileTypeFacet(fileAnnotationType);
				if (result == null) result = caseAnnotationType(fileAnnotationType);
				if (result == null) result = caseType(fileAnnotationType);
				if (result == null) result = caseDocumentableElement(fileAnnotationType);
				if (result == null) result = caseIdentifiableElement(fileAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.OBJECT_ANNOTATION_TYPE: {
				ObjectAnnotationType objectAnnotationType = (ObjectAnnotationType)theEObject;
				T1 result = caseObjectAnnotationType(objectAnnotationType);
				if (result == null) result = caseObjectTypeFacet(objectAnnotationType);
				if (result == null) result = caseAnnotationType(objectAnnotationType);
				if (result == null) result = caseType(objectAnnotationType);
				if (result == null) result = caseDocumentableElement(objectAnnotationType);
				if (result == null) result = caseIdentifiableElement(objectAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ARRAY_ANNOTATION_TYPE: {
				ArrayAnnotationType arrayAnnotationType = (ArrayAnnotationType)theEObject;
				T1 result = caseArrayAnnotationType(arrayAnnotationType);
				if (result == null) result = caseArrayTypeFacet(arrayAnnotationType);
				if (result == null) result = caseAnnotationType(arrayAnnotationType);
				if (result == null) result = caseType(arrayAnnotationType);
				if (result == null) result = caseDocumentableElement(arrayAnnotationType);
				if (result == null) result = caseIdentifiableElement(arrayAnnotationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ANY_TYPE: {
				AnyType anyType = (AnyType)theEObject;
				T1 result = caseAnyType(anyType);
				if (result == null) result = caseDataType(anyType);
				if (result == null) result = caseAnnotatable(anyType);
				if (result == null) result = caseDocumentableElement(anyType);
				if (result == null) result = caseIdentifiableElement(anyType);
				if (result == null) result = caseEnumFacet(anyType);
				if (result == null) result = caseType(anyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ANNOTATABLE: {
				Annotatable annotatable = (Annotatable)theEObject;
				T1 result = caseAnnotatable(annotatable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.OBJECT_TYPE: {
				ObjectType objectType = (ObjectType)theEObject;
				T1 result = caseObjectType(objectType);
				if (result == null) result = caseAnyType(objectType);
				if (result == null) result = caseObjectTypeFacet(objectType);
				if (result == null) result = caseDataType(objectType);
				if (result == null) result = caseAnnotatable(objectType);
				if (result == null) result = caseDocumentableElement(objectType);
				if (result == null) result = caseIdentifiableElement(objectType);
				if (result == null) result = caseEnumFacet(objectType);
				if (result == null) result = caseType(objectType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ARRAY_TYPE: {
				ArrayType arrayType = (ArrayType)theEObject;
				T1 result = caseArrayType(arrayType);
				if (result == null) result = caseAnyType(arrayType);
				if (result == null) result = caseArrayTypeFacet(arrayType);
				if (result == null) result = caseDataType(arrayType);
				if (result == null) result = caseAnnotatable(arrayType);
				if (result == null) result = caseDocumentableElement(arrayType);
				if (result == null) result = caseIdentifiableElement(arrayType);
				if (result == null) result = caseEnumFacet(arrayType);
				if (result == null) result = caseType(arrayType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.STRING_TYPE: {
				StringType stringType = (StringType)theEObject;
				T1 result = caseStringType(stringType);
				if (result == null) result = caseAnyType(stringType);
				if (result == null) result = caseStringTypeFacet(stringType);
				if (result == null) result = caseDataType(stringType);
				if (result == null) result = caseAnnotatable(stringType);
				if (result == null) result = caseDocumentableElement(stringType);
				if (result == null) result = caseIdentifiableElement(stringType);
				if (result == null) result = caseEnumFacet(stringType);
				if (result == null) result = caseType(stringType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.NUMBER_TYPE: {
				NumberType numberType = (NumberType)theEObject;
				T1 result = caseNumberType(numberType);
				if (result == null) result = caseAnyType(numberType);
				if (result == null) result = caseNumberTypeFacet(numberType);
				if (result == null) result = caseDataType(numberType);
				if (result == null) result = caseAnnotatable(numberType);
				if (result == null) result = caseDocumentableElement(numberType);
				if (result == null) result = caseIdentifiableElement(numberType);
				if (result == null) result = caseEnumFacet(numberType);
				if (result == null) result = caseType(numberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INTEGER_TYPE: {
				IntegerType integerType = (IntegerType)theEObject;
				T1 result = caseIntegerType(integerType);
				if (result == null) result = caseNumberType(integerType);
				if (result == null) result = caseAnyType(integerType);
				if (result == null) result = caseNumberTypeFacet(integerType);
				if (result == null) result = caseDataType(integerType);
				if (result == null) result = caseAnnotatable(integerType);
				if (result == null) result = caseDocumentableElement(integerType);
				if (result == null) result = caseIdentifiableElement(integerType);
				if (result == null) result = caseEnumFacet(integerType);
				if (result == null) result = caseType(integerType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.BOOLEAN_TYPE: {
				BooleanType booleanType = (BooleanType)theEObject;
				T1 result = caseBooleanType(booleanType);
				if (result == null) result = caseAnyType(booleanType);
				if (result == null) result = caseDataType(booleanType);
				if (result == null) result = caseAnnotatable(booleanType);
				if (result == null) result = caseDocumentableElement(booleanType);
				if (result == null) result = caseIdentifiableElement(booleanType);
				if (result == null) result = caseEnumFacet(booleanType);
				if (result == null) result = caseType(booleanType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DATE_ONLY_TYPE: {
				DateOnlyType dateOnlyType = (DateOnlyType)theEObject;
				T1 result = caseDateOnlyType(dateOnlyType);
				if (result == null) result = caseAnyType(dateOnlyType);
				if (result == null) result = caseDataType(dateOnlyType);
				if (result == null) result = caseAnnotatable(dateOnlyType);
				if (result == null) result = caseDocumentableElement(dateOnlyType);
				if (result == null) result = caseIdentifiableElement(dateOnlyType);
				if (result == null) result = caseEnumFacet(dateOnlyType);
				if (result == null) result = caseType(dateOnlyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TIME_ONLY_TYPE: {
				TimeOnlyType timeOnlyType = (TimeOnlyType)theEObject;
				T1 result = caseTimeOnlyType(timeOnlyType);
				if (result == null) result = caseAnyType(timeOnlyType);
				if (result == null) result = caseDataType(timeOnlyType);
				if (result == null) result = caseAnnotatable(timeOnlyType);
				if (result == null) result = caseDocumentableElement(timeOnlyType);
				if (result == null) result = caseIdentifiableElement(timeOnlyType);
				if (result == null) result = caseEnumFacet(timeOnlyType);
				if (result == null) result = caseType(timeOnlyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DATE_TIME_ONLY_TYPE: {
				DateTimeOnlyType dateTimeOnlyType = (DateTimeOnlyType)theEObject;
				T1 result = caseDateTimeOnlyType(dateTimeOnlyType);
				if (result == null) result = caseAnyType(dateTimeOnlyType);
				if (result == null) result = caseDataType(dateTimeOnlyType);
				if (result == null) result = caseAnnotatable(dateTimeOnlyType);
				if (result == null) result = caseDocumentableElement(dateTimeOnlyType);
				if (result == null) result = caseIdentifiableElement(dateTimeOnlyType);
				if (result == null) result = caseEnumFacet(dateTimeOnlyType);
				if (result == null) result = caseType(dateTimeOnlyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DATE_TIME_TYPE: {
				DateTimeType dateTimeType = (DateTimeType)theEObject;
				T1 result = caseDateTimeType(dateTimeType);
				if (result == null) result = caseAnyType(dateTimeType);
				if (result == null) result = caseDateTimeTypeFacet(dateTimeType);
				if (result == null) result = caseDataType(dateTimeType);
				if (result == null) result = caseAnnotatable(dateTimeType);
				if (result == null) result = caseDocumentableElement(dateTimeType);
				if (result == null) result = caseIdentifiableElement(dateTimeType);
				if (result == null) result = caseEnumFacet(dateTimeType);
				if (result == null) result = caseType(dateTimeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.FILE_TYPE: {
				FileType fileType = (FileType)theEObject;
				T1 result = caseFileType(fileType);
				if (result == null) result = caseAnyType(fileType);
				if (result == null) result = caseFileTypeFacet(fileType);
				if (result == null) result = caseDataType(fileType);
				if (result == null) result = caseAnnotatable(fileType);
				if (result == null) result = caseDocumentableElement(fileType);
				if (result == null) result = caseIdentifiableElement(fileType);
				if (result == null) result = caseEnumFacet(fileType);
				if (result == null) result = caseType(fileType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.NIL_TYPE: {
				NilType nilType = (NilType)theEObject;
				T1 result = caseNilType(nilType);
				if (result == null) result = caseAnyType(nilType);
				if (result == null) result = caseDataType(nilType);
				if (result == null) result = caseAnnotatable(nilType);
				if (result == null) result = caseDocumentableElement(nilType);
				if (result == null) result = caseIdentifiableElement(nilType);
				if (result == null) result = caseEnumFacet(nilType);
				if (result == null) result = caseType(nilType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INSTANCE: {
				Instance<?> instance = (Instance<?>)theEObject;
				T1 result = caseInstance(instance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.STRING_INSTANCE: {
				StringInstance stringInstance = (StringInstance)theEObject;
				T1 result = caseStringInstance(stringInstance);
				if (result == null) result = caseInstance(stringInstance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.BOOLEAN_INSTANCE: {
				BooleanInstance booleanInstance = (BooleanInstance)theEObject;
				T1 result = caseBooleanInstance(booleanInstance);
				if (result == null) result = caseInstance(booleanInstance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.NUMBER_INSTANCE: {
				NumberInstance numberInstance = (NumberInstance)theEObject;
				T1 result = caseNumberInstance(numberInstance);
				if (result == null) result = caseInstance(numberInstance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INTEGER_INSTANCE: {
				IntegerInstance integerInstance = (IntegerInstance)theEObject;
				T1 result = caseIntegerInstance(integerInstance);
				if (result == null) result = caseInstance(integerInstance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.OBJECT_INSTANCE: {
				ObjectInstance objectInstance = (ObjectInstance)theEObject;
				T1 result = caseObjectInstance(objectInstance);
				if (result == null) result = caseInstance(objectInstance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Api</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Api</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseApi(Api object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseTypeContainer(TypeContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Library</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Library</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseLibrary(Library object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Library Use</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Library Use</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseLibraryUse(LibraryUse object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T> T1 caseType(Type<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDataType(DataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAnnotationType(AnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAnyAnnotationType(AnyAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Time Only Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Time Only Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseTimeOnlyAnnotationType(TimeOnlyAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Time Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Time Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDateTimeAnnotationType(DateTimeAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Time Only Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Time Only Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDateTimeOnlyAnnotationType(DateTimeOnlyAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Only Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Only Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDateOnlyAnnotationType(DateOnlyAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Number Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Number Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseNumberAnnotationType(NumberAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseIntegerAnnotationType(IntegerAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseBooleanAnnotationType(BooleanAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStringAnnotationType(StringAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Nil Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Nil Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseNilAnnotationType(NilAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFileAnnotationType(FileAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseObjectAnnotationType(ObjectAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseArrayAnnotationType(ArrayAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAnyType(AnyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Annotatable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotatable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAnnotatable(Annotatable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseObjectType(ObjectType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseArrayType(ArrayType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStringType(StringType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Number Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Number Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseNumberType(NumberType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseIntegerType(IntegerType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseBooleanType(BooleanType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Only Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Only Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDateOnlyType(DateOnlyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Time Only Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Time Only Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseTimeOnlyType(TimeOnlyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Time Only Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Time Only Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDateTimeOnlyType(DateTimeOnlyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Time Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Time Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDateTimeType(DateTimeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFileType(FileType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Nil Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Nil Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseNilType(NilType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends AnyType> T1 caseInstance(Instance<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStringInstance(StringInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseBooleanInstance(BooleanInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Number Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Number Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseNumberInstance(NumberInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseIntegerInstance(IntegerInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseObjectInstance(ObjectInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Documentable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Documentable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDocumentableElement(DocumentableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identifiable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifiable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseIdentifiableElement(IdentifiableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Time Type Facet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Time Type Facet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDateTimeTypeFacet(DateTimeTypeFacet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Number Type Facet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Number Type Facet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseNumberTypeFacet(NumberTypeFacet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Type Facet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Type Facet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStringTypeFacet(StringTypeFacet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Type Facet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Type Facet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFileTypeFacet(FileTypeFacet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Type Facet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Type Facet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T, P extends Property<T>> T1 caseObjectTypeFacet(ObjectTypeFacet<T, P> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Type Facet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Type Facet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T> T1 caseArrayTypeFacet(ArrayTypeFacet<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Facet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Facet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEnumFacet(EnumFacet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T1 defaultCase(EObject object) {
		return null;
	}

} //TypesSwitch
