package de.ugoe.cs.cpdp.loader;

import java.io.File;

import org.junit.Test;

import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class AUDIChangeLoaderTest {

	@Test
	public void load() throws Exception {
		AUDIChangeLoader loader = new AUDIChangeLoader();
		ArffSaver saver = new ArffSaver();
		Instances data;
		
		data = loader.load(new File("C:/SWESVN/Papers/ICTSS/2015/trunk/defectprediction/Datenauswertung/A_mask_metric_src.csv"));
		saver.setInstances(data);
		saver.setFile(new File("C:/SWESVN/Papers/ICTSS/2015/trunk/defectprediction/Datenauswertung/A_mask_change.arff"));
		saver.writeBatch();
		
		data = loader.load(new File("C:/SWESVN/Papers/ICTSS/2015/trunk/defectprediction/Datenauswertung/K_mask_metric_src.csv"));
		saver.setInstances(data);
		saver.setFile(new File("C:/SWESVN/Papers/ICTSS/2015/trunk/defectprediction/Datenauswertung/K_mask_change.arff"));
		saver.writeBatch();
		
		data = loader.load(new File("C:/SWESVN/Papers/ICTSS/2015/trunk/defectprediction/Datenauswertung/L_mask_metric_src.csv"));
		saver.setInstances(data);
		saver.setFile(new File("C:/SWESVN/Papers/ICTSS/2015/trunk/defectprediction/Datenauswertung/L_mask_change.arff"));
		saver.writeBatch();
	}

}
