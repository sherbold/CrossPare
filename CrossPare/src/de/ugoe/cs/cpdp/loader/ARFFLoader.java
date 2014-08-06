package de.ugoe.cs.cpdp.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

/**
 * Loads ARFF files and chooses the last attribute as class attribute.
 * 
 * @author Steffen Herbold
 */
public class ARFFLoader implements SingleVersionLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ugoe.cs.cpdp.loader.SingleVersionLoader#load(java.io.File)
	 */
	@Override
	public Instances load(File file) {
		BufferedReader reader;
		Instances data;
		try {
			reader = new BufferedReader(new FileReader(file));
			data = new Instances(reader);
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("error reading file: " + file.getName(), e);
		}

		// setting class attribute
		data.setClassIndex(data.numAttributes() - 1);

		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.ugoe.cs.cpdp.loader.SingleVersionLoader#filenameFilter(java.lang.String
	 * )
	 */
	@Override
	public boolean filenameFilter(String filename) {
		return filename.endsWith(".arff");
	}

}
