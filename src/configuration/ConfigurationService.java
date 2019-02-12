package configuration;
import org.w3c.dom.NodeList;

public interface ConfigurationService {
    NodeList getNodeList() throws ConfigurationException;

    String getConfiguration(String key) throws ConfigurationException;
}
