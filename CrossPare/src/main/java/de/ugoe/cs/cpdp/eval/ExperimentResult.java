
package de.ugoe.cs.cpdp.eval;

/**
 * <p>
 * Data class to store experiment results
 * </p>
 * 
 * @author Steffen Herbold
 */
public class ExperimentResult {

    /**
     * configuration name of the experiment
     */
    private final String configurationName;

    /**
     * name of the target product
     */
    private final String productName;

    /**
     * name of the training product
     */
    private String trainProductName = "";

    /**
     * name of the classifier used
     */
    private final String classifier;

    /**
     * number of instances of the target product
     */
    int sizeTestData;

    /**
     * number of instances of the training data
     */
    int sizeTrainingData;

    /**
     * error of the prediction
     */
    double error = Double.NaN;

    /**
     * recall of the prediction
     */
    double recall = Double.NaN;

    /**
     * precision of the prediction
     */
    double precision = Double.NaN;

    /**
     * F1 score of the prediction
     */
    double fscore = Double.NaN;

    /**
     * G score of the prediction
     */
    double gscore = Double.NaN;

    /**
     * Matthews correlation coefficient of the prediction
     */
    double mcc = Double.NaN;

    /**
     * Area under the curve of the prediction
     */
    double auc = Double.NaN;

    /**
     * Balance between recall and probability of false detection
     */
    double balance = Double.NaN;

    /**
     * Effort of the prediction
     */
    double aucec = Double.NaN;

    /**
     * Number of bugs found if 20 percent of the code are reviewed
     */
    double nofb20 = Double.NaN;

    /**
     * Relative number of bugs found if 20 percent of the code are reviewed
     */
    double relb20 = Double.NaN;

    /**
     * Number of instances that have to be visited before 80 percent of the bugs are found
     */
    double nofi80 = Double.NaN;

    /**
     * Relative number of instances that have to be visited before 80 percent of the bugs are found
     */
    double reli80 = Double.NaN;

    /**
     * Relative effort invested before 80 percent of the bugs are found
     */
    double rele80 = Double.NaN;

    /**
     * Normalized expected cost of misclassification with factor 15
     */
    double necm15 = Double.NaN;

    /**
     * Normalized expected cost of misclassification with factor 20
     */
    double necm20 = Double.NaN;

    /**
     * Normalized expected cost of misclassification with factor 25
     */
    double necm25 = Double.NaN;

    /**
     * Number of bugs that are found if the classification is used, i.e., all instances are reviewed that are predicted as defect-prone.
     */
    double nofbPredicted = Double.NaN;
    
    /**
     * Number of bugs that are missed if the classification is used, i.e., all instances are reviewed that are predicted as defect-prone.
     */
    double nofbMissed = Double.NaN;
    
    /**
     * True positive rate of the prediction
     */
    double tpr = Double.NaN;

    /**
     * True negative rate of the prediction
     */
    double tnr = Double.NaN;

    /**
     * false positive rate of the prediction
     */
    double fpr = Double.NaN;

    /**
     * false negative rate of the prediction
     */
    double fnr = Double.NaN;

    /**
     * number of true positives
     */
    double tp = Double.NaN;

    /**
     * number of false negatives
     */
    double fn = Double.NaN;

    /**
     * number of true negatives
     */
    double tn = Double.NaN;

    /**
     * number of false positives
     */
    double fp = Double.NaN;
    
	/**
	 * lower cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects
	 */
	double lowerConst1to1 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects
	 */
	double upperConst1to1 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects
	 */
	double lowerSize1to1 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects
	 */
	double upperSize1to1 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects
	 */
	double lowerConst1toM = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects
	 */
	double upperConst1toM = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects
	 */
	double lowerSize1toM = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects
	 */
	double upperSize1toM = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects
	 */
	double lowerConstNtoM = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects
	 */
	double upperConstNtoM = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects
	 */
	double lowerSizeNtoM = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects
	 */
	double upperSizeNtoM = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects and 10% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConst1to1Imp10 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects and 10% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConst1to1Imp10 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects and 10% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSize1to1Imp10 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects and 10% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSize1to1Imp10 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects and 10% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConst1toMImp10 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects and 10% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConst1toMImp10 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects and 10% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSize1toMImp10 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects and 10% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSize1toMImp10 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects and 10% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConstNtoMImp10 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects and 10% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConstNtoMImp10 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects and 10% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSizeNtoMImp10 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects and 10% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSizeNtoMImp10 = Double.NaN;
	
	/**
	 * lower cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects and 20% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConst1to1Imp20 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects and 20% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConst1to1Imp20 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects and 20% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSize1to1Imp20 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects and 20% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSize1to1Imp20 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects and 20% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConst1toMImp20 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects and 20% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConst1toMImp20 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects and 20% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSize1toMImp20 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects and 20% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSize1toMImp20 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects and 20% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConstNtoMImp20 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects and 20% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConstNtoMImp20 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects and 20% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSizeNtoMImp20 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects and 20% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSizeNtoMImp20 = Double.NaN;
	
	/**
	 * lower cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects and 30% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConst1to1Imp30 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects and 30% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConst1to1Imp30 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects and 30% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSize1to1Imp30 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects and 30% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSize1to1Imp30 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects and 30% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConst1toMImp30 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects and 30% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConst1toMImp30 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects and 30% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSize1toMImp30 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects and 30% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSize1toMImp30 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects and 30% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConstNtoMImp30 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects and 30% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConstNtoMImp30 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects and 30% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSizeNtoMImp30 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects and 30% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSizeNtoMImp30 = Double.NaN;
	
	/**
	 * lower cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects and 40% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConst1to1Imp40 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects and 40% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConst1to1Imp40 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects and 40% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSize1to1Imp40 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects and 40% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSize1to1Imp40 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects and 40% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConst1toMImp40 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects and 40% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConst1toMImp40 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects and 40% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSize1toMImp40 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects and 40% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSize1toMImp40 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects and 40% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConstNtoMImp40 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects and 40% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConstNtoMImp40 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects and 40% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSizeNtoMImp40 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects and 40% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSizeNtoMImp40 = Double.NaN;
	
	/**
	 * lower cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects and 50% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConst1to1Imp50 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-1 relationship between
	 * instances and defects and 50% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConst1to1Imp50 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects and 50% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSize1to1Imp50 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-1 relationship
	 * between instances and defects and 50% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSize1to1Imp50 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects and 50% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConst1toMImp50 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a 1-to-M relationship between
	 * instances and defects and 50% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConst1toMImp50 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects and 50% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSize1toMImp50 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a 1-to-M relationship
	 * between instances and defects and 50% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSize1toMImp50 = Double.NaN;

	/**
	 * lower cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects and 50% probability of quality assurance missing a
	 * predicted defect
	 */
	double lowerConstNtoMImp50 = Double.NaN;

