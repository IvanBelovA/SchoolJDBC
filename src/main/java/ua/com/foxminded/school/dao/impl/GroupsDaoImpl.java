package ua.com.foxminded.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.school.dao.DataBaseConnector;
import ua.com.foxminded.school.dao.GroupsDao;
import ua.com.foxminded.school.dto.PropertiesDataBase;
import ua.com.foxminded.school.exceptions.DaoExceptions;
import ua.com.foxminded.school.model.Group;
import ua.com.foxminded.school.model.GroupModel;

public class GroupsDaoImpl implements GroupsDao {

    private static final String INSERT_GROUP = "insertGroup";
    private static final String FIND_LESS_GROUP = "findLessGroup";

    private DataBaseConnector connector;
    private PropertiesDataBase properties;

    public GroupsDaoImpl(DataBaseConnector connector, PropertiesDataBase properties) {
        this.connector = connector;
        this.properties = properties;
    }

    public List<Group> createGroups(List<String> listGroups) {
        List<Group> groups = new ArrayList<>();
        String query = properties.getPropertiesDataBase().getProperty(INSERT_GROUP);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (String group : listGroups) {
                statement.setString(1, group);
                statement.execute();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    groups.add(new Group(rs.getInt(1), group));
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data", e);
        }
        return groups;
    }

    public List<GroupModel> findMinimumGroup() {
        List<GroupModel> group = new ArrayList<>();
        String query = properties.getPropertiesDataBase().getProperty(FIND_LESS_GROUP);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    group.add(new GroupModel(resultSet.getInt(1), resultSet.getString(3), resultSet.getInt(2)));
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Impossible find less group ", e);
        }
        return group;
    }

}
