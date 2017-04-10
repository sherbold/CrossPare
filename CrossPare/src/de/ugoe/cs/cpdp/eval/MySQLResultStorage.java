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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import de.lmu.ifi.dbs.elki.logging.Logging.Level;
import de.ugoe.cs.util.console.Console;

/**
 * <p>
 * Implements a storage of experiment results in a MySQL database.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class MySQLResultStorage implements IResultStorage {
    
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
     * Creates a new results storage. Tries to read a properties file mysql.cred located in the
     * working directory. If this file is not found or any parameter is not defined, the following default values are used:
     * <ul>
     * <li>db.host = localhost</li>
     * <li>db.port = 3306</li>
     * <li>db.name = crosspare</li>
     * <li>db.user = crosspare</li>
     * <li>db.pass = benchmark</li>
     * <li>db.results.tablename = results</li>
     * <li>db.results.createtable = false</li>
     * </p>
     */
    public MySQLResultStorage() {
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(new FileInputStream("mysql.cred"));
        }
        catch (IOException e) {
            Console.printerr("Could not load mysql.cred file: " + e.getMessage());
            Console.printerr("Must be a properties file located in working directory.");
            Console
                .traceln(Level.WARNING,
                         "Using default DB configuration since mysql.cred file could not be loaded");
        }
        String dbHost = dbProperties.getProperty("db.host", "localhost");
        String dbPort = dbProperties.getProperty("db.port", "3306");
        String dbName = dbProperties.getProperty("db.name", "crosspare");
        String dbUser = dbProperties.getProperty("db.user", "root");
        String dbPass = dbProperties.getProperty("db.pass", "balla");
        resultsTableName = dbProperties.getProperty("db.results.tablename", "results");
        boolean createTableIfNotExists = Boolean.parseBoolean(dbProperties.getProperty("db.results.createtable", "false"));
        connectToDB(dbHost, dbPort, dbName, dbUser, dbPass);
        
        // create the results table if required
        if( checkIfTableExists() && createTableIfNotExists ) {
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
        connectionPool = new MysqlDataSource();
        connectionPool.setUser(dbUser);
        connectionPool.setPassword(dbPass);
        connectionPool.setUrl("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.eval.IResultStorage#addResult(de.ugoe.cs.cpdp.eval.ExperimentResult)
     */
    @Override
    public void addResult(ExperimentResult result) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + resultsTableName + " VALUES (NULL,");
        sql.append("\'" + result.getConfigurationName() + "\',");
        sql.append("\'" + result.getProductName() + "\',");
        sql.append("\'" + result.getClassifier() + "\',");
        sql.append(result.getSizeTestData() + ",");
        sql.append(result.getSizeTrainingData() + ",");
        sql.append(result.getError() + ",");
        sql.append(result.getRecall() + ",");
        sql.append(result.getPrecision() + ",");
        sql.append(result.getFscore() + ",");
        sql.append(result.getGscore() + ",");
        sql.append(result.getMcc() + ",");
        sql.append(result.getAuc() + ",");
        sql.append(result.getAucec() + ",");
        sql.append(result.getTpr() + ",");
        sql.append(result.getTnr() + ",");
        sql.append(result.getFpr() + ",");
        sql.append(result.getFnr() + ",");
        sql.append(result.getTp() + ",");
        sql.append(result.getFn() + ",");
        sql.append(result.getTn() + ",");
        sql.append(result.getFp() + ");");
        
        Statement stmt;
        try {
            stmt = connectionPool.getConnection().createStatement();
            stmt.executeUpdate(sql.toString().replace("NaN", "NULL"));
        }
        catch (SQLException e) {
            Console.printerr("Problem with MySQL connection: ");
            Console.printerr("SQLException: " + e.getMessage());
            Console.printerr("SQLState: " + e.getSQLState());
            Console.printerr("VendorError: " + e.getErrorCode());
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
        String sql = "SELECT COUNT(*) as cnt FROM " + resultsTableName + " WHERE configurationName=\'" +
            experimentName + "\' AND productName=\'" + productName + "\' AND classifier=\'" +
            classifierName + "\';";
        Statement stmt;
        try {
            stmt = connectionPool.getConnection().createStatement();
            ResultSet results = stmt.executeQuery(sql);
            results.next();
            return results.getInt("cnt");
        }
        catch (SQLException e) {
            Console.printerr("Problem with MySQL connection: \n");
            Console.printerr("SQLException: " + e.getMessage() + "\n");
            Console.printerr("SQLState: " + e.getSQLState() + "\n");
            Console.printerr("VendorError: " + e.getErrorCode() + "\n");
            return 0;
        }
    }
    
    /**
     * <p>
     * Checks if the results table exists. 
     * </p>
     *
     * @return
     */
    public boolean checkIfTableExists() {
        // TODO implement method
        return true;
    }
    
    /**
     * <p>
     * Tries to create the results table in the DB. 
     * </p>
     */
    public void createResultsTable() {
        // TODO immplement method
    }
    
    public int containsHeterogeneousResult(String experimentName, String productName, String classifierName, String trainProductName) {
    	return 0;
    }
 
}
