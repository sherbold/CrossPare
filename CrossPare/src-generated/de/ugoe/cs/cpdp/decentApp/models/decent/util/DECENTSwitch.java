/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.util;

import de.ugoe.cs.cpdp.decentApp.models.decent.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage
 * @generated
 */
public class DECENTSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DECENTPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DECENTSwitch() {
		if (modelPackage == null) {
			modelPackage = DECENTPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case DECENTPackage.MODEL: {
				Model model = (Model)theEObject;
				T result = caseModel(model);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.ELEMENT: {
				Element element = (Element)theEObject;
				T result = caseElement(element);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.STEP: {
				Step step = (Step)theEObject;
				T result = caseStep(step);
				if (result == null) result = caseElement(step);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.AGENT_POOL: {
				AgentPool agentPool = (AgentPool)theEObject;
				T result = caseAgentPool(agentPool);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.ATTRIBUTE_POOL: {
				AttributePool attributePool = (AttributePool)theEObject;
				T result = caseAttributePool(attributePool);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.ARTIFACT_TYPE_HIERARCHY: {
				ArtifactTypeHierarchy artifactTypeHierarchy = (ArtifactTypeHierarchy)theEObject;
				T result = caseArtifactTypeHierarchy(artifactTypeHierarchy);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.ARTIFACT_TYPE: {
				ArtifactType artifactType = (ArtifactType)theEObject;
				T result = caseArtifactType(artifactType);
				if (result == null) result = caseElement(artifactType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.PROJECT: {
				Project project = (Project)theEObject;
				T result = caseProject(project);
				if (result == null) result = caseElement(project);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.AGENT: {
				Agent agent = (Agent)theEObject;
				T result = caseAgent(agent);
				if (result == null) result = caseElement(agent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.AGENT_STATE: {
				AgentState agentState = (AgentState)theEObject;
				T result = caseAgentState(agentState);
				if (result == null) result = caseElement(agentState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.LOCATION: {
				Location location = (Location)theEObject;
				T result = caseLocation(location);
				if (result == null) result = caseElement(location);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.ARTIFACT: {
				Artifact artifact = (Artifact)theEObject;
				T result = caseArtifact(artifact);
				if (result == null) result = caseElement(artifact);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.STATE: {
				State state = (State)theEObject;
				T result = caseState(state);
				if (result == null) result = caseElement(state);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.ACTIVITY: {
				Activity activity = (Activity)theEObject;
				T result = caseActivity(activity);
				if (result == null) result = caseElement(activity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.ACTIVITY_TYPE: {
				ActivityType activityType = (ActivityType)theEObject;
				T result = caseActivityType(activityType);
				if (result == null) result = caseElement(activityType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.DIMENSION: {
				Dimension dimension = (Dimension)theEObject;
				T result = caseDimension(dimension);
				if (result == null) result = caseElement(dimension);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.ATTRIBUTE: {
				Attribute attribute = (Attribute)theEObject;
				T result = caseAttribute(attribute);
				if (result == null) result = caseElement(attribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.VALUE: {
				Value value = (Value)theEObject;
				T result = caseValue(value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.INTEGER_VALUE: {
				IntegerValue integerValue = (IntegerValue)theEObject;
				T result = caseIntegerValue(integerValue);
				if (result == null) result = caseValue(integerValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.DOUBLE_VALUE: {
				DoubleValue doubleValue = (DoubleValue)theEObject;
				T result = caseDoubleValue(doubleValue);
				if (result == null) result = caseValue(doubleValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.STRING_VALUE: {
				StringValue stringValue = (StringValue)theEObject;
				T result = caseStringValue(stringValue);
				if (result == null) result = caseValue(stringValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.LIST_VALUE: {
				ListValue listValue = (ListValue)theEObject;
				T result = caseListValue(listValue);
				if (result == null) result = caseValue(listValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.STRING_LIST_VALUE: {
				StringListValue stringListValue = (StringListValue)theEObject;
				T result = caseStringListValue(stringListValue);
				if (result == null) result = caseValue(stringListValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.DOUBLE_LIST_VALUE: {
				DoubleListValue doubleListValue = (DoubleListValue)theEObject;
				T result = caseDoubleListValue(doubleListValue);
				if (result == null) result = caseValue(doubleListValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.INTEGER_LIST_VALUE: {
				IntegerListValue integerListValue = (IntegerListValue)theEObject;
				T result = caseIntegerListValue(integerListValue);
				if (result == null) result = caseValue(integerListValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DECENTPackage.DELTA: {
				Delta delta = (Delta)theEObject;
				T result = caseDelta(delta);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModel(Model object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElement(Element object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStep(Step object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Agent Pool</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Agent Pool</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAgentPool(AgentPool object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Pool</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Pool</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributePool(AttributePool object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Artifact Type Hierarchy</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Artifact Type Hierarchy</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArtifactTypeHierarchy(ArtifactTypeHierarchy object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Artifact Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Artifact Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArtifactType(ArtifactType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProject(Project object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Agent</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Agent</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAgent(Agent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Agent State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Agent State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAgentState(AgentState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Location</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Location</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocation(Location object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Artifact</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Artifact</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArtifact(Artifact object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseState(State object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActivity(Activity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Activity Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Activity Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActivityType(ActivityType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dimension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDimension(Dimension object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttribute(Attribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValue(Value object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerValue(IntegerValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Double Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Double Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDoubleValue(DoubleValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringValue(StringValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>List Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>List Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseListValue(ListValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String List Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String List Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringListValue(StringListValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Double List Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Double List Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDoubleListValue(DoubleListValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer List Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer List Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerListValue(IntegerListValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Delta</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Delta</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDelta(Delta object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //DECENTSwitch
