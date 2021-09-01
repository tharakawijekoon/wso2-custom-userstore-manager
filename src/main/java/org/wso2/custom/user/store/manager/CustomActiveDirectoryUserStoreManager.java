package org.wso2.custom.user.store.manager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.user.api.Properties;
import org.wso2.carbon.user.api.Property;
import org.wso2.carbon.user.api.RealmConfiguration;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.claim.ClaimManager;
import org.wso2.carbon.user.core.ldap.ActiveDirectoryUserStoreManager;
import org.wso2.carbon.user.core.profile.ProfileConfigurationManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomActiveDirectoryUserStoreManager extends ActiveDirectoryUserStoreManager {

    private static Log log = LogFactory.getLog(CustomActiveDirectoryUserStoreManager.class);

    private static final String CUSTOM_GROUP_REGEX = "CustomGroupRegex";

    public CustomActiveDirectoryUserStoreManager() {
        super();
    }

    public CustomActiveDirectoryUserStoreManager(RealmConfiguration realmConfig, Map<String, Object> properties,
                                                 ClaimManager claimManager, ProfileConfigurationManager profileManager,
                                                 UserRealm realm, Integer tenantId) throws UserStoreException {

        super(realmConfig, properties, claimManager, profileManager, realm, tenantId);
        log.info("CustomActiveDirectoryUserStoreManager initialized...");
    }

    protected String[] getLDAPRoleListOfUser(String userName, String filter, String searchBase, boolean shared)
            throws UserStoreException {

        String[] results = super.getLDAPRoleListOfUser(userName, filter, searchBase, shared);

        String customGroupRegex = realmConfig.getUserStoreProperty(CUSTOM_GROUP_REGEX);


        if(!StringUtils.isEmpty(customGroupRegex)) {

            if(log.isDebugEnabled()){
                log.debug("Filtering groups : " + Arrays.toString(results) + " with regex : " + customGroupRegex);
            }
            try {
                results = Stream.of(results).filter(str -> str.matches(customGroupRegex))
                        .collect(Collectors.toList()).toArray(new String[0]);
            } catch (PatternSyntaxException ex) {
                throw new UserStoreException("Invalid CustomGroupRegex configured ", ex);
            }

            if(log.isDebugEnabled()){
                log.debug("Filtered groups : " + Arrays.toString(results) + " with regex : " + customGroupRegex);
            }
        }

        return results;
    }

    protected List<String> getLDAPRoleNames(int searchTime, String filter, int maxItemLimit, String searchFilter,
                                            String roleNameProperty, String searchBase, boolean appendTenantDomain)
            throws UserStoreException {
        List<String> results = super.getLDAPRoleNames(searchTime, filter, maxItemLimit, searchFilter, roleNameProperty,
                searchBase, appendTenantDomain);
        String customGroupRegex = realmConfig.getUserStoreProperty(CUSTOM_GROUP_REGEX);

        if(!StringUtils.isEmpty(customGroupRegex)) {

            if(log.isDebugEnabled()) {
                log.debug("Filtering groups : " + results +" with regex : " + customGroupRegex);
            }
            try {
                results = results.stream().filter(str -> str.matches(customGroupRegex)).collect(Collectors.toList());
            } catch (PatternSyntaxException ex) {
                throw new UserStoreException("Invalid CustomGroupRegex configured "+ customGroupRegex, ex);
            }

            if(log.isDebugEnabled()){
                log.debug("Filtered groups : " + results +" with regex : " + customGroupRegex);
            }
        }

        return results;
    }

    public Properties getDefaultUserStoreProperties() {
        Properties properties = super.getDefaultUserStoreProperties();
        properties.setMandatoryProperties(CustomActiveDirectoryUserStoreConstants.EXTENDED_ACTIVE_DIRECTORY_UM_PROPERTIES.toArray
                (new Property[CustomActiveDirectoryUserStoreConstants.EXTENDED_ACTIVE_DIRECTORY_UM_PROPERTIES.size()]));
        properties.setOptionalProperties(CustomActiveDirectoryUserStoreConstants.EXTENDED_OPTIONAL_ACTIVE_DIRECTORY_UM_PROPERTIES.toArray
                (new Property[CustomActiveDirectoryUserStoreConstants.EXTENDED_OPTIONAL_ACTIVE_DIRECTORY_UM_PROPERTIES.size()]));
        return properties;
    }

}
