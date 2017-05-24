/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getName <em>Name</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getContent <em>Content</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getSteps <em>Steps</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getProjects <em>Projects</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getArtifactTypeHierarchy <em>Artifact Type Hierarchy</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getAgentPool <em>Agent Pool</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getAttributePool <em>Attribute Pool</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getActivityTypes <em>Activity Types</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getDimensions <em>Dimensions</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getModel()
 * @model
 * @generated
 */
public interface Model extends EObject {
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
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getModel_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getModel_Content()
	 * @model
	 * @generated
	 */
	EList<String> getContent();

	/**
	 * Returns the value of the '<em><b>Steps</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Step}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Steps</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Steps</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getModel_Steps()
	 * @model containment="true"
	 * @generated
	 */
	EList<Step> getSteps();

	/**
	 * Returns the value of the '<em><b>Projects</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Project}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Projects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projects</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getModel_Projects()
	 * @model containment="true"
	 * @generated
	 */
	EList<Project> getProjects();

	/**
	 * Returns the value of the '<em><b>Artifact Type Hierarchy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Type Hierarchy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Type Hierarchy</em>' containment reference.
	 * @see #setArtifactTypeHierarchy(ArtifactTypeHierarchy)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getModel_ArtifactTypeHierarchy()
	 * @model containment="true"
	 * @generated
	 */
	ArtifactTypeHierarchy getArtifactTypeHierarchy();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getArtifactTypeHierarchy <em>Artifact Type Hierarchy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Type Hierarchy</em>' containment reference.
	 * @see #getArtifactTypeHierarchy()
	 * @generated
	 */
	void setArtifactTypeHierarchy(ArtifactTypeHierarchy value);

	/**
	 * Returns the value of the '<em><b>Agent Pool</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agent Pool</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agent Pool</em>' containment reference.
	 * @see #setAgentPool(AgentPool)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getModel_AgentPool()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AgentPool getAgentPool();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getAgentPool <em>Agent Pool</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agent Pool</em>' containment reference.
	 * @see #getAgentPool()
	 * @generated
	 */
	void setAgentPool(AgentPool value);

	/**
	 * Returns the value of the '<em><b>Attribute Pool</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Pool</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Pool</em>' containment reference.
	 * @see #setAttributePool(AttributePool)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getModel_AttributePool()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AttributePool getAttributePool();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getAttributePool <em>Attribute Pool</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Pool</em>' containment reference.
	 * @see #getAttributePool()
	 * @generated
	 */
	void setAttributePool(AttributePool value);

	/**
	 * Returns the value of the '<em><b>Activity Types</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activity Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Types</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getModel_ActivityTypes()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ActivityType> getActivityTypes();

	/**
	 * Returns the value of the '<em><b>Dimensions</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Dimension}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dimensions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimensions</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getModel_Dimensions()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Dimension> getDimensions();

} // Model
