/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.util;

import de.ugoe.cs.cpdp.decentApp.models.decent.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage
 * @generated
 */
public class DECENTAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DECENTPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DECENTAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DECENTPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DECENTSwitch<Adapter> modelSwitch =
		new DECENTSwitch<Adapter>() {
			@Override
			public Adapter caseModel(Model object) {
				return createModelAdapter();
			}
			@Override
			public Adapter caseElement(Element object) {
				return createElementAdapter();
			}
			@Override
			public Adapter caseStep(Step object) {
				return createStepAdapter();
			}
			@Override
			public Adapter caseAgentPool(AgentPool object) {
				return createAgentPoolAdapter();
			}
			@Override
			public Adapter caseAttributePool(AttributePool object) {
				return createAttributePoolAdapter();
			}
			@Override
			public Adapter caseArtifactTypeHierarchy(ArtifactTypeHierarchy object) {
				return createArtifactTypeHierarchyAdapter();
			}
			@Override
			public Adapter caseArtifactType(ArtifactType object) {
				return createArtifactTypeAdapter();
			}
			@Override
			public Adapter caseProject(Project object) {
				return createProjectAdapter();
			}
			@Override
			public Adapter caseAgent(Agent object) {
				return createAgentAdapter();
			}
			@Override
			public Adapter caseAgentState(AgentState object) {
				return createAgentStateAdapter();
			}
			@Override
			public Adapter caseLocation(Location object) {
				return createLocationAdapter();
			}
			@Override
			public Adapter caseArtifact(Artifact object) {
				return createArtifactAdapter();
			}
			@Override
			public Adapter caseState(State object) {
				return createStateAdapter();
			}
			@Override
			public Adapter caseActivity(Activity object) {
				return createActivityAdapter();
			}
			@Override
			public Adapter caseActivityType(ActivityType object) {
				return createActivityTypeAdapter();
			}
			@Override
			public Adapter caseDimension(Dimension object) {
				return createDimensionAdapter();
			}
			@Override
			public Adapter caseAttribute(Attribute object) {
				return createAttributeAdapter();
			}
			@Override
			public Adapter caseValue(Value object) {
				return createValueAdapter();
			}
			@Override
			public Adapter caseIntegerValue(IntegerValue object) {
				return createIntegerValueAdapter();
			}
			@Override
			public Adapter caseDoubleValue(DoubleValue object) {
				return createDoubleValueAdapter();
			}
			@Override
			public Adapter caseStringValue(StringValue object) {
				return createStringValueAdapter();
			}
			@Override
			public Adapter caseListValue(ListValue object) {
				return createListValueAdapter();
			}
			@Override
			public Adapter caseStringListValue(StringListValue object) {
				return createStringListValueAdapter();
			}
			@Override
			public Adapter caseDoubleListValue(DoubleListValue object) {
				return createDoubleListValueAdapter();
			}
			@Override
			public Adapter caseIntegerListValue(IntegerListValue object) {
				return createIntegerListValueAdapter();
			}
			@Override
			public Adapter caseDelta(Delta object) {
				return createDeltaAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Model
	 * @generated
	 */
	public Adapter createModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Element <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Element
	 * @generated
	 */
	public Adapter createElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Step <em>Step</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Step
	 * @generated
	 */
	public Adapter createStepAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentPool <em>Agent Pool</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentPool
	 * @generated
	 */
	public Adapter createAgentPoolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AttributePool <em>Attribute Pool</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AttributePool
	 * @generated
	 */
	public Adapter createAttributePoolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactTypeHierarchy <em>Artifact Type Hierarchy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactTypeHierarchy
	 * @generated
	 */
	public Adapter createArtifactTypeHierarchyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType <em>Artifact Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType
	 * @generated
	 */
	public Adapter createArtifactTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Project
	 * @generated
	 */
	public Adapter createProjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Agent <em>Agent</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Agent
	 * @generated
	 */
	public Adapter createAgentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.AgentState <em>Agent State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.AgentState
	 * @generated
	 */
	public Adapter createAgentStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Location
	 * @generated
	 */
	public Adapter createLocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Artifact <em>Artifact</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Artifact
	 * @generated
	 */
	public Adapter createArtifactAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.State
	 * @generated
	 */
	public Adapter createStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Activity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Activity
	 * @generated
	 */
	public Adapter createActivityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType <em>Activity Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType
	 * @generated
	 */
	public Adapter createActivityTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Dimension
	 * @generated
	 */
	public Adapter createDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Attribute
	 * @generated
	 */
	public Adapter createAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Value
	 * @generated
	 */
	public Adapter createValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.IntegerValue <em>Integer Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.IntegerValue
	 * @generated
	 */
	public Adapter createIntegerValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.DoubleValue <em>Double Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DoubleValue
	 * @generated
	 */
	public Adapter createDoubleValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.StringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.StringValue
	 * @generated
	 */
	public Adapter createStringValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.ListValue <em>List Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.ListValue
	 * @generated
	 */
	public Adapter createListValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.StringListValue <em>String List Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.StringListValue
	 * @generated
	 */
	public Adapter createStringListValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.DoubleListValue <em>Double List Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DoubleListValue
	 * @generated
	 */
	public Adapter createDoubleListValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.IntegerListValue <em>Integer List Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.IntegerListValue
	 * @generated
	 */
	public Adapter createIntegerListValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.decent.Delta <em>Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.decent.Delta
	 * @generated
	 */
	public Adapter createDeltaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //DECENTAdapterFactory
