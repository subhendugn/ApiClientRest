package response;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class ParseJSON {

    private String response;

    private List<String> result;


    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }


    public List<String> getResult(String jsonPayload, String queryString) {
        String[] query = queryString.split(" ");
        System.out.println(query.length);

        List<String> results = new ArrayList<>();

        System.out.println(jsonPayload);

        results.addAll(parseAll(jsonPayload,queryString));

        if(results.size() < 0){
            System.out.println("Not a valid Query!!");
            return null;
        }
        return result;
    }


    public void setResult(List<String> result) {
        this.result = result;
    }


    private List<String> parseAll(String jsonPayload, String queryString)
    {
        List<String> results = new ArrayList<>();
        try {
            String json = jsonPayload.substring(1,jsonPayload.length()-1);
            JSONArray jsonArray = new JSONArray(jsonPayload);
            for(int i =0; i < jsonArray.length(); i++){
                JSONObject json_obj = jsonArray.getJSONObject(i);
                for (Iterator iterator = json_obj.keys(); iterator.hasNext();) {
                    Object key1 = iterator.next();
                    String key = "";
                    Object value = "";
                    key = key1.toString();
                    value = json_obj.get(key);
                    if (key.equals(queryString)) {
                        System.out.println(value);
                        results.add(value.toString());
                    }else if(value instanceof JSONArray){

                        String json1 = value.toString();
                        JSONArray jsonArray1 = new JSONArray(json1);

                        for(int j = 0; j < jsonArray1.length(); j++){
                            JSONObject jsonObject = jsonArray1.getJSONObject(j);
                            for(Iterator iterator1 = jsonObject.keys(); iterator1.hasNext();){
                                String key3 = iterator1.next().toString();
                                String value3 = jsonObject.get(key3).toString();
                                if(key3.equals(queryString)){
                                    System.out.println(value3);
                                    results.add(value3.toString());
                                }
                            }
                        }
                    }else if(value instanceof JSONObject){

                            JSONObject jsonObject = new JSONObject(value.toString());

                            for(Iterator iterator1 = jsonObject.keys(); iterator1.hasNext();){

                                String key3 = iterator1.next().toString();
                                String value3 = jsonObject.get(key3).toString();

                                if(key3.equals(queryString)){
                                    System.out.println(value3);
                                    results.add(value3.toString());
                                }
                            }
                    }
                    }
                }
            } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }



}