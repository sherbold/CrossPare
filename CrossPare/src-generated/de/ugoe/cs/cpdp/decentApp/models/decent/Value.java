/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Value#getName <em>Name</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Value#getOfAttribute <em>Of Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getValue()
 * @model abstract="true"
 * @generated
 */
public interface Value extends EObject {
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
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getValue_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Value#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Of Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Of Attribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Of Attribute</em>' reference.
	 * @see #setOfAttribute(Attribute)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getValue_OfAttribute()
	 * @model required="true"
	 * @generated
	 */
	Attribute getOfAttribute();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Value#getOfAttribute <em>Of Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of Attribute</em>' reference.
	 * @see #getOfAttribute()
	 * @generated
	 */
	void setOfAttribute(Attribute value);

} // Value