	/**
	 * upper cost boundary with constant QA costs and a N-to-M relationship between
	 * instances and defects and 50% probability of quality assurance missing a
	 * predicted defect
	 */
	double upperConstNtoMImp50 = Double.NaN;

	/**
	 * lower cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects and 50% probability of quality assurance
	 * missing a predicted defect
	 */
	double lowerSizeNtoMImp50 = Double.NaN;

	/**
	 * upper cost boundary with size aware QA costs and a N-to-M relationship
	 * between instances and defects and 50% probability of quality assurance
	 * missing a predicted defect
	 */
	double upperSizeNtoMImp50 = Double.NaN;
	
    /**
     * <p>
     * Constructor. Creates a new ExperimentResult.
     * </p>
     *
     * @param configurationName
     *            the configuration name
     * @param productName
     *            the product name
     * @param classifier
     *            the classifier name
     */
    @SuppressWarnings("hiding")
    public ExperimentResult(String configurationName, String productName, String classifier) {
        this.configurationName = configurationName;
        this.productName = productName;
        this.classifier = classifier;
    }

    /**
     * 
     * <p>
     * Constructor. Creates a new ExperimentResult.
     * </p>
     *
     * @param configurationName
     *            the configuration name
     * @param productName
     *            the product name
     * @param classifier
     *            the classifier name
     * @param trainProductName
     *            the name of the training product
     */
    @SuppressWarnings("hiding")
    public ExperimentResult(String configurationName,
                            String productName,
                            String classifier,
                            String trainProductName)
    {
        this.configurationName = configurationName;
        this.productName = productName;
        this.classifier = classifier;
        this.trainProductName = trainProductName;
    }

    /**
     * <p>
     * returns the configuration name
     * </p>
     *
     * @return the configuration name
     */
    public String getConfigurationName() {
        return this.configurationName;
    }

    /**
     * <p>
     * returns the product name
     * </p>
     *
     * @return the product name
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * <p>
     * returns the name of the training product
     * </p>
     *
     * @return the name of the training product
     */
    public String getTrainProductName() {
        return this.trainProductName;
    }

    /**
     * <p>
     * returns the classifier name
     * </p>
     *
     * @return the classifier name
     */
    public String getClassifier() {
        return this.classifier;
    }

    /**
     * <p>
     * returns the number of instances of the target product
     * </p>
     *
     * @return number of instances
     */
    public int getSizeTestData() {
        return this.sizeTestData;
    }

    /**
     * <p>
     * sets the number of instances of the target product
     * </p>
     *
     * @param sizeTestData
     *            number of instances
     */
    @SuppressWarnings("hiding")
    public void setSizeTestData(int sizeTestData) {
        this.sizeTestData = sizeTestData;
    }

    /**
     * <p>
     * returns the number of instances of the training data
     * </p>
     *
     * @return number of instances
     */
    public int getSizeTrainingData() {
        return this.sizeTrainingData;
    }

    /**
     * <p>
     * sets the number of instances of the training data
     * </p>
     *
     * @param sizeTrainingData
     *            number of instances
     */
    @SuppressWarnings("hiding")
    public void setSizeTrainingData(int sizeTrainingData) {
        this.sizeTrainingData = sizeTrainingData;
    }

    /**
     * <p>
     * returns the error
     * </p>
     *
     * @return the error
     */
    public double getError() {
        return this.error;
    }

    /**
     * <p>
     * sets the error
     * </p>
     *
     * @param error
     *            the error
     */
    @SuppressWarnings("hiding")
    public void setError(double error) {
        this.error = error;
    }

    /**
     * <p>
     * returns the recall
     * </p>
     *
     * @return the recall
     */
    public double getRecall() {
        return this.recall;
    }

    /**
     * <p>
     * sets the recall
     * </p>
     *
     * @param recall
     *            the recall
     */
    @SuppressWarnings("hiding")
    public void setRecall(double recall) {
        this.recall = recall;
    }

    /**
     * <p>
     * returns the precision
     * </p>
     *
     * @return the precision
     */
    public double getPrecision() {
        return this.precision;
    }

    /**
     * <p>
     * sets the precision
     * </p>
     *
     * @param precision
     *            the precision
     */
    @SuppressWarnings("hiding")
    public void setPrecision(double precision) {
    	if( Double.isFinite(precision) ) {
    		this.precision = precision;
    	} else {
    		this.precision = -1;
    	}
    }

    /**
     * <p>
     * returns the F1 score
     * </p>
     *
     * @return the F1 score
     */
    public double getFscore() {
        return this.fscore;
    }

    /**
     * <p>
     * sets the F1 score
     * </p>
     *
     * @param fscore
     *            the F1 score
     */
    @SuppressWarnings("hiding")
    public void setFscore(double fscore) {
    	if( Double.isFinite(fscore) ) {
    		this.fscore = fscore;
    	} else {
    		this.fscore = -1;
    	}
    }

    /**
     * <p>
     * returns the G score
     * </p>
     *
     * @return the G score
     */
    public double getGscore() {
        return this.gscore;
    }

    /**
     * <p>
     * sets the G score
     * </p>
     *
     * @param gscore
     *            the G score
     */
    @SuppressWarnings("hiding")
    public void setGscore(double gscore) {
        this.gscore = gscore;
    }

    /**
     * <p>
     * returns the MCC
     * </p>
     *
     * @return the MCC
     */
    public double getMcc() {
        return this.mcc;
    }

    /**
     * <p>
     * sets the MCC
     * </p>
     *
     * @param mcc
     *            the MCC
     */
    @SuppressWarnings("hiding")
    public void setMcc(double mcc) {
    	if( Double.isFinite(mcc) ) {
    		this.mcc = mcc;
    	} else {
    		this.mcc = -2;
    	}
    }

