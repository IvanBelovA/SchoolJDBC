package ua.com.foxminded.school.model;

public class GroupModel {

    private int groupId;
    private String groupName;
    private int groupQuantity;

    public GroupModel(int groupId, String groupName, int groupQuantity) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupQuantity = groupQuantity;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGroupQuantity() {
        return groupQuantity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + groupId;
        result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
        result = prime * result + groupQuantity;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GroupModel other = (GroupModel) obj;
        if (groupId != other.groupId)
            return false;
        if (groupName == null) {
            if (other.groupName != null)
                return false;
        } else if (!groupName.equals(other.groupName))
            return false;
        if (groupQuantity != other.groupQuantity)
            return false;
        return true;
    }

}
