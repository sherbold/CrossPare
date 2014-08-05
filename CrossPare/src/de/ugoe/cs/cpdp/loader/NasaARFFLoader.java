package de.ugoe.cs.cpdp.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.Reorder;

/**
 * Loads the instances for a software version from an ARFF file of the
 * NASA/SOFTLAB/MDP data.
 * 
 * @author Steffen Herbold
 */
public class NasaARFFLoader implements SingleVersionLoader {

	/**
	 * used to map attributes the same attribute with different names to each
	 * other
	 */
	Map<String, String> attributeNameMap;

	/**
	 * used to ensure that the attribute order is the same after loading
	 */
	List<String> attributeOrder;

	/**
	 * Constructor. Creates a new NasaARFFLoader.
	 */
	public NasaARFFLoader() {
		attributeNameMap = new HashMap<>();

		// Map entries for ar project
		attributeNameMap.put("total_loc", "LOC_TOTAL");
		attributeNameMap.put("comment_loc", "LOC_COMMENTS");
		attributeNameMap.put("code_and_comment_loc", "LOC_CODE_AND_COMMENT");
		attributeNameMap.put("executable_loc", "LOC_EXECUTABLE");
		attributeNameMap.put("unique_operands", "NUM_UNIQUE_OPERANDS");
		attributeNameMap.put("unique_operators", "NUM_UNIQUE_OPERATORS");
		attributeNameMap.put("total_operands", "NUM_OPERANDS");
		attributeNameMap.put("total_operators", "NUM_OPERATORS");
		attributeNameMap.put("halstead_length", "HALSTEAD_LENGTH");
		attributeNameMap.put("halstead_volume", "HALSTEAD_VOLUME");
		attributeNameMap.put("halstead_difficulty", "HALSTEAD_DIFFICULTY");
		attributeNameMap.put("halstead_effort", "HALSTEAD_EFFORT");
		attributeNameMap.put("halstead_error", "HALSTEAD_ERROR_EST");
		attributeNameMap.put("halstead_time", "HALSTEAD_PROG_TIME");
		attributeNameMap.put("branch_count", "BRANCH_COUNT");
		attributeNameMap.put("cyclomatic_complexity", "CYCLOMATIC_COMPLEXITY");
		attributeNameMap.put("design_complexity", "DESIGN_COMPLEXITY");

		// Map entries for KC2
		attributeNameMap.put("loc", "LOC_TOTAL");
		attributeNameMap.put("lOCode", "LOC_EXECUTABLE");
		attributeNameMap.put("lOComment", "LOC_COMMENTS");
		attributeNameMap.put("lOCodeAndComment", "LOC_CODE_AND_COMMENT");
		attributeNameMap.put("uniq_Op", "NUM_UNIQUE_OPERATORS");
		attributeNameMap.put("uniq_Opnd", "NUM_UNIQUE_OPERANDS");
		attributeNameMap.put("total_Op", "NUM_OPERATORS");
		attributeNameMap.put("total_Opnd", "NUM_OPERANDS");
		attributeNameMap.put("v", "HALSTEAD_VOLUME");
		attributeNameMap.put("l", "HALSTEAD_LENGTH");
		attributeNameMap.put("d", "HALSTEAD_DIFFICULTY");
		attributeNameMap.put("e", "HALSTEAD_EFFORT");
		attributeNameMap.put("b", "HALSTEAD_ERROR_EST");
		attributeNameMap.put("t", "HALSTEAD_PROG_TIME");
		attributeNameMap.put("branchCount", "BRANCH_COUNT");
		attributeNameMap.put("v(g)", "CYCLOMATIC_COMPLEXITY");
		attributeNameMap.put("iv(g)", "DESIGN_COMPLEXITY");

		attributeNameMap.put("defects", "bug");
		attributeNameMap.put("Defective", "bug");
		attributeNameMap.put("problems", "bug");
		attributeNameMap.put("label", "bug");

		// build list with normalized attribute order
		attributeOrder = new LinkedList<>();

		attributeOrder.add("LOC_TOTAL");
		attributeOrder.add("LOC_EXECUTABLE");
		attributeOrder.add("LOC_COMMENTS");
		attributeOrder.add("LOC_CODE_AND_COMMENT");
		attributeOrder.add("NUM_UNIQUE_OPERATORS");
		attributeOrder.add("NUM_UNIQUE_OPERANDS");
		attributeOrder.add("NUM_OPERATORS");
		attributeOrder.add("NUM_OPERANDS");
		attributeOrder.add("HALSTEAD_VOLUME");
		attributeOrder.add("HALSTEAD_LENGTH");
		attributeOrder.add("HALSTEAD_DIFFICULTY");
		attributeOrder.add("HALSTEAD_EFFORT");
		attributeOrder.add("HALSTEAD_ERROR_EST");
		attributeOrder.add("HALSTEAD_PROG_TIME");
		attributeOrder.add("BRANCH_COUNT");
		attributeOrder.add("CYCLOMATIC_COMPLEXITY");
		attributeOrder.add("DESIGN_COMPLEXITY");
		attributeOrder.add("bug");
	}

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
			throw new RuntimeException("Error reading data", e);
		}

		// setting class attribute
		data.setClassIndex(data.numAttributes() - 1);

		// normalize attribute names
		for (int i = 0; i < data.numAttributes(); i++) {
			String mapValue = attributeNameMap.get(data.attribute(i).name());
			if (mapValue != null) {
				data.renameAttribute(i, mapValue);
			}
		}

		// determine new attribute order (unwanted attributes are implicitly
		// removed
		String orderString = "";
		for (String attName : attributeOrder) {
			for (int i = 0; i < data.numAttributes(); i++) {
				if (attName.equals(data.attribute(i).name())) {
					orderString += (i + 1) + ",";
				}
			}
		}
		orderString = orderString.substring(0, orderString.length() - 1);

		String relationName = data.relationName();
		String[] options = new String[2];
		options[0] = "-R";
		options[1] = orderString;
		Reorder reorder = new Reorder();
		try {
			reorder.setOptions(options);
			reorder.setInputFormat(data);
			data = Filter.useFilter(data, reorder);
		} catch (Exception e) {
			throw new RuntimeException("Error while reordering the data", e);
		}
		if (data.numAttributes() != attributeOrder.size()) {
			throw new RuntimeException(
					"Invalid number of attributes; filename: " + file.getName());
		}

		// normalize bug nominal values
		Add add = new Add();
		add.setAttributeIndex("last");
		add.setNominalLabels("0,1");
		add.setAttributeName("bug-new");
		try {
			add.setInputFormat(data);
			data = Filter.useFilter(data, add);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error while normalizing the bug nonminal values", e);
		}
		data.setRelationName(relationName);

		double classValue;

		String firstValue = data.classAttribute().enumerateValues()
				.nextElement().toString();
		if (firstValue.equals("Y") || firstValue.equals("yes")
				|| firstValue.equals("true")) {
			classValue = 0.0;
		} else {
			classValue = 1.0;
		}

		for (int i = 0; i < data.numInstances(); i++) {
			if (data.instance(i).classValue() == classValue) {
				data.instance(i).setValue(data.classIndex() + 1, 1.0);
			} else {
				data.instance(i).setValue(data.classIndex() + 1, 0.0);
			}
		}

		int oldClassIndex = data.classIndex();
		data.setClassIndex(oldClassIndex + 1);
		data.deleteAttributeAt(oldClassIndex);

		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader.SingleVersionLoader#
	 * filenameFilter(java.lang.String)
	 */
	@Override
	public boolean filenameFilter(String filename) {
		return filename.endsWith(".arff");
	}

}