    /**
     * <p>
     * returns the AUC
     * </p>
     *
     * @return the AUC
     */
    public double getAuc() {
        return this.auc;
    }

    /**
     * <p>
     * sets the AUC
     * </p>
     *
     * @param auc
     *            the AUC
     */
    @SuppressWarnings("hiding")
    public void setAuc(double auc) {
        this.auc = auc;
    }

    /**
     * <p>
     * returns the balance
     * </p>
     *
     * @return the balance
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * <p>
     * sets the balance
     * </p>
     *
     * @param auc
     *            the balance
     */
    @SuppressWarnings("hiding")
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * <p>
     * returns the effort as AUCEC
     * </p>
     *
     * @return the effort
     */
    public double getAucec() {
        return this.aucec;
    }

    /**
     * <p>
     * sets the effort as AUCEC
     * </p>
     *
     * @param aucec
     *            the effort
     */
    @SuppressWarnings("hiding")
    public void setAucec(double aucec) {
        this.aucec = aucec;
    }

    /**
     * <p>
     * returns the effort as NofB20
     * </p>
     *
     * @return the effort as NofB20
     */
    public double getNofb20() {
        return this.nofb20;
    }

    /**
     * <p>
     * sets the effort as NofB20
     * </p>
     *
     * @param aucec
     *            the effort as NofB20
     */
    @SuppressWarnings("hiding")
    public void setNofb20(double nofb20) {
        this.nofb20 = nofb20;
    }

    /**
     * <p>
     * returns the effort as RelB20
     * </p>
     *
     * @return the effort as RelB20
     */
    public double getRelb20() {
        return this.relb20;
    }

    /**
     * <p>
     * sets the effort as RelB20
     * </p>
     *
     * @param aucec
     *            the effort as RelB20
     */
    @SuppressWarnings("hiding")
    public void setRelb20(double relb20) {
        this.relb20 = relb20;
    }

    /**
     * <p>
     * returns the effort as NofI80
     * </p>
     *
     * @return the effort as NofI80
     */
    public double getNofi80() {
        return this.nofi80;
    }

    /**
     * <p>
     * sets the effort as NofI80
     * </p>
     *
     * @param aucec
     *            the effort as NofI80
     */
    @SuppressWarnings("hiding")
    public void setNofi80(double nofi80) {
        this.nofi80 = nofi80;
    }

    /**
     * <p>
     * returns the effort as RelI80
     * </p>
     *
     * @return the effort as RelI80
     */
    public double getReli80() {
        return this.reli80;
    }

    /**
     * <p>
     * sets the effort as RelI80
     * </p>
     *
     * @param aucec
     *            the effort as RelI80
     */
    @SuppressWarnings("hiding")
    public void setReli80(double reli80) {
        this.reli80 = reli80;
    }

    /**
     * <p>
     * returns the effort as RelE80
     * </p>
     *
     * @return the effort as RelE80
     */
    public double getRele80() {
        return this.rele80;
    }

    /**
     * <p>
     * sets the effort as RelE80
     * </p>
     *
     * @param aucec
     *            the effort as RelE80
     */
    @SuppressWarnings("hiding")
    public void setRele80(double rele80) {
        this.rele80 = rele80;
    }

    /**
     * <p>
     * returns the cost as NECM15
     * </p>
     *
     * @return the cost as NECM15
     */
    public double getNecm15() {
        return this.necm15;
    }

    /**
     * <p>
     * sets the cost as NECM15
     * </p>
     *
     * @param aucec
     *            the cost as NECM15
     */
    @SuppressWarnings("hiding")
    public void setNecm15(double necm15) {
        this.necm15 = necm15;
    }

    /**
     * <p>
     * returns the cost as NECM20
     * </p>
     *
     * @return the cost as NECM20
     */
    public double getNecm20() {
        return this.necm20;
    }

    /**
     * <p>
     * sets the cost as NECM20
     * </p>
     *
     * @param aucec
     *            the cost as NECM20
     */
    @SuppressWarnings("hiding")
    public void setNecm20(double necm20) {
        this.necm20 = necm20;
    }

    /**
     * <p>
     * returns the cost as NECM25
     * </p>
     *
     * @return the cost as NECM25
     */
    public double getNecm25() {
        return this.necm25;
    }

    /**
     * <p>
     * sets the cost as NECM25
     * </p>
     *
     * @param aucec
     *            the cost as NECM25
     */
    @SuppressWarnings("hiding")
    public void setNecm25(double necm25) {
        this.necm25 = necm25;
    }
    
    /**
     * <p>
     * returns the predicted defects
     * </p>
     *
     * @return the predicted defects
     */
    public double getNofbPredicted() {
        return this.nofbPredicted;
    }

    /**
     * <p>
     * sets the predicted defects
     * </p>
     *
     * @param nofbPredicted
     *            the nofbPredicted
     */
    @SuppressWarnings("hiding")
    public void setNofbPredicted(double nofbPredicted) {
        this.nofbPredicted = nofbPredicted;
    }
    /**
     * <p>
     * returns the missed defects
     * </p>
     *
     * @return the missed defects
     */
    public double getNofbMissed() {
        return this.nofbMissed;
    }

    /**
     * <p>
     * sets the missed defects
     * </p>
     *
     * @param nofbMissed
     *            the missed defects
     */
    @SuppressWarnings("hiding")
    public void setNofbMissed(double nofbMissed) {
        this.nofbMissed = nofbMissed;
    }

    /**
     * <p>
     * returns the TPR
     * </p>
     *
     * @return the TPR
     */
    public double getTpr() {
        return this.tpr;
    }

    /**
     * <p>
     * sets the TPR
     * </p>
     *
     * @param tpr
     *            the TPR
     */
    @SuppressWarnings("hiding")
    public void setTpr(double tpr) {
        this.tpr = tpr;
    }

    /**
     * <p>
     * sets the TNR
     * </p>
     *
     * @return the TNR
     */
    public double getTnr() {
        return this.tnr;
    }

    /**
     * <p>
     * sets the TNR
     * </p>
     *
     * @param tnr
     *            the TNR
     */
    @SuppressWarnings("hiding")
    public void setTnr(double tnr) {
        this.tnr = tnr;
    }

    /**
     * <p>
     * returns the FPR
     * </p>
     *
     * @return the FPR
     */
    public double getFpr() {
        return this.fpr;
    }

