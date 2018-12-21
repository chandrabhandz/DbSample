package com.sample.dbsample.operations;

import com.sample.dbsample.Application;
import dnl.utils.text.table.TextTable;
import com.sample.dbsample.dbutils.DbUtilities;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Read {

    private static final Logger LOGGER = Logger.getLogger(Application.class);
    private static final String TAB_SPACES = "\t\t\t\t\t\t";

    public Read(String username, String password) throws SQLException {
        LOGGER.info(TAB_SPACES + "Selected option is : All Employees Details : ");
        DisplayResults(username, password);
    }

    private void DisplayResults(String username, String password) throws SQLException {
        try {
            DbUtilities dbUtilities = new DbUtilities(username, password, "Organization");

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
                LOGGER.info(TAB_SPACES + "No database records found");
            }

            //close db connection
            dbUtilities.disconnectFromDB();
        } catch (SQLException ex) {
            LOGGER.trace(TAB_SPACES + "The following error has occurred: " + ex.getMessage());
        }
    }
}
