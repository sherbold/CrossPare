package de.ugoe.cs.cpdp.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.ugoe.cs.cpdp.IParameterizable;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

/**
 * Loader for just-in-time data
 * @author jvdmosel
 */
public class JitDataLoader implements SingleVersionLoader, IBugMatrixLoader, IParameterizable {
	
	/**
	 * the bug matrix
	 */
	private Instances bugMatrix = null;
	
	/**
	 * the used label
	 * default is jira
	 */
	private String label = "jira";
	
	/**
	 * the committer dates
	 */
	private List<OffsetDateTime> committerDates = new ArrayList<>();

	/**
     * names of the attributes to be kept (determined by {@link #setParameter(String)})
     */
	private ArrayList<String> attributeNames = new ArrayList<>();

	/**
     * Sets the label to be used and the attributes that will be kept. The string contains the label and the blank-separated regular expressions of the
     * attributes to be kept. <br>
     * <br>
     * Note, that the regular expressions currently must not contain whitespaces.
     * 
     * @param parameters
     *            string with the label and blank-separated attribute names
     */
	@Override
	public void setParameter(String parameters) {
		if (parameters != null) {
			String[] parameterArray = parameters.split(" ");
			int index = 0;
			if(parameterArray[index].equals("adhoc") || parameterArray[index].equals("jira")) {
				label = parameterArray[index];
				index++;
			}
			while(index < parameterArray.length) {
				this.attributeNames.add(parameterArray[index]);
				index++;
			}
		}
	}
	
	/**
	 * Splits a line outside of labels into parts using a delimiter
	 * @param line
	 *            line to be split
	 * @param delimiter
	 *            delimiter that is used
	 * @return
	 */			
	private static String[] split(final String line, final char delimiter) {
		CharSequence[] temp = new CharSequence[(line.length() / 2) + 1];
		int wordCount = 0;
		// indices of the first split
		int i = 0;
		int j = line.indexOf(delimiter, 0);
		// start and end indices of labels to be skipped
		int adhocLabelStart = line.indexOf('"', 1);
		int adhocLabelEnd = line.indexOf('"', adhocLabelStart+1);
		int jiraLabelStart = line.indexOf('"', adhocLabelEnd+1);
		int jiraLabelEnd = line.indexOf('"', jiraLabelStart+1);
		while (j >= 0) {
			// split
			temp[wordCount++] = line.substring(i, j);
			// start index of next split
			i = j + 1;
			// set end index of next split
			if(i == adhocLabelStart) {
				j = adhocLabelEnd+1;
			} else if(i == jiraLabelStart){
				j = jiraLabelEnd+1;
			} else {
				j = line.indexOf(delimiter, i);
			}
		}
		temp[wordCount++] = line.substring(i); 
		String[] parts = new String[wordCount];
		System.arraycopy(temp, 0, parts, 0, wordCount);
		return parts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader.SingleVersionLoader#load(
	 * java.io.File)
	 */
	@Override
	public Instances load(File file, boolean binaryClass) {
		final String[] lines;
		try {
			List<String> stringList = Files.readAllLines(file.toPath());
			lines = stringList.toArray(new String[] {});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// configure Instances
		final ArrayList<Attribute> atts = new ArrayList<>();

		String[] lineSplit = lines[0].split(",");
		List<Integer> usedAttributes = new ArrayList<>();
		int index = 3;
		while (!"label_adhoc".equals(lineSplit[index])) {
			// skip previous inducing as this was more of a test
			if("previous_inducing".equals(lineSplit[index])) {
				index++;
				continue;
			}
			String curAttribute = lineSplit[index];
			boolean hasMatch = false;
			for( String regex : attributeNames ) {
				if( curAttribute.matches(regex) ) {
					hasMatch = true;
				}
			}
			if( hasMatch || attributeNames.isEmpty() ) {
				atts.add(new Attribute(curAttribute));
				usedAttributes.add(index);
			}
			index++;
		}
		
		// set correct label index
		int labelIndex = (label.equals("adhoc")) ? index : index + 1;
		int issueMatrixIndex = index + 4;
		Attribute classAtt;
		if (binaryClass) {
			// add nominal class attribute
			final ArrayList<String> classAttVals = new ArrayList<>();
			classAttVals.add("0");
			classAttVals.add("1");
			classAtt = new Attribute("bug", classAttVals);
		} else {
			// add numeric class attribute
			classAtt = new Attribute("bugs");
		}
		atts.add(classAtt);
		
		final Instances data = new Instances(file.getName(), atts, 0);
		data.setClass(classAtt);

		// fetch data
		for (int i = 1; i < lines.length; i++) {
			lineSplit = split(lines[i], ',');;
			double[] values = new double[usedAttributes.size()+1];
			for (int j = 0; j < values.length - 1; j++) {
				String value = lineSplit[usedAttributes.get(j)].trim();
				// encode truth values to binary
				if(!"True".equals(value) && !"False".equals(value)) {
					values[j] = Double.parseDouble(lineSplit[usedAttributes.get(j)].trim());
				} else {
					values[j] = ("False".equals(value)) ? 0 : 1;
				}
			}
			List<String> issueList = Arrays.asList(lineSplit[labelIndex].trim().split("\\s*,\\s*"));
			if (binaryClass) {
				// nominal class value
				values[values.length - 1] = (issueList.size() == 0) ? 0 : 1;
			} else {
				// numeric class value
				values[values.length - 1] = issueList.size();
			}
			data.add(new DenseInstance(1.0, values));
		}
		
		// create issue matrix
		final ArrayList<Attribute> issueMatrixAtts = new ArrayList<>();
		List<Integer> usedIssues = new ArrayList<>();
		lineSplit = lines[0].split(",");
		while (issueMatrixIndex < lineSplit.length) {
			// determine whether its an adhoc or jira issue
			String keyword = lineSplit[issueMatrixIndex].substring(0,5);
			// skip bugmatrix entries that are not part of the label
			if(!label.equals(keyword)) {
				issueMatrixIndex++;
				continue;
			}
			issueMatrixAtts.add(new Attribute(lineSplit[issueMatrixIndex]));
			usedIssues.add(issueMatrixIndex);
			issueMatrixIndex++;
		}
		bugMatrix = new Instances(file.getName(), issueMatrixAtts, 0);
		for (int i = 1; i < lines.length; i++) {
			lineSplit = split(lines[i], ',');
			double[] values = new double[issueMatrixAtts.size()];
			for (int j = 0; j < values.length; j++) {
				values[j] = Double.parseDouble(lineSplit[usedIssues.get(j)].trim());
			}
			bugMatrix.add(new DenseInstance(1.0, values));
		}

		// fetch committer dates
		int dateIndex = 1;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
		committerDates = new ArrayList<>();
		for (int i = 1; i < lines.length; i++) {
			lineSplit = split(lines[i], ',');
			String dateString = lineSplit[dateIndex];
			committerDates.add(OffsetDateTime.parse(dateString, formatter));
		}
        return data;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ugoe.cs.cpdp.loader.IBugMatrixLoader#getBugMatrix()
	 */
	@Override
	public Instances getBugMatrix() {
		return this.bugMatrix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader.SingleVersionLoader#
	 * filenameFilter(java.lang.String)
	 */
	@Override
	public boolean filenameFilter(String filename) {
		return filename.endsWith(".csv");
	}
	
	/**
	 * Returns a list of committer dates
	 * @return dates of the commits 
	 */
	public List<OffsetDateTime> getCommitterDates() {
		return this.committerDates;
	}
}
