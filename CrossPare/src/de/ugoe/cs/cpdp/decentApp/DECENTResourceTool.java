package de.ugoe.cs.cpdp.decentApp;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EObjectValidator;

import DECENT.DECENTPackage;
import DECENT.impl.DECENTPackageImpl;
import DECENT.util.DECENTResourceFactoryImpl;

/**
 * Class for handling decent model files
 * 
 * @author Philip Makedonski, Fabian Trautsch
 *
 */
public class DECENTResourceTool extends ResourceTool {
	
	/**
	 * Initializes the Tool Factory, from which the models can be loaded and
	 * inizializes the validator.
	 */
	public DECENTResourceTool(){
		super(DECENTResourceTool.class.getName());
		DECENTPackageImpl.init();
		this.resourceFactory = new DECENTResourceFactoryImpl();
		initializeValidator();
	}
	
	/**
	 * Inizializes the model validator
	 */
	@Override
	protected void initializeValidator(){
		super.initializeValidator();
		EObjectValidator validator = new EObjectValidator();
	    EValidator.Registry.INSTANCE.put(DECENTPackage.eINSTANCE, validator);
	}
	
	

}
