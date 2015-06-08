/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Agent</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getEMail <em>EMail</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getProjects <em>Projects</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getActivities <em>Activities</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getStates <em>States</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgent()
 * @model
 * @generated
 */
public interface Agent extends Element {
	/**
	 * Returns the value of the '<em><b>EMail</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EMail</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EMail</em>' attribute.
	 * @see #setEMail(String)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgent_EMail()
	 * @model required="true"
	 * @generated
	 */
	String getEMail();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getEMail <em>EMail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EMail</em>' attribute.
	 * @see #getEMail()
	 * @generated
	 */
	void setEMail(String value);

	/**
	 * Returns the value of the '<em><b>Projects</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Project#getAgents <em>Agents</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Projects</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projects</em>' reference.
	 * @see #setProjects(Project)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgent_Projects()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Project#getAgents
	 * @model opposite="agents" required="true"
	 * @generated
	 */
	Project getProjects();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getProjects <em>Projects</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Projects</em>' reference.
	 * @see #getProjects()
	 * @generated
	 */
	void setProjects(Project value);

	/**
	 * Returns the value of the '<em><b>Activities</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgent <em>Agent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activities</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgent_Activities()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgent
	 * @model opposite="agent" containment="true"
	 * @generated
	 */
	EList<Activity> getActivities();

	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getAgent <em>Agent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>States</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAgent_States()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getAgent
	 * @model opposite="agent" containment="true"
	 * @generated
	 */
	EList<AgentState> getStates();

} // Agent