    /**
     * <p>
     * sets the FPR
     * </p>
     *
     * @param fpr
     *            the FPR
     */
    @SuppressWarnings("hiding")
    public void setFpr(double fpr) {
        this.fpr = fpr;
    }

    /**
     * <p>
     * returns the FNR
     * </p>
     *
     * @return the FNR
     */
    public double getFnr() {
        return this.fnr;
    }

    /**
     * <p>
     * sets the FNR
     * </p>
     *
     * @param fnr
     *            the FNR
     */
    @SuppressWarnings("hiding")
    public void setFnr(double fnr) {
        this.fnr = fnr;
    }

    /**
     * <p>
     * returns the TPs
     * </p>
     *
     * @return the TPs
     */
    public double getTp() {
        return this.tp;
    }

    /**
     * <p>
     * sets the TPs
     * </p>
     *
     * @param tp
     *            the TPs
     */
    @SuppressWarnings("hiding")
    public void setTp(double tp) {
        this.tp = tp;
    }

    /**
     * <p>
     * returns the FNs
     * </p>
     *
     * @return the FNs
     */
    public double getFn() {
        return this.fn;
    }

    /**
     * <p>
     * sets the FNs
     * </p>
     *
     * @param fn
     *            the FNs
     */
    @SuppressWarnings("hiding")
    public void setFn(double fn) {
        this.fn = fn;
    }

    /**
     * <p>
     * returns the TNs
     * </p>
     *
     * @return the TNs
     */
    public double getTn() {
        return this.tn;
    }

    /**
     * <p>
     * sets the TNs
     * </p>
     *
     * @param tn
     *            the TNs
     */
    @SuppressWarnings("hiding")
    public void setTn(double tn) {
        this.tn = tn;
    }

    /**
     * <p>
     * returns the FPs
     * </p>
     *
     * @return the FPs
     */
    public double getFp() {
        return this.fp;
    }

    /**
     * <p>
     * sets the FPs
     * </p>
     *
     * @param fp
     *            the FPs
     */
    @SuppressWarnings("hiding")
    public void setFp(double fp) {
        this.fp = fp;
    }

	/**
	 * @return the lowerConst1to1
	 */
	public double getLowerConst1to1() {
		return lowerConst1to1;
	}

	/**
	 * @param lowerConst1to1 the lowerConst1to1 to set
	 */
	public void setLowerConst1to1(double lowerConst1to1) {
		this.lowerConst1to1 = lowerConst1to1;
	}

	/**
	 * @return the upperConst1to1
	 */
	public double getUpperConst1to1() {
		return upperConst1to1;
	}

	/**
	 * @param upperConst1to1 the upperConst1to1 to set
	 */
	public void setUpperConst1to1(double upperConst1to1) {
		this.upperConst1to1 = upperConst1to1;
	}

	/**
	 * @return the lowerSize1to1
	 */
	public double getLowerSize1to1() {
		return lowerSize1to1;
	}

	/**
	 * @param lowerSize1to1 the lowerSize1to1 to set
	 */
	public void setLowerSize1to1(double lowerSize1to1) {
		this.lowerSize1to1 = lowerSize1to1;
	}

	/**
	 * @return the upperSize1to1
	 */
	public double getUpperSize1to1() {
		return upperSize1to1;
	}

	/**
	 * @param upperSize1to1 the upperSize1to1 to set
	 */
	public void setUpperSize1to1(double upperSize1to1) {
		this.upperSize1to1 = upperSize1to1;
	}

	/**
	 * @return the lowerConst1toM
	 */
	public double getLowerConst1toM() {
		return lowerConst1toM;
	}

	/**
	 * @param lowerConst1toM the lowerConst1toM to set
	 */
	public void setLowerConst1toM(double lowerConst1toM) {
		this.lowerConst1toM = lowerConst1toM;
	}

	/**
	 * @return the upperConst1toM
	 */
	public double getUpperConst1toM() {
		return upperConst1toM;
	}

	/**
	 * @param upperConst1toM the upperConst1toM to set
	 */
	public void setUpperConst1toM(double upperConst1toM) {
		this.upperConst1toM = upperConst1toM;
	}

	/**
	 * @return the lowerSize1toM
	 */
	public double getLowerSize1toM() {
		return lowerSize1toM;
	}

	/**
	 * @param lowerSize1toM the lowerSize1toM to set
	 */
	public void setLowerSize1toM(double lowerSize1toM) {
		this.lowerSize1toM = lowerSize1toM;
	}

	/**
	 * @return the upperSize1toM
	 */
	public double getUpperSize1toM() {
		return upperSize1toM;
	}

	/**
	 * @param upperSize1toM the upperSize1toM to set
	 */
	public void setUpperSize1toM(double upperSize1toM) {
		this.upperSize1toM = upperSize1toM;
	}

	/**
	 * @return the lowerConstNtoM
	 */
	public double getLowerConstNtoM() {
		return lowerConstNtoM;
	}

	/**
	 * @param lowerConstNtoM the lowerConstNtoM to set
	 */
	public void setLowerConstNtoM(double lowerConstNtoM) {
		this.lowerConstNtoM = lowerConstNtoM;
	}

	/**
	 * @return the upperConstNtoM
	 */
	public double getUpperConstNtoM() {
		return upperConstNtoM;
	}

	/**
	 * @param upperConstNtoM the upperConstNtoM to set
	 */
	public void setUpperConstNtoM(double upperConstNtoM) {
		this.upperConstNtoM = upperConstNtoM;
	}

	/**
	 * @return the lowerSizeNtoM
	 */
	public double getLowerSizeNtoM() {
		return lowerSizeNtoM;
	}

	/**
	 * @param lowerSizeNtoM the lowerSizeNtoM to set
	 */
	public void setLowerSizeNtoM(double lowerSizeNtoM) {
		this.lowerSizeNtoM = lowerSizeNtoM;
	}

	/**
	 * @return the upperSizeNtoM
	 */
	public double getUpperSizeNtoM() {
		return upperSizeNtoM;
	}

	/**
	 * @param upperSizeNtoM the upperSizeNtoM to set
	 */
	public void setUpperSizeNtoM(double upperSizeNtoM) {
		this.upperSizeNtoM = upperSizeNtoM;
	}

