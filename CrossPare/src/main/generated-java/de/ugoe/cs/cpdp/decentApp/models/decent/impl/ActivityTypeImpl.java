/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import de.ugoe.cs.cpdp.decentApp.models.decent.ActivityType;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;
import de.ugoe.cs.cpdp.decentApp.models.decent.Dimension;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activity Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ActivityTypeImpl#getDimension <em>Dimension</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActivityTypeImpl extends ElementImpl implements ActivityType {
	/**
	 * The cached value of the '{@link #getDimension() <em>Dimension</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDimension()
	 * @generated
	 * @ordered
	 */
	protected EList<Dimension> dimension;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActivityTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DECENTPackage.Literals.ACTIVITY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Dimension> getDimension() {
		if (dimension == null) {
			dimension = new EObjectResolvingEList<Dimension>(Dimension.class, this, DECENTPackage.ACTIVITY_TYPE__DIMENSION);
		}
		return dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DECENTPackage.ACTIVITY_TYPE__DIMENSION:
				return getDimension();
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
			case DECENTPackage.ACTIVITY_TYPE__DIMENSION:
				getDimension().clear();
				getDimension().addAll((Collection<? extends Dimension>)newValue);
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
			case DECENTPackage.ACTIVITY_TYPE__DIMENSION:
				getDimension().clear();
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
			case DECENTPackage.ACTIVITY_TYPE__DIMENSION:
				return dimension != null && !dimension.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ActivityTypeImpl
