package ua.com.foxminded.school.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.school.dto.PropertiesDataBase;
import ua.com.foxminded.school.exceptions.DaoExceptions;

public class TabelsSchoolCreator {

    private PropertiesDataBase propertiesDataBase;
    private DataBaseConnector dataBaseConnector;

    public TabelsSchoolCreator(PropertiesDataBase propertiesDataBase, DataBaseConnector dataBaseConnector) {
        this.propertiesDataBase = propertiesDataBase;
        this.dataBaseConnector = dataBaseConnector;
    }

    public void createTabels(List<String> sqlScript) {
        try (Connection connector = dataBaseConnector.connection(propertiesDataBase.getConfigurationDB());
                Statement statement = connector.createStatement()) {

            statement.execute(sqlScript.stream()
                    .map(Object :: toString)
                    .collect(Collectors.joining(System.lineSeparator())));

        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect script.");
        }
    }

}
