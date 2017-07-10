/**
 */
package io.vrap.rmf.raml.model.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Annotation Target</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * The location within an API specificaxtion where annotations can be applied MUST be one of the target locations
 * in the following Target Locations table. The targets are the locations themselves, not sub-properties within the locations.
 * For example, the Method target refers to the method node, not to the method display name, description, and so on.
 * <!-- end-model-doc -->
 * @see io.vrap.rmf.raml.model.types.TypesPackage#getAnnotationTarget()
 * @model
 * @generated
 */
public enum AnnotationTarget implements Enumerator {
	/**
	 * The '<em><b>API</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #API_VALUE
	 * @generated
	 * @ordered
	 */
	API(0, "API", "API"),

	/**
	 * The '<em><b>Documentation Item</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOCUMENTATION_ITEM_VALUE
	 * @generated
	 * @ordered
	 */
	DOCUMENTATION_ITEM(0, "DocumentationItem", "DocumentationItem"),

	/**
	 * The '<em><b>Resource</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	RESOURCE(0, "Resource", "Resource"),

	/**
	 * The '<em><b>Method</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHOD_VALUE
	 * @generated
	 * @ordered
	 */
	METHOD(0, "Method", "Method"),

	/**
	 * The '<em><b>Response</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESPONSE_VALUE
	 * @generated
	 * @ordered
	 */
	RESPONSE(0, "Response", "Response"),

	/**
	 * The '<em><b>Request Body</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REQUEST_BODY_VALUE
	 * @generated
	 * @ordered
	 */
	REQUEST_BODY(0, "RequestBody", "RequestBody"),

	/**
	 * The '<em><b>Response Body</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESPONSE_BODY_VALUE
	 * @generated
	 * @ordered
	 */
	RESPONSE_BODY(0, "ResponseBody", "ResponseBody"),

	/**
	 * The '<em><b>Type Declaration</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TYPE_DECLARATION_VALUE
	 * @generated
	 * @ordered
	 */
	TYPE_DECLARATION(0, "TypeDeclaration", "TypeDeclaration"),

	/**
	 * The '<em><b>Example</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXAMPLE_VALUE
	 * @generated
	 * @ordered
	 */
	EXAMPLE(0, "Example", "Example"),

	/**
	 * The '<em><b>Resource Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESOURCE_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	RESOURCE_TYPE(0, "ResourceType", "ResourceType"),

	/**
	 * The '<em><b>Trait</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRAIT_VALUE
	 * @generated
	 * @ordered
	 */
	TRAIT(0, "Trait", "Trait"),

	/**
	 * The '<em><b>Security Scheme</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SECURITY_SCHEME_VALUE
	 * @generated
	 * @ordered
	 */
	SECURITY_SCHEME(0, "SecurityScheme", "SecurityScheme"),

	/**
	 * The '<em><b>Security Scheme Settings</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SECURITY_SCHEME_SETTINGS_VALUE
	 * @generated
	 * @ordered
	 */
	SECURITY_SCHEME_SETTINGS(0, "SecuritySchemeSettings", "SecuritySchemeSettings"),

	/**
	 * The '<em><b>Annotation Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ANNOTATION_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	ANNOTATION_TYPE(0, "AnnotationType", "AnnotationType"),

	/**
	 * The '<em><b>Library</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	LIBRARY(0, "Library", "Library"),

	/**
	 * The '<em><b>Overlay</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OVERLAY_VALUE
	 * @generated
	 * @ordered
	 */
	OVERLAY(0, "Overlay", "Overlay"),

	/**
	 * The '<em><b>Extension</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	EXTENSION(0, "Extension", "Extension");

	/**
	 * The '<em><b>API</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The root of a RAML document
	 * <!-- end-model-doc -->
	 * @see #API
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int API_VALUE = 0;

	/**
	 * The '<em><b>Documentation Item</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An item in the collection of items that is the value of the root-level documentation node.
	 * <!-- end-model-doc -->
	 * @see #DOCUMENTATION_ITEM
	 * @model name="DocumentationItem"
	 * @generated
	 * @ordered
	 */
	public static final int DOCUMENTATION_ITEM_VALUE = 0;

	/**
	 * The '<em><b>Resource</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A resource (relative URI) node, root-level or nested
	 * <!-- end-model-doc -->
	 * @see #RESOURCE
	 * @model name="Resource"
	 * @generated
	 * @ordered
	 */
	public static final int RESOURCE_VALUE = 0;

	/**
	 * The '<em><b>Method</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A method node.
	 * <!-- end-model-doc -->
	 * @see #METHOD
	 * @model name="Method"
	 * @generated
	 * @ordered
	 */
	public static final int METHOD_VALUE = 0;

	/**
	 * The '<em><b>Response</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A declaration of the responses node, whose key is an HTTP status code.
	 * <!-- end-model-doc -->
	 * @see #RESPONSE
	 * @model name="Response"
	 * @generated
	 * @ordered
	 */
	public static final int RESPONSE_VALUE = 0;

