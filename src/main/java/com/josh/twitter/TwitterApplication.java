package com.josh.twitter;

import com.josh.twitter.health.TwitterConnectHealthCheck;
import com.josh.twitter.resources.TwitterResource;
import com.josh.twitter.resources.TwitterUserNetworkResource;
import com.josh.twitter.service.OathService;
import com.josh.twitter.service.TimelineService;
import com.josh.twitter.service.UserNetworkService;
import com.josh.twitter.service.impl.OathServiceImpl;
import com.josh.twitter.service.impl.TimelineServiceImpl;
import com.josh.twitter.service.impl.UserNetworkServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TwitterApplication extends Application<TwitterConfiguration>{

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApplication.class);

    public static String bearerToken = "";

    public static void main(String[] args) throws Exception {
        new TwitterApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<TwitterConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(TwitterConfiguration configuration, Environment environment) throws Exception {



        final TwitterConnectHealthCheck twitterConnectHealthCheck = new TwitterConnectHealthCheck(configuration.getConsumerKey(), configuration.getConsumerSecret());
        environment.healthChecks().register("twitterConnectHealthCheck", twitterConnectHealthCheck);

        final TimelineService timelineService = new TimelineServiceImpl();
        environment.jersey().register(timelineService);

        final TwitterResource twitterResource = new TwitterResource(timelineService);
        environment.jersey().register(twitterResource);

        final OathService oathService = new OathServiceImpl(configuration.getConsumerKey(), configuration.getConsumerSecret());
        environment.jersey().register(oathService);

        final UserNetworkService userNetworkService = new UserNetworkServiceImpl();
        environment.jersey().register(userNetworkService);

        final TwitterUserNetworkResource userNetworkResource = new TwitterUserNetworkResource(userNetworkService);
        environment.jersey().register(userNetworkResource);

        environment.lifecycle().addServerLifecycleListener(new ServerLifecycleListener() {
            @Override
            public void serverStarted(Server server) {
                /// ... do things here ....
                LOGGER.info("Server started");
                try {
                    LOGGER.info("Request Bearer Token");
                    bearerToken = oathService.requestBearerToken();
                } catch (IOException e) {
                    LOGGER.warn("Authentication failed");
                }
            }
        });


    }


}