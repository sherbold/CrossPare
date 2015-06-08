/**
 */
package de.ugoe.cs.cpdp.decentApp.models.arffx;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Value#getOfAttribute <em>Of Attribute</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Value#getContent <em>Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getValue()
 * @model
 * @generated
 */
public interface Value extends EObject {
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
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getValue_OfAttribute()
	 * @model
	 * @generated
	 */
	Attribute getOfAttribute();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Value#getOfAttribute <em>Of Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of Attribute</em>' reference.
	 * @see #getOfAttribute()
	 * @generated
	 */
	void setOfAttribute(Attribute value);

	/**
	 * Returns the value of the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' attribute.
	 * @see #setContent(String)
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getValue_Content()
	 * @model
	 * @generated
	 */
	String getContent();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Value#getContent <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' attribute.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(String value);

} // Value
