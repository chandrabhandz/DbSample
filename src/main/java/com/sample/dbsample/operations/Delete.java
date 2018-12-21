package com.sample.dbsample.operations;

import com.sample.dbsample.Application;
import com.sample.dbsample.dbutils.DbUtilities;
import com.sample.dbsample.dbutils.MysqlDbUtilities;
import dnl.utils.text.table.TextTable;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import static com.sample.dbsample.Constants.TAB_SPACES;

public class Delete {

    private static final Logger LOGGER = Logger.getLogger(Application.class);

    public Delete(DbUtilities dbUtilities) {
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
        this.displayRecord(dbUtilities, email);

        String confirmDelete;
        LOGGER.info(TAB_SPACES + "Enter Y to confirm deletion : ");
        confirmDelete = userInput.nextLine();

        if ("Y".equalsIgnoreCase(confirmDelete)) {

            String query = "DELETE FROM employees WHERE email = '" + email + "'";
            dbUtilities.executeSQLStatement(query);

            LOGGER.info(TAB_SPACES + "The Record has successfully being deleted.");
        }
    }

    private void displayRecord(DbUtilities dbUtilities, String email) {
        try {
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
            }

        } catch (SQLException ex) {
            LOGGER.trace(TAB_SPACES + "The following error has occurred: " + ex.getMessage());
        }
    }
}
