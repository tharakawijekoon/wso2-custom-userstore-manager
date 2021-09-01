package org.wso2.custom.user.store.manager;

import org.wso2.carbon.user.api.Property;
import org.wso2.carbon.user.core.ldap.ActiveDirectoryUserStoreConstants;

import java.util.ArrayList;

public class CustomActiveDirectoryUserStoreConstants {

    public static final ArrayList<Property> EXTENDED_ACTIVE_DIRECTORY_UM_PROPERTIES =
            (ArrayList<Property>)ActiveDirectoryUserStoreConstants.ACTIVE_DIRECTORY_UM_PROPERTIES.clone();
    public static final ArrayList<Property> EXTENDED_OPTIONAL_ACTIVE_DIRECTORY_UM_PROPERTIES =
            (ArrayList<Property>)ActiveDirectoryUserStoreConstants.OPTIONAL_ACTIVE_DIRECTORY_UM_PROPERTIES.clone();

    private static final String CUSTOM_GROUP_REGEX = "CustomGroupRegex";

    static {

        setProperty(CUSTOM_GROUP_REGEX, "Custom Group Regex", ".*",
                "Regex pattern to filter out groups");

    }

    private static void setMandatoryProperty(String name, String displayName, String value,
                                             String description, boolean encrypt) {
        String propertyDescription = displayName + "#" + description;
        if (encrypt) {
            propertyDescription += "#encrypt";
        }
        Property property = new Property(name, value, propertyDescription, null);
        EXTENDED_ACTIVE_DIRECTORY_UM_PROPERTIES.add(property);

    }

    private static void setProperty(String name, String displayName, String value,
                                    String description) {
        Property property = new Property(name, value, displayName + "#" + description, null);
        EXTENDED_OPTIONAL_ACTIVE_DIRECTORY_UM_PROPERTIES.add(property);

    }
}

