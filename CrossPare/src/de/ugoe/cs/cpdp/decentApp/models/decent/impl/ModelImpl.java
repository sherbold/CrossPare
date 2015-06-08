/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType;
import de.ugoe.cs.cpdp.decentApp.models.decent.AgentPool;
import de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactTypeHierarchy;
import de.ugoe.cs.cpdp.decentApp.models.decent.AttributePool;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;
import de.ugoe.cs.cpdp.decentApp.models.decent.Dimension;
import de.ugoe.cs.cpdp.decentApp.models.decent.Model;
import de.ugoe.cs.cpdp.decentApp.models.decent.Project;
import de.ugoe.cs.cpdp.decentApp.models.decent.Step;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ModelImpl#getContent <em>Content</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ModelImpl#getSteps <em>Steps</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ModelImpl#getProjects <em>Projects</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ModelImpl#getArtifactTypeHierarchy <em>Artifact Type Hierarchy</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ModelImpl#getAgentPool <em>Agent Pool</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ModelImpl#getAttributePool <em>Attribute Pool</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ModelImpl#getActivityTypes <em>Activity Types</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ModelImpl#getDimensions <em>Dimensions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelImpl extends MinimalEObjectImpl.Container implements Model {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected EList<String> content;

	/**
	 * The cached value of the '{@link #getSteps() <em>Steps</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSteps()
	 * @generated
	 * @ordered
	 */
	protected EList<Step> steps;

	/**
	 * The cached value of the '{@link #getProjects() <em>Projects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<Project> projects;

	/**
	 * The cached value of the '{@link #getArtifactTypeHierarchy() <em>Artifact Type Hierarchy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactTypeHierarchy()
	 * @generated
	 * @ordered
	 */
	protected ArtifactTypeHierarchy artifactTypeHierarchy;

	/**
	 * The cached value of the '{@link #getAgentPool() <em>Agent Pool</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAgentPool()
	 * @generated
	 * @ordered
	 */
	protected AgentPool agentPool;

	/**
	 * The cached value of the '{@link #getAttributePool() <em>Attribute Pool</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributePool()
	 * @generated
	 * @ordered
	 */
	protected AttributePool attributePool;

	/**
	 * The cached value of the '{@link #getActivityTypes() <em>Activity Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivityTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<ActivityType> activityTypes;

	/**
	 * The cached value of the '{@link #getDimensions() <em>Dimensions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDimensions()
	 * @generated
	 * @ordered
	 */
	protected EList<Dimension> dimensions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DECENTPackage.Literals.MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.MODEL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getContent() {
		if (content == null) {
			content = new EDataTypeUniqueEList<String>(String.class, this, DECENTPackage.MODEL__CONTENT);
		}
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Step> getSteps() {
		if (steps == null) {
			steps = new EObjectContainmentEList<Step>(Step.class, this, DECENTPackage.MODEL__STEPS);
		}
		return steps;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Project> getProjects() {
		if (projects == null) {
			projects = new EObjectContainmentEList<Project>(Project.class, this, DECENTPackage.MODEL__PROJECTS);
		}
		return projects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactTypeHierarchy getArtifactTypeHierarchy() {
		return artifactTypeHierarchy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArtifactTypeHierarchy(ArtifactTypeHierarchy newArtifactTypeHierarchy, NotificationChain msgs) {
		ArtifactTypeHierarchy oldArtifactTypeHierarchy = artifactTypeHierarchy;
		artifactTypeHierarchy = newArtifactTypeHierarchy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DECENTPackage.MODEL__ARTIFACT_TYPE_HIERARCHY, oldArtifactTypeHierarchy, newArtifactTypeHierarchy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifactTypeHierarchy(ArtifactTypeHierarchy newArtifactTypeHierarchy) {
		if (newArtifactTypeHierarchy != artifactTypeHierarchy) {
			NotificationChain msgs = null;
			if (artifactTypeHierarchy != null)
				msgs = ((InternalEObject)artifactTypeHierarchy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DECENTPackage.MODEL__ARTIFACT_TYPE_HIERARCHY, null, msgs);
			if (newArtifactTypeHierarchy != null)
				msgs = ((InternalEObject)newArtifactTypeHierarchy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DECENTPackage.MODEL__ARTIFACT_TYPE_HIERARCHY, null, msgs);
			msgs = basicSetArtifactTypeHierarchy(newArtifactTypeHierarchy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.MODEL__ARTIFACT_TYPE_HIERARCHY, newArtifactTypeHierarchy, newArtifactTypeHierarchy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentPool getAgentPool() {
		return agentPool;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAgentPool(AgentPool newAgentPool, NotificationChain msgs) {
		AgentPool oldAgentPool = agentPool;
		agentPool = newAgentPool;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DECENTPackage.MODEL__AGENT_POOL, oldAgentPool, newAgentPool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgentPool(AgentPool newAgentPool) {
		if (newAgentPool != agentPool) {
			NotificationChain msgs = null;
			if (agentPool != null)
				msgs = ((InternalEObject)agentPool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DECENTPackage.MODEL__AGENT_POOL, null, msgs);
			if (newAgentPool != null)
				msgs = ((InternalEObject)newAgentPool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DECENTPackage.MODEL__AGENT_POOL, null, msgs);
			msgs = basicSetAgentPool(newAgentPool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.MODEL__AGENT_POOL, newAgentPool, newAgentPool));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributePool getAttributePool() {
		return attributePool;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributePool(AttributePool newAttributePool, NotificationChain msgs) {
		AttributePool oldAttributePool = attributePool;
		attributePool = newAttributePool;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DECENTPackage.MODEL__ATTRIBUTE_POOL, oldAttributePool, newAttributePool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributePool(AttributePool newAttributePool) {
		if (newAttributePool != attributePool) {
			NotificationChain msgs = null;
			if (attributePool != null)
				msgs = ((InternalEObject)attributePool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DECENTPackage.MODEL__ATTRIBUTE_POOL, null, msgs);
			if (newAttributePool != null)
				msgs = ((InternalEObject)newAttributePool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DECENTPackage.MODEL__ATTRIBUTE_POOL, null, msgs);
			msgs = basicSetAttributePool(newAttributePool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.MODEL__ATTRIBUTE_POOL, newAttributePool, newAttributePool));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ActivityType> getActivityTypes() {
		if (activityTypes == null) {
			activityTypes = new EObjectContainmentEList<ActivityType>(ActivityType.class, this, DECENTPackage.MODEL__ACTIVITY_TYPES);
		}
		return activityTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Dimension> getDimensions() {
		if (dimensions == null) {
			dimensions = new EObjectContainmentEList<Dimension>(Dimension.class, this, DECENTPackage.MODEL__DIMENSIONS);
		}
		return dimensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DECENTPackage.MODEL__STEPS:
				return ((InternalEList<?>)getSteps()).basicRemove(otherEnd, msgs);
			case DECENTPackage.MODEL__PROJECTS:
				return ((InternalEList<?>)getProjects()).basicRemove(otherEnd, msgs);
			case DECENTPackage.MODEL__ARTIFACT_TYPE_HIERARCHY:
				return basicSetArtifactTypeHierarchy(null, msgs);
			case DECENTPackage.MODEL__AGENT_POOL:
				return basicSetAgentPool(null, msgs);
			case DECENTPackage.MODEL__ATTRIBUTE_POOL:
				return basicSetAttributePool(null, msgs);
			case DECENTPackage.MODEL__ACTIVITY_TYPES:
				return ((InternalEList<?>)getActivityTypes()).basicRemove(otherEnd, msgs);
			case DECENTPackage.MODEL__DIMENSIONS:
				return ((InternalEList<?>)getDimensions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DECENTPackage.MODEL__NAME:
				return getName();
			case DECENTPackage.MODEL__CONTENT:
				return getContent();
			case DECENTPackage.MODEL__STEPS:
				return getSteps();
			case DECENTPackage.MODEL__PROJECTS:
				return getProjects();
			case DECENTPackage.MODEL__ARTIFACT_TYPE_HIERARCHY:
				return getArtifactTypeHierarchy();
			case DECENTPackage.MODEL__AGENT_POOL:
				return getAgentPool();
			case DECENTPackage.MODEL__ATTRIBUTE_POOL:
				return getAttributePool();
			case DECENTPackage.MODEL__ACTIVITY_TYPES:
				return getActivityTypes();
			case DECENTPackage.MODEL__DIMENSIONS:
				return getDimensions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DECENTPackage.MODEL__NAME:
				setName((String)newValue);
				return;
			case DECENTPackage.MODEL__CONTENT:
				getContent().clear();
				getContent().addAll((Collection<? extends String>)newValue);
				return;
			case DECENTPackage.MODEL__STEPS:
				getSteps().clear();
				getSteps().addAll((Collection<? extends Step>)newValue);
				return;
			case DECENTPackage.MODEL__PROJECTS:
				getProjects().clear();
				getProjects().addAll((Collection<? extends Project>)newValue);
				return;
			case DECENTPackage.MODEL__ARTIFACT_TYPE_HIERARCHY:
				setArtifactTypeHierarchy((ArtifactTypeHierarchy)newValue);
				return;
			case DECENTPackage.MODEL__AGENT_POOL:
				setAgentPool((AgentPool)newValue);
				return;
			case DECENTPackage.MODEL__ATTRIBUTE_POOL:
				setAttributePool((AttributePool)newValue);
				return;
			case DECENTPackage.MODEL__ACTIVITY_TYPES:
				getActivityTypes().clear();
				getActivityTypes().addAll((Collection<? extends ActivityType>)newValue);
				return;
			case DECENTPackage.MODEL__DIMENSIONS:
				getDimensions().clear();
				getDimensions().addAll((Collection<? extends Dimension>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DECENTPackage.MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DECENTPackage.MODEL__CONTENT:
				getContent().clear();
				return;
			case DECENTPackage.MODEL__STEPS:
				getSteps().clear();
				return;
			case DECENTPackage.MODEL__PROJECTS:
				getProjects().clear();
				return;
			case DECENTPackage.MODEL__ARTIFACT_TYPE_HIERARCHY:
				setArtifactTypeHierarchy((ArtifactTypeHierarchy)null);
				return;
			case DECENTPackage.MODEL__AGENT_POOL:
				setAgentPool((AgentPool)null);
				return;
			case DECENTPackage.MODEL__ATTRIBUTE_POOL:
				setAttributePool((AttributePool)null);
				return;
			case DECENTPackage.MODEL__ACTIVITY_TYPES:
				getActivityTypes().clear();
				return;
			case DECENTPackage.MODEL__DIMENSIONS:
				getDimensions().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DECENTPackage.MODEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DECENTPackage.MODEL__CONTENT:
				return content != null && !content.isEmpty();
			case DECENTPackage.MODEL__STEPS:
				return steps != null && !steps.isEmpty();
			case DECENTPackage.MODEL__PROJECTS:
				return projects != null && !projects.isEmpty();
			case DECENTPackage.MODEL__ARTIFACT_TYPE_HIERARCHY:
				return artifactTypeHierarchy != null;
			case DECENTPackage.MODEL__AGENT_POOL:
				return agentPool != null;
			case DECENTPackage.MODEL__ATTRIBUTE_POOL:
				return attributePool != null;
			case DECENTPackage.MODEL__ACTIVITY_TYPES:
				return activityTypes != null && !activityTypes.isEmpty();
			case DECENTPackage.MODEL__DIMENSIONS:
				return dimensions != null && !dimensions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", content: ");
		result.append(content);
		result.append(')');
		return result.toString();
	}

} //ModelImpl
