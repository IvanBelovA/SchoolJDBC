package ua.com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.com.foxminded.school.dao.impl.GroupsDaoImpl;
import ua.com.foxminded.school.dto.PropertiesDataBase;
import ua.com.foxminded.school.model.Group;
import ua.com.foxminded.school.model.GroupModel;

class GroupsDaoTest {

    private static final String GROUP_NAME = "AA-11";
    private static final int GROUP_ID = 1;
    private static final int STUDENT_ID = 1;

    private GroupsDaoImpl groupsDao;
    private DataBaseConnector connector;
    private PropertiesDataBase properties;

    @BeforeEach
    void setUp() {
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        Properties testProperties = databaseTest.setUpSqlQuery();

        connector = Mockito.mock(DataBaseConnector.class);
        properties = Mockito.mock(PropertiesDataBase.class);
        groupsDao = new GroupsDaoImpl(connector, properties);

        when(properties.getPropertiesDataBase()).thenReturn(testProperties);
    }

    @Test
    void shouldReturnAllLessGroupWhenCallHim() {
        //given
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        databaseTest.setUpGroupsDaoTest();
        List<GroupModel> actual = Arrays.asList(new GroupModel(STUDENT_ID, GROUP_NAME, GROUP_ID));

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.connection());
        List<GroupModel> expected = groupsDao.findMinimumGroup();

        //then
        assertEquals(actual, expected);
    }

    @Test
    void shouldAddGroupsWhenDeliverGroups() {
        //given
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        databaseTest.getCleanDataBase();
        List<Group> actual = Arrays.asList(new Group(STUDENT_ID, GROUP_NAME));
        List<String> testGroupName = Arrays.asList(GROUP_NAME);

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.connection());
        List<Group> expected = groupsDao.createGroups(testGroupName);

        //then
        assertEquals(actual, expected);
    }
}
