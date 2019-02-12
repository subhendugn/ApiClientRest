package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import configuration.ConfigurationException;
import configuration.ConfigurationHolder;
import org.apache.log4j.PropertyConfigurator;
import org.glassfish.jersey.client.ClientConfig;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.Base64;
import java.util.concurrent.TimeUnit;


public class EthosClientImpl implements EthosClientService{
	private String END_POINT;
	private String AUTHENTICATION_SCHEME;
	private String APP_KEY;
	private String RESPONSE_MODE;
	private String SPACE = " ";

	//logger
	static Logger log = Logger.getLogger(EthosClientImpl.class);

	public String getEND_POINT(){
		return this.END_POINT;
	}

	public String getAUTHENTICATION_SCHEME(){
		return this.AUTHENTICATION_SCHEME;
	}

	public String getAPP_KEY() {
		return this.APP_KEY;
	}

	public String getRESPONSE_MODE() {
		return this.RESPONSE_MODE;
	}

	public static boolean getLogger(){
		PropertyConfigurator.configure("log4j.properties");
		if(log.isDebugEnabled()){
			return true;
		}else{
			return false;
		}
	}
	public EthosClientImpl(){
		try {
			this.END_POINT = ConfigurationHolder.getConfiguration("EthosBaseURI");
			this.AUTHENTICATION_SCHEME = ConfigurationHolder.getConfiguration("AuthenticationScheme") + SPACE;
			this.APP_KEY = ConfigurationHolder.getConfiguration("ApiKey");
			this.RESPONSE_MODE = ConfigurationHolder.getConfiguration("ResponseMode");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void DumpValues(EthosClientImpl ethosClient){

		if(getLogger()){
			log.debug("---------------------------------------------");
			log.debug("END_POINT : "+ethosClient.getEND_POINT());
			log.debug("AUTHENTICATION_SCHEME : "+ethosClient.getAUTHENTICATION_SCHEME());
			log.debug("APP_KEY : "+ethosClient.getAPP_KEY());
			log.debug("RESPONSE_MODE : "+ethosClient.getRESPONSE_MODE());
			log.debug("---------------------------------------------");
		}


	}
    public String getToken(EthosClientImpl ethosClient) {
		String token = "";

		ClientConfig config = new ClientConfig();


		Client client = ClientBuilder.newClient(config);

		try {
			String appKey = ethosClient.getAUTHENTICATION_SCHEME() + ethosClient.getAPP_KEY();
			log.debug("appKey : "+appKey);

			Invocation.Builder request = client.target(ethosClient.getEND_POINT()+"auth").request(MediaType.APPLICATION_JSON_TYPE);
			request.header("Authorization", appKey);

			Response response = request.post(Entity.entity("", MediaType.TEXT_PLAIN));

			token = response.readEntity(String.class);
			long startDate = response.getDate().getTime();
			long expirydate = startDate + TimeUnit.MINUTES.toMinutes(5);
			log.debug("Start Time : "+ startDate);
			log.debug("Expiry Time : "+ expirydate);
		}catch (Exception e) {
			log.debug(e);
		}finally {
			// dispose
		}

		return token;
	}




    public String getResponseFromApi(String resource, String filter, String value, String token, EthosClientImpl ethosClient) {
		String result = "";
		String appKey = ethosClient.getAUTHENTICATION_SCHEME() + token;


		ClientConfig config = new ClientConfig();


		Client client = ClientBuilder.newClient(config);
		try {
			String URI = ethosClient.getEND_POINT();
			//String URI = "https://integrate.elluciancloud.com/api/student-academic-programs?student=24ee84a4-bb84-45c7-af8a-7796d730f07b";

			//URI = URI.concat("api/" + resource + "?" + filter);
			//URI= URI.concat(value); //when you have criteria
			//String requestURI = new String(Base64.);

			if(filter.startsWith("{")){
				//passing JSON
				URI = URI + "api/" + resource + "?" + "criteria=" + URLEncoder.encode(filter, "UTF-8");
			}else {
				//passing plain URL
				URI = URI.concat("api/" + resource + "?" + filter + "=" + value); // when you dont have criteria
			}



			//URI = URI + "api/student-academic-programs?student=24ee84a4-bb84-45c7-af8a-7796d730f07b";



			log.debug("URI :"+URI);
			Invocation.Builder request = client.target(URI).request();
			request.header("Authorization", appKey);
			request.header("Accept", "application/vnd.hedtech.integration.v8+json");

			Response response = request.get();

			result = response.readEntity(String.class);

		}catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
			log.debug(e);
		}finally {
			//close client
			return result;
		}
	}


}
