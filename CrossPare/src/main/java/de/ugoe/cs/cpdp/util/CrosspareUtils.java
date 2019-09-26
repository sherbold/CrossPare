package de.ugoe.cs.cpdp.util;

import java.util.LinkedList;
import java.util.List;

import de.ugoe.cs.cpdp.ExperimentConfiguration;
import de.ugoe.cs.cpdp.eval.IResultStorage;
import de.ugoe.cs.cpdp.training.ISetWiseTestdataAwareTrainingStrategy;
import de.ugoe.cs.cpdp.training.ISetWiseTrainingStrategy;
import de.ugoe.cs.cpdp.training.ITestAwareTrainingStrategy;
import de.ugoe.cs.cpdp.training.ITrainer;
import de.ugoe.cs.cpdp.training.ITrainingStrategy;
import de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer;
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
	
	/**
     * <p>
     * helper function that checks if the results are already in the data store
     * </p>
     *
     * @param version
     *            version for which the results are checked
     * @return
     *            number of already available results.
     */
    public static int resultsAvailable(SoftwareVersion version, ExperimentConfiguration config) {
        if (config.getResultStorages().isEmpty()) {
            return 0;
        }

        List<ITrainer> allTrainers = new LinkedList<>();
        for (ISetWiseTrainingStrategy setwiseTrainer : config.getSetWiseTrainers()) {
            allTrainers.add(setwiseTrainer);
        }
        for (ISetWiseTestdataAwareTrainingStrategy setwiseTestdataAwareTrainer : config
            .getSetWiseTestdataAwareTrainers())
        {
            allTrainers.add(setwiseTestdataAwareTrainer);
        }
        for (ITrainingStrategy trainer : config.getTrainers()) {
            allTrainers.add(trainer);
        }
        for (ITestAwareTrainingStrategy trainer : config.getTestAwareTrainers()) {
            allTrainers.add(trainer);
        }

        int available = Integer.MAX_VALUE;
        for (IResultStorage storage : config.getResultStorages()) {
            String classifierName = ((IWekaCompatibleTrainer) allTrainers.get(0)).getName();
            int curAvailable = storage.containsResult(config.getExperimentName(),
                                                      version.getVersion(), classifierName);
            if (curAvailable < available) {
                available = curAvailable;
            }
        }
        return available;
    }
}
