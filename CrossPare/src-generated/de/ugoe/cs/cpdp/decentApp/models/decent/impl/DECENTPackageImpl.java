/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.impl;

import de.ugoe.cs.cpdp.decentApp.models.decent.util.DECENTValidator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;

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
import de.ugoe.cs.cpdp.decentApp.models.decent.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DECENTPackageImpl extends EPackageImpl implements DECENTPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stepEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agentPoolEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributePoolEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactTypeHierarchyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agentStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass locationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dimensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass doubleValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringListValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass doubleListValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerListValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DECENTPackageImpl() {
		super(eNS_URI, DECENTFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link DECENTPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DECENTPackage init() {
		if (isInited) return (DECENTPackage)EPackage.Registry.INSTANCE.getEPackage(DECENTPackage.eNS_URI);

		// Obtain or create and register package
		DECENTPackageImpl theDECENTPackage = (DECENTPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DECENTPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DECENTPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theDECENTPackage.createPackageContents();

		// Initialize created meta-data
		theDECENTPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theDECENTPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return DECENTValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theDECENTPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DECENTPackage.eNS_URI, theDECENTPackage);
		return theDECENTPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModel() {
		return modelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModel_Name() {
		return (EAttribute)modelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModel_Content() {
		return (EAttribute)modelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModel_Steps() {
		return (EReference)modelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModel_Projects() {
		return (EReference)modelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModel_ArtifactTypeHierarchy() {
		return (EReference)modelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModel_AgentPool() {
		return (EReference)modelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModel_AttributePool() {
		return (EReference)modelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModel_ActivityTypes() {
		return (EReference)modelEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModel_Dimensions() {
		return (EReference)modelEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElement() {
		return elementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElement_Name() {
		return (EAttribute)elementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElement_ID() {
		return (EAttribute)elementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStep() {
		return stepEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStep_Duration() {
		return (EAttribute)stepEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAgentPool() {
		return agentPoolEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentPool_Agents() {
		return (EReference)agentPoolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributePool() {
		return attributePoolEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributePool_Attributes() {
		return (EReference)attributePoolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifactTypeHierarchy() {
		return artifactTypeHierarchyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifactTypeHierarchy_Types() {
		return (EReference)artifactTypeHierarchyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifactType() {
		return artifactTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifactType_ContainerTypes() {
		return (EReference)artifactTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProject() {
		return projectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Agents() {
		return (EReference)projectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Location() {
		return (EReference)projectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAgent() {
		return agentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgent_EMail() {
		return (EAttribute)agentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgent_Projects() {
		return (EReference)agentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgent_Activities() {
		return (EReference)agentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgent_States() {
		return (EReference)agentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAgentState() {
		return agentStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgentState_Date() {
		return (EAttribute)agentStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentState_Agent() {
		return (EReference)agentStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentState_Activities() {
		return (EReference)agentStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentState_Next() {
		return (EReference)agentStateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentState_Previous() {
		return (EReference)agentStateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentState_Values() {
		return (EReference)agentStateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocation() {
		return locationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocation_Type() {
		return (EReference)locationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocation_Project() {
		return (EReference)locationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocation_Artifacts() {
		return (EReference)locationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifact() {
		return artifactEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifact_Type() {
		return (EReference)artifactEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifact_Parent() {
		return (EReference)artifactEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifact_Children() {
		return (EReference)artifactEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifact_States() {
		return (EReference)artifactEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifact_Attributes() {
		return (EReference)artifactEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getState() {
		return stateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Artifact() {
		return (EReference)stateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Activity() {
		return (EReference)stateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_FromActivity() {
		return (EReference)stateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Next() {
		return (EReference)stateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Previous() {
		return (EReference)stateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Parent() {
		return (EReference)stateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Children() {
		return (EReference)stateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Values() {
		return (EReference)stateEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivity() {
		return activityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_Type() {
		return (EReference)activityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_Agent() {
		return (EReference)activityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_State() {
		return (EReference)activityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_TargetState() {
		return (EReference)activityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_Deltas() {
		return (EReference)activityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActivity_Date() {
		return (EAttribute)activityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_Values() {
		return (EReference)activityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_AgentState() {
		return (EReference)activityEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivityType() {
		return activityTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityType_Dimension() {
		return (EReference)activityTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDimension() {
		return dimensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttribute() {
		return attributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttribute_ArtifactTypes() {
		return (EReference)attributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttribute_Type() {
		return (EReference)attributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttribute_Dimension() {
		return (EReference)attributeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttribute_Description() {
		return (EAttribute)attributeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValue() {
		return valueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValue_Name() {
		return (EAttribute)valueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getValue_OfAttribute() {
		return (EReference)valueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerValue() {
		return integerValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerValue_Content() {
		return (EAttribute)integerValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDoubleValue() {
		return doubleValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDoubleValue_Content() {
		return (EAttribute)doubleValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringValue() {
		return stringValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringValue_Content() {
		return (EAttribute)stringValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getListValue() {
		return listValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getListValue_Content() {
		return (EReference)listValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringListValue() {
		return stringListValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringListValue_Content() {
		return (EAttribute)stringListValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDoubleListValue() {
		return doubleListValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDoubleListValue_Content() {
		return (EAttribute)doubleListValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerListValue() {
		return integerListValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerListValue_Content() {
		return (EAttribute)integerListValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDelta() {
		return deltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDelta_Activity() {
		return (EReference)deltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDelta_OnAttribute() {
		return (EReference)deltaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDelta_TargetValue() {
		return (EReference)deltaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDelta_SourceValue() {
		return (EReference)deltaEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDelta_Change() {
		return (EAttribute)deltaEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DECENTFactory getDECENTFactory() {
		return (DECENTFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		modelEClass = createEClass(MODEL);
		createEAttribute(modelEClass, MODEL__NAME);
		createEAttribute(modelEClass, MODEL__CONTENT);
		createEReference(modelEClass, MODEL__STEPS);
		createEReference(modelEClass, MODEL__PROJECTS);
		createEReference(modelEClass, MODEL__ARTIFACT_TYPE_HIERARCHY);
		createEReference(modelEClass, MODEL__AGENT_POOL);
		createEReference(modelEClass, MODEL__ATTRIBUTE_POOL);
		createEReference(modelEClass, MODEL__ACTIVITY_TYPES);
		createEReference(modelEClass, MODEL__DIMENSIONS);

		elementEClass = createEClass(ELEMENT);
		createEAttribute(elementEClass, ELEMENT__NAME);
		createEAttribute(elementEClass, ELEMENT__ID);

		stepEClass = createEClass(STEP);
		createEAttribute(stepEClass, STEP__DURATION);

		agentPoolEClass = createEClass(AGENT_POOL);
		createEReference(agentPoolEClass, AGENT_POOL__AGENTS);

		attributePoolEClass = createEClass(ATTRIBUTE_POOL);
		createEReference(attributePoolEClass, ATTRIBUTE_POOL__ATTRIBUTES);

		artifactTypeHierarchyEClass = createEClass(ARTIFACT_TYPE_HIERARCHY);
		createEReference(artifactTypeHierarchyEClass, ARTIFACT_TYPE_HIERARCHY__TYPES);

		artifactTypeEClass = createEClass(ARTIFACT_TYPE);
		createEReference(artifactTypeEClass, ARTIFACT_TYPE__CONTAINER_TYPES);

		projectEClass = createEClass(PROJECT);
		createEReference(projectEClass, PROJECT__AGENTS);
		createEReference(projectEClass, PROJECT__LOCATION);

		agentEClass = createEClass(AGENT);
		createEAttribute(agentEClass, AGENT__EMAIL);
		createEReference(agentEClass, AGENT__PROJECTS);
		createEReference(agentEClass, AGENT__ACTIVITIES);
		createEReference(agentEClass, AGENT__STATES);

		agentStateEClass = createEClass(AGENT_STATE);
		createEAttribute(agentStateEClass, AGENT_STATE__DATE);
		createEReference(agentStateEClass, AGENT_STATE__AGENT);
		createEReference(agentStateEClass, AGENT_STATE__ACTIVITIES);
		createEReference(agentStateEClass, AGENT_STATE__NEXT);
		createEReference(agentStateEClass, AGENT_STATE__PREVIOUS);
		createEReference(agentStateEClass, AGENT_STATE__VALUES);

		locationEClass = createEClass(LOCATION);
		createEReference(locationEClass, LOCATION__TYPE);
		createEReference(locationEClass, LOCATION__PROJECT);
		createEReference(locationEClass, LOCATION__ARTIFACTS);

		artifactEClass = createEClass(ARTIFACT);
		createEReference(artifactEClass, ARTIFACT__TYPE);
		createEReference(artifactEClass, ARTIFACT__PARENT);
		createEReference(artifactEClass, ARTIFACT__CHILDREN);
		createEReference(artifactEClass, ARTIFACT__STATES);
		createEReference(artifactEClass, ARTIFACT__ATTRIBUTES);

		stateEClass = createEClass(STATE);
		createEReference(stateEClass, STATE__ARTIFACT);
		createEReference(stateEClass, STATE__ACTIVITY);
		createEReference(stateEClass, STATE__FROM_ACTIVITY);
		createEReference(stateEClass, STATE__NEXT);
		createEReference(stateEClass, STATE__PREVIOUS);
		createEReference(stateEClass, STATE__PARENT);
		createEReference(stateEClass, STATE__CHILDREN);
		createEReference(stateEClass, STATE__VALUES);

		activityEClass = createEClass(ACTIVITY);
		createEReference(activityEClass, ACTIVITY__TYPE);
		createEReference(activityEClass, ACTIVITY__AGENT);
		createEReference(activityEClass, ACTIVITY__STATE);
		createEReference(activityEClass, ACTIVITY__TARGET_STATE);
		createEReference(activityEClass, ACTIVITY__DELTAS);
		createEAttribute(activityEClass, ACTIVITY__DATE);
		createEReference(activityEClass, ACTIVITY__VALUES);
		createEReference(activityEClass, ACTIVITY__AGENT_STATE);

		activityTypeEClass = createEClass(ACTIVITY_TYPE);
		createEReference(activityTypeEClass, ACTIVITY_TYPE__DIMENSION);

		dimensionEClass = createEClass(DIMENSION);

		attributeEClass = createEClass(ATTRIBUTE);
		createEReference(attributeEClass, ATTRIBUTE__ARTIFACT_TYPES);
		createEReference(attributeEClass, ATTRIBUTE__TYPE);
		createEReference(attributeEClass, ATTRIBUTE__DIMENSION);
		createEAttribute(attributeEClass, ATTRIBUTE__DESCRIPTION);

		valueEClass = createEClass(VALUE);
		createEAttribute(valueEClass, VALUE__NAME);
		createEReference(valueEClass, VALUE__OF_ATTRIBUTE);

		integerValueEClass = createEClass(INTEGER_VALUE);
		createEAttribute(integerValueEClass, INTEGER_VALUE__CONTENT);

		doubleValueEClass = createEClass(DOUBLE_VALUE);
		createEAttribute(doubleValueEClass, DOUBLE_VALUE__CONTENT);

		stringValueEClass = createEClass(STRING_VALUE);
		createEAttribute(stringValueEClass, STRING_VALUE__CONTENT);

		listValueEClass = createEClass(LIST_VALUE);
		createEReference(listValueEClass, LIST_VALUE__CONTENT);

		stringListValueEClass = createEClass(STRING_LIST_VALUE);
		createEAttribute(stringListValueEClass, STRING_LIST_VALUE__CONTENT);

		doubleListValueEClass = createEClass(DOUBLE_LIST_VALUE);
		createEAttribute(doubleListValueEClass, DOUBLE_LIST_VALUE__CONTENT);

		integerListValueEClass = createEClass(INTEGER_LIST_VALUE);
		createEAttribute(integerListValueEClass, INTEGER_LIST_VALUE__CONTENT);

		deltaEClass = createEClass(DELTA);
		createEReference(deltaEClass, DELTA__ACTIVITY);
		createEReference(deltaEClass, DELTA__ON_ATTRIBUTE);
		createEReference(deltaEClass, DELTA__TARGET_VALUE);
		createEReference(deltaEClass, DELTA__SOURCE_VALUE);
		createEAttribute(deltaEClass, DELTA__CHANGE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		stepEClass.getESuperTypes().add(this.getElement());
		artifactTypeEClass.getESuperTypes().add(this.getElement());
		projectEClass.getESuperTypes().add(this.getElement());
		agentEClass.getESuperTypes().add(this.getElement());
		agentStateEClass.getESuperTypes().add(this.getElement());
		locationEClass.getESuperTypes().add(this.getElement());
		artifactEClass.getESuperTypes().add(this.getElement());
		stateEClass.getESuperTypes().add(this.getElement());
		activityEClass.getESuperTypes().add(this.getElement());
		activityTypeEClass.getESuperTypes().add(this.getElement());
		dimensionEClass.getESuperTypes().add(this.getElement());
		attributeEClass.getESuperTypes().add(this.getElement());
		integerValueEClass.getESuperTypes().add(this.getValue());
		doubleValueEClass.getESuperTypes().add(this.getValue());
		stringValueEClass.getESuperTypes().add(this.getValue());
		listValueEClass.getESuperTypes().add(this.getValue());
		stringListValueEClass.getESuperTypes().add(this.getValue());
		doubleListValueEClass.getESuperTypes().add(this.getValue());
		integerListValueEClass.getESuperTypes().add(this.getValue());

		// Initialize classes, features, and operations; add parameters
		initEClass(modelEClass, Model.class, "Model", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModel_Name(), ecorePackage.getEString(), "name", null, 1, 1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModel_Content(), ecorePackage.getEString(), "content", null, 0, -1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_Steps(), this.getStep(), null, "steps", null, 0, -1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_Projects(), this.getProject(), null, "projects", null, 0, -1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_ArtifactTypeHierarchy(), this.getArtifactTypeHierarchy(), null, "artifactTypeHierarchy", null, 0, 1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_AgentPool(), this.getAgentPool(), null, "agentPool", null, 1, 1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_AttributePool(), this.getAttributePool(), null, "attributePool", null, 1, 1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_ActivityTypes(), this.getActivityType(), null, "activityTypes", null, 0, -1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getModel_Dimensions(), this.getDimension(), null, "dimensions", null, 0, -1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(elementEClass, Element.class, "Element", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getElement_Name(), ecorePackage.getEString(), "name", null, 1, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElement_ID(), ecorePackage.getEInt(), "ID", "-1", 1, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stepEClass, Step.class, "Step", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStep_Duration(), ecorePackage.getEString(), "duration", null, 0, 1, Step.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(agentPoolEClass, AgentPool.class, "AgentPool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAgentPool_Agents(), this.getAgent(), null, "agents", null, 0, -1, AgentPool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributePoolEClass, AttributePool.class, "AttributePool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributePool_Attributes(), this.getAttribute(), null, "attributes", null, 0, -1, AttributePool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(artifactTypeHierarchyEClass, ArtifactTypeHierarchy.class, "ArtifactTypeHierarchy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArtifactTypeHierarchy_Types(), this.getArtifactType(), null, "types", null, 0, -1, ArtifactTypeHierarchy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(artifactTypeEClass, ArtifactType.class, "ArtifactType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArtifactType_ContainerTypes(), this.getArtifactType(), null, "containerTypes", null, 0, -1, ArtifactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(projectEClass, Project.class, "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProject_Agents(), this.getAgent(), this.getAgent_Projects(), "agents", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Location(), this.getLocation(), this.getLocation_Project(), "location", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(agentEClass, Agent.class, "Agent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAgent_EMail(), ecorePackage.getEString(), "eMail", null, 1, 1, Agent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgent_Projects(), this.getProject(), this.getProject_Agents(), "projects", null, 1, 1, Agent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgent_Activities(), this.getActivity(), this.getActivity_Agent(), "activities", null, 0, -1, Agent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgent_States(), this.getAgentState(), this.getAgentState_Agent(), "states", null, 0, -1, Agent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(agentStateEClass, AgentState.class, "AgentState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAgentState_Date(), ecorePackage.getEDate(), "date", null, 0, 1, AgentState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgentState_Agent(), this.getAgent(), this.getAgent_States(), "agent", null, 1, 1, AgentState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgentState_Activities(), this.getActivity(), this.getActivity_AgentState(), "activities", null, 0, -1, AgentState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgentState_Next(), this.getAgentState(), this.getAgentState_Previous(), "next", null, 0, 1, AgentState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgentState_Previous(), this.getAgentState(), this.getAgentState_Next(), "previous", null, 0, 1, AgentState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgentState_Values(), this.getValue(), null, "values", null, 0, -1, AgentState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(locationEClass, Location.class, "Location", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLocation_Type(), this.getArtifactType(), null, "type", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLocation_Project(), this.getProject(), this.getProject_Location(), "project", null, 1, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLocation_Artifacts(), this.getArtifact(), null, "artifacts", null, 0, -1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(artifactEClass, Artifact.class, "Artifact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArtifact_Type(), this.getArtifactType(), null, "type", null, 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArtifact_Parent(), this.getArtifact(), this.getArtifact_Children(), "parent", null, 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArtifact_Children(), this.getArtifact(), this.getArtifact_Parent(), "children", null, 0, -1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArtifact_States(), this.getState(), this.getState_Artifact(), "states", null, 0, -1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArtifact_Attributes(), this.getAttribute(), null, "attributes", null, 0, -1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateEClass, State.class, "State", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getState_Artifact(), this.getArtifact(), this.getArtifact_States(), "artifact", null, 1, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_Activity(), this.getActivity(), this.getActivity_State(), "activity", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_FromActivity(), this.getActivity(), this.getActivity_TargetState(), "fromActivity", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_Next(), this.getState(), this.getState_Previous(), "next", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_Previous(), this.getState(), this.getState_Next(), "previous", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_Parent(), this.getState(), this.getState_Children(), "parent", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_Children(), this.getState(), this.getState_Parent(), "children", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_Values(), this.getValue(), null, "values", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(activityEClass, Activity.class, "Activity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActivity_Type(), this.getActivityType(), null, "type", null, 0, -1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActivity_Agent(), this.getAgent(), this.getAgent_Activities(), "agent", null, 1, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActivity_State(), this.getState(), this.getState_Activity(), "state", null, 1, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActivity_TargetState(), this.getState(), this.getState_FromActivity(), "targetState", null, 1, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActivity_Deltas(), this.getDelta(), this.getDelta_Activity(), "deltas", null, 0, -1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActivity_Date(), ecorePackage.getEDate(), "date", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActivity_Values(), this.getValue(), null, "values", null, 0, -1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActivity_AgentState(), this.getAgentState(), this.getAgentState_Activities(), "agentState", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(activityTypeEClass, ActivityType.class, "ActivityType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActivityType_Dimension(), this.getDimension(), null, "dimension", null, 0, -1, ActivityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(dimensionEClass, Dimension.class, "Dimension", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(attributeEClass, Attribute.class, "Attribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttribute_ArtifactTypes(), this.getArtifactType(), null, "artifactTypes", null, 0, -1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getAttribute_Type(), ecorePackage.getEDataType(), null, "type", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttribute_Dimension(), this.getDimension(), null, "dimension", null, 0, -1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getAttribute_Description(), ecorePackage.getEString(), "description", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueEClass, Value.class, "Value", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValue_Name(), ecorePackage.getEString(), "name", null, 0, 1, Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getValue_OfAttribute(), this.getAttribute(), null, "ofAttribute", null, 1, 1, Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(integerValueEClass, IntegerValue.class, "IntegerValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerValue_Content(), ecorePackage.getEInt(), "content", null, 0, 1, IntegerValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(doubleValueEClass, DoubleValue.class, "DoubleValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDoubleValue_Content(), ecorePackage.getEDouble(), "content", null, 0, 1, DoubleValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringValueEClass, StringValue.class, "StringValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringValue_Content(), ecorePackage.getEString(), "content", null, 0, 1, StringValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(listValueEClass, ListValue.class, "ListValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getListValue_Content(), ecorePackage.getEObject(), null, "content", null, 0, -1, ListValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringListValueEClass, StringListValue.class, "StringListValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringListValue_Content(), ecorePackage.getEString(), "content", null, 0, -1, StringListValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(doubleListValueEClass, DoubleListValue.class, "DoubleListValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDoubleListValue_Content(), ecorePackage.getEDouble(), "content", null, 0, -1, DoubleListValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(integerListValueEClass, IntegerListValue.class, "IntegerListValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerListValue_Content(), ecorePackage.getEInt(), "content", null, 0, -1, IntegerListValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deltaEClass, Delta.class, "Delta", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDelta_Activity(), this.getActivity(), this.getActivity_Deltas(), "activity", null, 1, 1, Delta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDelta_OnAttribute(), this.getAttribute(), null, "onAttribute", null, 1, 1, Delta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDelta_TargetValue(), this.getValue(), null, "targetValue", null, 1, 1, Delta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDelta_SourceValue(), this.getValue(), null, "sourceValue", null, 1, 1, Delta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDelta_Change(), ecorePackage.getEDouble(), "change", null, 0, 1, Delta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/OCL/Import
		createImportAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot
		createPivotAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/OCL/Import</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createImportAnnotations() {
		String source = "http://www.eclipse.org/OCL/Import";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "ecore", "http://www.eclipse.org/emf/2002/Ecore#/"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "invocationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
			 "settingDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
			 "validationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot"
		   });	
		addAnnotation
		  (artifactEClass, 
		   source, 
		   new String[] {
			 "constraints", "typeHierarchy"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createPivotAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot";	
		addAnnotation
		  (artifactEClass, 
		   source, 
		   new String[] {
			 "typeHierarchy", "type.containerTypes->includes(parent.type) or parent = null"
		   });
	}

} //DECENTPackageImpl
