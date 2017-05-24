/**
 */
package de.ugoe.cs.cpdp.decentApp.models.arffx;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute#getName <em>Name</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute#getType <em>Type</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute#getDimension <em>Dimension</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getAttribute_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Type)
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getAttribute_Type()
	 * @model
	 * @generated
	 */
	Type getType();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Type value);

	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dimension</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' reference.
	 * @see #setDimension(Dimension)
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getAttribute_Dimension()
	 * @model
	 * @generated
	 */
	Dimension getDimension();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute#getDimension <em>Dimension</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dimension</em>' reference.
	 * @see #getDimension()
	 * @generated
	 */
	void setDimension(Dimension value);

} // Attribute