	/**
	 * @return the lowerConst1to1Imp10
	 */
	public double getLowerConst1to1Imp10() {
		return lowerConst1to1Imp10;
	}

	/**
	 * @param lowerConst1to1Imp10 the lowerConst1to1Imp10 to set
	 */
	public void setLowerConst1to1Imp10(double lowerConst1to1Imp10) {
		this.lowerConst1to1Imp10 = lowerConst1to1Imp10;
	}

	/**
	 * @return the upperConst1to1Imp10
	 */
	public double getUpperConst1to1Imp10() {
		return upperConst1to1Imp10;
	}

	/**
	 * @param upperConst1to1Imp10 the upperConst1to1Imp10 to set
	 */
	public void setUpperConst1to1Imp10(double upperConst1to1Imp10) {
		this.upperConst1to1Imp10 = upperConst1to1Imp10;
	}

	/**
	 * @return the lowerSize1to1Imp10
	 */
	public double getLowerSize1to1Imp10() {
		return lowerSize1to1Imp10;
	}

	/**
	 * @param lowerSize1to1Imp10 the lowerSize1to1Imp10 to set
	 */
	public void setLowerSize1to1Imp10(double lowerSize1to1Imp10) {
		this.lowerSize1to1Imp10 = lowerSize1to1Imp10;
	}

	/**
	 * @return the upperSize1to1Imp10
	 */
	public double getUpperSize1to1Imp10() {
		return upperSize1to1Imp10;
	}

	/**
	 * @param upperSize1to1Imp10 the upperSize1to1Imp10 to set
	 */
	public void setUpperSize1to1Imp10(double upperSize1to1Imp10) {
		this.upperSize1to1Imp10 = upperSize1to1Imp10;
	}

	/**
	 * @return the lowerConst1toMImp10
	 */
	public double getLowerConst1toMImp10() {
		return lowerConst1toMImp10;
	}

	/**
	 * @param lowerConst1toMImp10 the lowerConst1toMImp10 to set
	 */
	public void setLowerConst1toMImp10(double lowerConst1toMImp10) {
		this.lowerConst1toMImp10 = lowerConst1toMImp10;
	}

	/**
	 * @return the upperConst1toMImp10
	 */
	public double getUpperConst1toMImp10() {
		return upperConst1toMImp10;
	}

	/**
	 * @param upperConst1toMImp10 the upperConst1toMImp10 to set
	 */
	public void setUpperConst1toMImp10(double upperConst1toMImp10) {
		this.upperConst1toMImp10 = upperConst1toMImp10;
	}

	/**
	 * @return the lowerSize1toMImp10
	 */
	public double getLowerSize1toMImp10() {
		return lowerSize1toMImp10;
	}

	/**
	 * @param lowerSize1toMImp10 the lowerSize1toMImp10 to set
	 */
	public void setLowerSize1toMImp10(double lowerSize1toMImp10) {
		this.lowerSize1toMImp10 = lowerSize1toMImp10;
	}

	/**
	 * @return the upperSize1toMImp10
	 */
	public double getUpperSize1toMImp10() {
		return upperSize1toMImp10;
	}

	/**
	 * @param upperSize1toMImp10 the upperSize1toMImp10 to set
	 */
	public void setUpperSize1toMImp10(double upperSize1toMImp10) {
		this.upperSize1toMImp10 = upperSize1toMImp10;
	}

	/**
	 * @return the lowerConstNtoMImp10
	 */
	public double getLowerConstNtoMImp10() {
		return lowerConstNtoMImp10;
	}

	/**
	 * @param lowerConstNtoMImp10 the lowerConstNtoMImp10 to set
	 */
	public void setLowerConstNtoMImp10(double lowerConstNtoMImp10) {
		this.lowerConstNtoMImp10 = lowerConstNtoMImp10;
	}

	/**
	 * @return the upperConstNtoMImp10
	 */
	public double getUpperConstNtoMImp10() {
		return upperConstNtoMImp10;
	}

	/**
	 * @param upperConstNtoMImp10 the upperConstNtoMImp10 to set
	 */
	public void setUpperConstNtoMImp10(double upperConstNtoMImp10) {
		this.upperConstNtoMImp10 = upperConstNtoMImp10;
	}

	/**
	 * @return the lowerSizeNtoMImp10
	 */
	public double getLowerSizeNtoMImp10() {
		return lowerSizeNtoMImp10;
	}

	/**
	 * @param lowerSizeNtoMImp10 the lowerSizeNtoMImp10 to set
	 */
	public void setLowerSizeNtoMImp10(double lowerSizeNtoMImp10) {
		this.lowerSizeNtoMImp10 = lowerSizeNtoMImp10;
	}

	/**
	 * @return the upperSizeNtoMImp10
	 */
	public double getUpperSizeNtoMImp10() {
		return upperSizeNtoMImp10;
	}

	/**
	 * @param upperSizeNtoMImp10 the upperSizeNtoMImp10 to set
	 */
	public void setUpperSizeNtoMImp10(double upperSizeNtoMImp10) {
		this.upperSizeNtoMImp10 = upperSizeNtoMImp10;
	}

	/**
	 * @return the lowerConst1to1Imp20
	 */
	public double getLowerConst1to1Imp20() {
		return lowerConst1to1Imp20;
	}

	/**
	 * @param lowerConst1to1Imp20 the lowerConst1to1Imp20 to set
	 */
	public void setLowerConst1to1Imp20(double lowerConst1to1Imp20) {
		this.lowerConst1to1Imp20 = lowerConst1to1Imp20;
	}

	/**
	 * @return the upperConst1to1Imp20
	 */
	public double getUpperConst1to1Imp20() {
		return upperConst1to1Imp20;
	}

	/**
	 * @param upperConst1to1Imp20 the upperConst1to1Imp20 to set
	 */
	public void setUpperConst1to1Imp20(double upperConst1to1Imp20) {
		this.upperConst1to1Imp20 = upperConst1to1Imp20;
	}

	/**
	 * @return the lowerSize1to1Imp20
	 */
	public double getLowerSize1to1Imp20() {
		return lowerSize1to1Imp20;
	}

	/**
	 * @param lowerSize1to1Imp20 the lowerSize1to1Imp20 to set
	 */
	public void setLowerSize1to1Imp20(double lowerSize1to1Imp20) {
		this.lowerSize1to1Imp20 = lowerSize1to1Imp20;
	}

