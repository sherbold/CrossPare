/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Project#getAgents <em>Agents</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Project#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends Element {
	/**
	 * Returns the value of the '<em><b>Agents</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getProjects <em>Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agents</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agents</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getProject_Agents()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getProjects
	 * @model opposite="projects"
	 * @generated
	 */
	EList<Agent> getAgents();

	/**
	 * Returns the value of the '<em><b>Location</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Location}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getProject_Location()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Location#getProject
	 * @model opposite="project" containment="true"
	 * @generated
	 */
	EList<Location> getLocation();

} // Project
