/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.util;

import de.ugoe.cs.cpdp.decentApp.models.decent.*;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;

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
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage
 * @generated
 */
public class DECENTValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final DECENTValidator INSTANCE = new DECENTValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "DECENT";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DECENTValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return DECENTPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case DECENTPackage.MODEL:
				return validateModel((Model)value, diagnostics, context);
			case DECENTPackage.ELEMENT:
				return validateElement((Element)value, diagnostics, context);
			case DECENTPackage.STEP:
				return validateStep((Step)value, diagnostics, context);
			case DECENTPackage.AGENT_POOL:
				return validateAgentPool((AgentPool)value, diagnostics, context);
			case DECENTPackage.ATTRIBUTE_POOL:
				return validateAttributePool((AttributePool)value, diagnostics, context);
			case DECENTPackage.ARTIFACT_TYPE_HIERARCHY:
				return validateArtifactTypeHierarchy((ArtifactTypeHierarchy)value, diagnostics, context);
			case DECENTPackage.ARTIFACT_TYPE:
				return validateArtifactType((ArtifactType)value, diagnostics, context);
			case DECENTPackage.PROJECT:
				return validateProject((Project)value, diagnostics, context);
			case DECENTPackage.AGENT:
				return validateAgent((Agent)value, diagnostics, context);
			case DECENTPackage.AGENT_STATE:
				return validateAgentState((AgentState)value, diagnostics, context);
			case DECENTPackage.LOCATION:
				return validateLocation((Location)value, diagnostics, context);
			case DECENTPackage.ARTIFACT:
				return validateArtifact((Artifact)value, diagnostics, context);
			case DECENTPackage.STATE:
				return validateState((State)value, diagnostics, context);
			case DECENTPackage.ACTIVITY:
				return validateActivity((Activity)value, diagnostics, context);
			case DECENTPackage.ACTIVITY_TYPE:
				return validateActivityType((ActivityType)value, diagnostics, context);
			case DECENTPackage.DIMENSION:
				return validateDimension((Dimension)value, diagnostics, context);
			case DECENTPackage.ATTRIBUTE:
				return validateAttribute((Attribute)value, diagnostics, context);
			case DECENTPackage.VALUE:
				return validateValue((Value)value, diagnostics, context);
			case DECENTPackage.INTEGER_VALUE:
				return validateIntegerValue((IntegerValue)value, diagnostics, context);
			case DECENTPackage.DOUBLE_VALUE:
				return validateDoubleValue((DoubleValue)value, diagnostics, context);
			case DECENTPackage.STRING_VALUE:
				return validateStringValue((StringValue)value, diagnostics, context);
			case DECENTPackage.LIST_VALUE:
				return validateListValue((ListValue)value, diagnostics, context);
			case DECENTPackage.STRING_LIST_VALUE:
				return validateStringListValue((StringListValue)value, diagnostics, context);
			case DECENTPackage.DOUBLE_LIST_VALUE:
				return validateDoubleListValue((DoubleListValue)value, diagnostics, context);
			case DECENTPackage.INTEGER_LIST_VALUE:
				return validateIntegerListValue((IntegerListValue)value, diagnostics, context);
			case DECENTPackage.DELTA:
				return validateDelta((Delta)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateModel(Model model, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(model, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateElement(Element element, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(element, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStep(Step step, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(step, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAgentPool(AgentPool agentPool, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(agentPool, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributePool(AttributePool attributePool, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(attributePool, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArtifactTypeHierarchy(ArtifactTypeHierarchy artifactTypeHierarchy, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(artifactTypeHierarchy, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArtifactType(ArtifactType artifactType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(artifactType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProject(Project project, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(project, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAgent(Agent agent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(agent, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAgentState(AgentState agentState, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(agentState, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLocation(Location location, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(location, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArtifact(Artifact artifact, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(artifact, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(artifact, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(artifact, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(artifact, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(artifact, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(artifact, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(artifact, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(artifact, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(artifact, diagnostics, context);
		if (result || diagnostics != null) result &= validateArtifact_typeHierarchy(artifact, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the typeHierarchy constraint of '<em>Artifact</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ARTIFACT__TYPE_HIERARCHY__EEXPRESSION = "type.containerTypes->includes(parent.type) or parent = null";

	/**
	 * Validates the typeHierarchy constraint of '<em>Artifact</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArtifact_typeHierarchy(Artifact artifact, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DECENTPackage.Literals.ARTIFACT,
				 artifact,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "typeHierarchy",
				 ARTIFACT__TYPE_HIERARCHY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateState(State state, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(state, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateActivity(Activity activity, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(activity, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateActivityType(ActivityType activityType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(activityType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDimension(Dimension dimension, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(dimension, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttribute(Attribute attribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(attribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateValue(Value value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(value, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntegerValue(IntegerValue integerValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(integerValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDoubleValue(DoubleValue doubleValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(doubleValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStringValue(StringValue stringValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(stringValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateListValue(ListValue listValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(listValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStringListValue(StringListValue stringListValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(stringListValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDoubleListValue(DoubleListValue doubleListValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(doubleListValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntegerListValue(IntegerListValue integerListValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(integerListValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDelta(Delta delta, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(delta, diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //DECENTValidator
