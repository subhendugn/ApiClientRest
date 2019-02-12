package configuration;


public class ConfigurationHolder {

    public static String getConfiguration(String key) throws ConfigurationException{
        ConfigurationServiceImpl configurationService = new ConfigurationServiceImpl();
        return configurationService.getConfiguration(key);
    }
}
