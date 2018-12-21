package com.sample.dbsample.config;

import com.sample.dbsample.dbutils.DbUtilities;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Scanner;

import static com.sample.dbsample.Constants.TAB_SPACES;

public abstract class Connector {

    private static final Logger LOGGER = LogManager.getLogger(Connector.class);

    protected String username;
    protected String password;

    public Connector(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public abstract DbUtilities getDbUtility();

    public void process() {
        Scanner userInput = new Scanner(System.in);

        String query;
        LOGGER.info(TAB_SPACES + "Enter query to execute : ");
        query = userInput.nextLine();

        if (StringUtils.isNotBlank(query)) {
            DbUtilities dbUtilities = this.getDbUtility();
            dbUtilities.executeSQLStatement(query);
        }
    }
}
