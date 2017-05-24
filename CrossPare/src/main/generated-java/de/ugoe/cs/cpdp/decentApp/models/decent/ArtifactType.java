/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artifact Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType#getContainerTypes <em>Container Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getArtifactType()
 * @model
 * @generated
 */
public interface ArtifactType extends Element {
	/**
	 * Returns the value of the '<em><b>Container Types</b></em>' reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Container Types</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container Types</em>' reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#getArtifactType_ContainerTypes()
	 * @model
	 * @generated
	 */
	EList<ArtifactType> getContainerTypes();

} // ArtifactType
