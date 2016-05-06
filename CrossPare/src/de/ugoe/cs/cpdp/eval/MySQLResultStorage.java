// Copyright 2015 Georg-August-Universit�t G�ttingen, Germany
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
     * Connection to the database
     */
    private Connection con = null;

    /**
     * <p>
     * Creates a new results storage. Tries to read a properties file mysql.cred located in the
     * working directory. If this file is not found, the default database configuration is used:
     * <ul>
     * <li>dbHost = localhost</li>
     * <li>dbPort = 3306</li>
     * <li>dbName = crosspare</li>
     * <li>dbUser = crosspare</li>
     * <li>dbPass = benchmark</li>
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
        String dbUser = dbProperties.getProperty("db.user", "crosspare");
        String dbPass = dbProperties.getProperty("db.pass", "benchmark");
        connectToDB(dbHost, dbPort, dbName, dbUser, dbPass);
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
        try {
            Properties connectionProperties = new Properties();
            connectionProperties.put("user", dbUser);
            connectionProperties.put("password", dbPass);
            connectionProperties.put("autoReconnect", "true");
            connectionProperties.put("maxReconnects", "10000");
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" +
                dbName, connectionProperties);
        }
        catch (ClassNotFoundException e) {
            Console.printerr("JDBC driver not found");
        }
        catch (SQLException e) {
            Console.printerr("Problem with MySQL connection: ");
            Console.printerr("SQLException: " + e.getMessage());
            Console.printerr("SQLState: " + e.getSQLState());
            Console.printerr("VendorError: " + e.getErrorCode());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.eval.IResultStorage#addResult(de.ugoe.cs.cpdp.eval.ExperimentResult)
     */
    @Override
    public void addResult(ExperimentResult result) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO crosspare.results VALUES (NULL,");
        sql.append("\'" + result.getConfigurationName() + "\',");
        sql.append("\'" + result.getProductName() + "\',");
        sql.append("\'" + result.getClassifier() + "\',");
        sql.append(result.getSizeTestData() + ",");
        sql.append(result.getSizeTrainingData() + ",");
        sql.append(result.getSuccHe() + ",");
        sql.append(result.getSuccZi() + ",");
        sql.append(result.getSuccG75() + ",");
        sql.append(result.getSuccG60() + ",");
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
            stmt = con.createStatement();
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
    public boolean containsResult(String experimentName, String productName) {
        String sql = "SELECT COUNT(*) as cnt FROM crosspare.results WHERE configurationName=\'" +
            experimentName + "\' AND productName=\'" + productName + "\';";
        Statement stmt;
        boolean contained = false;
        try {
            stmt = con.createStatement();
            ResultSet results = stmt.executeQuery(sql);
            results.next();
            int count = results.getInt("cnt");
            contained = count > 0;
        }
        catch (SQLException e) {
            Console.printerr("Problem with MySQL connection: \n");
            Console.printerr("SQLException: " + e.getMessage() + "\n");
            Console.printerr("SQLState: " + e.getSQLState() + "\n");
            Console.printerr("VendorError: " + e.getErrorCode() + "\n");
        }
        return contained;
    }
}
