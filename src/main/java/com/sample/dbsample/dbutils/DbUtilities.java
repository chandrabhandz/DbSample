package com.sample.dbsample.dbutils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DbUtilities {
    protected static final String TAB_SPACES = "\t\t\t\t\t\t:";
    private static final Logger LOG = LogManager.getLogger(MysqlDbUtilities.class);

    abstract void init(String username, String password, String dbname);

    abstract Connection getConnection();


    public ResultSet readRecords(String query) {
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException ex) {
            LOG.error(TAB_SPACES + ex.getMessage());
        }
        return null;
    }

    public void executeSQLStatement(String query) {
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            ResultSet resultSet = statement.getResultSet();
            do {
                if (!resultSet.first()) {
                    break;
                } else {
                    if (resultSet.next()) {
                        int numColumns = resultSet.getMetaData().getColumnCount();
                        for (int i = 1; i <= numColumns; i++) {
                            LOG.info(TAB_SPACES + "" + resultSet.getObject(i));
                        }
                    }
                }
            } while (resultSet.next());
        } catch (SQLException ex) {
            LOG.error(TAB_SPACES + ex.getMessage());
        }
    }
}
