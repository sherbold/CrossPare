/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.impl;

import de.ugoe.cs.cpdp.decentApp.models.decent.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import de.ugoe.cs.cpdp.decentApp.models.decent.Activity;
import de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType;
import de.ugoe.cs.cpdp.decentApp.models.decent.Agent;
import de.ugoe.cs.cpdp.decentApp.models.decent.AgentPool;
import de.ugoe.cs.cpdp.decentApp.models.decent.AgentState;
import de.ugoe.cs.cpdp.decentApp.models.decent.Artifact;
import de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType;
import de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactTypeHierarchy;
import de.ugoe.cs.cpdp.decentApp.models.decent.Attribute;
import de.ugoe.cs.cpdp.decentApp.models.decent.AttributePool;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTFactory;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;
import de.ugoe.cs.cpdp.decentApp.models.decent.Delta;
import de.ugoe.cs.cpdp.decentApp.models.decent.Dimension;
import de.ugoe.cs.cpdp.decentApp.models.decent.DoubleListValue;
import de.ugoe.cs.cpdp.decentApp.models.decent.DoubleValue;
import de.ugoe.cs.cpdp.decentApp.models.decent.Element;
import de.ugoe.cs.cpdp.decentApp.models.decent.IntegerListValue;
import de.ugoe.cs.cpdp.decentApp.models.decent.IntegerValue;
import de.ugoe.cs.cpdp.decentApp.models.decent.ListValue;
import de.ugoe.cs.cpdp.decentApp.models.decent.Location;
import de.ugoe.cs.cpdp.decentApp.models.decent.Model;
import de.ugoe.cs.cpdp.decentApp.models.decent.Project;
import de.ugoe.cs.cpdp.decentApp.models.decent.State;
import de.ugoe.cs.cpdp.decentApp.models.decent.Step;
import de.ugoe.cs.cpdp.decentApp.models.decent.StringListValue;
import de.ugoe.cs.cpdp.decentApp.models.decent.StringValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DECENTFactoryImpl extends EFactoryImpl implements DECENTFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DECENTFactory init() {
		try {
			DECENTFactory theDECENTFactory = (DECENTFactory)EPackage.Registry.INSTANCE.getEFactory(DECENTPackage.eNS_URI);
			if (theDECENTFactory != null) {
				return theDECENTFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DECENTFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DECENTFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DECENTPackage.MODEL: return createModel();
			case DECENTPackage.ELEMENT: return createElement();
			case DECENTPackage.STEP: return createStep();
			case DECENTPackage.AGENT_POOL: return createAgentPool();
			case DECENTPackage.ATTRIBUTE_POOL: return createAttributePool();
			case DECENTPackage.ARTIFACT_TYPE_HIERARCHY: return createArtifactTypeHierarchy();
			case DECENTPackage.ARTIFACT_TYPE: return createArtifactType();
			case DECENTPackage.PROJECT: return createProject();
			case DECENTPackage.AGENT: return createAgent();
			case DECENTPackage.AGENT_STATE: return createAgentState();
			case DECENTPackage.LOCATION: return createLocation();
			case DECENTPackage.ARTIFACT: return createArtifact();
			case DECENTPackage.STATE: return createState();
			case DECENTPackage.ACTIVITY: return createActivity();
			case DECENTPackage.ACTIVITY_TYPE: return createActivityType();
			case DECENTPackage.DIMENSION: return createDimension();
			case DECENTPackage.ATTRIBUTE: return createAttribute();
			case DECENTPackage.INTEGER_VALUE: return createIntegerValue();
			case DECENTPackage.DOUBLE_VALUE: return createDoubleValue();
			case DECENTPackage.STRING_VALUE: return createStringValue();
			case DECENTPackage.LIST_VALUE: return createListValue();
			case DECENTPackage.STRING_LIST_VALUE: return createStringListValue();
			case DECENTPackage.DOUBLE_LIST_VALUE: return createDoubleListValue();
			case DECENTPackage.INTEGER_LIST_VALUE: return createIntegerListValue();
			case DECENTPackage.DELTA: return createDelta();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model createModel() {
		ModelImpl model = new ModelImpl();
		return model;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element createElement() {
		ElementImpl element = new ElementImpl();
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Step createStep() {
		StepImpl step = new StepImpl();
		return step;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentPool createAgentPool() {
		AgentPoolImpl agentPool = new AgentPoolImpl();
		return agentPool;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributePool createAttributePool() {
		AttributePoolImpl attributePool = new AttributePoolImpl();
		return attributePool;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactTypeHierarchy createArtifactTypeHierarchy() {
		ArtifactTypeHierarchyImpl artifactTypeHierarchy = new ArtifactTypeHierarchyImpl();
		return artifactTypeHierarchy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactType createArtifactType() {
		ArtifactTypeImpl artifactType = new ArtifactTypeImpl();
		return artifactType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Project createProject() {
		ProjectImpl project = new ProjectImpl();
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Agent createAgent() {
		AgentImpl agent = new AgentImpl();
		return agent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentState createAgentState() {
		AgentStateImpl agentState = new AgentStateImpl();
		return agentState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Location createLocation() {
		LocationImpl location = new LocationImpl();
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Artifact createArtifact() {
		ArtifactImpl artifact = new ArtifactImpl();
		return artifact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity createActivity() {
		ActivityImpl activity = new ActivityImpl();
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivityType createActivityType() {
		ActivityTypeImpl activityType = new ActivityTypeImpl();
		return activityType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dimension createDimension() {
		DimensionImpl dimension = new DimensionImpl();
		return dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerValue createIntegerValue() {
		IntegerValueImpl integerValue = new IntegerValueImpl();
		return integerValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DoubleValue createDoubleValue() {
		DoubleValueImpl doubleValue = new DoubleValueImpl();
		return doubleValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringValue createStringValue() {
		StringValueImpl stringValue = new StringValueImpl();
		return stringValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ListValue createListValue() {
		ListValueImpl listValue = new ListValueImpl();
		return listValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringListValue createStringListValue() {
		StringListValueImpl stringListValue = new StringListValueImpl();
		return stringListValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DoubleListValue createDoubleListValue() {
		DoubleListValueImpl doubleListValue = new DoubleListValueImpl();
		return doubleListValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerListValue createIntegerListValue() {
		IntegerListValueImpl integerListValue = new IntegerListValueImpl();
		return integerListValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Delta createDelta() {
		DeltaImpl delta = new DeltaImpl();
		return delta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DECENTPackage getDECENTPackage() {
		return (DECENTPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DECENTPackage getPackage() {
		return DECENTPackage.eINSTANCE;
	}

} //DECENTFactoryImpl
