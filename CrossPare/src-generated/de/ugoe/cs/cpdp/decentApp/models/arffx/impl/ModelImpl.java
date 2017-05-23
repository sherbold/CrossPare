/**
 */
package de.ugoe.cs.cpdp.decentApp.models.arffx.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Dimension;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Instance;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Model;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.impl.ModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.impl.ModelImpl#getMeta <em>Meta</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.impl.ModelImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.impl.ModelImpl#getData <em>Data</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.impl.ModelImpl#getDimensions <em>Dimensions</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.impl.ModelImpl#getTypes <em>Types</em>}</li>
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
	 * The cached value of the '{@link #getMeta() <em>Meta</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMeta()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> meta;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

	/**
	 * The cached value of the '{@link #getData() <em>Data</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getData()
	 * @generated
	 * @ordered
	 */
	protected EList<Instance> data;

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
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> types;

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
		return ARFFxPackage.Literals.MODEL;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ARFFxPackage.MODEL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getMeta() {
		if (meta == null) {
			meta = new EcoreEMap<String,String>(ARFFxPackage.Literals.META_DATA, MetaDataImpl.class, this, ARFFxPackage.MODEL__META);
		}
		return meta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<Attribute>(Attribute.class, this, ARFFxPackage.MODEL__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instance> getData() {
		if (data == null) {
			data = new EObjectContainmentEList<Instance>(Instance.class, this, ARFFxPackage.MODEL__DATA);
		}
		return data;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Dimension> getDimensions() {
		if (dimensions == null) {
			dimensions = new EObjectContainmentEList<Dimension>(Dimension.class, this, ARFFxPackage.MODEL__DIMENSIONS);
		}
		return dimensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Type> getTypes() {
		if (types == null) {
			types = new EObjectContainmentEList<Type>(Type.class, this, ARFFxPackage.MODEL__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ARFFxPackage.MODEL__META:
				return ((InternalEList<?>)getMeta()).basicRemove(otherEnd, msgs);
			case ARFFxPackage.MODEL__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case ARFFxPackage.MODEL__DATA:
				return ((InternalEList<?>)getData()).basicRemove(otherEnd, msgs);
			case ARFFxPackage.MODEL__DIMENSIONS:
				return ((InternalEList<?>)getDimensions()).basicRemove(otherEnd, msgs);
			case ARFFxPackage.MODEL__TYPES:
				return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
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
			case ARFFxPackage.MODEL__NAME:
				return getName();
			case ARFFxPackage.MODEL__META:
				if (coreType) return getMeta();
				else return getMeta().map();
			case ARFFxPackage.MODEL__ATTRIBUTES:
				return getAttributes();
			case ARFFxPackage.MODEL__DATA:
				return getData();
			case ARFFxPackage.MODEL__DIMENSIONS:
				return getDimensions();
			case ARFFxPackage.MODEL__TYPES:
				return getTypes();
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
			case ARFFxPackage.MODEL__NAME:
				setName((String)newValue);
				return;
			case ARFFxPackage.MODEL__META:
				((EStructuralFeature.Setting)getMeta()).set(newValue);
				return;
			case ARFFxPackage.MODEL__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends Attribute>)newValue);
				return;
			case ARFFxPackage.MODEL__DATA:
				getData().clear();
				getData().addAll((Collection<? extends Instance>)newValue);
				return;
			case ARFFxPackage.MODEL__DIMENSIONS:
				getDimensions().clear();
				getDimensions().addAll((Collection<? extends Dimension>)newValue);
				return;
			case ARFFxPackage.MODEL__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends Type>)newValue);
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
			case ARFFxPackage.MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ARFFxPackage.MODEL__META:
				getMeta().clear();
				return;
			case ARFFxPackage.MODEL__ATTRIBUTES:
				getAttributes().clear();
				return;
			case ARFFxPackage.MODEL__DATA:
				getData().clear();
				return;
			case ARFFxPackage.MODEL__DIMENSIONS:
				getDimensions().clear();
				return;
			case ARFFxPackage.MODEL__TYPES:
				getTypes().clear();
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
			case ARFFxPackage.MODEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ARFFxPackage.MODEL__META:
				return meta != null && !meta.isEmpty();
			case ARFFxPackage.MODEL__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case ARFFxPackage.MODEL__DATA:
				return data != null && !data.isEmpty();
			case ARFFxPackage.MODEL__DIMENSIONS:
				return dimensions != null && !dimensions.isEmpty();
			case ARFFxPackage.MODEL__TYPES:
				return types != null && !types.isEmpty();
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
		result.append(')');
		return result.toString();
	}

} //ModelImpl
