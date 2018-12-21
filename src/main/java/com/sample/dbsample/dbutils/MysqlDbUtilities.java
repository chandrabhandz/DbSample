package com.sample.dbsample.dbutils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;


public class MysqlDbUtilities extends DbUtilities {

    private static final Logger LOG = LogManager.getLogger(MysqlDbUtilities.class);

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/";

    public MysqlDbUtilities(String username, String password, String dbName) {
        init(username, password, dbName);
    }

    @Override
    public void init(String username, String password, String dbname) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            LOG.trace(TAB_SPACES + "The following error has occurred: " + e.getMessage());
        }
    }

}