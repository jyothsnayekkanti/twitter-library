package com.josh.twitter.health;

import com.codahale.metrics.health.HealthCheck;

public class TwitterConnectHealthCheck extends HealthCheck {

    private String consumerKey;
    private String consumerSecret;

    public TwitterConnectHealthCheck(String consumerKey, String consumerSecret){
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    @Override
    protected Result check() throws Exception {
            System.out.println("Health Check");
            if(null != consumerKey && null != consumerSecret)
                return Result.healthy();
            else
                return Result.unhealthy("No consumer keys provided");
    }
}
