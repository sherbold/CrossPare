package de.ugoe.cs.cpdp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.list.SetUniqueList;

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
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

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

        /**
     * Helper method that combines a set of {@link SoftwareVersion} sets into a single
     * {@link Instances} set.
     * 
     * @param trainversionSet
     *            set of {@link SoftwareVersion} which training data is to be combined
     * @return single {@link Instances} set
     */
    public static Instances makeSingleTrainingSet(SetUniqueList<SoftwareVersion> trainversionSet) {
        Instances traindataFull = null;
        for (SoftwareVersion trainversion : trainversionSet) {
            Instances traindata = trainversion.getInstances();
            if (traindataFull == null) {
                traindataFull = new Instances(traindata);
            }
            else {
                for (int i = 0; i < traindata.numInstances(); i++) {
                    traindataFull.add(traindata.instance(i));
                }
            }
        }
        return traindataFull;
    }

    /**
     * Helper method that combines a set of {@link SoftwareVersion} into a single
     * {@link Instances} bugmatrix.
     * 
     * @param trainversionSet
     *            set of {@link SoftwareVersion} which bug matrices are to be combined
     * @return single {@link Instances} bugmatrix
     */
    public static Instances makeSingleBugMatrixSet(SetUniqueList<SoftwareVersion> trainversionSet) {
        final ArrayList<Attribute> bugMatrixAtts = new ArrayList<>();
        final Map<String, Integer> nameToIndex = new HashMap<>();
        for (SoftwareVersion trainversion : trainversionSet) {
            for (Iterator<Attribute> iterator = trainversion.getBugMatrix().enumerateAttributes().asIterator(); iterator
                    .hasNext();) {
                String attName = iterator.next().name();
                Attribute att = new Attribute(attName);
                if (!bugMatrixAtts.contains(att)) {
                    bugMatrixAtts.add(att);
                    nameToIndex.put(attName, nameToIndex.size());
                }
            }
        }
        Instances singleBugMatrix = new Instances("singleBugMatrix", bugMatrixAtts, 0);
        for (SoftwareVersion trainversion : trainversionSet) {
            Instances bugMatrix = trainversion.getBugMatrix();
            List<String> attNames = new ArrayList<>();
            for (Iterator<Attribute> iterator = bugMatrix.enumerateAttributes().asIterator(); iterator.hasNext();) {
                attNames.add(iterator.next().name());
            }
            for (Instance instance : bugMatrix) {
                double[] allBugs = new double[bugMatrixAtts.size()];
                double[] bugs = instance.toDoubleArray();
                for (int j = 0; j < bugs.length; j++) {
                    allBugs[nameToIndex.get(attNames.get(j))] = bugs[j];
                }
                singleBugMatrix.add(new DenseInstance(1.0, allBugs));
            }
        }
        return singleBugMatrix;
    }

    /**
     * Helper method that combines a set of SetUniqueList<SoftwareVersion> into a single
     * Softwareversion.
     * 
     * @param trainversionSet
     *            set of {@link SoftwareVersion} to be combined
     * @return single {@link SoftwareVersion} set
     */
    public static SoftwareVersion makeSingleVersionSet(SetUniqueList<SoftwareVersion> trainversionSet) {
        Instances instances = makeSingleTrainingSet(trainversionSet);
        Instances bugmatrix = makeSingleBugMatrixSet(trainversionSet);
        List<Double> efforts = null;
        List<Double> numBugs = null;
        for (SoftwareVersion trainversion : trainversionSet) {
            List<Double> tempEfforts = trainversion.getEfforts();
            List<Double> tempNumBugs = trainversion.getNumBugs();
            if (efforts == null) {
                efforts = new ArrayList<Double>(tempEfforts);
            }
            if (numBugs == null) {
                numBugs = new ArrayList<Double>(tempNumBugs);
            }
            else {
                for (int i = 0; i < tempEfforts.size(); i++) {
                    efforts.add(tempEfforts.get(i));
                }
                for (int i = 0; i < tempNumBugs.size(); i++) {
                    numBugs.add(tempNumBugs.get(i));
                }
            }
        }
        return new SoftwareVersion("singleTrainVersionSet", "trainProjects", "trainVersions", instances, bugmatrix,
                efforts, numBugs, null, null);
    }
}
