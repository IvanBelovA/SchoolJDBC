package ua.com.foxminded.school;

import ua.com.foxminded.school.dao.DataBaseConnector;
import ua.com.foxminded.school.dao.TabelsSchoolCreator;
import ua.com.foxminded.school.dao.impl.CoursesDaoImpl;
import ua.com.foxminded.school.dao.impl.GroupsDaoImpl;
import ua.com.foxminded.school.dao.impl.StudentsDaoImpl;
import ua.com.foxminded.school.dto.PropertiesDataBase;
import ua.com.foxminded.school.reader.FileReader;
import ua.com.foxminded.school.reader.PropertiesReader;
import ua.com.foxminded.school.service.DataCreatorService;
import ua.com.foxminded.school.service.SchoolMenu;
import ua.com.foxminded.school.service.ShoolCreatorService;
import ua.com.foxminded.school.view.SchoolViewer;

public class SchoolApplication {

    public static void main(String[] args) {
        String dataProperties = "generatedata.properties";
        String dataBaseProperties = "database.properties";
        String configProperties = "configurationdb.properties";

        PropertiesReader propertiesReader = new PropertiesReader();
        PropertiesDataBase propertiesDataBase = new PropertiesDataBase(
                propertiesReader.readFileProperties(configProperties),
                propertiesReader.readFileProperties(dataBaseProperties));

        FileReader reader = new FileReader();
        DataBaseConnector connector = new DataBaseConnector();
        TabelsSchoolCreator creator = new TabelsSchoolCreator(propertiesDataBase, connector);
        GroupsDaoImpl groupsDaoImpl = new GroupsDaoImpl(connector, propertiesDataBase);
        CoursesDaoImpl coursesDaoImpl = new CoursesDaoImpl(connector, propertiesDataBase);
        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(connector, propertiesDataBase);
        SchoolViewer viewer = new SchoolViewer();

        DataCreatorService creatorService = new DataCreatorService(propertiesReader, reader);
        ShoolCreatorService schoolCreator = new ShoolCreatorService(propertiesDataBase, reader, creator,
                groupsDaoImpl, coursesDaoImpl, studentsDaoImpl);
        schoolCreator.createSchool(creatorService.create(dataProperties));

        SchoolMenu schoolMenu = new SchoolMenu(viewer, groupsDaoImpl, coursesDaoImpl, studentsDaoImpl);
        schoolMenu.runSchool();
    }

}
