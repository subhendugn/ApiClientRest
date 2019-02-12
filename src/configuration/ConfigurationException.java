package configuration;
import org.apache.log4j.Logger;

public class ConfigurationException extends Throwable {
    //logger
    static Logger log = Logger.getLogger(ConfigurationException.class);
    ConfigurationException( Exception e ) {
        log.debug(e.getStackTrace());
    }

}

