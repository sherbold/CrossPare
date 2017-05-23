
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
     * Effort of the prediction
     */
    double aucec = Double.NaN;

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
        return configurationName;
    }

    /**
     * <p>
     * returns the product name
     * </p>
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * <p>
     * returns the name of the training product
     * </p>
     *
     * @return the name of the training product
     */
    public String getTrainProductName() {
        return trainProductName;
    }

    /**
     * <p>
     * returns the classifier name
     * </p>
     *
     * @return the classifier name
     */
    public String getClassifier() {
        return classifier;
    }

    /**
     * <p>
     * returns the number of instances of the target product
     * </p>
     *
     * @return number of instances
     */
    public int getSizeTestData() {
        return sizeTestData;
    }

    /**
     * <p>
     * sets the number of instances of the target product
     * </p>
     *
     * @param sizeTestData
     *            number of instances
     */
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
        return sizeTrainingData;
    }

    /**
     * <p>
     * sets the number of instances of the training data
     * </p>
     *
     * @param sizeTrainingData
     *            number of instances
     */
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
        return error;
    }

    /**
     * <p>
     * sets the error
     * </p>
     *
     * @param error
     *            the error
     */
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
        return recall;
    }

    /**
     * <p>
     * sets the recall
     * </p>
     *
     * @param recall
     *            the recall
     */
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
        return precision;
    }

    /**
     * <p>
     * sets the precision
     * </p>
     *
     * @param precision
     *            the precision
     */
    public void setPrecision(double precision) {
        this.precision = precision;
    }

    /**
     * <p>
     * returns the F1 score
     * </p>
     *
     * @return the F1 score
     */
    public double getFscore() {
        return fscore;
    }

    /**
     * <p>
     * sets the F1 score
     * </p>
     *
     * @param fscore
     *            the F1 score
     */
    public void setFscore(double fscore) {
        this.fscore = fscore;
    }

    /**
     * <p>
     * returns the G score
     * </p>
     *
     * @return the G score
     */
    public double getGscore() {
        return gscore;
    }

    /**
     * <p>
     * sets the G score
     * </p>
     *
     * @param gscore
     *            the G score
     */
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
        return mcc;
    }

    /**
     * <p>
     * sets the MCC
     * </p>
     *
     * @param mcc
     *            the MCC
     */
    public void setMcc(double mcc) {
        this.mcc = mcc;
    }

    /**
     * <p>
     * returns the AUC
     * </p>
     *
     * @return the AUC
     */
    public double getAuc() {
        return auc;
    }

    /**
     * <p>
     * sets the AUC
     * </p>
     *
     * @param auc
     *            the AUC
     */
    public void setAuc(double auc) {
        this.auc = auc;
    }

    /**
     * <p>
     * returns the effort as AUCEC
     * </p>
     *
     * @return the effort
     */
    public double getAucec() {
        return aucec;
    }

    /**
     * <p>
     * sets the effort as AUCEC
     * </p>
     *
     * @param aucec
     *            the effort
     */
    public void setAucec(double aucec) {
        this.aucec = aucec;
    }

    /**
     * <p>
     * returns the TPR
     * </p>
     *
     * @return the TPR
     */
    public double getTpr() {
        return tpr;
    }

    /**
     * <p>
     * sets the TPR
     * </p>
     *
     * @param tpr
     *            the TPR
     */
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
        return tnr;
    }

    /**
     * <p>
     * sets the TNR
     * </p>
     *
     * @param tnr
     *            the TNR
     */
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
        return fpr;
    }

    /**
     * <p>
     * sets the FPR
     * </p>
     *
     * @param fpr
     *            the FPR
     */
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
        return fnr;
    }

    /**
     * <p>
     * sets the FNR
     * </p>
     *
     * @param fnr
     *            the FNR
     */
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
        return tp;
    }

    /**
     * <p>
     * sets the TPs
     * </p>
     *
     * @param tp
     *            the TPs
     */
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
        return fn;
    }

    /**
     * <p>
     * sets the FNs
     * </p>
     *
     * @param fn
     *            the FNs
     */
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
        return tn;
    }

    /**
     * <p>
     * sets the TNs
     * </p>
     *
     * @param tn
     *            the TNs
     */
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
        return fp;
    }

    /**
     * <p>
     * sets the FPs
     * </p>
     *
     * @param fp
     *            the FPs
     */
    public void setFp(double fp) {
        this.fp = fp;
    }
}
