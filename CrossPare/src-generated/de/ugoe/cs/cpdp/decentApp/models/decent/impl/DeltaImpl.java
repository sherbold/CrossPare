/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import de.ugoe.cs.cpdp.decentApp.models.decent.Activity;
import de.ugoe.cs.cpdp.decentApp.models.decent.Attribute;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;
import de.ugoe.cs.cpdp.decentApp.models.decent.Delta;
import de.ugoe.cs.cpdp.decentApp.models.decent.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.DeltaImpl#getActivity <em>Activity</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.DeltaImpl#getOnAttribute <em>On Attribute</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.DeltaImpl#getTargetValue <em>Target Value</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.DeltaImpl#getSourceValue <em>Source Value</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.DeltaImpl#getChange <em>Change</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeltaImpl extends MinimalEObjectImpl.Container implements Delta {
	/**
	 * The cached value of the '{@link #getOnAttribute() <em>On Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnAttribute()
	 * @generated
	 * @ordered
	 */
	protected Attribute onAttribute;

	/**
	 * The cached value of the '{@link #getTargetValue() <em>Target Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetValue()
	 * @generated
	 * @ordered
	 */
	protected Value targetValue;

	/**
	 * The cached value of the '{@link #getSourceValue() <em>Source Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceValue()
	 * @generated
	 * @ordered
	 */
	protected Value sourceValue;

	/**
	 * The default value of the '{@link #getChange() <em>Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChange()
	 * @generated
	 * @ordered
	 */
	protected static final double CHANGE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getChange() <em>Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChange()
	 * @generated
	 * @ordered
	 */
	protected double change = CHANGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DECENTPackage.Literals.DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity getActivity() {
		if (eContainerFeatureID() != DECENTPackage.DELTA__ACTIVITY) return null;
		return (Activity)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivity(Activity newActivity, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newActivity, DECENTPackage.DELTA__ACTIVITY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivity(Activity newActivity) {
		if (newActivity != eInternalContainer() || (eContainerFeatureID() != DECENTPackage.DELTA__ACTIVITY && newActivity != null)) {
			if (EcoreUtil.isAncestor(this, newActivity))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newActivity != null)
				msgs = ((InternalEObject)newActivity).eInverseAdd(this, DECENTPackage.ACTIVITY__DELTAS, Activity.class, msgs);
			msgs = basicSetActivity(newActivity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.DELTA__ACTIVITY, newActivity, newActivity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute getOnAttribute() {
		if (onAttribute != null && onAttribute.eIsProxy()) {
			InternalEObject oldOnAttribute = (InternalEObject)onAttribute;
			onAttribute = (Attribute)eResolveProxy(oldOnAttribute);
			if (onAttribute != oldOnAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.DELTA__ON_ATTRIBUTE, oldOnAttribute, onAttribute));
			}
		}
		return onAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute basicGetOnAttribute() {
		return onAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnAttribute(Attribute newOnAttribute) {
		Attribute oldOnAttribute = onAttribute;
		onAttribute = newOnAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.DELTA__ON_ATTRIBUTE, oldOnAttribute, onAttribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value getTargetValue() {
		if (targetValue != null && targetValue.eIsProxy()) {
			InternalEObject oldTargetValue = (InternalEObject)targetValue;
			targetValue = (Value)eResolveProxy(oldTargetValue);
			if (targetValue != oldTargetValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.DELTA__TARGET_VALUE, oldTargetValue, targetValue));
			}
		}
		return targetValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value basicGetTargetValue() {
		return targetValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetValue(Value newTargetValue) {
		Value oldTargetValue = targetValue;
		targetValue = newTargetValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.DELTA__TARGET_VALUE, oldTargetValue, targetValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value getSourceValue() {
		if (sourceValue != null && sourceValue.eIsProxy()) {
			InternalEObject oldSourceValue = (InternalEObject)sourceValue;
			sourceValue = (Value)eResolveProxy(oldSourceValue);
			if (sourceValue != oldSourceValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DECENTPackage.DELTA__SOURCE_VALUE, oldSourceValue, sourceValue));
			}
		}
		return sourceValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value basicGetSourceValue() {
		return sourceValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceValue(Value newSourceValue) {
		Value oldSourceValue = sourceValue;
		sourceValue = newSourceValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.DELTA__SOURCE_VALUE, oldSourceValue, sourceValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getChange() {
		return change;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChange(double newChange) {
		double oldChange = change;
		change = newChange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DECENTPackage.DELTA__CHANGE, oldChange, change));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DECENTPackage.DELTA__ACTIVITY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetActivity((Activity)otherEnd, msgs);
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
			case DECENTPackage.DELTA__ACTIVITY:
				return basicSetActivity(null, msgs);
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
			case DECENTPackage.DELTA__ACTIVITY:
				return eInternalContainer().eInverseRemove(this, DECENTPackage.ACTIVITY__DELTAS, Activity.class, msgs);
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
			case DECENTPackage.DELTA__ACTIVITY:
				return getActivity();
			case DECENTPackage.DELTA__ON_ATTRIBUTE:
				if (resolve) return getOnAttribute();
				return basicGetOnAttribute();
			case DECENTPackage.DELTA__TARGET_VALUE:
				if (resolve) return getTargetValue();
				return basicGetTargetValue();
			case DECENTPackage.DELTA__SOURCE_VALUE:
				if (resolve) return getSourceValue();
				return basicGetSourceValue();
			case DECENTPackage.DELTA__CHANGE:
				return getChange();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DECENTPackage.DELTA__ACTIVITY:
				setActivity((Activity)newValue);
				return;
			case DECENTPackage.DELTA__ON_ATTRIBUTE:
				setOnAttribute((Attribute)newValue);
				return;
			case DECENTPackage.DELTA__TARGET_VALUE:
				setTargetValue((Value)newValue);
				return;
			case DECENTPackage.DELTA__SOURCE_VALUE:
				setSourceValue((Value)newValue);
				return;
			case DECENTPackage.DELTA__CHANGE:
				setChange((Double)newValue);
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
			case DECENTPackage.DELTA__ACTIVITY:
				setActivity((Activity)null);
				return;
			case DECENTPackage.DELTA__ON_ATTRIBUTE:
				setOnAttribute((Attribute)null);
				return;
			case DECENTPackage.DELTA__TARGET_VALUE:
				setTargetValue((Value)null);
				return;
			case DECENTPackage.DELTA__SOURCE_VALUE:
				setSourceValue((Value)null);
				return;
			case DECENTPackage.DELTA__CHANGE:
				setChange(CHANGE_EDEFAULT);
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
			case DECENTPackage.DELTA__ACTIVITY:
				return getActivity() != null;
			case DECENTPackage.DELTA__ON_ATTRIBUTE:
				return onAttribute != null;
			case DECENTPackage.DELTA__TARGET_VALUE:
				return targetValue != null;
			case DECENTPackage.DELTA__SOURCE_VALUE:
				return sourceValue != null;
			case DECENTPackage.DELTA__CHANGE:
				return change != CHANGE_EDEFAULT;
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
		result.append(" (change: ");
		result.append(change);
		result.append(')');
		return result.toString();
	}

} //DeltaImpl
