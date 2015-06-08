/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delta</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getActivity <em>Activity</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getOnAttribute <em>On Attribute</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getTargetValue <em>Target Value</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getSourceValue <em>Source Value</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getChange <em>Change</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getDelta()
 * @model
 * @generated
 */
public interface Delta extends EObject {
	/**
	 * Returns the value of the '<em><b>Activity</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getDeltas <em>Deltas</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activity</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity</em>' container reference.
	 * @see #setActivity(Activity)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getDelta_Activity()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getDeltas
	 * @model opposite="deltas" required="true" transient="false"
	 * @generated
	 */
	Activity getActivity();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getActivity <em>Activity</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity</em>' container reference.
	 * @see #getActivity()
	 * @generated
	 */
	void setActivity(Activity value);

	/**
	 * Returns the value of the '<em><b>On Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Attribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Attribute</em>' reference.
	 * @see #setOnAttribute(Attribute)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getDelta_OnAttribute()
	 * @model required="true"
	 * @generated
	 */
	Attribute getOnAttribute();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getOnAttribute <em>On Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>On Attribute</em>' reference.
	 * @see #getOnAttribute()
	 * @generated
	 */
	void setOnAttribute(Attribute value);

	/**
	 * Returns the value of the '<em><b>Target Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Value</em>' reference.
	 * @see #setTargetValue(Value)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getDelta_TargetValue()
	 * @model required="true"
	 * @generated
	 */
	Value getTargetValue();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getTargetValue <em>Target Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Value</em>' reference.
	 * @see #getTargetValue()
	 * @generated
	 */
	void setTargetValue(Value value);

	/**
	 * Returns the value of the '<em><b>Source Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Value</em>' reference.
	 * @see #setSourceValue(Value)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getDelta_SourceValue()
	 * @model required="true"
	 * @generated
	 */
	Value getSourceValue();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getSourceValue <em>Source Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Value</em>' reference.
	 * @see #getSourceValue()
	 * @generated
	 */
	void setSourceValue(Value value);

	/**
	 * Returns the value of the '<em><b>Change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change</em>' attribute.
	 * @see #setChange(double)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getDelta_Change()
	 * @model
	 * @generated
	 */
	double getChange();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getChange <em>Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Change</em>' attribute.
	 * @see #getChange()
	 * @generated
	 */
	void setChange(double value);

} // Delta
