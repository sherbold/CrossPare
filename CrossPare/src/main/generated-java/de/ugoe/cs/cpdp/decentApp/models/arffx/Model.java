/**
 */
package de.ugoe.cs.cpdp.decentApp.models.arffx;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Model#getName <em>Name</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Model#getMeta <em>Meta</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Model#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Model#getData <em>Data</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Model#getDimensions <em>Dimensions</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Model#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getModel()
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
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getModel_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Model#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Meta</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Meta</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta</em>' map.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getModel_Meta()
	 * @model mapType="ARFFx.MetaData<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getMeta();

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getModel_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Attribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Data</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.arffx.Instance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getModel_Data()
	 * @model containment="true"
	 * @generated
	 */
	EList<Instance> getData();

	/**
	 * Returns the value of the '<em><b>Dimensions</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.arffx.Dimension}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dimensions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimensions</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getModel_Dimensions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Dimension> getDimensions();

	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link de.ugoe.cs.cpdp.decentApp.models.arffx.Type}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage#getModel_Types()
	 * @model containment="true"
	 * @generated
	 */
	EList<Type> getTypes();

} // Model
