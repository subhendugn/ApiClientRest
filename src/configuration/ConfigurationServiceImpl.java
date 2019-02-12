package configuration;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileNotFoundException;


public class ConfigurationServiceImpl implements ConfigurationService{

    public NodeList getNodeList() throws ConfigurationException {
        try {
            File configuration = new File("./src/config/EthosConfiguration.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document config = dBuilder.parse(configuration);
            config.getDocumentElement().normalize();

            NodeList nodeList = config.getElementsByTagName("EthosConfiguration");

            return nodeList;
        } catch (FileNotFoundException fe){
            throw new ConfigurationException( fe );
        }
        catch (Exception e) {
            throw new ConfigurationException( e );
        } finally {

        }
    }

    public String getConfiguration(String nodeKey) throws ConfigurationException{
        String nodeValue = "";
        try{
            NodeList list = getNodeList();
            for(int i = 0; i < list.getLength(); i++){

                Node numberOfNodes = list.item(i);

                if(numberOfNodes.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) numberOfNodes;
                    nodeValue = element.getElementsByTagName(nodeKey).item(0).getTextContent();
                }
            }
            return nodeValue;

        }catch (Exception e){
            throw new ConfigurationException( e );
        }
    }

}
