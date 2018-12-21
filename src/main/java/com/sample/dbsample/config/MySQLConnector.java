package com.sample.dbsample.config;

import com.sample.dbsample.dbutils.DbUtilities;
import com.sample.dbsample.dbutils.MysqlDbUtilities;
import org.apache.commons.lang.StringUtils;

public class MySQLConnector extends Connector {


    public MySQLConnector(String username, String password) {
        super(username, password);
    }

    @Override
    public DbUtilities getDbUtility() {
        return new MysqlDbUtilities(username, password, StringUtils.EMPTY);
    }

}