	/**
	 * The '<em><b>Request Body</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The body node of a method.
	 * <!-- end-model-doc -->
	 * @see #REQUEST_BODY
	 * @model name="RequestBody"
	 * @generated
	 * @ordered
	 */
	public static final int REQUEST_BODY_VALUE = 0;

	/**
	 * The '<em><b>Response Body</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The body node of a response.
	 * <!-- end-model-doc -->
	 * @see #RESPONSE_BODY
	 * @model name="ResponseBody"
	 * @generated
	 * @ordered
	 */
	public static final int RESPONSE_BODY_VALUE = 0;

	/**
	 * The '<em><b>Type Declaration</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A data type declaration (inline or in a global types collection), header declaration,
	 * query parameter declaration, URI parameter declaration, or a property within any of these declarations,
	 * where the type property can be used.
	 * <!-- end-model-doc -->
	 * @see #TYPE_DECLARATION
	 * @model name="TypeDeclaration"
	 * @generated
	 * @ordered
	 */
	public static final int TYPE_DECLARATION_VALUE = 0;

	/**
	 * The '<em><b>Example</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Either an example or examples node
	 * <!-- end-model-doc -->
	 * @see #EXAMPLE
	 * @model name="Example"
	 * @generated
	 * @ordered
	 */
	public static final int EXAMPLE_VALUE = 0;

	/**
	 * The '<em><b>Resource Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A resource type node.
	 * <!-- end-model-doc -->
	 * @see #RESOURCE_TYPE
	 * @model name="ResourceType"
	 * @generated
	 * @ordered
	 */
	public static final int RESOURCE_TYPE_VALUE = 0;

	/**
	 * The '<em><b>Trait</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A trait node.
	 * <!-- end-model-doc -->
	 * @see #TRAIT
	 * @model name="Trait"
	 * @generated
	 * @ordered
	 */
	public static final int TRAIT_VALUE = 0;

	/**
	 * The '<em><b>Security Scheme</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A security scheme declaration.
	 * <!-- end-model-doc -->
	 * @see #SECURITY_SCHEME
	 * @model name="SecurityScheme"
	 * @generated
	 * @ordered
	 */
	public static final int SECURITY_SCHEME_VALUE = 0;

	/**
	 * The '<em><b>Security Scheme Settings</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The settings node of a security scheme declaration
	 * <!-- end-model-doc -->
	 * @see #SECURITY_SCHEME_SETTINGS
	 * @model name="SecuritySchemeSettings"
	 * @generated
	 * @ordered
	 */
	public static final int SECURITY_SCHEME_SETTINGS_VALUE = 0;

	/**
	 * The '<em><b>Annotation Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A declaration of the annotationTypes node, whose key is a name of an annotation type and whose value describes the annotation.
	 * <!-- end-model-doc -->
	 * @see #ANNOTATION_TYPE
	 * @model name="AnnotationType"
	 * @generated
	 * @ordered
	 */
	public static final int ANNOTATION_TYPE_VALUE = 0;

	/**
	 * The '<em><b>Library</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The root of a library.
	 * <!-- end-model-doc -->
	 * @see #LIBRARY
	 * @model name="Library"
	 * @generated
	 * @ordered
	 */
	public static final int LIBRARY_VALUE = 0;

	/**
	 * The '<em><b>Overlay</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The root of an overlay.
	 * <!-- end-model-doc -->
	 * @see #OVERLAY
	 * @model name="Overlay"
	 * @generated
	 * @ordered
	 */
	public static final int OVERLAY_VALUE = 0;

	/**
	 * The '<em><b>Extension</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The root of an extension.
	 * <!-- end-model-doc -->
	 * @see #EXTENSION
	 * @model name="Extension"
	 * @generated
	 * @ordered
	 */
	public static final int EXTENSION_VALUE = 0;

	/**
	 * An array of all the '<em><b>Annotation Target</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AnnotationTarget[] VALUES_ARRAY =
		new AnnotationTarget[] {
			API,
			DOCUMENTATION_ITEM,
			RESOURCE,
			METHOD,
			RESPONSE,
			REQUEST_BODY,
			RESPONSE_BODY,
			TYPE_DECLARATION,
			EXAMPLE,
			RESOURCE_TYPE,
			TRAIT,
			SECURITY_SCHEME,
			SECURITY_SCHEME_SETTINGS,
			ANNOTATION_TYPE,
			LIBRARY,
			OVERLAY,
			EXTENSION,
		};

	/**
	 * A public read-only list of all the '<em><b>Annotation Target</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AnnotationTarget> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Annotation Target</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AnnotationTarget get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AnnotationTarget result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Annotation Target</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AnnotationTarget getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AnnotationTarget result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Annotation Target</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AnnotationTarget get(int value) {
		switch (value) {
			case API_VALUE: return API;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private AnnotationTarget(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //AnnotationTarget
