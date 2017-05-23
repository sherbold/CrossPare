/**
 */
package de.ugoe.cs.cpdp.decentApp.models.arffx.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.impl.ValueImpl#getOfAttribute <em>Of Attribute</em>}</li>
 *   <li>{@link de.ugoe.cs.cpdp.decentApp.models.arffx.impl.ValueImpl#getContent <em>Content</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ValueImpl extends MinimalEObjectImpl.Container implements Value {
	/**
	 * The cached value of the '{@link #getOfAttribute() <em>Of Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOfAttribute()
	 * @generated
	 * @ordered
	 */
	protected Attribute ofAttribute;

	/**
	 * The default value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected String content = CONTENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ARFFxPackage.Literals.VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute getOfAttribute() {
		if (ofAttribute != null && ofAttribute.eIsProxy()) {
			InternalEObject oldOfAttribute = (InternalEObject)ofAttribute;
			ofAttribute = (Attribute)eResolveProxy(oldOfAttribute);
			if (ofAttribute != oldOfAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ARFFxPackage.VALUE__OF_ATTRIBUTE, oldOfAttribute, ofAttribute));
			}
		}
		return ofAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute basicGetOfAttribute() {
		return ofAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfAttribute(Attribute newOfAttribute) {
		Attribute oldOfAttribute = ofAttribute;
		ofAttribute = newOfAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ARFFxPackage.VALUE__OF_ATTRIBUTE, oldOfAttribute, ofAttribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContent(String newContent) {
		String oldContent = content;
		content = newContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ARFFxPackage.VALUE__CONTENT, oldContent, content));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ARFFxPackage.VALUE__OF_ATTRIBUTE:
				if (resolve) return getOfAttribute();
				return basicGetOfAttribute();
			case ARFFxPackage.VALUE__CONTENT:
				return getContent();
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
			case ARFFxPackage.VALUE__OF_ATTRIBUTE:
				setOfAttribute((Attribute)newValue);
				return;
			case ARFFxPackage.VALUE__CONTENT:
				setContent((String)newValue);
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
			case ARFFxPackage.VALUE__OF_ATTRIBUTE:
				setOfAttribute((Attribute)null);
				return;
			case ARFFxPackage.VALUE__CONTENT:
				setContent(CONTENT_EDEFAULT);
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
			case ARFFxPackage.VALUE__OF_ATTRIBUTE:
				return ofAttribute != null;
			case ARFFxPackage.VALUE__CONTENT:
				return CONTENT_EDEFAULT == null ? content != null : !CONTENT_EDEFAULT.equals(content);
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

} //ValueImpl
