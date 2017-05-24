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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import de.ugoe.cs.cpdp.decentApp.models.decent.Artifact;
import de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType;
import de.ugoe.cs.cpdp.decentApp.models.decent.Attribute;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;
import de.ugoe.cs.cpdp.decentApp.models.decent.State;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artifact</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ArtifactImpl#getType <em>Type</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ArtifactImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ArtifactImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ArtifactImpl#getStates <em>States</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ArtifactImpl#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArtifactImpl extends ElementImpl implements Artifact {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ArtifactType type;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Artifact> children;

	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> states;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArtifactImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DECENTPackage.Literals.ARTIFACT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (ArtifactType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.ARTIFACT__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ArtifactType newType) {
		ArtifactType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.ARTIFACT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Artifact getParent() {
		if (eContainerFeatureID() != DECENTPackage.ARTIFACT__PARENT) return null;
		return (Artifact)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(Artifact newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, DECENTPackage.ARTIFACT__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Artifact newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != DECENTPackage.ARTIFACT__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, DECENTPackage.ARTIFACT__CHILDREN, Artifact.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.ARTIFACT__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Artifact> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<Artifact>(Artifact.class, this, DECENTPackage.ARTIFACT__CHILDREN, DECENTPackage.ARTIFACT__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getStates() {
		if (states == null) {
			states = new EObjectContainmentWithInverseEList<State>(State.class, this, DECENTPackage.ARTIFACT__STATES, DECENTPackage.STATE__ARTIFACT);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectResolvingEList<Attribute>(Attribute.class, this, DECENTPackage.ARTIFACT__ATTRIBUTES);
		}
		return attributes;
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
			case DECENTPackage.ARTIFACT__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((Artifact)otherEnd, msgs);
			case DECENTPackage.ARTIFACT__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
			case DECENTPackage.ARTIFACT__STATES:
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
			case DECENTPackage.ARTIFACT__PARENT:
				return basicSetParent(null, msgs);
			case DECENTPackage.ARTIFACT__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case DECENTPackage.ARTIFACT__STATES:
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
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case DECENTPackage.ARTIFACT__PARENT:
				return eInternalContainer().eInverseRemove(this, DECENTPackage.ARTIFACT__CHILDREN, Artifact.class, msgs);
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
			case DECENTPackage.ARTIFACT__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case DECENTPackage.ARTIFACT__PARENT:
				return getParent();
			case DECENTPackage.ARTIFACT__CHILDREN:
				return getChildren();
			case DECENTPackage.ARTIFACT__STATES:
				return getStates();
			case DECENTPackage.ARTIFACT__ATTRIBUTES:
				return getAttributes();
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
			case DECENTPackage.ARTIFACT__TYPE:
				setType((ArtifactType)newValue);
				return;
			case DECENTPackage.ARTIFACT__PARENT:
				setParent((Artifact)newValue);
				return;
			case DECENTPackage.ARTIFACT__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends Artifact>)newValue);
				return;
			case DECENTPackage.ARTIFACT__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends State>)newValue);
				return;
			case DECENTPackage.ARTIFACT__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends Attribute>)newValue);
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
			case DECENTPackage.ARTIFACT__TYPE:
				setType((ArtifactType)null);
				return;
			case DECENTPackage.ARTIFACT__PARENT:
				setParent((Artifact)null);
				return;
			case DECENTPackage.ARTIFACT__CHILDREN:
				getChildren().clear();
				return;
			case DECENTPackage.ARTIFACT__STATES:
				getStates().clear();
				return;
			case DECENTPackage.ARTIFACT__ATTRIBUTES:
				getAttributes().clear();
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
			case DECENTPackage.ARTIFACT__TYPE:
				return type != null;
			case DECENTPackage.ARTIFACT__PARENT:
				return getParent() != null;
			case DECENTPackage.ARTIFACT__CHILDREN:
				return children != null && !children.isEmpty();
			case DECENTPackage.ARTIFACT__STATES:
				return states != null && !states.isEmpty();
			case DECENTPackage.ARTIFACT__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ArtifactImpl
