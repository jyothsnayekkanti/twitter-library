package com.josh.twitter.service;

import java.io.IOException;

public interface TimelineService {

    String fetchTimeLineTweet(String userHandle) throws IOException;

}
