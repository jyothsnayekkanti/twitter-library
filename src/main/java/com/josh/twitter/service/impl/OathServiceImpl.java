package com.josh.twitter.service.impl;

import com.josh.twitter.constants.APIRequest;
import com.josh.twitter.service.OathService;
import com.josh.twitter.util.HttpClient;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class OathServiceImpl implements OathService {

    private String consumerKey;
    private String consumerSecret;

    public OathServiceImpl(String consumerKey, String consumerSecret){
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    // Constructs the request for requesting a bearer token and returns that token as a string
    @Override
    public String requestBearerToken() throws IOException {

        String bearerToken = null;
        String encodedCredentials = encodeKeys();

        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("Authorization", "Basic " + encodedCredentials);
        requestProperties.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        requestProperties.put("Content-Length", "29");

        // Parse the JSON response into a JSON mapped object to fetch fields from.
        JSONObject obj = (JSONObject) JSONValue.parse(HttpClient.execute(APIRequest.OATH_END_POINT,"POST",requestProperties));

        if (obj != null) {
            String tokenType = (String)obj.get("token_type");
            String token = (String)obj.get("access_token");

            bearerToken = ((tokenType.equals("bearer")) && (token != null)) ? token : "";
            //System.out.println("bearerToken"+bearerToken);
        }
        return bearerToken;

    }

    // Encodes the consumer key and secret to create the basic authorization key
    private String encodeKeys() {
        try {
            String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
            String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");

            String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
            byte[] encodedBytes = Base64.encodeBase64(fullKey.getBytes());
            return new String(encodedBytes);
        }
        catch (UnsupportedEncodingException e) {
            return new String();
        }
    }
}
