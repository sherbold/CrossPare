// Copyright 2015 Georg-August-Universität Göttingen, Germany
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package de.ugoe.cs.cpdp.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.ugoe.cs.cpdp.util.WekaUtils;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.Reorder;

/**
 * Loads the instances for a software version from an ARFF file of the NASA/SOFTLAB/MDP data.
 * 
 * @author Steffen Herbold
 */
public class NasaARFFLoader implements SingleVersionLoader {

    /**
     * used to map attributes the same attribute with different names to each other
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
        this.attributeNameMap = new HashMap<>();

        // Map entries for ar project
        this.attributeNameMap.put("total_loc", "LOC_TOTAL");
        this.attributeNameMap.put("comment_loc", "LOC_COMMENTS");
        this.attributeNameMap.put("code_and_comment_loc", "LOC_CODE_AND_COMMENT");
        this.attributeNameMap.put("executable_loc", "LOC_EXECUTABLE");
        this.attributeNameMap.put("unique_operands", "NUM_UNIQUE_OPERANDS");
        this.attributeNameMap.put("unique_operators", "NUM_UNIQUE_OPERATORS");
        this.attributeNameMap.put("total_operands", "NUM_OPERANDS");
        this.attributeNameMap.put("total_operators", "NUM_OPERATORS");
        this.attributeNameMap.put("halstead_length", "HALSTEAD_LENGTH");
        this.attributeNameMap.put("halstead_volume", "HALSTEAD_VOLUME");
        this.attributeNameMap.put("halstead_difficulty", "HALSTEAD_DIFFICULTY");
        this.attributeNameMap.put("halstead_effort", "HALSTEAD_EFFORT");
        this.attributeNameMap.put("halstead_error", "HALSTEAD_ERROR_EST");
        this.attributeNameMap.put("halstead_time", "HALSTEAD_PROG_TIME");
        this.attributeNameMap.put("branch_count", "BRANCH_COUNT");
        this.attributeNameMap.put("cyclomatic_complexity", "CYCLOMATIC_COMPLEXITY");
        this.attributeNameMap.put("design_complexity", "DESIGN_COMPLEXITY");

        // Map entries for KC2
        this.attributeNameMap.put("loc", "LOC_TOTAL");
        this.attributeNameMap.put("lOCode", "LOC_EXECUTABLE");
        this.attributeNameMap.put("lOComment", "LOC_COMMENTS");
        this.attributeNameMap.put("lOCodeAndComment", "LOC_CODE_AND_COMMENT");
        this.attributeNameMap.put("uniq_Op", "NUM_UNIQUE_OPERATORS");
        this.attributeNameMap.put("uniq_Opnd", "NUM_UNIQUE_OPERANDS");
        this.attributeNameMap.put("total_Op", "NUM_OPERATORS");
        this.attributeNameMap.put("total_Opnd", "NUM_OPERANDS");
        this.attributeNameMap.put("v", "HALSTEAD_VOLUME");
        this.attributeNameMap.put("l", "HALSTEAD_LENGTH");
        this.attributeNameMap.put("d", "HALSTEAD_DIFFICULTY");
        this.attributeNameMap.put("e", "HALSTEAD_EFFORT");
        this.attributeNameMap.put("b", "HALSTEAD_ERROR_EST");
        this.attributeNameMap.put("t", "HALSTEAD_PROG_TIME");
        this.attributeNameMap.put("branchCount", "BRANCH_COUNT");
        this.attributeNameMap.put("v(g)", "CYCLOMATIC_COMPLEXITY");
        this.attributeNameMap.put("iv(g)", "DESIGN_COMPLEXITY");

        this.attributeNameMap.put("defects", "bug");
        this.attributeNameMap.put("Defective", "bug");
        this.attributeNameMap.put("problems", "bug");
        this.attributeNameMap.put("label", "bug");

        // build list with normalized attribute order
        this.attributeOrder = new LinkedList<>();

        this.attributeOrder.add("LOC_TOTAL");
        this.attributeOrder.add("LOC_EXECUTABLE");
        this.attributeOrder.add("LOC_COMMENTS");
        this.attributeOrder.add("LOC_CODE_AND_COMMENT");
        this.attributeOrder.add("NUM_UNIQUE_OPERATORS");
        this.attributeOrder.add("NUM_UNIQUE_OPERANDS");
        this.attributeOrder.add("NUM_OPERATORS");
        this.attributeOrder.add("NUM_OPERANDS");
        this.attributeOrder.add("HALSTEAD_VOLUME");
        this.attributeOrder.add("HALSTEAD_LENGTH");
        this.attributeOrder.add("HALSTEAD_DIFFICULTY");
        this.attributeOrder.add("HALSTEAD_EFFORT");
        this.attributeOrder.add("HALSTEAD_ERROR_EST");
        this.attributeOrder.add("HALSTEAD_PROG_TIME");
        this.attributeOrder.add("BRANCH_COUNT");
        this.attributeOrder.add("CYCLOMATIC_COMPLEXITY");
        this.attributeOrder.add("DESIGN_COMPLEXITY");
        this.attributeOrder.add("bug");
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.SingleVersionLoader#load(java.io.File)
     */
    @Override
    public Instances load(File file, boolean binaryClass) {
        Instances data;
        try(BufferedReader reader = new BufferedReader(new FileReader(file));) {
            data = new Instances(reader);
        }
        catch (IOException e) {
            throw new RuntimeException("Error reading data", e);
        }

        // setting class attribute
        data.setClassIndex(data.numAttributes() - 1);

        // normalize attribute names
        for (int i = 0; i < data.numAttributes(); i++) {
            String mapValue = this.attributeNameMap.get(data.attribute(i).name());
            if (mapValue != null) {
                data.renameAttribute(i, mapValue);
            }
        }

        // determine new attribute order (unwanted attributes are implicitly
        // removed
        String orderString = "";
        for (String attName : this.attributeOrder) {
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
        }
        catch (Exception e) {
            throw new RuntimeException("Error while reordering the data", e);
        }
        if (data.numAttributes() != this.attributeOrder.size()) {
            throw new RuntimeException("Invalid number of attributes; filename: " + file.getName());
        }

        // normalize bug nominal values
        Add add = new Add();
        add.setAttributeIndex("last");
        add.setNominalLabels("0,1");
        add.setAttributeName("bug-new");
        try {
            add.setInputFormat(data);
            data = Filter.useFilter(data, add);
        }
        catch (Exception e) {
            throw new RuntimeException("Error while normalizing the bug nonminal values", e);
        }
        data.setRelationName(relationName);

        double classValue;

        String firstValue = data.classAttribute().enumerateValues().nextElement().toString();
        if (firstValue.equals("Y") || firstValue.equals("yes") || firstValue.equals("true")) {
            classValue = 0.0;
        }
        else {
            classValue = 1.0;
        }

        for (int i = 0; i < data.numInstances(); i++) {
            if (data.instance(i).classValue() == classValue) {
                data.instance(i).setValue(data.classIndex() + 1, 1.0);
            }
            else {
                data.instance(i).setValue(data.classIndex() + 1, 0.0);
            }
        }

        int oldClassIndex = data.classIndex();
        data.setClassIndex(oldClassIndex + 1);
        data.deleteAttributeAt(oldClassIndex);
        
        if(!binaryClass) {
            WekaUtils.makeClassNumeric(data);
        }

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
