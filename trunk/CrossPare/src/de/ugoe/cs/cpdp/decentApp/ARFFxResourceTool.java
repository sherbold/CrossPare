package de.ugoe.cs.cpdp.decentApp;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EObjectValidator;

import ARFFx.ARFFxPackage;
import ARFFx.impl.ARFFxPackageImpl;

/**
 * Class for handling arffx model files
 * 
 * @author Philip Makedonski, Fabian Trautsch
 *
 */
public class ARFFxResourceTool extends ResourceTool {
	
	/**
	 * Initializes the Tool Factory, from which the models can be loaded and
	 * inizializes the validator.
	 */
	public ARFFxResourceTool(){
		super(ARFFxResourceTool.class.getName());
		ARFFxPackageImpl.init();
		
		// Commented, because simulation has problems with this
		initializeValidator();
	}
	
	/**
	 * Inizializes the model validator
	 */
	@Override
	protected void initializeValidator(){
		super.initializeValidator();
		EObjectValidator validator = new EObjectValidator();
	    EValidator.Registry.INSTANCE.put(ARFFxPackage.eINSTANCE, validator);
	}	
	

}
