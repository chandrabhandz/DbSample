package com.sample.dbsample.config;

import com.sample.dbsample.dbutils.DbUtilities;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Scanner;

public class ConnectSystemMySQL {

    private static final Logger LOGGER = Logger.getLogger(ConnectSystemMySQL.class);
    private static final String TAB_SPACES = "\t\t\t\t\t\t";

    public ConnectSystemMySQL(String username, String password) throws SQLException {
        Scanner userInput = new Scanner(System.in);

        String query;
        LOGGER.info(TAB_SPACES + "Enter query to execute : ");
        query = userInput.nextLine();

        if (StringUtils.isNotBlank(query)) {
            DbUtilities dbUtilities = new DbUtilities(username, password);
            dbUtilities.executeSQLStatement(query);
        }
    }
}

