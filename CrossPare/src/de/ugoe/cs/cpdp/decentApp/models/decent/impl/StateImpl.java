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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import de.ugoe.cs.cpdp.decentApp.models.decent.Activity;
import de.ugoe.cs.cpdp.decentApp.models.decent.Artifact;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;
import de.ugoe.cs.cpdp.decentApp.models.decent.State;
import de.ugoe.cs.cpdp.decentApp.models.decent.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.StateImpl#getArtifact <em>Artifact</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.StateImpl#getActivity <em>Activity</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.StateImpl#getFromActivity <em>From Activity</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.StateImpl#getNext <em>Next</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.StateImpl#getPrevious <em>Previous</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.StateImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.StateImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.StateImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateImpl extends ElementImpl implements State {
	/**
	 * The cached value of the '{@link #getActivity() <em>Activity</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivity()
	 * @generated
	 * @ordered
	 */
	protected EList<Activity> activity;

	/**
	 * The cached value of the '{@link #getFromActivity() <em>From Activity</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromActivity()
	 * @generated
	 * @ordered
	 */
	protected EList<Activity> fromActivity;

	/**
	 * The cached value of the '{@link #getNext() <em>Next</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNext()
	 * @generated
	 * @ordered
	 */
	protected EList<State> next;

	/**
	 * The cached value of the '{@link #getPrevious() <em>Previous</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrevious()
	 * @generated
	 * @ordered
	 */
	protected EList<State> previous;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected State parent;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<State> children;

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
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DECENTPackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Artifact getArtifact() {
		if (eContainerFeatureID() != DECENTPackage.STATE__ARTIFACT) return null;
		return (Artifact)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArtifact(Artifact newArtifact, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newArtifact, DECENTPackage.STATE__ARTIFACT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifact(Artifact newArtifact) {
		if (newArtifact != eInternalContainer() || (eContainerFeatureID() != DECENTPackage.STATE__ARTIFACT && newArtifact != null)) {
			if (EcoreUtil.isAncestor(this, newArtifact))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newArtifact != null)
				msgs = ((InternalEObject)newArtifact).eInverseAdd(this, DECENTPackage.ARTIFACT__STATES, Artifact.class, msgs);
			msgs = basicSetArtifact(newArtifact, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.STATE__ARTIFACT, newArtifact, newArtifact));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Activity> getActivity() {
		if (activity == null) {
			activity = new EObjectWithInverseResolvingEList<Activity>(Activity.class, this, DECENTPackage.STATE__ACTIVITY, DECENTPackage.ACTIVITY__STATE);
		}
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Activity> getFromActivity() {
		if (fromActivity == null) {
			fromActivity = new EObjectWithInverseResolvingEList<Activity>(Activity.class, this, DECENTPackage.STATE__FROM_ACTIVITY, DECENTPackage.ACTIVITY__TARGET_STATE);
		}
		return fromActivity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getNext() {
		if (next == null) {
			next = new EObjectWithInverseResolvingEList.ManyInverse<State>(State.class, this, DECENTPackage.STATE__NEXT, DECENTPackage.STATE__PREVIOUS);
		}
		return next;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getPrevious() {
		if (previous == null) {
			previous = new EObjectWithInverseResolvingEList.ManyInverse<State>(State.class, this, DECENTPackage.STATE__PREVIOUS, DECENTPackage.STATE__NEXT);
		}
		return previous;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (State)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.STATE__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(State newParent, NotificationChain msgs) {
		State oldParent = parent;
		parent = newParent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DECENTPackage.STATE__PARENT, oldParent, newParent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(State newParent) {
		if (newParent != parent) {
			NotificationChain msgs = null;
			if (parent != null)
				msgs = ((InternalEObject)parent).eInverseRemove(this, DECENTPackage.STATE__CHILDREN, State.class, msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, DECENTPackage.STATE__CHILDREN, State.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.STATE__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getChildren() {
		if (children == null) {
			children = new EObjectWithInverseResolvingEList<State>(State.class, this, DECENTPackage.STATE__CHILDREN, DECENTPackage.STATE__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Value> getValues() {
		if (values == null) {
			values = new EObjectContainmentEList<Value>(Value.class, this, DECENTPackage.STATE__VALUES);
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
			case DECENTPackage.STATE__ARTIFACT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetArtifact((Artifact)otherEnd, msgs);
			case DECENTPackage.STATE__ACTIVITY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getActivity()).basicAdd(otherEnd, msgs);
			case DECENTPackage.STATE__FROM_ACTIVITY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFromActivity()).basicAdd(otherEnd, msgs);
			case DECENTPackage.STATE__NEXT:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getNext()).basicAdd(otherEnd, msgs);
			case DECENTPackage.STATE__PREVIOUS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPrevious()).basicAdd(otherEnd, msgs);
			case DECENTPackage.STATE__PARENT:
				if (parent != null)
					msgs = ((InternalEObject)parent).eInverseRemove(this, DECENTPackage.STATE__CHILDREN, State.class, msgs);
				return basicSetParent((State)otherEnd, msgs);
			case DECENTPackage.STATE__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
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
			case DECENTPackage.STATE__ARTIFACT:
				return basicSetArtifact(null, msgs);
			case DECENTPackage.STATE__ACTIVITY:
				return ((InternalEList<?>)getActivity()).basicRemove(otherEnd, msgs);
			case DECENTPackage.STATE__FROM_ACTIVITY:
				return ((InternalEList<?>)getFromActivity()).basicRemove(otherEnd, msgs);
			case DECENTPackage.STATE__NEXT:
				return ((InternalEList<?>)getNext()).basicRemove(otherEnd, msgs);
			case DECENTPackage.STATE__PREVIOUS:
				return ((InternalEList<?>)getPrevious()).basicRemove(otherEnd, msgs);
			case DECENTPackage.STATE__PARENT:
				return basicSetParent(null, msgs);
			case DECENTPackage.STATE__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case DECENTPackage.STATE__VALUES:
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
			case DECENTPackage.STATE__ARTIFACT:
				return eInternalContainer().eInverseRemove(this, DECENTPackage.ARTIFACT__STATES, Artifact.class, msgs);
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
			case DECENTPackage.STATE__ARTIFACT:
				return getArtifact();
			case DECENTPackage.STATE__ACTIVITY:
				return getActivity();
			case DECENTPackage.STATE__FROM_ACTIVITY:
				return getFromActivity();
			case DECENTPackage.STATE__NEXT:
				return getNext();
			case DECENTPackage.STATE__PREVIOUS:
				return getPrevious();
			case DECENTPackage.STATE__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case DECENTPackage.STATE__CHILDREN:
				return getChildren();
			case DECENTPackage.STATE__VALUES:
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
			case DECENTPackage.STATE__ARTIFACT:
				setArtifact((Artifact)newValue);
				return;
			case DECENTPackage.STATE__ACTIVITY:
				getActivity().clear();
				getActivity().addAll((Collection<? extends Activity>)newValue);
				return;
			case DECENTPackage.STATE__FROM_ACTIVITY:
				getFromActivity().clear();
				getFromActivity().addAll((Collection<? extends Activity>)newValue);
				return;
			case DECENTPackage.STATE__NEXT:
				getNext().clear();
				getNext().addAll((Collection<? extends State>)newValue);
				return;
			case DECENTPackage.STATE__PREVIOUS:
				getPrevious().clear();
				getPrevious().addAll((Collection<? extends State>)newValue);
				return;
			case DECENTPackage.STATE__PARENT:
				setParent((State)newValue);
				return;
			case DECENTPackage.STATE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends State>)newValue);
				return;
			case DECENTPackage.STATE__VALUES:
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
			case DECENTPackage.STATE__ARTIFACT:
				setArtifact((Artifact)null);
				return;
			case DECENTPackage.STATE__ACTIVITY:
				getActivity().clear();
				return;
			case DECENTPackage.STATE__FROM_ACTIVITY:
				getFromActivity().clear();
				return;
			case DECENTPackage.STATE__NEXT:
				getNext().clear();
				return;
			case DECENTPackage.STATE__PREVIOUS:
				getPrevious().clear();
				return;
			case DECENTPackage.STATE__PARENT:
				setParent((State)null);
				return;
			case DECENTPackage.STATE__CHILDREN:
				getChildren().clear();
				return;
			case DECENTPackage.STATE__VALUES:
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
			case DECENTPackage.STATE__ARTIFACT:
				return getArtifact() != null;
			case DECENTPackage.STATE__ACTIVITY:
				return activity != null && !activity.isEmpty();
			case DECENTPackage.STATE__FROM_ACTIVITY:
				return fromActivity != null && !fromActivity.isEmpty();
			case DECENTPackage.STATE__NEXT:
				return next != null && !next.isEmpty();
			case DECENTPackage.STATE__PREVIOUS:
				return previous != null && !previous.isEmpty();
			case DECENTPackage.STATE__PARENT:
				return parent != null;
			case DECENTPackage.STATE__CHILDREN:
				return children != null && !children.isEmpty();
			case DECENTPackage.STATE__VALUES:
				return values != null && !values.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StateImpl
