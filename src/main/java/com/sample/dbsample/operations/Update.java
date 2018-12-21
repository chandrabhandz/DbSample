package com.sample.dbsample.operations;


import com.sample.dbsample.Application;
import com.sample.dbsample.dbutils.DbUtilities;
import com.sample.dbsample.model.Employee;
import dnl.utils.text.table.TextTable;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import static com.sample.dbsample.Constants.TAB_SPACES;

public class Update {

    private static final Logger LOGGER = Logger.getLogger(Application.class);
    private static final String UPDATE_QUERY = "UPDATE employees SET name = '";

    public Update(DbUtilities dbUtilities) {
        Scanner userInput = new Scanner(System.in);
        LOGGER.info(TAB_SPACES + "Selected option is : Update employee's Details ");

        String email;
        LOGGER.info(TAB_SPACES + "Enter email to update : ");
        email = userInput.nextLine();

        if (StringUtils.isBlank(email)) {
            LOGGER.info(TAB_SPACES + "Email must not be empty or null.");
            LOGGER.info("\n");
        }

        //retrieve record to update
        Employee employee = displayRecord(email, dbUtilities);

        String name;
        LOGGER.info(TAB_SPACES + "Enter employee name : ");
        name = userInput.nextLine();
        LOGGER.info("\n");

        String mobile;
        LOGGER.info(TAB_SPACES + "Enter employee contact number : ");
        mobile = userInput.nextLine();
        LOGGER.info("\n");

        if (StringUtils.isBlank(name) && StringUtils.isBlank(mobile)) {
            LOGGER.info(TAB_SPACES + "Fields must not be empty or null.");
            LOGGER.info("\n");
        }

        String query;
        if (StringUtils.isBlank(name)) {
            query = UPDATE_QUERY + employee.getName() + "',mobile_No = '" + mobile + "' WHERE email = '" + email + "'";
        } else if (StringUtils.isBlank(mobile)) {
            query = UPDATE_QUERY + name + "',mobile_No = '" + employee.getMobile() + "' WHERE email = '" + email + "'";
        } else {
            query = UPDATE_QUERY + name + "',mobile_No = '" + mobile + "' WHERE email = '" + email + "'";
        }

        dbUtilities.executeSQLStatement(query);

        LOGGER.info(TAB_SPACES + "The Record has successfully being updated.");

    }

    private Employee displayRecord(String email, DbUtilities dbUtilities) {
        Employee employee = new Employee();
        try {
            String query = "SELECT name, mobile_No FROM employees WHERE email = '" + email + "'";
            ResultSet resultSet = dbUtilities.readRecords(query);

            // process query results
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String mobile = resultSet.getString("mobile_No");

                employee.setName(name);
                employee.setMobile(mobile);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int numberOfColumns = metaData.getColumnCount();

                LOGGER.info("\n");
                LOGGER.info(TAB_SPACES + "Database Records...");

                String countQuery = "SELECT COUNT(*) FROM employees";
                ResultSet rs = dbUtilities.readRecords(countQuery);
                rs.next();
                int rowCount = rs.getInt(1);

                String columns[] = new String[numberOfColumns];
                Object list[][] = new Object[rowCount][];

                LOGGER.info("\n");
                for (int i = 1; i <= numberOfColumns; i++) {
                    columns[i - 1] = metaData.getColumnName(i);
                }
                int a = 0;
                do {
                    String[] values = new String[numberOfColumns];
                    for (int i = 1; i <= numberOfColumns; i++) {
                        values[i - 1] = resultSet.getObject(i).toString();
                    }
                    list[a++] = values;
                } while (resultSet.next());
                TextTable textTable = new TextTable(columns, list);
                textTable.printTable();
                LOGGER.info("\n");

            } else {
                LOGGER.info(TAB_SPACES + "No database records found.");
            }
        } catch (SQLException ex) {
            LOGGER.trace(TAB_SPACES + "The following error has occurred: " + ex.getMessage());
        }
        return employee;
    }
}
