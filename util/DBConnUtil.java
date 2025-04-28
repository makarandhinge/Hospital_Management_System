package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    public static Connection getConnection(String propertyFileName) {
        String connectionString = DBPropertyUtil.getPropertyString(propertyFileName);
        try {
            return DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
