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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see io.vrap.rmf.raml.model.types.TypesPackage
 * @generated
 */
public class TypesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TypesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TypesPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypesSwitch<Adapter> modelSwitch =
		new TypesSwitch<Adapter>() {
			@Override
			public Adapter caseApi(Api object) {
				return createApiAdapter();
			}
			@Override
			public Adapter caseTypeContainer(TypeContainer object) {
				return createTypeContainerAdapter();
			}
			@Override
			public Adapter caseLibrary(Library object) {
				return createLibraryAdapter();
			}
			@Override
			public Adapter caseLibraryUse(LibraryUse object) {
				return createLibraryUseAdapter();
			}
			@Override
			public <T> Adapter caseType(Type<T> object) {
				return createTypeAdapter();
			}
			@Override
			public Adapter caseDataType(DataType object) {
				return createDataTypeAdapter();
			}
			@Override
			public Adapter caseAnnotationType(AnnotationType object) {
				return createAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseAnyAnnotationType(AnyAnnotationType object) {
				return createAnyAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseTimeOnlyAnnotationType(TimeOnlyAnnotationType object) {
				return createTimeOnlyAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseDateTimeAnnotationType(DateTimeAnnotationType object) {
				return createDateTimeAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseDateTimeOnlyAnnotationType(DateTimeOnlyAnnotationType object) {
				return createDateTimeOnlyAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseDateOnlyAnnotationType(DateOnlyAnnotationType object) {
				return createDateOnlyAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseNumberAnnotationType(NumberAnnotationType object) {
				return createNumberAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseIntegerAnnotationType(IntegerAnnotationType object) {
				return createIntegerAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseBooleanAnnotationType(BooleanAnnotationType object) {
				return createBooleanAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseStringAnnotationType(StringAnnotationType object) {
				return createStringAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseNilAnnotationType(NilAnnotationType object) {
				return createNilAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseFileAnnotationType(FileAnnotationType object) {
				return createFileAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseObjectAnnotationType(ObjectAnnotationType object) {
				return createObjectAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseArrayAnnotationType(ArrayAnnotationType object) {
				return createArrayAnnotationTypeAdapter();
			}
			@Override
			public Adapter caseAnyType(AnyType object) {
				return createAnyTypeAdapter();
			}
			@Override
			public Adapter caseAnnotatable(Annotatable object) {
				return createAnnotatableAdapter();
			}
			@Override
			public Adapter caseObjectType(ObjectType object) {
				return createObjectTypeAdapter();
			}
			@Override
			public Adapter caseArrayType(ArrayType object) {
				return createArrayTypeAdapter();
			}
			@Override
			public Adapter caseStringType(StringType object) {
				return createStringTypeAdapter();
			}
			@Override
			public Adapter caseNumberType(NumberType object) {
				return createNumberTypeAdapter();
			}
			@Override
			public Adapter caseIntegerType(IntegerType object) {
				return createIntegerTypeAdapter();
			}
			@Override
			public Adapter caseBooleanType(BooleanType object) {
				return createBooleanTypeAdapter();
			}
			@Override
			public Adapter caseDateOnlyType(DateOnlyType object) {
				return createDateOnlyTypeAdapter();
			}
			@Override
			public Adapter caseTimeOnlyType(TimeOnlyType object) {
				return createTimeOnlyTypeAdapter();
			}
			@Override
			public Adapter caseDateTimeOnlyType(DateTimeOnlyType object) {
				return createDateTimeOnlyTypeAdapter();
			}
			@Override
			public Adapter caseDateTimeType(DateTimeType object) {
				return createDateTimeTypeAdapter();
			}
			@Override
			public Adapter caseFileType(FileType object) {
				return createFileTypeAdapter();
			}
			@Override
			public Adapter caseNilType(NilType object) {
				return createNilTypeAdapter();
			}
			@Override
			public <T extends AnyType> Adapter caseInstance(Instance<T> object) {
				return createInstanceAdapter();
			}
			@Override
			public Adapter caseStringInstance(StringInstance object) {
				return createStringInstanceAdapter();
			}
			@Override
			public Adapter caseBooleanInstance(BooleanInstance object) {
				return createBooleanInstanceAdapter();
			}
			@Override
			public Adapter caseNumberInstance(NumberInstance object) {
				return createNumberInstanceAdapter();
			}
			@Override
			public Adapter caseIntegerInstance(IntegerInstance object) {
				return createIntegerInstanceAdapter();
			}
			@Override
			public Adapter caseObjectInstance(ObjectInstance object) {
				return createObjectInstanceAdapter();
			}
			@Override
			public Adapter caseDocumentableElement(DocumentableElement object) {
				return createDocumentableElementAdapter();
			}
			@Override
			public Adapter caseIdentifiableElement(IdentifiableElement object) {
				return createIdentifiableElementAdapter();
			}
			@Override
			public Adapter caseDateTimeTypeFacet(DateTimeTypeFacet object) {
				return createDateTimeTypeFacetAdapter();
			}
			@Override
			public Adapter caseNumberTypeFacet(NumberTypeFacet object) {
				return createNumberTypeFacetAdapter();
			}
			@Override
			public Adapter caseStringTypeFacet(StringTypeFacet object) {
				return createStringTypeFacetAdapter();
			}
			@Override
			public Adapter caseFileTypeFacet(FileTypeFacet object) {
				return createFileTypeFacetAdapter();
			}
			@Override
			public <T, P extends Property<T>> Adapter caseObjectTypeFacet(ObjectTypeFacet<T, P> object) {
				return createObjectTypeFacetAdapter();
			}
			@Override
			public <T> Adapter caseArrayTypeFacet(ArrayTypeFacet<T> object) {
				return createArrayTypeFacetAdapter();
			}
			@Override
			public Adapter caseEnumFacet(EnumFacet object) {
				return createEnumFacetAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.Api <em>Api</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.Api
	 * @generated
	 */
	public Adapter createApiAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.TypeContainer <em>Type Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.TypeContainer
	 * @generated
	 */
	public Adapter createTypeContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.Library <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.Library
	 * @generated
	 */
	public Adapter createLibraryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.LibraryUse <em>Library Use</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.LibraryUse
	 * @generated
	 */
	public Adapter createLibraryUseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.Type
	 * @generated
	 */
	public Adapter createTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.DataType
	 * @generated
	 */
	public Adapter createDataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.AnnotationType <em>Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.AnnotationType
	 * @generated
	 */
	public Adapter createAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.AnyAnnotationType <em>Any Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.AnyAnnotationType
	 * @generated
	 */
	public Adapter createAnyAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.TimeOnlyAnnotationType <em>Time Only Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.TimeOnlyAnnotationType
	 * @generated
	 */
	public Adapter createTimeOnlyAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.DateTimeAnnotationType <em>Date Time Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.DateTimeAnnotationType
	 * @generated
	 */
	public Adapter createDateTimeAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.DateTimeOnlyAnnotationType <em>Date Time Only Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.DateTimeOnlyAnnotationType
	 * @generated
	 */
	public Adapter createDateTimeOnlyAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.DateOnlyAnnotationType <em>Date Only Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.DateOnlyAnnotationType
	 * @generated
	 */
	public Adapter createDateOnlyAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.NumberAnnotationType <em>Number Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.NumberAnnotationType
	 * @generated
	 */
	public Adapter createNumberAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.IntegerAnnotationType <em>Integer Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.IntegerAnnotationType
	 * @generated
	 */
	public Adapter createIntegerAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.BooleanAnnotationType <em>Boolean Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.BooleanAnnotationType
	 * @generated
	 */
	public Adapter createBooleanAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.StringAnnotationType <em>String Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.StringAnnotationType
	 * @generated
	 */
	public Adapter createStringAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.NilAnnotationType <em>Nil Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.NilAnnotationType
	 * @generated
	 */
	public Adapter createNilAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.FileAnnotationType <em>File Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.FileAnnotationType
	 * @generated
	 */
	public Adapter createFileAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.ObjectAnnotationType <em>Object Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.ObjectAnnotationType
	 * @generated
	 */
	public Adapter createObjectAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.ArrayAnnotationType <em>Array Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.ArrayAnnotationType
	 * @generated
	 */
	public Adapter createArrayAnnotationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.AnyType <em>Any Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.AnyType
	 * @generated
	 */
	public Adapter createAnyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.Annotatable <em>Annotatable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.Annotatable
	 * @generated
	 */
	public Adapter createAnnotatableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.ObjectType <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.ObjectType
	 * @generated
	 */
	public Adapter createObjectTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.ArrayType <em>Array Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.ArrayType
	 * @generated
	 */
	public Adapter createArrayTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.StringType <em>String Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.StringType
	 * @generated
	 */
	public Adapter createStringTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.NumberType <em>Number Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.NumberType
	 * @generated
	 */
	public Adapter createNumberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.IntegerType <em>Integer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.IntegerType
	 * @generated
	 */
	public Adapter createIntegerTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.BooleanType <em>Boolean Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.BooleanType
	 * @generated
	 */
	public Adapter createBooleanTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.DateOnlyType <em>Date Only Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.DateOnlyType
	 * @generated
	 */
	public Adapter createDateOnlyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.TimeOnlyType <em>Time Only Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.TimeOnlyType
	 * @generated
	 */
	public Adapter createTimeOnlyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.DateTimeOnlyType <em>Date Time Only Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.DateTimeOnlyType
	 * @generated
	 */
	public Adapter createDateTimeOnlyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.DateTimeType <em>Date Time Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.DateTimeType
	 * @generated
	 */
	public Adapter createDateTimeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.FileType <em>File Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.FileType
	 * @generated
	 */
	public Adapter createFileTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.NilType <em>Nil Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.NilType
	 * @generated
	 */
	public Adapter createNilTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.Instance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.Instance
	 * @generated
	 */
	public Adapter createInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.StringInstance <em>String Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.StringInstance
	 * @generated
	 */
	public Adapter createStringInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.BooleanInstance <em>Boolean Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.BooleanInstance
	 * @generated
	 */
	public Adapter createBooleanInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.NumberInstance <em>Number Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.NumberInstance
	 * @generated
	 */
	public Adapter createNumberInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.IntegerInstance <em>Integer Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.IntegerInstance
	 * @generated
	 */
	public Adapter createIntegerInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.types.ObjectInstance <em>Object Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.types.ObjectInstance
	 * @generated
	 */
	public Adapter createObjectInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.elements.DocumentableElement <em>Documentable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.elements.DocumentableElement
	 * @generated
	 */
	public Adapter createDocumentableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.elements.IdentifiableElement <em>Identifiable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.elements.IdentifiableElement
	 * @generated
	 */
	public Adapter createIdentifiableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.facets.DateTimeTypeFacet <em>Date Time Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.facets.DateTimeTypeFacet
	 * @generated
	 */
	public Adapter createDateTimeTypeFacetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.facets.NumberTypeFacet <em>Number Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.facets.NumberTypeFacet
	 * @generated
	 */
	public Adapter createNumberTypeFacetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.facets.StringTypeFacet <em>String Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.facets.StringTypeFacet
	 * @generated
	 */
	public Adapter createStringTypeFacetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.facets.FileTypeFacet <em>File Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.facets.FileTypeFacet
	 * @generated
	 */
	public Adapter createFileTypeFacetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.facets.ObjectTypeFacet <em>Object Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.facets.ObjectTypeFacet
	 * @generated
	 */
	public Adapter createObjectTypeFacetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.facets.ArrayTypeFacet <em>Array Type Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.facets.ArrayTypeFacet
	 * @generated
	 */
	public Adapter createArrayTypeFacetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.facets.EnumFacet <em>Enum Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.facets.EnumFacet
	 * @generated
	 */
	public Adapter createEnumFacetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //TypesAdapterFactory
