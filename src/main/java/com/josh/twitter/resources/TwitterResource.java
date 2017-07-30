package com.josh.twitter.resources;

import com.josh.twitter.service.TimelineService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("api/twitterusertimeline")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TwitterResource {

    private TimelineService timelineService;

    public TwitterResource(TimelineService timelineService){
        this.timelineService = timelineService;
    }

    @Path("/timelinetweet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public String getTimelineTweet(@QueryParam(value="userHandle") String userHandle) throws IOException {
        String tweet = timelineService.fetchTimeLineTweet(userHandle);
        return tweet;
    }









}
