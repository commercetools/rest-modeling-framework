/**
 */
package io.vrap.rmf.raml.model.elements;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see io.vrap.rmf.raml.model.elements.ElementsFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelDirectory='/raml-model/src/main/xcore-gen' complianceLevel='8.0' basePackage='io.vrap.rmf.raml.model'"
 * @generated
 */
public interface ElementsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "elements";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.vrap.io/raml/elements";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "elements";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ElementsPackage eINSTANCE = io.vrap.rmf.raml.model.elements.impl.ElementsPackageImpl.init();

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.elements.IdentifiableElement <em>Identifiable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.elements.IdentifiableElement
	 * @see io.vrap.rmf.raml.model.elements.impl.ElementsPackageImpl#getIdentifiableElement()
	 * @generated
	 */
	int IDENTIFIABLE_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Identifiable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Identifiable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.elements.DocumentableElement <em>Documentable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.elements.DocumentableElement
	 * @see io.vrap.rmf.raml.model.elements.impl.ElementsPackageImpl#getDocumentableElement()
	 * @generated
	 */
	int DOCUMENTABLE_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTABLE_ELEMENT__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTABLE_ELEMENT__DISPLAY_NAME = 1;

	/**
	 * The number of structural features of the '<em>Documentable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTABLE_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Documentable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTABLE_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.vrap.rmf.raml.model.elements.TypedElement <em>Typed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.vrap.rmf.raml.model.elements.TypedElement
	 * @see io.vrap.rmf.raml.model.elements.impl.ElementsPackageImpl#getTypedElement()
	 * @generated
	 */
	int TYPED_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Type Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__TYPE_REFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Type Declaration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__TYPE_DECLARATION = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__TYPE = 2;

	/**
	 * The number of structural features of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.elements.IdentifiableElement <em>Identifiable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identifiable Element</em>'.
	 * @see io.vrap.rmf.raml.model.elements.IdentifiableElement
	 * @generated
	 */
	EClass getIdentifiableElement();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.elements.IdentifiableElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see io.vrap.rmf.raml.model.elements.IdentifiableElement#getName()
	 * @see #getIdentifiableElement()
	 * @generated
	 */
	EAttribute getIdentifiableElement_Name();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.elements.DocumentableElement <em>Documentable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Documentable Element</em>'.
	 * @see io.vrap.rmf.raml.model.elements.DocumentableElement
	 * @generated
	 */
	EClass getDocumentableElement();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.elements.DocumentableElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see io.vrap.rmf.raml.model.elements.DocumentableElement#getDescription()
	 * @see #getDocumentableElement()
	 * @generated
	 */
	EAttribute getDocumentableElement_Description();

	/**
	 * Returns the meta object for the attribute '{@link io.vrap.rmf.raml.model.elements.DocumentableElement#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see io.vrap.rmf.raml.model.elements.DocumentableElement#getDisplayName()
	 * @see #getDocumentableElement()
	 * @generated
	 */
	EAttribute getDocumentableElement_DisplayName();

	/**
	 * Returns the meta object for class '{@link io.vrap.rmf.raml.model.elements.TypedElement <em>Typed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Element</em>'.
	 * @see io.vrap.rmf.raml.model.elements.TypedElement
	 * @generated
	 */
	EClass getTypedElement();

	/**
	 * Returns the meta object for the reference '{@link io.vrap.rmf.raml.model.elements.TypedElement#getTypeReference <em>Type Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type Reference</em>'.
	 * @see io.vrap.rmf.raml.model.elements.TypedElement#getTypeReference()
	 * @see #getTypedElement()
	 * @generated
	 */
	EReference getTypedElement_TypeReference();

	/**
	 * Returns the meta object for the containment reference '{@link io.vrap.rmf.raml.model.elements.TypedElement#getTypeDeclaration <em>Type Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Declaration</em>'.
	 * @see io.vrap.rmf.raml.model.elements.TypedElement#getTypeDeclaration()
	 * @see #getTypedElement()
	 * @generated
	 */
	EReference getTypedElement_TypeDeclaration();

	/**
	 * Returns the meta object for the reference '{@link io.vrap.rmf.raml.model.elements.TypedElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see io.vrap.rmf.raml.model.elements.TypedElement#getType()
	 * @see #getTypedElement()
	 * @generated
	 */
	EReference getTypedElement_Type();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ElementsFactory getElementsFactory();

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
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.elements.IdentifiableElement <em>Identifiable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.elements.IdentifiableElement
		 * @see io.vrap.rmf.raml.model.elements.impl.ElementsPackageImpl#getIdentifiableElement()
		 * @generated
		 */
		EClass IDENTIFIABLE_ELEMENT = eINSTANCE.getIdentifiableElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFIABLE_ELEMENT__NAME = eINSTANCE.getIdentifiableElement_Name();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.elements.DocumentableElement <em>Documentable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.elements.DocumentableElement
		 * @see io.vrap.rmf.raml.model.elements.impl.ElementsPackageImpl#getDocumentableElement()
		 * @generated
		 */
		EClass DOCUMENTABLE_ELEMENT = eINSTANCE.getDocumentableElement();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENTABLE_ELEMENT__DESCRIPTION = eINSTANCE.getDocumentableElement_Description();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENTABLE_ELEMENT__DISPLAY_NAME = eINSTANCE.getDocumentableElement_DisplayName();

		/**
		 * The meta object literal for the '{@link io.vrap.rmf.raml.model.elements.TypedElement <em>Typed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.vrap.rmf.raml.model.elements.TypedElement
		 * @see io.vrap.rmf.raml.model.elements.impl.ElementsPackageImpl#getTypedElement()
		 * @generated
		 */
		EClass TYPED_ELEMENT = eINSTANCE.getTypedElement();

		/**
		 * The meta object literal for the '<em><b>Type Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPED_ELEMENT__TYPE_REFERENCE = eINSTANCE.getTypedElement_TypeReference();

		/**
		 * The meta object literal for the '<em><b>Type Declaration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPED_ELEMENT__TYPE_DECLARATION = eINSTANCE.getTypedElement_TypeDeclaration();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPED_ELEMENT__TYPE = eINSTANCE.getTypedElement_Type();

	}

} //ElementsPackage
