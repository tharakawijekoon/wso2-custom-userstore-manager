# wso2-custom-userstore-manager
A custom userstore manager to filter out groups retrieved from the AD with a regex pattern

## Build
Execute the following command to build the project

```mvn clean install```

## Deploy and Configure

* Copy and place the built JAR artifact from the /target/org.wso2.custom.user.store.manager-1.0.0.jar to the <IS_HOME>/repository/components/dropins directory.

* In order to use the new userstore manager you can change the class name of the existing userstore file located at <IS_HOME>/repository/deployment/server/userstores/<USERSTORE-DOMAIN>.xml to the following class.
   ```
    <UserStoreManager class="org.wso2.custom.user.store.manager.CustomActiveDirectoryUserStoreManager">
   ```
   or add and configure a new userstore manager from the console by selecting the above class.
   <img width="1679" alt="Screen Shot 2021-09-01 at 3 42 34 PM" src="https://user-images.githubusercontent.com/47600906/131654083-3e6fb101-41b0-4acf-bdd1-8cd7ab2d21a5.png">

* Configure the regex pattern you need to filter the groups from the userstore file.
   ```
   <Property name="CustomGroupRegex">.*ICAR_NCAR.*</Property>
   ```
   or from the management console UI.
   <img width="1677" alt="Screen Shot 2021-09-01 at 3 43 11 PM" src="https://user-images.githubusercontent.com/47600906/131654230-2a547fd6-b05c-431f-a561-db7cc0180bb9.png">

## Test

After the above changes you should be able to see only roles having string "ICAR_NCAR" in the name through the management console.
