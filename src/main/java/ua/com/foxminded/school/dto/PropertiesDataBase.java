package ua.com.foxminded.school.dto;

import java.util.Properties;

public class PropertiesDataBase {

    private Properties ConfigurationDB;
    private Properties propertiesDataBase;

    public PropertiesDataBase(Properties configurationDB, Properties createTabelsScript) {
        ConfigurationDB = configurationDB;
        propertiesDataBase = createTabelsScript;
    }

    public Properties getConfigurationDB() {
        return ConfigurationDB;
    }

    public void setConfigurationDB(Properties configurationDB) {
        ConfigurationDB = configurationDB;
    }

    public Properties getPropertiesDataBase() {
        return propertiesDataBase;
    }

    public void setPropertiesDataBase(Properties createTabelsScript) {
        propertiesDataBase = createTabelsScript;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ConfigurationDB == null) ? 0 : ConfigurationDB.hashCode());
        result = prime * result + ((propertiesDataBase == null) ? 0 : propertiesDataBase.hashCode());
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
        PropertiesDataBase other = (PropertiesDataBase) obj;
        if (ConfigurationDB == null) {
            if (other.ConfigurationDB != null)
                return false;
        } else if (!ConfigurationDB.equals(other.ConfigurationDB))
            return false;
        if (propertiesDataBase == null) {
            if (other.propertiesDataBase != null)
                return false;
        } else if (!propertiesDataBase.equals(other.propertiesDataBase))
            return false;
        return true;
    }

}
