/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Agent State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getDate <em>Date</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getAgent <em>Agent</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getActivities <em>Activities</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getNext <em>Next</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getPrevious <em>Previous</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgentState()
 * @model
 * @generated
 */
public interface AgentState extends Element {
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
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgentState_Date()
	 * @model
	 * @generated
	 */
	Date getDate();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(Date value);

	/**
	 * Returns the value of the '<em><b>Agent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agent</em>' container reference.
	 * @see #setAgent(Agent)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgentState_Agent()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getStates
	 * @model opposite="states" required="true" transient="false"
	 * @generated
	 */
	Agent getAgent();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getAgent <em>Agent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agent</em>' container reference.
	 * @see #getAgent()
	 * @generated
	 */
	void setAgent(Agent value);

	/**
	 * Returns the value of the '<em><b>Activities</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgentState <em>Agent State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activities</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activities</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgentState_Activities()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgentState
	 * @model opposite="agentState"
	 * @generated
	 */
	EList<Activity> getActivities();

	/**
	 * Returns the value of the '<em><b>Next</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getPrevious <em>Previous</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Next</em>' reference.
	 * @see #setNext(AgentState)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgentState_Next()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getPrevious
	 * @model opposite="previous"
	 * @generated
	 */
	AgentState getNext();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getNext <em>Next</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Next</em>' reference.
	 * @see #getNext()
	 * @generated
	 */
	void setNext(AgentState value);

	/**
	 * Returns the value of the '<em><b>Previous</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getNext <em>Next</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Previous</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Previous</em>' reference.
	 * @see #setPrevious(AgentState)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgentState_Previous()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getNext
	 * @model opposite="next"
	 * @generated
	 */
	AgentState getPrevious();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getPrevious <em>Previous</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Previous</em>' reference.
	 * @see #getPrevious()
	 * @generated
	 */
	void setPrevious(AgentState value);

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
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgentState_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<Value> getValues();

} // AgentState
