package com.josh.twitter.constants;

public class APIRequest {

    public static final String OATH_END_POINT = "https://api.twitter.com/oauth2/token";
    public static final String API = "https://api.twitter.com/1.1/";
    public static final String USER_TIMELINE = API+"statuses/user_timeline.json";
    public static final String FOLLOWERS_IDS = API+"followers/ids.json";
    public static final String FOLLOWERS_LISTS = API+"followers/list.json";
    public static final String FRIENDS_IDS = API+"friends/ids.json";
    public static final String FRIENDS_LISTS = API+"friends/list.json";
}
