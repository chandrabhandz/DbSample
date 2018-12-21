package com.sample.dbsample.operations;

import com.sample.dbsample.Application;
import com.sample.dbsample.dbutils.DbUtilities;
import dnl.utils.text.table.TextTable;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class Delete {

    private static final Logger LOGGER = Logger.getLogger(Application.class);
    private static final String TAB_SPACES = "\t\t\t\t\t\t";

    public Delete(String username, String password) throws SQLException {
        Scanner userInput = new Scanner(System.in);
        LOGGER.info(TAB_SPACES + "Selected option is : Delete employee's Details ");

        String email;
        LOGGER.info(TAB_SPACES + "Enter employee email to delete : ");
        email = userInput.nextLine();

        if (StringUtils.isBlank(email)) {
            LOGGER.info(TAB_SPACES + "Email must not be empty or null.");
            LOGGER.info("\n");
        }

        //retrieve record to delete
        DisplayRecord(username, password, email);

        String confirmDelete;
        LOGGER.info(TAB_SPACES + "Enter Y to confirm deletion : ");
        confirmDelete = userInput.nextLine();

        if ("Y".equalsIgnoreCase(confirmDelete)) {

            DbUtilities dbUtilities = new DbUtilities(username, password, "Organization");
            String query = "DELETE FROM employees WHERE email = '" + email + "'";
            dbUtilities.executeSQLStatement(query);

            LOGGER.info(TAB_SPACES + "The Record has successfully being deleted.");
        }
    }

    private void DisplayRecord(String username, String password, String email) throws SQLException {
        try {
            DbUtilities dbUtilities = new DbUtilities(username, password, "Organization");

            String query = "SELECT name, email, mobile_No FROM employees WHERE email = '" + email + "'";
            ResultSet resultSet = dbUtilities.readRecords(query);

            // process query results
            if (resultSet.next()) {

                ResultSetMetaData metaData = resultSet.getMetaData();
                int numberOfColumns = metaData.getColumnCount();
                LOGGER.info(TAB_SPACES + "Database Records...");
                LOGGER.info("\n");
                String countQuery = "SELECT COUNT(*) FROM employees";
                ResultSet rs = dbUtilities.readRecords(countQuery);
                rs.next();
                int rowCount = rs.getInt(1);

                String columns[] = new String[numberOfColumns];
                Object list[][] = new Object[rowCount][];

                LOGGER.info("");
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
                //Application.DisplayMenu();
            }

            //close db connection
            dbUtilities.disconnectFromDB();
        } catch (SQLException ex) {
            LOGGER.trace(TAB_SPACES + "The following error has occurred: " + ex.getMessage());
        }
    }
}
