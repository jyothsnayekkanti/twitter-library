package com.josh.twitter.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HighWorthFollowerResponse {

    private String userHandle;
    private List<User> highWorthFollowers;
    private List<User> highWorthFriends;

    public HighWorthFollowerResponse(String userHandle, List<User> highWorthFollowers, List<User> highWorthFriends){
        this.userHandle = userHandle;
        this.highWorthFollowers = highWorthFollowers;
        this.highWorthFriends = highWorthFriends;
    }

    @JsonProperty
    public String getUserHandle() {
        return userHandle;
    }

    @JsonProperty
    public List<User> getHighWorthFollowers() {
        return highWorthFollowers;
    }

    @JsonProperty
    public List<User> getHighWorthFriends() {
        return highWorthFriends;
    }
}
