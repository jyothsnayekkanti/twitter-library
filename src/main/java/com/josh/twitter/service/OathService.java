package com.josh.twitter.service;

import java.io.IOException;

public interface OathService {

    String requestBearerToken() throws IOException;

}
