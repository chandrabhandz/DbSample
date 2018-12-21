package com.sample.dbsample.dbutils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.DriverManager;

public class OracleDbUtilities extends DbUtilities {

    private static final Logger LOG = LogManager.getLogger(OracleDbUtilities.class);

    private static final String DATABASE_URL = "jdbc:oracle:thin:@localhost:49161:xe";

    public OracleDbUtilities(String username, String password) {
        init(username, password, StringUtils.EMPTY);
    }

    @Override
    public void init(String username, String password, String dbname) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(DATABASE_URL, username, password);
        } catch (Exception e) {
            LOG.error(TAB_SPACES + "The following error has occurred: " + e.getMessage());
        }
    }
}
