/**
 */
package de.ugoe.cs.cpdp.decentApp.models.decent.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import de.ugoe.cs.cpdp.decentApp.models.decent.DECENTPackage;
import de.ugoe.cs.cpdp.decentApp.models.decent.StringListValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>String List Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.decent.impl.StringListValueImpl#getContent <em>Content</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StringListValueImpl extends ValueImpl implements StringListValue {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StringListValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DECENTPackage.Literals.STRING_LIST_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getContent() {
		if (content == null) {
			content = new EDataTypeUniqueEList<String>(String.class, this, DECENTPackage.STRING_LIST_VALUE__CONTENT);
		}
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DECENTPackage.STRING_LIST_VALUE__CONTENT:
				return getContent();
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
			case DECENTPackage.STRING_LIST_VALUE__CONTENT:
				getContent().clear();
				getContent().addAll((Collection<? extends String>)newValue);
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
			case DECENTPackage.STRING_LIST_VALUE__CONTENT:
				getContent().clear();
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
			case DECENTPackage.STRING_LIST_VALUE__CONTENT:
				return content != null && !content.isEmpty();
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
		result.append(" (content: ");
		result.append(content);
		result.append(')');
		return result.toString();
	}

} //StringListValueImpl
