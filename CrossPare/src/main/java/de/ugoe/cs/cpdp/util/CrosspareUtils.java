package de.ugoe.cs.cpdp.util;

import java.util.LinkedList;
import java.util.List;

import de.ugoe.cs.cpdp.versions.IVersionFilter;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;

/**
 * Crosspare specific helper functions
 * 
 * @author sherbold
 */
public class CrosspareUtils {

	/**
	 * Helper method that checks if a version passes all filters.
	 * 
	 * @param version     version that is checked
	 * @param allVersions a list of all versions that may be used a reference, e.g.,
	 *                    to get the ten largest
	 * @param filters     list of the filters
	 * @return true, if the version passes all filters, false otherwise
	 */
	public static boolean isVersion(SoftwareVersion version, List<SoftwareVersion> allVersions,
			List<IVersionFilter> filters) {
		boolean result = true;
		for (IVersionFilter filter : filters) {
			result &= !filter.apply(version, allVersions);
		}
		return result;
	}

	/**
	 * Helper method that can remove versions given a filter
	 * 
	 * @param versions versions that are filtered
	 * @param filters  the filter
	 * @return number of removed versions
	 */
	public static int filterVersions(List<SoftwareVersion> versions, List<IVersionFilter> filters) {
		List<Integer> removeIndex = new LinkedList<>();
		for (int i = 0; i < versions.size(); i++) {
			if (!CrosspareUtils.isVersion(versions.get(i), versions, filters)) {
				removeIndex.add(0, i); // add to front to simplify removal
			}
		}
		for (int index : removeIndex) {
			// separate loop for removal because the version list must not change while
			// determining the matches
			versions.remove(index);
		}
		return removeIndex.size();
	}
}