	/**
	 * @return the upperSize1to1Imp20
	 */
	public double getUpperSize1to1Imp20() {
		return upperSize1to1Imp20;
	}

	/**
	 * @param upperSize1to1Imp20 the upperSize1to1Imp20 to set
	 */
	public void setUpperSize1to1Imp20(double upperSize1to1Imp20) {
		this.upperSize1to1Imp20 = upperSize1to1Imp20;
	}

	/**
	 * @return the lowerConst1toMImp20
	 */
	public double getLowerConst1toMImp20() {
		return lowerConst1toMImp20;
	}

	/**
	 * @param lowerConst1toMImp20 the lowerConst1toMImp20 to set
	 */
	public void setLowerConst1toMImp20(double lowerConst1toMImp20) {
		this.lowerConst1toMImp20 = lowerConst1toMImp20;
	}

	/**
	 * @return the upperConst1toMImp20
	 */
	public double getUpperConst1toMImp20() {
		return upperConst1toMImp20;
	}

	/**
	 * @param upperConst1toMImp20 the upperConst1toMImp20 to set
	 */
	public void setUpperConst1toMImp20(double upperConst1toMImp20) {
		this.upperConst1toMImp20 = upperConst1toMImp20;
	}

	/**
	 * @return the lowerSize1toMImp20
	 */
	public double getLowerSize1toMImp20() {
		return lowerSize1toMImp20;
	}

	/**
	 * @param lowerSize1toMImp20 the lowerSize1toMImp20 to set
	 */
	public void setLowerSize1toMImp20(double lowerSize1toMImp20) {
		this.lowerSize1toMImp20 = lowerSize1toMImp20;
	}

	/**
	 * @return the upperSize1toMImp20
	 */
	public double getUpperSize1toMImp20() {
		return upperSize1toMImp20;
	}

	/**
	 * @param upperSize1toMImp20 the upperSize1toMImp20 to set
	 */
	public void setUpperSize1toMImp20(double upperSize1toMImp20) {
		this.upperSize1toMImp20 = upperSize1toMImp20;
	}

	/**
	 * @return the lowerConstNtoMImp20
	 */
	public double getLowerConstNtoMImp20() {
		return lowerConstNtoMImp20;
	}

	/**
	 * @param lowerConstNtoMImp20 the lowerConstNtoMImp20 to set
	 */
	public void setLowerConstNtoMImp20(double lowerConstNtoMImp20) {
		this.lowerConstNtoMImp20 = lowerConstNtoMImp20;
	}

	/**
	 * @return the upperConstNtoMImp20
	 */
	public double getUpperConstNtoMImp20() {
		return upperConstNtoMImp20;
	}

	/**
	 * @param upperConstNtoMImp20 the upperConstNtoMImp20 to set
	 */
	public void setUpperConstNtoMImp20(double upperConstNtoMImp20) {
		this.upperConstNtoMImp20 = upperConstNtoMImp20;
	}

	/**
	 * @return the lowerSizeNtoMImp20
	 */
	public double getLowerSizeNtoMImp20() {
		return lowerSizeNtoMImp20;
	}

	/**
	 * @param lowerSizeNtoMImp20 the lowerSizeNtoMImp20 to set
	 */
	public void setLowerSizeNtoMImp20(double lowerSizeNtoMImp20) {
		this.lowerSizeNtoMImp20 = lowerSizeNtoMImp20;
	}

	/**
	 * @return the upperSizeNtoMImp20
	 */
	public double getUpperSizeNtoMImp20() {
		return upperSizeNtoMImp20;
	}

	/**
	 * @param upperSizeNtoMImp20 the upperSizeNtoMImp20 to set
	 */
	public void setUpperSizeNtoMImp20(double upperSizeNtoMImp20) {
		this.upperSizeNtoMImp20 = upperSizeNtoMImp20;
	}

	/**
	 * @return the lowerConst1to1Imp30
	 */
	public double getLowerConst1to1Imp30() {
		return lowerConst1to1Imp30;
	}

	/**
	 * @param lowerConst1to1Imp30 the lowerConst1to1Imp30 to set
	 */
	public void setLowerConst1to1Imp30(double lowerConst1to1Imp30) {
		this.lowerConst1to1Imp30 = lowerConst1to1Imp30;
	}

	/**
	 * @return the upperConst1to1Imp30
	 */
	public double getUpperConst1to1Imp30() {
		return upperConst1to1Imp30;
	}

	/**
	 * @param upperConst1to1Imp30 the upperConst1to1Imp30 to set
	 */
	public void setUpperConst1to1Imp30(double upperConst1to1Imp30) {
		this.upperConst1to1Imp30 = upperConst1to1Imp30;
	}

	/**
	 * @return the lowerSize1to1Imp30
	 */
	public double getLowerSize1to1Imp30() {
		return lowerSize1to1Imp30;
	}

	/**
	 * @param lowerSize1to1Imp30 the lowerSize1to1Imp30 to set
	 */
	public void setLowerSize1to1Imp30(double lowerSize1to1Imp30) {
		this.lowerSize1to1Imp30 = lowerSize1to1Imp30;
	}

	/**
	 * @return the upperSize1to1Imp30
	 */
	public double getUpperSize1to1Imp30() {
		return upperSize1to1Imp30;
	}

	/**
	 * @param upperSize1to1Imp30 the upperSize1to1Imp30 to set
	 */
	public void setUpperSize1to1Imp30(double upperSize1to1Imp30) {
		this.upperSize1to1Imp30 = upperSize1to1Imp30;
	}

	/**
	 * @return the lowerConst1toMImp30
	 */
	public double getLowerConst1toMImp30() {
		return lowerConst1toMImp30;
	}

	/**
	 * @param lowerConst1toMImp30 the lowerConst1toMImp30 to set
	 */
	public void setLowerConst1toMImp30(double lowerConst1toMImp30) {
		this.lowerConst1toMImp30 = lowerConst1toMImp30;
	}

	/**
	 * @return the upperConst1toMImp30
	 */
	public double getUpperConst1toMImp30() {
		return upperConst1toMImp30;
	}

	/**
	 * @param upperConst1toMImp30 the upperConst1toMImp30 to set
	 */
	public void setUpperConst1toMImp30(double upperConst1toMImp30) {
		this.upperConst1toMImp30 = upperConst1toMImp30;
	}

