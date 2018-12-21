package com.sample.dbsample.operations;

import com.sample.dbsample.Application;
import com.sample.dbsample.Constants;
import com.sample.dbsample.dbutils.DbUtilities;
import dnl.utils.text.table.TextTable;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Read {

    private static final Logger LOGGER = Logger.getLogger(Application.class);

    public Read(DbUtilities dbUtilities) {
        LOGGER.info(Constants.TAB_SPACES + "Selected option is : All Employees Details : ");
        this.displayResults(dbUtilities);
    }

    private void displayResults(DbUtilities dbUtilities) {
        try {

            String query = "SELECT name, email, mobile_No FROM employees";
            ResultSet resultSet = dbUtilities.readRecords(query);

            // process query results
            if (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int numberOfColumns = metaData.getColumnCount();
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
                int value = 0;
                do {
                    String[] values = new String[numberOfColumns];
                    for (int i = 1; i <= numberOfColumns; i++) {
                        values[i - 1] = resultSet.getObject(i).toString();
                    }
                    list[value++] = values;
                } while (resultSet.next());
                TextTable textTable = new TextTable(columns, list);

                textTable.setAddRowNumbering(true);
                textTable.printTable();
                LOGGER.info("\n");
            } else {
                LOGGER.info(Constants.TAB_SPACES + "No database records found");
            }

        } catch (SQLException ex) {
            LOGGER.trace(Constants.TAB_SPACES + "The following error has occurred: " + ex.getMessage());
        }
    }
}
