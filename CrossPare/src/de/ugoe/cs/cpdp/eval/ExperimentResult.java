package de.ugoe.cs.cpdp.eval;

public class ExperimentResult {

    private final String configurationName;
    private final String productName;
    private final String classifier;
    
    public ExperimentResult(String configurationName, String productName, String classifier) {
        this.configurationName = configurationName;
        this.productName = productName;
        this.classifier = classifier;
    }
    
    int sizeTestData;
    int sizeTrainingData;
    double succHe = Double.NaN;
    double succZi = Double.NaN;
    double succG75 = Double.NaN;
    double succG60 = Double.NaN;
    double error = Double.NaN;
    double recall = Double.NaN;
    double precision = Double.NaN;
    double fscore = Double.NaN;
    double gscore = Double.NaN;
    double mcc = Double.NaN;
    double auc = Double.NaN;
    double aucec = Double.NaN;
    double tpr = Double.NaN;
    double tnr = Double.NaN;
    double fpr = Double.NaN;
    double fnr = Double.NaN;
    double tp = Double.NaN;
    double fn = Double.NaN;
    double tn = Double.NaN;
    double fp = Double.NaN;

    public String getConfigurationName() {
        return configurationName;
    }
    public String getProductName() {
        return productName;
    }
    public String getClassifier() {
        return classifier;
    }
    public int getSizeTestData() {
        return sizeTestData;
    }
    public void setSizeTestData(int sizeTestData) {
        this.sizeTestData = sizeTestData;
    }
    public int getSizeTrainingData() {
        return sizeTrainingData;
    }
    public void setSizeTrainingData(int sizeTrainingData) {
        this.sizeTrainingData = sizeTrainingData;
    }
    public double getSuccHe() {
        return succHe;
    }
    public void setSuccHe(double succHe) {
        this.succHe = succHe;
    }
    public double getSuccZi() {
        return succZi;
    }
    public void setSuccZi(double succZi) {
        this.succZi = succZi;
    }
    public double getSuccG75() {
        return succG75;
    }
    public void setSuccG75(double succG75) {
        this.succG75 = succG75;
    }
    public double getSuccG60() {
        return succG60;
    }
    public void setSuccG60(double succG60) {
        this.succG60 = succG60;
    }
    public double getError() {
        return error;
    }
    public void setError(double error) {
        this.error = error;
    }
    public double getRecall() {
        return recall;
    }
    public void setRecall(double recall) {
        this.recall = recall;
    }
    public double getPrecision() {
        return precision;
    }
    public void setPrecision(double precision) {
        this.precision = precision;
    }
    public double getFscore() {
        return fscore;
    }
    public void setFscore(double fscore) {
        this.fscore = fscore;
    }
    public double getGscore() {
        return gscore;
    }
    public void setGscore(double gscore) {
        this.gscore = gscore;
    }
    public double getMcc() {
        return mcc;
    }
    public void setMcc(double mcc) {
        this.mcc = mcc;
    }
    public double getAuc() {
        return auc;
    }
    public void setAuc(double auc) {
        this.auc = auc;
    }
    public double getAucec() {
        return aucec;
    }
    public void setAucec(double aucec) {
        this.aucec = aucec;
    }
    public double getTpr() {
        return tpr;
    }
    public void setTpr(double tpr) {
        this.tpr = tpr;
    }
    public double getTnr() {
        return tnr;
    }
    public void setTnr(double tnr) {
        this.tnr = tnr;
    }
    public double getFpr() {
        return fpr;
    }
    public void setFpr(double fpr) {
        this.fpr = fpr;
    }
    public double getFnr() {
        return fnr;
    }
    public void setFnr(double fnr) {
        this.fnr = fnr;
    }
    public double getTp() {
        return tp;
    }
    public void setTp(double tp) {
        this.tp = tp;
    }
    public double getFn() {
        return fn;
    }
    public void setFn(double fn) {
        this.fn = fn;
    }
    public double getTn() {
        return tn;
    }
    public void setTn(double tn) {
        this.tn = tn;
    }
    public double getFp() {
        return fp;
    }
    public void setFp(double fp) {
        this.fp = fp;
    }
}
