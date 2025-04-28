package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getPropertyString(String propertyFileName) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(propertyFileName)) {
            props.load(fis);
            String host = props.getProperty("host");
            String dbname = props.getProperty("dbname");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            String port = props.getProperty("port");
            return "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?user=" + user + "&password=" + password;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
