package com.sample.dbsample;

import com.sample.dbsample.config.OracleConnector;
import com.sample.dbsample.config.EmployeeConnector;
import com.sample.dbsample.config.MySQLConnector;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Scanner;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class);
    private static final String TAB_SPACES = "\t\t\t\t\t\t";

    public static void main(String[] args) throws SQLException {
        BasicConfigurator.configure();
        new Application().displayMenu();
    }

    private void displayMenu() throws SQLException {
        Scanner userInput = new Scanner(System.in);
        String readMenu;

        String username;
        LOGGER.info(TAB_SPACES + "Enter Username for Database : ");
        username = userInput.nextLine();

        String password;
        LOGGER.info(TAB_SPACES + "Enter Password : ");
        password = userInput.nextLine();

        do {
            // Display menu graphics
            LOGGER.info(TAB_SPACES + "-------------------------------------------------");
            LOGGER.info(TAB_SPACES + "|              MySQL Management System          |");
            LOGGER.info(TAB_SPACES + "|-----------------------------------------------|");
            LOGGER.info(TAB_SPACES + "| Options:                                      |");
            LOGGER.info(TAB_SPACES + "|        1. Connect With System MySql.          |");
            LOGGER.info(TAB_SPACES + "|        2. Connect With Organization Database. |");
            LOGGER.info(TAB_SPACES + "|        3. Connect With Oracle DB.             |");
            LOGGER.info(TAB_SPACES + "|        4. Exit.                               |");
            LOGGER.info(TAB_SPACES + "-------------------------------------------------");

            LOGGER.info("\n");

            LOGGER.info(TAB_SPACES + "Select option : ");
            readMenu = userInput.nextLine();

            LOGGER.info("\n");

            switch (readMenu) {
                case "1":
                    new MySQLConnector(username, password).process();
                    break;
                case "2":
                    new EmployeeConnector(username, password).process();
                    break;
                case "3" :
                    new OracleConnector(username, password).process();
                    break;
                case "4":
                    LOGGER.info("System exiting");
                    System.exit(0);
                    break;
                default:
                    LOGGER.info(TAB_SPACES + "Invalid selection");
                    break;
            }
        } while (!readMenu.equals("4"));
    }
}