	/**
	 * @return the lowerSize1toMImp30
	 */
	public double getLowerSize1toMImp30() {
		return lowerSize1toMImp30;
	}

	/**
	 * @param lowerSize1toMImp30 the lowerSize1toMImp30 to set
	 */
	public void setLowerSize1toMImp30(double lowerSize1toMImp30) {
		this.lowerSize1toMImp30 = lowerSize1toMImp30;
	}

	/**
	 * @return the upperSize1toMImp30
	 */
	public double getUpperSize1toMImp30() {
		return upperSize1toMImp30;
	}

	/**
	 * @param upperSize1toMImp30 the upperSize1toMImp30 to set
	 */
	public void setUpperSize1toMImp30(double upperSize1toMImp30) {
		this.upperSize1toMImp30 = upperSize1toMImp30;
	}

	/**
	 * @return the lowerConstNtoMImp30
	 */
	public double getLowerConstNtoMImp30() {
		return lowerConstNtoMImp30;
	}

	/**
	 * @param lowerConstNtoMImp30 the lowerConstNtoMImp30 to set
	 */
	public void setLowerConstNtoMImp30(double lowerConstNtoMImp30) {
		this.lowerConstNtoMImp30 = lowerConstNtoMImp30;
	}

	/**
	 * @return the upperConstNtoMImp30
	 */
	public double getUpperConstNtoMImp30() {
		return upperConstNtoMImp30;
	}

	/**
	 * @param upperConstNtoMImp30 the upperConstNtoMImp30 to set
	 */
	public void setUpperConstNtoMImp30(double upperConstNtoMImp30) {
		this.upperConstNtoMImp30 = upperConstNtoMImp30;
	}

	/**
	 * @return the lowerSizeNtoMImp30
	 */
	public double getLowerSizeNtoMImp30() {
		return lowerSizeNtoMImp30;
	}

	/**
	 * @param lowerSizeNtoMImp30 the lowerSizeNtoMImp30 to set
	 */
	public void setLowerSizeNtoMImp30(double lowerSizeNtoMImp30) {
		this.lowerSizeNtoMImp30 = lowerSizeNtoMImp30;
	}

	/**
	 * @return the upperSizeNtoMImp30
	 */
	public double getUpperSizeNtoMImp30() {
		return upperSizeNtoMImp30;
	}

	/**
	 * @param upperSizeNtoMImp30 the upperSizeNtoMImp30 to set
	 */
	public void setUpperSizeNtoMImp30(double upperSizeNtoMImp30) {
		this.upperSizeNtoMImp30 = upperSizeNtoMImp30;
	}

	/**
	 * @return the lowerConst1to1Imp40
	 */
	public double getLowerConst1to1Imp40() {
		return lowerConst1to1Imp40;
	}

	/**
	 * @param lowerConst1to1Imp40 the lowerConst1to1Imp40 to set
	 */
	public void setLowerConst1to1Imp40(double lowerConst1to1Imp40) {
		this.lowerConst1to1Imp40 = lowerConst1to1Imp40;
	}

	/**
	 * @return the upperConst1to1Imp40
	 */
	public double getUpperConst1to1Imp40() {
		return upperConst1to1Imp40;
	}

	/**
	 * @param upperConst1to1Imp40 the upperConst1to1Imp40 to set
	 */
	public void setUpperConst1to1Imp40(double upperConst1to1Imp40) {
		this.upperConst1to1Imp40 = upperConst1to1Imp40;
	}

	/**
	 * @return the lowerSize1to1Imp40
	 */
	public double getLowerSize1to1Imp40() {
		return lowerSize1to1Imp40;
	}

	/**
	 * @param lowerSize1to1Imp40 the lowerSize1to1Imp40 to set
	 */
	public void setLowerSize1to1Imp40(double lowerSize1to1Imp40) {
		this.lowerSize1to1Imp40 = lowerSize1to1Imp40;
	}

	/**
	 * @return the upperSize1to1Imp40
	 */
	public double getUpperSize1to1Imp40() {
		return upperSize1to1Imp40;
	}

	/**
	 * @param upperSize1to1Imp40 the upperSize1to1Imp40 to set
	 */
	public void setUpperSize1to1Imp40(double upperSize1to1Imp40) {
		this.upperSize1to1Imp40 = upperSize1to1Imp40;
	}

	/**
	 * @return the lowerConst1toMImp40
	 */
	public double getLowerConst1toMImp40() {
		return lowerConst1toMImp40;
	}

	/**
	 * @param lowerConst1toMImp40 the lowerConst1toMImp40 to set
	 */
	public void setLowerConst1toMImp40(double lowerConst1toMImp40) {
		this.lowerConst1toMImp40 = lowerConst1toMImp40;
	}

	/**
	 * @return the upperConst1toMImp40
	 */
	public double getUpperConst1toMImp40() {
		return upperConst1toMImp40;
	}

	/**
	 * @param upperConst1toMImp40 the upperConst1toMImp40 to set
	 */
	public void setUpperConst1toMImp40(double upperConst1toMImp40) {
		this.upperConst1toMImp40 = upperConst1toMImp40;
	}

	/**
	 * @return the lowerSize1toMImp40
	 */
	public double getLowerSize1toMImp40() {
		return lowerSize1toMImp40;
	}

	/**
	 * @param lowerSize1toMImp40 the lowerSize1toMImp40 to set
	 */
	public void setLowerSize1toMImp40(double lowerSize1toMImp40) {
		this.lowerSize1toMImp40 = lowerSize1toMImp40;
	}

	/**
	 * @return the upperSize1toMImp40
	 */
	public double getUpperSize1toMImp40() {
		return upperSize1toMImp40;
	}

	/**
	 * @param upperSize1toMImp40 the upperSize1toMImp40 to set
	 */
	public void setUpperSize1toMImp40(double upperSize1toMImp40) {
		this.upperSize1toMImp40 = upperSize1toMImp40;
	}

	/**
	 * @return the lowerConstNtoMImp40
	 */
	public double getLowerConstNtoMImp40() {
		return lowerConstNtoMImp40;
	}

	/**
	 * @param lowerConstNtoMImp40 the lowerConstNtoMImp40 to set
	 */
	public void setLowerConstNtoMImp40(double lowerConstNtoMImp40) {
		this.lowerConstNtoMImp40 = lowerConstNtoMImp40;
	}

