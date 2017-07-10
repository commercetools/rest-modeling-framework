/**
 */
package io.vrap.rmf.raml.model.facets.util;

import io.vrap.rmf.raml.model.facets.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see io.vrap.rmf.raml.model.facets.FacetsPackage
 * @generated
 */
public class FacetsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static FacetsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FacetsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = FacetsPackage.eINSTANCE;
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
	protected FacetsSwitch<Adapter> modelSwitch =
		new FacetsSwitch<Adapter>() {
			@Override
			public Adapter caseEnumFacet(EnumFacet object) {
				return createEnumFacetAdapter();
			}
			@Override
			public <T, P extends Property<T>> Adapter caseObjectTypeFacet(ObjectTypeFacet<T, P> object) {
				return createObjectTypeFacetAdapter();
			}
			@Override
			public <T> Adapter caseProperty(Property<T> object) {
				return createPropertyAdapter();
			}
			@Override
			public <T> Adapter caseArrayTypeFacet(ArrayTypeFacet<T> object) {
				return createArrayTypeFacetAdapter();
			}
			@Override
			public Adapter caseStringTypeFacet(StringTypeFacet object) {
				return createStringTypeFacetAdapter();
			}
			@Override
			public Adapter caseNumberTypeFacet(NumberTypeFacet object) {
				return createNumberTypeFacetAdapter();
			}
			@Override
			public Adapter caseDateTimeTypeFacet(DateTimeTypeFacet object) {
				return createDateTimeTypeFacetAdapter();
			}
			@Override
			public Adapter caseFileTypeFacet(FileTypeFacet object) {
				return createFileTypeFacetAdapter();
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
	 * Creates a new adapter for an object of class '{@link io.vrap.rmf.raml.model.facets.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see io.vrap.rmf.raml.model.facets.Property
	 * @generated
	 */
	public Adapter createPropertyAdapter() {
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

} //FacetsAdapterFactory
