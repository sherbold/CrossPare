/**
 */
package de.ugoe.cs.cpdp.decentApp.models.arffx.util;

import de.ugoe.cs.cpdp.decentApp.models.arffx.*;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Dimension;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Instance;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Model;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Type;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Value;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage
 * @generated
 */
public class ARFFxAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ARFFxPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ARFFxAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ARFFxPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ARFFxSwitch<Adapter> modelSwitch =
		new ARFFxSwitch<Adapter>() {
			@Override
			public Adapter caseModel(Model object) {
				return createModelAdapter();
			}
			@Override
			public Adapter caseAttribute(Attribute object) {
				return createAttributeAdapter();
			}
			@Override
			public Adapter caseInstance(Instance object) {
				return createInstanceAdapter();
			}
			@Override
			public Adapter caseValue(Value object) {
				return createValueAdapter();
			}
			@Override
			public Adapter caseDimension(Dimension object) {
				return createDimensionAdapter();
			}
			@Override
			public Adapter caseType(Type object) {
				return createTypeAdapter();
			}
			@Override
			public Adapter caseMetaData(Map.Entry<String, String> object) {
				return createMetaDataAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.Model
	 * @generated
	 */
	public Adapter createModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute
	 * @generated
	 */
	public Adapter createAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Instance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.Instance
	 * @generated
	 */
	public Adapter createInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.Value
	 * @generated
	 */
	public Adapter createValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.Dimension
	 * @generated
	 */
	public Adapter createDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.ugoe.cs.cpdp.decentApp.models.arffx.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.ugoe.cs.cpdp.decentApp.models.arffx.Type
	 * @generated
	 */
	public Adapter createTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createMetaDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ARFFxAdapterFactory
