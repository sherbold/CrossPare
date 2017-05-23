/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>String List Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.StringListValue#getContent <em>Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getStringListValue()
 * @model
 * @generated
 */
public interface StringListValue extends Value {
	/**
	 * Returns the value of the '<em><b>Content</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' attribute list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getStringListValue_Content()
	 * @model
	 * @generated
	 */
	EList<String> getContent();

} // StringListValue
