/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import de.ugoe.cs.cpdp.decentApp.models.decent.Activity;
import de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType;
import de.ugoe.cs.cpdp.decentApp.models.decent.Agent;
import de.ugoe.cs.cpdp.decentApp.models.decent.AgentState;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;
import de.ugoe.cs.cpdp.decentApp.models.decent.Delta;
import de.ugoe.cs.cpdp.decentApp.models.decent.State;
import de.ugoe.cs.cpdp.decentApp.models.decent.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ActivityImpl#getType <em>Type</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ActivityImpl#getAgent <em>Agent</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ActivityImpl#getState <em>State</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ActivityImpl#getTargetState <em>Target State</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ActivityImpl#getDeltas <em>Deltas</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ActivityImpl#getDate <em>Date</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ActivityImpl#getValues <em>Values</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ActivityImpl#getAgentState <em>Agent State</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActivityImpl extends ElementImpl implements Activity {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EList<ActivityType> type;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected State state;

	/**
	 * The cached value of the '{@link #getTargetState() <em>Target State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetState()
	 * @generated
	 * @ordered
	 */
	protected State targetState;

	/**
	 * The cached value of the '{@link #getDeltas() <em>Deltas</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltas()
	 * @generated
	 * @ordered
	 */
	protected EList<Delta> deltas;

	/**
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected Date date = DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<Value> values;

	/**
	 * The cached value of the '{@link #getAgentState() <em>Agent State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAgentState()
	 * @generated
	 * @ordered
	 */
	protected AgentState agentState;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActivityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DECENTPackage.Literals.ACTIVITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ActivityType> getType() {
		if (type == null) {
			type = new EObjectResolvingEList<ActivityType>(ActivityType.class, this, DECENTPackage.ACTIVITY__TYPE);
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Agent getAgent() {
		if (eContainerFeatureID() != DECENTPackage.ACTIVITY__AGENT) return null;
		return (Agent)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAgent(Agent newAgent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAgent, DECENTPackage.ACTIVITY__AGENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgent(Agent newAgent) {
		if (newAgent != eInternalContainer() || (eContainerFeatureID() != DECENTPackage.ACTIVITY__AGENT && newAgent != null)) {
			if (EcoreUtil.isAncestor(this, newAgent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAgent != null)
				msgs = ((InternalEObject)newAgent).eInverseAdd(this, DECENTPackage.AGENT__ACTIVITIES, Agent.class, msgs);
			msgs = basicSetAgent(newAgent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.ACTIVITY__AGENT, newAgent, newAgent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getState() {
		if (state != null && state.eIsProxy()) {
			InternalEObject oldState = (InternalEObject)state;
			state = (State)eResolveProxy(oldState);
			if (state != oldState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.ACTIVITY__STATE, oldState, state));
			}
		}
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetState(State newState, NotificationChain msgs) {
		State oldState = state;
		state = newState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DECENTPackage.ACTIVITY__STATE, oldState, newState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(State newState) {
		if (newState != state) {
			NotificationChain msgs = null;
			if (state != null)
				msgs = ((InternalEObject)state).eInverseRemove(this, DECENTPackage.STATE__ACTIVITY, State.class, msgs);
			if (newState != null)
				msgs = ((InternalEObject)newState).eInverseAdd(this, DECENTPackage.STATE__ACTIVITY, State.class, msgs);
			msgs = basicSetState(newState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.ACTIVITY__STATE, newState, newState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getTargetState() {
		if (targetState != null && targetState.eIsProxy()) {
			InternalEObject oldTargetState = (InternalEObject)targetState;
			targetState = (State)eResolveProxy(oldTargetState);
			if (targetState != oldTargetState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.ACTIVITY__TARGET_STATE, oldTargetState, targetState));
			}
		}
		return targetState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetTargetState() {
		return targetState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTargetState(State newTargetState, NotificationChain msgs) {
		State oldTargetState = targetState;
		targetState = newTargetState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DECENTPackage.ACTIVITY__TARGET_STATE, oldTargetState, newTargetState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetState(State newTargetState) {
		if (newTargetState != targetState) {
			NotificationChain msgs = null;
			if (targetState != null)
				msgs = ((InternalEObject)targetState).eInverseRemove(this, DECENTPackage.STATE__FROM_ACTIVITY, State.class, msgs);
			if (newTargetState != null)
				msgs = ((InternalEObject)newTargetState).eInverseAdd(this, DECENTPackage.STATE__FROM_ACTIVITY, State.class, msgs);
			msgs = basicSetTargetState(newTargetState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.ACTIVITY__TARGET_STATE, newTargetState, newTargetState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Delta> getDeltas() {
		if (deltas == null) {
			deltas = new EObjectContainmentWithInverseEList<Delta>(Delta.class, this, DECENTPackage.ACTIVITY__DELTAS, DECENTPackage.DELTA__ACTIVITY);
		}
		return deltas;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDate(Date newDate) {
		Date oldDate = date;
		date = newDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.ACTIVITY__DATE, oldDate, date));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Value> getValues() {
		if (values == null) {
			values = new EObjectContainmentEList<Value>(Value.class, this, DECENTPackage.ACTIVITY__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentState getAgentState() {
		if (agentState != null && agentState.eIsProxy()) {
			InternalEObject oldAgentState = (InternalEObject)agentState;
			agentState = (AgentState)eResolveProxy(oldAgentState);
			if (agentState != oldAgentState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.ACTIVITY__AGENT_STATE, oldAgentState, agentState));
			}
		}
		return agentState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentState basicGetAgentState() {
		return agentState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAgentState(AgentState newAgentState, NotificationChain msgs) {
		AgentState oldAgentState = agentState;
		agentState = newAgentState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DECENTPackage.ACTIVITY__AGENT_STATE, oldAgentState, newAgentState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgentState(AgentState newAgentState) {
		if (newAgentState != agentState) {
			NotificationChain msgs = null;
			if (agentState != null)
				msgs = ((InternalEObject)agentState).eInverseRemove(this, DECENTPackage.AGENT_STATE__ACTIVITIES, AgentState.class, msgs);
			if (newAgentState != null)
				msgs = ((InternalEObject)newAgentState).eInverseAdd(this, DECENTPackage.AGENT_STATE__ACTIVITIES, AgentState.class, msgs);
			msgs = basicSetAgentState(newAgentState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.ACTIVITY__AGENT_STATE, newAgentState, newAgentState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DECENTPackage.ACTIVITY__AGENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAgent((Agent)otherEnd, msgs);
			case DECENTPackage.ACTIVITY__STATE:
				if (state != null)
					msgs = ((InternalEObject)state).eInverseRemove(this, DECENTPackage.STATE__ACTIVITY, State.class, msgs);
				return basicSetState((State)otherEnd, msgs);
			case DECENTPackage.ACTIVITY__TARGET_STATE:
				if (targetState != null)
					msgs = ((InternalEObject)targetState).eInverseRemove(this, DECENTPackage.STATE__FROM_ACTIVITY, State.class, msgs);
				return basicSetTargetState((State)otherEnd, msgs);
			case DECENTPackage.ACTIVITY__DELTAS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDeltas()).basicAdd(otherEnd, msgs);
			case DECENTPackage.ACTIVITY__AGENT_STATE:
				if (agentState != null)
					msgs = ((InternalEObject)agentState).eInverseRemove(this, DECENTPackage.AGENT_STATE__ACTIVITIES, AgentState.class, msgs);
				return basicSetAgentState((AgentState)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DECENTPackage.ACTIVITY__AGENT:
				return basicSetAgent(null, msgs);
			case DECENTPackage.ACTIVITY__STATE:
				return basicSetState(null, msgs);
			case DECENTPackage.ACTIVITY__TARGET_STATE:
				return basicSetTargetState(null, msgs);
			case DECENTPackage.ACTIVITY__DELTAS:
				return ((InternalEList<?>)getDeltas()).basicRemove(otherEnd, msgs);
			case DECENTPackage.ACTIVITY__VALUES:
				return ((InternalEList<?>)getValues()).basicRemove(otherEnd, msgs);
			case DECENTPackage.ACTIVITY__AGENT_STATE:
				return basicSetAgentState(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case DECENTPackage.ACTIVITY__AGENT:
				return eInternalContainer().eInverseRemove(this, DECENTPackage.AGENT__ACTIVITIES, Agent.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DECENTPackage.ACTIVITY__TYPE:
				return getType();
			case DECENTPackage.ACTIVITY__AGENT:
				return getAgent();
			case DECENTPackage.ACTIVITY__STATE:
				if (resolve) return getState();
				return basicGetState();
			case DECENTPackage.ACTIVITY__TARGET_STATE:
				if (resolve) return getTargetState();
				return basicGetTargetState();
			case DECENTPackage.ACTIVITY__DELTAS:
				return getDeltas();
			case DECENTPackage.ACTIVITY__DATE:
				return getDate();
			case DECENTPackage.ACTIVITY__VALUES:
				return getValues();
			case DECENTPackage.ACTIVITY__AGENT_STATE:
				if (resolve) return getAgentState();
				return basicGetAgentState();
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
			case DECENTPackage.ACTIVITY__TYPE:
				getType().clear();
				getType().addAll((Collection<? extends ActivityType>)newValue);
				return;
			case DECENTPackage.ACTIVITY__AGENT:
				setAgent((Agent)newValue);
				return;
			case DECENTPackage.ACTIVITY__STATE:
				setState((State)newValue);
				return;
			case DECENTPackage.ACTIVITY__TARGET_STATE:
				setTargetState((State)newValue);
				return;
			case DECENTPackage.ACTIVITY__DELTAS:
				getDeltas().clear();
				getDeltas().addAll((Collection<? extends Delta>)newValue);
				return;
			case DECENTPackage.ACTIVITY__DATE:
				setDate((Date)newValue);
				return;
			case DECENTPackage.ACTIVITY__VALUES:
				getValues().clear();
				getValues().addAll((Collection<? extends Value>)newValue);
				return;
			case DECENTPackage.ACTIVITY__AGENT_STATE:
				setAgentState((AgentState)newValue);
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
			case DECENTPackage.ACTIVITY__TYPE:
				getType().clear();
				return;
			case DECENTPackage.ACTIVITY__AGENT:
				setAgent((Agent)null);
				return;
			case DECENTPackage.ACTIVITY__STATE:
				setState((State)null);
				return;
			case DECENTPackage.ACTIVITY__TARGET_STATE:
				setTargetState((State)null);
				return;
			case DECENTPackage.ACTIVITY__DELTAS:
				getDeltas().clear();
				return;
			case DECENTPackage.ACTIVITY__DATE:
				setDate(DATE_EDEFAULT);
				return;
			case DECENTPackage.ACTIVITY__VALUES:
				getValues().clear();
				return;
			case DECENTPackage.ACTIVITY__AGENT_STATE:
				setAgentState((AgentState)null);
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
			case DECENTPackage.ACTIVITY__TYPE:
				return type != null && !type.isEmpty();
			case DECENTPackage.ACTIVITY__AGENT:
				return getAgent() != null;
			case DECENTPackage.ACTIVITY__STATE:
				return state != null;
			case DECENTPackage.ACTIVITY__TARGET_STATE:
				return targetState != null;
			case DECENTPackage.ACTIVITY__DELTAS:
				return deltas != null && !deltas.isEmpty();
			case DECENTPackage.ACTIVITY__DATE:
				return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
			case DECENTPackage.ACTIVITY__VALUES:
				return values != null && !values.isEmpty();
			case DECENTPackage.ACTIVITY__AGENT_STATE:
				return agentState != null;
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
		result.append(" (date: ");
		result.append(date);
		result.append(')');
		return result.toString();
	}

} //ActivityImpl
