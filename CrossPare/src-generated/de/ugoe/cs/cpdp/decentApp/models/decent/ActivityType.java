/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType#getDimension <em>Dimension</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivityType()
 * @model
 * @generated
 */
public interface ActivityType extends Element {
	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Dimension}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dimension</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivityType_Dimension()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Dimension> getDimension();

} // ActivityType
