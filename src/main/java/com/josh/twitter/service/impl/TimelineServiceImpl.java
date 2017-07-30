package com.josh.twitter.service.impl;

import com.josh.twitter.constants.APIRequest;
import com.josh.twitter.service.TimelineService;
import com.josh.twitter.util.HttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.josh.twitter.TwitterApplication.bearerToken;

public class TimelineServiceImpl implements TimelineService {

    // Fetches the first tweet from a given user's timeline
    @Override
    public String fetchTimeLineTweet(String userHandle) throws IOException {

        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("Authorization", "Bearer " + bearerToken);

        // Parse the JSON response into a JSON mapped object to fetch fields from.
        JSONArray obj = (JSONArray) JSONValue.parse(
                HttpClient.execute(
                        APIRequest.USER_TIMELINE+"?screen_name="+userHandle+"&count=1",
                        "GET",
                        requestProperties));

        if (obj != null) {
            String tweet = ((JSONObject)obj.get(0)).get("text").toString();
            return (tweet != null) ? tweet : "";
        }
        return new String();
    }
}
