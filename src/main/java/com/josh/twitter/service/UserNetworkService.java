package com.josh.twitter.service;

import com.josh.twitter.api.HighWorthFollowerResponse;

import java.io.IOException;

public interface UserNetworkService {

    int fetchFollowersCount(String userHandle) throws IOException;
    HighWorthFollowerResponse getHighWorthFollowers(String userHandle, int pageCount, int minFollowers, int maxFollowers) throws IOException;
}
