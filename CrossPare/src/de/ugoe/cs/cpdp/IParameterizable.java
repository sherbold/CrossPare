package de.ugoe.cs.cpdp;

/**
 * Interface that defines that an entity excepts a parameter string. Can be used to configure parts of an experiment. How (and if) this parameter is interpreted depends entirely on the entity.
 * @author Steffen Herbold
 *
 */
public interface IParameterizable {

	/**
	 * Sets the parameters of an entity.
	 * @param parameters parameters as string
	 */
	void setParameter(String parameters);
}
