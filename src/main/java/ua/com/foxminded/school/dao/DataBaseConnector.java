package ua.com.foxminded.school.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import ua.com.foxminded.school.exceptions.DaoExceptions;

public class DataBaseConnector {

    private static final String URL = "url";
    private static final String USER_NAME = "name";
    private static final String PASSWORD = "password";

    public Connection connection(Properties configurationDB) {
        try {
            return DriverManager.getConnection(configurationDB.getProperty(URL),
                    configurationDB.getProperty(USER_NAME), configurationDB.getProperty(PASSWORD));
        } catch (SQLException e) {
            throw new DaoExceptions("Impossible get connection ", e);
        }
    }

}
