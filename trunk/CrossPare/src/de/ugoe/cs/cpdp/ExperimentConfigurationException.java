package de.ugoe.cs.cpdp;

/**
 * Thrown if there is an error creating an experiment configuration.
 * @author Steffen Herbold
 */
public class ExperimentConfigurationException extends Exception {

	/**
	 * Standard serialization ID. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see Exception#Exception() 
	 */
	public ExperimentConfigurationException() {
		super();
	}
	
	/**
	 * @see Exception#Exception(String)
	 */
	public ExperimentConfigurationException(String message) {
		super(message);
	}
	
	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public ExperimentConfigurationException(String message, Throwable e) {
		super(message, e);
	}
	
	/**
	 * @see Exception#Exception(Throwable)
	 */
	public ExperimentConfigurationException(Throwable e) {
		super(e);
	}

}
