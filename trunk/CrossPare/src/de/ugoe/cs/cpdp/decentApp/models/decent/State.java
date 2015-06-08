/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getArtifact <em>Artifact</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getActivity <em>Activity</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getFromActivity <em>From Activity</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getNext <em>Next</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getPrevious <em>Previous</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getParent <em>Parent</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getChildren <em>Children</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getState()
 * @model
 * @generated
 */
public interface State extends Element {
	/**
	 * Returns the value of the '<em><b>Artifact</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact</em>' container reference.
	 * @see #setArtifact(Artifact)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getState_Artifact()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getStates
	 * @model opposite="states" required="true" transient="false"
	 * @generated
	 */
	Artifact getArtifact();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getArtifact <em>Artifact</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact</em>' container reference.
	 * @see #getArtifact()
	 * @generated
	 */
	void setArtifact(Artifact value);

	/**
	 * Returns the value of the '<em><b>Activity</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activity</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getState_Activity()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getState
	 * @model opposite="state"
	 * @generated
	 */
	EList<Activity> getActivity();

	/**
	 * Returns the value of the '<em><b>From Activity</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getTargetState <em>Target State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From Activity</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From Activity</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getState_FromActivity()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getTargetState
	 * @model opposite="targetState"
	 * @generated
	 */
	EList<Activity> getFromActivity();

	/**
	 * Returns the value of the '<em><b>Next</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.State}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getPrevious <em>Previous</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Next</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getState_Next()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getPrevious
	 * @model opposite="previous"
	 * @generated
	 */
	EList<State> getNext();

	/**
	 * Returns the value of the '<em><b>Previous</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.State}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getNext <em>Next</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Previous</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Previous</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getState_Previous()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getNext
	 * @model opposite="next"
	 * @generated
	 */
	EList<State> getPrevious();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(State)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getState_Parent()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getChildren
	 * @model opposite="children"
	 * @generated
	 */
	State getParent();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(State value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.State}.
	 * It is bidirectional and its opposite is '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getState_Children()
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getParent
	 * @model opposite="parent"
	 * @generated
	 */
	EList<State> getChildren();

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
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getState_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<Value> getValues();

} // State
