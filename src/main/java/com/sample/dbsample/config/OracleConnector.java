package com.sample.dbsample.config;

import com.sample.dbsample.dbutils.DbUtilities;
import com.sample.dbsample.dbutils.OracleDbUtilities;

public class OracleConnector extends Connector {

    public OracleConnector(String username, String password) {
        super(username, password);
    }

    @Override
    public DbUtilities getDbUtility() {
        return new OracleDbUtilities(username, password);
    }

}
