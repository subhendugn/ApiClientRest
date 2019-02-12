package Resource;

import client.EthosClientImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.apache.log4j.Logger;
import response.ParseJSON;



public class EthosResource {
    //logger
    static Logger log = Logger.getLogger(EthosResource.class);
    static final String resource = "persons";

    //static final String value = "A00031928";
    static final String value = "";
    //static final String filter = "{\"credential\":{\"type\":\"bannerId\",\"value\":\""+value+"\"}}";
    static final String filter = "";
    static final String queryString = "id";


    public static void main(String[] args) {

        EthosClientImpl ethosClient = new EthosClientImpl();
        ethosClient.DumpValues(ethosClient);



        log.debug("----------------------------------Calling Token API-------------------------------------------------------");
        String token = ethosClient.getToken(ethosClient);
        log.debug("Access Token : "+token);
        log.debug("----------------------------------------------------------------------------------------------------------");



        log.debug("----------------------------------Calling Resource--------------------------------------------------------");
        //api/students/8cda219f-75f5-439d-9878-2674871e4413
        String json_value = ethosClient.getResponseFromApi(resource, filter, value, token,ethosClient);

        if(ethosClient.getRESPONSE_MODE().toLowerCase().equals("json")){
            log.debug("-----------------------------------JSON--------------------------------------------------------------");
            log.debug(json_value);

            log.debug("-----------------------------------------------------------------------------------------------------");
        }else{
            String xml_value = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>";

            try {
                JSONObject json_obj = new JSONObject(json_value.substring(1,json_value.length()-1));
                xml_value = xml_value + XML.toString(json_obj);

                xml_value = xml_value + "</response>";
            } catch (JSONException e) {
                //e.printStackTrace();
                log.debug(e);
            }

            log.debug("-----------------------------------XML--------------------------------------------------------------");
            log.debug("XML :: "+xml_value);
            log.debug("----------------------------------------------------------------------------------------------------");
        }

        log.debug("--------------------------------------Done----------------------------------------------------------------");


        ParseJSON parseJSON = new ParseJSON();
        parseJSON.getResult(json_value, queryString);



    }
    }
