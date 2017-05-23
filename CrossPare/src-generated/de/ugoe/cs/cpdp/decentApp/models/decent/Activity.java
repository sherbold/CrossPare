/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getType <em>Type</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgent <em>Agent</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getState <em>State</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getTargetState <em>Target State</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getDeltas <em>Deltas</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getDate <em>Date</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getValues <em>Values</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgentState <em>Agent State</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivity()
 * @model
 * @generated
 */
public interface Activity extends Element {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivity_Type()
	 * @model
	 * @generated
	 */
	EList<ActivityType> getType();

	/**
	 * Returns the value of the '<em><b>Agent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getActivities <em>Activities</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agent</em>' container reference.
	 * @see #setAgent(Agent)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivity_Agent()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getActivities
	 * @model opposite="activities" required="true" transient="false"
	 * @generated
	 */
	Agent getAgent();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgent <em>Agent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agent</em>' container reference.
	 * @see #getAgent()
	 * @generated
	 */
	void setAgent(Agent value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getActivity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' reference.
	 * @see #setState(State)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivity_State()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getActivity
	 * @model opposite="activity" required="true"
	 * @generated
	 */
	State getState();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getState <em>State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' reference.
	 * @see #getState()
	 * @generated
	 */
	void setState(State value);

	/**
	 * Returns the value of the '<em><b>Target State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getFromActivity <em>From Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target State</em>' reference.
	 * @see #setTargetState(State)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivity_TargetState()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getFromActivity
	 * @model opposite="fromActivity" required="true"
	 * @generated
	 */
	State getTargetState();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getTargetState <em>Target State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target State</em>' reference.
	 * @see #getTargetState()
	 * @generated
	 */
	void setTargetState(State value);

	/**
	 * Returns the value of the '<em><b>Deltas</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getActivity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deltas</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deltas</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivity_Deltas()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getActivity
	 * @model opposite="activity" containment="true"
	 * @generated
	 */
	EList<Delta> getDeltas();

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(Date)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivity_Date()
	 * @model
	 * @generated
	 */
	Date getDate();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(Date value);

	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Value}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivity_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<Value> getValues();

	/**
	 * Returns the value of the '<em><b>Agent State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getActivities <em>Activities</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agent State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agent State</em>' reference.
	 * @see #setAgentState(AgentState)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getActivity_AgentState()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getActivities
	 * @model opposite="activities"
	 * @generated
	 */
	AgentState getAgentState();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgentState <em>Agent State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agent State</em>' reference.
	 * @see #getAgentState()
	 * @generated
	 */
	void setAgentState(AgentState value);

} // Activity
