/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Location</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location#getType <em>Type</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location#getProject <em>Project</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location#getArtifacts <em>Artifacts</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getLocation()
 * @model
 * @generated
 */
public interface Location extends Element {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(ArtifactType)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getLocation_Type()
	 * @model
	 * @generated
	 */
	ArtifactType getType();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ArtifactType value);

	/**
	 * Returns the value of the '<em><b>Project</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Project#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' container reference.
	 * @see #setProject(Project)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getLocation_Project()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Project#getLocation
	 * @model opposite="location" required="true" transient="false"
	 * @generated
	 */
	Project getProject();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location#getProject <em>Project</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' container reference.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(Project value);

	/**
	 * Returns the value of the '<em><b>Artifacts</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Artifact}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifacts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifacts</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getLocation_Artifacts()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Artifact> getArtifacts();

} // Location
