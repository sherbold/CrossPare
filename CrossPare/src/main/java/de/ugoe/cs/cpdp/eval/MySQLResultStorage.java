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

package de.ugoe.cs.cpdp.eval;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * <p>
 * Implements a storage of experiment results in a MySQL database.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class MySQLResultStorage implements IResultStorage {

	/**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");
	
    /**
     * Name of the table where the results are stored.
     */
    final String resultsTableName;

    /**
     * Connection pool for the data base.
     */
    private MysqlDataSource connectionPool = null;

    /**
     * <p>
     * Creates a MySQLResultStorage with the default parameter file mysql.cred from the working
     * directory.
     * </p>
     * 
     * @see #MySQLResultStorage(String)
     */
    public MySQLResultStorage() {
        this("mysql.cred");
    }

    /**
     * <p>
     * Creates a new results storage. Tries to read a properties file located in the working
     * directory. If this file is not found or any parameter is not defined, the following default
     * values are used:
     * <ul>
     * <li>db.host = localhost</li>
     * <li>db.port = 3306</li>
     * <li>db.name = crosspare</li>
     * <li>db.user = crosspare</li>
     * <li>db.pass = crosspare</li>
     * <li>db.results.tablename = results</li>
     * <li>db.results.createtable = false</li>
     * </p>
     * 
     * @param parameterFile
     *            name of the parameter file
     */
    public MySQLResultStorage(String parameterFile) {
        Properties dbProperties = new Properties();
        try(FileInputStream is = new FileInputStream(parameterFile);) {
            dbProperties.load(is);
        }
        catch (IOException e) {
            LOGGER.error("Could not load mysql.cred file: " + e.getMessage());
            LOGGER.error("Must be a properties file located in working directory.");
            LOGGER.warn("Using default DB configuration since mysql.cred file could not be loaded");
        }
        String dbHost = dbProperties.getProperty("db.host", "localhost");
        String dbPort = dbProperties.getProperty("db.port", "3306");
        String dbName = dbProperties.getProperty("db.name", "crosspare");
        String dbUser = dbProperties.getProperty("db.user", "crosspare");
        String dbPass = dbProperties.getProperty("db.pass", "crosspare");
        this.resultsTableName = dbProperties.getProperty("db.results.tablename", "results");
        boolean createTableIfNotExists =
            Boolean.parseBoolean(dbProperties.getProperty("db.results.createtable", "false"));
        connectToDB(dbHost, dbPort, dbName, dbUser, dbPass);

        // create the results table if required
        if (!doesResultsTableExist() && createTableIfNotExists) {
            createResultsTable();
        }
    }

    /**
     * <p>
     * Sets up the database connection
     * </p>
     *
     * @param dbHost
     *            host of the database
     * @param dbPort
     *            port of the database
     * @param dbName
     *            name of the database
     * @param dbUser
     *            user of the database
     * @param dbPass
     *            password of the user
     */
    private void connectToDB(String dbHost,
                             String dbPort,
                             String dbName,
                             String dbUser,
                             String dbPass)
    {
        this.connectionPool = new MysqlDataSource();
        this.connectionPool.setUser(dbUser);
        this.connectionPool.setPassword(dbPass);
        this.connectionPool.setUrl("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.eval.IResultStorage#addResult(de.ugoe.cs.cpdp.eval.ExperimentResult)
     */
    @Override
    public void addResult(ExperimentResult result) {
        StringBuilder preparedSql = new StringBuilder();
        preparedSql.append("INSERT INTO " + this.resultsTableName + " (");
        preparedSql.append("`configurationName`,");
        preparedSql.append("`productName`,");
        preparedSql.append("`classifier`,");
        preparedSql.append("`testsize`,");
        preparedSql.append("`trainsize`,");
        preparedSql.append("`error`,");
        preparedSql.append("`recall`,");
        preparedSql.append("`precision`,");
        preparedSql.append("`fscore`,");
        preparedSql.append("`gscore`,");
        preparedSql.append("`mcc`,");
        preparedSql.append("`balance`,");
        preparedSql.append("`auc`,");
        preparedSql.append("`aucec`,");
        preparedSql.append("`nofb20`,");
        preparedSql.append("`relb20`,");
        preparedSql.append("`nofi80`,");
        preparedSql.append("`reli80`,");
        preparedSql.append("`rele80`,");
        preparedSql.append("`necm15`,");
        preparedSql.append("`necm20`,");
        preparedSql.append("`necm25`,");
        preparedSql.append("`nofbPredicted`,");
        preparedSql.append("`nofbMissed`,");
        preparedSql.append("`tpr`,");
        preparedSql.append("`tnr`,");
        preparedSql.append("`fpr`,");
        preparedSql.append("`fnr`,");
        preparedSql.append("`tp`,");
        preparedSql.append("`fn`,");
        preparedSql.append("`tn`,");
        preparedSql.append("`fp`,");
        preparedSql.append("`lowerConst1to1`,");
        preparedSql.append("`upperConst1to1`,");
        preparedSql.append("`lowerConst1toM`,");
        preparedSql.append("`upperConst1toM`,");
        preparedSql.append("`lowerConstNtoM`,");
        preparedSql.append("`upperConstNtoM`,");
        preparedSql.append("`lowerSize1to1`,");
        preparedSql.append("`upperSize1to1`,");
        preparedSql.append("`lowerSize1toM`,");
        preparedSql.append("`upperSize1toM`,");
        preparedSql.append("`lowerSizeNtoM`,");
        preparedSql.append("`upperSizeNtoM`,");
        preparedSql.append("`lowerConst1to1Imp10`,");
        preparedSql.append("`upperConst1to1Imp10`,");
        preparedSql.append("`lowerConst1toMImp10`,");
        preparedSql.append("`upperConst1toMImp10`,");
        preparedSql.append("`lowerConstNtoMImp10`,");
        preparedSql.append("`upperConstNtoMImp10`,");
        preparedSql.append("`lowerSize1to1Imp10`,");
        preparedSql.append("`upperSize1to1Imp10`,");
        preparedSql.append("`lowerSize1toMImp10`,");
        preparedSql.append("`upperSize1toMImp10`,");
        preparedSql.append("`lowerSizeNtoMImp10`,");
        preparedSql.append("`upperSizeNtoMImp10`,");
        preparedSql.append("`lowerConst1to1Imp20`,");
        preparedSql.append("`upperConst1to1Imp20`,");
        preparedSql.append("`lowerConst1toMImp20`,");
        preparedSql.append("`upperConst1toMImp20`,");
        preparedSql.append("`lowerConstNtoMImp20`,");
        preparedSql.append("`upperConstNtoMImp20`,");
        preparedSql.append("`lowerSize1to1Imp20`,");
        preparedSql.append("`upperSize1to1Imp20`,");
        preparedSql.append("`lowerSize1toMImp20`,");
        preparedSql.append("`upperSize1toMImp20`,");
        preparedSql.append("`lowerSizeNtoMImp20`,");
        preparedSql.append("`upperSizeNtoMImp20`,");
        preparedSql.append("`lowerConst1to1Imp30`,");
        preparedSql.append("`upperConst1to1Imp30`,");
        preparedSql.append("`lowerConst1toMImp30`,");
        preparedSql.append("`upperConst1toMImp30`,");
        preparedSql.append("`lowerConstNtoMImp30`,");
        preparedSql.append("`upperConstNtoMImp30`,");
        preparedSql.append("`lowerSize1to1Imp30`,");
        preparedSql.append("`upperSize1to1Imp30`,");
        preparedSql.append("`lowerSize1toMImp30`,");
        preparedSql.append("`upperSize1toMImp30`,");
        preparedSql.append("`lowerSizeNtoMImp30`,");
        preparedSql.append("`upperSizeNtoMImp30`,");
        preparedSql.append("`lowerConst1to1Imp40`,");
        preparedSql.append("`upperConst1to1Imp40`,");
        preparedSql.append("`lowerConst1toMImp40`,");
        preparedSql.append("`upperConst1toMImp40`,");
        preparedSql.append("`lowerConstNtoMImp40`,");
        preparedSql.append("`upperConstNtoMImp40`,");
        preparedSql.append("`lowerSize1to1Imp40`,");
        preparedSql.append("`upperSize1to1Imp40`,");
        preparedSql.append("`lowerSize1toMImp40`,");
        preparedSql.append("`upperSize1toMImp40`,");
        preparedSql.append("`lowerSizeNtoMImp40`,");
        preparedSql.append("`upperSizeNtoMImp40`,");
        preparedSql.append("`lowerConst1to1Imp50`,");
        preparedSql.append("`upperConst1to1Imp50`,");
        preparedSql.append("`lowerConst1toMImp50`,");
        preparedSql.append("`upperConst1toMImp50`,");
        preparedSql.append("`lowerConstNtoMImp50`,");
        preparedSql.append("`upperConstNtoMImp50`,");
        preparedSql.append("`lowerSize1to1Imp50`,");
        preparedSql.append("`upperSize1to1Imp50`,");
        preparedSql.append("`lowerSize1toMImp50`,");
        preparedSql.append("`upperSize1toMImp50`,");
        preparedSql.append("`lowerSizeNtoMImp50`,");
        preparedSql.append("`upperSizeNtoMImp50`) VALUES ");
        preparedSql.append("(");
        for(int i=0; i<103; i++) {
        	preparedSql.append("?,");
        }
        preparedSql.append("?)");

        try(PreparedStatement stmt = this.connectionPool.getConnection().prepareStatement(preparedSql.toString());) {
        	int i=1;
            stmt.setString(i++, result.getConfigurationName());
            stmt.setString(i++, result.getProductName());
            stmt.setString(i++, result.getClassifier());
            stmt.setInt(i++, result.getSizeTestData());
            stmt.setInt(i++, result.getSizeTrainingData());
            stmt.setDouble(i++, result.getError());
            stmt.setDouble(i++, result.getRecall());
            stmt.setDouble(i++, result.getPrecision());
            stmt.setDouble(i++, result.getFscore());
            stmt.setDouble(i++, result.getGscore());
            stmt.setDouble(i++, result.getMcc());
            stmt.setDouble(i++, result.getAuc());
            stmt.setDouble(i++, result.getBalance());
            stmt.setDouble(i++, result.getAucec());
            stmt.setDouble(i++, result.getNofb20());
            stmt.setDouble(i++, result.getRelb20());
            stmt.setDouble(i++, result.getNofi80());
            stmt.setDouble(i++, result.getReli80());
            stmt.setDouble(i++, result.getRele80());
            stmt.setDouble(i++, result.getNecm15());
            stmt.setDouble(i++, result.getNecm20());
            stmt.setDouble(i++, result.getNecm25());
            stmt.setDouble(i++, result.getNofbPredicted());
            stmt.setDouble(i++, result.getNofbMissed());
            stmt.setDouble(i++, result.getTpr());
            stmt.setDouble(i++, result.getTnr());
            stmt.setDouble(i++, result.getFpr());
            stmt.setDouble(i++, result.getFnr());
            stmt.setDouble(i++, result.getTp());
            stmt.setDouble(i++, result.getFn());
            stmt.setDouble(i++, result.getTn());
            stmt.setDouble(i++, result.getFp());
            stmt.setDouble(i++, result.getLowerConst1to1());
            stmt.setDouble(i++, result.getUpperConst1to1());
            stmt.setDouble(i++, result.getLowerConst1toM());
            stmt.setDouble(i++, result.getUpperConst1toM());
            stmt.setDouble(i++, result.getLowerConstNtoM());
            stmt.setDouble(i++, result.getUpperConstNtoM());
            stmt.setDouble(i++, result.getLowerSize1to1());
            stmt.setDouble(i++, result.getUpperSize1to1());
            stmt.setDouble(i++, result.getLowerSize1toM());
            stmt.setDouble(i++, result.getUpperSize1toM());
            stmt.setDouble(i++, result.getLowerSizeNtoM());
            stmt.setDouble(i++, result.getUpperSizeNtoM());
            stmt.setDouble(i++, result.getLowerConst1to1Imp10());
            stmt.setDouble(i++, result.getUpperConst1to1Imp10());
            stmt.setDouble(i++, result.getLowerConst1toMImp10());
            stmt.setDouble(i++, result.getUpperConst1toMImp10());
            stmt.setDouble(i++, result.getLowerConstNtoMImp10());
            stmt.setDouble(i++, result.getUpperConstNtoMImp10());
            stmt.setDouble(i++, result.getLowerSize1to1Imp10());
            stmt.setDouble(i++, result.getUpperSize1to1Imp10());
            stmt.setDouble(i++, result.getLowerSize1toMImp10());
            stmt.setDouble(i++, result.getUpperSize1toMImp10());
            stmt.setDouble(i++, result.getLowerSizeNtoMImp10());
            stmt.setDouble(i++, result.getUpperSizeNtoMImp10());
            stmt.setDouble(i++, result.getLowerConst1to1Imp20());
            stmt.setDouble(i++, result.getUpperConst1to1Imp20());
            stmt.setDouble(i++, result.getLowerConst1toMImp20());
            stmt.setDouble(i++, result.getUpperConst1toMImp20());
            stmt.setDouble(i++, result.getLowerConstNtoMImp20());
            stmt.setDouble(i++, result.getUpperConstNtoMImp20());
            stmt.setDouble(i++, result.getLowerSize1to1Imp20());
            stmt.setDouble(i++, result.getUpperSize1to1Imp20());
            stmt.setDouble(i++, result.getLowerSize1toMImp20());
            stmt.setDouble(i++, result.getUpperSize1toMImp20());
            stmt.setDouble(i++, result.getLowerSizeNtoMImp20());
            stmt.setDouble(i++, result.getUpperSizeNtoMImp20());
            stmt.setDouble(i++, result.getLowerConst1to1Imp30());
            stmt.setDouble(i++, result.getUpperConst1to1Imp30());
            stmt.setDouble(i++, result.getLowerConst1toMImp30());
            stmt.setDouble(i++, result.getUpperConst1toMImp30());
            stmt.setDouble(i++, result.getLowerConstNtoMImp30());
            stmt.setDouble(i++, result.getUpperConstNtoMImp30());
            stmt.setDouble(i++, result.getLowerSize1to1Imp30());
            stmt.setDouble(i++, result.getUpperSize1to1Imp30());
            stmt.setDouble(i++, result.getLowerSize1toMImp30());
            stmt.setDouble(i++, result.getUpperSize1toMImp30());
            stmt.setDouble(i++, result.getLowerSizeNtoMImp30());
            stmt.setDouble(i++, result.getUpperSizeNtoMImp30());
            stmt.setDouble(i++, result.getLowerConst1to1Imp40());
            stmt.setDouble(i++, result.getUpperConst1to1Imp40());
            stmt.setDouble(i++, result.getLowerConst1toMImp40());
            stmt.setDouble(i++, result.getUpperConst1toMImp40());
            stmt.setDouble(i++, result.getLowerConstNtoMImp40());
            stmt.setDouble(i++, result.getUpperConstNtoMImp40());
            stmt.setDouble(i++, result.getLowerSize1to1Imp40());
            stmt.setDouble(i++, result.getUpperSize1to1Imp40());
            stmt.setDouble(i++, result.getLowerSize1toMImp40());
            stmt.setDouble(i++, result.getUpperSize1toMImp40());
            stmt.setDouble(i++, result.getLowerSizeNtoMImp40());
            stmt.setDouble(i++, result.getUpperSizeNtoMImp40());
            stmt.setDouble(i++, result.getLowerConst1to1Imp50());
            stmt.setDouble(i++, result.getUpperConst1to1Imp50());
            stmt.setDouble(i++, result.getLowerConst1toMImp50());
            stmt.setDouble(i++, result.getUpperConst1toMImp50());
            stmt.setDouble(i++, result.getLowerConstNtoMImp50());
            stmt.setDouble(i++, result.getUpperConstNtoMImp50());
            stmt.setDouble(i++, result.getLowerSize1to1Imp50());
            stmt.setDouble(i++, result.getUpperSize1to1Imp50());
            stmt.setDouble(i++, result.getLowerSize1toMImp50());
            stmt.setDouble(i++, result.getUpperSize1toMImp50());
            stmt.setDouble(i++, result.getLowerSizeNtoMImp50());
            stmt.setDouble(i++, result.getUpperSizeNtoMImp50());
            

            int qryResult = stmt.executeUpdate();
            if (qryResult < 1) {
            	LOGGER.error("Insert failed.");
            }
        }
        catch (SQLException e) {
        	LOGGER.error("Problem with MySQL connection: ");
        	LOGGER.error("SQLException: " + e.getMessage());
        	LOGGER.error("SQLState: " + e.getSQLState());
        	LOGGER.error("VendorError: " + e.getErrorCode());
            return;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.eval.IResultStorage#containsResult(java.lang.String, java.lang.String)
     */
    @Override
    public int containsResult(String experimentName, String productName, String classifierName) {
        String preparedSql = "SELECT COUNT(*) as cnt FROM " + this.resultsTableName +
            " WHERE configurationName=? AND productName=? AND classifier=?";
        try(PreparedStatement stmt = this.connectionPool.getConnection().prepareStatement(preparedSql);) {
            stmt.setString(1, experimentName);
            stmt.setString(2, productName);
            stmt.setString(3, classifierName);
            try(ResultSet results = stmt.executeQuery();) {
                results.next();
                return results.getInt("cnt");
            }
        }
        catch (SQLException e) {
        	LOGGER.error("Problem with MySQL connection: \n");
        	LOGGER.error("SQLException: " + e.getMessage() + "\n");
        	LOGGER.error("SQLState: " + e.getSQLState() + "\n");
        	LOGGER.error("VendorError: " + e.getErrorCode() + "\n");
            return 0;
        }
    }

    /**
     * <p>
     * Checks if the results table exists.
     * </p>
     *
     * @return true if exists, false otherwise
     */
    public boolean doesResultsTableExist() {
        boolean exists = false;
        try {
            DatabaseMetaData meta = this.connectionPool.getConnection().getMetaData();
            try(ResultSet res = meta.getTables(null, null, this.resultsTableName, null);) {
                exists = res.next();
            }
        }
        catch (SQLException e) {
        	LOGGER.error("Problem with MySQL connection: \n");
        	LOGGER.error("SQLException: " + e.getMessage() + "\n");
        	LOGGER.error("SQLState: " + e.getSQLState() + "\n");
        	LOGGER.error("VendorError: " + e.getErrorCode() + "\n");
        }
        return exists;
    }

    /**
     * <p>
     * Tries to create the results table in the DB.
     * </p>
     */
    public void createResultsTable() {
        String sql = "CREATE TABLE `" + this.resultsTableName + "` (" +
            "`idresults` int(11) NOT NULL AUTO_INCREMENT," +
            "`configurationName` varchar(250) NOT NULL," +
            "`productName` varchar(100) NOT NULL," +
            "`classifier` varchar(50) NOT NULL," +
            "`testsize` int(11) DEFAULT NULL," +
            "`trainsize` int(11) DEFAULT NULL," +
            "`error` double DEFAULT NULL," +
            "`recall` double DEFAULT NULL," +
            "`precision` double DEFAULT NULL," +
            "`fscore` double DEFAULT NULL," +
            "`gscore` double DEFAULT NULL," +
            "`mcc` double DEFAULT NULL," +
            "`auc` double DEFAULT NULL," +
            "`balance` double DEFAULT NULL," + 
            "`aucec` double DEFAULT NULL," +
            "`nofb20` double DEFAULT NULL," +
            "`relb20` double DEFAULT NULL," +
            "`nofi80` double DEFAULT NULL," +  
            "`reli80` double DEFAULT NULL," +
            "`rele80` double DEFAULT NULL," +  
            "`necm15` double DEFAULT NULL," +
            "`necm20` double DEFAULT NULL," +  
            "`necm25` double DEFAULT NULL," +
            "`nofbPredicted` double DEFAULT NULL," + 
            "`nofbMissed` double DEFAULT NULL," +
            "`tpr` double DEFAULT NULL," +
            "`tnr` double DEFAULT NULL," +
            "`fpr` double DEFAULT NULL," +
            "`fnr` double DEFAULT NULL," +
            "`tp` double DEFAULT NULL," +
            "`fn` double DEFAULT NULL," +
            "`tn` double DEFAULT NULL," +
            "`fp` double DEFAULT NULL," + 
            "`lowerConst1to1` double DEFAULT NULL," +
            "`upperConst1to1` double DEFAULT NULL," +
            "`lowerConst1toM` double DEFAULT NULL," +
            "`upperConst1toM` double DEFAULT NULL," +
            "`lowerConstNtoM` double DEFAULT NULL," +
            "`upperConstNtoM` double DEFAULT NULL," +
            "`lowerSize1to1` double DEFAULT NULL," +
            "`upperSize1to1` double DEFAULT NULL," +
            "`lowerSize1toM` double DEFAULT NULL," +
            "`upperSize1toM` double DEFAULT NULL," +
            "`lowerSizeNtoM` double DEFAULT NULL," +
            "`upperSizeNtoM` double DEFAULT NULL," +
            "`lowerConst1to1Imp10` double DEFAULT NULL," +
            "`upperConst1to1Imp10` double DEFAULT NULL," +
            "`lowerConst1toMImp10` double DEFAULT NULL," +
            "`upperConst1toMImp10` double DEFAULT NULL," +
            "`lowerConstNtoMImp10` double DEFAULT NULL," +
            "`upperConstNtoMImp10` double DEFAULT NULL," +
            "`lowerSize1to1Imp10` double DEFAULT NULL," +
            "`upperSize1to1Imp10` double DEFAULT NULL," +
            "`lowerSize1toMImp10` double DEFAULT NULL," +
            "`upperSize1toMImp10` double DEFAULT NULL," +
            "`lowerSizeNtoMImp10` double DEFAULT NULL," +
            "`upperSizeNtoMImp10` double DEFAULT NULL," +
            "`lowerConst1to1Imp20` double DEFAULT NULL," +
            "`upperConst1to1Imp20` double DEFAULT NULL," +
            "`lowerConst1toMImp20` double DEFAULT NULL," +
            "`upperConst1toMImp20` double DEFAULT NULL," +
            "`lowerConstNtoMImp20` double DEFAULT NULL," +
            "`upperConstNtoMImp20` double DEFAULT NULL," +
            "`lowerSize1to1Imp20` double DEFAULT NULL," +
            "`upperSize1to1Imp20` double DEFAULT NULL," +
            "`lowerSize1toMImp20` double DEFAULT NULL," +
            "`upperSize1toMImp20` double DEFAULT NULL," +
            "`lowerSizeNtoMImp20` double DEFAULT NULL," +
            "`upperSizeNtoMImp20` double DEFAULT NULL," +
            "`lowerConst1to1Imp30` double DEFAULT NULL," +
            "`upperConst1to1Imp30` double DEFAULT NULL," +
            "`lowerConst1toMImp30` double DEFAULT NULL," +
            "`upperConst1toMImp30` double DEFAULT NULL," +
            "`lowerConstNtoMImp30` double DEFAULT NULL," +
            "`upperConstNtoMImp30` double DEFAULT NULL," +
            "`lowerSize1to1Imp30` double DEFAULT NULL," +
            "`upperSize1to1Imp30` double DEFAULT NULL," +
            "`lowerSize1toMImp30` double DEFAULT NULL," +
            "`upperSize1toMImp30` double DEFAULT NULL," +
            "`lowerSizeNtoMImp30` double DEFAULT NULL," +
            "`upperSizeNtoMImp30` double DEFAULT NULL," +
            "`lowerConst1to1Imp40` double DEFAULT NULL," +
            "`upperConst1to1Imp40` double DEFAULT NULL," +
            "`lowerConst1toMImp40` double DEFAULT NULL," +
            "`upperConst1toMImp40` double DEFAULT NULL," +
            "`lowerConstNtoMImp40` double DEFAULT NULL," +
            "`upperConstNtoMImp40` double DEFAULT NULL," +
            "`lowerSize1to1Imp40` double DEFAULT NULL," +
            "`upperSize1to1Imp40` double DEFAULT NULL," +
            "`lowerSize1toMImp40` double DEFAULT NULL," +
            "`upperSize1toMImp40` double DEFAULT NULL," +
            "`lowerSizeNtoMImp40` double DEFAULT NULL," +
            "`upperSizeNtoMImp40` double DEFAULT NULL," +
            "`lowerConst1to1Imp50` double DEFAULT NULL," +
            "`upperConst1to1Imp50` double DEFAULT NULL," +
            "`lowerConst1toMImp50` double DEFAULT NULL," +
            "`upperConst1toMImp50` double DEFAULT NULL," +
            "`lowerConstNtoMImp50` double DEFAULT NULL," +
            "`upperConstNtoMImp50` double DEFAULT NULL," +
            "`lowerSize1to1Imp50` double DEFAULT NULL," +
            "`upperSize1to1Imp50` double DEFAULT NULL," +
            "`lowerSize1toMImp50` double DEFAULT NULL," +
            "`upperSize1toMImp50` double DEFAULT NULL," +
            "`lowerSizeNtoMImp50` double DEFAULT NULL," +
            "`upperSizeNtoMImp50` double DEFAULT NULL," +
            "PRIMARY KEY (`idresults`)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=77777 DEFAULT CHARSET=utf8;";
        try(Statement stmt = this.connectionPool.getConnection().createStatement();) { 
            stmt.execute(sql);
            LOGGER.info("Created new table " + this.resultsTableName);
        }
        catch (SQLException e) {
        	LOGGER.error("Problem with MySQL connection: \n");
        	LOGGER.error("SQLException: " + e.getMessage() + "\n");
        	LOGGER.error("SQLState: " + e.getSQLState() + "\n");
        	LOGGER.error("VendorError: " + e.getErrorCode() + "\n");
        }
    }

    @Override
    public int containsHeterogeneousResult(String experimentName,
                                           String productName,
                                           String classifierName,
                                           String trainProductName)
    {
        // TODO dummy implementation
        return 0;
    }

}
