package com.josh.twitter.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User{

    private long id;
    private String id_str;
    private String name;
    private String screen_name;
    private String location;
    private int followers_count;
    private int friends_count;

    @JsonProperty
    public String getLocation() {
        return location;
    }

    @JsonProperty

        public long getId() {
            return id;
        }

        @JsonProperty
        public String getId_str() {
            return id_str;
        }

        @JsonProperty
        public String getName() {
            return name;
        }

        @JsonProperty
        public String getScreen_name() {
            return screen_name;
        }

        @JsonProperty
        public int getFollowers_count() {
            return followers_count;
        }

        @JsonProperty
        public int getFriends_count() {
            return friends_count;
        }
}
