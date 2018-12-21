package com.sample.dbsample.dbutils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


public class DbUtilities {

    private static final Logger LOG = LogManager.getLogger(DbUtilities.class);

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String TAB_SPACES = "\t\t\t\t\t\t";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;


    public DbUtilities(String username, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, username, password);
        } catch (SQLException ex) {
            System.out.println(TAB_SPACES + "The following error has occurred: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            LOG.trace(TAB_SPACES + "The following error has occurred: " + e.getMessage());
        }
    }

    public DbUtilities(String username, String password, String dbName) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL + dbName, username, password);
        } catch (SQLException ex) {
            System.out.println(TAB_SPACES + "The following error has occurred: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            LOG.trace(TAB_SPACES + "The following error has occurred: " + e.getMessage());
        }
    }

    public void disconnectFromDB() {

        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            LOG.trace(TAB_SPACES + "The following error has occurred: " + ex.getMessage());
        }
    }

    public ResultSet readRecords(String query) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            return resultSet;
        } catch (SQLException ex) {
            LOG.trace(TAB_SPACES + "The following error has occurred: " + ex.getMessage());
        }

        return resultSet;
    }

    public void executeSQLStatement(String query) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);

            do {
                if (Objects.isNull(statement.getResultSet())) {
                    break;
                } else {
                    resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        int numColumns = resultSet.getMetaData().getColumnCount();
                        for (int i = 1; i <= numColumns; i++) {
                            LOG.info(TAB_SPACES + "" + resultSet.getObject(i));
                        }
                    }
                }
            } while (resultSet.next());
        } catch (SQLException ex) {
            LOG.trace(TAB_SPACES + "The following error has occurred: " + ex.getStackTrace());
        }
    }
}