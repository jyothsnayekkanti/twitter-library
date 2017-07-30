package com.josh.twitter.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.josh.twitter.api.HighWorthFollowerResponse;
import com.josh.twitter.api.User;
import com.josh.twitter.constants.APIRequest;
import com.josh.twitter.service.UserNetworkService;
import com.josh.twitter.util.HttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.josh.twitter.TwitterApplication.bearerToken;

public class UserNetworkServiceImpl implements UserNetworkService {

    Gson gson = new Gson();
    Type userListType = new TypeToken<List<User>>() {}.getType();

    // Fetches the followers count for a given user
    @Override
    public int fetchFollowersCount(String userHandle) throws IOException {

        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("Authorization", "Bearer " + bearerToken);

        // Parse the JSON response into a JSON mapped object to fetch fields from.
        JSONObject obj = (JSONObject) JSONValue.parse(
                HttpClient.execute(APIRequest.FOLLOWERS_IDS+"?&screen_name="+userHandle+"&&stringify_ids=true",
                        "GET",
                        requestProperties));
        if (obj != null) {
            int followerCount = ((JSONArray)obj.get("ids")).size();
            //System.out.println("follower count"+followerCount);
            return followerCount;
        }
        return 0;
    }

    // Fetches the high worth followers for a given user
    @Override
    public HighWorthFollowerResponse getHighWorthFollowers(String userHandle, int pageCount, int minFollowerCount, int maxFollowerCount) throws IOException {
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("Authorization", "Bearer " + bearerToken);
        HighWorthFollowerResponse followerInfo;

        followerInfo = new HighWorthFollowerResponse(
                userHandle,
                getHighWorthFollowersNetwork(userHandle, pageCount, minFollowerCount, maxFollowerCount),
                getHighWorthFriendsNetwork(userHandle, pageCount, minFollowerCount, maxFollowerCount));

/*        String json = gson.toJson(followerInfo, HighWorthFollowerResponse.class);
        return json;        */

        return followerInfo;

    }

    private List<User> getHighWorthFollowersNetwork(String userHandle, int pageCount, int minFollowerCount, int maxFollowerCount) throws IOException {

        List<User> followersWithHWfollowers = new ArrayList<User>();
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("Authorization", "Bearer " + bearerToken);

        JSONObject obj = (JSONObject)JSONValue.parse(
                HttpClient.execute(
                        APIRequest.FOLLOWERS_LISTS+"?&screen_name="+userHandle+"&count="+pageCount,
                        "GET",
                        requestProperties));

        if (obj != null) {
            List<User> userList = new ArrayList<User>();
            JSONArray userArray = ((JSONArray) obj.get("users"));
            userList = gson.fromJson(userArray.toString(), userListType);
            followersWithHWfollowers = filterFollowers(userList, minFollowerCount, maxFollowerCount);
        }
        return followersWithHWfollowers;
    }
    private List<User> getHighWorthFriendsNetwork(String userHandle, int pageCount, int minFollowerCount, int maxFollowerCount) throws IOException {

        List<User> friendsWithHWfollowers = new ArrayList<User>();
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("Authorization", "Bearer " + bearerToken);

        JSONObject obj = (JSONObject)JSONValue.parse(
                HttpClient.execute(
                        APIRequest.FRIENDS_LISTS+"?&screen_name="+userHandle+"&count="+pageCount,
                        "GET",
                        requestProperties));

        if (obj != null) {
            List<User> userList = new ArrayList<User>();
            JSONArray userArray = ((JSONArray) obj.get("users"));
            userList = gson.fromJson(userArray.toString(), userListType);
            friendsWithHWfollowers = filterFollowers(userList, minFollowerCount, maxFollowerCount);

        }
        return friendsWithHWfollowers;
    }

    private List<User> filterFollowers(List<User> userList, int minFollowerCount, int maxFollowerCount){
        List<User> filteredUserLIst = new ArrayList<>();
        for (User user : userList) {
            if (user.getFollowers_count() >= minFollowerCount
                    && user.getFollowers_count() <= maxFollowerCount
                    && user.getLocation().matches(".*Ireland.*")) {
                filteredUserLIst.add(user);
            }
        }
        return filteredUserLIst;
    }
}
