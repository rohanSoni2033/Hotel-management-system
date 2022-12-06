package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.Booking;

public class Utils {
    public static String wrapQuotesString(String str) {
        return "\"" + str + "\"";
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("failed to close the statement " + exception);
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                System.out.println("failed to close the result set " + exception);
            }
        }
    }
}
