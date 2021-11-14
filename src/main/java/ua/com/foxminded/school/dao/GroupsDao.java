package ua.com.foxminded.school.dao;

import java.util.List;

import ua.com.foxminded.school.model.Group;
import ua.com.foxminded.school.model.GroupModel;

public interface GroupsDao extends GenericDao<Group> {

    List<Group> createGroups(List<String> listGroups);
    List<GroupModel> findMinimumGroup();
}
