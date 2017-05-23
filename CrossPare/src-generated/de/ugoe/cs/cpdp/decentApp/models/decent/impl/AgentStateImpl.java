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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import de.ugoe.cs.cpdp.decentApp.models.decent.Activity;
import de.ugoe.cs.cpdp.decentApp.models.decent.Agent;
import de.ugoe.cs.cpdp.decentApp.models.decent.AgentState;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;
import de.ugoe.cs.cpdp.decentApp.models.decent.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Agent State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.AgentStateImpl#getDate <em>Date</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.AgentStateImpl#getAgent <em>Agent</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.AgentStateImpl#getActivities <em>Activities</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.AgentStateImpl#getNext <em>Next</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.AgentStateImpl#getPrevious <em>Previous</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.AgentStateImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AgentStateImpl extends ElementImpl implements AgentState {
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
	 * The cached value of the '{@link #getActivities() <em>Activities</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivities()
	 * @generated
	 * @ordered
	 */
	protected EList<Activity> activities;

	/**
	 * The cached value of the '{@link #getNext() <em>Next</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNext()
	 * @generated
	 * @ordered
	 */
	protected AgentState next;

	/**
	 * The cached value of the '{@link #getPrevious() <em>Previous</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrevious()
	 * @generated
	 * @ordered
	 */
	protected AgentState previous;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AgentStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DECENTPackage.Literals.AGENT_STATE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.AGENT_STATE__DATE, oldDate, date));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Agent getAgent() {
		if (eContainerFeatureID() != DECENTPackage.AGENT_STATE__AGENT) return null;
		return (Agent)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAgent(Agent newAgent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAgent, DECENTPackage.AGENT_STATE__AGENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgent(Agent newAgent) {
		if (newAgent != eInternalContainer() || (eContainerFeatureID() != DECENTPackage.AGENT_STATE__AGENT && newAgent != null)) {
			if (EcoreUtil.isAncestor(this, newAgent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAgent != null)
				msgs = ((InternalEObject)newAgent).eInverseAdd(this, DECENTPackage.AGENT__STATES, Agent.class, msgs);
			msgs = basicSetAgent(newAgent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.AGENT_STATE__AGENT, newAgent, newAgent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Activity> getActivities() {
		if (activities == null) {
			activities = new EObjectWithInverseResolvingEList<Activity>(Activity.class, this, DECENTPackage.AGENT_STATE__ACTIVITIES, DECENTPackage.ACTIVITY__AGENT_STATE);
		}
		return activities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentState getNext() {
		if (next != null && next.eIsProxy()) {
			InternalEObject oldNext = (InternalEObject)next;
			next = (AgentState)eResolveProxy(oldNext);
			if (next != oldNext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.AGENT_STATE__NEXT, oldNext, next));
			}
		}
		return next;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentState basicGetNext() {
		return next;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNext(AgentState newNext, NotificationChain msgs) {
		AgentState oldNext = next;
		next = newNext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DECENTPackage.AGENT_STATE__NEXT, oldNext, newNext);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNext(AgentState newNext) {
		if (newNext != next) {
			NotificationChain msgs = null;
			if (next != null)
				msgs = ((InternalEObject)next).eInverseRemove(this, DECENTPackage.AGENT_STATE__PREVIOUS, AgentState.class, msgs);
			if (newNext != null)
				msgs = ((InternalEObject)newNext).eInverseAdd(this, DECENTPackage.AGENT_STATE__PREVIOUS, AgentState.class, msgs);
			msgs = basicSetNext(newNext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.AGENT_STATE__NEXT, newNext, newNext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentState getPrevious() {
		if (previous != null && previous.eIsProxy()) {
			InternalEObject oldPrevious = (InternalEObject)previous;
			previous = (AgentState)eResolveProxy(oldPrevious);
			if (previous != oldPrevious) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.AGENT_STATE__PREVIOUS, oldPrevious, previous));
			}
		}
		return previous;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentState basicGetPrevious() {
		return previous;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrevious(AgentState newPrevious, NotificationChain msgs) {
		AgentState oldPrevious = previous;
		previous = newPrevious;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DECENTPackage.AGENT_STATE__PREVIOUS, oldPrevious, newPrevious);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrevious(AgentState newPrevious) {
		if (newPrevious != previous) {
			NotificationChain msgs = null;
			if (previous != null)
				msgs = ((InternalEObject)previous).eInverseRemove(this, DECENTPackage.AGENT_STATE__NEXT, AgentState.class, msgs);
			if (newPrevious != null)
				msgs = ((InternalEObject)newPrevious).eInverseAdd(this, DECENTPackage.AGENT_STATE__NEXT, AgentState.class, msgs);
			msgs = basicSetPrevious(newPrevious, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.AGENT_STATE__PREVIOUS, newPrevious, newPrevious));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Value> getValues() {
		if (values == null) {
			values = new EObjectContainmentEList<Value>(Value.class, this, DECENTPackage.AGENT_STATE__VALUES);
		}
		return values;
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
			case DECENTPackage.AGENT_STATE__AGENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAgent((Agent)otherEnd, msgs);
			case DECENTPackage.AGENT_STATE__ACTIVITIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getActivities()).basicAdd(otherEnd, msgs);
			case DECENTPackage.AGENT_STATE__NEXT:
				if (next != null)
					msgs = ((InternalEObject)next).eInverseRemove(this, DECENTPackage.AGENT_STATE__PREVIOUS, AgentState.class, msgs);
				return basicSetNext((AgentState)otherEnd, msgs);
			case DECENTPackage.AGENT_STATE__PREVIOUS:
				if (previous != null)
					msgs = ((InternalEObject)previous).eInverseRemove(this, DECENTPackage.AGENT_STATE__NEXT, AgentState.class, msgs);
				return basicSetPrevious((AgentState)otherEnd, msgs);
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
			case DECENTPackage.AGENT_STATE__AGENT:
				return basicSetAgent(null, msgs);
			case DECENTPackage.AGENT_STATE__ACTIVITIES:
				return ((InternalEList<?>)getActivities()).basicRemove(otherEnd, msgs);
			case DECENTPackage.AGENT_STATE__NEXT:
				return basicSetNext(null, msgs);
			case DECENTPackage.AGENT_STATE__PREVIOUS:
				return basicSetPrevious(null, msgs);
			case DECENTPackage.AGENT_STATE__VALUES:
				return ((InternalEList<?>)getValues()).basicRemove(otherEnd, msgs);
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
			case DECENTPackage.AGENT_STATE__AGENT:
				return eInternalContainer().eInverseRemove(this, DECENTPackage.AGENT__STATES, Agent.class, msgs);
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
			case DECENTPackage.AGENT_STATE__DATE:
				return getDate();
			case DECENTPackage.AGENT_STATE__AGENT:
				return getAgent();
			case DECENTPackage.AGENT_STATE__ACTIVITIES:
				return getActivities();
			case DECENTPackage.AGENT_STATE__NEXT:
				if (resolve) return getNext();
				return basicGetNext();
			case DECENTPackage.AGENT_STATE__PREVIOUS:
				if (resolve) return getPrevious();
				return basicGetPrevious();
			case DECENTPackage.AGENT_STATE__VALUES:
				return getValues();
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
			case DECENTPackage.AGENT_STATE__DATE:
				setDate((Date)newValue);
				return;
			case DECENTPackage.AGENT_STATE__AGENT:
				setAgent((Agent)newValue);
				return;
			case DECENTPackage.AGENT_STATE__ACTIVITIES:
				getActivities().clear();
				getActivities().addAll((Collection<? extends Activity>)newValue);
				return;
			case DECENTPackage.AGENT_STATE__NEXT:
				setNext((AgentState)newValue);
				return;
			case DECENTPackage.AGENT_STATE__PREVIOUS:
				setPrevious((AgentState)newValue);
				return;
			case DECENTPackage.AGENT_STATE__VALUES:
				getValues().clear();
				getValues().addAll((Collection<? extends Value>)newValue);
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
			case DECENTPackage.AGENT_STATE__DATE:
				setDate(DATE_EDEFAULT);
				return;
			case DECENTPackage.AGENT_STATE__AGENT:
				setAgent((Agent)null);
				return;
			case DECENTPackage.AGENT_STATE__ACTIVITIES:
				getActivities().clear();
				return;
			case DECENTPackage.AGENT_STATE__NEXT:
				setNext((AgentState)null);
				return;
			case DECENTPackage.AGENT_STATE__PREVIOUS:
				setPrevious((AgentState)null);
				return;
			case DECENTPackage.AGENT_STATE__VALUES:
				getValues().clear();
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
			case DECENTPackage.AGENT_STATE__DATE:
				return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
			case DECENTPackage.AGENT_STATE__AGENT:
				return getAgent() != null;
			case DECENTPackage.AGENT_STATE__ACTIVITIES:
				return activities != null && !activities.isEmpty();
			case DECENTPackage.AGENT_STATE__NEXT:
				return next != null;
			case DECENTPackage.AGENT_STATE__PREVIOUS:
				return previous != null;
			case DECENTPackage.AGENT_STATE__VALUES:
				return values != null && !values.isEmpty();
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

} //AgentStateImpl
