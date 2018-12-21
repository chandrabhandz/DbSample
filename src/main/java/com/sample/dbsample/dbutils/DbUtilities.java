package com.sample.dbsample.dbutils;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public abstract class DbUtilities {
    private static final Logger LOG = LogManager.getLogger(MysqlDbUtilities.class);
    protected static final String TAB_SPACES = "\t\t\t\t\t\t:";
    protected Connection connection = null;
    protected Statement statement = null;
    protected ResultSet resultSet = null;

    abstract void init(String username, String password, String dbname);

    public void disconnectFromDB() {

        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            LOG.trace(TAB_SPACES + ex.getMessage());
        }
    }

    public ResultSet readRecords(String query) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            return resultSet;
        } catch (SQLException ex) {
            LOG.trace(TAB_SPACES + ex.getMessage());
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
            LOG.trace(TAB_SPACES + ex.getMessage());
        }
    }
}