	/**
	 * @return the upperConstNtoMImp40
	 */
	public double getUpperConstNtoMImp40() {
		return upperConstNtoMImp40;
	}

	/**
	 * @param upperConstNtoMImp40 the upperConstNtoMImp40 to set
	 */
	public void setUpperConstNtoMImp40(double upperConstNtoMImp40) {
		this.upperConstNtoMImp40 = upperConstNtoMImp40;
	}

	/**
	 * @return the lowerSizeNtoMImp40
	 */
	public double getLowerSizeNtoMImp40() {
		return lowerSizeNtoMImp40;
	}

	/**
	 * @param lowerSizeNtoMImp40 the lowerSizeNtoMImp40 to set
	 */
	public void setLowerSizeNtoMImp40(double lowerSizeNtoMImp40) {
		this.lowerSizeNtoMImp40 = lowerSizeNtoMImp40;
	}

	/**
	 * @return the upperSizeNtoMImp40
	 */
	public double getUpperSizeNtoMImp40() {
		return upperSizeNtoMImp40;
	}

	/**
	 * @param upperSizeNtoMImp40 the upperSizeNtoMImp40 to set
	 */
	public void setUpperSizeNtoMImp40(double upperSizeNtoMImp40) {
		this.upperSizeNtoMImp40 = upperSizeNtoMImp40;
	}

	/**
	 * @return the lowerConst1to1Imp50
	 */
	public double getLowerConst1to1Imp50() {
		return lowerConst1to1Imp50;
	}

	/**
	 * @param lowerConst1to1Imp50 the lowerConst1to1Imp50 to set
	 */
	public void setLowerConst1to1Imp50(double lowerConst1to1Imp50) {
		this.lowerConst1to1Imp50 = lowerConst1to1Imp50;
	}

	/**
	 * @return the upperConst1to1Imp50
	 */
	public double getUpperConst1to1Imp50() {
		return upperConst1to1Imp50;
	}

	/**
	 * @param upperConst1to1Imp50 the upperConst1to1Imp50 to set
	 */
	public void setUpperConst1to1Imp50(double upperConst1to1Imp50) {
		this.upperConst1to1Imp50 = upperConst1to1Imp50;
	}

	/**
	 * @return the lowerSize1to1Imp50
	 */
	public double getLowerSize1to1Imp50() {
		return lowerSize1to1Imp50;
	}

	/**
	 * @param lowerSize1to1Imp50 the lowerSize1to1Imp50 to set
	 */
	public void setLowerSize1to1Imp50(double lowerSize1to1Imp50) {
		this.lowerSize1to1Imp50 = lowerSize1to1Imp50;
	}

	/**
	 * @return the upperSize1to1Imp50
	 */
	public double getUpperSize1to1Imp50() {
		return upperSize1to1Imp50;
	}

	/**
	 * @param upperSize1to1Imp50 the upperSize1to1Imp50 to set
	 */
	public void setUpperSize1to1Imp50(double upperSize1to1Imp50) {
		this.upperSize1to1Imp50 = upperSize1to1Imp50;
	}

	/**
	 * @return the lowerConst1toMImp50
	 */
	public double getLowerConst1toMImp50() {
		return lowerConst1toMImp50;
	}

	/**
	 * @param lowerConst1toMImp50 the lowerConst1toMImp50 to set
	 */
	public void setLowerConst1toMImp50(double lowerConst1toMImp50) {
		this.lowerConst1toMImp50 = lowerConst1toMImp50;
	}

	/**
	 * @return the upperConst1toMImp50
	 */
	public double getUpperConst1toMImp50() {
		return upperConst1toMImp50;
	}

	/**
	 * @param upperConst1toMImp50 the upperConst1toMImp50 to set
	 */
	public void setUpperConst1toMImp50(double upperConst1toMImp50) {
		this.upperConst1toMImp50 = upperConst1toMImp50;
	}

	/**
	 * @return the lowerSize1toMImp50
	 */
	public double getLowerSize1toMImp50() {
		return lowerSize1toMImp50;
	}

	/**
	 * @param lowerSize1toMImp50 the lowerSize1toMImp50 to set
	 */
	public void setLowerSize1toMImp50(double lowerSize1toMImp50) {
		this.lowerSize1toMImp50 = lowerSize1toMImp50;
	}

	/**
	 * @return the upperSize1toMImp50
	 */
	public double getUpperSize1toMImp50() {
		return upperSize1toMImp50;
	}

	/**
	 * @param upperSize1toMImp50 the upperSize1toMImp50 to set
	 */
	public void setUpperSize1toMImp50(double upperSize1toMImp50) {
		this.upperSize1toMImp50 = upperSize1toMImp50;
	}

	/**
	 * @return the lowerConstNtoMImp50
	 */
	public double getLowerConstNtoMImp50() {
		return lowerConstNtoMImp50;
	}

	/**
	 * @param lowerConstNtoMImp50 the lowerConstNtoMImp50 to set
	 */
	public void setLowerConstNtoMImp50(double lowerConstNtoMImp50) {
		this.lowerConstNtoMImp50 = lowerConstNtoMImp50;
	}

	/**
	 * @return the upperConstNtoMImp50
	 */
	public double getUpperConstNtoMImp50() {
		return upperConstNtoMImp50;
	}

	/**
	 * @param upperConstNtoMImp50 the upperConstNtoMImp50 to set
	 */
	public void setUpperConstNtoMImp50(double upperConstNtoMImp50) {
		this.upperConstNtoMImp50 = upperConstNtoMImp50;
	}

	/**
	 * @return the lowerSizeNtoMImp50
	 */
	public double getLowerSizeNtoMImp50() {
		return lowerSizeNtoMImp50;
	}

	/**
	 * @param lowerSizeNtoMImp50 the lowerSizeNtoMImp50 to set
	 */
	public void setLowerSizeNtoMImp50(double lowerSizeNtoMImp50) {
		this.lowerSizeNtoMImp50 = lowerSizeNtoMImp50;
	}

	/**
	 * @return the upperSizeNtoMImp50
	 */
	public double getUpperSizeNtoMImp50() {
		return upperSizeNtoMImp50;
	}

	/**
	 * @param upperSizeNtoMImp50 the upperSizeNtoMImp50 to set
	 */
	public void setUpperSizeNtoMImp50(double upperSizeNtoMImp50) {
		this.upperSizeNtoMImp50 = upperSizeNtoMImp50;
	}
}
