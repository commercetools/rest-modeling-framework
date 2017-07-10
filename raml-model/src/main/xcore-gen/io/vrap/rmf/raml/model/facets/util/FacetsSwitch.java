/**
 */
package io.vrap.rmf.raml.model.facets.util;

import io.vrap.rmf.raml.model.facets.*;

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
 * @see io.vrap.rmf.raml.model.facets.FacetsPackage
 * @generated
 */
public class FacetsSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static FacetsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FacetsSwitch() {
		if (modelPackage == null) {
			modelPackage = FacetsPackage.eINSTANCE;
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
			case FacetsPackage.ENUM_FACET: {
				EnumFacet enumFacet = (EnumFacet)theEObject;
				T1 result = caseEnumFacet(enumFacet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FacetsPackage.OBJECT_TYPE_FACET: {
				ObjectTypeFacet<?, ?> objectTypeFacet = (ObjectTypeFacet<?, ?>)theEObject;
				T1 result = caseObjectTypeFacet(objectTypeFacet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FacetsPackage.PROPERTY: {
				Property<?> property = (Property<?>)theEObject;
				T1 result = caseProperty(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FacetsPackage.ARRAY_TYPE_FACET: {
				ArrayTypeFacet<?> arrayTypeFacet = (ArrayTypeFacet<?>)theEObject;
				T1 result = caseArrayTypeFacet(arrayTypeFacet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FacetsPackage.STRING_TYPE_FACET: {
				StringTypeFacet stringTypeFacet = (StringTypeFacet)theEObject;
				T1 result = caseStringTypeFacet(stringTypeFacet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FacetsPackage.NUMBER_TYPE_FACET: {
				NumberTypeFacet numberTypeFacet = (NumberTypeFacet)theEObject;
				T1 result = caseNumberTypeFacet(numberTypeFacet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FacetsPackage.DATE_TIME_TYPE_FACET: {
				DateTimeTypeFacet dateTimeTypeFacet = (DateTimeTypeFacet)theEObject;
				T1 result = caseDateTimeTypeFacet(dateTimeTypeFacet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FacetsPackage.FILE_TYPE_FACET: {
				FileTypeFacet fileTypeFacet = (FileTypeFacet)theEObject;
				T1 result = caseFileTypeFacet(fileTypeFacet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
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
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T> T1 caseProperty(Property<T> object) {
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

} //FacetsSwitch
