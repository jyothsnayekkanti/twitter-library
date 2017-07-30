package com.josh.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class TwitterConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String consumerKey;

    @NotEmpty
    @JsonProperty
    private String consumerSecret;

    public String getConsumerKey() {
        return consumerKey;

    }

    public String getConsumerSecret() {
        return consumerSecret;
    }
}
