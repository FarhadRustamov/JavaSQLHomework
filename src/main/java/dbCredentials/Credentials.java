package dbCredentials;

import utils.PropertyUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Credentials {

    public Map<String, String> getCredentials(String filePath) {
        Properties properties = PropertyUtils.propertyLoader(filePath);
        Map<String, String> credentials = new HashMap<>();

        for (String key : properties.stringPropertyNames()) {
            credentials.put(key, properties.getProperty(key));
        }
        return credentials;
    }
}
