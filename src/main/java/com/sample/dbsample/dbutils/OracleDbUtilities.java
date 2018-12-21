package com.sample.dbsample.dbutils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDbUtilities extends DbUtilities {

    private static final Logger LOG = LogManager.getLogger(OracleDbUtilities.class);

    private static final String DATABASE_URL = "jdbc:oracle:thin:@192.168.0.168:49161:xe";
    private String username;
    private String password;

    public OracleDbUtilities(String username, String password) {
        init(username, password, StringUtils.EMPTY);
    }

    @Override
    public void init(String username, String password, String dbname) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.username = username;
            this.password = password;
        } catch (ClassNotFoundException e) {
            LOG.error(TAB_SPACES + "The following error has occurred: " + e.getMessage());
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, username, password);
        } catch (SQLException e) {
            LOG.error(TAB_SPACES + "The following error has occurred: " + e.getMessage());
        }
        return null;
    }
}
