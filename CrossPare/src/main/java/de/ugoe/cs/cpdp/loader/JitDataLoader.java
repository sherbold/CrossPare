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
 * @author jvdmosel
 *
 */
public class JitDataLoader implements SingleVersionLoader, IBugMatrixLoader, IParameterizable {
	
	private Instances bugMatrix = null;
	
	private boolean adhoc = false;
	
	private ArrayList<OffsetDateTime> committerDates = new ArrayList<>();
	
	private ArrayList<String> attributeNames = new ArrayList<>();
	
	@Override
	public void setParameter(String parameters) {
		if (parameters != null) {
            String[] parameterArray = parameters.split(" ");
            int index = 0;
            if(parameterArray[index].equals("adhoc")) {
            	this.adhoc = true;
            	index++;
            }
            while(index < parameterArray.length) {
                this.attributeNames.add(parameterArray[index]);
                index++;
            }
        }
	}
	
	private static String[] split(final String line)
	{
	    CharSequence[] temp = new CharSequence[(line.length() / 2) + 1];
	    int wordCount = 0;
	    int i = 0;
	    int j = line.indexOf(',', 0); // first substring
	    int adhocLabelStart = line.indexOf('"', 1);
	    int adhocLabelEnd = line.indexOf('"', adhocLabelStart+1);
	    int jiraLabelStart = line.indexOf('"', adhocLabelEnd+1);
	    int jiraLabelEnd = line.indexOf('"', jiraLabelStart+1);
	    while (j >= 0) {
	        temp[wordCount++] = line.substring(i, j);
	        i = j + 1;
	        if(i == adhocLabelStart) {
	        	j = adhocLabelEnd+1;
	        } else if(i == jiraLabelStart){
	        	j = jiraLabelEnd+1;
	        } else {
		        j = line.indexOf(',', i); // rest of substrings
	        }
	    }
	    temp[wordCount++] = line.substring(i); // last substring
	    String[] result = new String[wordCount];
	    System.arraycopy(temp, 0, result, 0, wordCount);
	    return result;
	}

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
		int committerDateIndex = 1;
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
		
		int labelIndex = (adhoc) ? index : index + 1;
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
			lineSplit = split(lines[i]);;
			double[] values = new double[usedAttributes.size()+1];
			for (int j = 0; j < values.length - 1; j++) {
				String value = lineSplit[usedAttributes.get(j)].trim();
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
		
		// create issue matrix and fetch committer dates
		final ArrayList<Attribute> issueMatrixAtts = new ArrayList<>();
		List<Integer> usedIssues = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
		lineSplit = lines[0].split(",");
		while (issueMatrixIndex < lineSplit.length) {
			// determine whether its an adhoc or jira issue
			String keyword = lineSplit[issueMatrixIndex].substring(0,5);
			if((this.adhoc && !"adhoc".equals(keyword)) || (!this.adhoc && "adhoc".equals(keyword))) {
				issueMatrixIndex++;
				continue;
			}
			issueMatrixAtts.add(new Attribute(lineSplit[issueMatrixIndex]));
			usedIssues.add(issueMatrixIndex);
			issueMatrixIndex++;
		}
		bugMatrix = new Instances(file.getName(), issueMatrixAtts, 0);
		committerDates = new ArrayList<>();
		for (int i = 1; i < lines.length; i++) {
			lineSplit = split(lines[i]);
			double[] values = new double[issueMatrixAtts.size()];
			for (int j = 0; j < values.length; j++) {
				if("True".equals(lineSplit[usedIssues.get(j)].trim())) {
					System.out.println("Test");
				}
				values[j] = Double.parseDouble(lineSplit[usedIssues.get(j)].trim());
			}
			bugMatrix.add(new DenseInstance(1.0, values));
			String dateString = lineSplit[committerDateIndex];
			committerDates.add(OffsetDateTime.parse(dateString, formatter)); 
		}
        return data;
	}
	
	@Override
	public Instances getBugMatrix() {
		return this.bugMatrix;
	}

	@Override
	public boolean filenameFilter(String filename) {
		return filename.endsWith(".csv");
	}
	
	/**
	 * Returns a list of committer dates
	 * @return dates of the commits 
	 */
	public ArrayList<OffsetDateTime> getCommitterDates() {
		return this.committerDates;
	}
}
