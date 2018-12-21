package com.sample.dbsample.operations;

import com.sample.dbsample.Application;
import com.sample.dbsample.dbutils.DbUtilities;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class Create {

    private static final Logger LOGGER = Logger.getLogger(Application.class);
    private static final String TAB_SPACES = "\t\t\t\t\t\t";

    public Create(DbUtilities dbUtilities) {
        Scanner userInput = new Scanner(System.in);
        LOGGER.info(TAB_SPACES + "Selected option is : New employee Creation : ");

        String name;
        LOGGER.info(TAB_SPACES + "Enter employee name : ");
        name = userInput.nextLine();

        String email;
        LOGGER.info(TAB_SPACES + "Enter employee email : ");
        email = userInput.nextLine();

        String contactNumber;
        LOGGER.info(TAB_SPACES + "Enter employee contact number : ");
        contactNumber = userInput.nextLine();

        LOGGER.info("\n");


        if (StringUtils.isBlank(name) || StringUtils.isBlank(email) || StringUtils.isBlank(contactNumber)) {
            LOGGER.info(TAB_SPACES + "Fields must not be empty or null.");
            LOGGER.info("\n");
        }

        String query = "INSERT INTO employees (name,email,mobile_No) VALUES ('" + name + "','" + email + "','" + contactNumber + "')";
        dbUtilities.executeSQLStatement(query);
    }
}