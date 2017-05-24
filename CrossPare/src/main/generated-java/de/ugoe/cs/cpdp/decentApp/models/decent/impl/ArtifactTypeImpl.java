/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import de.ugoe.cs.cpdp.decentApp.models.decent.ArtifactType;
import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artifact Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.ArtifactTypeImpl#getContainerTypes <em>Container Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArtifactTypeImpl extends ElementImpl implements ArtifactType {
	/**
	 * The cached value of the '{@link #getContainerTypes() <em>Container Types</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<ArtifactType> containerTypes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArtifactTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DECENTPackage.Literals.ARTIFACT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ArtifactType> getContainerTypes() {
		if (containerTypes == null) {
			containerTypes = new EObjectResolvingEList<ArtifactType>(ArtifactType.class, this, DECENTPackage.ARTIFACT_TYPE__CONTAINER_TYPES);
		}
		return containerTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DECENTPackage.ARTIFACT_TYPE__CONTAINER_TYPES:
				return getContainerTypes();
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
			case DECENTPackage.ARTIFACT_TYPE__CONTAINER_TYPES:
				getContainerTypes().clear();
				getContainerTypes().addAll((Collection<? extends ArtifactType>)newValue);
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
			case DECENTPackage.ARTIFACT_TYPE__CONTAINER_TYPES:
				getContainerTypes().clear();
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
			case DECENTPackage.ARTIFACT_TYPE__CONTAINER_TYPES:
				return containerTypes != null && !containerTypes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ArtifactTypeImpl
