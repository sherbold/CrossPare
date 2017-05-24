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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.ugoe.cs.cpdp.decentApp.models.decent.Activity;
import de.ugoe.cs.cpdp.decentApp.models.decent.Agent;
import de.ugoe.cs.cpdp.decentApp.models.decent.AgentState;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;
import de.ugoe.cs.cpdp.decentApp.models.decent.Project;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Agent</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.AgentImpl#getEMail <em>EMail</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.AgentImpl#getProjects <em>Projects</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.AgentImpl#getActivities <em>Activities</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.AgentImpl#getStates <em>States</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AgentImpl extends ElementImpl implements Agent {
	/**
	 * The default value of the '{@link #getEMail() <em>EMail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEMail()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEMail() <em>EMail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEMail()
	 * @generated
	 * @ordered
	 */
	protected String eMail = EMAIL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProjects() <em>Projects</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjects()
	 * @generated
	 * @ordered
	 */
	protected Project projects;

	/**
	 * The cached value of the '{@link #getActivities() <em>Activities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivities()
	 * @generated
	 * @ordered
	 */
	protected EList<Activity> activities;

	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<AgentState> states;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AgentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DECENTPackage.Literals.AGENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEMail() {
		return eMail;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEMail(String newEMail) {
		String oldEMail = eMail;
		eMail = newEMail;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.AGENT__EMAIL, oldEMail, eMail));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Project getProjects() {
		if (projects != null && projects.eIsProxy()) {
			InternalEObject oldProjects = (InternalEObject)projects;
			projects = (Project)eResolveProxy(oldProjects);
			if (projects != oldProjects) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.AGENT__PROJECTS, oldProjects, projects));
			}
		}
		return projects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Project basicGetProjects() {
		return projects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProjects(Project newProjects, NotificationChain msgs) {
		Project oldProjects = projects;
		projects = newProjects;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DECENTPackage.AGENT__PROJECTS, oldProjects, newProjects);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjects(Project newProjects) {
		if (newProjects != projects) {
			NotificationChain msgs = null;
			if (projects != null)
				msgs = ((InternalEObject)projects).eInverseRemove(this, DECENTPackage.PROJECT__AGENTS, Project.class, msgs);
			if (newProjects != null)
				msgs = ((InternalEObject)newProjects).eInverseAdd(this, DECENTPackage.PROJECT__AGENTS, Project.class, msgs);
			msgs = basicSetProjects(newProjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.AGENT__PROJECTS, newProjects, newProjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Activity> getActivities() {
		if (activities == null) {
			activities = new EObjectContainmentWithInverseEList<Activity>(Activity.class, this, DECENTPackage.AGENT__ACTIVITIES, DECENTPackage.ACTIVITY__AGENT);
		}
		return activities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AgentState> getStates() {
		if (states == null) {
			states = new EObjectContainmentWithInverseEList<AgentState>(AgentState.class, this, DECENTPackage.AGENT__STATES, DECENTPackage.AGENT_STATE__AGENT);
		}
		return states;
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
			case DECENTPackage.AGENT__PROJECTS:
				if (projects != null)
					msgs = ((InternalEObject)projects).eInverseRemove(this, DECENTPackage.PROJECT__AGENTS, Project.class, msgs);
				return basicSetProjects((Project)otherEnd, msgs);
			case DECENTPackage.AGENT__ACTIVITIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getActivities()).basicAdd(otherEnd, msgs);
			case DECENTPackage.AGENT__STATES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStates()).basicAdd(otherEnd, msgs);
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
			case DECENTPackage.AGENT__PROJECTS:
				return basicSetProjects(null, msgs);
			case DECENTPackage.AGENT__ACTIVITIES:
				return ((InternalEList<?>)getActivities()).basicRemove(otherEnd, msgs);
			case DECENTPackage.AGENT__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
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
			case DECENTPackage.AGENT__EMAIL:
				return getEMail();
			case DECENTPackage.AGENT__PROJECTS:
				if (resolve) return getProjects();
				return basicGetProjects();
			case DECENTPackage.AGENT__ACTIVITIES:
				return getActivities();
			case DECENTPackage.AGENT__STATES:
				return getStates();
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
			case DECENTPackage.AGENT__EMAIL:
				setEMail((String)newValue);
				return;
			case DECENTPackage.AGENT__PROJECTS:
				setProjects((Project)newValue);
				return;
			case DECENTPackage.AGENT__ACTIVITIES:
				getActivities().clear();
				getActivities().addAll((Collection<? extends Activity>)newValue);
				return;
			case DECENTPackage.AGENT__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends AgentState>)newValue);
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
			case DECENTPackage.AGENT__EMAIL:
				setEMail(EMAIL_EDEFAULT);
				return;
			case DECENTPackage.AGENT__PROJECTS:
				setProjects((Project)null);
				return;
			case DECENTPackage.AGENT__ACTIVITIES:
				getActivities().clear();
				return;
			case DECENTPackage.AGENT__STATES:
				getStates().clear();
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
			case DECENTPackage.AGENT__EMAIL:
				return EMAIL_EDEFAULT == null ? eMail != null : !EMAIL_EDEFAULT.equals(eMail);
			case DECENTPackage.AGENT__PROJECTS:
				return projects != null;
			case DECENTPackage.AGENT__ACTIVITIES:
				return activities != null && !activities.isEmpty();
			case DECENTPackage.AGENT__STATES:
				return states != null && !states.isEmpty();
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
		result.append(" (eMail: ");
		result.append(eMail);
		result.append(')');
		return result.toString();
	}

} //AgentImpl
