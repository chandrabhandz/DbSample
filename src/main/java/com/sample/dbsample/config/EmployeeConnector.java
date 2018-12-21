package com.sample.dbsample.config;

import com.sample.dbsample.Constants;
import com.sample.dbsample.dbutils.DbUtilities;
import com.sample.dbsample.dbutils.MysqlDbUtilities;
import com.sample.dbsample.operations.Create;
import com.sample.dbsample.operations.Delete;
import com.sample.dbsample.operations.Read;
import com.sample.dbsample.operations.Update;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Scanner;

import static com.sample.dbsample.Constants.TAB_SPACES;

public class EmployeeConnector extends Connector {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeConnector.class);

    public EmployeeConnector(String username, String password) {
        super(username, password);
    }

    @Override
    public DbUtilities getDbUtility() {
        return new MysqlDbUtilities(username, password, StringUtils.EMPTY);
    }

    @Override
    public void process() {
        Scanner userInput = new Scanner(System.in);
        String readMenu;

        do {
            LOGGER.info(TAB_SPACES + "*****************************************");
            LOGGER.info(TAB_SPACES + "|       Employee Management System      |");
            LOGGER.info(TAB_SPACES + "*****************************************");
            LOGGER.info(TAB_SPACES + "| Options:                              |");
            LOGGER.info(TAB_SPACES + "|        1. Create New Employee.        |");
            LOGGER.info(TAB_SPACES + "|        2. All Employees Details.      |");
            LOGGER.info(TAB_SPACES + "|        3. Update Employee Details     |");
            LOGGER.info(TAB_SPACES + "|        4. Delete Employee Detail.     |");
            LOGGER.info(TAB_SPACES + "|        5. Exit.                       |");
            LOGGER.info(TAB_SPACES + "*****************************************");

            LOGGER.info("\n");
            LOGGER.info(TAB_SPACES + "Select option: ");

            readMenu = userInput.nextLine();
            LOGGER.info("\n");

            switch (readMenu) {
                case "1":
                    new Create(new MysqlDbUtilities(username, password, Constants.DB_NAME));
                    break;
                case "2":
                    new Read(new MysqlDbUtilities(username, password, Constants.DB_NAME));
                    break;
                case "3":
                    new Update(new MysqlDbUtilities(username, password, Constants.DB_NAME));
                    break;
                case "4":
                    new Delete(new MysqlDbUtilities(username, password, Constants.DB_NAME));
                    break;
                case "5":
                    LOGGER.info("System exiting");
                    System.exit(0);
                    break;
                default:
                    LOGGER.info(TAB_SPACES + "Invalid selection");
                    break;
            }
        } while (!readMenu.equals("5"));
    }
}
