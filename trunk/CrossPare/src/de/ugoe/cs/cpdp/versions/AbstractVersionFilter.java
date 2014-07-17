package de.ugoe.cs.cpdp.versions;

import java.util.Iterator;
import java.util.List;

/**
 * Implements a skeletal {@link IVersionFilter}.
 * @author Steffen Herbold
 */
public abstract class AbstractVersionFilter implements IVersionFilter {

	/**
	 * @see de.ugoe.cs.cpdp.versions.IVersionFilter#apply(java.util.List)
	 */
	@Override
	public int apply(List<SoftwareVersion> versions) {
		int removed = 0;
		for( final Iterator<SoftwareVersion> iter=versions.iterator() ; iter.hasNext() ; ) {
			SoftwareVersion version = iter.next();
			
			if( apply(version) ) {
				iter.remove();
				removed++;
			}
		}
		return removed;
	}
}
