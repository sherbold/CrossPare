/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/OCL/Import ecore='http://www.eclipse.org/emf/2002/Ecore#/'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface DECENTPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "DECENT";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://decent/3.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "DECENT";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DECENTPackage eINSTANCE = de.ugoe.cs.cpdp.decentApp.models.decent.impl.DECENTPackageImpl.init();

	/**
	 * The meta object id for the '{@link DECENT.impl.ModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.ModelImpl
	 * @see DECENT.impl.DECENTPackageImpl#getModel()
	 * @generated
	 */
	int MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__CONTENT = 1;

	/**
	 * The feature id for the '<em><b>Steps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__STEPS = 2;

	/**
	 * The feature id for the '<em><b>Projects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__PROJECTS = 3;

	/**
	 * The feature id for the '<em><b>Artifact Type Hierarchy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__ARTIFACT_TYPE_HIERARCHY = 4;

	/**
	 * The feature id for the '<em><b>Agent Pool</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__AGENT_POOL = 5;

	/**
	 * The feature id for the '<em><b>Attribute Pool</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__ATTRIBUTE_POOL = 6;

	/**
	 * The feature id for the '<em><b>Activity Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__ACTIVITY_TYPES = 7;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__DIMENSIONS = 8;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FEATURE_COUNT = 9;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.ElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.ElementImpl
	 * @see DECENT.impl.DECENTPackageImpl#getElement()
	 * @generated
	 */
	int ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__ID = 1;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.StepImpl <em>Step</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.StepImpl
	 * @see DECENT.impl.DECENTPackageImpl#getStep()
	 * @generated
	 */
	int STEP = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEP__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEP__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEP__DURATION = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEP_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEP_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.AgentPoolImpl <em>Agent Pool</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.AgentPoolImpl
	 * @see DECENT.impl.DECENTPackageImpl#getAgentPool()
	 * @generated
	 */
	int AGENT_POOL = 3;

	/**
	 * The feature id for the '<em><b>Agents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_POOL__AGENTS = 0;

	/**
	 * The number of structural features of the '<em>Agent Pool</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_POOL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Agent Pool</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_POOL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.AttributePoolImpl <em>Attribute Pool</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.AttributePoolImpl
	 * @see DECENT.impl.DECENTPackageImpl#getAttributePool()
	 * @generated
	 */
	int ATTRIBUTE_POOL = 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_POOL__ATTRIBUTES = 0;

	/**
	 * The number of structural features of the '<em>Attribute Pool</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_POOL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Attribute Pool</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_POOL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.ArtifactTypeHierarchyImpl <em>Artifact Type Hierarchy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.ArtifactTypeHierarchyImpl
	 * @see DECENT.impl.DECENTPackageImpl#getArtifactTypeHierarchy()
	 * @generated
	 */
	int ARTIFACT_TYPE_HIERARCHY = 5;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_TYPE_HIERARCHY__TYPES = 0;

	/**
	 * The number of structural features of the '<em>Artifact Type Hierarchy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_TYPE_HIERARCHY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Artifact Type Hierarchy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_TYPE_HIERARCHY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.ArtifactTypeImpl <em>Artifact Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.ArtifactTypeImpl
	 * @see DECENT.impl.DECENTPackageImpl#getArtifactType()
	 * @generated
	 */
	int ARTIFACT_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_TYPE__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_TYPE__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Container Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_TYPE__CONTAINER_TYPES = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Artifact Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_TYPE_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Artifact Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_TYPE_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.ProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.ProjectImpl
	 * @see DECENT.impl.DECENTPackageImpl#getProject()
	 * @generated
	 */
	int PROJECT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Agents</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__AGENTS = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__LOCATION = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.AgentImpl <em>Agent</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.AgentImpl
	 * @see DECENT.impl.DECENTPackageImpl#getAgent()
	 * @generated
	 */
	int AGENT = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>EMail</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT__EMAIL = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Projects</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT__PROJECTS = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Activities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT__ACTIVITIES = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT__STATES = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Agent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Agent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.AgentStateImpl <em>Agent State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.AgentStateImpl
	 * @see DECENT.impl.DECENTPackageImpl#getAgentState()
	 * @generated
	 */
	int AGENT_STATE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_STATE__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_STATE__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_STATE__DATE = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Agent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_STATE__AGENT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Activities</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_STATE__ACTIVITIES = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Next</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_STATE__NEXT = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Previous</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_STATE__PREVIOUS = ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_STATE__VALUES = ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Agent State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_STATE_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Agent State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_STATE_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.LocationImpl <em>Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.LocationImpl
	 * @see DECENT.impl.DECENTPackageImpl#getLocation()
	 * @generated
	 */
	int LOCATION = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__TYPE = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Project</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__PROJECT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Artifacts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__ARTIFACTS = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.ArtifactImpl <em>Artifact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.ArtifactImpl
	 * @see DECENT.impl.DECENTPackageImpl#getArtifact()
	 * @generated
	 */
	int ARTIFACT = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__TYPE = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__PARENT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__CHILDREN = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__STATES = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__ATTRIBUTES = ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Artifact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Artifact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.StateImpl
	 * @see DECENT.impl.DECENTPackageImpl#getState()
	 * @generated
	 */
	int STATE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Artifact</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ARTIFACT = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Activity</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ACTIVITY = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>From Activity</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__FROM_ACTIVITY = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Next</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NEXT = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Previous</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PREVIOUS = ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PARENT = ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__CHILDREN = ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__VALUES = ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.ActivityImpl <em>Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.ActivityImpl
	 * @see DECENT.impl.DECENTPackageImpl#getActivity()
	 * @generated
	 */
	int ACTIVITY = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__TYPE = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Agent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__AGENT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__STATE = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Target State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__TARGET_STATE = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Deltas</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__DELTAS = ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__DATE = ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__VALUES = ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Agent State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__AGENT_STATE = ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.ActivityTypeImpl <em>Activity Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.ActivityTypeImpl
	 * @see DECENT.impl.DECENTPackageImpl#getActivityType()
	 * @generated
	 */
	int ACTIVITY_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_TYPE__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_TYPE__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_TYPE__DIMENSION = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Activity Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_TYPE_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Activity Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_TYPE_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.DimensionImpl
	 * @see DECENT.impl.DECENTPackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__ID = ELEMENT__ID;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.AttributeImpl
	 * @see DECENT.impl.DECENTPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__ID = ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Artifact Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__ARTIFACT_TYPES = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYPE = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__DIMENSION = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__DESCRIPTION = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.ValueImpl <em>Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.ValueImpl
	 * @see DECENT.impl.DECENTPackageImpl#getValue()
	 * @generated
	 */
	int VALUE = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Of Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE__OF_ATTRIBUTE = 1;

	/**
	 * The number of structural features of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.IntegerValueImpl <em>Integer Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.IntegerValueImpl
	 * @see DECENT.impl.DECENTPackageImpl#getIntegerValue()
	 * @generated
	 */
	int INTEGER_VALUE = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE__NAME = VALUE__NAME;

	/**
	 * The feature id for the '<em><b>Of Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE__OF_ATTRIBUTE = VALUE__OF_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE__CONTENT = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Integer Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.DoubleValueImpl <em>Double Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.DoubleValueImpl
	 * @see DECENT.impl.DECENTPackageImpl#getDoubleValue()
	 * @generated
	 */
	int DOUBLE_VALUE = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE__NAME = VALUE__NAME;

	/**
	 * The feature id for the '<em><b>Of Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE__OF_ATTRIBUTE = VALUE__OF_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE__CONTENT = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Double Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.StringValueImpl <em>String Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.StringValueImpl
	 * @see DECENT.impl.DECENTPackageImpl#getStringValue()
	 * @generated
	 */
	int STRING_VALUE = 20;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE__NAME = VALUE__NAME;

	/**
	 * The feature id for the '<em><b>Of Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE__OF_ATTRIBUTE = VALUE__OF_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE__CONTENT = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>String Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.ListValueImpl <em>List Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.ListValueImpl
	 * @see DECENT.impl.DECENTPackageImpl#getListValue()
	 * @generated
	 */
	int LIST_VALUE = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_VALUE__NAME = VALUE__NAME;

	/**
	 * The feature id for the '<em><b>Of Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_VALUE__OF_ATTRIBUTE = VALUE__OF_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_VALUE__CONTENT = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>List Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>List Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.StringListValueImpl <em>String List Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.StringListValueImpl
	 * @see DECENT.impl.DECENTPackageImpl#getStringListValue()
	 * @generated
	 */
	int STRING_LIST_VALUE = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LIST_VALUE__NAME = VALUE__NAME;

	/**
	 * The feature id for the '<em><b>Of Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LIST_VALUE__OF_ATTRIBUTE = VALUE__OF_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LIST_VALUE__CONTENT = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String List Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LIST_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>String List Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LIST_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.DoubleListValueImpl <em>Double List Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.DoubleListValueImpl
	 * @see DECENT.impl.DECENTPackageImpl#getDoubleListValue()
	 * @generated
	 */
	int DOUBLE_LIST_VALUE = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LIST_VALUE__NAME = VALUE__NAME;

	/**
	 * The feature id for the '<em><b>Of Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LIST_VALUE__OF_ATTRIBUTE = VALUE__OF_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LIST_VALUE__CONTENT = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double List Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LIST_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Double List Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LIST_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.IntegerListValueImpl <em>Integer List Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.IntegerListValueImpl
	 * @see DECENT.impl.DECENTPackageImpl#getIntegerListValue()
	 * @generated
	 */
	int INTEGER_LIST_VALUE = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LIST_VALUE__NAME = VALUE__NAME;

	/**
	 * The feature id for the '<em><b>Of Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LIST_VALUE__OF_ATTRIBUTE = VALUE__OF_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LIST_VALUE__CONTENT = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer List Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LIST_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Integer List Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LIST_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DECENT.impl.DeltaImpl <em>Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DECENT.impl.DeltaImpl
	 * @see DECENT.impl.DECENTPackageImpl#getDelta()
	 * @generated
	 */
	int DELTA = 25;

	/**
	 * The feature id for the '<em><b>Activity</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA__ACTIVITY = 0;

	/**
	 * The feature id for the '<em><b>On Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA__ON_ATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Target Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA__TARGET_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Source Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA__SOURCE_VALUE = 3;

	/**
	 * The feature id for the '<em><b>Change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA__CHANGE = 4;

	/**
	 * The number of structural features of the '<em>Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model
	 * @generated
	 */
	EClass getModel();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model#getName()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Name();

	/**
	 * Returns the meta object for the attribute list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Content</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model#getContent()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Content();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getSteps <em>Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Steps</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model#getSteps()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Steps();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getProjects <em>Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Projects</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model#getProjects()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Projects();

	/**
	 * Returns the meta object for the containment reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getArtifactTypeHierarchy <em>Artifact Type Hierarchy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Artifact Type Hierarchy</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model#getArtifactTypeHierarchy()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_ArtifactTypeHierarchy();

	/**
	 * Returns the meta object for the containment reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getAgentPool <em>Agent Pool</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Agent Pool</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model#getAgentPool()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_AgentPool();

	/**
	 * Returns the meta object for the containment reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getAttributePool <em>Attribute Pool</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Pool</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model#getAttributePool()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_AttributePool();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getActivityTypes <em>Activity Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Activity Types</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model#getActivityTypes()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_ActivityTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model#getDimensions <em>Dimensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dimensions</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model#getDimensions()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Dimensions();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Element <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Element
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Element#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Element#getName()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Element#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Element#getID()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_ID();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Step <em>Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Step</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Step
	 * @generated
	 */
	EClass getStep();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Step#getDuration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Duration</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Step#getDuration()
	 * @see #getStep()
	 * @generated
	 */
	EAttribute getStep_Duration();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentPool <em>Agent Pool</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agent Pool</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentPool
	 * @generated
	 */
	EClass getAgentPool();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentPool#getAgents <em>Agents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Agents</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentPool#getAgents()
	 * @see #getAgentPool()
	 * @generated
	 */
	EReference getAgentPool_Agents();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AttributePool <em>Attribute Pool</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Pool</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AttributePool
	 * @generated
	 */
	EClass getAttributePool();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AttributePool#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AttributePool#getAttributes()
	 * @see #getAttributePool()
	 * @generated
	 */
	EReference getAttributePool_Attributes();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactTypeHierarchy <em>Artifact Type Hierarchy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Artifact Type Hierarchy</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactTypeHierarchy
	 * @generated
	 */
	EClass getArtifactTypeHierarchy();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactTypeHierarchy#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactTypeHierarchy#getTypes()
	 * @see #getArtifactTypeHierarchy()
	 * @generated
	 */
	EReference getArtifactTypeHierarchy_Types();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType <em>Artifact Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Artifact Type</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType
	 * @generated
	 */
	EClass getArtifactType();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType#getContainerTypes <em>Container Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Container Types</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType#getContainerTypes()
	 * @see #getArtifactType()
	 * @generated
	 */
	EReference getArtifactType_ContainerTypes();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Project
	 * @generated
	 */
	EClass getProject();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Project#getAgents <em>Agents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Agents</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Project#getAgents()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Agents();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Project#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Location</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Project#getLocation()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Location();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent <em>Agent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agent</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Agent
	 * @generated
	 */
	EClass getAgent();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getEMail <em>EMail</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EMail</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getEMail()
	 * @see #getAgent()
	 * @generated
	 */
	EAttribute getAgent_EMail();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getProjects <em>Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Projects</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getProjects()
	 * @see #getAgent()
	 * @generated
	 */
	EReference getAgent_Projects();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getActivities <em>Activities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Activities</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getActivities()
	 * @see #getAgent()
	 * @generated
	 */
	EReference getAgent_Activities();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Agent#getStates()
	 * @see #getAgent()
	 * @generated
	 */
	EReference getAgent_States();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState <em>Agent State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agent State</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState
	 * @generated
	 */
	EClass getAgentState();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getDate()
	 * @see #getAgentState()
	 * @generated
	 */
	EAttribute getAgentState_Date();

	/**
	 * Returns the meta object for the container reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getAgent <em>Agent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Agent</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getAgent()
	 * @see #getAgentState()
	 * @generated
	 */
	EReference getAgentState_Agent();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getActivities <em>Activities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Activities</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getActivities()
	 * @see #getAgentState()
	 * @generated
	 */
	EReference getAgentState_Activities();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getNext <em>Next</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Next</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getNext()
	 * @see #getAgentState()
	 * @generated
	 */
	EReference getAgentState_Next();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getPrevious <em>Previous</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Previous</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getPrevious()
	 * @see #getAgentState()
	 * @generated
	 */
	EReference getAgentState_Previous();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState#getValues()
	 * @see #getAgentState()
	 * @generated
	 */
	EReference getAgentState_Values();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Location</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Location
	 * @generated
	 */
	EClass getLocation();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Location#getType()
	 * @see #getLocation()
	 * @generated
	 */
	EReference getLocation_Type();

	/**
	 * Returns the meta object for the container reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Project</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Location#getProject()
	 * @see #getLocation()
	 * @generated
	 */
	EReference getLocation_Project();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location#getArtifacts <em>Artifacts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Artifacts</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Location#getArtifacts()
	 * @see #getLocation()
	 * @generated
	 */
	EReference getLocation_Artifacts();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Artifact <em>Artifact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Artifact</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Artifact
	 * @generated
	 */
	EClass getArtifact();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getType()
	 * @see #getArtifact()
	 * @generated
	 */
	EReference getArtifact_Type();

	/**
	 * Returns the meta object for the container reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getParent()
	 * @see #getArtifact()
	 * @generated
	 */
	EReference getArtifact_Parent();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getChildren()
	 * @see #getArtifact()
	 * @generated
	 */
	EReference getArtifact_Children();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getStates()
	 * @see #getArtifact()
	 * @generated
	 */
	EReference getArtifact_States();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Attributes</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Artifact#getAttributes()
	 * @see #getArtifact()
	 * @generated
	 */
	EReference getArtifact_Attributes();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the container reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getArtifact <em>Artifact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Artifact</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getArtifact()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Artifact();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getActivity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Activity</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getActivity()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Activity();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getFromActivity <em>From Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>From Activity</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getFromActivity()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_FromActivity();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getNext <em>Next</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Next</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getNext()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Next();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getPrevious <em>Previous</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Previous</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getPrevious()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Previous();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getParent()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Parent();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Children</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getChildren()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Children();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State#getValues()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Values();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Activity</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity
	 * @generated
	 */
	EClass getActivity();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Type</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getType()
	 * @see #getActivity()
	 * @generated
	 */
	EReference getActivity_Type();

	/**
	 * Returns the meta object for the container reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgent <em>Agent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Agent</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgent()
	 * @see #getActivity()
	 * @generated
	 */
	EReference getActivity_Agent();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>State</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getState()
	 * @see #getActivity()
	 * @generated
	 */
	EReference getActivity_State();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getTargetState <em>Target State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target State</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getTargetState()
	 * @see #getActivity()
	 * @generated
	 */
	EReference getActivity_TargetState();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getDeltas <em>Deltas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Deltas</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getDeltas()
	 * @see #getActivity()
	 * @generated
	 */
	EReference getActivity_Deltas();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getDate()
	 * @see #getActivity()
	 * @generated
	 */
	EAttribute getActivity_Date();

	/**
	 * Returns the meta object for the containment reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getValues()
	 * @see #getActivity()
	 * @generated
	 */
	EReference getActivity_Values();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgentState <em>Agent State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Agent State</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity#getAgentState()
	 * @see #getActivity()
	 * @generated
	 */
	EReference getActivity_AgentState();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType <em>Activity Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Activity Type</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType
	 * @generated
	 */
	EClass getActivityType();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dimension</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType#getDimension()
	 * @see #getActivityType()
	 * @generated
	 */
	EReference getActivityType_Dimension();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getArtifactTypes <em>Artifact Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Artifact Types</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getArtifactTypes()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_ArtifactTypes();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getType()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_Type();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dimension</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getDimension()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_Dimension();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Attribute#getDescription()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Description();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Value
	 * @generated
	 */
	EClass getValue();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Value#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Value#getName()
	 * @see #getValue()
	 * @generated
	 */
	EAttribute getValue_Name();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Value#getOfAttribute <em>Of Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of Attribute</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Value#getOfAttribute()
	 * @see #getValue()
	 * @generated
	 */
	EReference getValue_OfAttribute();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.IntegerValue <em>Integer Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Value</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.IntegerValue
	 * @generated
	 */
	EClass getIntegerValue();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.IntegerValue#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.IntegerValue#getContent()
	 * @see #getIntegerValue()
	 * @generated
	 */
	EAttribute getIntegerValue_Content();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.DoubleValue <em>Double Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Double Value</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DoubleValue
	 * @generated
	 */
	EClass getDoubleValue();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.DoubleValue#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DoubleValue#getContent()
	 * @see #getDoubleValue()
	 * @generated
	 */
	EAttribute getDoubleValue_Content();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.StringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Value</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.StringValue
	 * @generated
	 */
	EClass getStringValue();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.StringValue#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.StringValue#getContent()
	 * @see #getStringValue()
	 * @generated
	 */
	EAttribute getStringValue_Content();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ListValue <em>List Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Value</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ListValue
	 * @generated
	 */
	EClass getListValue();

	/**
	 * Returns the meta object for the reference list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ListValue#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Content</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ListValue#getContent()
	 * @see #getListValue()
	 * @generated
	 */
	EReference getListValue_Content();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.StringListValue <em>String List Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String List Value</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.StringListValue
	 * @generated
	 */
	EClass getStringListValue();

	/**
	 * Returns the meta object for the attribute list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.StringListValue#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Content</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.StringListValue#getContent()
	 * @see #getStringListValue()
	 * @generated
	 */
	EAttribute getStringListValue_Content();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.DoubleListValue <em>Double List Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Double List Value</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DoubleListValue
	 * @generated
	 */
	EClass getDoubleListValue();

	/**
	 * Returns the meta object for the attribute list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.DoubleListValue#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Content</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DoubleListValue#getContent()
	 * @see #getDoubleListValue()
	 * @generated
	 */
	EAttribute getDoubleListValue_Content();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.IntegerListValue <em>Integer List Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer List Value</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.IntegerListValue
	 * @generated
	 */
	EClass getIntegerListValue();

	/**
	 * Returns the meta object for the attribute list '{@link de.ugoe.cs.cpdp.decentApp.models.decent.IntegerListValue#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Content</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.IntegerListValue#getContent()
	 * @see #getIntegerListValue()
	 * @generated
	 */
	EAttribute getIntegerListValue_Content();

	/**
	 * Returns the meta object for class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta <em>Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delta</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Delta
	 * @generated
	 */
	EClass getDelta();

	/**
	 * Returns the meta object for the container reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getActivity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Activity</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getActivity()
	 * @see #getDelta()
	 * @generated
	 */
	EReference getDelta_Activity();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getOnAttribute <em>On Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>On Attribute</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getOnAttribute()
	 * @see #getDelta()
	 * @generated
	 */
	EReference getDelta_OnAttribute();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getTargetValue <em>Target Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Value</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getTargetValue()
	 * @see #getDelta()
	 * @generated
	 */
	EReference getDelta_TargetValue();

	/**
	 * Returns the meta object for the reference '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getSourceValue <em>Source Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Value</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getSourceValue()
	 * @see #getDelta()
	 * @generated
	 */
	EReference getDelta_SourceValue();

	/**
	 * Returns the meta object for the attribute '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getChange <em>Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Change</em>'.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Delta#getChange()
	 * @see #getDelta()
	 * @generated
	 */
	EAttribute getDelta_Change();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DECENTFactory getDECENTFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link DECENT.impl.ModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.ModelImpl
		 * @see DECENT.impl.DECENTPackageImpl#getModel()
		 * @generated
		 */
		EClass MODEL = eINSTANCE.getModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__NAME = eINSTANCE.getModel_Name();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__CONTENT = eINSTANCE.getModel_Content();

		/**
		 * The meta object literal for the '<em><b>Steps</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__STEPS = eINSTANCE.getModel_Steps();

		/**
		 * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__PROJECTS = eINSTANCE.getModel_Projects();

		/**
		 * The meta object literal for the '<em><b>Artifact Type Hierarchy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__ARTIFACT_TYPE_HIERARCHY = eINSTANCE.getModel_ArtifactTypeHierarchy();

		/**
		 * The meta object literal for the '<em><b>Agent Pool</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__AGENT_POOL = eINSTANCE.getModel_AgentPool();

		/**
		 * The meta object literal for the '<em><b>Attribute Pool</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__ATTRIBUTE_POOL = eINSTANCE.getModel_AttributePool();

		/**
		 * The meta object literal for the '<em><b>Activity Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__ACTIVITY_TYPES = eINSTANCE.getModel_ActivityTypes();

		/**
		 * The meta object literal for the '<em><b>Dimensions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__DIMENSIONS = eINSTANCE.getModel_Dimensions();

		/**
		 * The meta object literal for the '{@link DECENT.impl.ElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.ElementImpl
		 * @see DECENT.impl.DECENTPackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT__NAME = eINSTANCE.getElement_Name();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT__ID = eINSTANCE.getElement_ID();

		/**
		 * The meta object literal for the '{@link DECENT.impl.StepImpl <em>Step</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.StepImpl
		 * @see DECENT.impl.DECENTPackageImpl#getStep()
		 * @generated
		 */
		EClass STEP = eINSTANCE.getStep();

		/**
		 * The meta object literal for the '<em><b>Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEP__DURATION = eINSTANCE.getStep_Duration();

		/**
		 * The meta object literal for the '{@link DECENT.impl.AgentPoolImpl <em>Agent Pool</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.AgentPoolImpl
		 * @see DECENT.impl.DECENTPackageImpl#getAgentPool()
		 * @generated
		 */
		EClass AGENT_POOL = eINSTANCE.getAgentPool();

		/**
		 * The meta object literal for the '<em><b>Agents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT_POOL__AGENTS = eINSTANCE.getAgentPool_Agents();

		/**
		 * The meta object literal for the '{@link DECENT.impl.AttributePoolImpl <em>Attribute Pool</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.AttributePoolImpl
		 * @see DECENT.impl.DECENTPackageImpl#getAttributePool()
		 * @generated
		 */
		EClass ATTRIBUTE_POOL = eINSTANCE.getAttributePool();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_POOL__ATTRIBUTES = eINSTANCE.getAttributePool_Attributes();

		/**
		 * The meta object literal for the '{@link DECENT.impl.ArtifactTypeHierarchyImpl <em>Artifact Type Hierarchy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.ArtifactTypeHierarchyImpl
		 * @see DECENT.impl.DECENTPackageImpl#getArtifactTypeHierarchy()
		 * @generated
		 */
		EClass ARTIFACT_TYPE_HIERARCHY = eINSTANCE.getArtifactTypeHierarchy();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACT_TYPE_HIERARCHY__TYPES = eINSTANCE.getArtifactTypeHierarchy_Types();

		/**
		 * The meta object literal for the '{@link DECENT.impl.ArtifactTypeImpl <em>Artifact Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.ArtifactTypeImpl
		 * @see DECENT.impl.DECENTPackageImpl#getArtifactType()
		 * @generated
		 */
		EClass ARTIFACT_TYPE = eINSTANCE.getArtifactType();

		/**
		 * The meta object literal for the '<em><b>Container Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACT_TYPE__CONTAINER_TYPES = eINSTANCE.getArtifactType_ContainerTypes();

		/**
		 * The meta object literal for the '{@link DECENT.impl.ProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.ProjectImpl
		 * @see DECENT.impl.DECENTPackageImpl#getProject()
		 * @generated
		 */
		EClass PROJECT = eINSTANCE.getProject();

		/**
		 * The meta object literal for the '<em><b>Agents</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__AGENTS = eINSTANCE.getProject_Agents();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__LOCATION = eINSTANCE.getProject_Location();

		/**
		 * The meta object literal for the '{@link DECENT.impl.AgentImpl <em>Agent</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.AgentImpl
		 * @see DECENT.impl.DECENTPackageImpl#getAgent()
		 * @generated
		 */
		EClass AGENT = eINSTANCE.getAgent();

		/**
		 * The meta object literal for the '<em><b>EMail</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AGENT__EMAIL = eINSTANCE.getAgent_EMail();

		/**
		 * The meta object literal for the '<em><b>Projects</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT__PROJECTS = eINSTANCE.getAgent_Projects();

		/**
		 * The meta object literal for the '<em><b>Activities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT__ACTIVITIES = eINSTANCE.getAgent_Activities();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT__STATES = eINSTANCE.getAgent_States();

		/**
		 * The meta object literal for the '{@link DECENT.impl.AgentStateImpl <em>Agent State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.AgentStateImpl
		 * @see DECENT.impl.DECENTPackageImpl#getAgentState()
		 * @generated
		 */
		EClass AGENT_STATE = eINSTANCE.getAgentState();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AGENT_STATE__DATE = eINSTANCE.getAgentState_Date();

		/**
		 * The meta object literal for the '<em><b>Agent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT_STATE__AGENT = eINSTANCE.getAgentState_Agent();

		/**
		 * The meta object literal for the '<em><b>Activities</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT_STATE__ACTIVITIES = eINSTANCE.getAgentState_Activities();

		/**
		 * The meta object literal for the '<em><b>Next</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT_STATE__NEXT = eINSTANCE.getAgentState_Next();

		/**
		 * The meta object literal for the '<em><b>Previous</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT_STATE__PREVIOUS = eINSTANCE.getAgentState_Previous();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT_STATE__VALUES = eINSTANCE.getAgentState_Values();

		/**
		 * The meta object literal for the '{@link DECENT.impl.LocationImpl <em>Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.LocationImpl
		 * @see DECENT.impl.DECENTPackageImpl#getLocation()
		 * @generated
		 */
		EClass LOCATION = eINSTANCE.getLocation();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCATION__TYPE = eINSTANCE.getLocation_Type();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCATION__PROJECT = eINSTANCE.getLocation_Project();

		/**
		 * The meta object literal for the '<em><b>Artifacts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCATION__ARTIFACTS = eINSTANCE.getLocation_Artifacts();

		/**
		 * The meta object literal for the '{@link DECENT.impl.ArtifactImpl <em>Artifact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.ArtifactImpl
		 * @see DECENT.impl.DECENTPackageImpl#getArtifact()
		 * @generated
		 */
		EClass ARTIFACT = eINSTANCE.getArtifact();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACT__TYPE = eINSTANCE.getArtifact_Type();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACT__PARENT = eINSTANCE.getArtifact_Parent();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACT__CHILDREN = eINSTANCE.getArtifact_Children();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACT__STATES = eINSTANCE.getArtifact_States();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACT__ATTRIBUTES = eINSTANCE.getArtifact_Attributes();

		/**
		 * The meta object literal for the '{@link DECENT.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.StateImpl
		 * @see DECENT.impl.DECENTPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Artifact</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__ARTIFACT = eINSTANCE.getState_Artifact();

		/**
		 * The meta object literal for the '<em><b>Activity</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__ACTIVITY = eINSTANCE.getState_Activity();

		/**
		 * The meta object literal for the '<em><b>From Activity</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__FROM_ACTIVITY = eINSTANCE.getState_FromActivity();

		/**
		 * The meta object literal for the '<em><b>Next</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__NEXT = eINSTANCE.getState_Next();

		/**
		 * The meta object literal for the '<em><b>Previous</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__PREVIOUS = eINSTANCE.getState_Previous();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__PARENT = eINSTANCE.getState_Parent();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__CHILDREN = eINSTANCE.getState_Children();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__VALUES = eINSTANCE.getState_Values();

		/**
		 * The meta object literal for the '{@link DECENT.impl.ActivityImpl <em>Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.ActivityImpl
		 * @see DECENT.impl.DECENTPackageImpl#getActivity()
		 * @generated
		 */
		EClass ACTIVITY = eINSTANCE.getActivity();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTIVITY__TYPE = eINSTANCE.getActivity_Type();

		/**
		 * The meta object literal for the '<em><b>Agent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTIVITY__AGENT = eINSTANCE.getActivity_Agent();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTIVITY__STATE = eINSTANCE.getActivity_State();

		/**
		 * The meta object literal for the '<em><b>Target State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTIVITY__TARGET_STATE = eINSTANCE.getActivity_TargetState();

		/**
		 * The meta object literal for the '<em><b>Deltas</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTIVITY__DELTAS = eINSTANCE.getActivity_Deltas();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTIVITY__DATE = eINSTANCE.getActivity_Date();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTIVITY__VALUES = eINSTANCE.getActivity_Values();

		/**
		 * The meta object literal for the '<em><b>Agent State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTIVITY__AGENT_STATE = eINSTANCE.getActivity_AgentState();

		/**
		 * The meta object literal for the '{@link DECENT.impl.ActivityTypeImpl <em>Activity Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.ActivityTypeImpl
		 * @see DECENT.impl.DECENTPackageImpl#getActivityType()
		 * @generated
		 */
		EClass ACTIVITY_TYPE = eINSTANCE.getActivityType();

		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTIVITY_TYPE__DIMENSION = eINSTANCE.getActivityType_Dimension();

		/**
		 * The meta object literal for the '{@link DECENT.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.DimensionImpl
		 * @see DECENT.impl.DECENTPackageImpl#getDimension()
		 * @generated
		 */
		EClass DIMENSION = eINSTANCE.getDimension();

		/**
		 * The meta object literal for the '{@link DECENT.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.AttributeImpl
		 * @see DECENT.impl.DECENTPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Artifact Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__ARTIFACT_TYPES = eINSTANCE.getAttribute_ArtifactTypes();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__DIMENSION = eINSTANCE.getAttribute_Dimension();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__DESCRIPTION = eINSTANCE.getAttribute_Description();

		/**
		 * The meta object literal for the '{@link DECENT.impl.ValueImpl <em>Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.ValueImpl
		 * @see DECENT.impl.DECENTPackageImpl#getValue()
		 * @generated
		 */
		EClass VALUE = eINSTANCE.getValue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE__NAME = eINSTANCE.getValue_Name();

		/**
		 * The meta object literal for the '<em><b>Of Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALUE__OF_ATTRIBUTE = eINSTANCE.getValue_OfAttribute();

		/**
		 * The meta object literal for the '{@link DECENT.impl.IntegerValueImpl <em>Integer Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.IntegerValueImpl
		 * @see DECENT.impl.DECENTPackageImpl#getIntegerValue()
		 * @generated
		 */
		EClass INTEGER_VALUE = eINSTANCE.getIntegerValue();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_VALUE__CONTENT = eINSTANCE.getIntegerValue_Content();

		/**
		 * The meta object literal for the '{@link DECENT.impl.DoubleValueImpl <em>Double Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.DoubleValueImpl
		 * @see DECENT.impl.DECENTPackageImpl#getDoubleValue()
		 * @generated
		 */
		EClass DOUBLE_VALUE = eINSTANCE.getDoubleValue();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOUBLE_VALUE__CONTENT = eINSTANCE.getDoubleValue_Content();

		/**
		 * The meta object literal for the '{@link DECENT.impl.StringValueImpl <em>String Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.StringValueImpl
		 * @see DECENT.impl.DECENTPackageImpl#getStringValue()
		 * @generated
		 */
		EClass STRING_VALUE = eINSTANCE.getStringValue();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_VALUE__CONTENT = eINSTANCE.getStringValue_Content();

		/**
		 * The meta object literal for the '{@link DECENT.impl.ListValueImpl <em>List Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.ListValueImpl
		 * @see DECENT.impl.DECENTPackageImpl#getListValue()
		 * @generated
		 */
		EClass LIST_VALUE = eINSTANCE.getListValue();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIST_VALUE__CONTENT = eINSTANCE.getListValue_Content();

		/**
		 * The meta object literal for the '{@link DECENT.impl.StringListValueImpl <em>String List Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.StringListValueImpl
		 * @see DECENT.impl.DECENTPackageImpl#getStringListValue()
		 * @generated
		 */
		EClass STRING_LIST_VALUE = eINSTANCE.getStringListValue();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_LIST_VALUE__CONTENT = eINSTANCE.getStringListValue_Content();

		/**
		 * The meta object literal for the '{@link DECENT.impl.DoubleListValueImpl <em>Double List Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.DoubleListValueImpl
		 * @see DECENT.impl.DECENTPackageImpl#getDoubleListValue()
		 * @generated
		 */
		EClass DOUBLE_LIST_VALUE = eINSTANCE.getDoubleListValue();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOUBLE_LIST_VALUE__CONTENT = eINSTANCE.getDoubleListValue_Content();

		/**
		 * The meta object literal for the '{@link DECENT.impl.IntegerListValueImpl <em>Integer List Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.IntegerListValueImpl
		 * @see DECENT.impl.DECENTPackageImpl#getIntegerListValue()
		 * @generated
		 */
		EClass INTEGER_LIST_VALUE = eINSTANCE.getIntegerListValue();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_LIST_VALUE__CONTENT = eINSTANCE.getIntegerListValue_Content();

		/**
		 * The meta object literal for the '{@link DECENT.impl.DeltaImpl <em>Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DECENT.impl.DeltaImpl
		 * @see DECENT.impl.DECENTPackageImpl#getDelta()
		 * @generated
		 */
		EClass DELTA = eINSTANCE.getDelta();

		/**
		 * The meta object literal for the '<em><b>Activity</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DELTA__ACTIVITY = eINSTANCE.getDelta_Activity();

		/**
		 * The meta object literal for the '<em><b>On Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DELTA__ON_ATTRIBUTE = eINSTANCE.getDelta_OnAttribute();

		/**
		 * The meta object literal for the '<em><b>Target Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DELTA__TARGET_VALUE = eINSTANCE.getDelta_TargetValue();

		/**
		 * The meta object literal for the '<em><b>Source Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DELTA__SOURCE_VALUE = eINSTANCE.getDelta_SourceValue();

		/**
		 * The meta object literal for the '<em><b>Change</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELTA__CHANGE = eINSTANCE.getDelta_Change();

	}

} //DECENTPackage
