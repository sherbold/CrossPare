
package de.ugoe.cs.cpdp.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import weka.core.Instances;

/**
 * <p>
 * Loads data from the RELINK data set.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class RelinkLoader implements SingleVersionLoader {

    @Override
    public Instances load(File file) {
        Instances tmpData;
        try(BufferedReader reader = new BufferedReader(new FileReader(file));) {
            tmpData = new Instances(reader);
        }
        catch (IOException e) {
            throw new RuntimeException("error reading file: " + file.getName(), e);
        }

        Set<String> attrNames = new HashSet<>();
        attrNames.add("AvgCyclomatic");
        attrNames.add("AvgCyclomaticModified");
        attrNames.add("AvgCyclomaticStrict");
        attrNames.add("AvgEssential");
        attrNames.add("AvgLine");
        attrNames.add("AvgLineBlank");
        attrNames.add("AvgLineCode");
        attrNames.add("AvgLineComment");
        attrNames.add("CountClassBase");
        attrNames.add("CountClassCoupled");
        attrNames.add("CountClassDerived");
        attrNames.add("CountDeclClassMethod");
        attrNames.add("CountDeclClassVariable");
        attrNames.add("CountDeclInstanceMethod");
        attrNames.add("CountDeclInstanceVariable");
        attrNames.add("CountDeclMethod");
        attrNames.add("CountDeclMethodAll");
        attrNames.add("CountDeclMethodPrivate");
        attrNames.add("CountDeclMethodProtected");
        attrNames.add("CountDeclMethodPublic");
        attrNames.add("CountLine");
        attrNames.add("CountLineBlank");
        attrNames.add("CountLineCode");
        attrNames.add("CountLineCodeDecl");
        attrNames.add("CountLineCodeExe");
        attrNames.add("CountLineComment");
        attrNames.add("CountSemicolon");
        attrNames.add("CountStmt");
        attrNames.add("CountStmtDecl");
        attrNames.add("CountStmtExe");
        attrNames.add("MaxCyclomatic");
        attrNames.add("MaxCyclomaticModified");
        attrNames.add("MaxCyclomaticStrict");
        attrNames.add("MaxInheritanceTree");
        attrNames.add("PercentLackOfCohesion");
        attrNames.add("RatioCommentToCode");
        attrNames.add("SumCyclomatic");
        attrNames.add("SumCyclomaticModified");
        attrNames.add("SumCyclomaticStrict");
        attrNames.add("SumEssential");
        attrNames.add("isDefective");

        for (int j = tmpData.numAttributes() - 1; j >= 0; j--) {
            if (!attrNames.contains(tmpData.attribute(j).name())) {
                tmpData.deleteAttributeAt(j);
            }
        }

        // setting class attribute
        tmpData.setClassIndex(tmpData.numAttributes() - 1);

        return tmpData;
    }

    @Override
    public boolean filenameFilter(String file) {
        return file.endsWith(".arff");
    }

}
