/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EDataType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getArtifactTypes <em>Artifact Types</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getType <em>Type</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getDimension <em>Dimension</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends Element {
	/**
	 * Returns the value of the '<em><b>Artifact Types</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Types</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Types</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAttribute_ArtifactTypes()
	 * @model ordered="false"
	 * @generated
	 */
	EList<ArtifactType> getArtifactTypes();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EDataType)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAttribute_Type()
	 * @model
	 * @generated
	 */
	EDataType getType();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EDataType value);

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
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAttribute_Dimension()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Dimension> getDimension();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getAttribute_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

} // Attribute
