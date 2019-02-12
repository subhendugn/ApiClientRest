package client;

interface EthosClientService {
    String getEND_POINT();

    String getAUTHENTICATION_SCHEME();

    String getAPP_KEY();

    String getRESPONSE_MODE();

    String getToken(EthosClientImpl ethosClient);

    String getResponseFromApi(String resource, String filter, String value, String token, EthosClientImpl ethosClient);
}
